package com.vladsch.flexmark.ext.simtoc;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.simtoc.internal.SimTocBlockParser;
import com.vladsch.flexmark.ext.simtoc.internal.SimTocNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.collection.DataKey;
import com.vladsch.flexmark.parser.Parser;

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
    // final public static DataKey<TocRepository> TOCS = new DataKey<>("TOCS", TocRepository::new);
    // final public static DataKey<KeepType> TOCS_KEEP = new DataKey<>("TOCS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates

    /**
     * DataKey specifying if [TOC level=#] with invalid level option: 0, 7,8,9 should still be parsed into a TOC node.
     *
     * @value #TOC_PARSE_INVALID_LEVEL
     *
     */
    final public static DataKey<Boolean> PARSE_INVALID_LEVEL = new DataKey<>("PARSE_INVALID_LEVEL", false);
    final public static DataKey<Boolean> HEADER_TEXT_ONLY = new DataKey<>("HEADER_TEXT_ONLY", false);

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
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(SimTocNodeRenderer::new);
    }
}
