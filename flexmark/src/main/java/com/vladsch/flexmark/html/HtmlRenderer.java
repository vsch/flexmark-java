package com.vladsch.flexmark.html;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.internal.util.*;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.HtmlBlock;
import com.vladsch.flexmark.node.HtmlInline;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;

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
public class HtmlRenderer {
    final static public DataKey<String> SOFT_BREAK = new DataKey<>("SOFT_BREAK", "\n");
    final static public DataKey<Boolean> PERCENT_ENCODE_URLS = new DataKey<>("ESCAPE_HTML", false);
    final static public DataKey<Integer> INDENT_SIZE = new DataKey<>("INDENT", 0);
    final public static DataKey<Boolean> LISTS_ORDERED_START = new DataKey<>("LISTS_ORDERED_START", true);
    final static public DataKey<Boolean> ESCAPE_HTML = new DataKey<>("ESCAPE_HTML", false);
    final static public DataKey<Boolean> ESCAPE_HTML_BLOCKS = new DynamicDefaultKey<>("ESCAPE_HTML_BLOCKS", ESCAPE_HTML::getFrom);
    final static public DataKey<Boolean> ESCAPE_HTML_COMMENT_BLOCKS = new DynamicDefaultKey<>("ESCAPE_HTML_COMMENT_BLOCKS", ESCAPE_HTML_BLOCKS::getFrom);
    final static public DataKey<Boolean> ESCAPE_INLINE_HTML = new DynamicDefaultKey<>("ESCAPE_HTML_BLOCKS", ESCAPE_HTML::getFrom);
    final static public DataKey<Boolean> ESCAPE_INLINE_HTML_COMMENTS = new DynamicDefaultKey<>("ESCAPE_INLINE_HTML_COMMENTS", ESCAPE_INLINE_HTML::getFrom);
    final static public DataKey<Boolean> SUPPRESS_HTML = new DataKey<>("SUPPRESS_HTML", false);
    final static public DataKey<Boolean> SUPPRESS_HTML_BLOCKS = new DynamicDefaultKey<>("SUPPRESS_HTML_BLOCKS", SUPPRESS_HTML::getFrom);
    final static public DataKey<Boolean> SUPPRESS_HTML_COMMENT_BLOCKS = new DynamicDefaultKey<>("SUPPRESS_HTML_COMMENT_BLOCKS", SUPPRESS_HTML_BLOCKS::getFrom);
    final static public DataKey<Boolean> SUPPRESS_INLINE_HTML = new DynamicDefaultKey<>("SUPPRESS_INLINE_HTML", SUPPRESS_HTML::getFrom);
    final static public DataKey<Boolean> SUPPRESS_INLINE_HTML_COMMENT = new DynamicDefaultKey<>("SUPPRESS_INLINE_HTML_COMMENT", SUPPRESS_INLINE_HTML::getFrom);

    private final List<AttributeProvider> attributeProviders;
    private final List<NodeRendererFactory> nodeRendererFactories;
    private final HtmlRendererOptions htmlOptions;
    private final DataHolder options;
    private final Builder builder;

