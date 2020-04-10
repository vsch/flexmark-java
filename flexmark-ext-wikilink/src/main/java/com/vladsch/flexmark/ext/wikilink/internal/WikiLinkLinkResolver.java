package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static com.vladsch.flexmark.ext.wikilink.WikiLinkExtension.WIKI_LINK;

public class WikiLinkLinkResolver implements LinkResolver {
    final private WikiLinkOptions options;

    public WikiLinkLinkResolver(LinkResolverBasicContext context) {
        this.options = new WikiLinkOptions(context.getOptions());
    }

    @NotNull
    @Override
    public ResolvedLink resolveLink(@NotNull Node node, @NotNull LinkResolverBasicContext context, @NotNull ResolvedLink link) {
        if (link.getLinkType() == WIKI_LINK) {
            StringBuilder sb = new StringBuilder();
            final boolean isWikiImage = node instanceof WikiImage;
            String wikiLink = link.getUrl();
            int iMax = wikiLink.length();
            boolean absolute = iMax > 0 && wikiLink.charAt(0) == '/';
            sb.append(isWikiImage ? options.getImagePrefix(absolute) : options.getLinkPrefix(absolute));

            boolean hadAnchorRef = false;
            boolean isEscaped = false;

            String linkEscapeChars = options.linkEscapeChars;
            String linkReplaceChars = options.linkReplaceChars;
            for (int i = absolute ? 1 : 0; i < iMax; i++) {
                char c = wikiLink.charAt(i);

                if (c == '#' && !hadAnchorRef && options.allowAnchors && !(isEscaped && options.allowAnchorEscape)) {
                    sb.append(isWikiImage ? options.imageFileExtension : options.linkFileExtension);
                    sb.append(c);
                    hadAnchorRef = true;
                    isEscaped = false;
                    continue;
                }

                if (c == '\\') {
                    if (isEscaped) {
                        // need to URL encode \
                        sb.append("%5C");
                    }
                    isEscaped = !isEscaped;
                } else {
                    isEscaped = false;
                    if (c == '#' && !hadAnchorRef) {
                        // need to URL encode the #
                        sb.append("%23");
                    } else {
                        int pos = linkEscapeChars.indexOf(c);
                        
                        if (pos < 0) {
                            sb.append(c);
                        } else {
                            sb.append(linkReplaceChars.charAt(pos));
                        }
                    }
                }
            }

            if (isEscaped) {
                // need to add dangling URL encoded \
                sb.append("%5C");
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
            return new WikiLinkLinkResolver(context);
        }
    }
}
