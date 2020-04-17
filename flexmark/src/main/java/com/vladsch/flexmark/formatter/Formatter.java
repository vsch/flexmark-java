package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.html.AttributeProviderFactory;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.builder.BuilderBase;
import com.vladsch.flexmark.util.collection.SubClassingBag;
import com.vladsch.flexmark.util.data.*;
import com.vladsch.flexmark.util.dependency.DependencyResolver;
import com.vladsch.flexmark.util.format.*;
import com.vladsch.flexmark.util.format.options.*;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Extension;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.formatter.FormattingPhase.DOCUMENT;

/**
 * Renders a tree of nodes to Markdown.
 * <p>
 * Start with the {@link #builder} method to configure the renderer. Example:
 * <pre><code>
 * Formatter formatter = Formatter.builder().build();
 * formatter.render(node);
 * </code></pre>
 */
@SuppressWarnings("WeakerAccess")
public class Formatter implements IRender {
    final public static Document[] EMPTY_DOCUMENTS = new Document[0];
    /**
     * output control for FormattingAppendable, see {@link LineAppendable#setOptions(int)}
     */
    final public static DataKey<Integer> FORMAT_FLAGS = new DataKey<>("FORMAT_FLAGS", LineAppendable.F_TRIM_LEADING_WHITESPACE | LineAppendable.F_TRIM_LEADING_EOL);

    // Use LineAppendable values instead
    // NOTE: F_ALLOW_LEADING_WHITESPACE is now inverted and named F_TRIM_LEADING_WHITESPACE
    @Deprecated final public static int FORMAT_CONVERT_TABS = LineAppendable.F_CONVERT_TABS;
    @Deprecated final public static int FORMAT_COLLAPSE_WHITESPACE = LineAppendable.F_COLLAPSE_WHITESPACE;
    @Deprecated final public static int FORMAT_SUPPRESS_TRAILING_WHITESPACE = LineAppendable.F_TRIM_TRAILING_WHITESPACE;
    @Deprecated final public static int FORMAT_ALL_OPTIONS = LineAppendable.F_FORMAT_ALL;

    final public static DataKey<Boolean> GENERATE_HEADER_ID = new DataKey<>("GENERATE_HEADER_ID", false);

    final public static DataKey<Integer> MAX_BLANK_LINES = SharedDataKeys.FORMATTER_MAX_BLANK_LINES;
    final public static DataKey<Integer> MAX_TRAILING_BLANK_LINES = SharedDataKeys.FORMATTER_MAX_TRAILING_BLANK_LINES;
    final public static DataKey<Integer> RIGHT_MARGIN = new DataKey<>("RIGHT_MARGIN", 0);

    final public static DataKey<Boolean> APPLY_SPECIAL_LEAD_IN_HANDLERS = SharedDataKeys.APPLY_SPECIAL_LEAD_IN_HANDLERS;
    final public static DataKey<Boolean> ESCAPE_SPECIAL_CHARS = SharedDataKeys.ESCAPE_SPECIAL_CHARS;
    final public static DataKey<Boolean> ESCAPE_NUMBERED_LEAD_IN = SharedDataKeys.ESCAPE_NUMBERED_LEAD_IN;
    final public static DataKey<Boolean> UNESCAPE_SPECIAL_CHARS = SharedDataKeys.UNESCAPE_SPECIAL_CHARS;

