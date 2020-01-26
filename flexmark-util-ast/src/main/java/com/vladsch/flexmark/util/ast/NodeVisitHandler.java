package com.vladsch.flexmark.util.ast;

import org.jetbrains.annotations.NotNull;

/**
 * Interface to visit variations on specific nodes:
 * visit() visiting node and if no handler defined then visit node's children
 * visitNodeOnly() visit node and if no handler then do not process children
 * visitChildren() visit node's children
 */
public interface NodeVisitHandler extends Visitor<Node> {
    void visitNodeOnly(@NotNull Node node);
    void visitChildren(@NotNull Node parent);
}
