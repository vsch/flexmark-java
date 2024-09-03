package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.visitor.AstAction;
import org.jetbrains.annotations.NotNull;

/**
 * Node visitor interface
 *
 * @param <N> specific node type
 */
public interface Visitor<N extends Node> extends AstAction<N> {
  void visit(@NotNull N node);
}
