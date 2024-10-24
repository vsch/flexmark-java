package com.vladsch.flexmark.util.ast;

import java.util.function.Function;

public final class NodeClassifier implements Function<Node, Class<?>> {
  public static final NodeClassifier INSTANCE = new NodeClassifier();

  private NodeClassifier() {}

  @Override
  public Class<?> apply(Node value) {
    return value.getClass();
  }
}
