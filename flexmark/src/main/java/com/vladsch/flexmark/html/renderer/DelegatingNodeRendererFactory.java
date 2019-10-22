package com.vladsch.flexmark.html.renderer;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Factory for instantiating new node renderers when rendering is done.
 */
public interface DelegatingNodeRendererFactory extends NodeRendererFactory {
    /**
     * List of renderer factories to which this factory's renderer may delegate rendering
     *
     * @return list of renderer factories
     */
    @Nullable Set<Class<?>> getDelegates();
}
