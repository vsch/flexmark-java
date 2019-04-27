package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.html.*;
import com.vladsch.flexmark.html.HtmlRenderer.Builder;
import com.vladsch.flexmark.html.HtmlRenderer.HtmlRendererExtension;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomLinkResolverSample {
    static final DataHolder OPTIONS = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(CustomExtension.create()));

    public static final DataKey<String> DOC_RELATIVE_URL = new DataKey<>("DOC_RELATIVE_URL", "");
    public static final DataKey<String> DOC_ROOT_URL = new DataKey<>("DOC_ROOT_URL", "");

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    public static class DocxLinkResolver implements LinkResolver {
        private final String docRelativeURL;
        private final String docRootURL;
        private final String[] relativeParts;

        public DocxLinkResolver(final LinkResolverContext context) {
            // can use context for custom settings
            // context.getDocument();
            // context.getHtmlOptions();
            String docRelativeURL = DOC_RELATIVE_URL.getFrom(context.getOptions());
            if (docRelativeURL != null) {
                docRelativeURL = Utils.removeStart(docRelativeURL, '/');
            }

            String docRootURL = DOC_ROOT_URL.getFrom(context.getOptions());
            if (docRootURL != null) {
                docRootURL = Utils.removeStart(docRootURL, '/');
            }
            this.docRelativeURL = docRelativeURL;
            this.docRootURL = docRootURL;
            relativeParts = docRelativeURL.split("/");
        }

        @Override
        public ResolvedLink resolveLink(final Node node, final LinkResolverContext context, final ResolvedLink link) {
            Document document = node.getDocument();

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
            public LinkResolver apply(final LinkResolverContext context) {
                return new com.vladsch.flexmark.docx.converter.internal.DocxLinkResolver(context);
            }
        }
    }

    static class CustomExtension implements HtmlRendererExtension {
        @Override
        public void rendererOptions(final MutableDataHolder options) {

        }

        @Override
        public void extend(final Builder rendererBuilder, final String rendererType) {
            rendererBuilder.linkResolverFactory(new DocxLinkResolver.Factory());
            rendererBuilder.nodeRendererFactory(new CustomLinkRenderer.Factory());
        }

        static Extension create() {
            return new CustomExtension();
        }
    }

    static class CustomLinkRenderer implements NodeRenderer {
        public static class Factory implements DelegatingNodeRendererFactory {
            @Override
            public NodeRenderer apply(final DataHolder options) {
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
