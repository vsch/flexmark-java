package com.vladsch.flexmark.ext.admonition.internal;

import com.vladsch.flexmark.ext.admonition.AdmonitionBlock;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
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
        return new HashSet<>(Collections.singletonList(
                new NodeFormattingHandler<>(AdmonitionBlock.class, AdmonitionNodeFormatter.this::render)
        ));
    }

    private void render(AdmonitionBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();
        markdown.append(node.getOpeningMarker()).append(' ');
        markdown.appendNonTranslating(node.getInfo());
        if (node.getTitle().isNotNull()) {
            markdown.append(' ').append('"').appendTranslating(node.getTitle()).append('"');
        }
        markdown.line();
        markdown.pushPrefix().addPrefix(RepeatedSequence.repeatOf(" ", options.contentIndent).toString());
        context.renderChildren(node);
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
