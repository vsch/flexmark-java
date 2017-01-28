package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ext.definition.DefinitionItem;
import com.vladsch.flexmark.ext.definition.DefinitionList;
import com.vladsch.flexmark.ext.definition.DefinitionTerm;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequence;
import com.vladsch.flexmark.util.sequence.SubSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DefinitionNodeFormatter implements NodeFormatter {
    private final FormatOptions options;

    public DefinitionNodeFormatter(DataHolder options) {
        this.options = new FormatOptions(options);
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<?>>(Arrays.asList(
                new NodeFormattingHandler<>(DefinitionList.class, new CustomNodeFormatter<DefinitionList>() {
                    @Override
                    public void render(DefinitionList node, NodeFormatterContext context, MarkdownWriter markdown) {
                        DefinitionNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(DefinitionTerm.class, new CustomNodeFormatter<DefinitionTerm>() {
                    @Override
                    public void render(DefinitionTerm node, NodeFormatterContext context, MarkdownWriter markdown) {
                        DefinitionNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(DefinitionItem.class, new CustomNodeFormatter<DefinitionItem>() {
                    @Override
                    public void render(DefinitionItem node, NodeFormatterContext context, MarkdownWriter markdown) {
                        DefinitionNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    private void render(DefinitionList node, NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
    }

    private void render(final DefinitionTerm node, final NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
    }

    private void render(final DefinitionItem node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        BasedSequence openMarkerChars = node.getChars().prefixOf(node.getFirstChild().getChars());
        BasedSequence openMarker = openMarkerChars.subSequence(0,1);
        BasedSequence openMarkerSpaces = openMarkerChars.subSequence(1);

        if (options.markerSpaces >= 1 && openMarkerSpaces.length() != options.markerSpaces) {
            openMarkerSpaces = SubSequence.of(RepeatedCharSequence.of(' ', options.markerSpaces));
        }

        switch (options.markerType) {
            case ANY: break;
            case COLON: openMarker = SubSequence.of(":"); break;
            case TILDE: openMarker = SubSequence.of("~"); break;
        }

        markdown.line().append(openMarker).append(openMarkerSpaces);
        context.renderChildren(node);
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new DefinitionNodeFormatter(options);
        }
    }
}
