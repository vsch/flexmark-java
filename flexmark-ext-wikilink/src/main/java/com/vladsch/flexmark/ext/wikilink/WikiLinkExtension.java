package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.wikilink.internal.WikiLinkLinkRefProcessor;
import com.vladsch.flexmark.ext.wikilink.internal.WikiLinkLinkResolver;
import com.vladsch.flexmark.ext.wikilink.internal.WikiLinkNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;

/**
 * Extension for wikilinks
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed emoji shortcuts text regions are turned into {@link com.vladsch.flexmark.ext.wikilink.WikiLink} nodes.
 * </p>
 */
public class WikiLinkExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final static public DataKey<Boolean> DISABLE_RENDERING = new DataKey<>("DISABLE_RENDERING", false);
    final static public DataKey<Boolean> LINK_FIRST_SYNTAX = new DataKey<>("LINK_FIRST_SYNTAX", false);
    final static public DataKey<String> LINK_PREFIX = new DataKey<>("LINK_PREFIX", "");
    final static public DataKey<String> LINK_FILE_EXTENSION = new DataKey<>("LINK_FILE_EXTENSION", "");
    final static public LinkType WIKI_LINK = new LinkType("WIKI");

    private WikiLinkExtension() {
    }

    public static Extension create() {
        return new WikiLinkExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.linkRefProcessorFactory(new WikiLinkLinkRefProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(WikiLinkNodeRenderer::new);
        rendererBuilder.linkResolverFactory(new WikiLinkLinkResolver.Factory());
    }
}
