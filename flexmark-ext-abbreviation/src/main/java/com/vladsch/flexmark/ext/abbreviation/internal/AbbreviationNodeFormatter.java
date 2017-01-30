package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AbbreviationNodeFormatter extends NodeRepositoryFormatter<AbbreviationRepository, AbbreviationBlock, Abbreviation> {
    private final FormatOptions options;

    public AbbreviationNodeFormatter(DataHolder options) {
        super(options);
        this.options = new FormatOptions(options);
    }

    @Override
    public AbbreviationRepository getRepository(final DataHolder options) {
        return AbbreviationExtension.ABBREVIATIONS.getFrom(options);
    }

    @Override
    public ElementPlacement getReferencePlacement() {
        return options.abbreviationsPlacement;
    }

    @Override
    public ElementPlacementSort getReferenceSort() {
        return options.abbreviationsSort;
    }

    @Override
    public void renderReferenceBlock(final AbbreviationBlock node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker()).append(node.getText()).append(node.getClosingMarker()).append(' ');
        markdown.append(node.getAbbreviation()).line();
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(Abbreviation.class, new CustomNodeFormatter<Abbreviation>() {
                    @Override
                    public void render(Abbreviation node, NodeFormatterContext context, MarkdownWriter markdown) {
                        AbbreviationNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(AbbreviationBlock.class, new CustomNodeFormatter<AbbreviationBlock>() {
                    @Override
                    public void render(AbbreviationBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        AbbreviationNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        if (options.abbreviationsPlacement != ElementPlacement.AS_IS && options.abbreviationsSort != ElementPlacementSort.SORT_UNUSED_LAST) return null;
        //noinspection unchecked,ArraysAsListWithZeroOrOneArgument
        return new HashSet<Class<?>>(Arrays.asList(
                Abbreviation.class
        ));
    }

    private void render(AbbreviationBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderReference(node, context, markdown);
    }

    private void render(Abbreviation node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getChars());
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new AbbreviationNodeFormatter(options);
        }
    }
}
