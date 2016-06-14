package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.internal.util.MutableDataSet;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FootnoteOptionsTest {
    private static Set<Extension> EXTENSIONS = Collections.singleton(FootnoteExtension.create());

    @Test
    public void prefixSuffix() {
        DataHolder options = new MutableDataSet()
                .set(FootnoteExtension.FOOTNOTE_REF_PREFIX, "[")
                .set(FootnoteExtension.FOOTNOTE_REF_SUFFIX, "]")
                .set(FootnoteExtension.FOOTNOTE_BACK_REF_STRING, "&lt;back&gt;")
                .set(HtmlRenderer.INDENT_SIZE, 2);
        HtmlRenderer RENDERER = HtmlRenderer.builder(options).extensions(EXTENSIONS).build();
        Parser PARSER = Parser.builder(options).extensions(EXTENSIONS).build();

        Node node = PARSER.parse("footnote[^abc]\n" +
                "\n" +
                "[^abc]: footnote text\n" +
                "\n");

        String html = RENDERER.render(node);
        String expected = "<p>footnote<sup id=\"fnref-1\"><a class=\"footnote-ref\" href=\"#fn-1\">[1]</a></sup></p>\n" +
                "<div class=\"footnotes\">\n" +
                "  <hr />\n" +
                "  <ol>\n" +
                "    <li id=\"fn-1\">\n" +
                "      <p>footnote text</p>\n" +
                "      <a href=\"#fnref-1\" class=\"footnote-backref\">&lt;back&gt;</a>\n" +
                "    </li>\n" +
                "  </ol>\n" +
                "</div>\n";

        assertEquals(expected, html);
    }
}
