package com.vladsch.flexmark.util.mappers;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.Computable;

public class NodeClassifier implements Computable<Class<?>, Node> {
    final public static NodeClassifier INSTANCE = new NodeClassifier();

    private NodeClassifier() {
    }

    @Override
    public Class<?> compute(Node value) {
        return value.getClass();
    }
}
