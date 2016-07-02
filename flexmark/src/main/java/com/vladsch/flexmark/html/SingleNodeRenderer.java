package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Node;

public interface SingleNodeRenderer<N extends Node> {
    void render(N node, NodeRendererContext context, HtmlWriter html);
}
