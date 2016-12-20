package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.anchorlink.AnchorLink;
import com.vladsch.flexmark.parser.block.DocumentPostProcessor;
import com.vladsch.flexmark.parser.block.DocumentPostProcessorFactory;

public class AnchorLinkPostProcessor extends DocumentPostProcessor {
    private final AnchorLinkOptions options;
    private final NodeVisitor myVisitor;

    public AnchorLinkPostProcessor(Document document) {
        this.options = new AnchorLinkOptions(document);
        myVisitor = new NodeVisitor(
                new VisitHandler<>(Heading.class, new Visitor<Heading>() {
                    @Override
                    public void visit(Heading node) {
                        AnchorLinkPostProcessor.this.visit(node);
                    }
                })
        );
    }

    @Override
    public Document processDocument(Document document) {
        myVisitor.visit(document);
        return document;
    }

    private void visit(Heading node) {
        if (!node.isOrDescendantOfType(DoNotDecorate.class)) {
            processNode(node);
        }
    }

    private void processNode(Heading node) {
        if (node.getText().isNotNull()) {
            Node anchor = new AnchorLink();

            if (!options.wrapText) {
                if (node.getFirstChild() == null) {
                    node.appendChild(anchor);
                } else {
                    node.getFirstChild().insertBefore(anchor);
                }
            } else {
                anchor.takeChildren(node);
                node.appendChild(anchor);
            }
        }
    }

    public static class Factory extends DocumentPostProcessorFactory {
        @Override
        public DocumentPostProcessor create(Document document) {
            return new AnchorLinkPostProcessor(document);
        }
    }
}
