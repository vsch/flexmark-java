package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MergeEnumeratedReferenceTest {
    private static DataHolder OPTIONS = new MutableDataSet()
            //.set(FormattingRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            //.set(Parser.EXTENSIONS, Collections.singleton(FormatterExtension.create()))
            .set(Parser.EXTENSIONS, Arrays.asList(EnumeratedReferenceExtension.create(), AttributesExtension.create()))
            .set(Parser.BLANK_LINES_IN_AST, true)
            .set(Parser.HTML_FOR_TRANSLATOR, true)
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

        String mergedOutput = FORMATTER.mergeRender(documents, 1, null);
        assertEquals("Merged results differ", expected, mergedOutput);
    }

    @Test
    public void test_IdAttributeConflict1() {
        assertMerged(
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig1:test}  \n" +
                        "[#fig1:test]\n" +
                        "\n" +
                        "[@fig1]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n");
    }

    @Test
    public void test_IdAttributeConflict2() {
        assertMerged(
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig1:test}  \n" +
                        "[#fig1:test]\n" +
                        "\n" +
                        "[@fig1]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n");
        assertMerged(
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig1:test}  \n" +
                        "[#fig1:test]\n" +
                        "\n" +
                        "[@fig1]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n");
    }

    @Test
    public void test_UndefinedIdConflict1() {
        assertMerged(
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig2:test}  \n" +
                        "[#fig2:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig1:test}  \n" +
                        "[#fig1:test]\n" +
                        "\n" +
                        "[@fig1]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig2:test}  \n" +
                        "[#fig2:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n");
    }

    @Test
    public void test_UndefinedIdConflict2() {
        assertMerged(
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig2:test}  \n" +
                        "[#fig2:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig1:test}  \n" +
                        "[#fig1:test]\n" +
                        "\n" +
                        "[@fig1]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig2:test}  \n" +
                        "[#fig2:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n");
        assertMerged(
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig2:test}  \n" +
                        "[#fig2:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig1:test}  \n" +
                        "[#fig1:test]\n" +
                        "\n" +
                        "[@fig1]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "![Fig](http://example.com/test.png){#fig2:test}  \n" +
                        "[#fig2:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n",
                "![Fig](http://example.com/test.png){#fig:test}  \n" +
                        "[#fig:test]\n" +
                        "\n" +
                        "[@fig]: Figure [#].\n" +
                        "\n");
    }
}