    final public static DataKey<DiscretionaryText> SPACE_AFTER_ATX_MARKER = new DataKey<>("SPACE_AFTER_ATX_MARKER", DiscretionaryText.ADD);
    final public static DataKey<Boolean> SETEXT_HEADING_EQUALIZE_MARKER = new DataKey<>("SETEXT_HEADING_EQUALIZE_MARKER", true);
    final public static DataKey<EqualizeTrailingMarker> ATX_HEADING_TRAILING_MARKER = new DataKey<>("ATX_HEADING_TRAILING_MARKER", EqualizeTrailingMarker.AS_IS);
    final public static DataKey<HeadingStyle> HEADING_STYLE = new DataKey<>("HEADING_STYLE", HeadingStyle.AS_IS);
    final public static NullableDataKey<String> THEMATIC_BREAK = new NullableDataKey<>("THEMATIC_BREAK");
    final public static DataKey<Boolean> BLOCK_QUOTE_BLANK_LINES = SharedDataKeys.BLOCK_QUOTE_BLANK_LINES;
    final public static DataKey<BlockQuoteMarker> BLOCK_QUOTE_MARKERS = new DataKey<>("BLOCK_QUOTE_MARKERS", BlockQuoteMarker.ADD_COMPACT_WITH_SPACE);
    final public static DataKey<Boolean> INDENTED_CODE_MINIMIZE_INDENT = new DataKey<>("INDENTED_CODE_MINIMIZE_INDENT", true);
    final public static DataKey<Boolean> FENCED_CODE_MINIMIZE_INDENT = new DataKey<>("FENCED_CODE_MINIMIZE_INDENT", true);
    final public static DataKey<Boolean> FENCED_CODE_MATCH_CLOSING_MARKER = new DataKey<>("FENCED_CODE_MATCH_CLOSING_MARKER", true);
    final public static DataKey<Boolean> FENCED_CODE_SPACE_BEFORE_INFO = new DataKey<>("FENCED_CODE_SPACE_BEFORE_INFO", false);
    final public static DataKey<Integer> FENCED_CODE_MARKER_LENGTH = new DataKey<>("FENCED_CODE_MARKER_LENGTH", 3);
    final public static DataKey<CodeFenceMarker> FENCED_CODE_MARKER_TYPE = new DataKey<>("FENCED_CODE_MARKER_TYPE", CodeFenceMarker.ANY);
    final public static DataKey<Boolean> LIST_ADD_BLANK_LINE_BEFORE = new DataKey<>("LIST_ADD_BLANK_LINE_BEFORE", false);
    final public static DataKey<Boolean> LIST_RENUMBER_ITEMS = new DataKey<>("LIST_RENUMBER_ITEMS", true);
    final public static DataKey<Boolean> LIST_REMOVE_EMPTY_ITEMS = new DataKey<>("LIST_REMOVE_EMPTY_ITEMS", false);
    final public static DataKey<ElementAlignment> LIST_ALIGN_NUMERIC = new DataKey<>("LIST_ALIGN_NUMERIC", ElementAlignment.NONE);
    final public static DataKey<Boolean> LIST_RESET_FIRST_ITEM_NUMBER = new DataKey<>("LIST_RESET_FIRST_ITEM_NUMBER", false);
    final public static DataKey<ListBulletMarker> LIST_BULLET_MARKER = new DataKey<>("LIST_BULLET_MARKER", ListBulletMarker.ANY);
    final public static DataKey<ListNumberedMarker> LIST_NUMBERED_MARKER = new DataKey<>("LIST_NUMBERED_MARKER", ListNumberedMarker.ANY);
    final public static DataKey<ListSpacing> LIST_SPACING = new DataKey<>("LIST_SPACING", ListSpacing.AS_IS);
    final public static DataKey<Boolean> LISTS_ITEM_CONTENT_AFTER_SUFFIX = new DataKey<>("LISTS_ITEM_CONTENT_AFTER_SUFFIX", false);
    final public static DataKey<ElementPlacement> REFERENCE_PLACEMENT = new DataKey<>("REFERENCE_PLACEMENT", ElementPlacement.AS_IS);
    final public static DataKey<ElementPlacementSort> REFERENCE_SORT = new DataKey<>("REFERENCE_SORT", ElementPlacementSort.AS_IS);
    final public static DataKey<Boolean> KEEP_IMAGE_LINKS_AT_START = new DataKey<>("KEEP_IMAGE_LINKS_AT_START", false);
    final public static DataKey<Boolean> KEEP_EXPLICIT_LINKS_AT_START = new DataKey<>("KEEP_EXPLICIT_LINKS_AT_START", false);
    final public static DataKey<Boolean> OPTIMIZED_INLINE_RENDERING = new DataKey<>("OPTIMIZED_INLINE_RENDERING", false);
    final public static DataKey<CharWidthProvider> FORMAT_CHAR_WIDTH_PROVIDER = TableFormatOptions.FORMAT_CHAR_WIDTH_PROVIDER;
    final public static DataKey<Boolean> KEEP_HARD_LINE_BREAKS = new DataKey<>("KEEP_HARD_LINE_BREAKS", true);
    final public static DataKey<Boolean> KEEP_SOFT_LINE_BREAKS = new DataKey<>("KEEP_SOFT_LINE_BREAKS", true);
    final public static DataKey<String> FORMATTER_ON_TAG = new DataKey<>("FORMATTER_ON_TAG", "@formatter" + ":on");
    final public static DataKey<String> FORMATTER_OFF_TAG = new DataKey<>("FORMATTER_OFF_TAG", "@formatter" + ":off");
    final public static DataKey<Boolean> FORMATTER_TAGS_ENABLED = new DataKey<>("FORMATTER_TAGS_ENABLED", false);
    final public static DataKey<Boolean> FORMATTER_TAGS_ACCEPT_REGEXP = new DataKey<>("FORMATTER_TAGS_ACCEPT_REGEXP", false);
    final public static NullableDataKey<Pattern> LINK_MARKER_COMMENT_PATTERN = new NullableDataKey<>("FORMATTER_TAGS_ACCEPT_REGEXP", (Pattern) null);

    final public static DataKey<Boolean> APPEND_TRANSFERRED_REFERENCES = new DataKey<>("APPEND_TRANSFERRED_REFERENCES", false);

    // used for translation phases of rendering
    final public static DataKey<String> TRANSLATION_ID_FORMAT = new DataKey<>("TRANSLATION_ID_FORMAT", "_%d_");
    final public static DataKey<String> TRANSLATION_HTML_BLOCK_PREFIX = new DataKey<>("TRANSLATION_HTML_BLOCK_PREFIX", "__");
    final public static DataKey<String> TRANSLATION_HTML_INLINE_PREFIX = new DataKey<>("TRANSLATION_HTML_INLINE_PREFIX", "_");
    final public static DataKey<String> TRANSLATION_AUTOLINK_PREFIX = new DataKey<>("TRANSLATION_AUTOLINK_PREFIX", "___");
    final public static DataKey<String> TRANSLATION_EXCLUDE_PATTERN = new DataKey<>("TRANSLATION_EXCLUDE_PATTERN", "^[^\\p{IsAlphabetic}]*$");
    final public static DataKey<String> TRANSLATION_HTML_BLOCK_TAG_PATTERN = Parser.TRANSLATION_HTML_BLOCK_TAG_PATTERN;
    final public static DataKey<String> TRANSLATION_HTML_INLINE_TAG_PATTERN = Parser.TRANSLATION_HTML_INLINE_TAG_PATTERN;

    // link resolver info for doc relative and doc root urls
    final public static DataKey<String> DOC_RELATIVE_URL = new DataKey<>("DOC_RELATIVE_URL", "");
    final public static DataKey<String> DOC_ROOT_URL = new DataKey<>("DOC_ROOT_URL", "");
    final public static DataKey<Boolean> DEFAULT_LINK_RESOLVER = new DataKey<>("DEFAULT_LINK_RESOLVER", false);

    // formatter family override
    final public static DataKey<ParserEmulationProfile> FORMATTER_EMULATION_PROFILE = new DataKey<>("FORMATTER_EMULATION_PROFILE", Parser.PARSER_EMULATION_PROFILE);

    // CAUTION: these keys should be set on the Document node being formatted or the formatter
    //  however a formatter instance can be used to format multiple documents while these are document specific so unless
    //  only a single document will be formatted by a formatter instance set them on the document only.
    // {{
    // these are used by table and paragraph wrapping
    final public static DataKey<List<TrackedOffset>> TRACKED_OFFSETS = new DataKey<>("TRACKED_OFFSETS", Collections.emptyList());

    // original sequence to use for tracked offset resolution since parser takes a contiguous sequence this is the equivalent sequence
    final public static DataKey<BasedSequence> TRACKED_SEQUENCE = new DataKey<>("TRACKED_SEQUENCE", BasedSequence.NULL);

    // used during paragraph wrapping to determine whether spaces are re-inserted if offsets are edit op flagged
    final public static DataKey<Boolean> RESTORE_TRACKED_SPACES = new DataKey<>("RESTORE_END_SPACES", false);

    // can be used to set indent for the document, useful when formatting a single paragraph that is extracted from another document
    //  and formatted paragraph will be re-inserted into that document
    final public static DataKey<CharSequence> DOCUMENT_FIRST_PREFIX = new DataKey<>("DOCUMENT_FIRST_PREFIX", BasedSequence.NULL);
    final public static DataKey<CharSequence> DOCUMENT_PREFIX = new DataKey<>("DOCUMENT_PREFIX", BasedSequence.NULL);
    // }}

