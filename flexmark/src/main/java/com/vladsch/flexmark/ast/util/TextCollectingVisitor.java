package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequenceBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.BiConsumer;

@SuppressWarnings("WeakerAccess")
public class TextCollectingVisitor {
    private SegmentedSequenceBuilder out;
    private final NodeVisitor myVisitor;
    private final HashSet<Class<?>> myLineBreakNodes;

    protected static Class<?>[] concatArrays(Class<?>[]... classes) {
        int total = 0;
        for (Class<?>[] classList : classes) {
            total += classList.length;
        }

        Class<?>[] result = new Class<?>[total];

        int index = 0;
        for (Class<?>[] classList : classes) {
            System.arraycopy(classList, 0, result, index, classList.length);
            index += classList.length;
        }

        return result;
    }

    public TextCollectingVisitor(Class<?>... lineBreakNodes) {
        myLineBreakNodes = lineBreakNodes.length == 0 ? null : new HashSet<>(Arrays.asList(lineBreakNodes));

        myVisitor = new NodeVisitor(
                new VisitHandler<>(Text.class, this::visit),
                new VisitHandler<>(TextBase.class, this::visit),
                new VisitHandler<>(HtmlEntity.class, this::visit),
                new VisitHandler<>(SoftLineBreak.class, this::visit),
                new VisitHandler<>(Paragraph.class, this::visit),
                new VisitHandler<>(HardLineBreak.class, this::visit)
        )
        {
            @Override
            public void processNode(Node node, boolean withChildren, BiConsumer<Node, Visitor<Node>> processor) {
                Visitor<Node> visitor = getAction(node);
                if (visitor != null) {
                    processor.accept(node, visitor);
                } else {
                    processChildren(node, processor);
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
        BasedSequence chars = node.getChars();
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
