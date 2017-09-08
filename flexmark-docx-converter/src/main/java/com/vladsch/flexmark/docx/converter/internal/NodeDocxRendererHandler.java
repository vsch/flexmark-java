package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeAdaptingVisitHandler;
import com.vladsch.flexmark.docx.converter.CustomNodeDocxRenderer;

public class NodeDocxRendererHandler<N extends Node> extends NodeAdaptingVisitHandler<N, CustomNodeDocxRenderer<N>> implements CustomNodeDocxRenderer<Node> {
    public NodeDocxRendererHandler(Class<? extends N> aClass, CustomNodeDocxRenderer<N> adapter) {
        super(aClass, adapter);
    }

    @Override
    public void render(Node node, DocxRendererContext context) {
        //noinspection unchecked
        myAdapter.render((N)node, context);
    }
}
