package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.IRender;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.options.DataKey;
import com.vladsch.flexmark.internal.util.options.MutableDataSet;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.IParse;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import org.junit.AssumptionViolatedException;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public abstract class RenderingTestCase {

    public static final String IGNORE_OPTION = "IGNORE";
    public static final String FAIL_OPTION = "FAIL";
    public static DataKey<Boolean> FAIL = new DataKey<Boolean>("FAIL", false);

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
            if (option.isEmpty()) continue;
            if (option.equals(IGNORE_OPTION)) {
                if (example == null)
                    throw new AssumptionViolatedException("Ignored: SpecExample test case options(" + optionSets + ") is using IGNORE option");
                else
                    throw new AssumptionViolatedException("Ignored: example(" + example.getSection() + ": " + example.getExampleNumber() + ") options(" + optionSets + ") is using ignore option");
            }

            if (option.equals(FAIL_OPTION)) {
                if (options == null) {
                    options = new MutableDataSet().set(FAIL, true);
                } else {
                    options = new MutableDataSet(options).set(FAIL, true);
                }
            } else {
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
            }
        }
        return options;
    }

    protected String ast(Node node) {
        AstVisitor astVisitor = new AstVisitor();
        node.accept(astVisitor);
        return astVisitor.getAst();
    }

    protected void assertRendering(String source, String expectedHtml) {
        assertRendering(source, expectedHtml, null);
    }

    protected void assertRendering(String source, String expectedHtml, String optionsSet) {
        DataHolder options = optionsSet == null ? null : getOptions(example(), optionsSet);
        Node node = parser().withOptions(options).parse(source);
        String html = renderer().withOptions(options).render(node);

        // include source for better assertion errors
        String expected = SpecReader.EXAMPLE_START + "\n" + showTabs(source + "\n" + SpecReader.TYPE_BREAK + "\n" + expectedHtml) + SpecReader.EXAMPLE_BREAK + "\n\n";
        String actual = SpecReader.EXAMPLE_START + "\n" + showTabs(source + "\n" + SpecReader.TYPE_BREAK + "\n" + html) + SpecReader.EXAMPLE_BREAK + "\n\n";
        if (options != null && options.get(FAIL)) thrown.expect(ComparisonFailure.class);
        assertEquals(expected, actual);
    }

    //protected void assertRenderingAst(String source, String expectedHtml, String expectedAst) {
    //    assertRenderingAst(source, expectedHtml, expectedAst, null);
    //}

    protected void assertRenderingAst(String source, String expectedHtml, String expectedAst, String optionsSet) {
        DataHolder options = optionsSet == null ? null : getOptions(example(), optionsSet);
        //assert options != null || optionsSet == null || optionsSet.isEmpty() : "Non empty optionsSet without any option customizations";
        Node node = parser().withOptions(options).parse(source);
        String html = renderer().withOptions(options).render(node);
        String ast = ast(node);

        // include source for better assertion errors
        String expected = DumpSpecReader.addSpecExample(showTabs(source), showTabs(expectedHtml), expectedAst, optionsSet);
        String actual = DumpSpecReader.addSpecExample(showTabs(source), showTabs(html), ast, optionsSet);
        if (options != null && options.get(FAIL)) thrown.expect(ComparisonFailure.class);
        assertEquals(expected, actual);
    }

    //protected void assertAst(String source, String expectedAst) {
    //    assertAst(source, expectedAst, null);
    //}

    protected void assertAst(String source, String expectedAst, String optionsSet) {
        DataHolder options = optionsSet == null ? null : getOptions(example(), optionsSet);
        Node node = parser().withOptions(options).parse(source);
        String ast = ast(node);

        // include source for better assertion errors
        String expected = DumpSpecReader.addSpecExample(showTabs(source), "", expectedAst, optionsSet);
        String actual = DumpSpecReader.addSpecExample(showTabs(source), "", ast, optionsSet);
        if (options != null && options.get(FAIL)) thrown.expect(ComparisonFailure.class);
        assertEquals(expected, actual);
    }

    public static String showTabs(String s) {
        // Tabs are shown as "rightwards arrow" for easier comparison
        return s.replace("\t", "\u2192");
    }
}
