package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationBlockParser;
import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationNodeRenderer;
import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationPostProcessor;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.node.Link;
import com.vladsch.flexmark.parser.Parser;

/**
 * Extension for automatically turning plain URLs and email addresses into links.
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed links are turned into normal {@link Link} nodes.
 * </p>
 */
public class AbbreviationExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final private boolean useLinks;

    public AbbreviationExtension(boolean useLinks) {
        this.useLinks = useLinks;
    }

    public static Extension create() {
        return new AbbreviationExtension(false);
    }

    public static Extension create(boolean useLinks) {
        return new AbbreviationExtension(useLinks);
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new AbbreviationBlockParser.Factory());
        parserBuilder.postProcessor(new AbbreviationPostProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
            @Override
            public NodeRenderer create(NodeRendererContext context) {
                return new AbbreviationNodeRenderer(context, useLinks);
            }
        });
    }
}
