package com.vladsch.flexmark.ext.autolink;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class MergeAutoLinkTest {
    private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.BLANK_LINES_IN_AST, true)
            .set(Parser.PARSE_INNER_HTML_COMMENTS, true)
            .set(Parser.HEADING_NO_ATX_SPACE, true)
            .set(Parser.EXTENSIONS, Collections.singletonList(AutolinkExtension.create()))
            .set(Formatter.DEFAULT_LINK_RESOLVER, true)
            .set(Formatter.MAX_TRAILING_BLANK_LINES, 0);

    private static Formatter FORMATTER = Formatter.builder(OPTIONS).build();
    private static Parser PARSER = Parser.builder(OPTIONS).build();

    private static void assertMerged(String expected, String... markdownSources) {
        assertMerged(expected, null, null, markdownSources);
    }

    private static void assertMerged(String expected, String[] documentUrls, String[] documentRootUrls, String... markdownSources) {
        int iMax = markdownSources.length;
        Document[] documents = new Document[iMax];

        for (int i = 0; i < iMax; i++) {
            documents[i] = PARSER.parse(markdownSources[i]);

            if (documentUrls != null && i < documentUrls.length && documentUrls[i] != null) {
                documents[i].set(Formatter.DOC_RELATIVE_URL, documentUrls[i]);
            }

            if (documentRootUrls != null && i < documentRootUrls.length && documentRootUrls[i] != null) {
                documents[i].set(Formatter.DOC_ROOT_URL, documentRootUrls[i]);
            }
        }

        String mergedOutput = FORMATTER.mergeRender(documents, 1);
        assertEquals("Merged results differ", expected, mergedOutput);
    }

    void testMailLink() {
        assertMerged(
                "test@example.com\n" +
                        "\n" +
                        "test@example.com\n" +
                        "\n",
                "test@example.com\n" +
                        "\n",
                "test@example.com\n" +
                        "\n");
    }

    @Test
    public void test_Mail_Link1() {
        testMailLink();
    }

    @Test
    public void test_Mail_Link2() {
        testMailLink();
        testMailLink();
    }

    void testMailLink1() {
        assertMerged(
                "test@example.com\n" +
                        "\n" +
                        "<test@example.com>\n" +
                        "\n",
                "test@example.com\n" +
                        "\n",
                "<test@example.com>\n" +
                        "\n");
    }

    @Test
    public void test_Mail_Link11() {
        testMailLink1();
    }

    @Test
    public void test_Mail_Link12() {
        testMailLink1();
        testMailLink1();
    }

    void testMailLink2() {
        assertMerged(
                "<test@example.com>\n" +
                        "\n" +
                        "test@example.com\n" +
                        "\n",
                "<test@example.com>\n" +
                        "\n",
                "test@example.com\n" +
                        "\n");
    }

    @Test
    public void test_Mail_Link21() {
        testMailLink2();
    }

    @Test
    public void test_Mail_Link22() {
        testMailLink2();
        testMailLink2();
    }

    void testMailLink3() {
        assertMerged(
                "<test@example.com>\n" +
                        "\n" +
                        "<test@example.com>\n" +
                        "\n",
                "<test@example.com>\n" +
                        "\n",
                "<test@example.com>\n" +
                        "\n");
    }

    @Test
    public void test_Mail_Link31() {
        testMailLink3();
    }

    @Test
    public void test_Mail_Link32() {
        testMailLink3();
        testMailLink3();
    }

    private void testHtmlPreservationAutoLink0() {
        assertMerged(
                "http://example.com\n" +
                        "\n" +
                        "http://example.com\n" +
                        "\n",
                "http://example.com\n" +
                        "\n",
                "http://example.com\n" +
                        "\n"
        );
    }

    @Test
    public void test_HtmlPreservationAutoLink01() {
        testHtmlPreservationAutoLink0();
    }

    @Test
    public void test_HtmlPreservationAutoLink02() {
        testHtmlPreservationAutoLink0();
        testHtmlPreservationAutoLink0();
    }

    private void testHtmlPreservationAutoLink1() {
        assertMerged(
                "<http://example.com>\n" +
                        "\n" +
                        "http://example.com\n" +
                        "\n",
                "<http://example.com>\n" +
                        "\n",
                "http://example.com\n" +
                        "\n"
        );
    }

    @Test
    public void test_HtmlPreservationAutoLink11() {
        testHtmlPreservationAutoLink1();
    }

    @Test
    public void test_HtmlPreservationAutoLink12() {
        testHtmlPreservationAutoLink1();
        testHtmlPreservationAutoLink1();
    }

    private void testHtmlPreservationAutoLink2() {
        assertMerged(
                "http://example.com\n" +
                        "\n" +
                        "<http://example.com>\n" +
                        "\n",
                "http://example.com\n" +
                        "\n",
                "<http://example.com>\n" +
                        "\n"
        );
    }

    @Test
    public void test_HtmlPreservationAutoLink21() {
        testHtmlPreservationAutoLink2();
    }

    @Test
    public void test_HtmlPreservationAutoLink22() {
        testHtmlPreservationAutoLink2();
        testHtmlPreservationAutoLink2();
    }

    private void testHtmlPreservationAutoLink3() {
        assertMerged(
                "<http://example.com>\n" +
                        "\n" +
                        "<http://example.com>\n" +
                        "\n",
                "<http://example.com>\n" +
                        "\n",
                "<http://example.com>\n" +
                        "\n"
        );
    }

    @Test
    public void test_HtmlPreservationAutoLink31() {
        testHtmlPreservationAutoLink3();
    }

    @Test
    public void test_HtmlPreservationAutoLink32() {
        testHtmlPreservationAutoLink3();
        testHtmlPreservationAutoLink3();
    }
}
