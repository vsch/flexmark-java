package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.toc.internal.SimTocBlockParser;
import com.vladsch.flexmark.ext.toc.internal.SimTocNodeRenderer;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

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
    // public static final DataKey<TocRepository> TOCS = new DataKey<>("TOCS", TocRepository::new);
    // public static final DataKey<KeepType> TOCS_KEEP = new DataKey<>("TOCS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates

    /**
     * DataKey specifying if [TOC level=#] with invalid level option: 0, 7,8,9 should still be parsed into a TOC node.
     * <p>
     * LEVELS levels of heading to use for TOC generation
     */
    public static final DataKey<Integer> LEVELS = TocExtension.LEVELS;
    public static final DataKey<Boolean> IS_TEXT_ONLY = TocExtension.IS_TEXT_ONLY;
    public static final DataKey<Boolean> IS_NUMBERED = TocExtension.IS_NUMBERED;
    public static final DataKey<TocOptions.ListType> LIST_TYPE = TocExtension.LIST_TYPE;
    public static final DataKey<Boolean> IS_HTML = new DataKey<>("IS_HTML", false);
    public static final DataKey<Integer> TITLE_LEVEL = new DataKey<>("TITLE_LEVEL", TocOptions.DEFAULT_TITLE_LEVEL);
    public static final DataKey<String> TITLE = new DataKey<>("TITLE", TocOptions.DEFAULT_TITLE);
    public static final DataKey<Boolean> AST_INCLUDE_OPTIONS = new DataKey<>("AST_INCLUDE_OPTIONS", false);
    public static final DataKey<Boolean> BLANK_LINE_SPACER = new DataKey<>("BLANK_LINE_SPACER", false);

    private SimTocExtension() {
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
        if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
        } else if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                @Override
                public NodeRenderer create(DataHolder options) {
                    return new SimTocNodeRenderer(options);
                }
            });
        }
    }
}
