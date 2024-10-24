package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.visitor.AstAction;
import com.vladsch.flexmark.util.visitor.AstHandler;

public class NodeFormattingHandler<N extends Node>
    extends AstHandler<N, NodeFormattingHandler.CustomNodeFormatter<N>> {
  public NodeFormattingHandler(Class<N> aClass, CustomNodeFormatter<N> adapter) {
    super(aClass, adapter);
  }

  void render(Node node, NodeFormatterContext context, MarkdownWriter markdown) {
    getAdapter().render((N) node, context, markdown);
  }

  public static interface CustomNodeFormatter<N extends Node> extends AstAction {
    void render(N node, NodeFormatterContext context, MarkdownWriter markdown);
  }
}
