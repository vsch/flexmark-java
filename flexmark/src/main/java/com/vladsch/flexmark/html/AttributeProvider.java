package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.MutableAttributes;
import org.jetbrains.annotations.NotNull;

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
     * <p>
     * Also used to get the id attribute for the node. Specifically for heading nodes. When the part parameter
     * is AttributablePart.ID only need to check and provide an id attribute.
     * <p>
     * When part is AttributablePart.LINK then attributes are being requested for a Link or Image link,
     * link status after link resolution will be found under the Attribute.LINK_STATUS.
     * Core defines LinkStatus.UNKNOWN,LinkStatus.VALID,LinkStatus.NOT_FOUND. Extensions can define more.
     * <p>
     * AttributablePart.NODE is a generic placeholder when the node did not provide a specific part for attribution.
     *  @param node       the node to set attributes for
     * @param part       attributes for the specific part of the node being generated, Core defines AttributablePart.LINK,
     *                   AttributablePart.ID and generic AttributablePart.NODE, extensions are free to define more
     * @param attributes the attributes, with any default attributes already set in the map
     */
    void setAttributes(@NotNull Node node, @NotNull AttributablePart part, @NotNull MutableAttributes attributes);
}
