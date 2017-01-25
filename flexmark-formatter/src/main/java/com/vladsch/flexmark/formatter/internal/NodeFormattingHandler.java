package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeAdaptingVisitHandler;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;

public class NodeFormattingHandler<N extends Node> extends NodeAdaptingVisitHandler<N, CustomNodeFormatter<N>> implements CustomNodeFormatter<Node> {
    public NodeFormattingHandler(Class<? extends N> aClass, CustomNodeFormatter<N> adapter) {
        super(aClass, adapter);
    }

    @Override
    public void render(Node node, NodeFormatterContext context, MarkdownWriter markdown) {
        //noinspection unchecked
        myAdapter.render((N)node, context, markdown);
    }
}
