package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ext.attributes.internal.AttributesNodeFormatter;
import com.vladsch.flexmark.ext.enumerated.reference.*;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EnumeratedReferenceNodeFormatter extends NodeRepositoryFormatter<EnumeratedReferenceRepository, EnumeratedReferenceBlock, EnumeratedReferenceText> {

    final private EnumeratedReferenceFormatOptions options;

    public EnumeratedReferenceNodeFormatter(DataHolder options) {
        super(options, null, AttributesNodeFormatter.ATTRIBUTE_UNIQUIFICATION_CATEGORY_MAP);
        this.options = new EnumeratedReferenceFormatOptions(options);
    }

    @Override
    public EnumeratedReferenceRepository getRepository(DataHolder options) {
        return EnumeratedReferenceExtension.ENUMERATED_REFERENCES.get(options);
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
    protected void renderReferenceBlock(EnumeratedReferenceBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine().append("[@").appendNonTranslating(node.getText()).append("]: ");
        markdown.pushPrefix().addPrefix("    ", true);
        context.renderChildren(node);
        markdown.popPrefix();
        markdown.blankLine();
    }

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(EnumeratedReferenceText.class, EnumeratedReferenceNodeFormatter.this::render),
                new NodeFormattingHandler<>(EnumeratedReferenceLink.class, EnumeratedReferenceNodeFormatter.this::render),
                new NodeFormattingHandler<>(EnumeratedReferenceBlock.class, EnumeratedReferenceNodeFormatter.this::render)
        ));
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        if (options.enumeratedReferencePlacement.isNoChange() || !options.enumeratedReferenceSort.isUnused()) return null;
        // noinspection ArraysAsListWithZeroOrOneArgument
        return new HashSet<>(Arrays.asList(
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

    private void render(EnumeratedReferenceText node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append("[#");
        if (context.isTransformingText()) {
            renderReferenceText(node.getText(), context, markdown);
        } else {
            context.renderChildren(node);
        }
        markdown.append("]");
    }

    private void render(EnumeratedReferenceLink node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append("[@");
        if (context.isTransformingText()) {
            renderReferenceText(node.getText(), context, markdown);
        } else {
            context.renderChildren(node);
        }
        markdown.append("]");
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new EnumeratedReferenceNodeFormatter(options);
        }

        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            // run before attributes formatter so categories are uniquified first
            // renderers are sorted in reverse order for backward compatibility
            Set<Class<?>> aSet = new HashSet<>();
            aSet.add(AttributesNodeFormatter.Factory.class);
            return aSet;
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }
    }
}
