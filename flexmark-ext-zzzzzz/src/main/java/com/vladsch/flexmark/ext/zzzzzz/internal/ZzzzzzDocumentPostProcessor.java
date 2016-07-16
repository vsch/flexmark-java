package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.internal.util.ast.NodeVisitor;
import com.vladsch.flexmark.internal.util.ast.VisitHandler;
import com.vladsch.flexmark.node.DoNotLinkify;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Text;
import com.vladsch.flexmark.parser.block.DocumentPostProcessor;
import com.vladsch.flexmark.parser.block.DocumentPostProcessorFactory;

public class ZzzzzzDocumentPostProcessor extends DocumentPostProcessor {
    private final NodeVisitor myVisitor;

    public ZzzzzzDocumentPostProcessor(Document document) {
        myVisitor = new NodeVisitor(
                new VisitHandler<>(Text.class, ZzzzzzDocumentPostProcessor.this::visit)
        );
    }

    @Override
    public Document processDocument(Document document) {
        myVisitor.visit(document);
        return document;
    }

    private void visit(Text text) {
        if (!text.isOrDescendantOfType(DoNotLinkify.class)) {
            // do some processing
        }
    }

    public static class Factory extends DocumentPostProcessorFactory {
        @Override
        public DocumentPostProcessor create(Document document) {
            return new ZzzzzzDocumentPostProcessor(document);
        }
    }
}
