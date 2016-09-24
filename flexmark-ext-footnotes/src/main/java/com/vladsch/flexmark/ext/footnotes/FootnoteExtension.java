package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.footnotes.internal.FootnoteBlockParser;
import com.vladsch.flexmark.ext.footnotes.internal.FootnoteLinkRefProcessor;
import com.vladsch.flexmark.ext.footnotes.internal.FootnoteNodeRenderer;
import com.vladsch.flexmark.ext.footnotes.internal.FootnoteRepository;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.DataKey;

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
public class FootnoteExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public final static DataKey<FootnoteRepository> FOOTNOTES = new DataKey<>("FOOTNOTES", FootnoteRepository::new);
    public final static DataKey<KeepType> FOOTNOTES_KEEP = new DataKey<>("FOOTNOTES_KEEP", KeepType.FIRST);
    final public static DataKey<String> FOOTNOTE_REF_PREFIX = new DataKey<String>("FOOTNOTE_REF_PREFIX", "");
    final public static DataKey<String> FOOTNOTE_REF_SUFFIX = new DataKey<String>("FOOTNOTE_REF_SUFFIX", "");
    final public static DataKey<String> FOOTNOTE_BACK_REF_STRING = new DataKey<String>("FOOTNOTE_BACK_REF_STRING", "&#8617;");
    final public static DataKey<String> FOOTNOTE_LINK_REF_CLASS = new DataKey<String>("FOOTNOTE_LINK_REF_CLASS", "footnote-ref");
    final public static DataKey<String> FOOTNOTE_BACK_LINK_REF_CLASS = new DataKey<String>("FOOTNOTE_BACK_LINK_REF_CLASS", "footnote-backref");

    private FootnoteExtension() {
    }

    public static Extension create() {
        return new FootnoteExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new FootnoteBlockParser.Factory());
        parserBuilder.linkRefProcessorFactory(new FootnoteLinkRefProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("JIRA")) {
        } else if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(FootnoteNodeRenderer::new);
        }
    }
}
