package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.TextBase;
import com.vladsch.flexmark.parser.block.DocumentPostProcessor;
import com.vladsch.flexmark.parser.block.DocumentPostProcessorFactory;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class ZzzzzzDocumentPostProcessor extends DocumentPostProcessor {
    final private NodeVisitor myVisitor;

    public ZzzzzzDocumentPostProcessor(Document document) {
        myVisitor = new NodeVisitor(
                new VisitHandler<>(Text.class, this::visit)
        );
    }

    @NotNull
    @Override
    public Document processDocument(@NotNull Document document) {
        myVisitor.visit(document);
        return document;
    }

    private void visit(Text node) {
        if (!node.isOrDescendantOfType(DoNotDecorate.class)) {
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
        @NotNull
        @Override
        public DocumentPostProcessor apply(@NotNull Document document) {
            return new ZzzzzzDocumentPostProcessor(document);
        }
    }
}
