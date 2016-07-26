package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.internal.util.ast.NodeAdaptingVisitHandler;
import com.vladsch.flexmark.node.Node;

public class NodeRenderingHandler<N extends Node> extends NodeAdaptingVisitHandler<N, CustomNodeRenderer<N>> implements CustomNodeRenderer<Node> {
    public NodeRenderingHandler(Class<? extends N> aClass, CustomNodeRenderer<N> adapter) {
        super(aClass, adapter);
    }

    @Override
    public void render(Node node, NodeRendererContext context, HtmlWriter html) {
        //noinspection unchecked
        myAdapter.render((N)node, context, html);
    }
}
