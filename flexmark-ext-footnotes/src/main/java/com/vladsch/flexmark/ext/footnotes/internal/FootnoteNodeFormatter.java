package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;

import java.util.*;

public class FootnoteNodeFormatter extends NodeRepositoryFormatter<FootnoteRepository, FootnoteBlock, Footnote> {
    public static final DataKey<Map<String, String>> FOOTNOTE_TRANSLATION_MAP = new DataKey<Map<String, String>>("FOOTNOTE_TRANSLATION_MAP", new HashMap<String, String>()); // assign attributes to text if previous is not a space
    private final FootnoteFormatOptions options;

    public FootnoteNodeFormatter(DataHolder options) {
        super(options, FOOTNOTE_TRANSLATION_MAP);
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
        markdown.blankLine().append("[^");
        markdown.append(transformReferenceId(node.getText().toString(), context));
        markdown.append("]: ");
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

    private void render(final Footnote node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.append("[^");
        if (context.isTransformingText()) {
            final String referenceId = transformReferenceId(node.getText().toString(), context);
            context.nonTranslatingSpan(new TranslatingSpanRender() {
                @Override
                public void render(final NodeFormatterContext context, final MarkdownWriter markdown) {
                    markdown.append(referenceId);
                }
            });
        } else {
            markdown.append(node.getText());
        }
        markdown.append("]");
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new FootnoteNodeFormatter(options);
        }
    }
}
