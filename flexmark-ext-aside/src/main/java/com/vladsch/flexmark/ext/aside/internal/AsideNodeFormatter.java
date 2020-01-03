package com.vladsch.flexmark.ext.aside.internal;

import com.vladsch.flexmark.ext.aside.AsideBlock;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class AsideNodeFormatter implements NodeFormatter {

    public AsideNodeFormatter(DataHolder options) {

    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Override
    public char getBlockQuoteLikePrefixChar() {
        return '|';
    }

    // only registered if assignTextAttributes is enabled
    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        HashSet<NodeFormattingHandler<?>> set = new HashSet<>();
        set.add(new NodeFormattingHandler<>(AsideBlock.class, AsideNodeFormatter.this::render));
        return set;
    }

    private void render(AsideBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        FormatterUtils.renderBlockQuoteLike(node, context, markdown);
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new AsideNodeFormatter(options);
        }
    }
}
