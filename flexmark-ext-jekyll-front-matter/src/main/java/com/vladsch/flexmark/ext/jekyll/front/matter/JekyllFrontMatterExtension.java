package com.vladsch.flexmark.ext.jekyll.front.matter;

import com.vladsch.flexmark.ext.jekyll.front.matter.internal.JekyllFrontMatterBlockParser;
import com.vladsch.flexmark.ext.jekyll.front.matter.internal.JekyllFrontMatterNodeFormatter;
import com.vladsch.flexmark.ext.jekyll.front.matter.internal.JekyllFrontMatterNodeRenderer;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.MutableDataHolder;

/**
 * Extension for jekyll_front_matters
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed jekyll_front_matter text is turned into {@link JekyllFrontMatterBlock} nodes.
 */
public class JekyllFrontMatterExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension {
    private JekyllFrontMatterExtension() {
    }

    public static Extension create() {
        return new JekyllFrontMatterExtension();
    }

    @Override
    public void extend(final Formatter.Builder builder) {
        builder.nodeFormatterFactory(new JekyllFrontMatterNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new JekyllFrontMatterBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new JekyllFrontMatterNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
        }
    }
}
