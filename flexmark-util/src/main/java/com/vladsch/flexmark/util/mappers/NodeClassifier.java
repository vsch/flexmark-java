package com.vladsch.flexmark.util.mappers;

import java.util.function.Function;
import com.vladsch.flexmark.util.ast.Node;

public class NodeClassifier implements Function<Node, Class<?>> {
    public static final NodeClassifier INSTANCE = new NodeClassifier();

    private NodeClassifier() {
    }

    @Override
    public Class<?> apply(Node value) {
        return value.getClass();
    }
}
