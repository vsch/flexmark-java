package com.vladsch.flexmark.ext.jekyll.front.matter;

import com.vladsch.flexmark.ext.jekyll.front.matter.internal.JekyllFrontMatterBlockParser;
import com.vladsch.flexmark.ext.jekyll.front.matter.internal.JekyllFrontMatterNodeFormatter;
import com.vladsch.flexmark.ext.jekyll.front.matter.internal.JekyllFrontMatterNodeRenderer;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

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

    public static JekyllFrontMatterExtension create() {
        return new JekyllFrontMatterExtension();
    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new JekyllFrontMatterNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new JekyllFrontMatterBlockParser.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new JekyllFrontMatterNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
        }
    }
}
