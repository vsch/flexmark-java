package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.util.misc.Pair;
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

import static com.vladsch.flexmark.test.util.spec.SpecReader.State.COMMENT;

public class SpecReader {
    final public static String EXAMPLE_KEYWORD = "example";
    final public static String EXAMPLE_BREAK = "````````````````````````````````";
    final public static String EXAMPLE_START = EXAMPLE_BREAK + " " + EXAMPLE_KEYWORD;
    final public static String EXAMPLE_START_NBSP = EXAMPLE_BREAK + "\u00A0" + EXAMPLE_KEYWORD;
    final public static String EXAMPLE_TEST_BREAK = "````````````````";
    final public static String EXAMPLE_TEST_START = EXAMPLE_TEST_BREAK + " " + EXAMPLE_KEYWORD;
    final public static String OPTIONS_KEYWORD = "options";
    final public static String OPTIONS_STRING = " " + OPTIONS_KEYWORD;
    final public static Pattern OPTIONS_PATTERN = Pattern.compile(".*(?:\\s|\u00A0)\\Q" + OPTIONS_KEYWORD + "\\E(?:\\s|\u00A0)*\\((?:\\s|\u00A0)*(.*)(?:\\s|\u00A0)*\\)(?:\\s|\u00A0)*");
    final public static String SECTION_BREAK = ".";
    final public static String SECTION_TEST_BREAK = "…";
    protected static final Pattern SECTION_PATTERN = Pattern.compile("#{1,6} +(.*)");

    protected final @NotNull InputStream inputStream;
    protected final @NotNull ResourceLocation resourceLocation;
    protected final boolean compoundSections;
    protected final List<SpecExample> examples = new ArrayList<>();

    protected final String[] sections = new String[7]; // 0 is not used and signals no section when indexed by lastSectionLevel
    protected int lastSectionLevel = 1;

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
    protected int commentLineNumber = 0;

    public SpecReader(@NotNull InputStream stream, @NotNull ResourceLocation location, boolean compoundSections) {
        this.inputStream = stream;
        this.resourceLocation = location;
        this.compoundSections = compoundSections;
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

    public static @NotNull SpecReader create(@NotNull ResourceLocation location, boolean compoundSections) {
        return create(location, (stream, location1) -> new SpecReader(stream, location1, compoundSections));
    }

    public static @NotNull <S extends SpecReader> S create(@NotNull ResourceLocation location, @NotNull SpecReaderFactory<S> readerFactory) {
        InputStream stream = location.getResourceInputStream();
        return readerFactory.create(stream, location);
    }

    public static @NotNull SpecReader createAndReadExamples(@NotNull ResourceLocation location, boolean compoundSections) {
        return createAndReadExamples(location, (stream, location1) -> new SpecReader(stream, location1, compoundSections));
    }

    public static @NotNull <S extends SpecReader> S createAndReadExamples(@NotNull ResourceLocation location, @NotNull SpecReaderFactory<S> readerFactory) {
        S reader = create(location, readerFactory);
        reader.readExamples();
        return reader;
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

            if (state == COMMENT) {
                // unterminated comment
                throw new IllegalStateException("Unterminated comment\n" + resourceLocation.getFileUrl(commentLineNumber));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // can use these to generate spec from source
    protected void addSpecLine(String line, boolean isSpecExampleOpen) {

    }

    protected void addSpecExample(@NotNull SpecExample example) {
        examples.add(example);
    }

    protected void processLine(String line) {
        boolean lineAbsorbed = false;
        boolean lineProcessed = false;

        switch (state) {
            case COMMENT: {
                // look for comment end
                String trimmed = line.trim();

                if (line.trim().startsWith("-->")) {
                    if (trimmed.endsWith("<!--")) {
                        // reset line number
                        commentLineNumber = lineNumber - 1;
                    } else {
                        state = State.BEFORE;
                    }
                    lineProcessed = true;
                }
            }
            break;

            case BEFORE: {
                String trimmed = line.trim();
                if (trimmed.startsWith("<!--")) {
                    if (!trimmed.endsWith("-->")) {
                        state = COMMENT;
                        commentLineNumber = lineNumber - 1;
                    }
                    lineProcessed = true;
                    break;
                }

                Matcher matcher = SECTION_PATTERN.matcher(line);
                if (matcher.matches()) {
                    if (compoundSections) {
                        Pair<String, Integer> pair = TestUtils.addSpecSection(matcher.group(), matcher.group(1), sections);
                        lastSectionLevel = pair.getSecond();
                        section = pair.getFirst();
                    } else {
                        section = matcher.group(1);
                    }

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
                    // NOTE: let dump spec reader get the actual definition line for comparison
                    //lineAbsorbed = true;
                }
            }
            break;
            case SOURCE:
                if (line.equals(SECTION_BREAK)) {
                    state = State.HTML;
                } else {
                    // examples use "rightwards arrow" to show tab
                    String processedLine = TestUtils.fromVisibleSpecText(line);
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
                } else if (line.equals(SECTION_BREAK)) {
                    state = State.AST;
                    lineAbsorbed = true;
                } else {
                    String processedLine = TestUtils.fromVisibleSpecText(line);
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
            } else if (section != null && state == State.BEFORE) {
                if (comment == null) comment = new StringBuilder();
                comment.append(line).append('\n');
            }
            addSpecLine(line, state != State.BEFORE && state != COMMENT);
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
        BEFORE, SOURCE, HTML, AST, COMMENT
    }
}
