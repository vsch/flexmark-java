package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.MutableAttributes;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * Provider which will provide attributes stored in the node's first {@link EmbeddedNodeAttributes} of the node's children
 */
public class EmbeddedAttributeProvider implements AttributeProvider {

    final public static IndependentAttributeProviderFactory Factory = new IndependentAttributeProviderFactory() {
        @NotNull
        @Override
        public AttributeProvider apply(@NotNull LinkResolverContext context) {
            return new EmbeddedAttributeProvider();
        }
    };

    EmbeddedAttributeProvider() {
    }

    @Override
    public void setAttributes(@NotNull Node node, @NotNull AttributablePart part, @NotNull MutableAttributes attributes) {
        if (part == AttributablePart.NODE) {
            Node firstChild = node.getChildOfType(EmbeddedNodeAttributes.class);
            if (firstChild instanceof EmbeddedNodeAttributes) {
                attributes.addValues(((EmbeddedNodeAttributes) firstChild).attributes);
            }
        }
    }

    // so we can attach attributes to any node in the AST and have a generic attribute provider serve them up
    public static class EmbeddedNodeAttributes extends Node {
        final @NotNull Attributes attributes;

        public EmbeddedNodeAttributes(@NotNull Node parent, @NotNull Attributes attributes) {
            super(parent.getChars().subSequence(0, 0));
            this.attributes = attributes;
        }

        @NotNull
        @Override
        public BasedSequence[] getSegments() {
            return Node.EMPTY_SEGMENTS;
        }

        @Override
        public void astString(@NotNull StringBuilder out, boolean withExtra) {
            out.append(EmbeddedNodeAttributes.class.getSimpleName());
            out.append("[").append(getStartOffset()).append(", ").append(getEndOffset()).append("]");
            out.append(", attributes: ").append(attributes.toString());

            if (withExtra) getAstExtra(out);
        }

        @Override
        public void astExtraChars(@NotNull StringBuilder out) {
        }
    }
}
