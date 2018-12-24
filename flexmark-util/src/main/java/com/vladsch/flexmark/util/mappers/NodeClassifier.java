package com.vladsch.flexmark.util.mappers;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.Computable;

public class NodeClassifier implements Computable<Class<?>, Node> {
    public static final NodeClassifier INSTANCE = new NodeClassifier();

    private NodeClassifier() {
    }

    @Override
    public Class<?> compute(Node value) {
        return value.getClass();
    }
}
