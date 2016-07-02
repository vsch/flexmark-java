package com.vladsch.flexmark.html;

import com.vladsch.flexmark.node.Node;

import java.util.Map;

/**
 * Extension point for adding/changing attributes on the primary HTML tag for a node.
 */
public interface AttributeProvider {

    /**
     * Set the attributes for the node by modifying the provided map.
     * <p>
     * This allows to change or even removeIndex default attributes. With great power comes great responsibility.
     * <p>
     * The attribute key and values will be escaped (preserving character entities), so don't escape them here,
     * otherwise they will be double-escaped.
     * 
     * Also used to get the id attribute for the node. Specifically for heading nodes. When the tag parameter 
     * is null only need to check and provide an id attribute.
     *
     * @param node       the node to set attributes for
     * @param tag        attributes for the specific tag being generated for the node, will be null when requesting id attribute for the node.
     * @param attributes the attributes, with any default attributes already set in the map
     */
    void setAttributes(Node node, String tag, Map<String, String> attributes);
}
