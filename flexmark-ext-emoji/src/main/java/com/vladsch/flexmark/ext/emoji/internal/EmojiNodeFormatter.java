package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class EmojiNodeFormatter implements NodeFormatter {

    public EmojiNodeFormatter(DataHolder options) {

    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    // only registered if assignTextAttributes is enabled
    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        HashSet<NodeFormattingHandler<?>> set = new HashSet<>();
        set.add(new NodeFormattingHandler<>(Emoji.class, EmojiNodeFormatter.this::render));
        return set;
    }

    void render(Emoji node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        markdown.appendNonTranslating(node.getText());
        markdown.append(node.getClosingMarker());
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new EmojiNodeFormatter(options);
        }
    }
}
