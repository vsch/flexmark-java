package com.vladsch.flexmark.util.ast;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public class NodeClassifier implements Function<Node, Class<?>> {
    final public static NodeClassifier INSTANCE = new NodeClassifier();

    private NodeClassifier() {
    }

    @Override
    public @NotNull Class<?> apply(@NotNull Node value) {
        return value.getClass();
    }
}
