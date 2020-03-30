package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profile.pegdown.Extensions;
import com.vladsch.flexmark.profile.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class CustomContextDataSample {
    class XhtmlContent {

    }

    final public static NullableDataKey<XhtmlContent> XHTML_CONTENT = new NullableDataKey<>("XHTML_CONTENT");

    static class CustomExtension implements HtmlRenderer.HtmlRendererExtension {
        @Override
        public void rendererOptions(@NotNull MutableDataHolder options) {

        }

        @Override
        public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
            htmlRendererBuilder.linkResolverFactory(new CustomLinkResolver.Factory());
        }

        static CustomExtension create() {
            return new CustomExtension();
        }
    }

    static class CustomLinkResolver implements LinkResolver {

        public CustomLinkResolver(LinkResolverBasicContext options) {
            // can use context for custom settings
            // context.getDocument();
            // context.getHtmlOptions();
        }

        @NotNull
        @Override
        public ResolvedLink resolveLink(@NotNull Node node, @NotNull LinkResolverBasicContext context, @NotNull ResolvedLink link) {
            Document document = context.getDocument();
            XhtmlContent xhtmlContent = XHTML_CONTENT.get(document);

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

    public static void main(String[] args) {
        DataHolder options = PegdownOptionsAdapter.flexmarkOptions(Extensions.ALL, CustomExtension.create());

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String markdown = "";
        XhtmlContent xhtmlContent = null;

        // You can re-use parser and renderer instances
        Document document = (Document) parser.parse(markdown);
        document.set(XHTML_CONTENT, xhtmlContent);
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
        System.out.println(html);
    }
}
