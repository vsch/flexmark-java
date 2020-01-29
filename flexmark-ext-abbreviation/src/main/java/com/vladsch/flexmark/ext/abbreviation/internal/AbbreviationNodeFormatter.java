package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AbbreviationNodeFormatter extends NodeRepositoryFormatter<AbbreviationRepository, AbbreviationBlock, Abbreviation> {
    final public static DataKey<Map<String, String>> ABBREVIATION_TRANSLATION_MAP = new DataKey<>("ABBREVIATION_TRANSLATION_MAP", new HashMap<>()); //
    final public static DataKey<Map<String, String>> ABBREVIATION_UNIQUIFICATION_MAP = new DataKey<>("ABBREVIATION_UNIQUIFICATION_MAP", new HashMap<>()); // uniquified references
    final private AbbreviationFormatOptions options;
    final private boolean transformUnderscores;
    final private boolean makeMergedAbbreviationsUnique;

    public AbbreviationNodeFormatter(DataHolder options) {
        super(options, ABBREVIATION_TRANSLATION_MAP, ABBREVIATION_UNIQUIFICATION_MAP);
        this.options = new AbbreviationFormatOptions(options);

        String transformedId = String.format(Formatter.TRANSLATION_ID_FORMAT.get(options), 1);
        transformUnderscores = transformedId.startsWith("_") && transformedId.endsWith("_");
        makeMergedAbbreviationsUnique = AbbreviationExtension.MAKE_MERGED_ABBREVIATIONS_UNIQUE.get(options);
    }

    @Override
    protected boolean makeReferencesUnique() {
        return makeMergedAbbreviationsUnique;
    }

    @Override
    public AbbreviationRepository getRepository(DataHolder options) {
        return AbbreviationExtension.ABBREVIATIONS.get(options);
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

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(Abbreviation.class, AbbreviationNodeFormatter.this::render),
                new NodeFormattingHandler<>(AbbreviationBlock.class, AbbreviationNodeFormatter.this::render)
        ));
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        if (options.abbreviationsPlacement.isNoChange() || !options.abbreviationsSort.isUnused()) return null;
        // noinspection ArraysAsListWithZeroOrOneArgument
        return new HashSet<>(Arrays.asList(
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
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new AbbreviationNodeFormatter(options);
        }
    }
}
