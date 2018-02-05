package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.HeaderIdGeneratorFactory;
import com.vladsch.flexmark.util.options.DataHolder;

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
    RendererBuilder attributeProviderFactory(AttributeProviderFactory attributeProviderFactory);
    /**
     * Add a factory for instantiating a node renderer (done when rendering). This allows to override the rendering
     * of node types or define rendering for custom node types.
     * <p>
     * If multiple node renderers for the same node type are created, the one from the factory that was added first
     * "wins". (This is how the rendering for core node types can be overridden; the default rendering comes last.)
     *
     * @param linkResolverFactory the factory for creating a node renderer
     * @return {@code this}
     */
    RendererBuilder linkResolverFactory(LinkResolverFactory linkResolverFactory);

    /**
     * Add a factory for generating the header id attribute from the header's text
     *
     * @param htmlIdGeneratorFactory the factory for generating header tag id attributes
     * @return {@code this}
     */
    public RendererBuilder htmlIdGeneratorFactory(HeaderIdGeneratorFactory htmlIdGeneratorFactory);
}
