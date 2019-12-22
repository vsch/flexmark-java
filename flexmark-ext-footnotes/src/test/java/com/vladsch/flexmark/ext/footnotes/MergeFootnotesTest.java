package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class MergeFootnotesTest {
    private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singletonList(FootnoteExtension.create()))
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
                "text [^footnote] embedded.\n" +
                        "\n" +
                        "[^footnote]: footnote text\n" +
                        "    with continuation\n" +
                        "\n" +
                        "text [^footnote1] embedded.\n" +
                        "\n" +
                        "[^footnote1]: footnote text\n" +
                        "    with continuation\n" +
                        "\n",
                "text [^footnote] embedded.\n" +
                        "\n" +
                        "[^footnote]: footnote text\n" +
                        "    with continuation\n" +
                        "\n",
                "text [^footnote] embedded.\n" +
                        "\n" +
                        "[^footnote]: footnote text\n" +
                        "    with continuation\n" +
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
                "text [^footnote] embedded.\n" +
                        "\n" +
                        "text [^footnote1] embedded.\n" +
                        "\n" +
                        "[^footnote]: footnote text\n" +
                        "    with continuation\n" +
                        "\n" +
                        "text [^footnote1] embedded.\n" +
                        "\n" +
                        "[^footnote1]: footnote text\n" +
                        "    with continuation\n" +
                        "\n",
                "text [^footnote] embedded.\n" +
                        "\n" +
                        "text [^footnote1] embedded.\n" +
                        "\n" +
                        "[^footnote]: footnote text\n" +
                        "    with continuation\n" +
                        "\n",
                "text [^footnote] embedded.\n" +
                        "\n" +
                        "[^footnote]: footnote text\n" +
                        "    with continuation\n" +
                        "\n");
    }

    @Test
    public void test_UndefinedIdConflict2() {
        testUndefinedIdConflict();
        testUndefinedIdConflict();
    }
}
