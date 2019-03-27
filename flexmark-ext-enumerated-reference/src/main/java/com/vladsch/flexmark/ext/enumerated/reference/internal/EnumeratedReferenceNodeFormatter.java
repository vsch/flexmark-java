package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ext.attributes.internal.AttributesNodeFormatter;
import com.vladsch.flexmark.ext.enumerated.reference.*;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EnumeratedReferenceNodeFormatter extends NodeRepositoryFormatter<EnumeratedReferenceRepository, EnumeratedReferenceBlock, EnumeratedReferenceText> {
    private final EnumeratedReferenceFormatOptions options;

    public EnumeratedReferenceNodeFormatter(DataHolder options) {
        super(options, null);
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
        markdown.blankLine().append("[@").appendNonTranslating(node.getText()).append("]: ");
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

    private static void renderReferenceText(BasedSequence text, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!text.isEmpty()) {
            BasedSequence valueChars = text;
            int pos = valueChars.indexOf(':');
            String category;
            String id = null;
            if (pos == -1) {
                category = text.toString();
            } else {
                category = valueChars.subSequence(0, pos).toString();
                id = valueChars.subSequence(pos + 1).toString();
            }

            String encoded = AttributesNodeFormatter.getEncodedIdAttribute(category, id, context, markdown);
            markdown.append(encoded);
        }
    }

    private void render(final EnumeratedReferenceText node, NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.append("[#");
        if (context.isTransformingText()) {
            renderReferenceText(node.getText(), context, markdown);
        } else {
            context.renderChildren(node);
        }
        markdown.append("]");
    }

    private void render(final EnumeratedReferenceLink node, NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.append("[@");
        if (context.isTransformingText()) {
            if (context.isTransformingText()) {
                renderReferenceText(node.getText(), context, markdown);
            } else {
                context.renderChildren(node);
            }
        } else {
            context.renderChildren(node);
        }
        markdown.append("]");
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new EnumeratedReferenceNodeFormatter(options);
        }
    }
}
