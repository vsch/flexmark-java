package com.vladsch.flexmark.html;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.MutableOptions;
import com.vladsch.flexmark.internal.util.Options;
import com.vladsch.flexmark.internal.util.PropertyKey;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.HtmlBlock;
import com.vladsch.flexmark.node.HtmlInline;
import com.vladsch.flexmark.node.Node;

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
    final static public PropertyKey<String> SOFTBREAK = new PropertyKey<>("HTML.SOFT_BREAK", "\n");
    final static public PropertyKey<Boolean> ESCAPE_HTML = new PropertyKey<>("HTML.ESCAPE_HTML", false);
    final static public PropertyKey<Boolean> PERCENT_ENCODE_URLS = new PropertyKey<>("HTML.ESCAPE_HTML", false);
    final static public PropertyKey<Integer> INDENT_SIZE = new PropertyKey<>("HTML.INDENT", 0);

    static class HtmlRendererOptions {
        public final String softbreak;
        public final boolean escapeHtml;
        public final boolean percentEncodeUrls;
        public final int indentSize;

        public HtmlRendererOptions(Options options) {
            softbreak = options.getOrDefault(SOFTBREAK);
            escapeHtml = options.getOrDefault(ESCAPE_HTML);
            percentEncodeUrls = options.getOrDefault(PERCENT_ENCODE_URLS);
            indentSize = options.getOrDefault(INDENT_SIZE);
        }
    }

    private final List<AttributeProvider> attributeProviders;
    private final List<NodeRendererFactory> nodeRendererFactories;
    private final HtmlRendererOptions htmlOptions;
    private final Options options;

    private HtmlRenderer(Builder builder) {
        this.options = builder.options.getReadOnlyCopy();
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

    public int getIndentSize() {
        return htmlOptions.indentSize;
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
    public static Builder builder(Options options) {
        return new Builder(options);
    }

    public void render(Node node, Appendable output) {
        MainNodeRenderer renderer = new MainNodeRenderer(new HtmlWriter(output, htmlOptions.indentSize));
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

    /**
     * Builder for configuring an {@link HtmlRenderer}. See methods for default configuration.
     */
    public static class Builder {
        public final MutableOptions options;
        private List<AttributeProvider> attributeProviders = new ArrayList<>();
        private List<NodeRendererFactory> nodeRendererFactories = new ArrayList<>();

        public Builder() {
            options = new MutableOptions();
        }

        public Builder(Options options) {
            this.options = new MutableOptions(options);
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
         * @param softbreak HTML for softbreak
         * @return {@code this}
         */
        public Builder softbreak(String softbreak) {
            this.options.set(SOFTBREAK, softbreak);
            return this;
        }

        /**
         * The size of the indent to use for hierarchical elements, default 0, means no indent, also fastest rendering
         *
         * @param indentSize number of spaces per indent
         * @return {@code this}
         */
        public Builder indentSize(int indentSize) {
            this.options.set(INDENT_SIZE, indentSize);
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
            this.options.set(ESCAPE_HTML, escapeHtml);
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
            this.options.set(PERCENT_ENCODE_URLS, percentEncodeUrls);
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
                    HtmlRendererExtension htmlRendererExtension = (HtmlRendererExtension) extension;
                    htmlRendererExtension.extend(this);
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
        private final Map<Class<? extends Node>, NodeRenderer> renderers;
        private final List<PhasedNodeRenderer> phasedRenderers;
        private final Set<RenderingPhase> renderingPhases;
        private RenderingPhase phase;
        private Node renderingNode;

        private MainNodeRenderer(HtmlWriter htmlWriter) {
            this.htmlWriter = htmlWriter;
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
        public RenderingPhase getRenderingPhase() {
            return phase;
        }

        @Override
        public boolean shouldEscapeHtml() {
            return htmlOptions.escapeHtml;
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
        public Map<String, String> extendRenderingNodeAttributes(Map<String, String> attributes) {
            return extendAttributes(renderingNode, attributes);
        }

        @Override
        public Map<String, String> extendAttributes(Node node, Map<String, String> attributes) {
            Map<String, String> attrs = new LinkedHashMap<>(attributes);
            setCustomAttributes(node, attrs);
            return attrs;
        }

        @Override
        public HtmlWriter getHtmlWriter() {
            return htmlWriter;
        }

        @Override
        public String getSoftbreak() {
            return htmlOptions.softbreak;
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

        private void setCustomAttributes(Node node, Map<String, String> attrs) {
            for (AttributeProvider attributeProvider : attributeProviders) {
                attributeProvider.setAttributes(node, attrs);
            }
        }
    }
}
