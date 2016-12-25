package com.vladsch.flexmark.superscript;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.internal.SuperscriptDelimiterProcessor;
import com.vladsch.flexmark.superscript.internal.SuperscriptJiraRenderer;
import com.vladsch.flexmark.superscript.internal.SuperscriptNodeRenderer;
import com.vladsch.flexmark.util.options.DataHolder;

/**
 * Extension for superscripts
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed superscript text is turned into {@link Superscript} nodes.
 * </p>
 */
public class SuperscriptExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private SuperscriptExtension() {
    }

    public static Extension create() {
        return new SuperscriptExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new SuperscriptDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
            rendererBuilder.nodeRendererFactory(new NodeRendererFactory() { @Override public NodeRenderer create(DataHolder options) {return new SuperscriptJiraRenderer(options);} });
        } else if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new NodeRendererFactory() { @Override public NodeRenderer create(DataHolder options) {return new SuperscriptNodeRenderer(options);} });
        }
    }
}
