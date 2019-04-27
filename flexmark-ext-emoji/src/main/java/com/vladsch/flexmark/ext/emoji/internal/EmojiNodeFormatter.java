package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class EmojiNodeFormatter implements NodeFormatter {

    public EmojiNodeFormatter(DataHolder options) {

    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    // only registered if assignTextAttributes is enabled
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        HashSet<NodeFormattingHandler<?>> set = new HashSet<NodeFormattingHandler<?>>();
        set.add(new NodeFormattingHandler<Emoji>(Emoji.class, new CustomNodeFormatter<Emoji>() {
            @Override
            public void render(Emoji node, NodeFormatterContext context, MarkdownWriter markdown) {
                EmojiNodeFormatter.this.render(node, context, markdown);
            }
        }));
        return set;
    }

    void render(Emoji node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        markdown.appendNonTranslating(node.getText());
        markdown.append(node.getClosingMarker());
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new EmojiNodeFormatter(options);
        }
    }
}
