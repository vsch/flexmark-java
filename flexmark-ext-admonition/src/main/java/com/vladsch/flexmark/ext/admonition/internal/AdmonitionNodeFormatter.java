package com.vladsch.flexmark.ext.admonition.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.admonition.AdmonitionBlock;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AdmonitionNodeFormatter implements NodeFormatter {
    private final AdmonitionOptions options;

    public AdmonitionNodeFormatter(DataHolder options) {
        this.options = new AdmonitionOptions(options);
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<? extends Node>>(Arrays.asList(
                new NodeFormattingHandler<AdmonitionBlock>(AdmonitionBlock.class, new CustomNodeFormatter<AdmonitionBlock>() {
                    @Override
                    public void render(AdmonitionBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        AdmonitionNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    private void render(final AdmonitionBlock node, final NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();
        markdown.append(node.getOpeningMarker()).append(' ');
        markdown.appendNonTranslating(node.getInfo());
        if (node.getTitle().isNotNull()) {
            markdown.append(' ').append('"').appendTranslating(node.getTitle()).append('"');
        }
        markdown.line();
        markdown.pushPrefix().addPrefix(RepeatedCharSequence.of(" ", options.contentIndent).toString());
        context.renderChildren(node);
        markdown.blankLine();
        markdown.popPrefix();
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new AdmonitionNodeFormatter(options);
        }
    }
}
