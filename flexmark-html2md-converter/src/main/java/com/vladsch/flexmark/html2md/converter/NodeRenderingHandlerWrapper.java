package com.vladsch.flexmark.html2md.converter;

import org.jsoup.nodes.Node;

class NodeRenderingHandlerWrapper<N extends Node> {
    public final HtmlNodeRendererHandler<N> myRenderingHandler;
    public final NodeRenderingHandlerWrapper<N> myPreviousRenderingHandler;

    public NodeRenderingHandlerWrapper(HtmlNodeRendererHandler<N> renderingHandler, NodeRenderingHandlerWrapper previousRenderingHandler) {
        myRenderingHandler = renderingHandler;
        myPreviousRenderingHandler = previousRenderingHandler;
    }
}
