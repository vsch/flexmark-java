package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class AttributesNodeFormatter implements NodeFormatter {

    private final AttributesOptions myOptions;

    public AttributesNodeFormatter(DataHolder options) {
        myOptions = new AttributesOptions(options);
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    // only registered if assignTextAttributes is enabled
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        HashSet<NodeFormattingHandler<?>> set = new HashSet<NodeFormattingHandler<?>>();
        set.add(new NodeFormattingHandler<AttributesNode>(AttributesNode.class, new CustomNodeFormatter<AttributesNode>() {
            @Override
            public void render(AttributesNode node, NodeFormatterContext context, MarkdownWriter markdown) {
                AttributesNodeFormatter.this.render(node, context, markdown);
            }
        }));
        return set;
    }

    void render(AttributesNode node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        markdown.appendNonTranslating(node.getText());
        markdown.append(node.getClosingMarker());
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new AttributesNodeFormatter(options);
        }
    }
}
