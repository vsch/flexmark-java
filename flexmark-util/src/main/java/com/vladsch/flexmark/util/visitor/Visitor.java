package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.Node;

/**
 * Node visitor interface
 *
 * @param <N> specific node type
 */
public interface Visitor<N> extends AstNodeAction<N> {
    default void visitAs(Node node) {
        visit((N)node);
    }

    void visit(N node);
}
