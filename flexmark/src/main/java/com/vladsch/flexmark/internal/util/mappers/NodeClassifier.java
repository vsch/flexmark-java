package com.vladsch.flexmark.internal.util.mappers;

import com.vladsch.flexmark.internal.util.collection.Computable;
import com.vladsch.flexmark.node.Node;

public class NodeClassifier implements Computable<Class<? extends Node>, Node> {
    final public static NodeClassifier INSTANCE = new NodeClassifier();

    private NodeClassifier() {
    }

    @Override
    public Class<? extends Node> compute(Node value) {
        return value.getClass();
    }
}
