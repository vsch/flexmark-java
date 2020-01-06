package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.formatter.internal.CoreNodeFormatter;
import com.vladsch.flexmark.formatter.internal.FormatControlProcessor;
import com.vladsch.flexmark.formatter.internal.MergeContextImpl;
import com.vladsch.flexmark.formatter.internal.MergeLinkResolver;
import com.vladsch.flexmark.formatter.internal.TranslationHandlerImpl;
import com.vladsch.flexmark.html.AttributeProviderFactory;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.HeaderIdGenerator;
import com.vladsch.flexmark.html.renderer.HeaderIdGeneratorFactory;
import com.vladsch.flexmark.html.renderer.HtmlIdGenerator;
import com.vladsch.flexmark.html.renderer.HtmlIdGeneratorFactory;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.BlankLine;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeCollectingVisitor;
import com.vladsch.flexmark.util.builder.BuilderBase;
import com.vladsch.flexmark.util.collection.SubClassingBag;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.data.NullableDataKey;
import com.vladsch.flexmark.util.data.ScopedDataSet;
import com.vladsch.flexmark.util.data.SharedDataKeys;
import com.vladsch.flexmark.util.dependency.DependencyHandler;
import com.vladsch.flexmark.util.dependency.FlatDependencyHandler;
import com.vladsch.flexmark.util.dependency.ResolvedDependencies;
import com.vladsch.flexmark.util.format.CharWidthProvider;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.format.TrackedOffsetList;
import com.vladsch.flexmark.util.format.TrackedOffsetUtils;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public static final Document[] EMPTY_DOCUMENTS = new Document[0];
    /**
     * output control for FormattingAppendable, see {@link LineAppendable#setOptions(int)}
     */
    public static final DataKey<Integer> FORMAT_FLAGS = new DataKey<>("FORMAT_FLAGS", LineAppendable.F_TRIM_LEADING_WHITESPACE);

    // Use LineAppendable values instead
    // NOTE: F_ALLOW_LEADING_WHITESPACE is now inverted and named F_TRIM_LEADING_WHITESPACE
    @Deprecated public static final int FORMAT_CONVERT_TABS = LineAppendable.F_CONVERT_TABS;
    @Deprecated public static final int FORMAT_COLLAPSE_WHITESPACE = LineAppendable.F_COLLAPSE_WHITESPACE;
    @Deprecated public static final int FORMAT_SUPPRESS_TRAILING_WHITESPACE = LineAppendable.F_TRIM_TRAILING_WHITESPACE;
    @Deprecated public static final int FORMAT_ALL_OPTIONS = LineAppendable.F_FORMAT_ALL;

    public static final DataKey<Boolean> GENERATE_HEADER_ID = new DataKey<>("GENERATE_HEADER_ID", false);

    public static final DataKey<Integer> MAX_BLANK_LINES = SharedDataKeys.FORMATTER_MAX_BLANK_LINES;
    public static final DataKey<Integer> MAX_TRAILING_BLANK_LINES = SharedDataKeys.FORMATTER_MAX_TRAILING_BLANK_LINES;
    public static final DataKey<Integer> RIGHT_MARGIN = new DataKey<>("RIGHT_MARGIN", 0);

    public static final DataKey<Boolean> APPLY_SPECIAL_LEAD_IN_HANDLERS = SharedDataKeys.APPLY_SPECIAL_LEAD_IN_HANDLERS;
    public static final DataKey<Boolean> ESCAPE_SPECIAL_CHARS = SharedDataKeys.ESCAPE_SPECIAL_CHARS;
    public static final DataKey<Boolean> ESCAPE_NUMBERED_LEAD_IN = SharedDataKeys.ESCAPE_NUMBERED_LEAD_IN;
    public static final DataKey<Boolean> UNESCAPE_SPECIAL_CHARS = SharedDataKeys.UNESCAPE_SPECIAL_CHARS;

    public static final DataKey<DiscretionaryText> SPACE_AFTER_ATX_MARKER = new DataKey<>("SPACE_AFTER_ATX_MARKER", DiscretionaryText.ADD);
    public static final DataKey<Boolean> SETEXT_HEADING_EQUALIZE_MARKER = new DataKey<>("SETEXT_HEADING_EQUALIZE_MARKER", true);
    public static final DataKey<EqualizeTrailingMarker> ATX_HEADING_TRAILING_MARKER = new DataKey<>("ATX_HEADING_TRAILING_MARKER", EqualizeTrailingMarker.AS_IS);
    public static final DataKey<HeadingStyle> HEADING_STYLE = new DataKey<>("HEADING_STYLE", HeadingStyle.AS_IS);
    public static final NullableDataKey<String> THEMATIC_BREAK = new NullableDataKey<>("THEMATIC_BREAK");
    public static final DataKey<Boolean> BLOCK_QUOTE_BLANK_LINES = SharedDataKeys.BLOCK_QUOTE_BLANK_LINES;
    public static final DataKey<BlockQuoteMarker> BLOCK_QUOTE_MARKERS = new DataKey<>("BLOCK_QUOTE_MARKERS", BlockQuoteMarker.ADD_COMPACT_WITH_SPACE);
    public static final DataKey<Boolean> INDENTED_CODE_MINIMIZE_INDENT = new DataKey<>("INDENTED_CODE_MINIMIZE_INDENT", true);
    public static final DataKey<Boolean> FENCED_CODE_MINIMIZE_INDENT = new DataKey<>("FENCED_CODE_MINIMIZE_INDENT", true);
    public static final DataKey<Boolean> FENCED_CODE_MATCH_CLOSING_MARKER = new DataKey<>("FENCED_CODE_MATCH_CLOSING_MARKER", true);
    public static final DataKey<Boolean> FENCED_CODE_SPACE_BEFORE_INFO = new DataKey<>("FENCED_CODE_SPACE_BEFORE_INFO", false);
    public static final DataKey<Integer> FENCED_CODE_MARKER_LENGTH = new DataKey<>("FENCED_CODE_MARKER_LENGTH", 3);
    public static final DataKey<CodeFenceMarker> FENCED_CODE_MARKER_TYPE = new DataKey<>("FENCED_CODE_MARKER_TYPE", CodeFenceMarker.ANY);
    public static final DataKey<Boolean> LIST_ADD_BLANK_LINE_BEFORE = new DataKey<>("LIST_ADD_BLANK_LINE_BEFORE", false);
    public static final DataKey<Boolean> LIST_RENUMBER_ITEMS = new DataKey<>("LIST_RENUMBER_ITEMS", true);
    public static final DataKey<Boolean> LIST_REMOVE_EMPTY_ITEMS = new DataKey<>("LIST_REMOVE_EMPTY_ITEMS", false);
    public static final DataKey<ElementAlignment> LIST_ALIGN_NUMERIC = new DataKey<>("LIST_ALIGN_NUMERIC", ElementAlignment.NONE);
    public static final DataKey<Boolean> LIST_RESET_FIRST_ITEM_NUMBER = new DataKey<>("LIST_RESET_FIRST_ITEM_NUMBER", false);
    public static final DataKey<ListBulletMarker> LIST_BULLET_MARKER = new DataKey<>("LIST_BULLET_MARKER", ListBulletMarker.ANY);
    public static final DataKey<ListNumberedMarker> LIST_NUMBERED_MARKER = new DataKey<>("LIST_NUMBERED_MARKER", ListNumberedMarker.ANY);
    public static final DataKey<ListSpacing> LIST_SPACING = new DataKey<>("LIST_SPACING", ListSpacing.AS_IS);
    public static final DataKey<Boolean> LISTS_ITEM_CONTENT_AFTER_SUFFIX = new DataKey<>("LISTS_ITEM_CONTENT_AFTER_SUFFIX", false);
    public static final DataKey<ElementPlacement> REFERENCE_PLACEMENT = new DataKey<>("REFERENCE_PLACEMENT", ElementPlacement.AS_IS);
    public static final DataKey<ElementPlacementSort> REFERENCE_SORT = new DataKey<>("REFERENCE_SORT", ElementPlacementSort.AS_IS);
    public static final DataKey<Boolean> KEEP_IMAGE_LINKS_AT_START = new DataKey<>("KEEP_IMAGE_LINKS_AT_START", false);
    public static final DataKey<Boolean> KEEP_EXPLICIT_LINKS_AT_START = new DataKey<>("KEEP_EXPLICIT_LINKS_AT_START", false);
    public static final DataKey<Boolean> OPTIMIZED_INLINE_RENDERING = new DataKey<>("OPTIMIZED_INLINE_RENDERING", false);
    public static final DataKey<CharWidthProvider> FORMAT_CHAR_WIDTH_PROVIDER = TableFormatOptions.FORMAT_CHAR_WIDTH_PROVIDER;
    public static final DataKey<Boolean> KEEP_HARD_LINE_BREAKS = new DataKey<>("KEEP_HARD_LINE_BREAKS", true);
    public static final DataKey<Boolean> KEEP_SOFT_LINE_BREAKS = new DataKey<>("KEEP_SOFT_LINE_BREAKS", true);
    public static final DataKey<String> FORMATTER_ON_TAG = new DataKey<>("FORMATTER_ON_TAG", "@formatter" + ":on");
    public static final DataKey<String> FORMATTER_OFF_TAG = new DataKey<>("FORMATTER_OFF_TAG", "@formatter" + ":off");
    public static final DataKey<Boolean> FORMATTER_TAGS_ENABLED = new DataKey<>("FORMATTER_TAGS_ENABLED", false);
    public static final DataKey<Boolean> FORMATTER_TAGS_ACCEPT_REGEXP = new DataKey<>("FORMATTER_TAGS_ACCEPT_REGEXP", false);
    public static final NullableDataKey<Pattern> LINK_MARKER_COMMENT_PATTERN = new NullableDataKey<>("FORMATTER_TAGS_ACCEPT_REGEXP", (Pattern) null);

    public static final DataKey<Boolean> APPEND_TRANSFERRED_REFERENCES = new DataKey<>("APPEND_TRANSFERRED_REFERENCES", false);

    // used for translation phases of rendering
    public static final DataKey<String> TRANSLATION_ID_FORMAT = new DataKey<>("TRANSLATION_ID_FORMAT", "_%d_");
    public static final DataKey<String> TRANSLATION_HTML_BLOCK_PREFIX = new DataKey<>("TRANSLATION_HTML_BLOCK_PREFIX", "__");
    public static final DataKey<String> TRANSLATION_HTML_INLINE_PREFIX = new DataKey<>("TRANSLATION_HTML_INLINE_PREFIX", "_");
    public static final DataKey<String> TRANSLATION_AUTOLINK_PREFIX = new DataKey<>("TRANSLATION_AUTOLINK_PREFIX", "___");
    public static final DataKey<String> TRANSLATION_EXCLUDE_PATTERN = new DataKey<>("TRANSLATION_EXCLUDE_PATTERN", "^[^\\p{IsAlphabetic}]*$");
    public static final DataKey<String> TRANSLATION_HTML_BLOCK_TAG_PATTERN = Parser.TRANSLATION_HTML_BLOCK_TAG_PATTERN;
    public static final DataKey<String> TRANSLATION_HTML_INLINE_TAG_PATTERN = Parser.TRANSLATION_HTML_INLINE_TAG_PATTERN;

    // list of documents across which to uniquify the reference ids if translating
    public static final DataKey<String> DOC_RELATIVE_URL = new DataKey<>("DOC_RELATIVE_URL", "");
    public static final DataKey<String> DOC_ROOT_URL = new DataKey<>("DOC_ROOT_URL", "");
    public static final DataKey<Boolean> DEFAULT_LINK_RESOLVER = new DataKey<>("DEFAULT_LINK_RESOLVER", false);

    // formatter family override
    public static final DataKey<ParserEmulationProfile> FORMATTER_EMULATION_PROFILE = new DataKey<>("FORMATTER_EMULATION_PROFILE", Parser.PARSER_EMULATION_PROFILE);

    // CAUTION: these keys must be set on the Document node being formatted NOT the formatter
    //  because a formatter instance can be used to format multiple documents while these are document specific.
    // {{
    // these are used by table and paragraph wrapping
    public static final DataKey<List<TrackedOffset>> TRACKED_OFFSETS = new DataKey<>("TRACKED_OFFSETS", Collections.emptyList());

    // original sequence to use for tracked offset resolution since parser takes a contiguous sequence this is the equivalent sequence
    public static final DataKey<BasedSequence> TRACKED_SEQUENCE = new DataKey<>("TRACKED_SEQUENCE", BasedSequence.NULL);

    // used during paragraph wrapping to determine whether spaces are re-inserted if offsets are edit op flagged
    public static final DataKey<Boolean> RESTORE_TRACKED_SPACES = new DataKey<>("RESTORE_END_SPACES", false);
    // }}

    /**
     * use corrected name
     */
    @Deprecated
    public static final DataKey<Boolean> SETEXT_HEADER_EQUALIZE_MARKER = SETEXT_HEADING_EQUALIZE_MARKER;
    /**
     * use corrected name
     */
    @Deprecated
    public static final DataKey<EqualizeTrailingMarker> ATX_HEADER_TRAILING_MARKER = ATX_HEADING_TRAILING_MARKER;

    /**
     * use TableFormatOptions instead
     */
    @Deprecated
    public static final DataKey<TableCaptionHandling> FORMAT_TABLE_CAPTION = TableFormatOptions.FORMAT_TABLE_CAPTION;
    /**
     * use TableFormatOptions instead
     */
    @Deprecated
    public static final DataKey<DiscretionaryText> FORMAT_TABLE_CAPTION_SPACES = TableFormatOptions.FORMAT_TABLE_CAPTION_SPACES;
    /**
     * use TableFormatOptions instead
     */
    @Deprecated
    public static final DataKey<String> FORMAT_TABLE_INDENT_PREFIX = TableFormatOptions.FORMAT_TABLE_INDENT_PREFIX;

    final FormatterOptions formatterOptions;
    final private DataHolder options;
    final List<LinkResolverFactory> linkResolverFactories;
    final NodeFormatterDependencies nodeFormatterFactories;
    final HeaderIdGeneratorFactory idGeneratorFactory;

    Formatter(Builder builder) {
        this.options = builder.toImmutable();
        this.formatterOptions = new FormatterOptions(this.options);
        this.idGeneratorFactory = builder.htmlIdGeneratorFactory == null ? new HeaderIdGenerator.Factory() : builder.htmlIdGeneratorFactory;

        this.linkResolverFactories = FlatDependencyHandler.computeDependencies(builder.linkResolverFactories);
        this.nodeFormatterFactories = calculateNodeFormatterFactories(builder.nodeFormatterFactories);
    }

    private static class NodeFormatterDependencyStage {
        final List<NodeFormatterFactory> dependents;

        public NodeFormatterDependencyStage(List<NodeFormatterFactory> dependents) {
            // compute mappings
            this.dependents = dependents;
        }
    }

    private static class NodeFormatterDependencyHandler extends DependencyHandler<NodeFormatterFactory, NodeFormatterDependencyStage, NodeFormatterDependencies> {
        NodeFormatterDependencyHandler() {}

        @NotNull
        @Override
        protected Class<?> getDependentClass(NodeFormatterFactory dependent) {
            return dependent.getClass();
        }

        @NotNull
        @Override
        protected NodeFormatterDependencies createResolvedDependencies(List<NodeFormatterDependencyStage> stages) {
            return new NodeFormatterDependencies(stages);
        }

        @NotNull
        @Override
        protected NodeFormatterDependencyStage createStage(List<NodeFormatterFactory> dependents) {
            return new NodeFormatterDependencyStage(dependents);
        }
    }

    private static class NodeFormatterDependencies extends ResolvedDependencies<NodeFormatterDependencyStage> {
        final private List<NodeFormatterFactory> nodeFactories;

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

    private static NodeFormatterDependencies calculateNodeFormatterFactories(List<NodeFormatterFactory> formatterFactories) {
        // By having the custom factories come first, extensions are able to change behavior of core syntax.
        List<NodeFormatterFactory> list = new ArrayList<>(formatterFactories);
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
        return new TranslationHandlerImpl(options, formatterOptions, idGeneratorFactory);
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
     *
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
        render(node, output, formatterOptions.maxTrailingBlankLines);
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
        MarkdownWriter markdown = new MarkdownWriter(output, formatterOptions.formatFlags);
        MainNodeFormatter renderer = new MainNodeFormatter(options, markdown, node.getDocument(), null);
        renderer.render(node);
        markdown.appendToSilently(output, formatterOptions.maxBlankLines, maxTrailingBlankLines);

        // resolve any unresolved tracked offsets that are outside elements which resolve their own
        BasedSequence sequence = node.getDocument().getChars();
        if (output instanceof SequenceBuilder && node.getDocument().getChars() != renderer.trackedSequence) {
            // have to use alternate builder sequence for tracked offset resolution
            sequence = ((SequenceBuilder) output).toSequence(renderer.trackedSequence);
        }

        TrackedOffsetUtils.resolveTrackedOffsets(sequence, markdown, renderer.trackedOffsets.getUnresolvedOffsets(), maxTrailingBlankLines);
    }

    /**
     * Render the tree of nodes to markdown
     *
     * @param document the root node
     *
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
        translationRender(document, output, formatterOptions.maxTrailingBlankLines, translationHandler, renderPurpose);
    }

    /**
     * Render the tree of nodes to markdown
     *
     * @param document the root node
     *
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
        MainNodeFormatter renderer = new MainNodeFormatter(options, new MarkdownWriter(formatterOptions.formatFlags & ~LineAppendable.F_TRIM_LEADING_WHITESPACE /*| FormattingAppendable.PASS_THROUGH*/), document.getDocument(), translationHandler);
        renderer.render(document);
        renderer.flushTo(output, formatterOptions.maxBlankLines, maxTrailingBlankLines);
    }

    /**
     * Render a node to the appendable
     *
     * @param documents node to render
     * @param output    appendable to use for the output
     */
    public void mergeRender(Document[] documents, Appendable output) {
        mergeRender(documents, output, formatterOptions.maxTrailingBlankLines);
    }

    public void mergeRender(List<Document> documents, Appendable output) {
        mergeRender(documents.toArray(Formatter.EMPTY_DOCUMENTS), output);
    }

    /**
     * Render the tree of nodes to markdown
     *
     * @param documents the root node
     *
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
            renderer.flushTo(sb, formatterOptions.maxBlankLines, maxTrailingBlankLines);

            translatedDocuments[index] = Parser.builder(mergeOptions).build().parse(sb.toString());
        });

        mergeContext.setDocuments(translatedDocuments);

        mergeContext.forEachPrecedingDocument(null, (context, document, index) -> {
            TranslationHandler translationHandler = (TranslationHandler) context;

            translationHandler.setRenderPurpose(RenderPurpose.TRANSLATED);

            MarkdownWriter markdownWriter = new MarkdownWriter(formatterOptions.formatFlags);
            MainNodeFormatter renderer = new MainNodeFormatter(mergeOptions, markdownWriter, document, translationHandler);
            renderer.render(document);
            markdownWriter.blankLine();
            renderer.flushTo(output, formatterOptions.maxBlankLines, maxTrailingBlankLines);
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
         *
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
         *
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

        MainNodeFormatter(DataHolder options, MarkdownWriter out, Document document, TranslationHandler translationHandler) {
            super(out);
            this.translationHandler = translationHandler;
            this.options = new ScopedDataSet(document, options);
            this.document = document;
            this.renderers = new HashMap<>(32);
            this.renderingPhases = new HashSet<>(FormattingPhase.values().length);
            Set<Class<?>> collectNodeTypes = new HashSet<>(100);

            Boolean defaultLinkResolver = DEFAULT_LINK_RESOLVER.get(options);
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

            List<NodeFormatterFactory> formatterFactories = nodeFormatterFactories.getNodeFactories();
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

            restoreTrackedSpaces = RESTORE_TRACKED_SPACES.get(document);
            BasedSequence sequence = TRACKED_SEQUENCE.get(document);
            List<TrackedOffset> offsets = TRACKED_OFFSETS.get(document);
            trackedSequence = sequence.isEmpty() ? document.getChars() : sequence;
            trackedOffsets = offsets.isEmpty() ? TrackedOffsetList.EMPTY_LIST : TrackedOffsetList.create(trackedSequence, offsets);

            assert trackedSequence.equals(document.getChars()) : "Tracked sequence must be character identical to document.getChars()";

            String charSequence = blockLikePrefixChars.toString();
            this.blockQuoteLikeChars = BasedSequence.of(charSequence);
            this.blockQuoteLikePredicate = CharPredicate.anyOf(charSequence);

            // generate ids by default even if they are not going to be used
            this.idGenerator = GENERATE_HEADER_ID.get(options) ? idGeneratorFactory != null ? idGeneratorFactory.create(this) : new HeaderIdGenerator.Factory().create(this) : null;

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
            return resolveLink(this, linkType, url, null, urlEncode);
        }

        @NotNull
        @Override
        public ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, Attributes attributes, Boolean urlEncode) {
            return resolveLink(this, linkType, url, attributes, urlEncode);
        }

        ResolvedLink resolveLink(NodeFormatterSubContext context, LinkType linkType, CharSequence url, Attributes attributes, Boolean urlEncode) {
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

        @NotNull
        @Override
        public DataHolder getOptions() {
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
        public final Iterable<? extends Node> nodesOfType(@NotNull Class<?>[] classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.itemsOfType(Node.class, classes);
        }

        @NotNull
        @Override
        public final Iterable<? extends Node> nodesOfType(@NotNull Collection<Class<?>> classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.itemsOfType(Node.class, classes);
        }

        @NotNull
        @Override
        public final Iterable<? extends Node> reversedNodesOfType(@NotNull Class<?>[] classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.reversedItemsOfType(Node.class, classes);
        }

        @NotNull
        @Override
        public final Iterable<? extends Node> reversedNodesOfType(@NotNull Collection<Class<?>> classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.reversedItemsOfType(Node.class, classes);
        }

        @Override
        public NodeFormatterContext getSubContext() {
            return getSubContextRaw(null, markdown.getBuilder());
        }

        @Override
        public NodeFormatterContext getSubContext(DataHolder options) {
            return getSubContextRaw(options, markdown.getBuilder());
        }

        @Override
        public NodeFormatterContext getSubContext(DataHolder options, @NotNull ISequenceBuilder<?, ?> builder) {
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
            public final Iterable<? extends Node> nodesOfType(@NotNull Class<?>[] classes) {
                return myMainNodeRenderer.nodesOfType(classes);
            }

            @NotNull
            @Override
            public final Iterable<? extends Node> nodesOfType(@NotNull Collection<Class<?>> classes) {
                return myMainNodeRenderer.nodesOfType(classes);
            }

            @NotNull
            @Override
            public final Iterable<? extends Node> reversedNodesOfType(@NotNull Class<?>[] classes) {
                return myMainNodeRenderer.reversedNodesOfType(classes);
            }

            @NotNull
            @Override
            public final Iterable<? extends Node> reversedNodesOfType(@NotNull Collection<Class<?>> classes) {
                return myMainNodeRenderer.reversedNodesOfType(classes);
            }

            @NotNull
            @Override
            public DataHolder getOptions() {return myOptions;}

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
            public NodeFormatterContext getSubContext() {
                return getSubContext(null, markdown.getBuilder());
            }

            @Override
            public NodeFormatterContext getSubContext(DataHolder options) {
                return getSubContext(options, markdown.getBuilder());
            }

            @Override
            public NodeFormatterContext getSubContext(DataHolder options, @NotNull ISequenceBuilder<?, ?> builder) {
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
                return myMainNodeRenderer.resolveLink(this, linkType, url, null, urlEncode);
            }

            @NotNull
            @Override
            public ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, Attributes attributes, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(this, linkType, url, attributes, urlEncode);
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
