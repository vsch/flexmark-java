package com.vladsch.flexmark.html.converter;

import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jsoup.nodes.Node;

import java.util.Set;

public class HtmlConverterTextExtension implements FlexmarkHtmlConverter.HtmlConverterExtension {
    public static HtmlConverterTextExtension create() {
        return new HtmlConverterTextExtension();
    }

    @Override
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(FlexmarkHtmlConverter.Builder builder) {
        builder.linkResolverFactory(new CustomLinkResolver.Factory());
    }

    static class CustomLinkResolver implements HtmlLinkResolver {
        public CustomLinkResolver(HtmlNodeConverterContext context) {
        }

        @Override
        public ResolvedLink resolveLink(Node node, HtmlNodeConverterContext context, ResolvedLink link) {
            if (link.getUrl().startsWith("http:")) {
                return link.withUrl("https:" + link.getUrl().substring("http:".length()));
            }
            return link;
        }

        static class Factory implements HtmlLinkResolverFactory {
            @Override
            public Set<Class<? extends HtmlLinkResolverFactory>> getAfterDependents() {
                return null;
            }

            @Override
            public Set<Class<? extends HtmlLinkResolverFactory>> getBeforeDependents() {
                return null;
            }

            @Override
            public boolean affectsGlobalScope() {
                return false;
            }

            @Override
            public HtmlLinkResolver apply(HtmlNodeConverterContext context) {
                return new CustomLinkResolver(context);
            }
        }
    }
}
