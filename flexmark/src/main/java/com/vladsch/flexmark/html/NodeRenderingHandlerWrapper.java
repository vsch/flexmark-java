package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;

class NodeRenderingHandlerWrapper {
    public final NodeRenderingHandler myRenderingHandler;
    public final NodeRenderingHandlerWrapper myPreviousRenderingHandler;

    public NodeRenderingHandlerWrapper(final NodeRenderingHandler renderingHandler, final NodeRenderingHandlerWrapper previousRenderingHandler) {
        myRenderingHandler = renderingHandler;
        myPreviousRenderingHandler = previousRenderingHandler;
    }
}
