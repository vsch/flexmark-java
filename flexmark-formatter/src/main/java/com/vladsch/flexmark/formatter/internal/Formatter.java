package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.IRender;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.*;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.options.*;

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
public class Formatter implements IRender {
    /**
     * output control for FormattingAppendable, see {@link FormattingAppendable#setOptions(int)}
     */
    public static final DataKey<Integer> FORMAT_FLAGS = new DataKey<>("FORMAT_FLAGS", 0);
    public static final DataKey<Integer> MAX_TRAILING_BLANK_LINES = new DataKey<>("MAX_TRAILING_BLANK_LINES", 1);

    // for convenience or these together and set FORMAT_FLAGS key above to the value, to have HtmlWriter apply these when rendering Html
    public static final int FORMAT_CONVERT_TABS = FormattingAppendable.CONVERT_TABS;
    public static final int FORMAT_COLLAPSE_WHITESPACE = FormattingAppendable.COLLAPSE_WHITESPACE;
    public static final int FORMAT_SUPPRESS_TRAILING_WHITESPACE = FormattingAppendable.SUPPRESS_TRAILING_WHITESPACE;
    public static final int FORMAT_ALL_OPTIONS = FormattingAppendable.FORMAT_ALL;

    private final List<NodeFormatterFactory> nodeFormatterFactories;
    private final FormatterOptions formatterOptions;
    private final DataHolder options;
    private final Builder builder;

    Formatter(Builder builder) {
        this.builder = new Builder(builder); // take a copy to avoid after creation side effects
        this.options = new DataSet(builder);
        this.formatterOptions = new FormatterOptions(this.options);
        this.nodeFormatterFactories = new ArrayList<>(builder.nodeFormatterFactories.size() + 1);
        this.nodeFormatterFactories.addAll(builder.nodeFormatterFactories);

        // Add as last. This means clients can override the rendering of core nodes if they want.
        this.nodeFormatterFactories.add(new NodeFormatterFactory() {
            @Override
            public NodeFormatter create(DataHolder options) {
                return new CoreNodeFormatter(options);
            }
        });

    }

    /**
     * Create a new builder for configuring an {@link Formatter}.
     *
     * @return a builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Create a new builder for configuring an {@link Formatter}.
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
        MainNodeFormatter renderer = new MainNodeFormatter(options, new MarkdownWriter(output, formatterOptions.formatFlags), node.getDocument());
        renderer.render(node);
        renderer.flush(formatterOptions.maxTrailingBlankLines);
    }

    /**
     * Render a node to the appendable
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void render(Node node, Appendable output, int maxTrailingBlankLines) {
        MainNodeFormatter renderer = new MainNodeFormatter(options, new MarkdownWriter(output, formatterOptions.formatFlags), node.getDocument());
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

    public Formatter withOptions(DataHolder options) {
        return options == null ? this : new Formatter(new Builder(builder, options));
    }

    /**
     * Builder for configuring an {@link Formatter}. See methods for default configuration.
     */
    public static class Builder extends MutableDataSet {
        List<AttributeProviderFactory> attributeProviderFactories = new ArrayList<>();
        List<NodeFormatterFactory> nodeFormatterFactories = new ArrayList<>();
        List<LinkResolverFactory> linkResolverFactories = new ArrayList<>();
        private final HashSet<FormatterExtension> loadedExtensions = new HashSet<>();
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
            this.nodeFormatterFactories.addAll(other.nodeFormatterFactories);
            this.linkResolverFactories.addAll(other.linkResolverFactories);
            this.loadedExtensions.addAll(other.loadedExtensions);
            this.htmlIdGeneratorFactory = other.htmlIdGeneratorFactory;
        }

        public Builder(Builder other, DataHolder options) {
            super(other);

            List<Extension> extensions = new ArrayList<Extension>();
            for (Extension extension : get(Parser.EXTENSIONS)) {
                extensions.add(extension);
            }

            if (options != null) {
                for (DataKey key : options.keySet()) {
                    if (key == Parser.EXTENSIONS) {
                        for (Extension extension : options.get(Parser.EXTENSIONS)) {
                            extensions.add(extension);
                        }
                    } else {
                        set(key, options.get(key));
                    }
                }
            }

            set(Parser.EXTENSIONS, extensions);
            extensions(extensions);
        }

