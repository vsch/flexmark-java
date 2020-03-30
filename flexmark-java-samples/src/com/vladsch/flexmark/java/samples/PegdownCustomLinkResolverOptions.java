package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profile.pegdown.Extensions;
import com.vladsch.flexmark.profile.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class PegdownCustomLinkResolverOptions {
    final private static DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(Extensions.ALL, CustomExtension.create());

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    static class CustomExtension implements HtmlRenderer.HtmlRendererExtension {
        @Override
        public void rendererOptions(@NotNull MutableDataHolder options) {

        }

        @Override
        public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
            htmlRendererBuilder.linkResolverFactory(new CustomLinkResolver.Factory());
            htmlRendererBuilder.nodeRendererFactory(new CustomLinkRenderer.Factory());
        }

        static CustomExtension create() {
            return new CustomExtension();
        }
    }

    static class CustomLinkResolver implements LinkResolver {
        public CustomLinkResolver(LinkResolverBasicContext context) {
            // can use context for custom settings
            // context.getDocument();
            // context.getHtmlOptions();
        }

        @NotNull
        @Override
        public ResolvedLink resolveLink(@NotNull Node node, @NotNull LinkResolverBasicContext context, @NotNull ResolvedLink link) {
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
            @Nullable
            @Override
            public Set<Class<?>> getAfterDependents() {
                return null;
            }

            @Nullable
            @Override
            public Set<Class<?>> getBeforeDependents() {
                return null;
            }

            @Override
            public boolean affectsGlobalScope() {
                return false;
            }

            @NotNull
            @Override
            public LinkResolver apply(@NotNull LinkResolverBasicContext context) {
                return new CustomLinkResolver(context);
            }
        }
    }

    static class CustomLinkRenderer implements NodeRenderer {
        public static class Factory implements DelegatingNodeRendererFactory {
            @NotNull
            @Override
            public NodeRenderer apply(@NotNull DataHolder options) {
                return new CustomLinkRenderer();
            }

            @Override
            public Set<Class<?>> getDelegates() {
                ///Set<Class<?>>();
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
            HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
            set.add(new NodeRenderingHandler<>(Link.class, (node, context, html) -> {
                // test the node to see if it needs overriding
                if (node.getText().equals("bar")) {
                    html.text("(eliminated)");
                } else {
                    // otherwise pass it for default rendering
                    context.delegateRender();
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
