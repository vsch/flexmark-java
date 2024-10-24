package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.visitor.AstAction;
import com.vladsch.flexmark.util.visitor.AstHandler;

public class NodeRenderingHandler<N extends Node>
    extends AstHandler<N, NodeRenderingHandler.CustomNodeRenderer<N>> {
  public NodeRenderingHandler(Class<N> aClass, CustomNodeRenderer<N> adapter) {
    super(aClass, adapter);
  }

  public void render(Node node, NodeRendererContext context, HtmlWriter html) {
    getAdapter().render((N) node, context, html);
  }

  public interface CustomNodeRenderer<N extends Node> extends AstAction {
    void render(N node, NodeRendererContext context, HtmlWriter html);
  }
}
