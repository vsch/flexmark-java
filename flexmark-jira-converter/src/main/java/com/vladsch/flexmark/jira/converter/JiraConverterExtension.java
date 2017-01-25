package com.vladsch.flexmark.jira.converter;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.jira.converter.internal.JiraConverterNodeRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for jira_converters
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The markdown AST is turned into JIRA formatted text
 * </p>
 */
public class JiraConverterExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    private JiraConverterExtension() {
    }

    public static Extension create() {
        return new JiraConverterExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {
        final String rendererType = HtmlRenderer.TYPE.getFrom(options);
        if (rendererType.equals("HTML")) {
            options.set(HtmlRenderer.TYPE, "JIRA");
        } else if (!rendererType.equals("JIRA")) {
            throw new IllegalStateException("Non HTML Renderer is already set to " + rendererType);
        }
    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        switch (rendererType) {
            case "JIRA":
                rendererBuilder.nodeRendererFactory(new JiraConverterNodeRenderer.Factory());
                break;
            default:
                throw new IllegalStateException("Jira Converter Extension used with non Jira Renderer " + rendererType);
        }
    }
}
