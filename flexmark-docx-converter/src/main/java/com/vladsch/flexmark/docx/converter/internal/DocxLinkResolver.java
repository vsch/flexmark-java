package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.docx.converter.DocxRenderer;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagBlock;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.misc.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class DocxLinkResolver implements LinkResolver {
    final private String docRelativeURL;
    final private String docRootURL;
    final private String[] relativeParts;
    final private boolean prefixWwwLinks;

    public DocxLinkResolver(LinkResolverBasicContext context) {
        // can use context for custom settings
        // context.getDocument();
        // context.getHtmlOptions();
        String docRelativeURL = DocxRenderer.DOC_RELATIVE_URL.get(context.getOptions());
        if (docRelativeURL != null) {
            docRelativeURL = Utils.removePrefix(docRelativeURL, '/');
        }

        String docRootURL = DocxRenderer.DOC_ROOT_URL.get(context.getOptions());
        if (docRootURL != null) {
            docRootURL = Utils.removePrefix(docRootURL, '/');
        }
        this.docRelativeURL = docRelativeURL;
        this.docRootURL = docRootURL;
        relativeParts = docRelativeURL.split("/");
        prefixWwwLinks = DocxRenderer.PREFIX_WWW_LINKS.get(context.getOptions());
    }

    @NotNull
    @Override
    public ResolvedLink resolveLink(@NotNull Node node, @NotNull LinkResolverBasicContext context, @NotNull ResolvedLink link) {
        // NOTE: node is document when resolving include urls
        if (node instanceof Image || node instanceof Link || node instanceof Reference || node instanceof JekyllTagBlock) {
            // resolve wiki image link
            String url = link.getUrl();

            if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("ftp://") || url.startsWith("sftp://")) {
                // resolve url, return one of LinkStatus other than LinkStatus.UNKNOWN
                return link.withStatus(LinkStatus.VALID)
                        .withUrl(url);
            } else if (url.startsWith("file:/")) {
                // assume it is good
                return link.withStatus(LinkStatus.VALID)
                        .withUrl(url);
            } else if (url.startsWith(DocxRenderer.EMOJI_RESOURCE_PREFIX)) {
                // assume it is good
                return link.withStatus(LinkStatus.VALID)
                        .withUrl(url);
            } else if (url.startsWith("/")) {
                if (docRootURL != null && !docRootURL.isEmpty()) {
                    // this one is root url, prefix with root url, without the trailing /
                    url = docRootURL + url;

                    return link.withStatus(LinkStatus.VALID)
                            .withUrl(url);
                }
            } else if (prefixWwwLinks && url.startsWith("www.")) {
                // should be prefixed with http://, we will just add it
                return link.withStatus(LinkStatus.INVALID)
                        .withUrl("http://" + url);
            } else if (!url.matches("^(?:[a-z]+:|#|\\?)")) {
                // relative, we will process it as a relative path to the docRelativeURL
                String pageRef = url;
                String suffix = "";
                int pos = url.indexOf('#');
                if (pos == 0) {
                    return link.withStatus(LinkStatus.VALID);
                } else {
                    if (pos > 0) {
                        // remove anchor
                        suffix = url.substring(pos);
                        pageRef = url.substring(0, pos);
                    } else if (url.contains("?")) {
                        // remove query
                        pos = url.indexOf("?");
                        suffix = url.substring(pos);
                        pageRef = url.substring(0, pos);
                    }

                    String[] pathParts = pageRef.split("/");
                    int docParts = relativeParts.length;
                    int iMax = pathParts.length;
                    StringBuilder resolved = new StringBuilder();
                    String sep = "";

                    for (int i = 0; i < iMax; i++) {
                        String part = pathParts[i];
                        if (part.equals(".")) {
                            // skp
                        } else if (part.equals("..")) {
                            // remove one doc part
                            if (docParts == 0) return link;
                            docParts--;
                        } else {
                            resolved.append(sep);
                            resolved.append(part);
                            sep = "/";
                        }
                    }

                    // prefix with remaining docParts
                    sep = docRelativeURL.startsWith("/") ? "/" : "";
                    StringBuilder resolvedURL = new StringBuilder();
                    iMax = docParts;
                    for (int i = 0; i < iMax; i++) {
                        resolvedURL.append(sep);
                        resolvedURL.append(relativeParts[i]);
                        sep = "/";
                    }

                    resolvedURL.append('/').append(resolved).append(suffix);
                    return link.withStatus(LinkStatus.VALID)
                            .withUrl(resolvedURL.toString());
                }
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
            return new DocxLinkResolver(context);
        }
    }
}

