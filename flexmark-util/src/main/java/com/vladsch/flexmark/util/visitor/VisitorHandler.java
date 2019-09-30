package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.Node;

/**
 * Node visitor handler interface
 * <p>
 * Must be package private so it cannot be inherited by specific implementations of Visitor&lt;N&gt;
 *
 * @param <N> specific node type
 */
interface VisitorHandler<N extends Node> extends Visitor<N> {
    default void visitNode(Node node) {
        visit((N) node);
    }
}
