package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecReader;

import static org.junit.Assert.assertEquals;

public abstract class RenderingTestCase {
    protected abstract Parser parser();
    protected abstract HtmlRenderer renderer();

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

    protected String ast(Node node) {
        AstVisitor astVisitor = new AstVisitor();
        node.accept(astVisitor);
        return astVisitor.getAst();
    }

    protected void assertRendering(String source, String expectedHtml) {
        assertRendering(source, expectedHtml, null);
    }

    protected void assertRendering(String source, String expectedHtml, String optionsSet) {
        DataHolder options = optionsSet == null ? null : options(optionsSet);
        Node node = parser().withOptions(options).parse(source);
        String html = renderer().withOptions(options).render(node);

        // include source for better assertion errors
        String expected = SpecReader.EXAMPLE_START + "\n" + showTabs(source + "\n" + SpecReader.TYPE_BREAK + "\n" + expectedHtml) + SpecReader.EXAMPLE_BREAK + "\n\n";
        String actual = SpecReader.EXAMPLE_START + "\n" + showTabs(source + "\n" + SpecReader.TYPE_BREAK + "\n" + html) + SpecReader.EXAMPLE_BREAK + "\n\n";
        assertEquals(expected, actual);
    }

    //protected void assertRenderingAst(String source, String expectedHtml, String expectedAst) {
    //    assertRenderingAst(source, expectedHtml, expectedAst, null);
    //}

    protected void assertRenderingAst(String source, String expectedHtml, String expectedAst, String optionsSet) {
        DataHolder options = optionsSet == null ? null : options(optionsSet);
        //assert options != null || optionsSet == null || optionsSet.isEmpty() : "Non empty optionsSet without any option customizations";
        Node node = parser().withOptions(options).parse(source);
        String html = renderer().withOptions(options).render(node);
        String ast = ast(node);

        // include source for better assertion errors
        String expected = DumpSpecReader.addSpecExample(showTabs(source), showTabs(expectedHtml), expectedAst, optionsSet);
        String actual = DumpSpecReader.addSpecExample(showTabs(source), showTabs(html), ast, optionsSet);
        assertEquals(expected, actual);
    }

    //protected void assertAst(String source, String expectedAst) {
    //    assertAst(source, expectedAst, null);
    //}

    protected void assertAst(String source, String expectedAst, String optionsSet) {
        DataHolder options = optionsSet == null ? null : options(optionsSet);
        Node node = parser().withOptions(options).parse(source);
        String ast = ast(node);

        // include source for better assertion errors
        String expected = DumpSpecReader.addSpecExample(showTabs(source), "", expectedAst, optionsSet);
        String actual = DumpSpecReader.addSpecExample(showTabs(source), "", ast, optionsSet);
        assertEquals(expected, actual);
    }

    public static String showTabs(String s) {
        // Tabs are shown as "rightwards arrow" for easier comparison
        return s.replace("\t", "\u2192");
    }
}
