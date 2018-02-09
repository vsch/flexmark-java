package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceBlock;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceLink;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceText;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EnumeratedReferenceNodeFormatter extends NodeRepositoryFormatter<EnumeratedReferenceRepository, EnumeratedReferenceBlock, EnumeratedReferenceText> {
    private final EnumeratedReferenceFormatOptions options;

    public EnumeratedReferenceNodeFormatter(DataHolder options) {
        super(options);
        this.options = new EnumeratedReferenceFormatOptions(options);
    }

    @Override
    public EnumeratedReferenceRepository getRepository(final DataHolder options) {
        return EnumeratedReferenceExtension.ENUMERATED_REFERENCES.getFrom(options);
    }

    @Override
    public ElementPlacement getReferencePlacement() {
        return options.enumeratedReferencePlacement;
    }

    @Override
    public ElementPlacementSort getReferenceSort() {
        return options.enumeratedReferenceSort;
    }

    @Override
    public void renderReferenceBlock(final EnumeratedReferenceBlock node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.blankLine().append("[@").append(node.getText()).append("]: ");
        markdown.pushPrefix().addPrefix("    ");
        context.renderChildren(node);
        markdown.popPrefix();
        markdown.blankLine();
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<?>>(Arrays.asList(
                new NodeFormattingHandler<EnumeratedReferenceText>(EnumeratedReferenceText.class, new CustomNodeFormatter<EnumeratedReferenceText>() {
                    @Override
                    public void render(EnumeratedReferenceText node, NodeFormatterContext context, MarkdownWriter markdown) {
                        EnumeratedReferenceNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<EnumeratedReferenceLink>(EnumeratedReferenceLink.class, new CustomNodeFormatter<EnumeratedReferenceLink>() {
                    @Override
                    public void render(EnumeratedReferenceLink node, NodeFormatterContext context, MarkdownWriter markdown) {
                        EnumeratedReferenceNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<EnumeratedReferenceBlock>(EnumeratedReferenceBlock.class, new CustomNodeFormatter<EnumeratedReferenceBlock>() {
                    @Override
                    public void render(EnumeratedReferenceBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        EnumeratedReferenceNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        if (options.enumeratedReferencePlacement != ElementPlacement.AS_IS && options.enumeratedReferenceSort != ElementPlacementSort.SORT_UNUSED_LAST) return null;
        //noinspection unchecked,ArraysAsListWithZeroOrOneArgument
        return new HashSet<Class<?>>(Arrays.asList(
                EnumeratedReferenceBlock.class
        ));
    }

    private void render(EnumeratedReferenceBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderReference(node, context, markdown);
    }

    private void render(EnumeratedReferenceText node, NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.append("[#");
        context.renderChildren(node);
        markdown.append("]");
    }

    private void render(EnumeratedReferenceLink node, NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.append("[@");
        context.renderChildren(node);
        markdown.append("]");
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new EnumeratedReferenceNodeFormatter(options);
        }
    }
}
