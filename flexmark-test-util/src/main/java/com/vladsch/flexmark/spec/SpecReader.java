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
    public static final String OPTIONS_STRING = " options";
    public static final Pattern OPTIONS_PATTERN = Pattern.compile(".*\\Q" + OPTIONS_STRING + "\\E\\s*\\(\\s*(.*)\\s*\\)\\s*");
    public static final String TYPE_BREAK = ".";
    public static final String EXAMPLE_BREAK = "````````````````````````````````";
    protected static final Pattern SECTION_PATTERN = Pattern.compile("#{1,6} *(.*)");

    protected final InputStream inputStream;

    protected State state = State.BEFORE;
    protected String section;
    protected String optionsSet;
    protected StringBuilder source;
    protected StringBuilder html;
    protected StringBuilder ast;
    protected int exampleNumber = 0;

    protected List<SpecExample> examples = new ArrayList<>();

    protected SpecReader(InputStream stream) {
        this.inputStream = stream;
    }

    public static List<SpecExample> readExamples() {
        return readExamples(null, null);
    }

    public static List<SpecExample> readExamples(String specResource) {
        return readExamples(specResource, null);
    }

    public static List<SpecExample> readExamples(String specResource, SpecReaderFactory readerFactory) {
        try (InputStream stream = getSpecInputStream(specResource)) {
            SpecReader reader;
            if (readerFactory == null) reader = new SpecReader(stream);
            else reader = readerFactory.create(stream);
            return reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readExamplesAsString() {
        return readExamplesAsString(null, null);
    }

    public static List<String> readExamplesAsString(String specResource) {
        return readExamplesAsString(specResource, null);
    }

    public static List<String> readExamplesAsString(String specResource, SpecReaderFactory readerFactory) {
        List<SpecExample> examples = SpecReader.readExamples(specResource, readerFactory);
        List<String> result = new ArrayList<>();
        for (SpecExample example : examples) {
            result.add(example.getSource());
        }
        return result;
    }

    public static String readSpec() {
        return readSpec(null);
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

    public static InputStream getSpecInputStream() {
        return getSpecInputStream(null);
    }

    public static InputStream getSpecInputStream(String specResource) {
        String specPath = specResource != null ? specResource : "/spec.txt";
        InputStream stream = SpecReader.class.getResourceAsStream(specPath);
        if (stream == null) {
            throw new IllegalStateException("Could not load " + specResource + " classpath resource");
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

    // can use these to generate spec from source
    protected void addSpecLine(String line) {

    }

    protected void addSpecExample(SpecExample example) {
        examples.add(example);
    }

    protected void processLine(String line) {
        boolean lineAbsorbed = false;

        switch (state) {
            case BEFORE:
                Matcher matcher = SECTION_PATTERN.matcher(line);
                if (matcher.matches()) {
                    section = matcher.group(1);
                    exampleNumber = 0;
                } else if (line.startsWith(EXAMPLE_START)) {
                    Matcher option_matcher = OPTIONS_PATTERN.matcher(line.subSequence(EXAMPLE_START.length(), line.length()));
                    if (option_matcher.matches()) {
                        optionsSet = option_matcher.group(1);
                    }

                    state = State.SOURCE;
                    exampleNumber++;
                    lineAbsorbed = true;
                }
                break;
            case SOURCE:
                if (line.equals(TYPE_BREAK)) {
                    state = State.HTML;
                    lineAbsorbed = true;
                } else {
                    // examples use "rightwards arrow" to show tab
                    String processedLine = line.replace('\u2192', '\t');
                    source.append(processedLine).append('\n');
                    lineAbsorbed = true;
                }
                break;
            case HTML:
                if (line.equals(EXAMPLE_BREAK)) {
                    state = State.BEFORE;
                    addSpecExample(new SpecExample(optionsSet, section, exampleNumber, source.toString(), html.toString(), null));
                    resetContents();
                    lineAbsorbed = true;
                } else if (line.equals(TYPE_BREAK)) {
                    state = State.AST;
                    lineAbsorbed = true;
                } else {
                    html.append(line).append('\n');
                    lineAbsorbed = true;
                }
                break;
            case AST:
                if (line.equals(EXAMPLE_BREAK)) {
                    state = State.BEFORE;
                    addSpecExample(new SpecExample(optionsSet, section, exampleNumber, source.toString(), html.toString(), ast.toString()));
                    resetContents();
                    lineAbsorbed = true;
                } else {
                    ast.append(line).append('\n');
                    lineAbsorbed = true;
                }
                break;
        }

        if (!lineAbsorbed)

        {
            addSpecLine(line);
        }
    }

    protected void resetContents() {
        optionsSet = "";
        source = new StringBuilder();
        html = new StringBuilder();
        ast = new StringBuilder();
    }

    protected enum State {
        BEFORE, SOURCE, HTML, AST
    }
}
