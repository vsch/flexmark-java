package com.vladsch.flexmark.html;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeAdaptingVisitor;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;

public interface CustomNodeRenderer<N extends Node> extends NodeAdaptingVisitor<N> {
    void render(N node, NodeRendererContext context, HtmlWriter html);
}
