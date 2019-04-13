package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.html.*;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;

import java.util.HashSet;
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
            rendererBuilder.nodeRendererFactory(new CustomLinkRenderer.Factory());
        }

        static CustomExtension create() {
            return new CustomExtension();
        }
    }

    static class CustomLinkResolver implements LinkResolver {
        public CustomLinkResolver(final LinkResolverContext context) {
            // can use context for custom settings
            // context.getDocument();
            // context.getHtmlOptions();
        }

        @Override
        public ResolvedLink resolveLink(final Node node, final LinkResolverContext context, final ResolvedLink link) {
            // you can also set/clear/modify attributes through ResolvedLink.getAttributes() and ResolvedLink.getNonNullAttributes()

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
            public LinkResolver create(final LinkResolverContext context) {
                return new CustomLinkResolver(context);
            }
        }
    }

    static class CustomLinkRenderer implements NodeRenderer {
        public static class Factory implements DelegatingNodeRendererFactory {
            @Override
            public NodeRenderer create(final DataHolder options) {
                return new CustomLinkRenderer();
            }

            @Override
            public Set<Class<? extends NodeRendererFactory>> getDelegates() {
                ///Set<Class<? extends NodeRendererFactory>> set = new HashSet<Class<? extends NodeRendererFactory>>();
                // add node renderer factory classes to which this renderer will delegate some of its rendering
                // core node renderer is assumed to have all depend it so there is no need to add it
                //set.add(WikiLinkNodeRenderer.Factory.class);
                //return set;

                // return null if renderer does not delegate or delegates only to core node renderer
                return null;
            }
        }

        ;

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
            set.add(new NodeRenderingHandler<Link>(Link.class, new CustomNodeRenderer<Link>() {
                @Override
                public void render(Link node, NodeRendererContext context, HtmlWriter html) {
                    // test the node to see if it needs overriding
                    if (node.getText().equals("bar")) {
                        html.text("(eliminated)");
                    } else {
                        // otherwise pass it for default rendering
                        context.delegateRender();
                    }
                }
            }));
            //set.add(new NodeRenderingHandler<WikiLink>(WikiLink.class, new CustomNodeRenderer<WikiLink>() {
            //    @Override
            //    public void render(WikiLink node, NodeRendererContext context, HtmlWriter html) {
            //        // test the node to see if it needs overriding
            //        Matcher matcher = CONFLUENCE_WIKI_LINK.matcher(node.getChars());
            //        if (matcher.find()) {
            //            String link = "...";
            //            html.raw(link);
            //        } else {
            //            // otherwise pass it for default rendering
            //            context.delegateRender();
            //        }
            //    }
            //}));

            return set;
        }
    }

    // use the PARSER to parse and RENDERER to render with pegdown compatibility
    public static void main(String[] args) {
        // You can re-use parser and renderer instances
        Node document = PARSER.parse("This is *Sparta* [[document]] and this is not a link [bar](/url)");
        String html = RENDERER.render(document);  // "<p>This is <em>Sparta</em> <a href="document.html">document</a> and this is not a link (eliminated)</p>\n"
        System.out.println(html);
    }
}
