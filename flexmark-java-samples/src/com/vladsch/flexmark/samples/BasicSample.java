package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationFamily;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

public class BasicSample {
    void commonMark() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("This is *Sparta*");
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }

    void kramdown() {
        MutableDataHolder options = new MutableDataSet();
        options.setFrom(ParserEmulationFamily.KRAMDOWN.getOptions());

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse("This is *Sparta*");
        renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }

    void multiMarkdown() {
        MutableDataHolder options = new MutableDataSet();

        // configure for MultiMarkdown differences from family
        options.setFrom(ParserEmulationFamily.FIXED_INDENT.getOptions()
                .setAutoLoose(true)
                .setAutoLooseOneLevelLists(true)
                .setItemMarkerSpace(false)
                .setLooseWhenBlankFollowsItemParagraph(true)
                .setLooseWhenHasTrailingBlankLine(false))

                // Other compatibility options, outside of lists
                .set(HtmlRenderer.RENDER_HEADER_ID, true)
                .set(HtmlRenderer.HEADER_ID_GENERATOR_RESOLVE_DUPES, false)
                .set(HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS, "")
                .set(HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES, true)
                .set(HtmlRenderer.SOFT_BREAK, " ");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse("This is *Sparta*");
        renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }

    void markdown() {
        MutableDataHolder options = new MutableDataSet();

        // configure for MultiMarkdown differences from family
        options.setFrom(ParserEmulationFamily.MARKDOWN.getOptions());

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse("This is *Sparta*");
        renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }
}
