package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ext.spec.example.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.internal.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SpecExampleNodeRenderer implements NodeRenderer
        // , PhasedNodeRenderer 
{
    private static String fromChars = " +/<>";
    private static String toChars = "-----";

    private final SpecExampleOptions options;

    public SpecExampleNodeRenderer(DataHolder options) {
        this.options = new SpecExampleOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(SpecExampleBlock.class, this::render),
                new NodeRenderingHandler<>(SpecExampleOptionsList.class, this::render),
                new NodeRenderingHandler<>(SpecExampleOption.class, this::render),
                new NodeRenderingHandler<>(SpecExampleSeparator.class, this::render),
                new NodeRenderingHandler<>(SpecExampleSource.class, this::render),
                new NodeRenderingHandler<>(SpecExampleHtml.class, this::render),
                new NodeRenderingHandler<>(SpecExampleAst.class, this::render)
        ));
    }

    private void render(SpecExampleOptionsList node, NodeRendererContext context, HtmlWriter html) { }

    private void render(SpecExampleOption node, NodeRendererContext context, HtmlWriter html) { }

    private void render(SpecExampleSeparator node, NodeRendererContext context, HtmlWriter html) {
        html.tagVoid("hr");
    }

    private void render(SpecExampleSource node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getChars().normalizeEOL();
        render(text, "language-markdown", context, html);
    }

    private void render(SpecExampleHtml node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getChars().normalizeEOL();
        render(text, "language-html", context, html);
        if (options.renderHtml) {
            html.tagVoid("hr");
            html.raw(text);
        }
    }

    private void render(SpecExampleAst node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getChars().normalizeEOL();
        render(text, "language-text", context, html);
    }

    private void render(SpecExampleBlock node, NodeRendererContext context, HtmlWriter html) {
        // here we should probably prettify and display section, number and options
        context.renderChildren(node);
    }

    private void render(String text, String languageClass, NodeRendererContext context, HtmlWriter html) {
        if (!languageClass.trim().isEmpty()) {
            html.attr("class", languageClass);
        }

        html.line();
        html.tag("pre");
        html.withAttr().tag("code");
        html.text(text);
        html.tag("/code");
        html.tag("/pre");
        html.line();
    }
}
