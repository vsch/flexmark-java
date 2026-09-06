package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import org.jspecify.annotations.Nullable;

class NodeRenderingHandlerWrapper {
    final public NodeRenderingHandler<?> myRenderingHandler;
    final public @Nullable NodeRenderingHandlerWrapper myPreviousRenderingHandler;

    public NodeRenderingHandlerWrapper(NodeRenderingHandler<?> renderingHandler, @Nullable NodeRenderingHandlerWrapper previousRenderingHandler) {
        myRenderingHandler = renderingHandler;
        myPreviousRenderingHandler = previousRenderingHandler;
    }
}
