package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Arrays;

public class ProfileEmulationFamilySamples {
    void commonMark() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("This is *Sparta*");
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }

    void kramdown() {
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.KRAMDOWN);
        options.set(Parser.EXTENSIONS, Arrays.asList(
                AbbreviationExtension.create(),
                DefinitionExtension.create(),
                FootnoteExtension.create(),
                TablesExtension.create(),
                TypographicExtension.create()
        ));

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse("This is *Sparta*");
        renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }

    void gitHub() {
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.GITHUB_DOC);
        options.set(Parser.EXTENSIONS, Arrays.asList(
                AutolinkExtension.create(),
                //AnchorLinkExtension.create(),
                //EmojiExtension.create(),
                StrikethroughExtension.create(),
                TablesExtension.create(),
                TaskListExtension.create()
        ));

        // uncomment and define location of emoji images from https://github.com/arvida/emoji-cheat-sheet.com
        // options.set(EmojiExtension.ROOT_IMAGE_PATH, "");

        // Uncomment if GFM anchor links are desired in headings
        // options.set(AnchorLinkExtension.ANCHORLINKS_SET_ID, false);
        // options.set(AnchorLinkExtension.ANCHORLINKS_ANCHOR_CLASS, "anchor");
        // options.set(AnchorLinkExtension.ANCHORLINKS_SET_NAME, true);
        // options.set(AnchorLinkExtension.ANCHORLINKS_TEXT_PREFIX, "<span class=\"octicon octicon-link\"></span>");

        // References compatibility
        options.set(Parser.REFERENCES_KEEP, KeepType.LAST);

        // Set GFM table parsing options
        options.set(TablesExtension.COLUMN_SPANS, false)
                .set(TablesExtension.MIN_HEADER_ROWS, 1)
                .set(TablesExtension.MAX_HEADER_ROWS, 1)
                .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                .set(TablesExtension.WITH_CAPTION, false)
                .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true);

        // Setup List Options for GitHub profile which is kramdown for documents
        options.setFrom(ParserEmulationProfile.GITHUB_DOC);

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse("This is *Sparta*");
        renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }

    void multiMarkdown() {
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MULTI_MARKDOWN);

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse("This is *Sparta*");
        renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }

    void markdown() {
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse("This is *Sparta*");
        renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }
}