    /**
     * use corrected name
     */
    @Deprecated final public static DataKey<Boolean> SETEXT_HEADER_EQUALIZE_MARKER = SETEXT_HEADING_EQUALIZE_MARKER;
    /**
     * use corrected name
     */
    @Deprecated final public static DataKey<EqualizeTrailingMarker> ATX_HEADER_TRAILING_MARKER = ATX_HEADING_TRAILING_MARKER;

    /**
     * use TableFormatOptions instead
     */
    @Deprecated final public static DataKey<TableCaptionHandling> FORMAT_TABLE_CAPTION = TableFormatOptions.FORMAT_TABLE_CAPTION;
    /**
     * use TableFormatOptions instead
     */
    @Deprecated final public static DataKey<DiscretionaryText> FORMAT_TABLE_CAPTION_SPACES = TableFormatOptions.FORMAT_TABLE_CAPTION_SPACES;
    /**
     * use TableFormatOptions instead
     */
    @Deprecated final public static DataKey<String> FORMAT_TABLE_INDENT_PREFIX = TableFormatOptions.FORMAT_TABLE_INDENT_PREFIX;

    // used during translation
    final public static DataKey<Map<String, String>> UNIQUIFICATION_MAP = new DataKey<>("REFERENCES_UNIQUIFICATION_MAP", HashMap::new);
    final public static DataKey<Map<String, String>> ATTRIBUTE_UNIQUIFICATION_ID_MAP = new DataKey<>("ATTRIBUTE_UNIQUIFICATION_ID_MAP", HashMap::new);

    final private DataHolder options;
    final List<LinkResolverFactory> linkResolverFactories;
    final List<NodeFormatterFactory> nodeFormatterFactories;
    final HeaderIdGeneratorFactory idGeneratorFactory;

    Formatter(Builder builder) {
        this.options = builder.toImmutable();
        this.idGeneratorFactory = builder.htmlIdGeneratorFactory == null ? new HeaderIdGenerator.Factory() : builder.htmlIdGeneratorFactory;

        this.linkResolverFactories = DependencyResolver.resolveFlatDependencies(builder.linkResolverFactories, null, null);
        this.nodeFormatterFactories = calculateNodeFormatterFactories(builder.nodeFormatterFactories);
    }

    private static List<NodeFormatterFactory> calculateNodeFormatterFactories(List<NodeFormatterFactory> formatterFactories) {
        // By having the custom factories come first, extensions are able to change behavior of core syntax.
        List<NodeFormatterFactory> list = new ArrayList<>(formatterFactories);
        list.add(new CoreNodeFormatter.Factory());
        return DependencyResolver.resolveFlatDependencies(list, null, null);
    }

    public TranslationHandler getTranslationHandler(TranslationHandlerFactory translationHandlerFactory, HtmlIdGeneratorFactory idGeneratorFactory) {
        return translationHandlerFactory.create(options, idGeneratorFactory);
    }

    public TranslationHandler getTranslationHandler(HtmlIdGeneratorFactory idGeneratorFactory) {
        return new TranslationHandlerImpl(options, idGeneratorFactory);
    }

    public TranslationHandler getTranslationHandler() {
        return new TranslationHandlerImpl(options, idGeneratorFactory);
    }

