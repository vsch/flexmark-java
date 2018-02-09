package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.attributes.AttributeNode;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.NodeTracker;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class AttributesNodePostProcessor extends NodePostProcessor {
    private final NodeAttributeRepository nodeAttributeRepository;
    private final AttributesOptions myOptions;

    public AttributesNodePostProcessor(Document document) {
        nodeAttributeRepository = AttributesExtension.NODE_ATTRIBUTES.getFrom(document);
        myOptions = new AttributesOptions(document);
    }

    @Override
    public void process(NodeTracker state, Node node) {
        if (node instanceof AttributesNode) {
            AttributesNode attributesNode = (AttributesNode) node;

            // apply to sibling unless the sibling is a Text node then apply it to the parent
            Node sibling = attributesNode.getPrevious();
            Node attributeOwner;
            if (sibling == null || sibling instanceof Text || sibling instanceof TextBase) {
                // if not attached to the text node, by having spaces in between, then give it to the parent
                if (sibling == null || !myOptions.assignTextAttributes || sibling.getChars().countTrailing(" \t") > 0) {
                    // here we remove the trailing chars from sibling if we are the last node of the parent, because
                    // these are trailing spaces
                    if (sibling != null) {
                        if (sibling.getPrevious() == null && sibling.getChars().isBlank()) {
                            // first and all blanks, remove it
                            sibling.unlink();
                            state.nodeRemoved(sibling);
                            sibling = null;
                        } else if (attributesNode.getNext() == null) {
                            sibling.setChars(sibling.getChars().trimEnd());
                        }
                    }

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
                    if (sibling instanceof Text) {
                        TextBase textBase = new TextBase(sibling.getChars());
                        // insert text base where text was
                        sibling.insertBefore(textBase);
                        sibling.unlink();
                        state.nodeRemoved(sibling);

                        textBase.appendChild(sibling);
                        state.nodeAddedWithChildren(textBase);
                        attributeOwner = textBase;
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
