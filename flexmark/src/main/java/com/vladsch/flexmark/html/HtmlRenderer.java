package com.vladsch.flexmark.html;

import com.vladsch.flexmark.ast.HtmlBlock;
import com.vladsch.flexmark.ast.HtmlInline;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.IRender;
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.builder.BuilderBase;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.collection.DynamicDefaultKey;
import com.vladsch.flexmark.util.dependency.DependencyHandler;
import com.vladsch.flexmark.util.dependency.FlatDependencyHandler;
import com.vladsch.flexmark.util.dependency.ResolvedDependencies;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.options.*;
import com.vladsch.flexmark.util.sequence.TagRange;

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
    public static final DataKey<String> STRONG_EMPHASIS_STYLE_HTML_OPEN = new DataKey<>("STRONG_EMPHASIS_STYLE_HTML_OPEN", (String) null);
    public static final DataKey<String> STRONG_EMPHASIS_STYLE_HTML_CLOSE = new DataKey<>("STRONG_EMPHASIS_STYLE_HTML_CLOSE", (String) null);
    public static final DataKey<String> EMPHASIS_STYLE_HTML_OPEN = new DataKey<>("EMPHASIS_STYLE_HTML_OPEN", (String) null);
    public static final DataKey<String> EMPHASIS_STYLE_HTML_CLOSE = new DataKey<>("EMPHASIS_STYLE_HTML_CLOSE", (String) null);
    public static final DataKey<String> CODE_STYLE_HTML_OPEN = new DataKey<>("CODE_STYLE_HTML_OPEN", (String) null);
    public static final DataKey<String> CODE_STYLE_HTML_CLOSE = new DataKey<>("CODE_STYLE_HTML_CLOSE", (String) null);
    public static final DataKey<String> INLINE_CODE_SPLICE_CLASS = new DataKey<>("INLINE_CODE_SPLICE_CLASS", (String) null);
    public static final DataKey<Boolean> PERCENT_ENCODE_URLS = new DataKey<>("PERCENT_ENCODE_URLS", false);
    public static final DataKey<Integer> INDENT_SIZE = new DataKey<>("INDENT_SIZE", 0);
    public static final DataKey<Boolean> ESCAPE_HTML = new DataKey<>("ESCAPE_HTML", false);
    public static final DataKey<Boolean> ESCAPE_HTML_BLOCKS = new DynamicDefaultKey<Boolean>("ESCAPE_HTML_BLOCKS", ESCAPE_HTML);
    public static final DataKey<Boolean> ESCAPE_HTML_COMMENT_BLOCKS = new DynamicDefaultKey<Boolean>("ESCAPE_HTML_COMMENT_BLOCKS", ESCAPE_HTML_BLOCKS);
    public static final DataKey<Boolean> ESCAPE_INLINE_HTML = new DynamicDefaultKey<Boolean>("ESCAPE_HTML_BLOCKS", ESCAPE_HTML);
    public static final DataKey<Boolean> ESCAPE_INLINE_HTML_COMMENTS = new DynamicDefaultKey<Boolean>("ESCAPE_INLINE_HTML_COMMENTS", ESCAPE_INLINE_HTML);
    public static final DataKey<Boolean> SUPPRESS_HTML = new DataKey<>("SUPPRESS_HTML", false);
    public static final DataKey<Boolean> SUPPRESS_HTML_BLOCKS = new DynamicDefaultKey<Boolean>("SUPPRESS_HTML_BLOCKS", SUPPRESS_HTML);
    public static final DataKey<Boolean> SUPPRESS_HTML_COMMENT_BLOCKS = new DynamicDefaultKey<Boolean>("SUPPRESS_HTML_COMMENT_BLOCKS", SUPPRESS_HTML_BLOCKS);
    public static final DataKey<Boolean> SUPPRESS_INLINE_HTML = new DynamicDefaultKey<Boolean>("SUPPRESS_INLINE_HTML", SUPPRESS_HTML);
    public static final DataKey<Boolean> SUPPRESS_INLINE_HTML_COMMENTS = new DynamicDefaultKey<Boolean>("SUPPRESS_INLINE_HTML_COMMENTS", SUPPRESS_INLINE_HTML);
    public static final DataKey<Boolean> SOURCE_WRAP_HTML = new DataKey<>("SOURCE_WRAP_HTML", false);
    public static final DataKey<Boolean> SOURCE_WRAP_HTML_BLOCKS = new DynamicDefaultKey<Boolean>("SOURCE_WRAP_HTML_BLOCKS", SOURCE_WRAP_HTML);
    //public static final DataKey<Boolean> SOURCE_WRAP_INLINE_HTML = new DynamicDefaultKey<>("SOURCE_WRAP_INLINE_HTML", SOURCE_WRAP_HTML::getFrom);
    public static final DataKey<Boolean> HEADER_ID_GENERATOR_RESOLVE_DUPES = new DataKey<>("HEADER_ID_GENERATOR_RESOLVE_DUPES", true);
    public static final DataKey<String> HEADER_ID_GENERATOR_TO_DASH_CHARS = new DataKey<>("HEADER_ID_GENERATOR_TO_DASH_CHARS", " -_");
    public static final DataKey<String> HEADER_ID_GENERATOR_NON_DASH_CHARS = new DataKey<>("HEADER_ID_GENERATOR_NON_DASH_CHARS", "");
    public static final DataKey<Boolean> HEADER_ID_GENERATOR_NO_DUPED_DASHES = new DataKey<>("HEADER_ID_GENERATOR_NO_DUPED_DASHES", false);
    public static final DataKey<Boolean> HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE = new DataKey<>("HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE", true);
    public static final DataKey<Boolean> RENDER_HEADER_ID = new DataKey<>("RENDER_HEADER_ID", false);
    public static final DataKey<Boolean> GENERATE_HEADER_ID = new DataKey<>("GENERATE_HEADER_ID", true);
    public static final DataKey<Boolean> DO_NOT_RENDER_LINKS = new DataKey<>("DO_NOT_RENDER_LINKS", false);
    public static final DataKey<String> FENCED_CODE_LANGUAGE_CLASS_PREFIX = new DataKey<>("FENCED_CODE_LANGUAGE_CLASS_PREFIX", "language-");
    public static final DataKey<String> FENCED_CODE_NO_LANGUAGE_CLASS = new DataKey<>("FENCED_CODE_NO_LANGUAGE_CLASS", "");
    public static final DataKey<String> SOURCE_POSITION_ATTRIBUTE = new DataKey<>("SOURCE_POSITION_ATTRIBUTE", "");
    public static final DataKey<Boolean> SOURCE_POSITION_PARAGRAPH_LINES = new DataKey<>("SOURCE_POSITION_PARAGRAPH_LINES", false);
    public static final DataKey<String> TYPE = new DataKey<>("TYPE", "HTML");
    public static final DataKey<ArrayList<TagRange>> TAG_RANGES = new DataKey<ArrayList<TagRange>>("TAG_RANGES", new DataValueFactory<ArrayList<TagRange>>() {
        @Override
        public ArrayList<TagRange> create(DataHolder value) {
            return new ArrayList<TagRange>();
        }
    });

    public static final DataKey<Boolean> RECHECK_UNDEFINED_REFERENCES = new DataKey<>("RECHECK_UNDEFINED_REFERENCES", false);
    public static final DataKey<Boolean> OBFUSCATE_EMAIL = new DataKey<>("OBFUSCATE_EMAIL", false);
    public static final DataKey<Boolean> OBFUSCATE_EMAIL_RANDOM = new DataKey<>("OBFUSCATE_EMAIL_RANDOM", true);
    public static final DataKey<Boolean> HTML_BLOCK_OPEN_TAG_EOL = new DataKey<>("HTML_BLOCK_OPEN_TAG_EOL", true);
    public static final DataKey<Boolean> HTML_BLOCK_CLOSE_TAG_EOL = new DataKey<>("HTML_BLOCK_CLOSE_TAG_EOL", true);
    public static final DataKey<Boolean> UNESCAPE_HTML_ENTITIES = new DataKey<>("UNESCAPE_HTML_ENTITIES", true);
    //public static final DataKey<Boolean> WRAP_TIGHT_ITEM_PARAGRAPH_IN_SPAN = new DataKey<>("WRAP_TIGHT_ITEM_PARAGRAPH_IN_SPAN", false);
    public static final DataKey<String> AUTOLINK_WWW_PREFIX = new DataKey<>("AUTOLINK_WWW_PREFIX", "http://");

    // regex for suppressed link prefixes
    public static final DataKey<String> SUPPRESSED_LINKS = new DataKey<>("SUPPRESSED_LINKS", "javascript:.*");
    public static final DataKey<Boolean> NO_P_TAGS_USE_BR = new DataKey<>("NO_P_TAGS_USE_BR", false);
    public static final DataKey<Boolean> EMBEDDED_ATTRIBUTE_PROVIDER = new DataKey<>("EMBEDDED_ATTRIBUTE_PROVIDER", true);

    /**
     * output control for FormattingAppendable, see {@link com.vladsch.flexmark.util.html.FormattingAppendable#setOptions(int)}
     */
    public static final DataKey<Integer> FORMAT_FLAGS = new DataKey<>("FORMAT_FLAGS", 0);
    public static final DataKey<Integer> MAX_TRAILING_BLANK_LINES = new DataKey<>("MAX_TRAILING_BLANK_LINES", 1);

    // convenience pass through
    public static final int CONVERT_TABS = FormattingAppendable.CONVERT_TABS;
    public static final int COLLAPSE_WHITESPACE = FormattingAppendable.COLLAPSE_WHITESPACE;
    public static final int SUPPRESS_TRAILING_WHITESPACE = FormattingAppendable.SUPPRESS_TRAILING_WHITESPACE;
    public static final int PREFIX_AFTER_PENDING_EOL = FormattingAppendable.PREFIX_AFTER_PENDING_EOL;
    public static final int PASS_THROUGH = FormattingAppendable.PASS_THROUGH;
    public static final int ALLOW_LEADING_WHITESPACE = FormattingAppendable.ALLOW_LEADING_WHITESPACE;
    public static final int FORMAT_ALL = FormattingAppendable.FORMAT_ALL;

    /**
     * Stores pairs of equivalent renderer types to allow extensions to resolve types not known to them
     * <p>
     * Pair contains: rendererType, equivalentType
     */
    public static final DataKey<List<Pair<String, String>>> RENDERER_TYPE_EQUIVALENCE = new DataKey<List<Pair<String, String>>>("RENDERER_TYPE_EQUIVALENCE", Collections.<Pair<String, String>>emptyList());

    // for convenience or these together and set FORMAT_FLAGS key above to the value, to have HtmlWriter apply these when rendering Html
    public static final int FORMAT_CONVERT_TABS = FormattingAppendable.CONVERT_TABS;
    public static final int FORMAT_COLLAPSE_WHITESPACE = FormattingAppendable.COLLAPSE_WHITESPACE;
    public static final int FORMAT_SUPPRESS_TRAILING_WHITESPACE = FormattingAppendable.SUPPRESS_TRAILING_WHITESPACE;
    public static final int FORMAT_ALL_OPTIONS = FormattingAppendable.FORMAT_ALL;

    // now not final only to allow disposal of resources
    private final List<AttributeProviderFactory> attributeProviderFactories;
    private final List<DelegatingNodeRendererFactoryWrapper> nodeRendererFactories;
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

        // resolve renderer dependencies
        final List<DelegatingNodeRendererFactoryWrapper> nodeRenderers = new ArrayList<DelegatingNodeRendererFactoryWrapper>(builder.nodeRendererFactories.size());

        for (int i = builder.nodeRendererFactories.size() - 1; i >= 0; i--) {
            final NodeRendererFactory nodeRendererFactory = builder.nodeRendererFactories.get(i);
            final Set<Class<? extends DelegatingNodeRendererFactoryWrapper>>[] myDelegates = new Set[] { null };

            nodeRenderers.add(new DelegatingNodeRendererFactoryWrapper(nodeRenderers, nodeRendererFactory));
        }

        // Add as last. This means clients can override the rendering of core nodes if they want by default
        final CoreNodeRenderer.Factory nodeRendererFactory = new CoreNodeRenderer.Factory();
        nodeRenderers.add(new DelegatingNodeRendererFactoryWrapper(nodeRenderers, nodeRendererFactory));

        RendererDependencyHandler resolver = new RendererDependencyHandler();
        nodeRendererFactories = resolver.resolveDependencies(nodeRenderers).getNodeRendererFactories();

        // KLUDGE: but for now works
        boolean addEmbedded = !builder.attributeProviderFactories.containsKey(EmbeddedAttributeProvider.Factory.getClass());
        List<AttributeProviderFactory> values = new ArrayList<>(builder.attributeProviderFactories.values());
        if (addEmbedded && EMBEDDED_ATTRIBUTE_PROVIDER.getFrom(options)) {
            // add it first so the rest can override it if needed
            values.add(0, EmbeddedAttributeProvider.Factory);
        }

        this.attributeProviderFactories = FlatDependencyHandler.computeDependencies(values);
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

    @Override
    public DataHolder getOptions() {
        return new DataSet(builder);
    }

    /**
     * Render a node to the appendable
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void render(Node node, Appendable output) {
        MainNodeRenderer renderer = new MainNodeRenderer(options, new HtmlWriter(output, htmlOptions.indentSize, htmlOptions.formatFlags, !htmlOptions.htmlBlockOpenTagEol, !htmlOptions.htmlBlockCloseTagEol), node.getDocument());
        renderer.render(node);
        renderer.flush(htmlOptions.maxTrailingBlankLines);
        renderer.dispose();
    }

    /**
     * Render a node to the appendable
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void render(Node node, Appendable output, int maxTrailingBlankLines) {
        MainNodeRenderer renderer = new MainNodeRenderer(options, new HtmlWriter(output, htmlOptions.indentSize, htmlOptions.formatFlags, !htmlOptions.htmlBlockOpenTagEol, !htmlOptions.htmlBlockCloseTagEol), node.getDocument());
        renderer.render(node);
        renderer.flush(maxTrailingBlankLines);
        renderer.dispose();
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

    static public boolean isCompatibleRendererType(final MutableDataHolder options, final String supportedRendererType) {
        final String rendererType = HtmlRenderer.TYPE.getFrom(options);
        return isCompatibleRendererType(options, rendererType, supportedRendererType);
    }

    static public boolean isCompatibleRendererType(final MutableDataHolder options, final String rendererType, final String supportedRendererType) {
        if (rendererType.equals(supportedRendererType)) {
            return true;
        }

        final List<Pair<String, String>> equivalence = RENDERER_TYPE_EQUIVALENCE.getFrom(options);

        for (Pair<String, String> pair : equivalence) {
            if (rendererType.equals(pair.getFirst())) {
                if (supportedRendererType.equals(pair.getSecond())) {
                    return true;
                }
            }
        }
        return false;
    }

    static public MutableDataHolder addRenderTypeEquivalence(final MutableDataHolder options, final String rendererType, final String supportedRendererType) {
        if (!isCompatibleRendererType(options, rendererType, supportedRendererType)) {
            // need to add
            final List<Pair<String, String>> equivalence = RENDERER_TYPE_EQUIVALENCE.getFrom(options);
            final ArrayList<Pair<String, String>> newEquivalence = new ArrayList<>(equivalence);
            newEquivalence.add(new Pair<String, String>(rendererType, supportedRendererType));
            options.set(RENDERER_TYPE_EQUIVALENCE, newEquivalence);
        }
        return options;
    }

    /**
     * Builder for configuring an {@link HtmlRenderer}. See methods for default configuration.
     */
    public static class Builder extends BuilderBase<Builder> implements RendererBuilder {
        Map<Class, AttributeProviderFactory> attributeProviderFactories = new LinkedHashMap<>();
        List<NodeRendererFactory> nodeRendererFactories = new ArrayList<NodeRendererFactory>();
        List<LinkResolverFactory> linkResolverFactories = new ArrayList<LinkResolverFactory>();
        HeaderIdGeneratorFactory htmlIdGeneratorFactory = null;

        public Builder() {
            super();
        }

        public Builder(DataHolder options) {
            super(options);
            loadExtensions();
        }

        public Builder(Builder other) {
            super(other);

            this.attributeProviderFactories.putAll(other.attributeProviderFactories);
            //this.nodeRendererFactories.addAll(other.nodeRendererFactories);
            this.linkResolverFactories.addAll(other.linkResolverFactories);
            this.htmlIdGeneratorFactory = other.htmlIdGeneratorFactory;
        }

        public Builder(Builder other, DataHolder options) {
            this(other);
            withOptions(options);
        }

        @Override
        protected void removeApiPoint(final Object apiPoint) {
            if (apiPoint instanceof AttributeProviderFactory) this.attributeProviderFactories.remove(apiPoint.getClass());
            else if (apiPoint instanceof NodeRendererFactory) this.nodeRendererFactories.remove(apiPoint);
            else if (apiPoint instanceof LinkResolverFactory) this.linkResolverFactories.remove(apiPoint);
            else if (apiPoint instanceof HeaderIdGeneratorFactory) this.htmlIdGeneratorFactory = null;
            else {
                throw new IllegalStateException("Unknown data point type: " + apiPoint.getClass().getName());
            }
        }

        @Override
        protected void preloadExtension(final Extension extension) {
            if (extension instanceof HtmlRendererExtension) {
                HtmlRendererExtension htmlRendererExtension = (HtmlRendererExtension) extension;
                htmlRendererExtension.rendererOptions(this);
            } else if (extension instanceof RendererExtension) {
                RendererExtension htmlRendererExtension = (RendererExtension) extension;
                htmlRendererExtension.rendererOptions(this);
            }
        }

        @Override
        protected boolean loadExtension(final Extension extension) {
            if (extension instanceof HtmlRendererExtension) {
                HtmlRendererExtension htmlRendererExtension = (HtmlRendererExtension) extension;
                htmlRendererExtension.extend(this, this.get(HtmlRenderer.TYPE));
                return true;
            } else if (extension instanceof RendererExtension) {
                RendererExtension htmlRendererExtension = (RendererExtension) extension;
                htmlRendererExtension.extend(this, this.get(HtmlRenderer.TYPE));
                return true;
            }
            return false;
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

        public boolean isRendererType(final String supportedRendererType) {
            final String rendererType = HtmlRenderer.TYPE.getFrom(this);
            return HtmlRenderer.isCompatibleRendererType(this, rendererType, supportedRendererType);
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
            this.attributeProviderFactories.put(attributeProviderFactory.getClass(), attributeProviderFactory);
            addExtensionApiPoint(attributeProviderFactory);
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
            addExtensionApiPoint(nodeRendererFactory);
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
            addExtensionApiPoint(linkResolverFactory);
            return this;
        }

        /**
         * Add a factory for generating the header id attribute from the header's text
         *
         * @param htmlIdGeneratorFactory the factory for generating header tag id attributes
         * @return {@code this}
         */
        public Builder htmlIdGeneratorFactory(HeaderIdGeneratorFactory htmlIdGeneratorFactory) {
            //noinspection VariableNotUsedInsideIf
            if (this.htmlIdGeneratorFactory != null) {
                throw new IllegalStateException("custom header id factory is already set to " + htmlIdGeneratorFactory.getClass().getName());
            }
            this.htmlIdGeneratorFactory = htmlIdGeneratorFactory;
            addExtensionApiPoint(htmlIdGeneratorFactory);
            return this;
        }
    }

    /**
     * Extension for {@link HtmlRenderer}.
     * <p>
     * This should be implemented by all extensions that have HtmlRenderer extension code.
     * <p>
     * Each extension will have its {@link HtmlRendererExtension#extend(Builder, String)} method called.
     * and should call back on the builder argument to register all extension points
     */
    public interface HtmlRendererExtension extends Extension {
        /**
         * This method is called first on all extensions so that they can adjust the options that must be
         * common to all extensions.
         *
         * @param options option set that will be used for the builder
         */
        void rendererOptions(MutableDataHolder options);

        /**
         * Called to give each extension to register extension points that it contains
         *
         * @param rendererBuilder builder to call back for extension point registration
         * @param rendererType    type of rendering being performed. For now "HTML", "JIRA" or "YOUTRACK"
         * @see Builder#attributeProviderFactory(AttributeProviderFactory)
         * @see Builder#nodeRendererFactory(NodeRendererFactory)
         * @see Builder#linkResolverFactory(LinkResolverFactory)
         * @see Builder#htmlIdGeneratorFactory(HeaderIdGeneratorFactory)
         */
        void extend(Builder rendererBuilder, String rendererType);
    }

    public static class RendererDependencyStage {
        private final List<DelegatingNodeRendererFactoryWrapper> dependents;

        public RendererDependencyStage(List<DelegatingNodeRendererFactoryWrapper> dependents) {
            this.dependents = dependents;
        }
    }

    public static class RendererDependencies extends ResolvedDependencies<RendererDependencyStage> {
        private final List<DelegatingNodeRendererFactoryWrapper> nodeRendererFactories;

        public RendererDependencies(List<RendererDependencyStage> dependentStages) {
            super(dependentStages);
            List<DelegatingNodeRendererFactoryWrapper> blockPreProcessorFactories = new ArrayList<DelegatingNodeRendererFactoryWrapper>();
            for (RendererDependencyStage stage : dependentStages) {
                blockPreProcessorFactories.addAll(stage.dependents);
            }
            this.nodeRendererFactories = blockPreProcessorFactories;
        }

        public List<DelegatingNodeRendererFactoryWrapper> getNodeRendererFactories() {
            return nodeRendererFactories;
        }
    }

    private static class RendererDependencyHandler extends DependencyHandler<DelegatingNodeRendererFactoryWrapper, RendererDependencyStage, RendererDependencies> {
        @Override
        protected Class getDependentClass(DelegatingNodeRendererFactoryWrapper dependent) {
            return dependent.getFactory().getClass();
        }

        @Override
        protected RendererDependencies createResolvedDependencies(List<RendererDependencyStage> stages) {
            return new RendererDependencies(stages);
        }

        @Override
        protected RendererDependencyStage createStage(List<DelegatingNodeRendererFactoryWrapper> dependents) {
            return new RendererDependencyStage(dependents);
        }
    }

    private class MainNodeRenderer extends NodeRendererSubContext implements NodeRendererContext, Disposable {
        private Document document;
        private Map<Class<?>, NodeRenderingHandlerWrapper> renderers;
        private List<PhasedNodeRenderer> phasedRenderers;
        private LinkResolver[] myLinkResolvers;
        private Set<RenderingPhase> renderingPhases;
        private DataHolder options;
        private RenderingPhase phase;
        private HtmlIdGenerator htmlIdGenerator;
        private HashMap<LinkType, HashMap<String, ResolvedLink>> resolvedLinkMap = new HashMap<LinkType, HashMap<String, ResolvedLink>>();
        private AttributeProvider[] attributeProviders;

        @Override
        public void dispose() {
            document = null;
            renderers = null;
            phasedRenderers = null;

            for (LinkResolver linkResolver : myLinkResolvers) {
                if (linkResolver instanceof Disposable) ((Disposable) linkResolver).dispose();
            }
            myLinkResolvers = null;

            renderingPhases = null;
            options = null;

            if (htmlIdGenerator instanceof Disposable) ((Disposable) htmlIdGenerator).dispose();
            htmlIdGenerator = null;
            resolvedLinkMap = null;

            for (AttributeProvider attributeProvider : attributeProviders) {
                if (attributeProvider instanceof Disposable) ((Disposable) attributeProvider).dispose();
            }
            attributeProviders = null;
        }

        MainNodeRenderer(DataHolder options, HtmlWriter htmlWriter, Document document) {
            super(htmlWriter);
            this.options = new ScopedDataSet(document, options);
            this.document = document;
            this.renderers = new HashMap<Class<?>, NodeRenderingHandlerWrapper>(32);
            this.renderingPhases = new HashSet<RenderingPhase>(RenderingPhase.values().length);
            this.phasedRenderers = new ArrayList<PhasedNodeRenderer>(nodeRendererFactories.size());
            this.myLinkResolvers = new LinkResolver[linkResolverFactories.size()];
            this.doNotRenderLinksNesting = htmlOptions.doNotRenderLinksInDocument ? 0 : 1;
            this.htmlIdGenerator = htmlIdGeneratorFactory != null ? htmlIdGeneratorFactory.create(this)
                    : (!(htmlOptions.renderHeaderId || htmlOptions.generateHeaderIds) ? HtmlIdGenerator.NULL : new HeaderIdGenerator.Factory().create(this));

            htmlWriter.setContext(this);

            for (int i = nodeRendererFactories.size() - 1; i >= 0; i--) {
                NodeRendererFactory nodeRendererFactory = nodeRendererFactories.get(i);
                NodeRenderer nodeRenderer = nodeRendererFactory.create(this.getOptions());
                for (NodeRenderingHandler nodeType : nodeRenderer.getNodeRenderingHandlers()) {
                    // Overwrite existing renderer
                    NodeRenderingHandlerWrapper handlerWrapper = new NodeRenderingHandlerWrapper(nodeType, renderers.get(nodeType.getNodeType()));
                    renderers.put(nodeType.getNodeType(), handlerWrapper);
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
        public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Boolean urlEncode) {
            return resolveLink(linkType, url, (Attributes) null, urlEncode);
        }

        @Override
        public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Attributes attributes, Boolean urlEncode) {
            HashMap<String, ResolvedLink> resolvedLinks = resolvedLinkMap.get(linkType);
            if (resolvedLinks == null) {
                resolvedLinks = new HashMap<String, ResolvedLink>();
                resolvedLinkMap.put(linkType, resolvedLinks);
            }

            String urlSeq = String.valueOf(url);
            ResolvedLink resolvedLink = resolvedLinks.get(urlSeq);
            if (resolvedLink == null) {
                resolvedLink = new ResolvedLink(linkType, urlSeq, attributes);

                if (!urlSeq.isEmpty()) {
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
                resolvedLinks.put(urlSeq, resolvedLink);
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
        public String encodeUrl(CharSequence url) {
            if (htmlOptions.percentEncodeUrls) {
                return Escaping.percentEncodeUrl(url);
            } else {
                return String.valueOf(url);
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
        public Attributes extendRenderingNodeAttributes(Node node, AttributablePart part, Attributes attributes) {
            Attributes attr = attributes != null ? attributes : new Attributes();
            for (AttributeProvider attributeProvider : attributeProviders) {
                attributeProvider.setAttributes(node, part, attr);
            }
            return attr;
        }

        @Override
        public void render(Node node) {
            renderNode(node, this);
        }

        @Override
        public void delegateRender() {
            renderByPreviousHandler(this);
        }

        void renderByPreviousHandler(NodeRendererSubContext subContext) {
            if (subContext.renderingNode != null) {
                NodeRenderingHandlerWrapper nodeRenderer = subContext.renderingHandlerWrapper.myPreviousRenderingHandler;
                if (nodeRenderer != null) {
                    Node oldNode = subContext.renderingNode;
                    int oldDoNotRenderLinksNesting = subContext.doNotRenderLinksNesting;
                    NodeRenderingHandlerWrapper prevWrapper = subContext.renderingHandlerWrapper;
                    try {
                        subContext.renderingHandlerWrapper = nodeRenderer;
                        nodeRenderer.myRenderingHandler.render(oldNode, subContext, subContext.htmlWriter);
                    } finally {
                        subContext.renderingNode = oldNode;
                        subContext.doNotRenderLinksNesting = oldDoNotRenderLinksNesting;
                        subContext.renderingHandlerWrapper = prevWrapper;
                    }
                }
            } else {
                throw new IllegalStateException("renderingByPreviousHandler called outside node rendering code");
            }
        }

        @Override
        public NodeRendererContext getSubContext(Appendable out, boolean inheritIndent) {
            HtmlWriter htmlWriter = new HtmlWriter(getHtmlWriter(), out, inheritIndent);
            htmlWriter.setContext(this);
            //noinspection ReturnOfInnerClass
            return new SubNodeRenderer(this, htmlWriter, false);
        }

        @Override
        public NodeRendererContext getDelegatedSubContext(final Appendable out, final boolean inheritIndent) {
            HtmlWriter htmlWriter = new HtmlWriter(getHtmlWriter(), out, inheritIndent);
            htmlWriter.setContext(this);
            //noinspection ReturnOfInnerClass
            return new SubNodeRenderer(this, htmlWriter, true);
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

                    if (getRenderingPhase() == RenderingPhase.BODY) {
                        NodeRenderingHandlerWrapper nodeRenderer = renderers.get(node.getClass());
                        if (nodeRenderer != null) {
                            subContext.doNotRenderLinksNesting = documentDoNotRenderLinksNesting;
                            NodeRenderingHandlerWrapper prevWrapper = subContext.renderingHandlerWrapper;
                            try {
                                subContext.renderingNode = node;
                                subContext.renderingHandlerWrapper = nodeRenderer;
                                nodeRenderer.myRenderingHandler.render(node, subContext, subContext.htmlWriter);
                            } finally {
                                subContext.renderingHandlerWrapper = prevWrapper;
                                subContext.renderingNode = null;
                                subContext.doNotRenderLinksNesting = oldDoNotRenderLinksNesting;
                            }
                        }
                    }
                }
            } else {
                NodeRenderingHandlerWrapper nodeRenderer = renderers.get(node.getClass());
                if (nodeRenderer != null) {
                    Node oldNode = this.renderingNode;
                    int oldDoNotRenderLinksNesting = subContext.doNotRenderLinksNesting;
                    NodeRenderingHandlerWrapper prevWrapper = subContext.renderingHandlerWrapper;
                    try {
                        subContext.renderingNode = node;
                        subContext.renderingHandlerWrapper = nodeRenderer;
                        nodeRenderer.myRenderingHandler.render(node, subContext, subContext.htmlWriter);
                    } finally {
                        subContext.renderingNode = oldNode;
                        subContext.doNotRenderLinksNesting = oldDoNotRenderLinksNesting;
                        subContext.renderingHandlerWrapper = prevWrapper;
                    }
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

            public SubNodeRenderer(MainNodeRenderer mainNodeRenderer, HtmlWriter htmlWriter, final boolean inheritCurrentHandler) {
                super(htmlWriter);
                myMainNodeRenderer = mainNodeRenderer;
                doNotRenderLinksNesting = mainNodeRenderer.getHtmlOptions().doNotRenderLinksInDocument ? 1 : 0;
                if (inheritCurrentHandler) {
                    renderingNode = mainNodeRenderer.renderingNode;
                    renderingHandlerWrapper = mainNodeRenderer.renderingHandlerWrapper;
                }
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
            public String encodeUrl(CharSequence url) {return myMainNodeRenderer.encodeUrl(url);}

            @Override
            public Attributes extendRenderingNodeAttributes(AttributablePart part, Attributes attributes) {
                return myMainNodeRenderer.extendRenderingNodeAttributes(
                        part,
                        attributes
                );
            }

            @Override
            public Attributes extendRenderingNodeAttributes(Node node, AttributablePart part, Attributes attributes) {
                return myMainNodeRenderer.extendRenderingNodeAttributes(
                        node,
                        part,
                        attributes
                );
            }

            @Override
            public void render(Node node) {
                myMainNodeRenderer.renderNode(node, this);
            }

            @Override
            public void delegateRender() {
                myMainNodeRenderer.renderByPreviousHandler(this);
            }

            @Override
            public Node getCurrentNode() {
                return myMainNodeRenderer.getCurrentNode();
            }

            @Override
            public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(linkType, url, urlEncode);
            }

            @Override
            public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Attributes attributes, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(linkType, url, attributes, urlEncode);
            }

            @Override
            public NodeRendererContext getSubContext(Appendable out, boolean inheritIndent) {
                HtmlWriter htmlWriter = new HtmlWriter(this.htmlWriter, out, inheritIndent);
                htmlWriter.setContext(this);
                //noinspection ReturnOfInnerClass
                return new SubNodeRenderer(myMainNodeRenderer, htmlWriter, false);
            }

            @Override
            public NodeRendererContext getDelegatedSubContext(final Appendable out, final boolean inheritIndent) {
                HtmlWriter htmlWriter = new HtmlWriter(this.htmlWriter, out, inheritIndent);
                htmlWriter.setContext(this);
                //noinspection ReturnOfInnerClass
                return new SubNodeRenderer(myMainNodeRenderer, htmlWriter, true);
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
