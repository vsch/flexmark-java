package com.vladsch.flexmark.test;

import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.spec.SpecReader;

import static org.junit.Assert.assertEquals;

public abstract class RenderingTestCase {
    protected abstract Node parse(String source);
    protected abstract String render(Node node);

    protected String ast(Node node) {
        AstVisitor astVisitor = new AstVisitor();
        node.accept(astVisitor);
        return astVisitor.getAst();
    }

    protected void assertRendering(String source, String expectedHtml) {
        Node node = parse(source);
        String html = render(node);

        // include source for better assertion errors
        String expected = SpecReader.EXAMPLE_START + "\n" + showTabs(source + "\n" + SpecReader.TYPE_BREAK + "\n" + expectedHtml) + SpecReader.EXAMPLE_BREAK + "\n\n";
        String actual = SpecReader.EXAMPLE_START + "\n" + showTabs(source + "\n" + SpecReader.TYPE_BREAK + "\n" + html) + SpecReader.EXAMPLE_BREAK + "\n\n";
        assertEquals(expected, actual);
    }

    protected void assertRenderingAst(String source, String expectedHtml, String expectedAst) {
        Node node = parse(source);
        String html = render(node);
        String ast = ast(node);

        // include source for better assertion errors
        String expected = SpecReader.EXAMPLE_START + "\n" + showTabs(source + SpecReader.TYPE_BREAK + "\n" + expectedHtml) + SpecReader.TYPE_BREAK + expectedAst + "\n" + SpecReader.EXAMPLE_BREAK + "\n\n";
        String actual = SpecReader.EXAMPLE_START + "\n" + showTabs(source + SpecReader.TYPE_BREAK + "\n" + html) + SpecReader.TYPE_BREAK + "\n" + ast + SpecReader.EXAMPLE_BREAK + "\n\n";
        assertEquals(expected, actual);
    }

    protected void assertAst(String source, String expectedAst) {
        Node node = parse(source);
        String ast = ast(node);

        // include source for better assertion errors
        String expected = SpecReader.EXAMPLE_START + "\n" + showTabs(source + SpecReader.TYPE_BREAK + "\n") + "\n" + SpecReader.TYPE_BREAK + expectedAst + SpecReader.EXAMPLE_BREAK + "\n\n";
        String actual = SpecReader.EXAMPLE_START + "\n" + showTabs(source + SpecReader.TYPE_BREAK + "\n") + "\n" + SpecReader.TYPE_BREAK + ast + SpecReader.EXAMPLE_BREAK + "\n\n";
        assertEquals(expected, actual);
    }

    private static String showTabs(String s) {
        // Tabs are shown as "rightwards arrow" for easier comparison
        return s.replace("\t", "\u2192");
    }
}
