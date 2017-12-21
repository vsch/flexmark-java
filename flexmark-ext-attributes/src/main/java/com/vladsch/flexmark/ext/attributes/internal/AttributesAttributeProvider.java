package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.attributes.AttributeNode;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.ArrayList;

import static com.vladsch.flexmark.util.html.Attribute.CLASS_ATTR;

public class AttributesAttributeProvider implements AttributeProvider {
    private final NodeAttributeRepository nodeAttributeRepository;

    public AttributesAttributeProvider(NodeRendererContext context) {
        DataHolder options = context.getOptions();
        nodeAttributeRepository = AttributesExtension.NODE_ATTRIBUTES.getFrom(options);
    }

    @Override
    public void setAttributes(Node node, AttributablePart part, Attributes attributes) {
        ArrayList<AttributesNode> nodeAttributesList = nodeAttributeRepository.get(node);
        if (nodeAttributesList != null) {
            // add these as attributes
            for (AttributesNode nodeAttributes : nodeAttributesList) {
                for (Node attribute : nodeAttributes.getChildren()) {
                    if (!(attribute instanceof AttributeNode)) continue;

                    final AttributeNode attributeNode = (AttributeNode) attribute;
                    if (!attributeNode.isImplicitName()) {
                        final BasedSequence attributeNodeName = attributeNode.getName();
                        if (attributeNodeName.isNotNull() && !attributeNodeName.isBlank()) {
                            if (!attributeNodeName.equals(CLASS_ATTR)) {
                                attributes.remove(attributeNodeName);
                            }
                            attributes.addValue(attributeNodeName, attributeNode.getValue());
                        } else {
                            // empty then ignore
                        }
                    } else {
                        // implicit
                        if (attributeNode.isClass()) {
                            attributes.addValue(CLASS_ATTR, attributeNode.getImplicitNameValue());
                        } else if (attributeNode.isId()) {
                            attributes.remove(Attribute.ID_ATTR);
                            attributes.addValue(Attribute.ID_ATTR, attributeNode.getImplicitNameValue());
                        } else {
                            // unknown
                            throw new IllegalStateException("Implicit attribute yet not class or id");
                        }
                    }
                }
            }
        }
    }

    public static class Factory extends IndependentAttributeProviderFactory {
        //@Override
        //public Set<Class<? extends AttributeProviderFactory>> getAfterDependents() {
        //    return null;
        //}
        //
        //@Override
        //public Set<Class<? extends AttributeProviderFactory>> getBeforeDependents() {
        //    return null;
        //}
        //
        //@Override
        //public boolean affectsGlobalScope() {
        //    return false;
        //}

        @Override
        public AttributeProvider create(NodeRendererContext context) {
            return new AttributesAttributeProvider(context);
        }
    }
}
