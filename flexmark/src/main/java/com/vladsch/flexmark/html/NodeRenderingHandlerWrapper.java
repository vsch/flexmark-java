package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class NodeRenderingHandlerWrapper {
  public final @NotNull NodeRenderingHandler<?> myRenderingHandler;
  public final @Nullable NodeRenderingHandlerWrapper myPreviousRenderingHandler;

  public NodeRenderingHandlerWrapper(
      @NotNull NodeRenderingHandler<?> renderingHandler,
      @Nullable NodeRenderingHandlerWrapper previousRenderingHandler) {
    myRenderingHandler = renderingHandler;
    myPreviousRenderingHandler = previousRenderingHandler;
  }
}
