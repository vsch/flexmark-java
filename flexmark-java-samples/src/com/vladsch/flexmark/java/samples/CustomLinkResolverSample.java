package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlRenderer.Builder;
import com.vladsch.flexmark.html.HtmlRenderer.HtmlRendererExtension;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CustomLinkResolverSample {
    final private static DataHolder OPTIONS = new MutableDataSet().set(Parser.EXTENSIONS, Collections.singletonList(CustomExtension.create()));

    final public static DataKey<String> DOC_RELATIVE_URL = new DataKey<>("DOC_RELATIVE_URL", "");
    final public static DataKey<String> DOC_ROOT_URL = new DataKey<>("DOC_ROOT_URL", "");

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    public static class DocxLinkResolver implements LinkResolver {
        protected static final String[] EMPTY_STRINGS = new String[0];
        final private String docRelativeURL;
        final private String docRootURL;
        final private String[] relativeParts;

        public DocxLinkResolver(LinkResolverBasicContext context) {
            // can use context for custom settings
            // context.getDocument();
            // context.getHtmlOptions();
            String docRelativeURL = DOC_RELATIVE_URL.get(context.getOptions());
            if (docRelativeURL != null) {
                docRelativeURL = Utils.removePrefix(docRelativeURL, '/');
            }

            String docRootURL = DOC_ROOT_URL.get(context.getOptions());
            if (docRootURL != null) {
                docRootURL = Utils.removePrefix(docRootURL, '/');
            }
            this.docRelativeURL = docRelativeURL;
            this.docRootURL = docRootURL;
            relativeParts = docRelativeURL == null ? EMPTY_STRINGS : docRelativeURL.split("/");
        }

        @NotNull
        @Override
        public ResolvedLink resolveLink(@NotNull Node node, @NotNull LinkResolverBasicContext context, @NotNull ResolvedLink link) {
            if (node instanceof Image || node instanceof Link || node instanceof Reference) {
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
                } else if (url.startsWith("/")) {
                    if (docRootURL != null && !docRootURL.isEmpty()) {
                        // this one is root url, prefix with root url, without the trailing /
                        url = docRootURL + url;

                        return link.withStatus(LinkStatus.VALID)
                                .withUrl(url);
                    }
                } else if (!url.matches("^(?:[a-z]+:|#|\\?)")) {
                    // relative, we will process it as a relative path to the docRelativeURL
                    String pageRef = url;
                    String suffix = "";
                    int pos = url.indexOf('#');
                    if (pos > 0) {
                        // remove anchor
                        suffix = url.substring(pos);
                        pageRef = url.substring(0, pos);
                    } else if (url.contains("?")) {
                        // remove query
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
                return new com.vladsch.flexmark.docx.converter.internal.DocxLinkResolver(context);
            }
        }
    }

    static class CustomExtension implements HtmlRendererExtension {
        @Override
        public void rendererOptions(@NotNull MutableDataHolder options) {

        }

        @Override
        public void extend(@NotNull Builder htmlRendererBuilder, @NotNull String rendererType) {
            htmlRendererBuilder.linkResolverFactory(new DocxLinkResolver.Factory());
            htmlRendererBuilder.nodeRendererFactory(new CustomLinkRenderer.Factory());
        }

        static CustomExtension create() {
            return new CustomExtension();
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
