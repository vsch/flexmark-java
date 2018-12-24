package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeAdaptingVisitHandler;
import com.vladsch.flexmark.docx.converter.CustomNodeDocxRenderer;
import com.vladsch.flexmark.docx.converter.DocxRendererContext;

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
