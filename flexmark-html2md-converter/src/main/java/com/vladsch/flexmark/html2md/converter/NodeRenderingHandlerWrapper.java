package com.vladsch.flexmark.html2md.converter;

import org.jsoup.nodes.Node;

class NodeRenderingHandlerWrapper<N extends Node> {
    final public HtmlNodeRendererHandler<N> myRenderingHandler;
    final public NodeRenderingHandlerWrapper<N> myPreviousRenderingHandler;

    public NodeRenderingHandlerWrapper(HtmlNodeRendererHandler<N> renderingHandler, NodeRenderingHandlerWrapper<N> previousRenderingHandler) {
        myRenderingHandler = renderingHandler;
        myPreviousRenderingHandler = previousRenderingHandler;
    }
}
