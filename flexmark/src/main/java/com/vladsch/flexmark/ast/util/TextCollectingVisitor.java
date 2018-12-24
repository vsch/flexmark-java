package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequenceBuilder;

import java.util.Arrays;
import java.util.HashSet;

@SuppressWarnings("WeakerAccess")
public class TextCollectingVisitor {
    private SegmentedSequenceBuilder out;
    private final NodeVisitor myVisitor;
    private final HashSet<Class> myLineBreakNodes;

    protected static Class[] concatArrays(Class[]... classes) {
        int total = 0;
        for (Class[] classList : classes) {
            total += classList.length;
        }

        Class[] result = new Class[total];

        int index = 0;
        for (Class[] classList : classes) {
            System.arraycopy(classList, 0, result, index, classList.length);
            index += classList.length;
        }
        
        return result;
    }

    public TextCollectingVisitor(Class... lineBreakNodes) {
        myLineBreakNodes = lineBreakNodes.length == 0 ? null : new HashSet<>(Arrays.asList(lineBreakNodes));

        myVisitor = new NodeVisitor(
                new VisitHandler<Text>(Text.class, new Visitor<Text>() {
                    @Override
                    public void visit(Text node) {
                        TextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<TextBase>(TextBase.class, new Visitor<TextBase>() {
                    @Override
                    public void visit(TextBase node) {
                        TextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<HtmlEntity>(HtmlEntity.class, new Visitor<HtmlEntity>() {
                    @Override
                    public void visit(HtmlEntity node) {
                        TextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<SoftLineBreak>(SoftLineBreak.class, new Visitor<SoftLineBreak>() {
                    @Override
                    public void visit(SoftLineBreak node) {
                        TextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<Paragraph>(Paragraph.class, new Visitor<Paragraph>() {
                    @Override
                    public void visit(Paragraph node) {
                        TextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<HardLineBreak>(HardLineBreak.class, new Visitor<HardLineBreak>() {
                    @Override
                    public void visit(HardLineBreak node) {
                        TextCollectingVisitor.this.visit(node);
                    }
                })
        ) {
            @Override
            public void visit(final Node node) {
                VisitHandler handler = myCustomHandlersMap.get(node.getClass());
                if (handler != null) {
                    handler.visit(node);
                } else {
                    visitChildren(node);
                    if (myLineBreakNodes != null && myLineBreakNodes.contains(node.getClass()) && !node.isOrDescendantOfType(DoNotCollectText.class)) {
                        out.append("\n");
                    }
                }
            }
        };
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

    private void visit(Paragraph node) {
        if (!node.isOrDescendantOfType(DoNotCollectText.class)) {
            if (!out.isEmpty()) {
                out.append("\n\n");
            }
            myVisitor.visitChildren(node);
        }
    }

    private void visit(SoftLineBreak node) {
        out.append(node.getChars());
    }

    private void visit(HardLineBreak node) {
        final BasedSequence chars = node.getChars();
        out.append(chars.subSequence(chars.length() - 1, chars.length()));
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
