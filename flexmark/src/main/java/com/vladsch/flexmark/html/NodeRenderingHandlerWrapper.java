package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class NodeRenderingHandlerWrapper {
    final public @NotNull NodeRenderingHandler<?> myRenderingHandler;
    final public @Nullable NodeRenderingHandlerWrapper myPreviousRenderingHandler;

    public NodeRenderingHandlerWrapper(@NotNull NodeRenderingHandler<?> renderingHandler, @Nullable NodeRenderingHandlerWrapper previousRenderingHandler) {
        myRenderingHandler = renderingHandler;
        myPreviousRenderingHandler = previousRenderingHandler;
    }
}
