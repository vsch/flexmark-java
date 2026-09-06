package com.vladsch.flexmark.util.ast;

import org.jspecify.annotations.Nullable;

public interface ReferenceNode<R extends NodeRepository<B>, B extends Node, N extends Node> extends Comparable<B> {
    @Nullable N getReferencingNode(Node node);
}
