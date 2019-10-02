package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.visitor.AstAction;
import com.vladsch.flexmark.util.visitor.AstHandler;

public class NodeDocxRendererHandler<N extends Node> extends AstHandler<N, NodeDocxRendererHandler.CustomNodeDocxRenderer<N>> {
    public NodeDocxRendererHandler(Class<N> aClass, CustomNodeDocxRenderer<N> adapter) {
        super(aClass, adapter);
    }

    public void render(Node node, DocxRendererContext context) {
        //noinspection unchecked
        getAdapter().render((N) node, context);
    }

    public static interface CustomNodeDocxRenderer<N extends Node> extends AstAction<N> {
        void render(N node, DocxRendererContext context);
    }
}
