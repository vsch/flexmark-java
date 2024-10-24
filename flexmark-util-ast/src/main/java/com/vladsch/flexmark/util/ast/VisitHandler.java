package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.visitor.AstHandler;

/** Node visit handler for specific node type */
public class VisitHandler<N extends Node> extends AstHandler<N, Visitor<N>>
    implements Visitor<Node> {
  public VisitHandler(Class<N> klass, Visitor<N> adapter) {
    super(klass, adapter);
  }

  @Override
  public void visit(Node node) {
    getAdapter().visit((N) node);
  }
}
