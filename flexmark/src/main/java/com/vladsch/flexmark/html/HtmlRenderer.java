package com.vladsch.flexmark.html;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.IRender;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.HtmlBlock;
import com.vladsch.flexmark.ast.HtmlInline;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.collection.DynamicDefaultKey;
import com.vladsch.flexmark.util.dependency.FlatDependencyHandler;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.options.*;
import com.vladsch.flexmark.util.sequence.TagRange;

import java.text.Format;
import java.util.*;

/**
 * Renders a tree of nodes to HTML.
 * <p>
 * Start with the {@link #builder} method to configure the renderer. Example:
 * <pre><code>
 * HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).build();
 * renderer.render(node);
 * </code></pre>
 */
@SuppressWarnings("WeakerAccess")
public class HtmlRenderer implements IRender {
    public static final DataKey<String> SOFT_BREAK = new DataKey<>("SOFT_BREAK", "\n");
    public static final DataKey<String> HARD_BREAK = new DataKey<>("HARD_BREAK", "<br />\n");
    public static final DataKey<Boolean> PERCENT_ENCODE_URLS = new DataKey<>("ESCAPE_HTML", false);
    public static final DataKey<Integer> INDENT_SIZE = new DataKey<>("INDENT", 0);
    public static final DataKey<Boolean> ESCAPE_HTML = new DataKey<>("ESCAPE_HTML", false);
    public static final DataKey<Boolean> ESCAPE_HTML_BLOCKS = new DynamicDefaultKey<>("ESCAPE_HTML_BLOCKS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return ESCAPE_HTML.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> ESCAPE_HTML_COMMENT_BLOCKS = new DynamicDefaultKey<>("ESCAPE_HTML_COMMENT_BLOCKS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return ESCAPE_HTML_BLOCKS.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> ESCAPE_INLINE_HTML = new DynamicDefaultKey<>("ESCAPE_HTML_BLOCKS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return ESCAPE_HTML.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> ESCAPE_INLINE_HTML_COMMENTS = new DynamicDefaultKey<>("ESCAPE_INLINE_HTML_COMMENTS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return ESCAPE_INLINE_HTML.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> SUPPRESS_HTML = new DataKey<>("SUPPRESS_HTML", false);
    public static final DataKey<Boolean> SUPPRESS_HTML_BLOCKS = new DynamicDefaultKey<>("SUPPRESS_HTML_BLOCKS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return SUPPRESS_HTML.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> SUPPRESS_HTML_COMMENT_BLOCKS = new DynamicDefaultKey<>("SUPPRESS_HTML_COMMENT_BLOCKS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return SUPPRESS_HTML_BLOCKS.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> SUPPRESS_INLINE_HTML = new DynamicDefaultKey<>("SUPPRESS_INLINE_HTML", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return SUPPRESS_HTML.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> SUPPRESS_INLINE_HTML_COMMENTS = new DynamicDefaultKey<>("SUPPRESS_INLINE_HTML_COMMENTS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return SUPPRESS_INLINE_HTML.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> SOURCE_WRAP_HTML = new DataKey<>("SOURCE_WRAP_HTML", false);
    public static final DataKey<Boolean> SOURCE_WRAP_HTML_BLOCKS = new DynamicDefaultKey<>("SOURCE_WRAP_HTML_BLOCKS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return SOURCE_WRAP_HTML.getFrom(holder);
        }
    });
    //public static final DataKey<Boolean> SOURCE_WRAP_INLINE_HTML = new DynamicDefaultKey<>("SOURCE_WRAP_INLINE_HTML", SOURCE_WRAP_HTML::getFrom);
    public static final DataKey<Boolean> HEADER_ID_GENERATOR_RESOLVE_DUPES = new DataKey<>("HEADER_ID_GENERATOR_RESOLVE_DUPES", true);
    public static final DataKey<String> HEADER_ID_GENERATOR_TO_DASH_CHARS = new DataKey<>("HEADER_ID_GENERATOR_TO_DASH_CHARS", " -_");
    public static final DataKey<Boolean> HEADER_ID_GENERATOR_NO_DUPED_DASHES = new DataKey<>("HEADER_ID_GENERATOR_NO_DUPED_DASHES", false);
    public static final DataKey<Boolean> RENDER_HEADER_ID = new DataKey<>("RENDER_HEADER_ID", false);
    public static final DataKey<Boolean> GENERATE_HEADER_ID = new DataKey<>("GENERATE_HEADER_ID", true);
    public static final DataKey<Boolean> DO_NOT_RENDER_LINKS = new DataKey<>("DO_NOT_RENDER_LINKS", false);
    public static final DataKey<String> FENCED_CODE_LANGUAGE_CLASS_PREFIX = new DataKey<>("LANGUAGE_CLASS_PREFIX", "language-");
    public static final DataKey<String> SOURCE_POSITION_ATTRIBUTE = new DataKey<>("SOURCE_POSITION_ATTRIBUTE", "");
    public static final DataKey<Boolean> SOURCE_POSITION_PARAGRAPH_LINES = new DataKey<>("SOURCE_POSITION_PARAGRAPH_LINES", false);
    public static final DataKey<String> TYPE = new DataKey<>("TYPE", "HTML");
    public static final DataKey<ArrayList<TagRange>> TAG_RANGES = new DataKey<>("TAG_RANGES", new DataValueFactory<ArrayList<TagRange>>() {
        @Override
        public ArrayList<TagRange> create(DataHolder value) {
            return new ArrayList<>();
        }
    });

    /**
     * output control for FormattingAppendable, see {@link com.vladsch.flexmark.util.html.FormattingAppendable#setOptions(int)}
     */
    public static final DataKey<Integer> FORMAT_FLAGS = new DataKey<>("FORMAT_FLAGS", 0);
    public static final DataKey<Integer> MAX_TRAILING_BLANK_LINES = new DataKey<>("MAX_TRAILING_BLANK_LINES", 1);

    // for convenience or these together and set FORMAT_FLAGS key above to the value, to have HtmlWriter apply these when rendering Html
    public static final int FORMAT_CONVERT_TABS = FormattingAppendable.CONVERT_TABS;
    public static final int FORMAT_COLLAPSE_WHITESPACE = FormattingAppendable.COLLAPSE_WHITESPACE;
    public static final int FORMAT_SUPPRESS_TRAILING_WHITESPACE = FormattingAppendable.SUPPRESS_TRAILING_WHITESPACE;
    public static final int FORMAT_ALL_OPTIONS = FormattingAppendable.FORMAT_ALL;

    private final List<AttributeProviderFactory> attributeProviderFactories;
    private final List<NodeRendererFactory> nodeRendererFactories;
    private final List<LinkResolverFactory> linkResolverFactories;
    private final HeaderIdGeneratorFactory htmlIdGeneratorFactory;
    private final HtmlRendererOptions htmlOptions;
    private final DataHolder options;
    private final Builder builder;

    HtmlRenderer(Builder builder) {
        this.builder = new Builder(builder); // take a copy to avoid after creation side effects
        this.options = new DataSet(builder);
        this.htmlOptions = new HtmlRendererOptions(this.options);

        this.htmlIdGeneratorFactory = builder.htmlIdGeneratorFactory;

        this.nodeRendererFactories = new ArrayList<>(builder.nodeRendererFactories.size() + 1);
        this.nodeRendererFactories.addAll(builder.nodeRendererFactories);

        // Add as last. This means clients can override the rendering of core nodes if they want.
        this.nodeRendererFactories.add(new NodeRendererFactory() {
            @Override
            public NodeRenderer create(DataHolder options) {
                return new CoreNodeRenderer(options);
            }
        });

        this.attributeProviderFactories = FlatDependencyHandler.computeDependencies(builder.attributeProviderFactories);
        this.linkResolverFactories = FlatDependencyHandler.computeDependencies(builder.linkResolverFactories);
    }

    /**
     * Create a new builder for configuring an {@link HtmlRenderer}.
     *
     * @return a builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Create a new builder for configuring an {@link HtmlRenderer}.
     *
     * @param options initialization options
     * @return a builder
     */
    public static Builder builder(DataHolder options) {
        return new Builder(options);
    }

    /**
     * Render a node to the appendable
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void render(Node node, Appendable output) {
        MainNodeRenderer renderer = new MainNodeRenderer(options, new HtmlWriter(output, htmlOptions.indentSize, htmlOptions.formatFlags), node.getDocument());
        renderer.render(node);
        renderer.flush(htmlOptions.maxTrailingBlankLines);
    }

    /**
     * Render a node to the appendable
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void render(Node node, Appendable output, int maxTrailingBlankLines) {
        MainNodeRenderer renderer = new MainNodeRenderer(options, new HtmlWriter(output, htmlOptions.indentSize, htmlOptions.formatFlags), node.getDocument());
        renderer.render(node);
        renderer.flush(maxTrailingBlankLines);
    }

    /**
     * Render the tree of nodes to HTML.
     *
     * @param node the root node
     * @return the rendered HTML
     */
    public String render(Node node) {
        StringBuilder sb = new StringBuilder();
        render(node, sb);
        return sb.toString();
    }

    public HtmlRenderer withOptions(DataHolder options) {
        return options == null ? this : new HtmlRenderer(new Builder(builder, options));
    }

    /**
     * Builder for configuring an {@link HtmlRenderer}. See methods for default configuration.
     */
    public static class Builder extends MutableDataSet {
        List<AttributeProviderFactory> attributeProviderFactories = new ArrayList<>();
        List<NodeRendererFactory> nodeRendererFactories = new ArrayList<>();
        List<LinkResolverFactory> linkResolverFactories = new ArrayList<>();
        private final HashSet<HtmlRendererExtension> loadedExtensions = new HashSet<>();
        HeaderIdGeneratorFactory htmlIdGeneratorFactory = null;

        public Builder() {
            super();
        }

        public Builder(DataHolder options) {
            super(options);

            if (options.contains(Parser.EXTENSIONS)) {
                extensions(get(Parser.EXTENSIONS));
            }
        }

        public Builder(Builder other) {
            super(other);

            this.attributeProviderFactories.addAll(other.attributeProviderFactories);
            this.nodeRendererFactories.addAll(other.nodeRendererFactories);
            this.linkResolverFactories.addAll(other.linkResolverFactories);
            this.loadedExtensions.addAll(other.loadedExtensions);
            this.htmlIdGeneratorFactory = other.htmlIdGeneratorFactory;
        }

        public Builder(Builder other, DataHolder options) {
            this(other);

            if (options != null) {
                setAll(options);

                if (options.contains(Parser.EXTENSIONS)) {
                    extensions(get(Parser.EXTENSIONS));
                }
            }
        }

        /**
         * @return the configured {@link HtmlRenderer}
         */
        public HtmlRenderer build() {
            return new HtmlRenderer(this);
        }

        /**
         * The HTML to use for rendering a softbreak, defaults to {@code "\n"} (meaning the rendered result doesn't have
         * a line break).
         * <p>
         * Set it to {@code "<br>"} (or {@code "<br />"} to make them hard breaks.
         * <p>
         * Set it to {@code " "} to ignore line wrapping in the source.
         *
         * @param softBreak HTML for softbreak
         * @return {@code this}
         */
        public Builder softBreak(String softBreak) {
            this.set(SOFT_BREAK, softBreak);
            return this;
        }

        /**
         * The size of the indent to use for hierarchical elements, default 0, means no indent, also fastest rendering
         *
         * @param indentSize number of spaces per indent
         * @return {@code this}
         */
        public Builder indentSize(int indentSize) {
            this.set(INDENT_SIZE, indentSize);
            return this;
        }

        /**
         * Whether {@link HtmlInline} and {@link HtmlBlock} should be escaped, defaults to {@code false}.
         * <p>
         * Note that {@link HtmlInline} is only a tag itself, not the text between an opening tag and a closing tag. So
         * markup in the text will be parsed as normal and is not affected by this option.
         *
         * @param escapeHtml true for escaping, false for preserving raw HTML
         * @return {@code this}
         */
        public Builder escapeHtml(boolean escapeHtml) {
            this.set(ESCAPE_HTML, escapeHtml);
            return this;
        }

        /**
         * Whether URLs of link or images should be percent-encoded, defaults to {@code false}.
         * <p>
         * If enabled, the following is done:
         * <ul>
         * <li>Existing percent-encoded parts are preserved (e.g. "%20" is kept as "%20")</li>
         * <li>Reserved characters such as "/" are preserved, except for "[" and "]" (see encodeURI in JS)</li>
         * <li>Unreserved characters such as "a" are preserved</li>
         * <li>Other characters such umlauts are percent-encoded</li>
         * </ul>
         *
         * @param percentEncodeUrls true to percent-encode, false for leaving as-is
         * @return {@code this}
         */
        @SuppressWarnings("SameParameterValue")
        public Builder percentEncodeUrls(boolean percentEncodeUrls) {
            this.set(PERCENT_ENCODE_URLS, percentEncodeUrls);
            return this;
        }

        /**
         * Add an attribute provider for adding/changing HTML attributes to the rendered tags.
         *
         * @param attributeProviderFactory the attribute provider factory to add
         * @return {@code this}
         */
        public Builder attributeProviderFactory(AttributeProviderFactory attributeProviderFactory) {
            this.attributeProviderFactories.add(attributeProviderFactory);
            return this;
        }

        /**
         * Add a factory for instantiating a node renderer (done when rendering). This allows to override the rendering
         * of node types or define rendering for custom node types.
         * <p>
         * If multiple node renderers for the same node type are created, the one from the factory that was added first
         * "wins". (This is how the rendering for core node types can be overridden; the default rendering comes last.)
         *
         * @param nodeRendererFactory the factory for creating a node renderer
         * @return {@code this}
         */
        public Builder nodeRendererFactory(NodeRendererFactory nodeRendererFactory) {
            this.nodeRendererFactories.add(nodeRendererFactory);
            return this;
        }

        /**
         * Add a factory for instantiating a node renderer (done when rendering). This allows to override the rendering
         * of node types or define rendering for custom node types.
         * <p>
         * If multiple node renderers for the same node type are created, the one from the factory that was added first
         * "wins". (This is how the rendering for core node types can be overridden; the default rendering comes last.)
         *
         * @param linkResolverFactory the factory for creating a node renderer
         * @return {@code this}
         */
        public Builder linkResolverFactory(LinkResolverFactory linkResolverFactory) {
            this.linkResolverFactories.add(linkResolverFactory);
            return this;
        }

        /**
         * Add a factory for generating the header id attribute from the header's text
         *
         * @param htmlIdGeneratorFactory the factory for generating header tag id attributes
         * @return {@code this}
         */
        public Builder htmlIdGeneratorFactory(HeaderIdGeneratorFactory htmlIdGeneratorFactory) {
            if (this.htmlIdGeneratorFactory != null) {
                throw new IllegalStateException("custom header id factory is already set to " + htmlIdGeneratorFactory.getClass().getName());
            }
            this.htmlIdGeneratorFactory = htmlIdGeneratorFactory;
            return this;
        }

        /**
         * @param extensions extensions to use on this HTML renderer
         * @return {@code this}
         */
        public Builder extensions(Iterable<? extends Extension> extensions) {
            for (Extension extension : extensions) {
                if (extension instanceof HtmlRendererExtension) {
                    if (!loadedExtensions.contains(extension)) {
                        HtmlRendererExtension htmlRendererExtension = (HtmlRendererExtension) extension;
                        htmlRendererExtension.extend(this, this.get(HtmlRenderer.TYPE));
                        loadedExtensions.add(htmlRendererExtension);
                    }
                }
            }
            return this;
        }
    }

    /**
     * Extension for {@link HtmlRenderer}.
     */
    public interface HtmlRendererExtension extends Extension {
        void extend(Builder rendererBuilder, String rendererType);
    }

    private class MainNodeRenderer extends NodeRendererSubContext implements NodeRendererContext {
        private final Document document;
        private final Map<Class<?>, NodeRenderingHandler> renderers;

        private final List<PhasedNodeRenderer> phasedRenderers;
        private final LinkResolver[] myLinkResolvers;
        private final Set<RenderingPhase> renderingPhases;
        private final DataHolder options;
        private RenderingPhase phase;
        private final HtmlIdGenerator htmlIdGenerator;
        private final HashMap<LinkType, HashMap<String, ResolvedLink>> resolvedLinkMap = new HashMap<>();
        private final AttributeProvider[] attributeProviders;

        MainNodeRenderer(DataHolder options, HtmlWriter htmlWriter, Document document) {
            super(htmlWriter);
            this.options = new ScopedDataSet(options, document);
            this.document = document;
            this.renderers = new HashMap<>(32);
            this.renderingPhases = new HashSet<>(RenderingPhase.values().length);
            this.phasedRenderers = new ArrayList<>(nodeRendererFactories.size());
            this.myLinkResolvers = new LinkResolver[linkResolverFactories.size()];
            this.doNotRenderLinksNesting = htmlOptions.doNotRenderLinksInDocument ? 0 : 1;
            this.htmlIdGenerator = htmlIdGeneratorFactory != null ? htmlIdGeneratorFactory.create(this)
                    : (!(htmlOptions.renderHeaderId || htmlOptions.generateHeaderIds) ? HtmlIdGenerator.NULL : new HeaderIdGenerator.Factory().create(this));

            htmlWriter.setContext(this);

            // The first node renderer for a node type "wins".
            for (int i = nodeRendererFactories.size() - 1; i >= 0; i--) {
                NodeRendererFactory nodeRendererFactory = nodeRendererFactories.get(i);
                NodeRenderer nodeRenderer = nodeRendererFactory.create(this.getOptions());
                for (NodeRenderingHandler nodeType : nodeRenderer.getNodeRenderingHandlers()) {
                    // Overwrite existing renderer
                    renderers.put(nodeType.getNodeType(), nodeType);
                }

                if (nodeRenderer instanceof PhasedNodeRenderer) {
                    this.renderingPhases.addAll(((PhasedNodeRenderer) nodeRenderer).getRenderingPhases());
                    this.phasedRenderers.add((PhasedNodeRenderer) nodeRenderer);
                }
            }

            for (int i = 0; i < linkResolverFactories.size(); i++) {
                myLinkResolvers[i] = linkResolverFactories.get(i).create(this);
            }

            this.attributeProviders = new AttributeProvider[attributeProviderFactories.size()];
            for (int i = 0; i < attributeProviderFactories.size(); i++) {
                attributeProviders[i] = attributeProviderFactories.get(i).create(this);
            }
        }

        @Override
        public Node getCurrentNode() {
            return renderingNode;
        }

        @Override
        public ResolvedLink resolveLink(LinkType linkType, String url, Boolean urlEncode) {
            HashMap<String, ResolvedLink> resolvedLinks = resolvedLinkMap.get(linkType);
            if (resolvedLinks == null) {
                resolvedLinks = new HashMap<>();
                resolvedLinkMap.put(linkType, resolvedLinks);
            }

            ResolvedLink resolvedLink = resolvedLinks.get(url);
            if (resolvedLink == null) {
                resolvedLink = new ResolvedLink(linkType, url);

                if (!url.isEmpty()) {
                    Node currentNode = getCurrentNode();

                    for (LinkResolver linkResolver : myLinkResolvers) {
                        resolvedLink = linkResolver.resolveLink(currentNode, this, resolvedLink);
                        if (resolvedLink.getStatus() != LinkStatus.UNKNOWN) break;
                    }

                    if (urlEncode == null && htmlOptions.percentEncodeUrls || urlEncode != null && urlEncode) {
                        resolvedLink = resolvedLink.withUrl(Escaping.percentEncodeUrl(resolvedLink.getUrl()));
                    }
                }

                // put it in the map
                resolvedLinks.put(url, resolvedLink);
            }

            return resolvedLink;
        }

        @Override
        public String getNodeId(Node node) {
            String id = htmlIdGenerator.getId(node);
            if (attributeProviderFactories.size() != 0) {

                Attributes attributes = new Attributes();
                if (id != null) attributes.replaceValue("id", id);

                for (AttributeProvider attributeProvider : attributeProviders) {
                    attributeProvider.setAttributes(this.renderingNode, AttributablePart.ID, attributes);
                }
                id = attributes.getValue("id");
            }
            return id;
        }

        @Override
        public DataHolder getOptions() {
            return options;
        }

        @Override
        public HtmlRendererOptions getHtmlOptions() {
            return htmlOptions;
        }

        @Override
        public Document getDocument() {
            return document;
        }

        @Override
        public RenderingPhase getRenderingPhase() {
            return phase;
        }

        @Override
        public String encodeUrl(String url) {
            if (htmlOptions.percentEncodeUrls) {
                return Escaping.percentEncodeUrl(url);
            } else {
                return url;
            }
        }

        @Override
        public Attributes extendRenderingNodeAttributes(AttributablePart part, Attributes attributes) {
            Attributes attr = attributes != null ? attributes : new Attributes();
            for (AttributeProvider attributeProvider : attributeProviders) {
                attributeProvider.setAttributes(this.renderingNode, part, attr);
            }
            return attr;
        }

        @Override
        public void render(Node node) {
            renderNode(node, this);
        }

        @Override
        public NodeRendererContext getSubContext(Appendable out, boolean inheritIndent) {
            HtmlWriter htmlWriter = new HtmlWriter(getHtmlWriter(), out, inheritIndent);
            htmlWriter.setContext(this);
            //noinspection ReturnOfInnerClass
            return new SubNodeRenderer(this, htmlWriter);
        }

        void renderNode(Node node, NodeRendererSubContext subContext) {
            if (node instanceof Document) {
                // here we render multiple phases
                int oldDoNotRenderLinksNesting = subContext.getDoNotRenderLinksNesting();
                int documentDoNotRenderLinksNesting = getHtmlOptions().doNotRenderLinksInDocument ? 1 : 0;
                this.htmlIdGenerator.generateIds(document);

                for (RenderingPhase phase : RenderingPhase.values()) {
                    if (phase != RenderingPhase.BODY && !renderingPhases.contains(phase)) { continue; }
                    this.phase = phase;
                    // here we render multiple phases
                    if (getRenderingPhase() == RenderingPhase.BODY) {
                        NodeRenderingHandler nodeRenderer = renderers.get(node.getClass());
                        if (nodeRenderer != null) {
                            subContext.doNotRenderLinksNesting = documentDoNotRenderLinksNesting;
                            subContext.renderingNode = node;
                            nodeRenderer.render(node, subContext, subContext.htmlWriter);
                            subContext.renderingNode = null;
                            subContext.doNotRenderLinksNesting = oldDoNotRenderLinksNesting;
                        }
                    } else {
                        // go through all renderers that want this phase
                        for (PhasedNodeRenderer phasedRenderer : phasedRenderers) {
                            if (phasedRenderer.getRenderingPhases().contains(phase)) {
                                subContext.doNotRenderLinksNesting = documentDoNotRenderLinksNesting;
                                subContext.renderingNode = node;
                                phasedRenderer.renderDocument(subContext, subContext.htmlWriter, (Document) node, phase);
                                subContext.renderingNode = null;
                                subContext.doNotRenderLinksNesting = oldDoNotRenderLinksNesting;
                            }
                        }
                    }
                }
            } else {
                NodeRenderingHandler nodeRenderer = renderers.get(node.getClass());
                if (nodeRenderer != null) {
                    Node oldNode = this.renderingNode;
                    int oldDoNotRenderLinksNesting = subContext.doNotRenderLinksNesting;
                    subContext.renderingNode = node;
                    nodeRenderer.render(node, subContext, subContext.htmlWriter);
                    subContext.renderingNode = oldNode;
                    subContext.doNotRenderLinksNesting = oldDoNotRenderLinksNesting;
                }
            }
        }

        public void renderChildren(Node parent) {
            renderChildrenNode(parent, this);
        }

        @SuppressWarnings("WeakerAccess")
        protected void renderChildrenNode(Node parent, NodeRendererSubContext subContext) {
            Node node = parent.getFirstChild();
            while (node != null) {
                Node next = node.getNext();
                renderNode(node, subContext);
                node = next;
            }
        }

        @SuppressWarnings("WeakerAccess")
        private class SubNodeRenderer extends NodeRendererSubContext implements NodeRendererContext {
            private final MainNodeRenderer myMainNodeRenderer;

            public SubNodeRenderer(MainNodeRenderer mainNodeRenderer, HtmlWriter htmlWriter) {
                super(htmlWriter);
                myMainNodeRenderer = mainNodeRenderer;
                doNotRenderLinksNesting = mainNodeRenderer.getHtmlOptions().doNotRenderLinksInDocument ? 1 : 0;
            }

            @Override
            public String getNodeId(Node node) {return myMainNodeRenderer.getNodeId(node);}

            @Override
            public DataHolder getOptions() {return myMainNodeRenderer.getOptions();}

            @Override
            public HtmlRendererOptions getHtmlOptions() {return myMainNodeRenderer.getHtmlOptions();}

            @Override
            public Document getDocument() {return myMainNodeRenderer.getDocument();}

            @Override
            public RenderingPhase getRenderingPhase() {return myMainNodeRenderer.getRenderingPhase();}

            @Override
            public String encodeUrl(String url) {return myMainNodeRenderer.encodeUrl(url);}

            @Override
            public Attributes extendRenderingNodeAttributes(AttributablePart part, Attributes attributes) {
                return myMainNodeRenderer.extendRenderingNodeAttributes(
                        part,
                        attributes
                );
            }

            @Override
            public void render(Node node) {
                myMainNodeRenderer.renderNode(node, this);
            }

            @Override
            public Node getCurrentNode() {
                return myMainNodeRenderer.getCurrentNode();
            }

            @Override
            public ResolvedLink resolveLink(LinkType linkType, String url, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(linkType, url, null);
            }

            @Override
            public NodeRendererContext getSubContext(Appendable out, boolean inheritIndent) {
                HtmlWriter htmlWriter = new HtmlWriter(this.htmlWriter, out, inheritIndent);
                htmlWriter.setContext(this);
                //noinspection ReturnOfInnerClass
                return new SubNodeRenderer(myMainNodeRenderer, htmlWriter);
            }

            @Override
            public void renderChildren(Node parent) {
                myMainNodeRenderer.renderChildrenNode(parent, this);
            }

            @Override
            public HtmlWriter getHtmlWriter() { return htmlWriter; }

            protected int getDoNotRenderLinksNesting() {return super.getDoNotRenderLinksNesting();}

            @Override
            public boolean isDoNotRenderLinks() {return super.isDoNotRenderLinks();}

            @Override
            public void doNotRenderLinks(boolean doNotRenderLinks) {super.doNotRenderLinks(doNotRenderLinks);}

            @Override
            public void doNotRenderLinks() {super.doNotRenderLinks();}

            @Override
            public void doRenderLinks() {super.doRenderLinks();}
        }
    }
}
