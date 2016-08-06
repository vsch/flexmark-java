package com.vladsch.flexmark.html;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeAdaptingVisitor;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;

public interface CustomNodeRenderer<N extends Node> extends NodeAdaptingVisitor<N> {
    void render(N node, NodeRendererContext context, HtmlWriter html);
}
