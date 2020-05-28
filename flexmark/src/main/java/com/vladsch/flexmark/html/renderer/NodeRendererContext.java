package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.HtmlRendererOptions;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.MutableAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The context for node rendering, including configuration and functionality for the node renderer to use.
 */
public interface NodeRendererContext extends LinkResolverContext {

    /**
     * Extend the attributes by extensions for the node being currently rendered.
     *
     * @param part       the tag of the node being rendered, some nodes render multiple tags with attributes
     * @param attributes the attributes that were calculated by the renderer or null, these may be modified. To preserve originals pass a copy.
     * @return the extended attributes with added/updated/removed entries
     */
    @NotNull MutableAttributes extendRenderingNodeAttributes(@NotNull AttributablePart part, @Nullable Attributes attributes);

    /**
     * Extend the attributes by extensions for the node being currently rendered.
     *
     * @param node       node for which to get attributes
     * @param part       the tag of the node being rendered, some nodes render multiple tags with attributes
     * @param attributes the attributes that were calculated by the renderer or null, these may be modified. To preserve originals pass a copy.
     * @return the extended attributes with added/updated/removed entries
     */
    @NotNull MutableAttributes extendRenderingNodeAttributes(@NotNull Node node, @NotNull AttributablePart part, @Nullable Attributes attributes);

    /**
     * @return the HTML writer to use
     */
    @NotNull HtmlWriter getHtmlWriter();

    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer, renderedNode, renderingHandler and doNotRenderLinksNesting from the parent.
     *
     * @param inheritIndent whether the html writer of the sub-context should inherit the current context's indentation level or start with 0 indentation
     * @return a new rendering context with a given appendable for its output
     */
    @NotNull NodeRendererContext getSubContext(boolean inheritIndent);

    /**
     * Creates a child rendering context that can be used to collect rendered html text of the previously registered node renderer. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @param inheritIndent whether the html writer of the sub-context should inherit the current context's indentation level or start with 0 indentation
     * @return a new rendering context with a given appendable for its output
     */
    @NotNull NodeRendererContext getDelegatedSubContext(boolean inheritIndent);

    /**
     * pass node rendering to previously registered handler
     */
    void delegateRender();

    /**
     * Get the id attribute for the given node.
     *
     * @param node node for which to get an id, depends on the {@link HtmlIdGenerator} assigned for the context. Default generator
     *             only creates ids for {@link com.vladsch.flexmark.ast.Heading} nodes or custom nodes that implement {@link com.vladsch.flexmark.ast.AnchorRefTarget} interface.
     * @return id string or null
     */
    @Nullable String getNodeId(@NotNull Node node);

    /**
     * Whether the current rendering context has link rendering disabled. When true any renderer that would render a link, should only output the
     * text of that link and image links should not render anything.
     *
     * @return true if links should be output as only text.
     * @see #isDoNotRenderLinks()
     * @see #doNotRenderLinks()
     * @see #doRenderLinks()
     * @see #doNotRenderLinks(boolean)
     */
    boolean isDoNotRenderLinks();

    /**
     * Increment/Decrement the do not render links in this context. This value will persist for the duration of the current node's render() method
     * and will be restored upon return. Effectively it will persist for the rendering of the children of this node.
     *
     * @param doNotRenderLinks if true then increment the doNotRenderLinks value, else decrement it
     * @see #isDoNotRenderLinks()
     * @see #doNotRenderLinks()
     * @see #doRenderLinks()
     * @see #doNotRenderLinks(boolean)
     */
    void doNotRenderLinks(boolean doNotRenderLinks);

    /**
     * Increment the do not render links in this context. This value will persist for the duration of the current node's render() method
     * and will be restored upon return. Effectively it will persist for the rendering of the children of this node.
     *
     * @see #isDoNotRenderLinks()
     * @see #doNotRenderLinks()
     * @see #doRenderLinks()
     * @see #doNotRenderLinks(boolean)
     */
    void doNotRenderLinks();

    /**
     * Decrement the do not render links in this context. This value will persist for the duration of the current node's render() method
     * and will be restored upon return. Effectively it will persist for the rendering of the children of this node.
     *
     * @throws IllegalStateException if the current doNotRender links value is 0.
     * @see #isDoNotRenderLinks()
     * @see #doNotRenderLinks()
     * @see #doRenderLinks()
     * @see #doNotRenderLinks(boolean)
     */
    void doRenderLinks();

    /**
     * @return current rendering phase
     */
    @NotNull RenderingPhase getRenderingPhase();

    /**
     * @return the {@link HtmlRendererOptions} for the context.
     */
    @NotNull HtmlRendererOptions getHtmlOptions();
}
