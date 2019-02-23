package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Provider which will provide attributes stored in the node's first {@link EmbeddedNodeAttributes} of the node's children
 */
public class EmbeddedAttributeProvider implements AttributeProvider {

    public static final IndependentAttributeProviderFactory Factory = new IndependentAttributeProviderFactory() {
        @Override
        public AttributeProvider create(LinkResolverContext context) {
            //noinspection ReturnOfInnerClass
            return new EmbeddedAttributeProvider();
        }
    };

    private EmbeddedAttributeProvider() {
    }

    @Override
    public void setAttributes(final Node node, final AttributablePart part, final Attributes attributes) {
        if (part == AttributablePart.NODE) {
            Node firstChild = node.getChildOfType(EmbeddedNodeAttributes.class);
            if (firstChild instanceof EmbeddedNodeAttributes) {
                attributes.addValues(((EmbeddedNodeAttributes) firstChild).attributes);
            }
        }
    }

    // so we can attach attributes to any node in the AST and have a generic attribute provider serve them up
    public static class EmbeddedNodeAttributes extends Node {
        final Attributes attributes;

        public EmbeddedNodeAttributes(Node parent, Attributes attributes) {
            super(parent.getChars().subSequence(0,0));
            this.attributes = attributes;
        }

        @Override
        public BasedSequence[] getSegments() {
            return Node.EMPTY_SEGMENTS;
        }

        @Override
        public void astString(final StringBuilder out, final boolean withExtra) {
            out.append(EmbeddedNodeAttributes.class.getSimpleName());
            out.append("[").append(getStartOffset()).append(", ").append(getEndOffset()).append("]");
            out.append(", attributes: ").append(attributes.toString());
            
            if (withExtra) getAstExtra(out);
        }

        @Override
        public void astExtraChars(final StringBuilder out) {
        }
    }
}
