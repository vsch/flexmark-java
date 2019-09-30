package com.vladsch.flexmark.util.visitor;

/**
 * Intended to be extended with specific visit function(s) and parameters. see {@link AstVisitor}
 *
 * @param <N> subclass of {@link Node}
 */
public interface AstNodeAdaptingVisitor<Node, N extends Node> {

}
