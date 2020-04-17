package com.vladsch.flexmark.ext.jekyll.tag;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class MergeJekyllTagTest {
    final static Map<String, String> content = new HashMap<>();
    static {
        content.put("test.md", "" +
                "## Embedded Content\n" +
                "\n" +
                "Content\n" +
                "\n" +
                "");
    }

    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(JekyllTagExtension.create()))
            .set(Parser.BLANK_LINES_IN_AST, true)
            .set(Parser.PARSE_INNER_HTML_COMMENTS, true)
            .set(Parser.HEADING_NO_ATX_SPACE, true)
            .set(Formatter.MAX_TRAILING_BLANK_LINES, 0)
            .set(JekyllTagExtension.INCLUDED_HTML, content)
            .set(JekyllTagExtension.EMBED_INCLUDED_CONTENT, true)
            ;

    private static final DataHolder NON_EMBEDDING_OPTIONS = new MutableDataSet(OPTIONS)
            .set(JekyllTagExtension.EMBED_INCLUDED_CONTENT, false);

    private static final Formatter EMBEDDING_FORMATTER = Formatter.builder(OPTIONS).build();
    private static final Formatter NON_EMBEDDING_FORMATTER = Formatter.builder(NON_EMBEDDING_OPTIONS).build();
    private static final Parser EMBEDDING_PARSER = Parser.builder(OPTIONS).build();
    private static final Parser NON_EMBEDDING_PARSER = Parser.builder(NON_EMBEDDING_OPTIONS).build();

    private static void assertMerged(boolean embedContent, String expected, String... markdownSources) {
        int iMax = markdownSources.length;
        Document[] documents = new Document[iMax];

        for (int i = 0; i < iMax; i++) {
            documents[i] = (embedContent ? EMBEDDING_PARSER : NON_EMBEDDING_PARSER).parse(markdownSources[i]);
        }

        String mergedOutput = (embedContent ? EMBEDDING_FORMATTER : NON_EMBEDDING_FORMATTER).mergeRender(documents, 1);
        assertEquals("Merged results differ", expected, mergedOutput);
    }

    @Test
    public void testIdAttributeConflict() {
        assertMerged(false
                , "" +
                        "{% include test.md %}\n" +
                        "\n" +
                        "{% include test.md %}\n" +
                        "\n" +
                        ""
                , "" +
                        "{% include test.md %}\n" +
                        "\n" +
                        ""
                , "" +
                        "{% include test.md %}\n" +
                        "\n" +
                        "");
    }

    @Test
    public void testIdAttributeConflictEmbed() {
        assertMerged(true
                , "" +
                        "## Embedded Content\n" +
                        "\n" +
                        "Content\n" +
                        "\n" +
                        "## Embedded Content\n" +
                        "\n" +
                        "Content\n" +
                        "\n" +
                        ""
                , "" +
                        "{% include test.md %}\n" +
                        "\n" +
                        ""
                , "" +
                        "{% include test.md %}\n" +
                        "\n" +
                        "");
    }
}
