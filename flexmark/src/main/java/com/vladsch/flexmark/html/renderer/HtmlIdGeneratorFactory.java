package com.vladsch.flexmark.html.renderer;

import org.jetbrains.annotations.NotNull;

/**
 * Factory for instantiating new node renderers when rendering is done.
 */
public interface HtmlIdGeneratorFactory {

    /**
     * Create an id generator
     *
     * @return an html id generator
     */
    @NotNull HtmlIdGenerator create();
}
