package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlRendererOptions;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;

import java.util.Map;

/**
 * The context for node rendering, including configuration and functionality for the node renderer to use.
 */
public interface NodeRendererContext {

    /**
     * @param url to be encoded
     * @return an encoded URL (depending on the configuration)
     */
    String encodeUrl(String url);

    /**
     * Extend the attributes by extensions for the node being currently rendered.
     *
     * @param tag        the tag of the node being rendered, some nodes render multiple tags with attributes
     * @param attributes the attributes that were calculated by the renderer
     * @return the extended attributes with added/updated/blockRemoved entries
     */
    Map<String, String> extendRenderingNodeAttributes(String tag, Map<String, String> attributes);

    /**
     * @return the HTML writer to use
     */
    HtmlWriter getHtmlWriter();

    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @param out           appendable to use for generated html
     * @param inheritIndent whether the html writer of the sub-context should inherit the current context's indentation level or start with 0 indentation
     * @return a new rendering context with a given appendable for its output
     */
    NodeRendererContext getSubContext(Appendable out, boolean inheritIndent);

    /**
     * Render the specified node and its children using the configured renderers. This should be used to render child
     * nodes; be careful not to pass the node that is being rendered, that would result in an endless loop.
     *
     * @param node the node to render
     */
    void render(Node node);

    /**
     * Render the children of the node, used by custom renderers
     *
     * @param parent
     */
    void renderChildren(Node parent);

    /**
     * Get the id attribute for the given node.
     *
     * @param node node for which to get an id, depends on the {@link HtmlIdGenerator} assigned for the context. Default generator
     *             only creates ids for {@link com.vladsch.flexmark.node.Heading} nodes.
     * @return id string or null
     */
    String getNodeId(Node node);

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
    RenderingPhase getRenderingPhase();

    /**
     * Get the current rendering context {@link DataHolder}. These are the options passed or set on the {@link HtmlRenderer#builder()} or passed to {@link HtmlRenderer#builder(DataHolder)}.
     * To get the document options you should use {@link #getDocument()} as the data holder.
     *
     * @return the current renderer options {@link DataHolder}
     */
    DataHolder getOptions();

    /**
     * @return the {@link HtmlRendererOptions} for the context.
     */
    HtmlRendererOptions getHtmlOptions();

    /**
     * @return the {@link Document} node of the current context
     */
    Document getDocument();
}
