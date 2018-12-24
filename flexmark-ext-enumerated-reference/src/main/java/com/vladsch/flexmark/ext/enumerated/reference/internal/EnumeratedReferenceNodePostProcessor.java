package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.ext.attributes.AttributeNode;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.NodeTracker;

public class EnumeratedReferenceNodePostProcessor extends NodePostProcessor {
    private final EnumeratedReferences enumeratedReferences;

    public EnumeratedReferenceNodePostProcessor(Document document) {
        enumeratedReferences = EnumeratedReferenceExtension.ENUMERATED_REFERENCE_ORDINALS.getFrom(document);
    }

    @Override
    public void process(NodeTracker state, Node node) {
        if (node instanceof AttributesNode) {
            AttributesNode attributesNode = (AttributesNode) node;

            for (Node attributeNode : attributesNode.getChildren()) {
                if (attributeNode instanceof AttributeNode) {
                    if (((AttributeNode) attributeNode).isId()) {
                        final String text = ((AttributeNode) attributeNode).getValue().toString();
                        enumeratedReferences.add(text);
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
            return new EnumeratedReferenceNodePostProcessor(document);
        }
    }
}
