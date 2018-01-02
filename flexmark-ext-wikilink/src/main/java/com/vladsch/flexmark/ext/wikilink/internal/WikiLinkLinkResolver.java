package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.*;

import java.util.Set;

import static com.vladsch.flexmark.ext.wikilink.WikiLinkExtension.WIKI_LINK;

public class WikiLinkLinkResolver implements LinkResolver {
    private final WikiLinkOptions options;

    public WikiLinkLinkResolver(LinkResolverContext context) {
        this.options = new WikiLinkOptions(context.getOptions());
    }

    @Override
    public ResolvedLink resolveLink(Node node, LinkResolverContext context, ResolvedLink link) {
        if (link.getLinkType() == WIKI_LINK) {
            StringBuilder sb = new StringBuilder();
            final boolean isWikiImage = node instanceof WikiImage;
            String wikiLink = link.getUrl();
            int iMax = wikiLink.length();
            sb.append(isWikiImage ? options.imagePrefix : options.linkPrefix);

            boolean hadAnchorRef = false;

            String linkEscapeChars = options.linkEscapeChars;
            String linkReplaceChars = options.linkReplaceChars;
            for (int i = 0; i < iMax; i++) {
                char c = wikiLink.charAt(i);

                if (c == '#') {
                    if (hadAnchorRef) continue;
                    sb.append(isWikiImage ? options.imageFileExtension : options.linkFileExtension);
                    hadAnchorRef = true;
                }

				int pos = linkEscapeChars.indexOf(c);

                if (pos < 0) {
                    sb.append(c);
                } else {
					sb.append(linkReplaceChars.charAt(pos));
                }
            }

            if (!hadAnchorRef) {
                sb.append(isWikiImage ? options.imageFileExtension : options.linkFileExtension);
            }

            if (isWikiImage) {
                return new ResolvedLink(LinkType.IMAGE, sb.toString(), null, LinkStatus.UNCHECKED);
            } else {
                return new ResolvedLink(LinkType.LINK, sb.toString(), null, LinkStatus.UNCHECKED);
            }
        }

        return link;
    }

    public static class Factory implements LinkResolverFactory {
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
        public LinkResolver create(LinkResolverContext context) {
            return new WikiLinkLinkResolver(context);
        }
    }
}
