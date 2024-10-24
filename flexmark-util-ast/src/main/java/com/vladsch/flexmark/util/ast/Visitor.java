package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.visitor.AstAction;

/**
 * Node visitor interface
 *
 * @param <N> specific node type
 */
public interface Visitor<N extends Node> extends AstAction {
  void visit(N node);
}
