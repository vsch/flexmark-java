package com.vladsch.flexmark.internal.util.mappers;

import com.vladsch.flexmark.internal.util.Computable;
import com.vladsch.flexmark.node.Node;

public class NodeClassifier implements Computable<Class<?>, Node> {
    final public static NodeClassifier INSTANCE = new NodeClassifier();

    private NodeClassifier() {
    }

    @Override
    public Class<?> compute(Node value) {
        return value.getClass();
    }
}
