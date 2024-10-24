package com.vladsch.flexmark.util.ast;

public interface ReferenceNode<B extends Node, N extends Node> extends Comparable<B> {

  N getReferencingNode(Node node);
}