        /**
         * @return the configured {@link Formatter}
         */
        public Formatter build() {
            return new Formatter(this);
        }

        /**
         * Add a factory for instantiating a node renderer (done when rendering). This allows to override the rendering
         * of node types or define rendering for custom node types.
         * <p>
         * If multiple node renderers for the same node type are created, the one from the factory that was added first
         * "wins". (This is how the rendering for core node types can be overridden; the default rendering comes last.)
         *
         * @param nodeFormatterFactory the factory for creating a node renderer
         * @return {@code this}
         */
        public Builder nodeFormatterFactory(NodeFormatterFactory nodeFormatterFactory) {
            this.nodeFormatterFactories.add(nodeFormatterFactory);
            return this;
        }

        /**
         * @param extensions extensions to use on this HTML renderer
         * @return {@code this}
         */
        public Builder extensions(Iterable<? extends Extension> extensions) {
            // first give extensions a chance to modify options
            for (Extension extension : extensions) {
                if (extension instanceof FormatterExtension) {
                    if (!loadedExtensions.contains(extension)) {
                        FormatterExtension formatterExtension = (FormatterExtension) extension;
                        formatterExtension.rendererOptions(this);
                    }
                }
            }

            for (Extension extension : extensions) {
                if (extension instanceof FormatterExtension) {
                    if (!loadedExtensions.contains(extension)) {
                        FormatterExtension formatterExtension = (FormatterExtension) extension;
                        formatterExtension.extend(this);
                        loadedExtensions.add(formatterExtension);
                    }
                }
            }
            return this;
        }
    }

    /**
     * Extension for {@link Formatter}.
     */
    public interface FormatterExtension extends Extension {
        /**
         * This method is called first on all extensions so that they can adjust the options.
         * @param options option set that will be used for the builder
         */
        void rendererOptions(MutableDataHolder options);

        void extend(Builder builder);
    }

    private class MainNodeFormatter extends NodeFormatterSubContext implements NodeFormatterContext {
        private final Document document;
        private final Map<Class<?>, NodeFormattingHandler> renderers;

        private final List<PhasedNodeFormatter> phasedFormatters;
        private final Set<FormattingPhase> renderingPhases;
        private final DataHolder options;
        private FormattingPhase phase;

        MainNodeFormatter(DataHolder options, MarkdownWriter out, Document document) {
            super(out);
            this.options = new ScopedDataSet(options, document);
            this.document = document;
            this.renderers = new HashMap<>(32);
            this.renderingPhases = new HashSet<>(FormattingPhase.values().length);
            this.phasedFormatters = new ArrayList<>(nodeFormatterFactories.size());

            out.setContext(this);

            // The first node renderer for a node type "wins".
            for (int i = nodeFormatterFactories.size() - 1; i >= 0; i--) {
                NodeFormatterFactory nodeFormatterFactory = nodeFormatterFactories.get(i);
                NodeFormatter nodeFormatter = nodeFormatterFactory.create(this.getOptions());
                for (NodeFormattingHandler nodeType : nodeFormatter.getNodeFormattingHandlers()) {
                    // Overwrite existing renderer
                    renderers.put(nodeType.getNodeType(), nodeType);
                }

                if (nodeFormatter instanceof PhasedNodeFormatter) {
                    this.renderingPhases.addAll(((PhasedNodeFormatter) nodeFormatter).getFormattingPhases());
                    this.phasedFormatters.add((PhasedNodeFormatter) nodeFormatter);
                }
            }
        }

        @Override
        public Node getCurrentNode() {
            return renderingNode;
        }

        @Override
        public DataHolder getOptions() {
            return options;
        }

        @Override
        public FormatterOptions getFormatterOptions() {
            return formatterOptions;
        }

        @Override
        public Document getDocument() {
            return document;
        }

        @Override
        public FormattingPhase getFormattingPhase() {
            return phase;
        }

        @Override
        public void render(Node node) {
            renderNode(node, this);
        }

        @Override
        public NodeFormatterContext getSubContext(Appendable out) {
            MarkdownWriter writer = new MarkdownWriter(out, getMarkdown().getOptions());
            writer.setContext(this);
            //noinspection ReturnOfInnerClass
            return new SubNodeFormatter(this, writer);
        }

        void renderNode(Node node, NodeFormatterSubContext subContext) {
            if (node instanceof Document) {
                // here we render multiple phases
                for (FormattingPhase phase : FormattingPhase.values()) {
                    if (phase != FormattingPhase.DOCUMENT && !renderingPhases.contains(phase)) { continue; }
                    this.phase = phase;
                    // here we render multiple phases
                    if (getFormattingPhase() == FormattingPhase.DOCUMENT) {
                        NodeFormattingHandler nodeRenderer = renderers.get(node.getClass());
                        if (nodeRenderer != null) {
                            subContext.renderingNode = node;
                            nodeRenderer.render(node, subContext, subContext.markdown);
                            subContext.renderingNode = null;
                        }
                    } else {
                        // go through all renderers that want this phase
                        for (PhasedNodeFormatter phasedFormatter : phasedFormatters) {
                            if (phasedFormatter.getFormattingPhases().contains(phase)) {
                                subContext.renderingNode = node;
                                phasedFormatter.renderDocument(subContext, subContext.markdown, (Document) node, phase);
                                subContext.renderingNode = null;
                            }
                        }
                    }
                }
            } else {
                NodeFormattingHandler nodeRenderer = renderers.get(node.getClass());
                if (nodeRenderer == null) renderers.get(Node.class);

                if (nodeRenderer != null) {
                    Node oldNode = this.renderingNode;
                    subContext.renderingNode = node;
                    nodeRenderer.render(node, subContext, subContext.markdown);
                    subContext.renderingNode = oldNode;
                } else {
                    // default behavior is controlled by generic Node.class that is implemented in CoreNodeFormatter
                    throw new IllegalStateException("Core Node Formatter should implement generic Node renderer");
                }
            }
        }

        public void renderChildren(Node parent) {
            renderChildrenNode(parent, this);
        }

        @SuppressWarnings("WeakerAccess")
        protected void renderChildrenNode(Node parent, NodeFormatterSubContext subContext) {
            Node node = parent.getFirstChild();
            while (node != null) {
                Node next = node.getNext();
                renderNode(node, subContext);
                node = next;
            }
        }

        @SuppressWarnings("WeakerAccess")
        private class SubNodeFormatter extends NodeFormatterSubContext implements NodeFormatterContext {
            private final MainNodeFormatter myMainNodeRenderer;

            public SubNodeFormatter(MainNodeFormatter mainNodeRenderer, MarkdownWriter out) {
                super(out);
                myMainNodeRenderer = mainNodeRenderer;
            }

            @Override
            public DataHolder getOptions() {return myMainNodeRenderer.getOptions();}

            @Override
            public FormatterOptions getFormatterOptions() {return myMainNodeRenderer.getFormatterOptions();}

            @Override
            public Document getDocument() {return myMainNodeRenderer.getDocument();}

            @Override
            public FormattingPhase getFormattingPhase() {return myMainNodeRenderer.getFormattingPhase();}

            @Override
            public void render(Node node) {
                myMainNodeRenderer.renderNode(node, this);
            }

            @Override
            public Node getCurrentNode() {
                return myMainNodeRenderer.getCurrentNode();
            }

            @Override
            public NodeFormatterContext getSubContext(Appendable out) {
                MarkdownWriter htmlWriter = new MarkdownWriter(out, this.markdown.getOptions());
                htmlWriter.setContext(this);
                //noinspection ReturnOfInnerClass
                return new SubNodeFormatter(myMainNodeRenderer, htmlWriter);
            }

            @Override
            public void renderChildren(Node parent) {
                myMainNodeRenderer.renderChildrenNode(parent, this);
            }

            @Override
            public MarkdownWriter getMarkdown() { return markdown; }
        }
    }
}
