package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.test.util.TestUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecReader {
    public static final String EXAMPLE_KEYWORD = "example";
    public static final String EXAMPLE_BREAK = "````````````````````````````````";
    public static final String EXAMPLE_START = EXAMPLE_BREAK + " " + EXAMPLE_KEYWORD;
    public static final String EXAMPLE_START_NBSP = EXAMPLE_BREAK + "\u00A0" + EXAMPLE_KEYWORD;
    public static final String EXAMPLE_TEST_BREAK = "````````````````";
    public static final String EXAMPLE_TEST_START = EXAMPLE_TEST_BREAK + " " + EXAMPLE_KEYWORD;
    public static final String OPTIONS_KEYWORD = "options";
    public static final String OPTIONS_STRING = " " + OPTIONS_KEYWORD;
    public static final Pattern OPTIONS_PATTERN = Pattern.compile(".*(?:\\s|\u00A0)\\Q" + OPTIONS_KEYWORD + "\\E(?:\\s|\u00A0)*\\((?:\\s|\u00A0)*(.*)(?:\\s|\u00A0)*\\)(?:\\s|\u00A0)*");
    public static final String TYPE_BREAK = ".";
    public static final String TYPE_TEST_BREAK = "…";
    protected static final Pattern SECTION_PATTERN = Pattern.compile("#{1,6} +(.*)");

    protected final @NotNull InputStream inputStream;
    protected final @NotNull ResourceLocation resourceLocation;
    protected final List<SpecExample> examples = new ArrayList<>();

    protected State state = State.BEFORE;
    protected String section;
    protected String optionsSet;
    protected StringBuilder source;
    protected StringBuilder html;
    protected StringBuilder ast;
    protected StringBuilder comment;
    protected int exampleNumber = 0;
    protected int lineNumber = 0;
    protected int contentLineNumber = 0;

    protected SpecReader(@NotNull InputStream stream, @NotNull ResourceLocation location) {
        this.inputStream = stream;
        this.resourceLocation = location;
    }

    @NotNull
    public String getFileUrl() {
        return resourceLocation.getFileUrl();
    }

    @NotNull
    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    @NotNull
    public List<SpecExample> getExamples() {
        return examples;
    }

    @NotNull
    public List<String> getExamplesSourceAsString() {
        List<String> result = new ArrayList<>();
        for (SpecExample example : examples) {
            result.add(example.getSource());
        }
        return result;
    }

    public static @NotNull SpecReader create(@NotNull ResourceLocation location) {
        return create(location, SpecReader::new);
    }

    public static @NotNull <S extends SpecReader> S create(@NotNull ResourceLocation location, @NotNull SpecReaderFactory<S> readerFactory) {
        InputStream stream = getSpecInputStream(location);
        return readerFactory.create(stream, location);
    }

    public static @NotNull SpecReader createAndReadExamples(@NotNull ResourceLocation location) {
        return createAndReadExamples(location, SpecReader::new);
    }
    public static @NotNull <S extends SpecReader> S createAndReadExamples(@NotNull ResourceLocation location, @NotNull SpecReaderFactory<S> readerFactory) {
        S reader = create(location, readerFactory);
        reader.readExamples();
        return reader;
    }

    public static @NotNull String readSpec(@NotNull ResourceLocation location) {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            InputStream inputStream = getSpecInputStream(location);
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            reader.close();
            streamReader.close();
            inputStream.close();
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static @NotNull InputStream getSpecInputStream(@NotNull ResourceLocation location) {
        String useSpecResource = location.getResolvedResourcePath();
        InputStream stream = location.getResourceClass().getResourceAsStream(useSpecResource);
        if (stream == null) {
            throw new IllegalStateException("Could not load " + location);
        }

        return stream;
    }

    public void readExamples() {
        try {
            resetContents();

            String line;
            lineNumber = 0;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                processLine(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // can use these to generate spec from source
    protected void addSpecLine(String line) {

    }

    protected void addSpecExample(@NotNull SpecExample example) {
        examples.add(example);
    }

    protected void processLine(String line) {
        boolean lineAbsorbed = false;
        boolean lineProcessed = false;

        switch (state) {
            case BEFORE:
                Matcher matcher = SECTION_PATTERN.matcher(line);
                if (matcher.matches()) {
                    section = matcher.group(1);
                    lineProcessed = true;
                    exampleNumber = 0;
                } else if (line.startsWith(EXAMPLE_START) || line.startsWith(EXAMPLE_START_NBSP)) {
                    Matcher option_matcher = OPTIONS_PATTERN.matcher(line.subSequence(EXAMPLE_START.length(), line.length()));
                    if (option_matcher.matches()) {
                        optionsSet = option_matcher.group(1);
                    }

                    state = State.SOURCE;
                    exampleNumber++;
                    contentLineNumber = lineNumber;
                    lineAbsorbed = true;
                }
                break;
            case SOURCE:
                if (line.equals(TYPE_BREAK)) {
                    state = State.HTML;
                } else {
                    // examples use "rightwards arrow" to show tab
                    String processedLine = TestUtils.unShowTabs(line);
                    source.append(processedLine).append('\n');
                }
                lineAbsorbed = true;
                break;
            case HTML:
                if (line.equals(EXAMPLE_BREAK)) {
                    state = State.BEFORE;
                    addSpecExample(new SpecExample(resourceLocation, contentLineNumber, optionsSet, section, exampleNumber, source.toString(), html.toString(), null, comment == null ? null : comment.toString()));
                    resetContents();
                    lineAbsorbed = true;
                } else if (line.equals(TYPE_BREAK)) {
                    state = State.AST;
                    lineAbsorbed = true;
                } else {
                    String processedLine = TestUtils.unShowTabs(line);
                    html.append(processedLine).append('\n');
                    lineAbsorbed = true;
                }
                break;
            case AST:
                if (line.equals(EXAMPLE_BREAK)) {
                    state = State.BEFORE;
                    addSpecExample(new SpecExample(resourceLocation, contentLineNumber, optionsSet, section, exampleNumber, source.toString(), html.toString(), ast.toString(), comment == null ? null : comment.toString()));
                    resetContents();
                } else {
                    ast.append(line).append('\n');
                }
                lineAbsorbed = true;
                break;
        }

        if (!lineAbsorbed) {
            if (lineProcessed) {
                comment = null;
            } else if (section != null) {
                if (comment == null) comment = new StringBuilder();
                comment.append(line).append('\n');
            }
            addSpecLine(line);
        }
    }

    protected void resetContents() {
        optionsSet = "";
        source = new StringBuilder();
        html = new StringBuilder();
        ast = new StringBuilder();
        comment = null;
        contentLineNumber = 0;
    }

    protected enum State {
        BEFORE, SOURCE, HTML, AST
    }
}
