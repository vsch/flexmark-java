package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;

class NodeRenderingHandlerWrapper {
  public final NodeRenderingHandler<?> myRenderingHandler;
  public final NodeRenderingHandlerWrapper myPreviousRenderingHandler;

  public NodeRenderingHandlerWrapper(
      NodeRenderingHandler<?> renderingHandler,
      NodeRenderingHandlerWrapper previousRenderingHandler) {
    myRenderingHandler = renderingHandler;
    myPreviousRenderingHandler = previousRenderingHandler;
  }
}
