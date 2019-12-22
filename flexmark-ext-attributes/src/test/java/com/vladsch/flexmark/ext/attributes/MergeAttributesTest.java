package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class MergeAttributesTest {
    private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singletonList(AttributesExtension.create()))
            .set(Parser.BLANK_LINES_IN_AST, true)
            .set(Parser.PARSE_INNER_HTML_COMMENTS, true)
            .set(Parser.HEADING_NO_ATX_SPACE, true)
            .set(Formatter.MAX_TRAILING_BLANK_LINES, 0);

    private static Formatter FORMATTER = Formatter.builder(OPTIONS).build();
    private static Parser PARSER = Parser.builder(OPTIONS).build();

    private static void assertMerged(String expected, String... markdownSources) {
        int iMax = markdownSources.length;
        Document[] documents = new Document[iMax];

        for (int i = 0; i < iMax; i++) {
            documents[i] = PARSER.parse(markdownSources[i]);
        }

        String mergedOutput = FORMATTER.mergeRender(documents, 1);
        assertEquals("Merged results differ", expected, mergedOutput);
    }

    @Test
    public void test_IdAttributeConflict1() {
        testIdAttributeConflict();
    }

    private void testIdAttributeConflict() {
        assertMerged(
                "![Fig](http://example.com/test.png){#fig:test}\n" +
                        "\n" +
                        "[Figure](#fig:test).\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig:test1}\n" +
                        "\n" +
                        "[Figure](#fig:test1).\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}\n" +
                        "\n" +
                        "[Figure](#fig:test).\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "\n" +
                        "[Figure](#fig:test).\n" +
                        "\n");
    }

    @Test
    public void test_IdAttributeConflict2() {
        testIdAttributeConflict();
        testIdAttributeConflict();
    }

    @Test
    public void test_UndefinedIdConflict1() {
        testUndefinedIdConflict();
    }

    private void testUndefinedIdConflict() {
        assertMerged(
                "![Fig](http://example.com/test.png){#fig:test}\n" +
                        "\n" +
                        "[Figure](#fig:test).\n" +
                        "\n" +
                        "[Figure](#fig:test1).\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig:test1}\n" +
                        "\n" +
                        "[Figure](#fig:test1).\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "\n" +
                        "[Figure](#fig:test).\n" +
                        "\n" +
                        "[Figure](#fig:test1).\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "\n" +
                        "[Figure](#fig:test).\n" +
                        "\n");
    }

    @Test
    public void test_UndefinedIdConflict2() {
        testUndefinedIdConflict();
        testUndefinedIdConflict();
    }

    // Header attribute id adjustment
    void testAtxHeadingConflict() {
        assertMerged(
                "# Atx Heading\n" +
                        "\n" +
                        "[link](#atx-heading)\n" +
                        "\n" +
                        "# Atx Heading {.atx-heading1}\n" +
                        "\n" +
                        "[link](#atx-heading1)\n" +
                        "\n",
                "# Atx Heading\n" +
                        "[link](#atx-heading)\n" +
                        "\n",
                "# Atx Heading\n" +
                        "[link](#atx-heading)\n" +
                        "\n");
    }

    @Test
    public void test_AtxHeadingConflict1() {
        testAtxHeadingConflict();
    }

    @Test
    public void test_AtxHeadingConflict2() {
        testAtxHeadingConflict();
        testAtxHeadingConflict();
    }

    void testSetextHeadingConflict() {
        assertMerged(
                "Setext Heading\n" +
                        "==============\n" +
                        "\n" +
                        "[link](#setext-heading)\n" +
                        "\n" +
                        "Setext Heading {.setext-heading1}\n" +
                        "=================================\n" +
                        "\n" +
                        "[link](#setext-heading1)\n" +
                        "\n",
                "Setext Heading\n" +
                        "=======\n" +
                        "[link](#setext-heading)\n" +
                        "\n",
                "Setext Heading\n" +
                        "=======\n" +
                        "[link](#setext-heading)\n" +
                        "\n");
    }

    @Test
    public void test_SetextHeadingConflict1() {
        testSetextHeadingConflict();
    }

    @Test
    public void test_SetextHeadingConflict2() {
        testSetextHeadingConflict();
        testSetextHeadingConflict();
    }

    // Header attribute id adjustment
    void testAtxHeadingExplicitConflict() {
        assertMerged(
                "# Atx Heading {#atx-explicit}\n" +
                        "\n" +
                        "[link](#atx-explicit)\n" +
                        "\n" +
                        "# Atx Heading {#atx-explicit1}\n" +
                        "\n" +
                        "[link](#atx-explicit1)\n" +
                        "\n",
                "# Atx Heading {#atx-explicit}\n" +
                        "[link](#atx-explicit)\n" +
                        "\n",
                "# Atx Heading {#atx-explicit}\n" +
                        "[link](#atx-explicit)\n" +
                        "\n");
    }

    @Test
    public void test_AtxHeadingExplicitConflict1() {
        testAtxHeadingExplicitConflict();
    }

    @Test
    public void test_AtxHeadingExplicitConflict2() {
        testAtxHeadingExplicitConflict();
        testAtxHeadingExplicitConflict();
    }

    void testSetextHeadingExplicitConflict() {
        assertMerged(
                "Setext Heading\n" +
                        "==============\n" +
                        "\n" +
                        "[link](#setext-heading)\n" +
                        "\n" +
                        "Setext Heading {.setext-heading1}\n" +
                        "=================================\n" +
                        "\n" +
                        "[link](#setext-heading1)\n" +
                        "\n",
                "Setext Heading\n" +
                        "=======\n" +
                        "[link](#setext-heading)\n" +
                        "\n",
                "Setext Heading\n" +
                        "=======\n" +
                        "[link](#setext-heading)\n" +
                        "\n");
    }

    @Test
    public void test_SetextHeadingExplicitConflict1() {
        testSetextHeadingExplicitConflict();
    }

    @Test
    public void test_SetextHeadingExplicitConflict2() {
        testSetextHeadingExplicitConflict();
        testSetextHeadingExplicitConflict();
    }

    private void testHtmlPreservation() {
        assertMerged(
                "# Heading {style=\"font-size: 26pt\"}\n" +
                        "\n" +
                        "\\<CUSTOMER_ADDRESS\\> {.addresse}\n" +
                        "\n" +
                        "<br />\n" +
                        "\n" +
                        "<br />\n" +
                        "\n" +
                        "[](http://example.com)\n" +
                        "\n",
                "# Heading {style=\"font-size: 26pt\"}\n" +
                        "\n" +
                        "\\<CUSTOMER_ADDRESS\\>\n" +
                        "{.addresse}\n" +
                        "\n" +
                        "<br />\n" +
                        "\n",
                "<br />\n" +
                        "\n" +
                        "[](http://example.com)\n" +
                        "\n"
        );
    }

    @Test
    public void testHtmlPreservation1() {
        testHtmlPreservation();
    }

    @Test
    public void testHtmlPreservation2() {
        testHtmlPreservation();
        testHtmlPreservation();
    }

    private void testHtmlPreservationLink() {
        assertMerged(
                "[](http://example.com)\n" +
                        "\n" +
                        "<br />\n" +
                        "\n" +
                        "<br />\n" +
                        "\n" +
                        "[](http://example.com)\n" +
                        "\n",
                "[](http://example.com)\n" +
                        "\n" +
                        "<br />\n" +
                        "\n",
                "<br />\n" +
                        "\n" +
                        "[](http://example.com)\n" +
                        "\n"
        );
    }

    @Test
    public void testHtmlPreservationLink1() {
        testHtmlPreservationLink();
    }

    @Test
    public void testHtmlPreservationLink2() {
        testHtmlPreservationLink();
        testHtmlPreservationLink();
    }
}
