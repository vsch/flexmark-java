package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.Node;

/**
 * Interface to visit variations on specific nodes:
 * visit() visiting node and if no handler defined then visit node's children
 * visitNodeOnly() visit node and if no handler then do not process children
 * visitChildren() visit node's children
 */
public interface NodeVisitHandler extends VisitHandler.Visitor<Node> {
    void visitNodeOnly(Node node);
    void visitChildren(Node parent);
}
