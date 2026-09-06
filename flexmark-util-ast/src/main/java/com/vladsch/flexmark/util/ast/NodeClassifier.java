package com.vladsch.flexmark.util.ast;


import java.util.function.Function;

public class NodeClassifier implements Function<Node, Class<?>> {
    final public static NodeClassifier INSTANCE = new NodeClassifier();

    private NodeClassifier() {
    }

    @Override
    public Class<?> apply(Node value) {
        return value.getClass();
    }
}
