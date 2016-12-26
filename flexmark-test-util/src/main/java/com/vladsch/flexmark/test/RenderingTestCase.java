package com.vladsch.flexmark.test;

import com.vladsch.flexmark.IParse;
import com.vladsch.flexmark.IRender;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.junit.AssumptionViolatedException;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public abstract class RenderingTestCase {

    public static final String IGNORE_OPTION_NAME = "IGNORE";
    public static final String FAIL_OPTION_NAME = "FAIL";
    public static final String NO_FILE_EOL_OPTION_NAME = "NO_FILE_EOL";
    public static DataKey<Boolean> FAIL = new DataKey<>(FAIL_OPTION_NAME, false);
    public static DataKey<Boolean> IGNORE = new DataKey<>(IGNORE_OPTION_NAME, false);
    public static DataKey<Boolean> NO_FILE_EOL = new DataKey<>(NO_FILE_EOL_OPTION_NAME, false);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    protected abstract IParse parser();
    protected abstract IRender renderer();
    protected abstract SpecExample example();

    /**
     * Customize options for an example
     *
     * @param optionSet name of the options set to use
     * @return options or null to use default
     */
    protected DataHolder options(String optionSet) {
        assert optionSet == null;
        return null;
    }

    /**
     * process comma separated list of option sets and combine them for final set to use
     *
     * @param example  spec example instance for which options are being processed
     * @param optionSets comma separate list of option set names
     * @return combined set from applying these options together
     */
    protected DataHolder getOptions(SpecExample example, String optionSets) {
        if (optionSets == null) return null;
        String[] optionNames = optionSets.replace('\u00A0', ' ').split(",");
        DataHolder options = null;
        boolean isFirst = true;
        for (String optionName : optionNames) {
            String option = optionName.trim();
            if (option.isEmpty() || option.startsWith("-")) continue;

            if (option.equals(IGNORE_OPTION_NAME)) {
                //noinspection ConstantConditions
                throwIgnoredOption(example, optionSets, option);
            }

            switch (option) {
                case FAIL_OPTION_NAME:
                    if (options == null) {
                        options = new MutableDataSet().set(FAIL, true);
                    } else {
                        options = new MutableDataSet(options).set(FAIL, true);
                    }
                    break;
                case NO_FILE_EOL_OPTION_NAME:
                    if (options == null) {
                        options = new MutableDataSet().set(NO_FILE_EOL, true);
                    } else {
                        options = new MutableDataSet(options).set(NO_FILE_EOL, true);
                    }
                    break;
                default:
                    if (options == null) {
                        options = options(option);

                        if (options == null) {
                            throw new IllegalStateException("Option " + option + " is not implemented in the RenderingTestCase subclass");
                        }
                    } else {
                        DataHolder dataSet = options(option);

                        if (dataSet != null) {
                            if (isFirst) {
                                options = new MutableDataSet(options);
                                isFirst = false;
                            }
                            ((MutableDataSet) options).setAll(dataSet);
                        } else {
                            throw new IllegalStateException("Option " + option + " is not implemented in the RenderingTestCase subclass");
                        }
                    }

                    if (IGNORE.getFrom(options)) {
                        //noinspection ConstantConditions
                        throwIgnoredOption(example, optionSets, option);
                    }
                    break;
            }
        }
        return options;
    }

    private void throwIgnoredOption(SpecExample example, String optionSets, String option) {
        if (example == null)
            throw new AssumptionViolatedException("Ignored: SpecExample test case options(" + optionSets + ") is using " + option + " option");
        else
            throw new AssumptionViolatedException("Ignored: example(" + example.getSection() + ": " + example.getExampleNumber() + ") options(" + optionSets + ") is using " + option + " option");
    }

    protected String ast(Node node) {
        return new AstCollectingVisitor().collectAndGetAstText(node);
    }

    protected void actualHtml(String html, String optionSet) {

    }

    protected void actualAst(String ast, String optionSet) {

    }

    protected void specExample(String expected, String actual, String optionSet) {

    }

    protected void assertRendering(String source, String expectedHtml) {
        assertRendering(source, expectedHtml, null);
    }

    /**
     * @return return true if actual html should be used in comparison, else only actual AST will be used in compared
     */
    protected boolean useActualHtml() {
        return true;
    }

    protected void assertRendering(String source, String expectedHtml, String optionsSet) {
        DataHolder options = optionsSet == null ? null : getOptions(example(), optionsSet);
        String parseSource = source;

        if (options != null && options.get(NO_FILE_EOL)) {
            if (!parseSource.isEmpty() && parseSource.charAt(parseSource.length()-1) == '\n') {
                parseSource = parseSource.substring(0, parseSource.length()-1);
            }
        }

        Node node = parser().withOptions(options).parse(parseSource);
        String html = renderer().withOptions(options).render(node);
        actualHtml(html, optionsSet);
        boolean useActualHtml = useActualHtml();

        // include source for better assertion errors
        String expected;
        String actual;
        if (example() != null && example().getSection() != null) {
            StringBuilder outExpected = new StringBuilder();
            DumpSpecReader.addSpecExample(outExpected, source, expectedHtml, "", optionsSet, true, example().getSection(), example().getExampleNumber());
            expected = outExpected.toString();

            StringBuilder outActual = new StringBuilder();
            DumpSpecReader.addSpecExample(outActual, source, useActualHtml ? html : expectedHtml, "", optionsSet, true, example().getSection(), example().getExampleNumber());
            actual = outActual.toString();
        } else {
            expected = DumpSpecReader.addSpecExample(source, expectedHtml, "", optionsSet);
            actual = DumpSpecReader.addSpecExample(source, useActualHtml ? html : expectedHtml, "", optionsSet);
        }

        specExample(expected, actual, optionsSet);
        if (options != null && options.get(FAIL)) {
            thrown.expect(ComparisonFailure.class);
        }
        assertEquals(expected, actual);
    }

    //protected void assertRenderingAst(String source, String expectedHtml, String expectedAst) {
    //    assertRenderingAst(source, expectedHtml, expectedAst, null);
    //}

    protected void assertRenderingAst(String source, String expectedHtml, String expectedAst, String optionsSet) {
        //assert options != null || optionsSet == null || optionsSet.isEmpty() : "Non empty optionsSet without any option customizations";
        DataHolder options = optionsSet == null ? null : getOptions(example(), optionsSet);
        String parseSource = source;

        if (options != null && options.get(NO_FILE_EOL)) {
            if (!parseSource.isEmpty() && parseSource.charAt(parseSource.length()-1) == '\n') {
                parseSource = parseSource.substring(0, parseSource.length()-1);
            }
        }

        Node node = parser().withOptions(options).parse(parseSource);
        String html = renderer().withOptions(options).render(node);
        actualHtml(html, optionsSet);
        String ast = ast(node);
        actualAst(ast, optionsSet);
        boolean useActualHtml = useActualHtml();

        // include source for better assertion errors
        String expected;
        String actual;
        if (example() != null && example().getSection() != null) {
            StringBuilder outExpected = new StringBuilder();
            DumpSpecReader.addSpecExample(outExpected, source, expectedHtml, expectedAst, optionsSet, true, example().getSection(), example().getExampleNumber());
            expected = outExpected.toString();

            StringBuilder outActual = new StringBuilder();
            DumpSpecReader.addSpecExample(outActual, source, useActualHtml ? html : expectedHtml, ast, optionsSet, true, example().getSection(), example().getExampleNumber());
            actual = outActual.toString();
        } else {
            expected = DumpSpecReader.addSpecExample(source, expectedHtml, expectedAst, optionsSet);
            actual = DumpSpecReader.addSpecExample(source, useActualHtml ? html : expectedHtml, ast, optionsSet);
        }
        specExample(expected, actual, optionsSet);
        if (options != null && options.get(FAIL)) thrown.expect(ComparisonFailure.class);
        assertEquals(expected, actual);
    }

    //protected void assertAst(String source, String expectedAst) {
    //    assertAst(source, expectedAst, null);
    //}

    protected void assertAst(String source, String expectedAst, String optionsSet) {
        DataHolder options = optionsSet == null ? null : getOptions(example(), optionsSet);
        String parseSource = source;

        if (options != null && options.get(NO_FILE_EOL)) {
            if (!parseSource.isEmpty() && parseSource.charAt(parseSource.length()-1) == '\n') {
                parseSource = parseSource.substring(0, parseSource.length()-1);
            }
        }

        Node node = parser().withOptions(options).parse(parseSource);
        String ast = ast(node);
        actualAst(ast, optionsSet);

        String expected;
        String actual;
        if (example() != null && example().getSection() != null) {
            StringBuilder outExpected = new StringBuilder();
            DumpSpecReader.addSpecExample(outExpected, source, "", expectedAst, optionsSet, true, example().getSection(), example().getExampleNumber());
            expected = outExpected.toString();

            StringBuilder outActual = new StringBuilder();
            DumpSpecReader.addSpecExample(outActual, source, "", ast, optionsSet, true, example().getSection(), example().getExampleNumber());
            actual = outActual.toString();
        } else {
            expected = DumpSpecReader.addSpecExample(source, "", expectedAst, optionsSet);
            actual = DumpSpecReader.addSpecExample(source, "", ast, optionsSet);
        }
        specExample(expected, actual, optionsSet);
        if (options != null && options.get(FAIL)) thrown.expect(ComparisonFailure.class);
        assertEquals(expected, actual);
    }
}
