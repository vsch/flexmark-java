package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FootnoteNodeFormatter extends NodeRepositoryFormatter<FootnoteRepository, FootnoteBlock, Footnote> {
    private final FootnoteFormatOptions options;

    public FootnoteNodeFormatter(DataHolder options) {
        super(options);
        this.options = new FootnoteFormatOptions(options);
    }

    @Override
    public FootnoteRepository getRepository(final DataHolder options) {
        return FootnoteExtension.FOOTNOTES.getFrom(options);
    }

    @Override
    public ElementPlacement getReferencePlacement() {
        return options.footnotePlacement;
    }

    @Override
    public ElementPlacementSort getReferenceSort() {
        return options.footnoteSort;
    }

    @Override
    public void renderReferenceBlock(final FootnoteBlock node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.blankLine().append("[^").append(node.getText()).append("]: ");
        markdown.pushPrefix().addPrefix("    ");
        context.renderChildren(node);
        markdown.popPrefix();
        markdown.blankLine();
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<?>>(Arrays.asList(
                new NodeFormattingHandler<Footnote>(Footnote.class, new CustomNodeFormatter<Footnote>() {
                    @Override
                    public void render(Footnote node, NodeFormatterContext context, MarkdownWriter markdown) {
                        FootnoteNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<FootnoteBlock>(FootnoteBlock.class, new CustomNodeFormatter<FootnoteBlock>() {
                    @Override
                    public void render(FootnoteBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        FootnoteNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        if (options.footnotePlacement != ElementPlacement.AS_IS && options.footnoteSort != ElementPlacementSort.SORT_UNUSED_LAST) return null;
        //noinspection unchecked,ArraysAsListWithZeroOrOneArgument
        return new HashSet<Class<?>>(Arrays.asList(
                Footnote.class
        ));
    }

    private void render(FootnoteBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderReference(node, context, markdown);
    }

    private void render(Footnote node, NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.append("[^");
        context.renderChildren(node);
        markdown.append("]");
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new FootnoteNodeFormatter(options);
        }
    }
}
