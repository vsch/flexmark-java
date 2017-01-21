package com.vladsch.flexmark.html.parser.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.html.parser.FlexmarkHtmlParser;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FlexmarkHtmlParserNodeRenderer implements NodeRenderer
        // , PhasedNodeRenderer
{
    private static String fromChars = " +/<>";
    private static String toChars = "-----";

    // private final FlexmarkHtmlParserOptions options;

    public FlexmarkHtmlParserNodeRenderer(DataHolder options) {
        // this.options = new FlexmarkHtmlParserOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        // @formatter:off
        // set.add(new NodeRenderingHandler<>(FlexmarkHtmlParser.class, new CustomNodeRenderer<FlexmarkHtmlParser>() { @Override public void render(FlexmarkHtmlParser node, NodeRendererContext context, HtmlWriter html) { FlexmarkHtmlParserNodeRenderer.this.render(node, context, html); } }));
        // set.add(new NodeRenderingHandler<>(FlexmarkHtmlParserBlock.class, new CustomNodeRenderer<FlexmarkHtmlParserBlock>() { @Override public void render(FlexmarkHtmlParserBlock node, NodeRendererContext context, HtmlWriter html) { FlexmarkHtmlParserNodeRenderer.this.render(node, context, html); } }));// ,// zzzoptionszzz(CUSTOM_NODE)
        // @formatter:on
        return set;
    }
}
