package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.attributes.AttributeNode;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.NodeTracker;

public class AttributesNodePostProcessor extends NodePostProcessor {
    private final NodeAttributeRepository nodeAttributeRepository;

    public AttributesNodePostProcessor(Document document) {
        nodeAttributeRepository = AttributesExtension.NODE_ATTRIBUTES.getFrom(document);
    }

    @Override
    public void process(NodeTracker state, Node node) {
        if (node instanceof AttributesNode) {
            AttributesNode attributesNode = (AttributesNode) node;

            // apply to sibling unless the sibling is a Text node then apply it to the parent
            Node sibling = attributesNode.getPrevious();
            Node attributeOwner;
            if (sibling == null || sibling instanceof Text || sibling instanceof TextBase) {
                attributeOwner = attributesNode.getParent();
                if (attributeOwner instanceof Paragraph && sibling == null) {
                    // put it in the parent's sibling
                    sibling = attributeOwner.getPrevious();
                    if (sibling == null) {
                        // put it at the paragraph's parent since it is the first element of a paragraph, if it is the document then attributes will not be used
                        attributeOwner = attributeOwner.getParent();
                    } else {
                        attributeOwner = sibling;
                    }
                }
            } else {
                attributeOwner = sibling;
            }

            nodeAttributeRepository.put(attributeOwner, attributesNode);

            // set the heading id for this node so the correct id will be used
            if (attributeOwner instanceof AnchorRefTarget) {
                for (Node attributeNode : attributesNode.getChildren()) {
                    if (attributeNode instanceof AttributeNode) {
                        if (((AttributeNode) attributeNode).isId()) {
                            ((AnchorRefTarget) attributeOwner).setAnchorRefId(((AttributeNode) attributeNode).getValue().toString());
                        }
                    }
                }
            }
        }
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory() {
            super(false);
            addNodes(AttributesNode.class);
        }

        @Override
        public NodePostProcessor create(Document document) {
            return new AttributesNodePostProcessor(document);
        }
    }
}
