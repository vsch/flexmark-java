package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MergeFormatterCoreTest {
    private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.BLANK_LINES_IN_AST, true)
            .set(Parser.PARSE_INNER_HTML_COMMENTS, true)
            .set(Parser.HEADING_NO_ATX_SPACE, true)
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

    void testRefLinkConflict() {
        assertMerged(
                "[ref]\n" +
                        "\n" +
                        "[ref]: http://example.com\n" +
                        "\n" +
                        "[ref1]\n" +
                        "\n" +
                        "[ref1]: http://example.com\n" +
                        "\n",
                "[ref]\n" +
                        "\n" +
                        "[ref]: http://example.com\n" +
                        "\n",
                "[ref]\n" +
                        "\n" +
                        "[ref]: http://example.com\n" +
                        "\n");
    }

    @Test
    public void test_RefLinkConflict1() {
        testRefLinkConflict();
    }

    @Test
    public void test_RefLinkConflict2() {
        testRefLinkConflict();
        testRefLinkConflict();
    }

    void testRefLinkCompoundConflict() {
        assertMerged(
                "[*ref*]\n" +
                        "\n" +
                        "[*ref*]: http://example.com\n" +
                        "\n" +
                        "[*ref*1]\n" +
                        "\n" +
                        "[*ref*1]: http://example.com\n" +
                        "\n",
                "[*ref*]\n" +
                        "\n" +
                        "[*ref*]: http://example.com\n" +
                        "\n",
                "[*ref*]\n" +
                        "\n" +
                        "[*ref*]: http://example.com\n" +
                        "\n");
    }

    @Test
    public void test_RefLinkCompoundConflict1() {
        testRefLinkCompoundConflict();
    }

    @Test
    public void test_RefLinkCompoundConflict2() {
        testRefLinkCompoundConflict();
        testRefLinkCompoundConflict();
    }

    void testRefImageConflict() {
        assertMerged(
                "[img]\n" +
                        "\n" +
                        "[img]: http://example.com/image.png\n" +
                        "\n" +
                        "[img1]\n" +
                        "\n" +
                        "[img1]: http://example.com/image.png\n" +
                        "\n",
                "[img]\n" +
                        "\n" +
                        "[img]: http://example.com/image.png\n" +
                        "\n",
                "[img]\n" +
                        "\n" +
                        "[img]: http://example.com/image.png\n" +
                        "\n");
    }

    @Test
    public void test_RefImageConflict1() {
        testRefImageConflict();
    }

    @Test
    public void test_RefImageConflict2() {
        testRefImageConflict();
        testRefImageConflict();
    }

    void testRefImageCompoundConflict() {
        assertMerged(
                "[*img*]\n" +
                        "\n" +
                        "[*img*]: http://example.com/image.png\n" +
                        "\n" +
                        "[*img*1]\n" +
                        "\n" +
                        "[*img*1]: http://example.com/image.png\n" +
                        "\n",
                "[*img*]\n" +
                        "\n" +
                        "[*img*]: http://example.com/image.png\n" +
                        "\n",
                "[*img*]\n" +
                        "\n" +
                        "[*img*]: http://example.com/image.png\n" +
                        "\n");
    }

    @Test
    public void test_RefImageCompoundConflict1() {
        testRefImageCompoundConflict();
    }

    @Test
    public void test_RefImageCompoundConflict2() {
        testRefImageCompoundConflict();
        testRefImageCompoundConflict();
    }

    private void testUndefinedRefLinkConflict() {
        assertMerged(
                "[ref]\n" +
                        "\n" +
                        "[ref1]\n" +
                        "\n" +
                        "[ref]: http://example.com\n" +
                        "\n" +
                        "[ref1]\n" +
                        "\n" +
                        "[ref1]: http://example.com\n" +
                        "\n",
                "[ref]\n" +
                        "\n" +
                        "[ref1]\n" +
                        "\n" +
                        "[ref]: http://example.com\n" +
                        "\n",
                "[ref]\n" +
                        "\n" +
                        "[ref]: http://example.com\n" +
                        "\n");
    }

    @Test
    public void test_UndefinedRefLinkConflict1() {
        testUndefinedRefLinkConflict();
    }

    @Test
    public void test_UndefinedRefLinkConflict2() {
        testUndefinedRefLinkConflict();
        testUndefinedRefLinkConflict();
    }

    private void testUndefinedRefImageConflict() {
        assertMerged(
                "[img]\n" +
                        "\n" +
                        "[img1]\n" +
                        "\n" +
                        "[img]: http://example.com/image.png\n" +
                        "\n" +
                        "[img1]\n" +
                        "\n" +
                        "[img1]: http://example.com/image.png\n" +
                        "\n",
                "[img]\n" +
                        "\n" +
                        "[img1]\n" +
                        "\n" +
                        "[img]: http://example.com/image.png\n" +
                        "\n",
                "[img]\n" +
                        "\n" +
                        "[img]: http://example.com/image.png\n" +
                        "\n");
    }

    @Test
    public void test_UndefinedRefImageConflict1() {
        testUndefinedRefImageConflict();
    }

    @Test
    public void test_UndefinedRefImageConflict2() {
        testUndefinedRefImageConflict();
        testUndefinedRefImageConflict();
    }

    private void testReferenceMapping() {
        assertMerged(
                "[img]\n" +
                        "\n" +
                        "[img]: dir1/img/image.png\n" +
                        "\n" +
                        "[img1]\n" +
                        "\n" +
                        "[img1]: dir2/img/image.png\n" +
                        "\n",
                new String[] {
                        "dir1",
                        "dir2",
                },
                null, "[img]\n" +
                        "\n" +
                        "[img]: img/image.png\n" +
                        "\n",
                "[img]\n" +
                        "\n" +
                        "[img]: img/image.png\n" +
                        "\n");
    }

    @Test
    public void testReferenceMapping1() {
        testReferenceMapping();
    }

    @Test
    public void testReferenceMapping2() {
        testReferenceMapping();
        testReferenceMapping();
    }

    private void testLinkMapping() {
        assertMerged(
                "[img](dir1/img/image.png)\n" +
                        "\n" +
                        "[img](dir2/img/image.png)\n" +
                        "\n",
                new String[] {
                        "dir1",
                        "dir2",
                },
                null, "[img](img/image.png)\n" +
                        "\n",
                "[img](img/image.png)\n" +
                        "\n"
        );
    }

    @Test
    public void testLinkMapping1() {
        testLinkMapping();
    }

    @Test
    public void testLinkMapping2() {
        testLinkMapping();
        testLinkMapping();
    }

    private void testImageMapping() {
        assertMerged(
                "![img](dir1/img/image.png)\n" +
                        "\n" +
                        "![img](dir2/img/image.png)\n" +
                        "\n",
                new String[] {
                        "dir1",
                        "dir2",
                },
                null, "![img](img/image.png)\n" +
                        "\n",
                "![img](img/image.png)\n" +
                        "\n"
        );
    }

    @Test
    public void testImageMapping1() {
        testImageMapping();
    }

    @Test
    public void testImageMapping2() {
        testImageMapping();
        testImageMapping();
    }

    private void testRootReferenceMapping() {
        assertMerged(
                "[img]\n" +
                        "\n" +
                        "[img]: /dir1/img/image.png\n" +
                        "\n" +
                        "[img1]\n" +
                        "\n" +
                        "[img1]: /dir2/img/image.png\n" +
                        "\n",
                null,
                new String[] {
                        "dir1",
                        "dir2",
                },
                "[img]\n" +
                        "\n" +
                        "[img]: /img/image.png\n" +
                        "\n",
                "[img]\n" +
                        "\n" +
                        "[img]: /img/image.png\n" +
                        "\n");
    }

    @Test
    public void testRootReferenceMapping1() {
        testRootReferenceMapping();
    }

    @Test
    public void testRootReferenceMapping2() {
        testRootReferenceMapping();
        testRootReferenceMapping();
    }

    private void testRootLinkMapping() {
        assertMerged(
                "[img](/dir1/img/image.png)\n" +
                        "\n" +
                        "[img](/dir2/img/image.png)\n" +
                        "\n",
                null,
                new String[] {
                        "dir1",
                        "dir2",
                },
                "[img](/img/image.png)\n" +
                        "\n",
                "[img](/img/image.png)\n" +
                        "\n"
        );
    }

    @Test
    public void testRootLinkMapping1() {
        testRootLinkMapping();
    }

    @Test
    public void testRootLinkMapping2() {
        testRootLinkMapping();
        testRootLinkMapping();
    }

    private void testRootImageMapping() {
        assertMerged(
                "![img](/dir1/img/image.png)\n" +
                        "\n" +
                        "![img](/dir2/img/image.png)\n" +
                        "\n",
                null,
                new String[] {
                        "dir1",
                        "dir2",
                },
                "![img](/img/image.png)\n" +
                        "\n",
                "![img](/img/image.png)\n" +
                        "\n"
        );
    }

    @Test
    public void testRootImageMapping1() {
        testRootImageMapping();
    }

    @Test
    public void testRootImageMapping2() {
        testRootImageMapping();
        testRootImageMapping();
    }

    private void testHtmlPreservation() {
        assertMerged(
                "# Heading {style=\"font-size: 26pt\"}\n" +
                        "\n" +
                        "\\<CUSTOMER_ADDRESS\\>\n" +
                        "{.addresse}\n" +
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

    private void testHtmlPreservationAutoLink() {
        assertMerged(
                "<http://example.com>\n" +
                        "\n" +
                        "<br />\n" +
                        "\n" +
                        "<br />\n" +
                        "\n" +
                        "<http://example.com>\n" +
                        "\n",
                "<http://example.com>\n" +
                        "\n" +
                        "<br />\n" +
                        "\n",
                "<br />\n" +
                        "\n" +
                        "<http://example.com>\n" +
                        "\n"
        );
    }

    @Test
    public void testHtmlPreservationAutoLink1() {
        testHtmlPreservationAutoLink();
    }

    @Test
    public void testHtmlPreservationAutoLink2() {
        testHtmlPreservationAutoLink();
        testHtmlPreservationAutoLink();
    }
}
