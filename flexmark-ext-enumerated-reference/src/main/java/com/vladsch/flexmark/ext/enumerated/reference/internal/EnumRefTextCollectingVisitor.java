package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceLink;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceText;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequenceBuilder;

@SuppressWarnings("WeakerAccess")
public class EnumRefTextCollectingVisitor {
    private SegmentedSequenceBuilder out;
    private final NodeVisitor myVisitor;
    private final int ordinal;

    public EnumRefTextCollectingVisitor(int ordinal) {
        myVisitor = new NodeVisitor(
                new VisitHandler<Text>(Text.class, new Visitor<Text>() {
                    @Override
                    public void visit(Text node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<TextBase>(TextBase.class, new Visitor<TextBase>() {
                    @Override
                    public void visit(TextBase node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<HtmlEntity>(HtmlEntity.class, new Visitor<HtmlEntity>() {
                    @Override
                    public void visit(HtmlEntity node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<SoftLineBreak>(SoftLineBreak.class, new Visitor<SoftLineBreak>() {
                    @Override
                    public void visit(SoftLineBreak node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<HardLineBreak>(HardLineBreak.class, new Visitor<HardLineBreak>() {
                    @Override
                    public void visit(HardLineBreak node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<EnumeratedReferenceText>(EnumeratedReferenceText.class, new Visitor<EnumeratedReferenceText>() {
                    @Override
                    public void visit(EnumeratedReferenceText node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<EnumeratedReferenceLink>(EnumeratedReferenceLink.class, new Visitor<EnumeratedReferenceLink>() {
                    @Override
                    public void visit(EnumeratedReferenceLink node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                })
        );
        this.ordinal = ordinal;
    }

    public String getText() {
        return out.toString();
    }

    public void collect(Node node) {
        out = new SegmentedSequenceBuilder(node.getChars());
        myVisitor.visit(node);
    }

    public String collectAndGetText(Node node) {
        collect(node);
        return out.toString();
    }

    public BasedSequence[] collectAndGetSegments(Node node) {
        collect(node);
        return out.toSegments();
    }

    public BasedSequence collectAndGetSequence(Node node) {
        collect(node);
        return out.toBasedSequence();
    }

    private void visit(EnumeratedReferenceText node) {
        final String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            out.append(String.valueOf(ordinal));
        }
    }

    private void visit(EnumeratedReferenceLink node) {
        final String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            out.append(String.valueOf(ordinal));
        }
    }

    private void visit(SoftLineBreak node) {
        out.append(node.getChars());
    }

    private void visit(HardLineBreak node) {
        final BasedSequence chars = node.getChars();
        out.append(chars.subSequence(chars.length()-1, chars.length()));
    }

    private void visit(HtmlEntity node) {
        out.append(node.getChars().unescape());
    }

    private void visit(Text node) {
        if (!node.isOrDescendantOfType(DoNotCollectText.class)) {
            out.append(node.getChars());
        }
    }

    private void visit(TextBase node) {
        out.append(node.getChars());
    }
}
