package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.ext.toc.internal.TocBlockParser;
import com.vladsch.flexmark.ext.toc.internal.TocNodeRenderer;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.ext.toc.internal.TocUtils;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for tocs
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed [TOC] text is turned into {@link TocBlock} nodes.
 * Rendered into table of contents based on the headings in the document
 */
public class TocExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // duplicated here for convenience
    public static final AttributablePart TOC_CONTENT = TocUtils.TOC_CONTENT;        // div element wrapping TOC list
    public static final AttributablePart TOC_LIST = TocUtils.TOC_LIST;              // ul/ol element of TOC list

    public static final DataKey<Integer> LEVELS = new DataKey<>("LEVELS", TocOptions.DEFAULT_LEVELS);
    public static final DataKey<Boolean> IS_TEXT_ONLY = new DataKey<>("IS_TEXT_ONLY", false);
    public static final DataKey<Boolean> IS_NUMBERED = new DataKey<>("IS_NUMBERED", false);
    public static final DataKey<TocOptions.ListType> LIST_TYPE = new DataKey<TocOptions.ListType>("LIST_TYPE", TocOptions.ListType.HIERARCHY);
    public static final DataKey<Boolean> IS_HTML = new DataKey<>("IS_HTML", false);
    public static final DataKey<Integer> TITLE_LEVEL = new DataKey<>("TITLE_LEVEL", TocOptions.DEFAULT_TITLE_LEVEL);
    public static final DataKey<String> TITLE = new DataKey<>("TITLE", (String) null);
    public static final DataKey<Boolean> AST_INCLUDE_OPTIONS = new DataKey<>("AST_INCLUDE_OPTIONS", false);
    public static final DataKey<Boolean> BLANK_LINE_SPACER = new DataKey<>("BLANK_LINE_SPACER", false);
    public static final DataKey<String> DIV_CLASS = new DataKey<>("DIV_CLASS", "");
    public static final DataKey<String> LIST_CLASS = new DataKey<>("LIST_CLASS", "");
    public static final DataKey<Boolean> CASE_SENSITIVE_TOC_TAG = new DataKey<>("CASE_SENSITIVE_TOC_TAG", true);

    // format options
    public static final DataKey<SimTocGenerateOnFormat> FORMAT_UPDATE_ON_FORMAT = new DataKey<>("FORMAT_UPDATE_ON_FORMAT", SimTocGenerateOnFormat.UPDATE);
    public static final DataKey<TocOptions> FORMAT_OPTIONS = new DataKey<>("FORMAT_OPTIONS", new DataValueFactory<TocOptions>() {
        @Override
        public TocOptions create(DataHolder options) {
            return new TocOptions(options, false);
        }
    });

    private TocExtension() {
    }

    public static Extension create() {
        return new TocExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {
        // set header id options if not already set
        if (!options.contains(HtmlRenderer.RENDER_HEADER_ID)) {
            options.set(HtmlRenderer.RENDER_HEADER_ID, true);
            options.set(HtmlRenderer.GENERATE_HEADER_ID, true);
        } else if (!options.contains(HtmlRenderer.GENERATE_HEADER_ID)) {
            options.set(HtmlRenderer.GENERATE_HEADER_ID, true);
        }
    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new TocBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new TocNodeRenderer.Factory());
        }
    }
}
