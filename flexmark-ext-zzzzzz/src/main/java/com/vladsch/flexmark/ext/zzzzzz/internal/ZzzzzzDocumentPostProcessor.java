package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.internal.util.ast.NodeVisitor;
import com.vladsch.flexmark.internal.util.ast.VisitHandler;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.DoNotLinkify;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Text;
import com.vladsch.flexmark.node.TextBase;
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

    private void visit(Text node) {
        if (!node.isOrDescendantOfType(DoNotLinkify.class)) {
            // do some processing

            BasedSequence original = node.getChars();
            boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
            TextBase textBase = null;

            while (wrapInTextBase) {
                if (wrapInTextBase) {
                    wrapInTextBase = false;
                    textBase = new TextBase(original);
                    node.insertBefore(textBase);
                    textBase.appendChild(node);
                }
            }
            
        }
    }

    public static class Factory extends DocumentPostProcessorFactory {
        @Override
        public DocumentPostProcessor create(Document document) {
            return new ZzzzzzDocumentPostProcessor(document);
        }
    }
}
