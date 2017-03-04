package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.toc.internal.SimTocBlockParser;
import com.vladsch.flexmark.ext.toc.internal.SimTocNodeRenderer;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for tocs
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link Parser.Builder#extensions(Iterable)},
 * {@link HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed [TOC] text is turned into {@link SimTocBlock} nodes.
 * Rendered into table of contents based on the headings in the document
 * </p>
 */
public class SimTocExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // duplicated here for convenience
    public static final DataKey<Integer> LEVELS = TocExtension.LEVELS;
    public static final DataKey<Boolean> IS_TEXT_ONLY = TocExtension.IS_TEXT_ONLY;
    public static final DataKey<Boolean> IS_NUMBERED = TocExtension.IS_NUMBERED;
    public static final DataKey<TocOptions.ListType> LIST_TYPE = TocExtension.LIST_TYPE;
    public static final DataKey<Boolean> IS_HTML = TocExtension.IS_HTML;
    public static final DataKey<Integer> TITLE_LEVEL = TocExtension.TITLE_LEVEL;
    public static final DataKey<String> TITLE = TocExtension.TITLE;
    public static final DataKey<Boolean> AST_INCLUDE_OPTIONS = TocExtension.AST_INCLUDE_OPTIONS;
    public static final DataKey<Boolean> BLANK_LINE_SPACER = TocExtension.BLANK_LINE_SPACER;

    // format options
    public static final DataKey<SimTocGenerateOnFormat> FORMAT_UPDATE_ON_FORMAT = TocExtension.FORMAT_UPDATE_ON_FORMAT;
    public static final DataKey<TocOptions> FORMAT_OPTIONS = TocExtension.FORMAT_OPTIONS;

    private SimTocExtension() {
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    public static Extension create() {
        return new SimTocExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new SimTocBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new SimTocNodeRenderer.Factory());
        } else if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
        }
    }
}
