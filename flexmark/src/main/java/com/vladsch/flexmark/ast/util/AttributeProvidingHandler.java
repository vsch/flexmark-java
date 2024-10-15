package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.MutableAttributes;
import com.vladsch.flexmark.util.visitor.AstAction;
import com.vladsch.flexmark.util.visitor.AstHandler;

class AttributeProvidingHandler<N extends Node>
    extends AstHandler<N, AttributeProvidingHandler.AttributeProvidingVisitor<N>> {
  AttributeProvidingHandler(Class<N> aClass, AttributeProvidingVisitor<N> adapter) {
    super(aClass, adapter);
  }

  void setAttributes(Node node, AttributablePart part, MutableAttributes attributes) {
    getAdapter().setAttributes((N) node, part, attributes);
  }

  static interface AttributeProvidingVisitor<N extends Node> extends AstAction<N> {
    void setAttributes(N node, AttributablePart part, MutableAttributes attributes);
  }
}