    private HtmlRenderer(Builder builder) {
        this.builder = new Builder(builder); // take a copy to avoid after creation side effects
        this.options = new DataSet(builder);
        this.htmlOptions = new HtmlRendererOptions(this.options);

        this.attributeProviders = builder.attributeProviders;
        this.nodeRendererFactories = new ArrayList<>(builder.nodeRendererFactories.size() + 1);
        this.nodeRendererFactories.addAll(builder.nodeRendererFactories);
        // Add as last. This means clients can override the rendering of core nodes if they want.
        this.nodeRendererFactories.add(new NodeRendererFactory() {
            @Override
            public NodeRenderer create(NodeRendererContext context) {
                return new CoreNodeRenderer(context);
            }
        });
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
     * @return a builder
     */
    public static Builder builder(DataHolder options) {
        return new Builder(options);
    }

    public void render(Node node, Appendable output) {
        MainNodeRenderer renderer = new MainNodeRenderer(options, new HtmlWriter(output, htmlOptions.indentSize), node.getDocument());
        renderer.render(node);
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
        private List<AttributeProvider> attributeProviders = new ArrayList<>();
        private List<NodeRendererFactory> nodeRendererFactories = new ArrayList<>();
        private final HashSet<HtmlRendererExtension> loadedExtensions = new HashSet<>();

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

            this.attributeProviders.addAll(other.attributeProviders);
            this.nodeRendererFactories.addAll(other.nodeRendererFactories);
            this.loadedExtensions.addAll(other.loadedExtensions);
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
        public Builder percentEncodeUrls(boolean percentEncodeUrls) {
            this.set(PERCENT_ENCODE_URLS, percentEncodeUrls);
            return this;
        }

        /**
         * Add an attribute provider for adding/changing HTML attributes to the rendered tags.
         *
         * @param attributeProvider the attribute provider to add
         * @return {@code this}
         */
        public Builder attributeProvider(AttributeProvider attributeProvider) {
            this.attributeProviders.add(attributeProvider);
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
         * @param extensions extensions to use on this HTML renderer
         * @return {@code this}
         */
        public Builder extensions(Iterable<? extends Extension> extensions) {
            for (Extension extension : extensions) {
                if (extension instanceof HtmlRendererExtension) {
                    if (!loadedExtensions.contains(extension)) {
                        HtmlRendererExtension htmlRendererExtension = (HtmlRendererExtension) extension;
                        htmlRendererExtension.extend(this);
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
        void extend(Builder rendererBuilder);
    }

    private class MainNodeRenderer implements NodeRendererContext {
        private final HtmlWriter htmlWriter;
        private final Document document;
        private final Map<Class<? extends Node>, NodeRenderer> renderers;
        private final List<PhasedNodeRenderer> phasedRenderers;
        private final Set<RenderingPhase> renderingPhases;
        private final DataHolder options;
        private RenderingPhase phase;
        private Node renderingNode;

        private MainNodeRenderer(DataHolder options, HtmlWriter htmlWriter, Document document) {
            this.options = new ScopedDataSet(options, document);
            this.htmlWriter = htmlWriter;
            this.document = document;
            this.renderers = new HashMap<>(32);
            this.renderingPhases = new HashSet<>(RenderingPhase.values().length);
            this.phasedRenderers = new ArrayList<>(nodeRendererFactories.size());
            htmlWriter.setContext(this);

            // The first node renderer for a node type "wins".
            for (int i = nodeRendererFactories.size() - 1; i >= 0; i--) {
                NodeRendererFactory nodeRendererFactory = nodeRendererFactories.get(i);
                NodeRenderer nodeRenderer = nodeRendererFactory.create(this);
                for (Class<? extends Node> nodeType : nodeRenderer.getNodeTypes()) {
                    // Overwrite existing renderer
                    renderers.put(nodeType, nodeRenderer);
                }

                if (nodeRenderer instanceof PhasedNodeRenderer) {
                    this.renderingPhases.addAll(((PhasedNodeRenderer) nodeRenderer).getRenderingPhases());
                    this.phasedRenderers.add((PhasedNodeRenderer) nodeRenderer);
                }
            }
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
        public Map<String, String> extendRenderingNodeAttributes(String tag, Map<String, String> attributes) {
            Map<String, String> attr = new LinkedHashMap<>(attributes);
            for (AttributeProvider attributeProvider : attributeProviders) {
                attributeProvider.setAttributes(renderingNode, tag, attr);
            }
            return attr;
        }

        @Override
        public HtmlWriter getHtmlWriter() {
            return htmlWriter;
        }

        @Override
        public void render(Node node) {
            if (node instanceof Document) {
                // here we render multiple phases
                for (RenderingPhase phase : RenderingPhase.values()) {
                    if (phase != RenderingPhase.BODY && !renderingPhases.contains(phase)) { continue; }
                    this.phase = phase;

                    if (phase == RenderingPhase.BODY) {
                        NodeRenderer nodeRenderer = renderers.get(node.getClass());
                        if (nodeRenderer != null) {
                            renderingNode = node;
                            nodeRenderer.render(node);
                            renderingNode = null;
                        }
                    } else {
                        // go through all renderers that want this phase
                        for (PhasedNodeRenderer phasedRenderer : phasedRenderers) {
                            this.phase = phase;
                            if (phasedRenderer.getRenderingPhases().contains(phase)) {
                                renderingNode = node;
                                phasedRenderer.render(node, phase);
                                renderingNode = null;
                            }
                        }
                    }
                }
            } else {
                NodeRenderer nodeRenderer = renderers.get(node.getClass());
                if (nodeRenderer != null) {
                    Node oldNode = renderingNode;
                    renderingNode = node;
                    nodeRenderer.render(node);
                    renderingNode = oldNode;
                }
            }
        }

        @Override
        public void renderChildren(Node parent) {
            Node node = parent.getFirstChild();
            while (node != null) {
                Node next = node.getNext();
                render(node);
                node = next;
            }
        }
    }
}
