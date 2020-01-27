package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ext.attributes.AttributeNode;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceRepository;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceText;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferences;
import com.vladsch.flexmark.html.renderer.HeaderIdGenerator;
import com.vladsch.flexmark.html.renderer.HtmlIdGenerator;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class EnumeratedReferenceNodePostProcessor extends NodePostProcessor {
    final private EnumeratedReferences enumeratedReferences;
    final private HtmlIdGenerator headerIdGenerator;

    public EnumeratedReferenceNodePostProcessor(Document document) {
        enumeratedReferences = EnumeratedReferenceExtension.ENUMERATED_REFERENCE_ORDINALS.get(document);
        headerIdGenerator = new HeaderIdGenerator.Factory().create();
        headerIdGenerator.generateIds(document);
    }

    @Override
    public void process(@NotNull NodeTracker state, @NotNull Node node) {
        if (node instanceof AttributesNode) {
            AttributesNode attributesNode = (AttributesNode) node;

            for (Node attributeNode : attributesNode.getChildren()) {
                if (attributeNode instanceof AttributeNode) {
                    if (((AttributeNode) attributeNode).isId()) {
                        final String text = ((AttributeNode) attributeNode).getValue().toString();
                        enumeratedReferences.add(text);
                        break;
                    }
                }
            }
        } else if (node instanceof Heading) {
            // see if it has bare enum reference text
            for (Node child : node.getChildren()) {
                if (child instanceof EnumeratedReferenceText) {
                    BasedSequence text = ((EnumeratedReferenceText) child).getText();
                    String type = EnumeratedReferenceRepository.getType(text.toString());
                    if (type.isEmpty() || text.equals(type + ":")) {
                        String id = (type.isEmpty() ? text : type) + ":" + headerIdGenerator.getId(node);
                        enumeratedReferences.add(id);
                    }
                }
            }
        }
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory() {
            super(false);
            addNodes(AttributesNode.class, Heading.class);
        }

        @NotNull
        @Override
        public NodePostProcessor apply(@NotNull Document document) {
            return new EnumeratedReferenceNodePostProcessor(document);
        }
    }
}