    @NotNull
    @Override
    public DataHolder getOptions() {
        return options;
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
     * @return a builder.
     */
    public static Builder builder(DataHolder options) {
        return new Builder(options);
    }

    /**
     * Render a node to the appendable
     * <p>
     * NOTE: if Appendable is LineAppendable then its builder will be used as builder for the markdown text, else string sequence builder will be used
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void render(@NotNull Node node, @NotNull Appendable output) {
        render(node, output, MAX_TRAILING_BLANK_LINES.get(options));
    }

    /**
     * Render node
     * <p>
     * NOTE: if Appendable is LineAppendable then its builder will be used as builder for the markdown text, else string sequence builder will be used
     *
     * @param node                  node to render
     * @param output                appendable to which to render the resulting text
     * @param maxTrailingBlankLines max trailing blank lines in output, -1 means no last line EOL
     */
    public void render(@NotNull Node node, @NotNull Appendable output, int maxTrailingBlankLines) {
        // NOTE: output to MarkdownWriter is only used to get builder if output is LineAppendable or ISequenceBuilder
        MarkdownWriter markdown = new MarkdownWriter(output, FORMAT_FLAGS.get(options));
        MainNodeFormatter renderer = new MainNodeFormatter(options, markdown, node.getDocument(), null);
        renderer.render(node);
        markdown.appendToSilently(output, MAX_BLANK_LINES.get(options), maxTrailingBlankLines);

        // resolve any unresolved tracked offsets that are outside elements which resolve their own
        BasedSequence sequence = node.getDocument().getChars();
        if (output instanceof SequenceBuilder && node.getDocument().getChars() != renderer.trackedSequence) {
            // have to use alternate builder sequence for tracked offset resolution
            sequence = ((SequenceBuilder) output).toSequence(renderer.trackedSequence);
        }

        TrackedOffsetUtils.resolveTrackedOffsets(sequence, markdown, renderer.trackedOffsets.getUnresolvedOffsets(), maxTrailingBlankLines, SharedDataKeys.RUNNING_TESTS.get(options));
    }

    /**
     * Render the tree of nodes to markdown
     *
     * @param document the root node
     * @return the formatted markdown
     */
    @NotNull
    public String render(@NotNull Node document) {
        StringBuilder sb = new StringBuilder();
        render(document, sb);
        return sb.toString();
    }

    /**
     * Render a node to the appendable
     *
     * @param document node to render
     * @param output   appendable to use for the output
     */
    public void translationRender(Node document, Appendable output, TranslationHandler translationHandler, RenderPurpose renderPurpose) {
        translationRender(document, output, MAX_TRAILING_BLANK_LINES.get(options), translationHandler, renderPurpose);
    }

    /**
     * Render the tree of nodes to markdown
     *
     * @param document the root node
     * @return the formatted markdown
     */
    public String translationRender(Node document, TranslationHandler translationHandler, RenderPurpose renderPurpose) {
        StringBuilder sb = new StringBuilder();
        translationRender(document, sb, translationHandler, renderPurpose);
        return sb.toString();
    }

    /**
     * Render a node to the appendable
     *
     * @param document node to render
     * @param output   appendable to use for the output
     */
    public void translationRender(Node document, Appendable output, int maxTrailingBlankLines, TranslationHandler translationHandler, RenderPurpose renderPurpose) {
        translationHandler.setRenderPurpose(renderPurpose);
        MainNodeFormatter renderer = new MainNodeFormatter(options, new MarkdownWriter(FORMAT_FLAGS.get(options) & ~LineAppendable.F_TRIM_LEADING_WHITESPACE /*| FormattingAppendable.PASS_THROUGH*/), document.getDocument(), translationHandler);
        renderer.render(document);
        renderer.flushTo(output, MAX_BLANK_LINES.get(options), maxTrailingBlankLines);
    }

    /**
     * Render a node to the appendable
     *
     * @param documents node to render
     * @param output    appendable to use for the output
     */
    public void mergeRender(Document[] documents, Appendable output) {
        mergeRender(documents, output, MAX_TRAILING_BLANK_LINES.get(options));
    }

    public void mergeRender(List<Document> documents, Appendable output) {
        mergeRender(documents.toArray(Formatter.EMPTY_DOCUMENTS), output);
    }

    /**
     * Render the tree of nodes to markdown
     *
     * @param documents the root node
     * @return the formatted markdown
     */
    public String mergeRender(Document[] documents, int maxTrailingBlankLines) {
        StringBuilder sb = new StringBuilder();
        mergeRender(documents, sb, maxTrailingBlankLines);
        return sb.toString();
    }

    public String mergeRender(List<Document> documents, int maxTrailingBlankLines) {
        return mergeRender(documents.toArray(Formatter.EMPTY_DOCUMENTS), maxTrailingBlankLines);
    }

    /**
     * Render a node to the appendable
     *
     * @param documents nodes to merge render
     * @param output    appendable to use for the output
     */
    public void mergeRender(List<Document> documents, Appendable output, int maxTrailingBlankLines) {
        mergeRender(documents.toArray(Formatter.EMPTY_DOCUMENTS), output, maxTrailingBlankLines);
    }

    public void mergeRender(Document[] documents, Appendable output, int maxTrailingBlankLines) {
        MutableDataSet mergeOptions = new MutableDataSet(options);
        mergeOptions.set(Parser.HTML_FOR_TRANSLATOR, true);

        TranslationHandler[] translationHandlers = new TranslationHandler[documents.length];
        //noinspection unchecked
        List<String>[] translationHandlersTexts = new List[documents.length];

        int iMax = documents.length;
        for (int i = 0; i < iMax; i++) {
            translationHandlers[i] = getTranslationHandler(idGeneratorFactory == null ? new HeaderIdGenerator.Factory() : idGeneratorFactory);
        }

        MergeContextImpl mergeContext = new MergeContextImpl(documents, translationHandlers);
        int formatFlags = FORMAT_FLAGS.get(this.options);
        int maxBlankLines = MAX_BLANK_LINES.get(this.options);

        mergeContext.forEachPrecedingDocument(null, (context, document, index) -> {
            TranslationHandler translationHandler = (TranslationHandler) context;

            translationHandler.setRenderPurpose(RenderPurpose.TRANSLATION_SPANS);
            MainNodeFormatter renderer = new MainNodeFormatter(mergeOptions, new MarkdownWriter(formatFlags), document, translationHandler);
            renderer.render(document);
            translationHandlersTexts[index] = translationHandler.getTranslatingTexts();
        });

        Document[] translatedDocuments = new Document[documents.length];

        mergeContext.forEachPrecedingDocument(null, (context, document, index) -> {
            TranslationHandler translationHandler = (TranslationHandler) context;

            translationHandler.setRenderPurpose(RenderPurpose.TRANSLATED_SPANS);
            translationHandler.setTranslatedTexts(translationHandlersTexts[index]);

            MainNodeFormatter renderer = new MainNodeFormatter(mergeOptions, new MarkdownWriter(formatFlags), document, translationHandler);
            renderer.render(document);
            StringBuilder sb = new StringBuilder();
            renderer.flushTo(sb, maxBlankLines, maxTrailingBlankLines);

            translatedDocuments[index] = Parser.builder(mergeOptions).build().parse(sb.toString());
        });

        mergeContext.setDocuments(translatedDocuments);

        mergeContext.forEachPrecedingDocument(null, (context, document, index) -> {
            TranslationHandler translationHandler = (TranslationHandler) context;

            translationHandler.setRenderPurpose(RenderPurpose.TRANSLATED);

            MarkdownWriter markdownWriter = new MarkdownWriter(formatFlags);
            MainNodeFormatter renderer = new MainNodeFormatter(mergeOptions, markdownWriter, document, translationHandler);
            renderer.render(document);
            markdownWriter.blankLine();
            renderer.flushTo(output, maxBlankLines, maxTrailingBlankLines);
        });
    }

    /**
     * Builder for configuring an {@link Formatter}. See methods for default configuration.
     */
    public static class Builder extends BuilderBase<Builder> {
        List<AttributeProviderFactory> attributeProviderFactories = new ArrayList<>();
        List<NodeFormatterFactory> nodeFormatterFactories = new ArrayList<>();
        List<LinkResolverFactory> linkResolverFactories = new ArrayList<>();
        HeaderIdGeneratorFactory htmlIdGeneratorFactory = null;

        public Builder() {
            super();
        }

        public Builder(DataHolder options) {
            super(options);
            loadExtensions();
        }

        /**
         * @return the configured {@link Formatter}
         */
        @NotNull
        public Formatter build() {
            return new Formatter(this);
        }

        @Override
        protected void removeApiPoint(@NotNull Object apiPoint) {
            if (apiPoint instanceof AttributeProviderFactory) this.attributeProviderFactories.remove(apiPoint);
            else if (apiPoint instanceof NodeFormatterFactory) this.nodeFormatterFactories.remove(apiPoint);
            else if (apiPoint instanceof LinkResolverFactory) this.linkResolverFactories.remove(apiPoint);
            else if (apiPoint instanceof HeaderIdGeneratorFactory) this.htmlIdGeneratorFactory = null;
            else {
                throw new IllegalStateException("Unknown data point type: " + apiPoint.getClass().getName());
            }
        }

        @Override
        protected void preloadExtension(@NotNull Extension extension) {
            if (extension instanceof FormatterExtension) {
                FormatterExtension formatterExtension = (FormatterExtension) extension;
                formatterExtension.rendererOptions(this);
            }
        }

        @Override
        protected boolean loadExtension(@NotNull Extension extension) {
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

        void extend(Builder formatterBuilder);
    }

    final private static Iterator<Node> NULL_ITERATOR = new Iterator<Node>() {
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

    final public static Iterable<Node> NULL_ITERABLE = () -> NULL_ITERATOR;

    private class MainNodeFormatter extends NodeFormatterSubContext {
        final private Document document;
        final private Map<Class<?>, List<NodeFormattingHandler<?>>> renderers;
        final private SubClassingBag<Node> collectedNodes;

        final private List<PhasedNodeFormatter> phasedFormatters;
        final private Set<FormattingPhase> renderingPhases;
        final private DataHolder options;
        private @NotNull final Boolean isFormatControlEnabled;
        private FormattingPhase phase;
        final TranslationHandler translationHandler;
        final private LinkResolver[] linkResolvers;
        final private HashMap<LinkType, HashMap<String, ResolvedLink>> resolvedLinkMap = new HashMap<>();
        final private ExplicitAttributeIdProvider explicitAttributeIdProvider;
        final private HtmlIdGenerator idGenerator;
        private @Nullable FormatControlProcessor controlProcessor;
        final private CharPredicate blockQuoteLikePredicate;
        final private BasedSequence blockQuoteLikeChars;
        final TrackedOffsetList trackedOffsets;
        final BasedSequence trackedSequence;
        final boolean restoreTrackedSpaces;
        final FormatterOptions formatterOptions;

        MainNodeFormatter(DataHolder options, MarkdownWriter out, Document document, TranslationHandler translationHandler) {
            super(out);
            this.translationHandler = translationHandler;
            this.options = new ScopedDataSet(document, options);
            this.formatterOptions = new FormatterOptions(this.options);
            this.document = document;
            this.renderers = new HashMap<>(32);
            this.renderingPhases = new HashSet<>(FormattingPhase.values().length);
            Set<Class<?>> collectNodeTypes = new HashSet<>(100);

            Boolean defaultLinkResolver = DEFAULT_LINK_RESOLVER.get(this.options);
            this.linkResolvers = new LinkResolver[linkResolverFactories.size() + (defaultLinkResolver ? 1 : 0)];

            isFormatControlEnabled = FORMATTER_TAGS_ENABLED.get(this.options);

            for (int i = 0; i < linkResolverFactories.size(); i++) {
                linkResolvers[i] = linkResolverFactories.get(i).apply(this);
            }

            if (defaultLinkResolver) {
                // add the default link resolver
                linkResolvers[linkResolverFactories.size()] = new MergeLinkResolver.Factory().apply(this);
            }

            out.setContext(this);

            List<NodeFormatterFactory> formatterFactories = nodeFormatterFactories;
            this.phasedFormatters = new ArrayList<>(formatterFactories.size());
            ExplicitAttributeIdProvider explicitAttributeIdProvider = null;
            StringBuilder blockLikePrefixChars = new StringBuilder();

            for (int i = formatterFactories.size() - 1; i >= 0; i--) {
                NodeFormatterFactory nodeFormatterFactory = formatterFactories.get(i);
                NodeFormatter nodeFormatter = nodeFormatterFactory.create(this.options);

                // see if implements
                if (nodeFormatter instanceof ExplicitAttributeIdProvider) {
                    explicitAttributeIdProvider = (ExplicitAttributeIdProvider) nodeFormatter;
                }

                char blockLikePrefixChar = nodeFormatter.getBlockQuoteLikePrefixChar();
                if (blockLikePrefixChar != SequenceUtils.NUL) {
                    blockLikePrefixChars.append(blockLikePrefixChar);
                }

                Set<NodeFormattingHandler<?>> formattingHandlers = nodeFormatter.getNodeFormattingHandlers();
                if (formattingHandlers == null) continue;

                for (NodeFormattingHandler<?> formattingHandler : formattingHandlers) {
                    // Overwrite existing renderer
                    List<NodeFormattingHandler<?>> rendererList = renderers.computeIfAbsent(formattingHandler.getNodeType(), key -> new ArrayList<>());
                    rendererList.add(0, formattingHandler);
//                    renderers.put(nodeType.getNodeType(), nodeType);
                }

                // get nodes of interest,
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

            restoreTrackedSpaces = RESTORE_TRACKED_SPACES.get(this.options);
            BasedSequence sequence = TRACKED_SEQUENCE.get(this.options);
            List<TrackedOffset> offsets = TRACKED_OFFSETS.get(this.options);

            trackedSequence = sequence.isEmpty() ? document.getChars() : sequence;
            trackedOffsets = offsets.isEmpty() ? TrackedOffsetList.EMPTY_LIST : TrackedOffsetList.create(trackedSequence, offsets);

            assert trackedSequence.equals(document.getChars()) : String.format("TRACKED_SEQUENCE must be character identical to document.getChars()\n" +
                    "TRACKED_SEQUENCE: '%s'\n" +
                    " altSeq: '%s'\n" +
                    "", trackedSequence.toVisibleWhitespaceString(), document.getChars().toVisibleWhitespaceString());

            String charSequence = blockLikePrefixChars.toString();
            this.blockQuoteLikeChars = BasedSequence.of(charSequence);
            this.blockQuoteLikePredicate = CharPredicate.anyOf(charSequence);

            // generate ids by default even if they are not going to be used
            this.idGenerator = GENERATE_HEADER_ID.get(this.options) ? idGeneratorFactory != null ? idGeneratorFactory.create(this) : new HeaderIdGenerator.Factory().create(this) : null;

            if (idGenerator != null) {
                idGenerator.generateIds(document);
            }

            this.explicitAttributeIdProvider = explicitAttributeIdProvider;

            // collect nodes of interest from document
            if (!collectNodeTypes.isEmpty()) {
                NodeCollectingVisitor collectingVisitor = new NodeCollectingVisitor(collectNodeTypes);
                collectingVisitor.collect(document);
                collectedNodes = collectingVisitor.getSubClassingBag();
            } else {
                collectedNodes = null;
            }
        }

        @NotNull
        @Override
        public String encodeUrl(@NotNull CharSequence url) {
            return String.valueOf(url);
        }

        @NotNull
        @Override
        public ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, Boolean urlEncode) {
            return resolveLink(this, linkType, url, null);
        }

        @NotNull
        @Override
        public ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, Attributes attributes, Boolean urlEncode) {
            return resolveLink(this, linkType, url, attributes);
        }

        ResolvedLink resolveLink(NodeFormatterSubContext context, LinkType linkType, CharSequence url, Attributes attributes) {
            HashMap<String, ResolvedLink> resolvedLinks = resolvedLinkMap.computeIfAbsent(linkType, k -> new HashMap<>());

            String urlSeq = String.valueOf(url);
            ResolvedLink resolvedLink = resolvedLinks.get(urlSeq);
            if (resolvedLink == null) {
                resolvedLink = new ResolvedLink(linkType, urlSeq, attributes);

                if (!urlSeq.isEmpty()) {
                    Node currentNode = context.renderingNode;

                    for (LinkResolver linkResolver : linkResolvers) {
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
        public void addExplicitId(@NotNull Node node, @Nullable String id, @NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown) {
            if (id != null && explicitAttributeIdProvider != null) {
                explicitAttributeIdProvider.addExplicitId(node, id, context, markdown);
            }
        }

        @NotNull
        @Override
        public RenderPurpose getRenderPurpose() {
            return translationHandler == null ? RenderPurpose.FORMAT : translationHandler.getRenderPurpose();
        }

        @Override
        public boolean isTransformingText() {
            return translationHandler != null && translationHandler.isTransformingText();
        }

        @NotNull
        @Override
        public CharSequence transformNonTranslating(CharSequence prefix, @NotNull CharSequence nonTranslatingText, CharSequence suffix, CharSequence suffix2) {
            return translationHandler == null ? nonTranslatingText : translationHandler.transformNonTranslating(prefix, nonTranslatingText, suffix, suffix2);
        }

        @NotNull
        @Override
        public CharSequence transformTranslating(CharSequence prefix, @NotNull CharSequence translatingText, CharSequence suffix, CharSequence suffix2) {
            return translationHandler == null ? translatingText : translationHandler.transformTranslating(prefix, translatingText, suffix, suffix2);
        }

        @NotNull
        @Override
        public CharSequence transformAnchorRef(@NotNull CharSequence pageRef, @NotNull CharSequence anchorRef) {
            return translationHandler == null ? anchorRef : translationHandler.transformAnchorRef(pageRef, anchorRef);
        }

        @Override
        public void postProcessNonTranslating(@NotNull Function<String, CharSequence> postProcessor, @NotNull Runnable scope) {
            if (translationHandler != null) translationHandler.postProcessNonTranslating(postProcessor, scope);
            else scope.run();
        }

        @NotNull
        @Override
        public <T> T postProcessNonTranslating(@NotNull Function<String, CharSequence> postProcessor, @NotNull Supplier<T> scope) {
            if (translationHandler != null) return translationHandler.postProcessNonTranslating(postProcessor, scope);
            else return scope.get();
        }

        @Override
        public boolean isPostProcessingNonTranslating() {
            return translationHandler != null && translationHandler.isPostProcessingNonTranslating();
        }

        @Override
        public MergeContext getMergeContext() {
            return translationHandler == null ? null : translationHandler.getMergeContext();
        }

        @Override
        public HtmlIdGenerator getIdGenerator() {
            return translationHandler == null ? idGenerator : translationHandler.getIdGenerator();
        }

        @Override
        public void translatingSpan(@NotNull TranslatingSpanRender render) {
            if (translationHandler != null) {
                translationHandler.translatingSpan(render);
            } else {
                render.render(this, markdown);
            }
        }

        @Override
        public void nonTranslatingSpan(@NotNull TranslatingSpanRender render) {
            if (translationHandler != null) {
                translationHandler.nonTranslatingSpan(render);
            } else {
                render.render(this, markdown);
            }
        }

        @Override
        public void translatingRefTargetSpan(@Nullable Node target, @NotNull TranslatingSpanRender render) {
            if (translationHandler != null) {
                translationHandler.translatingRefTargetSpan(target, render);
            } else {
                render.render(this, markdown);
            }
        }

        @NotNull
        @Override
        public MutableDataHolder getTranslationStore() {
            if (translationHandler != null) {
                return translationHandler.getTranslationStore();
            } else {
                return document;
            }
        }

        @Override
        public void customPlaceholderFormat(@NotNull TranslationPlaceholderGenerator generator, @NotNull TranslatingSpanRender render) {
            if (translationHandler != null) {
                translationHandler.customPlaceholderFormat(generator, render);
            } else {
                render.render(this, markdown);
            }
        }

        @NotNull
        @Override
        public Node getCurrentNode() {
            return renderingNode;
        }

        @Override
        public @NotNull DataHolder getOptions() {
            return options;
        }

        @NotNull
        @Override
        public FormatterOptions getFormatterOptions() {
            return formatterOptions;
        }

        @NotNull
        @Override
        public Document getDocument() {
            return document;
        }

        @Override
        public @NotNull CharPredicate getBlockQuoteLikePrefixPredicate() {
            return blockQuoteLikePredicate;
        }

        @Override
        public @NotNull BasedSequence getBlockQuoteLikePrefixChars() {
            return blockQuoteLikeChars;
        }

        @Override
        public @NotNull TrackedOffsetList getTrackedOffsets() {
            return trackedOffsets;
        }

        @Override
        public boolean isRestoreTrackedSpaces() {
            return restoreTrackedSpaces;
        }

        @Override
        public @NotNull BasedSequence getTrackedSequence() {
            return trackedSequence;
        }

        @NotNull
        @Override
        public FormattingPhase getFormattingPhase() {
            return phase;
        }

        @Override
        public void render(@NotNull Node node) {
            renderNode(node, this);
        }

        @NotNull
        @Override
        final public Iterable<? extends Node> nodesOfType(@NotNull Class<?>[] classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.itemsOfType(Node.class, classes);
        }

        @NotNull
        @Override
        final public Iterable<? extends Node> nodesOfType(@NotNull Collection<Class<?>> classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.itemsOfType(Node.class, classes);
        }

        @NotNull
        @Override
        final public Iterable<? extends Node> reversedNodesOfType(@NotNull Class<?>[] classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.reversedItemsOfType(Node.class, classes);
        }

        @NotNull
        @Override
        final public Iterable<? extends Node> reversedNodesOfType(@NotNull Collection<Class<?>> classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.reversedItemsOfType(Node.class, classes);
        }

        @Override
        public @NotNull NodeFormatterContext getSubContext() {
            return getSubContextRaw(null, markdown.getBuilder());
        }

        @Override
        public @NotNull NodeFormatterContext getSubContext(@Nullable DataHolder options) {
            return getSubContextRaw(options, markdown.getBuilder());
        }

        @Override
        public @NotNull NodeFormatterContext getSubContext(@Nullable DataHolder options, @NotNull ISequenceBuilder<?, ?> builder) {
            return getSubContextRaw(options, builder);
        }

        NodeFormatterContext getSubContextRaw(@Nullable DataHolder options, @NotNull ISequenceBuilder<?, ?> builder) {
            MarkdownWriter writer = new MarkdownWriter(builder, getMarkdown().getOptions());
            writer.setContext(this);
            //noinspection ReturnOfInnerClass
            return new SubNodeFormatter(this, writer, options);
        }

        void renderNode(Node node, NodeFormatterSubContext subContext) {
            if (node instanceof Document) {
                // here we render multiple phases
                if (translationHandler != null) {
                    translationHandler.beginRendering((Document) node, subContext, subContext.markdown);
                }

                for (FormattingPhase phase : FormattingPhase.values()) {
                    if (phase != DOCUMENT && !renderingPhases.contains(phase)) { continue; }
                    this.phase = phase;
                    // here we render multiple phases
                    if (this.phase == DOCUMENT) {
                        // pre-indent document
                        subContext.markdown.pushPrefix().setPrefix(DOCUMENT_FIRST_PREFIX.get((Document) node), false).setPrefix(DOCUMENT_PREFIX.get((Document) node), true);

                        List<NodeFormattingHandler<?>> nodeRendererList = renderers.get(node.getClass());
                        if (nodeRendererList != null) {
                            subContext.rendererList = nodeRendererList;
                            subContext.rendererIndex = 0;
                            subContext.renderingNode = node;
                            nodeRendererList.get(0).render(node, subContext, subContext.markdown);
                            subContext.renderingNode = null;
                            subContext.rendererList = null;
                            subContext.rendererIndex = -1;
                        }

                        subContext.markdown.popPrefix();
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
                if (isFormatControlEnabled) {
                    if (controlProcessor == null) {
                        controlProcessor = new FormatControlProcessor(document, this.options);
                        controlProcessor.initializeFrom(node);
                    } else {
                        controlProcessor.processFormatControl(node);
                    }
                }

                if (isFormatControlEnabled && controlProcessor.isFormattingOff()) {
                    if (node instanceof BlankLine) subContext.markdown.blankLine();
                    else subContext.markdown.append(node.getChars());
                } else {
                    List<NodeFormattingHandler<?>> nodeRendererList = renderers.get(node.getClass());

                    if (nodeRendererList == null) {
                        nodeRendererList = renderers.get(Node.class);
                    }

                    if (nodeRendererList != null) {
                        List<NodeFormattingHandler<?>> oldRendererList = subContext.rendererList;
                        int oldRendererIndex = subContext.rendererIndex;
                        Node oldRenderingNode = subContext.renderingNode;

                        subContext.rendererList = nodeRendererList;
                        subContext.rendererIndex = 0;
                        subContext.renderingNode = node;
                        nodeRendererList.get(0).render(node, subContext, subContext.markdown);
                        subContext.renderingNode = oldRenderingNode;
                        subContext.rendererList = oldRendererList;
                        subContext.rendererIndex = oldRendererIndex;
                    } else {
                        // default behavior is controlled by generic Node.class that is implemented in CoreNodeFormatter
                        throw new IllegalStateException("Core Node Formatter should implement generic Node renderer");
                    }
                }
            }
        }

        public void renderChildren(@NotNull Node parent) {
            renderChildrenNode(parent, this);
        }

        @Override
        public void delegateRender() {
            delegateRender(this);
        }

        protected void delegateRender(NodeFormatterSubContext subContext) {
            if (subContext.getFormattingPhase() != DOCUMENT) {
                throw new IllegalStateException("Delegate rendering only supported in document rendering phase");
            }

            if (subContext.rendererList == null || subContext.renderingNode == null) {
                throw new IllegalStateException("Delegate rendering can only be called from node render handler");
            }

            Node node = subContext.renderingNode;
            List<NodeFormattingHandler<?>> oldRendererList = subContext.rendererList;
            List<NodeFormattingHandler<?>> rendererList = oldRendererList;
            int oldRendererIndex = subContext.rendererIndex;
            int rendererIndex = oldRendererIndex + 1;

            if (rendererIndex >= rendererList.size()) {
                if (node instanceof Document) {
                    // no default needed, just ignore
                    return;
                } else {
                    // see if there is a default node renderer list
                    List<NodeFormattingHandler<?>> nodeRendererList = renderers.get(Node.class);
                    if (nodeRendererList == null) {
                        throw new IllegalStateException("Core Node Formatter should implement generic Node renderer");
                    } else if (oldRendererList == nodeRendererList) {
                        throw new IllegalStateException("Core Node Formatter should not delegate generic Node renderer");
                    }

                    rendererList = nodeRendererList;
                    rendererIndex = 0;
                }
            }

            subContext.rendererList = rendererList;
            subContext.rendererIndex = rendererIndex;
            rendererList.get(rendererIndex).render(node, subContext, subContext.markdown);
            subContext.rendererIndex = oldRendererIndex;
            subContext.rendererList = oldRendererList;
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
            final private MainNodeFormatter myMainNodeRenderer;
            final private DataHolder myOptions;
            final private FormatterOptions myFormatterOptions;

            public SubNodeFormatter(MainNodeFormatter mainNodeRenderer, MarkdownWriter out, @Nullable DataHolder options) {
                super(out);
                myMainNodeRenderer = mainNodeRenderer;
                myOptions = options == null || options == myMainNodeRenderer.getOptions() ? myMainNodeRenderer.getOptions() : new ScopedDataSet(myMainNodeRenderer.getOptions(), options);
                myFormatterOptions = new FormatterOptions(myOptions);
            }

            @NotNull
            @Override
            public MutableDataHolder getTranslationStore() {
                return myMainNodeRenderer.getTranslationStore();
            }

            @NotNull
            @Override
            final public Iterable<? extends Node> nodesOfType(@NotNull Class<?>[] classes) {
                return myMainNodeRenderer.nodesOfType(classes);
            }

            @NotNull
            @Override
            final public Iterable<? extends Node> nodesOfType(@NotNull Collection<Class<?>> classes) {
                return myMainNodeRenderer.nodesOfType(classes);
            }

            @NotNull
            @Override
            final public Iterable<? extends Node> reversedNodesOfType(@NotNull Class<?>[] classes) {
                return myMainNodeRenderer.reversedNodesOfType(classes);
            }

            @NotNull
            @Override
            final public Iterable<? extends Node> reversedNodesOfType(@NotNull Collection<Class<?>> classes) {
                return myMainNodeRenderer.reversedNodesOfType(classes);
            }

            @Override
            public @NotNull DataHolder getOptions() {return myOptions;}

            @NotNull
            @Override
            public FormatterOptions getFormatterOptions() {return myFormatterOptions;}

            @NotNull
            @Override
            public Document getDocument() {return myMainNodeRenderer.getDocument();}

            @Override
            @NotNull
            public CharPredicate getBlockQuoteLikePrefixPredicate() {return myMainNodeRenderer.getBlockQuoteLikePrefixPredicate();}

            @Override
            @NotNull
            public BasedSequence getBlockQuoteLikePrefixChars() {return myMainNodeRenderer.getBlockQuoteLikePrefixChars();}

            /**
             * Sub-context does not have offset tracking
             *
             * @return empty lise
             */
            @Override
            public @NotNull TrackedOffsetList getTrackedOffsets() {
                return TrackedOffsetList.EMPTY_LIST;
            }

            @Override
            public boolean isRestoreTrackedSpaces() {
                return false;
            }

            @Override
            public @NotNull BasedSequence getTrackedSequence() {
                return myMainNodeRenderer.getTrackedSequence();
            }

            @NotNull
            @Override
            public FormattingPhase getFormattingPhase() {return myMainNodeRenderer.getFormattingPhase();}

            @Override
            public void render(@NotNull Node node) {
                myMainNodeRenderer.renderNode(node, this);
            }

            @NotNull
            @Override
            public Node getCurrentNode() {
                return this.renderingNode;
            }

            @Override
            public void delegateRender() {
                myMainNodeRenderer.delegateRender(this);
            }

            @Override
            public @NotNull NodeFormatterContext getSubContext() {
                return getSubContext(null, markdown.getBuilder());
            }

            @Override
            public @NotNull NodeFormatterContext getSubContext(@Nullable DataHolder options) {
                return getSubContext(options, markdown.getBuilder());
            }

            @Override
            public @NotNull NodeFormatterContext getSubContext(@Nullable DataHolder options, @NotNull ISequenceBuilder<?, ?> builder) {
                MarkdownWriter htmlWriter = new MarkdownWriter(builder, this.markdown.getOptions());
                htmlWriter.setContext(this);
                //noinspection ReturnOfInnerClass
                return new SubNodeFormatter(myMainNodeRenderer, htmlWriter, options == null || options == myOptions ? myOptions : new ScopedDataSet(myOptions, options));
            }

            @Override
            public void renderChildren(@NotNull Node parent) {
                myMainNodeRenderer.renderChildrenNode(parent, this);
            }

            @NotNull
            @Override
            public MarkdownWriter getMarkdown() { return markdown; }

            @NotNull
            @Override
            public RenderPurpose getRenderPurpose() {
                return myMainNodeRenderer.getRenderPurpose();
            }

            @Override
            public boolean isTransformingText() {
                return myMainNodeRenderer.isTransformingText();
            }

            @NotNull
            @Override
            public CharSequence transformNonTranslating(CharSequence prefix, @NotNull CharSequence nonTranslatingText, CharSequence suffix, CharSequence suffix2) {
                return myMainNodeRenderer.transformNonTranslating(prefix, nonTranslatingText, suffix, suffix2);
            }

            @NotNull
            @Override
            public CharSequence transformTranslating(CharSequence prefix, @NotNull CharSequence translatingText, CharSequence suffix, CharSequence suffix2) {
                return myMainNodeRenderer.transformTranslating(prefix, translatingText, suffix, suffix2);
            }

            @NotNull
            @Override
            public CharSequence transformAnchorRef(@NotNull CharSequence pageRef, @NotNull CharSequence anchorRef) {
                return myMainNodeRenderer.transformAnchorRef(pageRef, anchorRef);
            }

            @Override
            public void translatingSpan(@NotNull TranslatingSpanRender render) {
                myMainNodeRenderer.translatingSpan(render);
            }

            @Override
            public void nonTranslatingSpan(@NotNull TranslatingSpanRender render) {
                myMainNodeRenderer.nonTranslatingSpan(render);
            }

            @Override
            public void translatingRefTargetSpan(@Nullable Node target, @NotNull TranslatingSpanRender render) {
                myMainNodeRenderer.translatingRefTargetSpan(target, render);
            }

            @Override
            public void customPlaceholderFormat(@NotNull TranslationPlaceholderGenerator generator, @NotNull TranslatingSpanRender render) {
                myMainNodeRenderer.customPlaceholderFormat(generator, render);
            }

            @NotNull
            @Override
            public String encodeUrl(@NotNull CharSequence url) {
                return myMainNodeRenderer.encodeUrl(url);
            }

            @NotNull
            @Override
            public ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(this, linkType, url, null);
            }

            @NotNull
            @Override
            public ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, Attributes attributes, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(this, linkType, url, attributes);
            }

            @Override
            public void postProcessNonTranslating(@NotNull Function<String, CharSequence> postProcessor, @NotNull Runnable scope) {
                myMainNodeRenderer.postProcessNonTranslating(postProcessor, scope);
            }

            @NotNull
            @Override
            public <T> T postProcessNonTranslating(@NotNull Function<String, CharSequence> postProcessor, @NotNull Supplier<T> scope) {
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
            public void addExplicitId(@NotNull Node node, @Nullable String id, @NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown) {
                myMainNodeRenderer.addExplicitId(node, id, context, markdown);
            }

            @Override
            public HtmlIdGenerator getIdGenerator() {
                return myMainNodeRenderer.getIdGenerator();
            }
        }
    }
}
