package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.HeaderIdGeneratorFactory;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Extension point for RenderingExtensions that only provide attributes, link resolvers or html id generators
 */
public interface RendererBuilder extends DataHolder {

    /**
     * Add an attribute provider for adding/changing HTML attributes to the rendered tags.
     *
     * @param attributeProviderFactory the attribute provider factory to add
     * @return {@code this}
     */
    @NotNull RendererBuilder attributeProviderFactory(@NotNull AttributeProviderFactory attributeProviderFactory);
    
    /**
     * Add a factory for resolving links in markdown to URI used in rendering
     *
     * @param linkResolverFactory the factory for creating a node renderer
     * @return {@code this}
     */
    @NotNull RendererBuilder linkResolverFactory(@NotNull LinkResolverFactory linkResolverFactory);

    /**
     * Add a factory for resolving URI to content
     *
     * @param contentResolverFactory the factory for creating a node renderer
     * @return {@code this}
     */
    @NotNull RendererBuilder contentResolverFactory(@NotNull UriContentResolverFactory contentResolverFactory);

    /**
     * Add a factory for generating the header id attribute from the header's text
     *
     * @param htmlIdGeneratorFactory the factory for generating header tag id attributes
     * @return {@code this}
     */
    @NotNull RendererBuilder htmlIdGeneratorFactory(@NotNull HeaderIdGeneratorFactory htmlIdGeneratorFactory);
}
