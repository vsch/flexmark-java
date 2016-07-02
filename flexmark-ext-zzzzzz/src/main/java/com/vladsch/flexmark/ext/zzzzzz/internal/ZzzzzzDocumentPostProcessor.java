package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.node.DoNotLinkify;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.node.Text;
import com.vladsch.flexmark.parser.block.DocumentPostProcessor;
import com.vladsch.flexmark.parser.block.DocumentPostProcessorFactory;

public class ZzzzzzDocumentPostProcessor extends DocumentPostProcessor {
    public ZzzzzzDocumentPostProcessor(Document document) {
    }

    @Override
    public Document processDocument(Document document) {
        document.accept(this);
        return document;
    }

    @Override
    public void visit(Text text) {
        if (!text.isOrDescendantOfType(DoNotLinkify.class)) {
        }
    }

    private Node processText(Text node) {
        return node;
    }

    public static class Factory extends DocumentPostProcessorFactory {
        @Override
        public DocumentPostProcessor create(Document document) {
            return new ZzzzzzDocumentPostProcessor(document);
        }
    }
}
