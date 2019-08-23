package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;

import java.util.*;

public class AbbreviationNodeFormatter extends NodeRepositoryFormatter<AbbreviationRepository, AbbreviationBlock, Abbreviation> {
    public static final DataKey<Map<String, String>> ABBREVIATION_TRANSLATION_MAP = new DataKey<Map<String, String>>("ABBREVIATION_TRANSLATION_MAP", new HashMap<String, String>()); //
    public static final DataKey<Map<String, String>> ABBREVIATION_UNIQUIFICATION_MAP = new DataKey<Map<String, String>>("ABBREVIATION_UNIQUIFICATION_MAP", new HashMap<String, String>()); // uniquified references
    private final FormatOptions options;
    private final boolean transformUnderscores;
    private final boolean makeMergedAbbreviationsUnique;

    public AbbreviationNodeFormatter(DataHolder options) {
        super(options, ABBREVIATION_TRANSLATION_MAP, ABBREVIATION_UNIQUIFICATION_MAP);
        this.options = new FormatOptions(options);

        String transformedId = String.format(Formatter.TRANSLATION_ID_FORMAT.getFrom(options), 1);
        transformUnderscores = transformedId.startsWith("_") && transformedId.endsWith("_");
        makeMergedAbbreviationsUnique = AbbreviationExtension.MAKE_MERGED_ABBREVIATIONS_UNIQUE.getFrom(options);
    }

    @Override
    protected boolean makeReferencesUnique() {
        return makeMergedAbbreviationsUnique;
    }

    @Override
    public AbbreviationRepository getRepository(DataHolder options) {
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
    public String modifyTransformedReference(String transformedText, NodeFormatterContext context) {
        if (transformUnderscores && context.isTransformingText()) {
            if (transformedText.startsWith("-") && transformedText.endsWith("-")) {
                transformedText = "_" + transformedText.substring(1, transformedText.length() - 1) + "_";
            } else if (transformedText.startsWith("_") && transformedText.endsWith("_")) {
                transformedText = "-" + transformedText.substring(1, transformedText.length() - 1) + "-";
            }
        }

        return transformedText;
    }

    @Override
    public void renderReferenceBlock(AbbreviationBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        markdown.append(transformReferenceId(node.getText().toString(), context));
        markdown.append(node.getClosingMarker()).append(' ');
        markdown.appendTranslating(node.getAbbreviation()).line();
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<? extends Node>>(Arrays.asList(
                new NodeFormattingHandler<Abbreviation>(Abbreviation.class, new CustomNodeFormatter<Abbreviation>() {
                    @Override
                    public void render(Abbreviation node, NodeFormatterContext context, MarkdownWriter markdown) {
                        AbbreviationNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<AbbreviationBlock>(AbbreviationBlock.class, new CustomNodeFormatter<AbbreviationBlock>() {
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
        if (context.isTransformingText()) {
            String referenceId = transformReferenceId(node.getChars().toString(), context);
            markdown.append(referenceId);
        } else {
            markdown.append(node.getChars());
        }
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(DataHolder options) {
            return new AbbreviationNodeFormatter(options);
        }
    }
}
