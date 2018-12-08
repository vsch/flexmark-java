package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.footnotes.internal.*;
import com.vladsch.flexmark.formatter.internal.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for footnotes
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed footnote references in text regions are turned into {@link Footnote} nodes.
 * The parsed footnote definitions are turned into {@link FootnoteBlock} nodes.
 * </p>
 */
public class FootnoteExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Parser.ReferenceHoldingExtension, Formatter.FormatterExtension {
    public static final DataKey<KeepType> FOOTNOTES_KEEP = new DataKey<>("FOOTNOTES_KEEP", KeepType.FIRST);

    public static final DataKey<FootnoteRepository> FOOTNOTES = new DataKey<>("FOOTNOTES", new DataValueFactory<FootnoteRepository>() {
        @Override
        public FootnoteRepository create(DataHolder options) {
            return new FootnoteRepository(options);
        }
    });
    public static final DataKey<String> FOOTNOTE_REF_PREFIX = new DataKey<>("FOOTNOTE_REF_PREFIX", "");
    public static final DataKey<String> FOOTNOTE_REF_SUFFIX = new DataKey<>("FOOTNOTE_REF_SUFFIX", "");
    public static final DataKey<String> FOOTNOTE_BACK_REF_STRING = new DataKey<>("FOOTNOTE_BACK_REF_STRING", "&#8617;");
    public static final DataKey<String> FOOTNOTE_LINK_REF_CLASS = new DataKey<>("FOOTNOTE_LINK_REF_CLASS", "footnote-ref");
    public static final DataKey<String> FOOTNOTE_BACK_LINK_REF_CLASS = new DataKey<>("FOOTNOTE_BACK_LINK_REF_CLASS", "footnote-backref");

    // formatter options
    public static final DataKey<ElementPlacement> FOOTNOTE_PLACEMENT = new DataKey<>("FOOTNOTE_PLACEMENT", ElementPlacement.AS_IS);
    public static final DataKey<ElementPlacementSort> FOOTNOTE_SORT = new DataKey<>("FOOTNOTE_SORT", ElementPlacementSort.AS_IS);

    private FootnoteExtension() {
    }

    public static Extension create() {
        return new FootnoteExtension();
    }

    @Override
    public void extend(final Formatter.Builder builder) {
        builder.nodeFormatterFactory(new FootnoteNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public boolean transferReferences(final MutableDataHolder document, final DataHolder included) {
        if (document.contains(FOOTNOTES) && included.contains(FOOTNOTES)) {
            return Parser.transferReferences(FOOTNOTES.getFrom(document), FOOTNOTES.getFrom(included), FOOTNOTES_KEEP.getFrom(document) == KeepType.FIRST);
        }
        return false;
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new FootnoteBlockParser.Factory());
        parserBuilder.linkRefProcessorFactory(new FootnoteLinkRefProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new FootnoteNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
        }
    }
}
