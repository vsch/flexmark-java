package com.vladsch.flexmark.spec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecReader {
    public static final String EXAMPLE_START = "```````````````````````````````` example";
    public static final String TYPE_BREAK = ".";
    public static final String EXAMPLE_BREAK = "````````````````````````````````";
    protected static final Pattern SECTION_PATTERN = Pattern.compile("#{1,6} *(.*)");

    protected final InputStream inputStream;

    protected State state = State.BEFORE;
    protected String section;
    protected StringBuilder source;
    protected StringBuilder html;
    protected StringBuilder ast;
    protected int exampleNumber = 0;

    protected List<SpecExample> examples = new ArrayList<>();

    protected SpecReader(InputStream stream) {
        this.inputStream = stream;
    }

    public static List<SpecExample> readExamples(String specResource) {
        try (InputStream stream = getSpecInputStream(specResource)) {
            return new SpecReader(stream).read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readExamplesAsString(String specResource) {
        List<SpecExample> examples = SpecReader.readExamples(specResource);
        List<String> result = new ArrayList<>();
        for (SpecExample example : examples) {
            result.add(example.getSource());
        }
        return result;
    }

    public static String readSpec(String specResource) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getSpecInputStream(specResource), Charset.forName("UTF-8")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getSpecInputStream(String specResource) {
        InputStream stream = SpecReader.class.getResourceAsStream(specResource != null ? specResource : "/spec.txt");
        if (stream == null) {
            throw new IllegalStateException("Could not load spec.txt classpath resource");
        }

        return stream;
    }

    protected List<SpecExample> read() throws IOException {
        resetContents();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        }

        return examples;
    }

    protected void processLine(String line) {
        switch (state) {
            case BEFORE:
                Matcher matcher = SECTION_PATTERN.matcher(line);
                if (matcher.matches()) {
                    section = matcher.group(1);
                    exampleNumber = 0;
                }
                if (line.equals(EXAMPLE_START)) {
                    state = State.SOURCE;
                    exampleNumber++;
                }
                break;
            case SOURCE:
                if (line.equals(TYPE_BREAK)) {
                    state = State.HTML;
                } else {
                    // examples use "rightwards arrow" to show tab
                    String processedLine = line.replace('\u2192', '\t');
                    source.append(processedLine).append('\n');
                }
                break;
            case HTML:
                if (line.equals(EXAMPLE_BREAK)) {
                    state = State.BEFORE;
                    examples.add(new SpecExample(section, exampleNumber, source.toString(), html.toString(), null));
                    resetContents();
                } else if (line.equals(TYPE_BREAK)) {
                    state = State.AST;
                } else {
                    html.append(line).append('\n');
                }
                break;
            case AST:
                if (line.equals(EXAMPLE_BREAK)) {
                    state = State.BEFORE;
                    examples.add(new SpecExample(section, exampleNumber, source.toString(), html.toString(), ast.toString()));
                    resetContents();
                } else {
                    ast.append(line).append('\n');
                }
                break;
        }
    }

    protected void resetContents() {
        source = new StringBuilder();
        html = new StringBuilder();
        ast = new StringBuilder();
    }

    protected enum State {
        BEFORE, SOURCE, HTML, AST
    }
}
