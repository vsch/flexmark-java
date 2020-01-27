package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.enumerated.reference.*;
import com.vladsch.flexmark.util.ast.DoNotCollectText;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;

@SuppressWarnings("WeakerAccess")
public class EnumRefTextCollectingVisitor {
    private SequenceBuilder out;
    final private NodeVisitor visitor;
    private Runnable ordinalRunnable;

    public EnumRefTextCollectingVisitor() {
        this(-1);
    }

    public EnumRefTextCollectingVisitor(int ordinal) {
        ordinalRunnable = ordinal < 0 ? null : () -> out.add(String.valueOf(ordinal));

        visitor = new NodeVisitor(
                new VisitHandler<>(Text.class, this::visit),
                new VisitHandler<>(TextBase.class, this::visit),
                new VisitHandler<>(HtmlEntity.class, this::visit),
                new VisitHandler<>(SoftLineBreak.class, this::visit),
                new VisitHandler<>(HardLineBreak.class, this::visit),
                new VisitHandler<>(EnumeratedReferenceText.class, this::visit),
                new VisitHandler<>(EnumeratedReferenceLink.class, this::visit)
        );
    }

    public String getText() {
        return out.toString();
    }

    public void collect(BasedSequence basedSequence, EnumeratedReferenceRendering[] renderings, String defaultFormat) {
        out = SequenceBuilder.emptyBuilder(basedSequence);
        EnumeratedReferences.renderReferenceOrdinals(renderings, new OrdinalRenderer(this));
    }

    private static class OrdinalRenderer implements EnumeratedOrdinalRenderer {
        final EnumRefTextCollectingVisitor renderer;

        public OrdinalRenderer(EnumRefTextCollectingVisitor renderer) {
            this.renderer = renderer;
        }

        @Override
        public void startRendering(EnumeratedReferenceRendering[] renderings) {

        }

        @Override
        public void setEnumOrdinalRunnable(Runnable runnable) {
            renderer.ordinalRunnable = runnable;
        }

        @Override
        public Runnable getEnumOrdinalRunnable() {
            return renderer.ordinalRunnable;
        }

        @Override
        public void render(int referenceOrdinal, EnumeratedReferenceBlock referenceFormat, String defaultText, boolean needSeparator) {
            Runnable compoundRunnable = renderer.ordinalRunnable;

            if (referenceFormat != null) {
                renderer.ordinalRunnable = () -> {
                    if (compoundRunnable != null) compoundRunnable.run();
                    renderer.out.add(String.valueOf(referenceOrdinal));
                    if (needSeparator) renderer.out.add(".");
                };

                renderer.visitor.visitChildren(referenceFormat);
            } else {
                renderer.out.add(defaultText + " ");
                if (compoundRunnable != null) compoundRunnable.run();
                renderer.out.add(String.valueOf(referenceOrdinal));
                if (needSeparator) renderer.out.add(".");
            }
        }

        @Override
        public void endRendering() {

        }
    }

    public String collectAndGetText(BasedSequence basedSequence, EnumeratedReferenceRendering[] renderings, String defaultFormat) {
        collect(basedSequence, renderings, defaultFormat);
        return out.toString();
    }

    private void visit(EnumeratedReferenceText node) {
        String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            if (ordinalRunnable != null) ordinalRunnable.run();
        }
    }

    private void visit(EnumeratedReferenceLink node) {
        String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            if (ordinalRunnable != null) ordinalRunnable.run();
        }
    }

    private void visit(SoftLineBreak node) {
        out.add(node.getChars());
    }

    private void visit(HardLineBreak node) {
        BasedSequence chars = node.getChars();
        out.add(chars.subSequence(chars.length() - 1, chars.length()));
    }

    private void visit(HtmlEntity node) {
        out.add(node.getChars().unescape());
    }

    private void visit(Text node) {
        if (!node.isOrDescendantOfType(DoNotCollectText.class)) {
            out.add(node.getChars());
        }
    }

    private void visit(TextBase node) {
        out.add(node.getChars());
    }
}
