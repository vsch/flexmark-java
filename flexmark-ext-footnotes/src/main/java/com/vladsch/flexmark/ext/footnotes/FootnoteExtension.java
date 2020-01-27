package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.ext.footnotes.internal.*;
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
 * Extension for footnotes
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed footnote references in text regions are turned into {@link Footnote} nodes.
 * The parsed footnote definitions are turned into {@link FootnoteBlock} nodes.
 */
public class FootnoteExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Parser.ReferenceHoldingExtension, Formatter.FormatterExtension {
    final public static DataKey<KeepType> FOOTNOTES_KEEP = new DataKey<>("FOOTNOTES_KEEP", KeepType.FIRST);

    final public static DataKey<FootnoteRepository> FOOTNOTES = new DataKey<>("FOOTNOTES", new FootnoteRepository(null), FootnoteRepository::new);
    final public static DataKey<String> FOOTNOTE_REF_PREFIX = new DataKey<>("FOOTNOTE_REF_PREFIX", "");
    final public static DataKey<String> FOOTNOTE_REF_SUFFIX = new DataKey<>("FOOTNOTE_REF_SUFFIX", "");
    final public static DataKey<String> FOOTNOTE_BACK_REF_STRING = new DataKey<>("FOOTNOTE_BACK_REF_STRING", "&#8617;");
    final public static DataKey<String> FOOTNOTE_LINK_REF_CLASS = new DataKey<>("FOOTNOTE_LINK_REF_CLASS", "footnote-ref");
    final public static DataKey<String> FOOTNOTE_BACK_LINK_REF_CLASS = new DataKey<>("FOOTNOTE_BACK_LINK_REF_CLASS", "footnote-backref");

    // formatter options
    final public static DataKey<ElementPlacement> FOOTNOTE_PLACEMENT = new DataKey<>("FOOTNOTE_PLACEMENT", ElementPlacement.AS_IS);
    final public static DataKey<ElementPlacementSort> FOOTNOTE_SORT = new DataKey<>("FOOTNOTE_SORT", ElementPlacementSort.AS_IS);

    private FootnoteExtension() {
    }

    public static FootnoteExtension create() {
        return new FootnoteExtension();
    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new FootnoteNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public boolean transferReferences(MutableDataHolder document, DataHolder included) {
        if (document.contains(FOOTNOTES) && included.contains(FOOTNOTES)) {
            return Parser.transferReferences(FOOTNOTES.get(document), FOOTNOTES.get(included), FOOTNOTES_KEEP.get(document) == KeepType.FIRST);
        }
        return false;
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new FootnoteBlockParser.Factory());
        parserBuilder.linkRefProcessorFactory(new FootnoteLinkRefProcessor.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new FootnoteNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
        }
    }
}
