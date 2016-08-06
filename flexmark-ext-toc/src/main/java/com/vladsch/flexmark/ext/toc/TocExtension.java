package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.toc.internal.TocBlockParser;
import com.vladsch.flexmark.ext.toc.internal.TocNodeRenderer;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;

/**
 * Extension for tocs
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed [TOC] text is turned into {@link TocBlock} nodes.
 * Rendered into table of contents based on the headings in the document
 * </p>
 */
public class TocExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // final public static DataKey<TocRepository> TOCS = new DataKey<>("TOCS", TocRepository::new); 
    // final public static DataKey<KeepType> TOCS_KEEP = new DataKey<>("TOCS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates 

    final public static DataKey<Integer> LEVELS = new DataKey<>("LEVELS", TocOptions.DEFAULT_LEVELS);
    final public static DataKey<Boolean> IS_TEXT_ONLY = new DataKey<>("IS_TEXT_ONLY", false);
    final public static DataKey<Boolean> IS_NUMBERED = new DataKey<>("IS_NUMBERED", false);

    private TocExtension() {
    }

    public static Extension create() {
        return new TocExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new TocBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(TocNodeRenderer::new);
    }
}
