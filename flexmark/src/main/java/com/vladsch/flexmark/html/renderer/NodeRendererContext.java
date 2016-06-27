package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.HtmlRendererOptions;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
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
     *
     * @param tag        the tag of the node being rendered, some nodes render multiple tags with attributes
     * @param attributes the attributes that were calculated by the renderer
     * @return the extended attributes with added/updated/removed entries
     */
    Map<String, String> extendRenderingNodeAttributes(String tag, Map<String, String> attributes);

    /**
     * @return the HTML writer to use
     */
    HtmlWriter getHtmlWriter();

    /**
     * Render the specified node and its children using the configured renderers. This should be used to render child
     * nodes; be careful not to pass the node that is being rendered, that would result in an endless loop.
     *
     * @param node the node to render
     */
    void render(Node node);

    /**
     * Render the children of the node, used by custom renderers
     * @param parent
     */
    void renderChildren(Node parent);

    /**
     * @return current rendering phase
     */
    RenderingPhase getRenderingPhase();

    DataHolder getOptions();
    
    HtmlRendererOptions getHtmlOptions();

    Document getDocument();
}
