package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.ext.abbreviation.internal.*;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for adding abbreviations to markdown
 * <p>
 * Create it with {@link #create()} then configure builders
 * <p>
 * The parsed abbreviations are turned into abbr tags by default or a links as an option by setting the {@link AbbreviationExtension#USE_LINKS} key to true in option used to create the {@link Parser.Builder} via {@code Parser.builder(options)}
 */
public class AbbreviationExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Parser.ReferenceHoldingExtension, Formatter.FormatterExtension {
    /**
     * A {@link DataKey} that is used to set the behavior of the abbreviations repository when duplicates are defined. {@link KeepType}
     */
    final public static DataKey<KeepType> ABBREVIATIONS_KEEP = new DataKey<>("ABBREVIATIONS_KEEP", KeepType.FIRST);

    /**
     * A {@link DataKey} that is used to get the document's Node repository holding all the abbreviations defined in the current document.
     */
    final public static DataKey<AbbreviationRepository> ABBREVIATIONS = new DataKey<>("ABBREVIATIONS", new AbbreviationRepository(null), AbbreviationRepository::new);

    /**
     * A {@link DataKey} that is used to set the use links option when true, default is false and abbr tag will be used in the rendered HTML.
     */
    final public static DataKey<Boolean> USE_LINKS = new DataKey<>("USE_LINKS", false);

    // format options
    final public static DataKey<ElementPlacement> ABBREVIATIONS_PLACEMENT = new DataKey<>("ABBREVIATIONS_PLACEMENT", ElementPlacement.AS_IS);
    final public static DataKey<ElementPlacementSort> ABBREVIATIONS_SORT = new DataKey<>("ABBREVIATIONS_SORT", ElementPlacementSort.AS_IS);

    final public static DataKey<Boolean> MAKE_MERGED_ABBREVIATIONS_UNIQUE = new DataKey<>("MERGE_MAKE_ABBREVIATIONS_UNIQUE", false);

    public static AbbreviationExtension create() {
        return new AbbreviationExtension();
    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new AbbreviationNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public boolean transferReferences(MutableDataHolder document, DataHolder included) {
        // abbreviations cannot be transferred except before parsing the document
        //if (document.contains(ABBREVIATIONS) && included.contains(ABBREVIATIONS)) {
        //    if (Parser.transferReferences(ABBREVIATIONS.getFrom(document), ABBREVIATIONS.getFrom(included), ABBREVIATIONS_KEEP.getFrom(document) == KeepType.FIRST)) {
        //        // reset abbreviations optimization
        //        document.set(RECOMPUTE_ABBREVIATIONS_MAP, true);
        //    }
        //}
        return false;
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new AbbreviationBlockParser.Factory());
        //parserBuilder.paragraphPreProcessorFactory(AbbreviationParagraphPreProcessor.Factory());
        parserBuilder.postProcessorFactory(new AbbreviationNodePostProcessor.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new AbbreviationNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
        }
    }
}
