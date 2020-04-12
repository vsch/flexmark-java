package com.vladsch.flexmark.html;

import com.vladsch.flexmark.ast.HtmlBlock;
import com.vladsch.flexmark.ast.HtmlInline;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.builder.BuilderBase;
import com.vladsch.flexmark.util.data.*;
import com.vladsch.flexmark.util.dependency.DependencyResolver;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.format.TrackedOffsetUtils;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.misc.Extension;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import com.vladsch.flexmark.util.sequence.TagRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    final public static DataKey<String> SOFT_BREAK = new DataKey<>("SOFT_BREAK", "\n");
    final public static DataKey<String> HARD_BREAK = new DataKey<>("HARD_BREAK", "<br />\n");
    final public static NullableDataKey<String> STRONG_EMPHASIS_STYLE_HTML_OPEN = new NullableDataKey<>("STRONG_EMPHASIS_STYLE_HTML_OPEN");
    final public static NullableDataKey<String> STRONG_EMPHASIS_STYLE_HTML_CLOSE = new NullableDataKey<>("STRONG_EMPHASIS_STYLE_HTML_CLOSE");
    final public static NullableDataKey<String> EMPHASIS_STYLE_HTML_OPEN = new NullableDataKey<>("EMPHASIS_STYLE_HTML_OPEN");
    final public static NullableDataKey<String> EMPHASIS_STYLE_HTML_CLOSE = new NullableDataKey<>("EMPHASIS_STYLE_HTML_CLOSE");
    final public static NullableDataKey<String> CODE_STYLE_HTML_OPEN = new NullableDataKey<>("CODE_STYLE_HTML_OPEN");
    final public static NullableDataKey<String> CODE_STYLE_HTML_CLOSE = new NullableDataKey<>("CODE_STYLE_HTML_CLOSE");
    final public static NullableDataKey<String> INLINE_CODE_SPLICE_CLASS = new NullableDataKey<>("INLINE_CODE_SPLICE_CLASS");
    final public static DataKey<Boolean> PERCENT_ENCODE_URLS = SharedDataKeys.PERCENT_ENCODE_URLS;
    final public static DataKey<Integer> INDENT_SIZE = SharedDataKeys.INDENT_SIZE;
    final public static DataKey<Boolean> ESCAPE_HTML = new DataKey<>("ESCAPE_HTML", false);
    final public static DataKey<Boolean> ESCAPE_HTML_BLOCKS = new DataKey<>("ESCAPE_HTML_BLOCKS", ESCAPE_HTML);
    final public static DataKey<Boolean> ESCAPE_HTML_COMMENT_BLOCKS = new DataKey<>("ESCAPE_HTML_COMMENT_BLOCKS", ESCAPE_HTML_BLOCKS);
    final public static DataKey<Boolean> ESCAPE_INLINE_HTML = new DataKey<>("ESCAPE_HTML_BLOCKS", ESCAPE_HTML);
    final public static DataKey<Boolean> ESCAPE_INLINE_HTML_COMMENTS = new DataKey<>("ESCAPE_INLINE_HTML_COMMENTS", ESCAPE_INLINE_HTML);
    final public static DataKey<Boolean> SUPPRESS_HTML = new DataKey<>("SUPPRESS_HTML", false);
    final public static DataKey<Boolean> SUPPRESS_HTML_BLOCKS = new DataKey<>("SUPPRESS_HTML_BLOCKS", SUPPRESS_HTML);
    final public static DataKey<Boolean> SUPPRESS_HTML_COMMENT_BLOCKS = new DataKey<>("SUPPRESS_HTML_COMMENT_BLOCKS", SUPPRESS_HTML_BLOCKS);
    final public static DataKey<Boolean> SUPPRESS_INLINE_HTML = new DataKey<>("SUPPRESS_INLINE_HTML", SUPPRESS_HTML);
    final public static DataKey<Boolean> SUPPRESS_INLINE_HTML_COMMENTS = new DataKey<>("SUPPRESS_INLINE_HTML_COMMENTS", SUPPRESS_INLINE_HTML);
    final public static DataKey<Boolean> SOURCE_WRAP_HTML = new DataKey<>("SOURCE_WRAP_HTML", false);
    final public static DataKey<Boolean> SOURCE_WRAP_HTML_BLOCKS = new DataKey<>("SOURCE_WRAP_HTML_BLOCKS", SOURCE_WRAP_HTML);
    final public static DataKey<Boolean> HEADER_ID_GENERATOR_RESOLVE_DUPES = SharedDataKeys.HEADER_ID_GENERATOR_RESOLVE_DUPES;
    final public static DataKey<String> HEADER_ID_GENERATOR_TO_DASH_CHARS = SharedDataKeys.HEADER_ID_GENERATOR_TO_DASH_CHARS;
    final public static DataKey<String> HEADER_ID_GENERATOR_NON_DASH_CHARS = SharedDataKeys.HEADER_ID_GENERATOR_NON_DASH_CHARS;
    final public static DataKey<Boolean> HEADER_ID_GENERATOR_NO_DUPED_DASHES = SharedDataKeys.HEADER_ID_GENERATOR_NO_DUPED_DASHES;
    final public static DataKey<Boolean> HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE = SharedDataKeys.HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE;
    final public static DataKey<Boolean> HEADER_ID_REF_TEXT_TRIM_LEADING_SPACES = SharedDataKeys.HEADER_ID_REF_TEXT_TRIM_LEADING_SPACES;
    final public static DataKey<Boolean> HEADER_ID_REF_TEXT_TRIM_TRAILING_SPACES = SharedDataKeys.HEADER_ID_REF_TEXT_TRIM_TRAILING_SPACES;
    final public static DataKey<Boolean> HEADER_ID_ADD_EMOJI_SHORTCUT = SharedDataKeys.HEADER_ID_ADD_EMOJI_SHORTCUT;
    final public static DataKey<Boolean> RENDER_HEADER_ID = SharedDataKeys.RENDER_HEADER_ID;
    final public static DataKey<Boolean> GENERATE_HEADER_ID = SharedDataKeys.GENERATE_HEADER_ID;
    final public static DataKey<Boolean> DO_NOT_RENDER_LINKS = SharedDataKeys.DO_NOT_RENDER_LINKS;
    final public static DataKey<String> FENCED_CODE_LANGUAGE_CLASS_PREFIX = new DataKey<>("FENCED_CODE_LANGUAGE_CLASS_PREFIX", "language-");
    final public static DataKey<String> FENCED_CODE_NO_LANGUAGE_CLASS = new DataKey<>("FENCED_CODE_NO_LANGUAGE_CLASS", "");
    final public static DataKey<String> SOURCE_POSITION_ATTRIBUTE = new DataKey<>("SOURCE_POSITION_ATTRIBUTE", "");
    final public static DataKey<Boolean> SOURCE_POSITION_PARAGRAPH_LINES = new DataKey<>("SOURCE_POSITION_PARAGRAPH_LINES", false);
    final public static DataKey<String> TYPE = new DataKey<>("TYPE", "HTML");
    final public static DataKey<ArrayList<TagRange>> TAG_RANGES = new DataKey<>("TAG_RANGES", ArrayList::new);

    final public static DataKey<Boolean> RECHECK_UNDEFINED_REFERENCES = new DataKey<>("RECHECK_UNDEFINED_REFERENCES", false);
    final public static DataKey<Boolean> OBFUSCATE_EMAIL = new DataKey<>("OBFUSCATE_EMAIL", false);
    final public static DataKey<Boolean> OBFUSCATE_EMAIL_RANDOM = new DataKey<>("OBFUSCATE_EMAIL_RANDOM", true);
    final public static DataKey<Boolean> HTML_BLOCK_OPEN_TAG_EOL = new DataKey<>("HTML_BLOCK_OPEN_TAG_EOL", true);
    final public static DataKey<Boolean> HTML_BLOCK_CLOSE_TAG_EOL = new DataKey<>("HTML_BLOCK_CLOSE_TAG_EOL", true);
    final public static DataKey<Boolean> UNESCAPE_HTML_ENTITIES = new DataKey<>("UNESCAPE_HTML_ENTITIES", true);
    final public static DataKey<String> AUTOLINK_WWW_PREFIX = new DataKey<>("AUTOLINK_WWW_PREFIX", "http://");

    // regex for suppressed link prefixes
    final public static DataKey<String> SUPPRESSED_LINKS = new DataKey<>("SUPPRESSED_LINKS", "javascript:.*");
    final public static DataKey<Boolean> NO_P_TAGS_USE_BR = new DataKey<>("NO_P_TAGS_USE_BR", false);
    final public static DataKey<Boolean> EMBEDDED_ATTRIBUTE_PROVIDER = new DataKey<>("EMBEDDED_ATTRIBUTE_PROVIDER", true);

    /**
     * output control for FormattingAppendable, see {@link LineAppendable#setOptions(int)}
     */
    final public static DataKey<Integer> FORMAT_FLAGS = new DataKey<>("RENDERER_FORMAT_FLAGS", LineAppendable.F_TRIM_LEADING_WHITESPACE);
    final public static DataKey<Integer> MAX_TRAILING_BLANK_LINES = SharedDataKeys.RENDERER_MAX_TRAILING_BLANK_LINES;
    final public static DataKey<Integer> MAX_BLANK_LINES = SharedDataKeys.RENDERER_MAX_BLANK_LINES;

    // Use LineFormattingAppendable values instead,
    // NOTE: ALLOW_LEADING_WHITESPACE is now inverted and named F_TRIM_LEADING_WHITESPACE
    @Deprecated final public static int CONVERT_TABS = LineAppendable.F_CONVERT_TABS;
    @Deprecated final public static int COLLAPSE_WHITESPACE = LineAppendable.F_COLLAPSE_WHITESPACE;
    @Deprecated final public static int SUPPRESS_TRAILING_WHITESPACE = LineAppendable.F_TRIM_TRAILING_WHITESPACE;
    @Deprecated final public static int PASS_THROUGH = LineAppendable.F_PASS_THROUGH;
    //    @Deprecated final public static int ALLOW_LEADING_WHITESPACE = LineAppendable.F_TRIM_LEADING_WHITESPACE;
    @Deprecated final public static int FORMAT_ALL = LineAppendable.F_FORMAT_ALL;

    /**
     * Stores pairs of equivalent renderer types to allow extensions to resolve types not known to them
     * <p>
     * Pair contains: rendererType, equivalentType
     */
    final public static DataKey<List<Pair<String, String>>> RENDERER_TYPE_EQUIVALENCE = new DataKey<>("RENDERER_TYPE_EQUIVALENCE", Collections.emptyList());

    // Use LineFormattingAppendable values instead
    @Deprecated final public static int FORMAT_CONVERT_TABS = LineAppendable.F_CONVERT_TABS;
    @Deprecated final public static int FORMAT_COLLAPSE_WHITESPACE = LineAppendable.F_COLLAPSE_WHITESPACE;
    @Deprecated final public static int FORMAT_SUPPRESS_TRAILING_WHITESPACE = LineAppendable.F_TRIM_TRAILING_WHITESPACE;
    @Deprecated final public static int FORMAT_ALL_OPTIONS = LineAppendable.F_FORMAT_ALL;

    // Experimental, not tested
    final public static DataKey<List<TrackedOffset>> TRACKED_OFFSETS = new DataKey<>("TRACKED_OFFSETS", Collections.emptyList());

    // now not final only to allow disposal of resources
    final List<AttributeProviderFactory> attributeProviderFactories;
    final List<DelegatingNodeRendererFactoryWrapper> nodeRendererFactories;
    final List<LinkResolverFactory> linkResolverFactories;
    final HeaderIdGeneratorFactory htmlIdGeneratorFactory;
    final HtmlRendererOptions htmlOptions;
    final DataHolder options;

    HtmlRenderer(@NotNull Builder builder) {
        this.options = builder.toImmutable();
        this.htmlOptions = new HtmlRendererOptions(this.options);

        this.htmlIdGeneratorFactory = builder.htmlIdGeneratorFactory;

        // resolve renderer dependencies
        List<DelegatingNodeRendererFactoryWrapper> nodeRenderers = new ArrayList<>(builder.nodeRendererFactories.size());

        for (int i = builder.nodeRendererFactories.size() - 1; i >= 0; i--) {
            NodeRendererFactory nodeRendererFactory = builder.nodeRendererFactories.get(i);
            nodeRenderers.add(new DelegatingNodeRendererFactoryWrapper(nodeRenderers, nodeRendererFactory));
        }

        // Add as last. This means clients can override the rendering of core nodes if they want by default
        CoreNodeRenderer.Factory nodeRendererFactory = new CoreNodeRenderer.Factory();
        nodeRenderers.add(new DelegatingNodeRendererFactoryWrapper(nodeRenderers, nodeRendererFactory));

        nodeRendererFactories = DependencyResolver.resolveFlatDependencies(nodeRenderers, null, dependent -> dependent.getFactory().getClass());

        // HACK: but for now works
        boolean addEmbedded = !builder.attributeProviderFactories.containsKey(EmbeddedAttributeProvider.Factory.getClass());
        List<AttributeProviderFactory> values = new ArrayList<>(builder.attributeProviderFactories.values());
        if (addEmbedded && EMBEDDED_ATTRIBUTE_PROVIDER.get(options)) {
            // add it first so the rest can override it if needed
            values.add(0, EmbeddedAttributeProvider.Factory);
        }

        this.attributeProviderFactories = DependencyResolver.resolveFlatDependencies(values, null, null);
        this.linkResolverFactories = DependencyResolver.resolveFlatDependencies(builder.linkResolverFactories, null, null);
    }

    /**
     * Create a new builder for configuring an {@link HtmlRenderer}.
     *
     * @return a builder
     */
    public static @NotNull Builder builder() {
        return new Builder();
    }

    /**
     * Create a new builder for configuring an {@link HtmlRenderer}.
     *
     * @param options initialization options
     * @return a builder
     */
    public static @NotNull Builder builder(@Nullable DataHolder options) {
        return new Builder(options);
    }

    @NotNull
    @Override
    public DataHolder getOptions() {
        return options;
    }

    /**
     * Render a node to the appendable
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void render(@NotNull Node node, @NotNull Appendable output) {
        render(node, output, htmlOptions.maxTrailingBlankLines);
    }

    /**
     * Render a node to the appendable
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void render(@NotNull Node node, @NotNull Appendable output, int maxTrailingBlankLines) {
        HtmlWriter htmlWriter = new HtmlWriter(output, htmlOptions.indentSize, htmlOptions.formatFlags, !htmlOptions.htmlBlockOpenTagEol, !htmlOptions.htmlBlockCloseTagEol);
        MainNodeRenderer renderer = new MainNodeRenderer(options, htmlWriter, node.getDocument());
        if (renderer.htmlIdGenerator != HtmlIdGenerator.NULL && !(node instanceof Document)) {
            renderer.htmlIdGenerator.generateIds(node.getDocument());
        }

        renderer.render(node);
        htmlWriter.appendToSilently(output, htmlOptions.maxBlankLines, maxTrailingBlankLines);

        // resolve any unresolved tracked offsets that are outside elements which resolve their own
        TrackedOffsetUtils.resolveTrackedOffsets(node.getChars(), htmlWriter, TRACKED_OFFSETS.get(renderer.getDocument()), maxTrailingBlankLines, SharedDataKeys.RUNNING_TESTS.get(options));
        renderer.dispose();
    }

    /**
     * Render the tree of nodes to HTML.
     *
     * @param node the root node
     * @return the rendered HTML.
     */
    @NotNull
    public String render(@NotNull Node node) {
        StringBuilder sb = new StringBuilder();
        render(node, sb);
        return sb.toString();
    }

    static public boolean isCompatibleRendererType(@NotNull MutableDataHolder options, @NotNull String supportedRendererType) {
        String rendererType = HtmlRenderer.TYPE.get(options);
        return isCompatibleRendererType(options, rendererType, supportedRendererType);
    }

    static public boolean isCompatibleRendererType(@NotNull MutableDataHolder options, @NotNull String rendererType, @NotNull String supportedRendererType) {
        if (rendererType.equals(supportedRendererType)) {
            return true;
        }

        List<Pair<String, String>> equivalence = RENDERER_TYPE_EQUIVALENCE.get(options);

        for (Pair<String, String> pair : equivalence) {
            if (rendererType.equals(pair.getFirst())) {
                if (supportedRendererType.equals(pair.getSecond())) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("UnusedReturnValue")
    static public @NotNull MutableDataHolder addRenderTypeEquivalence(@NotNull MutableDataHolder options, @NotNull String rendererType, @NotNull String supportedRendererType) {
        if (!isCompatibleRendererType(options, rendererType, supportedRendererType)) {
            // need to add
            List<Pair<String, String>> equivalence = RENDERER_TYPE_EQUIVALENCE.get(options);
            ArrayList<Pair<String, String>> newEquivalence = new ArrayList<>(equivalence);
            newEquivalence.add(new Pair<>(rendererType, supportedRendererType));
            options.set(RENDERER_TYPE_EQUIVALENCE, newEquivalence);
        }
        return options;
    }

    /**
     * Builder for configuring an {@link HtmlRenderer}. See methods for default configuration.
     */
    public static class Builder extends BuilderBase<Builder> implements RendererBuilder {
        Map<Class<?>, AttributeProviderFactory> attributeProviderFactories = new LinkedHashMap<>();
        List<NodeRendererFactory> nodeRendererFactories = new ArrayList<>();
        List<LinkResolverFactory> linkResolverFactories = new ArrayList<>();
        HeaderIdGeneratorFactory htmlIdGeneratorFactory = null;

        public Builder() {
            super();
        }

        public Builder(@Nullable DataHolder options) {
            super(options);
            loadExtensions();
        }

        @Override
        protected void removeApiPoint(@NotNull Object apiPoint) {
            if (apiPoint instanceof AttributeProviderFactory) this.attributeProviderFactories.remove(apiPoint.getClass());
            else if (apiPoint instanceof NodeRendererFactory) this.nodeRendererFactories.remove(apiPoint);
            else if (apiPoint instanceof LinkResolverFactory) this.linkResolverFactories.remove(apiPoint);
            else if (apiPoint instanceof HeaderIdGeneratorFactory) this.htmlIdGeneratorFactory = null;
            else {
                throw new IllegalStateException("Unknown data point type: " + apiPoint.getClass().getName());
            }
        }

        @Override
        protected void preloadExtension(@NotNull Extension extension) {
            if (extension instanceof HtmlRendererExtension) {
                HtmlRendererExtension htmlRendererExtension = (HtmlRendererExtension) extension;
                htmlRendererExtension.rendererOptions(this);
            } else if (extension instanceof RendererExtension) {
                RendererExtension htmlRendererExtension = (RendererExtension) extension;
                htmlRendererExtension.rendererOptions(this);
            }
        }

        @Override
        protected boolean loadExtension(@NotNull Extension extension) {
            if (extension instanceof HtmlRendererExtension) {
                HtmlRendererExtension htmlRendererExtension = (HtmlRendererExtension) extension;
                htmlRendererExtension.extend(this, TYPE.get(this));
                return true;
            } else if (extension instanceof RendererExtension) {
                RendererExtension htmlRendererExtension = (RendererExtension) extension;
                htmlRendererExtension.extend(this, TYPE.get(this));
                return true;
            }
            return false;
        }

        /**
         * @return the configured {@link HtmlRenderer}
         */
        @NotNull
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
        public @NotNull Builder softBreak(@NotNull String softBreak) {
            this.set(SOFT_BREAK, softBreak);
            return this;
        }

        /**
         * The size of the indent to use for hierarchical elements, default 0, means no indent, also fastest rendering
         *
         * @param indentSize number of spaces per indent
         * @return {@code this}
         */
        public @NotNull Builder indentSize(int indentSize) {
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
        public @NotNull Builder escapeHtml(boolean escapeHtml) {
            this.set(ESCAPE_HTML, escapeHtml);
            return this;
        }

        public boolean isRendererType(@NotNull String supportedRendererType) {
            String rendererType = HtmlRenderer.TYPE.get(this);
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
        public @NotNull Builder percentEncodeUrls(boolean percentEncodeUrls) {
            this.set(PERCENT_ENCODE_URLS, percentEncodeUrls);
            return this;
        }

        /**
         * Add an attribute provider for adding/changing HTML attributes to the rendered tags.
         *
         * @param attributeProviderFactory the attribute provider factory to add
         * @return {@code this}
         */
        public @NotNull Builder attributeProviderFactory(@NotNull AttributeProviderFactory attributeProviderFactory) {
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
        public @NotNull Builder nodeRendererFactory(@NotNull NodeRendererFactory nodeRendererFactory) {
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
        public @NotNull Builder linkResolverFactory(@NotNull LinkResolverFactory linkResolverFactory) {
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
        @NotNull
        public Builder htmlIdGeneratorFactory(@NotNull HeaderIdGeneratorFactory htmlIdGeneratorFactory) {
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
        void rendererOptions(@NotNull MutableDataHolder options);

        /**
         * Called to give each extension to register extension points that it contains
         *
         * @param htmlRendererBuilder builder to call back for extension point registration
         * @param rendererType        type of rendering being performed. For now "HTML", "JIRA" or "YOUTRACK"
         * @see Builder#attributeProviderFactory(AttributeProviderFactory)
         * @see Builder#nodeRendererFactory(NodeRendererFactory)
         * @see Builder#linkResolverFactory(LinkResolverFactory)
         * @see Builder#htmlIdGeneratorFactory(HeaderIdGeneratorFactory)
         */
        void extend(@NotNull Builder htmlRendererBuilder, @NotNull String rendererType);
    }

    private class MainNodeRenderer extends NodeRendererSubContext implements NodeRendererContext, Disposable {
        private Document document;
        private Map<Class<?>, NodeRenderingHandlerWrapper> renderers;
        private List<PhasedNodeRenderer> phasedRenderers;
        private LinkResolver[] myLinkResolvers;
        private Set<RenderingPhase> renderingPhases;
        private DataHolder options;
        private RenderingPhase phase;
        HtmlIdGenerator htmlIdGenerator;
        private HashMap<LinkType, HashMap<String, ResolvedLink>> resolvedLinkMap = new HashMap<>();
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
            this.renderers = new HashMap<>(32);
            this.renderingPhases = new HashSet<>(RenderingPhase.values().length);
            this.phasedRenderers = new ArrayList<>(nodeRendererFactories.size());
            this.myLinkResolvers = new LinkResolver[linkResolverFactories.size()];
            this.doNotRenderLinksNesting = htmlOptions.doNotRenderLinksInDocument ? 0 : 1;
            this.htmlIdGenerator = htmlIdGeneratorFactory != null ? htmlIdGeneratorFactory.create(this)
                    : (!(htmlOptions.renderHeaderId || htmlOptions.generateHeaderIds) ? HtmlIdGenerator.NULL : new HeaderIdGenerator.Factory().create(this));

            htmlWriter.setContext(this);

            for (int i = nodeRendererFactories.size() - 1; i >= 0; i--) {
                NodeRendererFactory nodeRendererFactory = nodeRendererFactories.get(i);
                NodeRenderer nodeRenderer = nodeRendererFactory.apply(this.getOptions());
                Set<NodeRenderingHandler<?>> renderingHandlers = nodeRenderer.getNodeRenderingHandlers();

                assert (renderingHandlers != null);
                for (NodeRenderingHandler<?> nodeType : renderingHandlers) {
                    // Overwrite existing renderer
                    NodeRenderingHandlerWrapper handlerWrapper = new NodeRenderingHandlerWrapper(nodeType, renderers.get(nodeType.getNodeType()));
                    renderers.put(nodeType.getNodeType(), handlerWrapper);
                }

                if (nodeRenderer instanceof PhasedNodeRenderer) {
                    Set<RenderingPhase> renderingPhases = ((PhasedNodeRenderer) nodeRenderer).getRenderingPhases();
                    assert (renderingPhases != null);

                    this.renderingPhases.addAll(renderingPhases);
                    this.phasedRenderers.add((PhasedNodeRenderer) nodeRenderer);
                }
            }

            for (int i = 0; i < linkResolverFactories.size(); i++) {
                myLinkResolvers[i] = linkResolverFactories.get(i).apply(this);
            }

            this.attributeProviders = new AttributeProvider[attributeProviderFactories.size()];
            for (int i = 0; i < attributeProviderFactories.size(); i++) {
                attributeProviders[i] = attributeProviderFactories.get(i).apply(this);
            }
        }

        @NotNull
        @Override
        public Node getCurrentNode() {
            return renderingNode;
        }

        @NotNull
        @Override
        public ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, Boolean urlEncode) {
            return resolveLink(linkType, url, (Attributes) null, urlEncode);
        }

        @NotNull
        @Override
        public ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, Attributes attributes, Boolean urlEncode) {
            HashMap<String, ResolvedLink> resolvedLinks = resolvedLinkMap.computeIfAbsent(linkType, k -> new HashMap<>());

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
        public String getNodeId(@NotNull Node node) {
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

        @NotNull
        @Override
        public DataHolder getOptions() {
            return options;
        }

        @NotNull
        @Override
        public HtmlRendererOptions getHtmlOptions() {
            return htmlOptions;
        }

        @NotNull
        @Override
        public Document getDocument() {
            return document;
        }

        @NotNull
        @Override
        public RenderingPhase getRenderingPhase() {
            return phase;
        }

        @NotNull
        @Override
        public String encodeUrl(@NotNull CharSequence url) {
            if (htmlOptions.percentEncodeUrls) {
                return Escaping.percentEncodeUrl(url);
            } else {
                return String.valueOf(url);
            }
        }

        @NotNull
        @Override
        public Attributes extendRenderingNodeAttributes(@NotNull AttributablePart part, Attributes attributes) {
            Attributes attr = attributes != null ? attributes : new Attributes();
            for (AttributeProvider attributeProvider : attributeProviders) {
                attributeProvider.setAttributes(this.renderingNode, part, attr);
            }
            return attr;
        }

        @NotNull
        @Override
        public Attributes extendRenderingNodeAttributes(@NotNull Node node, @NotNull AttributablePart part, Attributes attributes) {
            Attributes attr = attributes != null ? attributes : new Attributes();
            for (AttributeProvider attributeProvider : attributeProviders) {
                attributeProvider.setAttributes(node, part, attr);
            }
            return attr;
        }

        @Override
        public void render(@NotNull Node node) {
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

        @NotNull
        @Override
        public NodeRendererContext getSubContext(boolean inheritIndent) {
            HtmlWriter htmlWriter = new HtmlWriter(getHtmlWriter(), inheritIndent);
            htmlWriter.setContext(this);
            //noinspection ReturnOfInnerClass
            return new SubNodeRenderer(this, htmlWriter, false);
        }

        @NotNull
        @Override
        public NodeRendererContext getDelegatedSubContext(boolean inheritIndent) {
            HtmlWriter htmlWriter = new HtmlWriter(getHtmlWriter(), inheritIndent);
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
                        if (Objects.requireNonNull(phasedRenderer.getRenderingPhases()).contains(phase)) {
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

        public void renderChildren(@NotNull Node parent) {
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
            final private MainNodeRenderer myMainNodeRenderer;

            public SubNodeRenderer(MainNodeRenderer mainNodeRenderer, HtmlWriter htmlWriter, boolean inheritCurrentHandler) {
                super(htmlWriter);
                myMainNodeRenderer = mainNodeRenderer;
                doNotRenderLinksNesting = mainNodeRenderer.getHtmlOptions().doNotRenderLinksInDocument ? 1 : 0;
                if (inheritCurrentHandler) {
                    renderingNode = mainNodeRenderer.renderingNode;
                    renderingHandlerWrapper = mainNodeRenderer.renderingHandlerWrapper;
                }
            }

            @Override
            public String getNodeId(@NotNull Node node) {return myMainNodeRenderer.getNodeId(node);}

            @NotNull
            @Override
            public DataHolder getOptions() {return myMainNodeRenderer.getOptions();}

            @NotNull
            @Override
            public HtmlRendererOptions getHtmlOptions() {return myMainNodeRenderer.getHtmlOptions();}

            @NotNull
            @Override
            public Document getDocument() {return myMainNodeRenderer.getDocument();}

            @NotNull
            @Override
            public RenderingPhase getRenderingPhase() {return myMainNodeRenderer.getRenderingPhase();}

            @NotNull
            @Override
            public String encodeUrl(@NotNull CharSequence url) {return myMainNodeRenderer.encodeUrl(url);}

            @NotNull
            @Override
            public Attributes extendRenderingNodeAttributes(@NotNull AttributablePart part, Attributes attributes) {
                return myMainNodeRenderer.extendRenderingNodeAttributes(
                        part,
                        attributes
                );
            }

            @NotNull
            @Override
            public Attributes extendRenderingNodeAttributes(@NotNull Node node, @NotNull AttributablePart part, Attributes attributes) {
                return myMainNodeRenderer.extendRenderingNodeAttributes(
                        node,
                        part,
                        attributes
                );
            }

            @Override
            public void render(@NotNull Node node) {
                myMainNodeRenderer.renderNode(node, this);
            }

            @Override
            public void delegateRender() {
                myMainNodeRenderer.renderByPreviousHandler(this);
            }

            @NotNull
            @Override
            public Node getCurrentNode() {
                return myMainNodeRenderer.getCurrentNode();
            }

            @NotNull
            @Override
            public ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(linkType, url, urlEncode);
            }

            @NotNull
            @Override
            public ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, Attributes attributes, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(linkType, url, attributes, urlEncode);
            }

            @NotNull
            @Override
            public NodeRendererContext getSubContext(boolean inheritIndent) {
                HtmlWriter htmlWriter = new HtmlWriter(this.htmlWriter, inheritIndent);
                htmlWriter.setContext(this);
                //noinspection ReturnOfInnerClass
                return new SubNodeRenderer(myMainNodeRenderer, htmlWriter, false);
            }

            @NotNull
            @Override
            public NodeRendererContext getDelegatedSubContext(boolean inheritIndent) {
                HtmlWriter htmlWriter = new HtmlWriter(this.htmlWriter, inheritIndent);
                htmlWriter.setContext(this);
                //noinspection ReturnOfInnerClass
                return new SubNodeRenderer(myMainNodeRenderer, htmlWriter, true);
            }

            @Override
            public void renderChildren(@NotNull Node parent) {
                myMainNodeRenderer.renderChildrenNode(parent, this);
            }

            @NotNull
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
