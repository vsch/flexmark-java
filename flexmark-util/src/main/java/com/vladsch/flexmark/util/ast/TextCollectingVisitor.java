package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class TextCollectingVisitor {
    private final NodeVisitor myVisitor;
    SpaceInsertingSequenceBuilder out;
    int flags;

    public TextCollectingVisitor() {
        myVisitor = new NodeVisitor() {
            @Override
            public void processNode(@NotNull Node node, boolean withChildren, @NotNull BiConsumer<Node, Visitor<Node>> processor) {
                if (!node.isOrDescendantOfType(DoNotCollectText.class)) {
                    if (node instanceof TextContainer) {
                        out.setLastNode(node);
                        if (((TextContainer) node).collectText(out, flags)) {
                            if (node instanceof BlankLineBreakNode && out.isNotEmpty()) {
                                out.appendEol();
                            }
                            processChildren(node, processor);
                            if (node instanceof LineBreakNode && out.needEol()) {
                                out.appendEol();
                            }
                        }
                    } else {
                        out.setLastNode(node);
                        if (node instanceof BlankLineBreakNode && out.isNotEmpty()) {
                            out.appendEol();
                        }
                        processChildren(node, processor);
                        if (node instanceof LineBreakNode && out.needEol()) {
                            out.appendEol();
                        }
                    }
                }
            }
        };
    }

    public String getText() {
        return out.toString();
    }

    public BasedSequence getSequence() {
        return out.toSequence();
    }

    public void collect(Node node) {
        collect(node, 0);
    }

    public String collectAndGetText(Node node) {
        return collectAndGetText(node, 0);
    }

    public BasedSequence collectAndGetSequence(Node node) {
        return collectAndGetSequence(node, 0);
    }

    public void collect(Node node, int flags) {
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(node.getChars());
        out = SpaceInsertingSequenceBuilder.emptyBuilder(node.getChars());

        this.flags = flags;
        myVisitor.visit(node);
    }

    public String collectAndGetText(Node node, int flags) {
        collect(node, flags);
        return out.toString();
    }

    public BasedSequence collectAndGetSequence(Node node, int flags) {
        collect(node, flags);
        return out.toSequence();
    }
}
