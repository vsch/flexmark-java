package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.ArrayList;
import java.util.Arrays;

public class PegdownOptions2 {
    static boolean headerLinks = false;
    static boolean hardwrap = false;
    static boolean allowHtml = false;
    static String processed;
    static String data = "";

    static final MutableDataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
            Extensions.ALL - (headerLinks ? 0 : Extensions.ANCHORLINKS)
                    - (hardwrap ? 0 : Extensions.HARDWRAPS) + (allowHtml ? 0 : Extensions.SUPPRESS_ALL_HTML)
            // add your extra extensions here
            //, new ConfluenceWikiLinkExtension()
    ).toMutable()
            // set additional options here:
            //.set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX,"")
            ;

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();
    // use the PARSER to parse and RENDERER to render with pegdown compatibility
    static {
        Node document = PARSER.parse(data);
        processed = RENDERER.render(document);
    }

    public static void main(String[] args) {
        final MutableDataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
                Extensions.ALL - (headerLinks ? 0 : Extensions.ANCHORLINKS)
                        - (hardwrap ? 0 : Extensions.HARDWRAPS) + (allowHtml ? 0 : Extensions.SUPPRESS_ALL_HTML)
                // add your extra extensions here
                //, new ConfluenceWikiLinkExtension()
        ).toMutable()
                // set additional options here:
                //.set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX,"")
                ;

        final Parser PARSER = Parser.builder(OPTIONS).build();
        final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

        String input = "[[test page]]" +
                "";

        Node node = PARSER.parse(input);
        System.out.println(RENDERER.render(node));
    }
}
