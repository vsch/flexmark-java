package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;

import java.util.Set;

public class PegdownCustomLinkResolverOptions {
    static final DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(Extensions.ALL, CustomExtension.create());

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    static class CustomExtension implements HtmlRenderer.HtmlRendererExtension {
        @Override
        public void rendererOptions(final MutableDataHolder options) {

        }

        @Override
        public void extend(final HtmlRenderer.Builder rendererBuilder, final String rendererType) {
            rendererBuilder.linkResolverFactory(new CustomLinkResolver.Factory());
        }

        static CustomExtension create() {
            return new CustomExtension();
        }
    }

    static class CustomLinkResolver implements LinkResolver {

        public CustomLinkResolver(final NodeRendererContext context) {
            // can use context for custom settings
            // context.getDocument();
            // context.getHtmlOptions();
        }

        @Override
        public ResolvedLink resolveLink(final Node node, final NodeRendererContext context, final ResolvedLink link) {
            if (node instanceof WikiImage) {
                // resolve wiki image link
                String url = link.getUrl() + ".png";

                // resolve url, return one of LinkStatus other than LinkStatus.UNKNOWN
                return link.withStatus(LinkStatus.VALID)
                        .withUrl(url);
            } else if (node instanceof WikiLink) {
                // resolve wiki link
                String url = link.getUrl() + ".html";

                // resolve url, return one of LinkStatus other than LinkStatus.UNKNOWN
                return link.withStatus(LinkStatus.VALID)
                        .withUrl(url);
            }
            return link;
        }

        static class Factory implements LinkResolverFactory {
            @Override
            public Set<Class<? extends LinkResolverFactory>> getAfterDependents() {
                return null;
            }

            @Override
            public Set<Class<? extends LinkResolverFactory>> getBeforeDependents() {
                return null;
            }

            @Override
            public boolean affectsGlobalScope() {
                return false;
            }

            @Override
            public LinkResolver create(final NodeRendererContext context) {
                return new CustomLinkResolver(context);
            }
        }
    }

    // use the PARSER to parse and RENDERER to render with pegdown compatibility
    public static void main(String[] args) {
        // You can re-use parser and renderer instances
        Node document = PARSER.parse("This is *Sparta* [[document]]");
        String html = RENDERER.render(document);  // "<p>This is <em>Sparta</em> <a href="document.html">document</a></p>\n"
        System.out.println(html);
    }
}
