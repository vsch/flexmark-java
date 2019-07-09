package com.vladsch.flexmark.util.format;

public interface NodeContext<N, C extends NodeContext> {
    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @return a new rendering context with a given appendable for its output
     */
    C getSubContext();

    /**
     * @return the current node being rendered
     */
    N getCurrentNode();
}
