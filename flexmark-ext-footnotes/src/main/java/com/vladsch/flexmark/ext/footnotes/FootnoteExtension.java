package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.footnotes.internal.FootnoteBlockParser;
import com.vladsch.flexmark.ext.footnotes.internal.FootnoteNodeRenderer;
import com.vladsch.flexmark.ext.footnotes.internal.FootnotePostProcessor;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;

/**
 * Extension for footnotes
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed emoji shortcuts text regions are turned into {@link Footnote} nodes.
 * </p>
 */
public class FootnoteExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private FootnoteExtension() {
    }

    public static Extension create() {
        return new FootnoteExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new FootnoteBlockParser.Factory());
        parserBuilder.postProcessor(new FootnotePostProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
            @Override
            public NodeRenderer create(NodeRendererContext context) {
                return new FootnoteNodeRenderer(context);
            }
        });
    }
}
