package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.html.AttributeProviderFactory;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeCollectingVisitor;
import com.vladsch.flexmark.util.builder.BuilderBase;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.collection.SubClassingBag;
import com.vladsch.flexmark.util.data.*;
import com.vladsch.flexmark.util.dependency.DependencyHandler;
import com.vladsch.flexmark.util.dependency.FlatDependencyHandler;
import com.vladsch.flexmark.util.dependency.ResolvedDependencies;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.options.*;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.LineFormattingAppendable;
import com.vladsch.flexmark.util.mappers.CharWidthProvider;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

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
     * output control for FormattingAppendable, see {@link LineFormattingAppendable#setOptions(int)}
     */
    public static final DataKey<Integer> FORMAT_FLAGS = new DataKey<>("FORMAT_FLAGS", 0);

    // for convenience or these together and set FORMAT_FLAGS key above to the value, to have HtmlWriter apply these when rendering Html
    public static final int FORMAT_CONVERT_TABS = LineFormattingAppendable.CONVERT_TABS;
    public static final int FORMAT_COLLAPSE_WHITESPACE = LineFormattingAppendable.COLLAPSE_WHITESPACE;
    public static final int FORMAT_SUPPRESS_TRAILING_WHITESPACE = LineFormattingAppendable.SUPPRESS_TRAILING_WHITESPACE;
    public static final int FORMAT_ALL_OPTIONS = LineFormattingAppendable.FORMAT_ALL;

    public static final DataKey<Integer> MAX_BLANK_LINES = new DataKey<>("MAX_BLANK_LINES", 2);
    public static final DataKey<Integer> MAX_TRAILING_BLANK_LINES = new DataKey<>("MAX_TRAILING_BLANK_LINES", 1);
    public static final DataKey<DiscretionaryText> SPACE_AFTER_ATX_MARKER = new DataKey<>("SPACE_AFTER_ATX_MARKER", DiscretionaryText.ADD);
    public static final DataKey<Boolean> SETEXT_HEADER_EQUALIZE_MARKER = new DataKey<>("SETEXT_HEADER_EQUALIZE_MARKER", true);
    public static final DataKey<EqualizeTrailingMarker> ATX_HEADER_TRAILING_MARKER = new DataKey<>("ATX_HEADER_TRAILING_MARKER", EqualizeTrailingMarker.AS_IS);
    public static final DataKey<String> THEMATIC_BREAK = new DataKey<>("THEMATIC_BREAK", (String) null);
    public static final DataKey<Boolean> BLOCK_QUOTE_BLANK_LINES = new DataKey<>("BLOCK_QUOTE_BLANK_LINES", true);
    public static final DataKey<BlockQuoteMarker> BLOCK_QUOTE_MARKERS = new DataKey<>("BLOCK_QUOTE_MARKERS", BlockQuoteMarker.ADD_COMPACT_WITH_SPACE);
    public static final DataKey<Boolean> INDENTED_CODE_MINIMIZE_INDENT = new DataKey<>("INDENTED_CODE_MINIMIZE_INDENT", true);
    public static final DataKey<Boolean> FENCED_CODE_MINIMIZE_INDENT = new DataKey<>("FENCED_CODE_MINIMIZE_INDENT", true);
    public static final DataKey<Boolean> FENCED_CODE_MATCH_CLOSING_MARKER = new DataKey<>("FENCED_CODE_MATCH_CLOSING_MARKER", true);
    public static final DataKey<Boolean> FENCED_CODE_SPACE_BEFORE_INFO = new DataKey<>("FENCED_CODE_SPACE_BEFORE_INFO", false);
    public static final DataKey<Integer> FENCED_CODE_MARKER_LENGTH = new DataKey<>("FENCED_CODE_MARKER_LENGTH", 3);
    public static final DataKey<CodeFenceMarker> FENCED_CODE_MARKER_TYPE = new DataKey<>("FENCED_CODE_MARKER_TYPE", CodeFenceMarker.ANY);
    public static final DataKey<Boolean> LIST_ADD_BLANK_LINE_BEFORE = new DataKey<>("LIST_ADD_BLANK_LINE_BEFORE", false);
    //public static final DataKey<Boolean> LIST_ALIGN_FIRST_LINE_TEXT = new DataKey<>("LIST_ALIGN_FIRST_LINE_TEXT", false);
    //public static final DataKey<Boolean> LIST_ALIGN_CHILD_BLOCKS = new DataKey<>("LIST_ALIGN_CHILD_BLOCKS", true);
    public static final DataKey<Boolean> LIST_RENUMBER_ITEMS = new DataKey<>("LIST_RENUMBER_ITEMS", true);
    public static final DataKey<Boolean> LIST_REMOVE_EMPTY_ITEMS = new DataKey<>("LIST_REMOVE_EMPTY_ITEMS", false);
    public static final DataKey<ListBulletMarker> LIST_BULLET_MARKER = new DataKey<>("LIST_BULLET_MARKER", ListBulletMarker.ANY);
    public static final DataKey<ListNumberedMarker> LIST_NUMBERED_MARKER = new DataKey<>("LIST_NUMBERED_MARKER", ListNumberedMarker.ANY);
    public static final DataKey<ListSpacing> LIST_SPACING = new DataKey<>("LIST_SPACING", ListSpacing.AS_IS);
    public static final DataKey<ElementPlacement> REFERENCE_PLACEMENT = new DataKey<>("REFERENCE_PLACEMENT", ElementPlacement.AS_IS);
    public static final DataKey<ElementPlacementSort> REFERENCE_SORT = new DataKey<>("REFERENCE_SORT", ElementPlacementSort.AS_IS);
    public static final DataKey<Boolean> KEEP_IMAGE_LINKS_AT_START = new DataKey<>("KEEP_IMAGE_LINKS_AT_START", false);
    public static final DataKey<Boolean> KEEP_EXPLICIT_LINKS_AT_START = new DataKey<>("KEEP_EXPLICIT_LINKS_AT_START", false);
    public static final DataKey<Boolean> OPTIMIZED_INLINE_RENDERING = new DataKey<>("OPTIMIZED_INLINE_RENDERING", false);
    //public static final DataKey<TrailingSpaces> KEEP_TRAILING_SPACES = new DataKey<>("KEEP_TRAILING_SPACES", TrailingSpaces.KEEP_LINE_BREAK);
    //public static final DataKey<TrailingSpaces> CODE_KEEP_TRAILING_SPACES = new DataKey<>("CODE_KEEP_TRAILING_SPACES", TrailingSpaces.KEEP_ALL);
    public static final DataKey<CharWidthProvider> FORMAT_CHAR_WIDTH_PROVIDER = TableFormatOptions.FORMAT_CHAR_WIDTH_PROVIDER;

    /**
     * @deprecated use FORMAT_ prefixed name
     */
    @Deprecated public static final DataKey<CharWidthProvider> CHAR_WIDTH_PROVIDER = TableFormatOptions.FORMAT_CHAR_WIDTH_PROVIDER;

    public static final DataKey<TableCaptionHandling> FORMAT_TABLE_CAPTION = TableFormatOptions.FORMAT_TABLE_CAPTION;
    public static final DataKey<DiscretionaryText> FORMAT_TABLE_CAPTION_SPACES = TableFormatOptions.FORMAT_TABLE_CAPTION_SPACES;
    public static final DataKey<String> FORMAT_TABLE_INDENT_PREFIX = TableFormatOptions.FORMAT_TABLE_INDENT_PREFIX;

    // formatter family override
    public static final DataKey<ParserEmulationProfile> FORMATTER_EMULATION_PROFILE = new DataKey<ParserEmulationProfile>("FORMATTER_EMULATION_PROFILE", Parser.PARSER_EMULATION_PROFILE);

    // used for translation phases of rendering
    public static final DataKey<String> TRANSLATION_ID_FORMAT = new DataKey<>("TRANSLATION_ID_FORMAT", "_%d_");
    public static final DataKey<String> TRANSLATION_HTML_BLOCK_PREFIX = new DataKey<>("TRANSLATION_HTML_BLOCK_PREFIX", "_");
    public static final DataKey<String> TRANSLATION_EXCLUDE_PATTERN = new DataKey<>("TRANSLATION_EXCLUDE_PATTERN", "^[^\\p{IsAlphabetic}]*$");
    public static final DataKey<String> TRANSLATION_HTML_BLOCK_TAG_PATTERN = Parser.TRANSLATION_HTML_BLOCK_TAG_PATTERN;
    public static final DataKey<String> TRANSLATION_HTML_INLINE_TAG_PATTERN = Parser.TRANSLATION_HTML_INLINE_TAG_PATTERN;

    public static final DataKey<Boolean> KEEP_HARD_LINE_BREAKS = new DataKey<>("KEEP_HARD_LINE_BREAKS", true);
    public static final DataKey<Boolean> KEEP_SOFT_LINE_BREAKS = new DataKey<>("KEEP_SOFT_LINE_BREAKS", true);
    public static final DataKey<Boolean> APPEND_TRANSFERRED_REFERENCES = new DataKey<>("APPEND_TRANSFERRED_REFERENCES", false);

    private static final Document[] EMPTY_DOCUMENTS = new Document[0];

    // list of documents across which to uniquify the reference ids if translating
    public static final DataKey<String> DOC_RELATIVE_URL = new DataKey<>("DOC_RELATIVE_URL", "");
    public static final DataKey<String> DOC_ROOT_URL = new DataKey<>("DOC_ROOT_URL", "");
    public static final DataKey<Boolean> DEFAULT_LINK_RESOLVER = new DataKey<>("DEFAULT_LINK_RESOLVER", false);

    final FormatterOptions formatterOptions;
    private final DataHolder options;
    private final Builder builder;
    final List<LinkResolverFactory> linkResolverFactories;
    private final NodeFormatterDependencies nodeFormatterFactories;

    private Formatter(Builder builder) {
        this.builder = new Builder(builder); // take a copy to avoid after creation side effects
        this.options = new DataSet(builder);
        this.formatterOptions = new FormatterOptions(this.options);
        //this.nodeFormatterFactories = new ArrayList<NodeFormatterFactory>(builder.nodeFormatterFactories.size() + 1);
        //this.nodeFormatterFactories.addAll(builder.nodeFormatterFactories);

        this.linkResolverFactories = FlatDependencyHandler.computeDependencies(builder.linkResolverFactories);
        this.nodeFormatterFactories = calculateBlockPreProcessors(this.options, builder.nodeFormatterFactories);
    }

    public static class NodeFormatterDependencyStage {
        private final List<NodeFormatterFactory> dependents;

        public NodeFormatterDependencyStage(List<NodeFormatterFactory> dependents) {
            // compute mappings
            this.dependents = dependents;
        }
    }

    private static class NodeFormatterDependencyHandler extends DependencyHandler<NodeFormatterFactory, NodeFormatterDependencyStage, NodeFormatterDependencies> {
        @Override
        protected Class<? extends NodeFormatterFactory> getDependentClass(NodeFormatterFactory dependent) {
            return dependent.getClass();
        }

        @Override
        protected NodeFormatterDependencies createResolvedDependencies(List<NodeFormatterDependencyStage> stages) {
            return new NodeFormatterDependencies(stages);
        }

        @Override
        protected NodeFormatterDependencyStage createStage(List<NodeFormatterFactory> dependents) {
            return new NodeFormatterDependencyStage(dependents);
        }
    }

    public static class NodeFormatterDependencies extends ResolvedDependencies<NodeFormatterDependencyStage> {
        private final List<NodeFormatterFactory> nodeFactories;

        public NodeFormatterDependencies(List<NodeFormatterDependencyStage> dependentStages) {
            super(dependentStages);
            ArrayList<NodeFormatterFactory> nodeFormatterFactories = new ArrayList<>();

            for (NodeFormatterDependencyStage stage : dependentStages) {
                nodeFormatterFactories.addAll(stage.dependents);
            }

            this.nodeFactories = nodeFormatterFactories;
        }

        public List<NodeFormatterFactory> getNodeFactories() {
            return nodeFactories;
        }
    }

    public static NodeFormatterDependencies calculateBlockPreProcessors(
            DataHolder options,
            List<NodeFormatterFactory> formatterFactories
    ) {
        // By having the custom factories come first, extensions are able to change behavior of core syntax.
        List<NodeFormatterFactory> list = new ArrayList<NodeFormatterFactory>(formatterFactories);
        list.add(new CoreNodeFormatter.Factory());

        NodeFormatterDependencyHandler resolver = new NodeFormatterDependencyHandler();
        return resolver.resolveDependencies(list);
    }

    public TranslationHandler getTranslationHandler(TranslationHandlerFactory translationHandlerFactory, HtmlIdGeneratorFactory idGeneratorFactory) {
        return translationHandlerFactory.create(options, formatterOptions, idGeneratorFactory);
    }

    public TranslationHandler getTranslationHandler(HtmlIdGeneratorFactory idGeneratorFactory) {
        return new TranslationHandlerImpl(options, formatterOptions, idGeneratorFactory);
    }

    public TranslationHandler getTranslationHandler() {
        return new TranslationHandlerImpl(options, formatterOptions, new HeaderIdGenerator.Factory());
    }

    @Override
    public DataHolder getOptions() {
        return new DataSet(builder);
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
        MainNodeFormatter renderer = new MainNodeFormatter(options, new MarkdownWriter(formatterOptions.formatFlags), node.getDocument(), null);
        renderer.render(node);
        renderer.flushTo(output, formatterOptions.maxTrailingBlankLines);
    }

    /**
     * Render a node to the appendable
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void render(Node node, Appendable output, int maxTrailingBlankLines) {
        MainNodeFormatter renderer = new MainNodeFormatter(options, new MarkdownWriter(formatterOptions.formatFlags), node.getDocument(), null);
        renderer.render(node);
        renderer.flushTo(output, maxTrailingBlankLines);
    }

    /**
     * Render the tree of nodes to markdown
     *
     * @param node the root node
     * @return the formatted markdown
     */
    public String render(Node node) {
        StringBuilder sb = new StringBuilder();
        render(node, sb);
        return sb.toString();
    }

    /**
     * Render a node to the appendable
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void translationRender(Node node, Appendable output, TranslationHandler translationHandler, RenderPurpose renderPurpose) {
        translationRender(node, output, formatterOptions.maxTrailingBlankLines, translationHandler, renderPurpose);
    }

    /**
     * Render the tree of nodes to markdown
     *
     * @param node the root node
     * @return the formatted markdown
     */
    public String translationRender(Node node, TranslationHandler translationHandler, RenderPurpose renderPurpose) {
        StringBuilder sb = new StringBuilder();
        translationRender(node, sb, translationHandler, renderPurpose);
        return sb.toString();
    }

    /**
     * Render a node to the appendable
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void translationRender(Node node, Appendable output, int maxTrailingBlankLines, TranslationHandler translationHandler, RenderPurpose renderPurpose) {
        translationHandler.setRenderPurpose(renderPurpose);
        MainNodeFormatter renderer = new MainNodeFormatter(options, new MarkdownWriter(formatterOptions.formatFlags /*| FormattingAppendable.PASS_THROUGH*/), node.getDocument(), translationHandler);
        renderer.render(node);
        renderer.flushTo(output, maxTrailingBlankLines);
    }

    /**
     * Render a node to the appendable
     *
     * @param documents node to render
     * @param output    appendable to use for the output
     */
    public void mergeRender(Document[] documents, Appendable output, HtmlIdGeneratorFactory idGeneratorFactory) {
        mergeRender(documents, output, formatterOptions.maxTrailingBlankLines, idGeneratorFactory);
    }

    /**
     * Render the tree of nodes to markdown
     *
     * @param documents the root node
     * @return the formatted markdown
     */
    public String mergeRender(Document[] documents, int maxTrailingBlankLines, HtmlIdGeneratorFactory idGeneratorFactory) {
        StringBuilder sb = new StringBuilder();
        mergeRender(documents, sb, maxTrailingBlankLines, idGeneratorFactory);
        return sb.toString();
    }

    /**
     * Render a node to the appendable
     *
     * @param documents nodes to merge render
     * @param output    appendable to use for the output
     */
    public void mergeRender(Document[] documents, Appendable output, int maxTrailingBlankLines, HtmlIdGeneratorFactory idGeneratorFactory) {
        MutableDataSet mergeOptions = new MutableDataSet(options);

        TranslationHandler[] translationHandlers = new TranslationHandler[documents.length];
        List<String>[] translationHandlersTexts = new List[documents.length];

        int iMax = documents.length;
        for (int i = 0; i < iMax; i++) {
            translationHandlers[i] = getTranslationHandler(idGeneratorFactory == null ? new HeaderIdGenerator.Factory() : idGeneratorFactory);
        }

        MergeContextImpl mergeContext = new MergeContextImpl(documents, translationHandlers);

        mergeContext.forEachPrecedingDocument(null, (context, document, index) -> {
            TranslationHandler translationHandler = (TranslationHandler) context;

            translationHandler.setRenderPurpose(RenderPurpose.TRANSLATION_SPANS);
            MainNodeFormatter renderer = new MainNodeFormatter(mergeOptions, new MarkdownWriter(formatterOptions.formatFlags), document, translationHandler);
            renderer.render(document);
            translationHandlersTexts[index] = translationHandler.getTranslatingTexts();
        });

        Document[] translatedDocuments = new Document[documents.length];

        mergeContext.forEachPrecedingDocument(null, (context, document, index) -> {
            TranslationHandler translationHandler = (TranslationHandler) context;

            translationHandler.setRenderPurpose(RenderPurpose.TRANSLATED_SPANS);
            translationHandler.setTranslatedTexts(translationHandlersTexts[index]);

            MainNodeFormatter renderer = new MainNodeFormatter(mergeOptions, new MarkdownWriter(formatterOptions.formatFlags), document, translationHandler);
            renderer.render(document);
            StringBuilder sb = new StringBuilder();
            renderer.flushTo(sb, maxTrailingBlankLines);

            translatedDocuments[index] = Parser.builder(options).build().parse(sb.toString());
        });

        mergeContext.setDocuments(translatedDocuments);

        mergeContext.forEachPrecedingDocument(null, (context, document, index) -> {
            TranslationHandler translationHandler = (TranslationHandler) context;

            translationHandler.setRenderPurpose(RenderPurpose.TRANSLATED);

            MainNodeFormatter renderer = new MainNodeFormatter(mergeOptions, new MarkdownWriter(formatterOptions.formatFlags), document, translationHandler);
            renderer.render(document);
            renderer.flushTo(output, maxTrailingBlankLines);
        });
    }

    public Formatter withOptions(DataHolder options) {
        return options == null ? this : new Formatter(new Builder(builder, options));
    }

    /**
     * Builder for configuring an {@link Formatter}. See methods for default configuration.
     */
    public static class Builder extends BuilderBase<Builder> {
        List<AttributeProviderFactory> attributeProviderFactories = new ArrayList<AttributeProviderFactory>();
        List<NodeFormatterFactory> nodeFormatterFactories = new ArrayList<NodeFormatterFactory>();
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

            this.attributeProviderFactories.addAll(other.attributeProviderFactories);
            //this.nodeFormatterFactories.addAll(other.nodeFormatterFactories); // not re-used
            this.linkResolverFactories.addAll(other.linkResolverFactories);
            //this.htmlIdGeneratorFactory = other.htmlIdGeneratorFactory;
        }

        public Builder(Builder other, DataHolder options) {
            this(other);
            withOptions(options);
        }

        /**
         * @return the configured {@link Formatter}
         */
        public Formatter build() {
            return new Formatter(this);
        }

        @Override
        protected void removeApiPoint(Object apiPoint) {
            if (apiPoint instanceof AttributeProviderFactory) this.attributeProviderFactories.remove(apiPoint.getClass());
            else if (apiPoint instanceof NodeFormatterFactory) this.nodeFormatterFactories.remove(apiPoint);
            else if (apiPoint instanceof LinkResolverFactory) this.linkResolverFactories.remove(apiPoint);
            else if (apiPoint instanceof HeaderIdGeneratorFactory) this.htmlIdGeneratorFactory = null;
            else {
                throw new IllegalStateException("Unknown data point type: " + apiPoint.getClass().getName());
            }
        }

        @Override
        protected void preloadExtension(Extension extension) {
            if (extension instanceof FormatterExtension) {
                FormatterExtension formatterExtension = (FormatterExtension) extension;
                formatterExtension.rendererOptions(this);
            }
        }

        @Override
        protected boolean loadExtension(Extension extension) {
            if (extension instanceof FormatterExtension) {
                FormatterExtension formatterExtension = (FormatterExtension) extension;
                formatterExtension.extend(this);
                return true;
            }
            return false;
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
        @SuppressWarnings("UnusedReturnValue")
        public Builder nodeFormatterFactory(NodeFormatterFactory nodeFormatterFactory) {
            this.nodeFormatterFactories.add(nodeFormatterFactory);
            return this;
        }
    }

    /**
     * Extension for {@link Formatter}.
     */
    public interface FormatterExtension extends Extension {
        /**
         * This method is called first on all extensions so that they can adjust the options.
         *
         * @param options option set that will be used for the builder
         */
        void rendererOptions(MutableDataHolder options);

        void extend(Builder builder);
    }

    private final static Iterator<? extends Node> NULL_ITERATOR = new Iterator<Node>() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Node next() {
            return null;
        }

        @Override
        public void remove() {
        }
    };

    final public static Iterable<? extends Node> NULL_ITERABLE = new Iterable<Node>() {
        @Override
        public Iterator<Node> iterator() {
            return null;
        }
    };

    private class MainNodeFormatter extends NodeFormatterSubContext {
        private final Document document;
        private final Map<Class, NodeFormattingHandler<?>> renderers;
        private final SubClassingBag<Node> collectedNodes;

        private final List<PhasedNodeFormatter> phasedFormatters;
        private final Set<FormattingPhase> renderingPhases;
        private final DataHolder options;
        private FormattingPhase phase;
        final TranslationHandler myTranslationHandler;
        private final LinkResolver[] myLinkResolvers;
        private final HashMap<LinkType, HashMap<String, ResolvedLink>> resolvedLinkMap = new HashMap<LinkType, HashMap<String, ResolvedLink>>();
        private final ExplicitAttributeIdProvider myExplicitAttributeIdProvider;

        MainNodeFormatter(DataHolder options, MarkdownWriter out, Document document, TranslationHandler translationHandler) {
            super(out);
            this.myTranslationHandler = translationHandler;
            this.options = new ScopedDataSet(document, options);
            this.document = document;
            this.renderers = new HashMap<Class, NodeFormattingHandler<?>>(32);
            this.renderingPhases = new HashSet<FormattingPhase>(FormattingPhase.values().length);
            Set<Class> collectNodeTypes = new HashSet<Class>(100);

            Boolean defaultLinkResolver = DEFAULT_LINK_RESOLVER.getFrom(options);
            this.myLinkResolvers = new LinkResolver[linkResolverFactories.size() + (defaultLinkResolver ? 1 : 0)];

            for (int i = 0; i < linkResolverFactories.size(); i++) {
                myLinkResolvers[i] = linkResolverFactories.get(i).apply(this);
            }

            if (defaultLinkResolver) {
                // add the default link resolver
                myLinkResolvers[linkResolverFactories.size()] = new MergeLinkResolver.Factory().apply(this);
            }

            out.setContext(this);

            List<NodeFormatterFactory> formatterFactories = nodeFormatterFactories.getNodeFactories();
            this.phasedFormatters = new ArrayList<PhasedNodeFormatter>(formatterFactories.size());
            ExplicitAttributeIdProvider explicitAttributeIdProvider = null;

            for (int i = formatterFactories.size() - 1; i >= 0; i--) {
                NodeFormatterFactory nodeFormatterFactory = formatterFactories.get(i);
                NodeFormatter nodeFormatter = nodeFormatterFactory.create(this.options);

                // see if implements
                if (nodeFormatter instanceof ExplicitAttributeIdProvider) {
                    explicitAttributeIdProvider = (ExplicitAttributeIdProvider) nodeFormatter;
                }

                Set<NodeFormattingHandler<?>> formattingHandlers = nodeFormatter.getNodeFormattingHandlers();
                if (formattingHandlers == null) continue;

                for (NodeFormattingHandler<?> nodeType : formattingHandlers) {
                    // Overwrite existing renderer
                    renderers.put(nodeType.getNodeType(), nodeType);
                }

                // get nodes of interest
                Set<Class<?>> nodeClasses = nodeFormatter.getNodeClasses();
                if (nodeClasses != null) {
                    collectNodeTypes.addAll(nodeClasses);
                }

                if (nodeFormatter instanceof PhasedNodeFormatter) {
                    Set<FormattingPhase> phases = ((PhasedNodeFormatter) nodeFormatter).getFormattingPhases();
                    if (phases != null) {
                        if (phases.isEmpty()) throw new IllegalStateException("PhasedNodeFormatter with empty Phases");
                        this.renderingPhases.addAll(phases);
                        this.phasedFormatters.add((PhasedNodeFormatter) nodeFormatter);
                    } else {
                        throw new IllegalStateException("PhasedNodeFormatter with null Phases");
                    }
                }
            }

            myExplicitAttributeIdProvider = explicitAttributeIdProvider;

            // collect nodes of interest from document
            if (!collectNodeTypes.isEmpty()) {
                NodeCollectingVisitor collectingVisitor = new NodeCollectingVisitor(collectNodeTypes);
                collectingVisitor.collect(document);
                collectedNodes = collectingVisitor.getSubClassingBag();
            } else {
                collectedNodes = null;
            }
        }

        @Override
        public String encodeUrl(CharSequence url) {
            return String.valueOf(url);
        }

        @Override
        public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Boolean urlEncode) {
            return resolveLink(this, linkType, url, (Attributes) null, urlEncode);
        }

        @Override
        public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Attributes attributes, Boolean urlEncode) {
            return resolveLink(this, linkType, url, attributes, urlEncode);
        }

        private ResolvedLink resolveLink(NodeFormatterSubContext context, LinkType linkType, CharSequence url, Attributes attributes, Boolean urlEncode) {
            HashMap<String, ResolvedLink> resolvedLinks = resolvedLinkMap.computeIfAbsent(linkType, k -> new HashMap<String, ResolvedLink>());

            String urlSeq = String.valueOf(url);
            ResolvedLink resolvedLink = resolvedLinks.get(urlSeq);
            if (resolvedLink == null) {
                resolvedLink = new ResolvedLink(linkType, urlSeq, attributes);

                if (!urlSeq.isEmpty()) {
                    Node currentNode = context.renderingNode;

                    for (LinkResolver linkResolver : myLinkResolvers) {
                        resolvedLink = linkResolver.resolveLink(currentNode, this, resolvedLink);
                        if (resolvedLink.getStatus() != LinkStatus.UNKNOWN) break;
                    }
                }

                // put it in the map
                resolvedLinks.put(urlSeq, resolvedLink);
            }

            return resolvedLink;
        }

        @Override
        public void addExplicitId(Node node, String id, NodeFormatterContext context, MarkdownWriter markdown) {
            if (myExplicitAttributeIdProvider != null) {
                myExplicitAttributeIdProvider.addExplicitId(node, id, context, markdown);
            }
        }

        @Override
        public RenderPurpose getRenderPurpose() {
            return myTranslationHandler == null ? RenderPurpose.FORMAT : myTranslationHandler.getRenderPurpose();
        }

        @Override
        public boolean isTransformingText() {
            return myTranslationHandler != null && myTranslationHandler.isTransformingText();
        }

        @Override
        public CharSequence transformNonTranslating(CharSequence prefix, CharSequence nonTranslatingText, CharSequence suffix, CharSequence suffix2) {
            return myTranslationHandler == null ? nonTranslatingText : myTranslationHandler.transformNonTranslating(prefix, nonTranslatingText, suffix, suffix2);
        }

        @Override
        public CharSequence transformTranslating(CharSequence prefix, CharSequence translatingText, CharSequence suffix, CharSequence suffix2) {
            return myTranslationHandler == null ? translatingText : myTranslationHandler.transformTranslating(prefix, translatingText, suffix, suffix2);
        }

        @Override
        public CharSequence transformAnchorRef(CharSequence pageRef, CharSequence anchorRef) {
            return myTranslationHandler == null ? anchorRef : myTranslationHandler.transformAnchorRef(pageRef, anchorRef);
        }

        @Override
        public void postProcessNonTranslating(Function<String, CharSequence> postProcessor, Runnable scope) {
            if (myTranslationHandler != null) myTranslationHandler.postProcessNonTranslating(postProcessor, scope);
            else scope.run();
        }

        @Override
        public <T> T postProcessNonTranslating(Function<String, CharSequence> postProcessor, Supplier<T> scope) {
            if (myTranslationHandler != null) return myTranslationHandler.postProcessNonTranslating(postProcessor, scope);
            else return scope.get();
        }

        @Override
        public boolean isPostProcessingNonTranslating() {
            return myTranslationHandler != null && myTranslationHandler.isPostProcessingNonTranslating();
        }

        @Override
        public MergeContext getMergeContext() {
            return myTranslationHandler == null ? null : myTranslationHandler.getMergeContext();
        }

        @Override
        public HtmlIdGenerator getIdGenerator() {
            return myTranslationHandler == null ? null : myTranslationHandler.getIdGenerator();
        }

        @Override
        public void translatingSpan(TranslatingSpanRender render) {
            if (myTranslationHandler != null) {
                myTranslationHandler.translatingSpan(render);
            } else {
                render.render(this, markdown);
            }
        }

        @Override
        public void nonTranslatingSpan(TranslatingSpanRender render) {
            if (myTranslationHandler != null) {
                myTranslationHandler.nonTranslatingSpan(render);
            } else {
                render.render(this, markdown);
            }
        }

        @Override
        public void translatingRefTargetSpan(Node target, TranslatingSpanRender render) {
            if (myTranslationHandler != null) {
                myTranslationHandler.translatingRefTargetSpan(target, render);
            } else {
                render.render(this, markdown);
            }
        }

        @Override
        public MutableDataHolder getTranslationStore() {
            if (myTranslationHandler != null) {
                return myTranslationHandler.getTranslationStore();
            } else {
                return document;
            }
        }

        @Override
        public void customPlaceholderFormat(TranslationPlaceholderGenerator generator, TranslatingSpanRender render) {
            if (myTranslationHandler != null) {
                myTranslationHandler.customPlaceholderFormat(generator, render);
            } else {
                render.render(this, markdown);
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
        public final Iterable<? extends Node> nodesOfType(Class<?>[] classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.itemsOfType(Node.class, classes);
        }

        @Override
        public final Iterable<? extends Node> nodesOfType(Collection<Class<?>> classes) {
            //noinspection unchecked
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.itemsOfType(Node.class, classes);
        }

        @Override
        public final Iterable<? extends Node> reversedNodesOfType(Class<?>[] classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.reversedItemsOfType(Node.class, classes);
        }

        @Override
        public final Iterable<? extends Node> reversedNodesOfType(Collection<Class<?>> classes) {
            //noinspection unchecked
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.reversedItemsOfType(Node.class, classes);
        }

        @Override
        public NodeFormatterContext getSubContext() {
            MarkdownWriter writer = new MarkdownWriter(getMarkdown().getOptions());
            writer.setContext(this);
            //noinspection ReturnOfInnerClass
            return new SubNodeFormatter(this, writer);
        }

        void renderNode(Node node, NodeFormatterSubContext subContext) {
            if (node instanceof Document) {
                // here we render multiple phases
                if (myTranslationHandler != null) {
                    myTranslationHandler.beginRendering((Document) node, subContext, subContext.markdown);
                }

                for (FormattingPhase phase : FormattingPhase.values()) {
                    if (phase != FormattingPhase.DOCUMENT && !renderingPhases.contains(phase)) { continue; }
                    this.phase = phase;
                    // here we render multiple phases
                    if (this.phase == FormattingPhase.DOCUMENT) {
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

                if (nodeRenderer == null) {
                    nodeRenderer = renderers.get(Node.class);
                }

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
            public MutableDataHolder getTranslationStore() {
                return myMainNodeRenderer.getTranslationStore();
            }

            @Override
            public final Iterable<? extends Node> nodesOfType(Class<?>[] classes) {
                return myMainNodeRenderer.nodesOfType(classes);
            }

            @Override
            public final Iterable<? extends Node> nodesOfType(Collection<Class<?>> classes) {
                return myMainNodeRenderer.nodesOfType(classes);
            }

            @Override
            public final Iterable<? extends Node> reversedNodesOfType(Class<?>[] classes) {
                return myMainNodeRenderer.reversedNodesOfType(classes);
            }

            @Override
            public final Iterable<? extends Node> reversedNodesOfType(Collection<Class<?>> classes) {
                //noinspection unchecked
                return myMainNodeRenderer.reversedNodesOfType(classes);
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
            public NodeFormatterContext getSubContext() {
                MarkdownWriter htmlWriter = new MarkdownWriter(this.markdown.getOptions());
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

            @Override
            public RenderPurpose getRenderPurpose() {
                return myMainNodeRenderer.getRenderPurpose();
            }

            @Override
            public boolean isTransformingText() {
                return myMainNodeRenderer.isTransformingText();
            }

            @Override
            public CharSequence transformNonTranslating(CharSequence prefix, CharSequence nonTranslatingText, CharSequence suffix, CharSequence suffix2) {
                return myMainNodeRenderer.transformNonTranslating(prefix, nonTranslatingText, suffix, suffix2);
            }

            @Override
            public CharSequence transformTranslating(CharSequence prefix, CharSequence translatingText, CharSequence suffix, CharSequence suffix2) {
                return myMainNodeRenderer.transformTranslating(prefix, translatingText, suffix, suffix2);
            }

            @Override
            public CharSequence transformAnchorRef(CharSequence pageRef, CharSequence anchorRef) {
                return myMainNodeRenderer.transformAnchorRef(pageRef, anchorRef);
            }

            @Override
            public void translatingSpan(TranslatingSpanRender render) {
                myMainNodeRenderer.translatingSpan(render);
            }

            @Override
            public void nonTranslatingSpan(TranslatingSpanRender render) {
                myMainNodeRenderer.nonTranslatingSpan(render);
            }

            @Override
            public void translatingRefTargetSpan(Node target, TranslatingSpanRender render) {
                myMainNodeRenderer.translatingRefTargetSpan(target, render);
            }

            @Override
            public void customPlaceholderFormat(TranslationPlaceholderGenerator generator, TranslatingSpanRender render) {
                myMainNodeRenderer.customPlaceholderFormat(generator, render);
            }

            @Override
            public String encodeUrl(CharSequence url) {
                return myMainNodeRenderer.encodeUrl(url);
            }

            @Override
            public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(this, linkType, url, (Attributes) null, urlEncode);
            }

            @Override
            public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Attributes attributes, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(this, linkType, url, attributes, urlEncode);
            }

            @Override
            public void postProcessNonTranslating(Function<String, CharSequence> postProcessor, Runnable scope) {
                myMainNodeRenderer.postProcessNonTranslating(postProcessor, scope);
            }

            @Override
            public <T> T postProcessNonTranslating(Function<String, CharSequence> postProcessor, Supplier<T> scope) {
                return myMainNodeRenderer.postProcessNonTranslating(postProcessor, scope);
            }

            @Override
            public boolean isPostProcessingNonTranslating() {
                return myMainNodeRenderer.isPostProcessingNonTranslating();
            }

            @Override
            public MergeContext getMergeContext() {
                return myMainNodeRenderer.getMergeContext();
            }

            @Override
            public void addExplicitId(Node node, String id, NodeFormatterContext context, MarkdownWriter markdown) {
                myMainNodeRenderer.addExplicitId(node, id, context, markdown);
            }

            @Override
            public HtmlIdGenerator getIdGenerator() {
                return myMainNodeRenderer.getIdGenerator();
            }
        }
    }
}
