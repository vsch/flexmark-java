package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.toc.internal.TocBlockParser;
import com.vladsch.flexmark.ext.toc.internal.TocNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

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
//    final public static DataKey<Boolean> TOC_OPTION1 = new DataKey<>("TOC_OPTION1", false); 
//    final public static DataKey<String> TOC_OPTION2 = new DataKey<>("TOC_OPTION2", "default"); 
//    final public static DataKey<Integer> TOC_OPTION3 = new DataKey<>("TOC_OPTION3", Integer.MAX_VALUE); 

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
