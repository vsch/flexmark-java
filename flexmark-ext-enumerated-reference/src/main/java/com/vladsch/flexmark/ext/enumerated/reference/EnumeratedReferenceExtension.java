package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.ext.enumerated.reference.internal.*;
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
 * Extension for enumerated_references
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * </p>
 * <p>
 * The parsed enumerated_reference text is turned into {@link EnumeratedReferenceText} nodes.
 * </p>
 */
public class EnumeratedReferenceExtension implements Parser.ParserExtension
        , HtmlRenderer.HtmlRendererExtension
        , Parser.ReferenceHoldingExtension
        , Formatter.FormatterExtension
{
    final public static DataKey<KeepType> ENUMERATED_REFERENCES_KEEP = new DataKey<>("ENUMERATED_REFERENCES_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    final public static DataKey<EnumeratedReferenceRepository> ENUMERATED_REFERENCES = new DataKey<>("ENUMERATED_REFERENCES", new EnumeratedReferenceRepository(null), EnumeratedReferenceRepository::new);
    final public static DataKey<EnumeratedReferences> ENUMERATED_REFERENCE_ORDINALS = new DataKey<>("ENUMERATED_REFERENCE_ORDINALS", new EnumeratedReferences(null), EnumeratedReferences::new);

    // formatter options
    final public static DataKey<ElementPlacement> ENUMERATED_REFERENCE_PLACEMENT = new DataKey<>("ENUMERATED_REFERENCE_PLACEMENT", ElementPlacement.AS_IS);
    final public static DataKey<ElementPlacementSort> ENUMERATED_REFERENCE_SORT = new DataKey<>("ENUMERATED_REFERENCE_SORT", ElementPlacementSort.AS_IS);

    private EnumeratedReferenceExtension() {
    }

    public static EnumeratedReferenceExtension create() {
        return new EnumeratedReferenceExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public boolean transferReferences(MutableDataHolder document, DataHolder included) {
        if (document.contains(ENUMERATED_REFERENCES) && included.contains(ENUMERATED_REFERENCES)) {
            return Parser.transferReferences(ENUMERATED_REFERENCES.get(document), ENUMERATED_REFERENCES.get(included), ENUMERATED_REFERENCES_KEEP.get(document) == KeepType.FIRST);
        }
        return false;
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        //parserBuilder.paragraphPreProcessorFactory(EnumeratedReferenceParagraphPreProcessor.Factory());
        parserBuilder.postProcessorFactory(new EnumeratedReferenceNodePostProcessor.Factory());
        parserBuilder.customBlockParserFactory(new EnumeratedReferenceBlockParser.Factory());
        parserBuilder.linkRefProcessorFactory(new EnumeratedReferenceLinkRefProcessor.Factory());
    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new EnumeratedReferenceNodeFormatter.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new EnumeratedReferenceNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
        }
    }
}
