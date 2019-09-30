package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.Node;

/**
 * Node visitor interface
 *
 * @param <N> specific node type
 */
public interface Visitor<N extends Node> extends AstAction<N> {
    void visit(N node);
}
