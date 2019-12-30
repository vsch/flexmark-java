package com.vladsch.flexmark.jira.converter;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.jira.converter.internal.JiraConverterNodeRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for jira_converters
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The markdown AST is turned into JIRA formatted text
 * </p>
 */
public class JiraConverterExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    private JiraConverterExtension() {
    }

    public static JiraConverterExtension create() {
        return new JiraConverterExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {

    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {
        String rendererType = HtmlRenderer.TYPE.get(options);
        if (rendererType.equals("HTML")) {
            options.set(HtmlRenderer.TYPE, "JIRA");
        } else if (!rendererType.equals("JIRA")) {
            throw new IllegalStateException("Non HTML Renderer is already set to " + rendererType);
        }
    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("JIRA")) {
            htmlRendererBuilder.nodeRendererFactory(new JiraConverterNodeRenderer.Factory());
        } else {
            throw new IllegalStateException("Jira Converter Extension used with non Jira Renderer " + rendererType);
        }
    }
}
