package com.vladsch.flexmark.ext.jekyll.tag.internal;

import com.vladsch.flexmark.ext.jekyll.tag.JekyllTag;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagBlock;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JekyllTagNodeFormatter implements PhasedNodeFormatter {
    private boolean embedIncludedContent;

    public JekyllTagNodeFormatter(DataHolder options) {
    }

    @Override
    public @Nullable Set<FormattingPhase> getFormattingPhases() {
        return Collections.singleton(FormattingPhase.COLLECT);
    }

    @Override
    public void renderDocument(@NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown, @NotNull Document document, @NotNull FormattingPhase phase) {
        this.embedIncludedContent = JekyllTagExtension.EMBED_INCLUDED_CONTENT.get(document);
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(JekyllTagBlock.class, JekyllTagNodeFormatter.this::render),
                new NodeFormattingHandler<>(JekyllTag.class, JekyllTagNodeFormatter.this::render)
        ));
    }

    private void render(JekyllTagBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (embedIncludedContent) {
            // remove jekyll tag node and just leave the included content
            Node child = node.getFirstChild();

            if (child != null) child = child.getNextAnyNot(JekyllTag.class);

            while (child != null) {
                Node next = child.getNextAnyNot(JekyllTag.class);
                context.render(child);
                child = next;
            }
        } else {
            Node child = node.getFirstChild();
            while (child != null) {
                Node next = child.getNextAny(JekyllTag.class);
                context.render(child);
                child = next;
            }
        }
    }

    private void render(JekyllTag node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!(node.getParent() instanceof JekyllTagBlock)) {
            Node prev = node.getPrevious();
            if (prev != null) {
                BasedSequence chars = prev.getChars();
                markdown.pushOptions().preserveSpaces()
                        .append(chars.baseSubSequence(chars.getEndOffset(), node.getStartOffset()))
                        .popOptions();
            } else {
                int startLine = node.getBaseSequence().startOfLine(node.getStartOffset());
                if (startLine < node.getStartOffset()) {
                    BasedSequence chars = node.baseSubSequence(startLine, node.getStartOffset());
                    markdown.pushOptions().preserveSpaces()
                            .append(chars)
                            .popOptions();
                }
            }
        }

        markdown.append(node.getChars());
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new JekyllTagNodeFormatter(options);
        }
    }
}
