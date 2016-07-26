package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.util.ast.NodeAdaptingVisitor;
import com.vladsch.flexmark.node.Node;

public interface CustomNodeRenderer<N extends Node> extends NodeAdaptingVisitor<N> {
    void render(N node, NodeRendererContext context, HtmlWriter html);
}
