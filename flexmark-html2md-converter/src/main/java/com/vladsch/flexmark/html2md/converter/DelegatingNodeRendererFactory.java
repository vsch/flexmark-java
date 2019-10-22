package com.vladsch.flexmark.html2md.converter;

import java.util.Set;

/**
 * Factory for instantiating new node renderers when rendering is done.
 */
public interface DelegatingNodeRendererFactory extends HtmlNodeRendererFactory {
    /**
     * List of renderer factories to which this factory's renderer may delegate rendering
     *
     * @return list of renderer factories
     */
    Set<Class<?>> getDelegates();
}
