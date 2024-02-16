package com.vladsch.flexmark.ext.admonition.internal;

import com.vladsch.flexmark.ext.admonition.AdmonitionBlock;
import com.vladsch.flexmark.ext.admonition.AdmonitionTitle;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class AdmonitionNodeFormatter implements NodeFormatter {
    final private AdmonitionOptions options;

    public AdmonitionNodeFormatter(DataHolder options) {
        this.options = new AdmonitionOptions(options);
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return Set.of(
                new NodeFormattingHandler<>(AdmonitionBlock.class, AdmonitionNodeFormatter.this::render),
                new NodeFormattingHandler<>(AdmonitionTitle.class, AdmonitionNodeFormatter.this::render)
        );
    }

    private void render(AdmonitionTitle node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        context.renderChildren(node);
        markdown.append(node.getClosingMarker());
    }

    private void render(AdmonitionBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();
        markdown.append(node.getOpeningMarker()).append(' ');
        markdown.appendNonTranslating(node.getInfo());
        AdmonitionTitle title = node.getTitleNode();
        if (title != null) {
            markdown.append(' ');
            context.render(title);
        }
        markdown.line();
        markdown.pushPrefix().addPrefix(RepeatedSequence.repeatOf(" ", options.contentIndent).toString());
        Node next = title == null ? node.getFirstChild() : title.getNext();
        while (next != null) {
            Node child = next;
            next = child.getNext();
            context.render(child);
        }
        markdown.blankLine();
        markdown.popPrefix();
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new AdmonitionNodeFormatter(options);
        }
    }
}
