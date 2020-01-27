package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.ext.toc.internal.TocBlockParser;
import com.vladsch.flexmark.ext.toc.internal.TocNodeRenderer;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;
import org.jetbrains.annotations.NotNull;

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
    final public static AttributablePart TOC_CONTENT = TocUtils.TOC_CONTENT;        // div element wrapping TOC list
    final public static AttributablePart TOC_LIST = TocUtils.TOC_LIST;              // ul/ol element of TOC list

    final public static DataKey<Integer> LEVELS = new DataKey<>("LEVELS", TocOptions.DEFAULT_LEVELS);
    final public static DataKey<Boolean> IS_TEXT_ONLY = new DataKey<>("IS_TEXT_ONLY", false);
    final public static DataKey<Boolean> IS_NUMBERED = new DataKey<>("IS_NUMBERED", false);
    final public static DataKey<TocOptions.ListType> LIST_TYPE = new DataKey<>("LIST_TYPE", TocOptions.ListType.HIERARCHY);
    final public static DataKey<Boolean> IS_HTML = new DataKey<>("IS_HTML", false);
    final public static DataKey<Integer> TITLE_LEVEL = new DataKey<>("TITLE_LEVEL", TocOptions.DEFAULT_TITLE_LEVEL);
    final public static NullableDataKey<String> TITLE = new NullableDataKey<>("TITLE");
    final public static DataKey<Boolean> AST_INCLUDE_OPTIONS = new DataKey<>("AST_INCLUDE_OPTIONS", false);
    final public static DataKey<Boolean> BLANK_LINE_SPACER = new DataKey<>("BLANK_LINE_SPACER", false);
    final public static DataKey<String> DIV_CLASS = new DataKey<>("DIV_CLASS", "");
    final public static DataKey<String> LIST_CLASS = new DataKey<>("LIST_CLASS", "");
    final public static DataKey<Boolean> CASE_SENSITIVE_TOC_TAG = new DataKey<>("CASE_SENSITIVE_TOC_TAG", true);

    // format options
    final public static DataKey<SimTocGenerateOnFormat> FORMAT_UPDATE_ON_FORMAT = new DataKey<>("FORMAT_UPDATE_ON_FORMAT", SimTocGenerateOnFormat.UPDATE);
    final public static DataKey<TocOptions> FORMAT_OPTIONS = new DataKey<>("FORMAT_OPTIONS", new TocOptions(null, false), options -> new TocOptions(options, false));

    private TocExtension() {
    }

    public static TocExtension create() {
        return new TocExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {
        // set header id options if not already set
        if (!options.contains(HtmlRenderer.RENDER_HEADER_ID)) {
            options.set(HtmlRenderer.RENDER_HEADER_ID, true);
            options.set(HtmlRenderer.GENERATE_HEADER_ID, true);
        } else if (!options.contains(HtmlRenderer.GENERATE_HEADER_ID)) {
            options.set(HtmlRenderer.GENERATE_HEADER_ID, true);
        }
    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new TocBlockParser.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new TocNodeRenderer.Factory());
        }
    }
}
