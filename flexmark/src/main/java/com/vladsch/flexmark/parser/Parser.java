package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.CustomBlockParserFactory;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessorFactory;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.parser.internal.DocumentParser;
import com.vladsch.flexmark.parser.internal.InlineParserImpl;
import com.vladsch.flexmark.parser.internal.LinkRefProcessorData;
import com.vladsch.flexmark.parser.internal.PostProcessorManager;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.builder.BuilderBase;
import com.vladsch.flexmark.util.data.*;
import com.vladsch.flexmark.util.misc.Extension;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.ReplacedBasedSequence;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Parses input text to a tree of nodes.
 * <p>
 * Start with the {@link #builder} method, configure the parser and build it. Example:
 * <pre>{@code
 * Parser parser = Parser.builder().build();
 * Node document = parser.parse("input text");
 * }</pre>
 */
public class Parser implements IParse {
    final public static DataKey<Collection<Extension>> EXTENSIONS = SharedDataKeys.EXTENSIONS;

    final public static DataKey<KeepType> REFERENCES_KEEP = new DataKey<>("REFERENCES_KEEP", KeepType.FIRST);
    final public static DataKey<ReferenceRepository> REFERENCES = new DataKey<>("REFERENCES", new ReferenceRepository(null), ReferenceRepository::new);

    final public static DataKey<Boolean> ASTERISK_DELIMITER_PROCESSOR = new DataKey<>("ASTERISK_DELIMITER_PROCESSOR", true);

    final public static DataKey<Boolean> TRACK_DOCUMENT_LINES = new DataKey<>("TRACK_DOCUMENT_LINES", false);

    final public static DataKey<Boolean> BLOCK_QUOTE_PARSER = new DataKey<>("BLOCK_QUOTE_PARSER", true);
    final public static DataKey<Boolean> BLOCK_QUOTE_EXTEND_TO_BLANK_LINE = new DataKey<>("BLOCK_QUOTE_EXTEND_TO_BLANK_LINE", false);
    final public static DataKey<Boolean> BLOCK_QUOTE_IGNORE_BLANK_LINE = new DataKey<>("BLOCK_QUOTE_IGNORE_BLANK_LINE", false);
    final public static DataKey<Boolean> BLOCK_QUOTE_ALLOW_LEADING_SPACE = new DataKey<>("BLOCK_QUOTE_ALLOW_LEADING_SPACE", true);
    final public static DataKey<Boolean> BLOCK_QUOTE_INTERRUPTS_PARAGRAPH = new DataKey<>("BLOCK_QUOTE_INTERRUPTS_PARAGRAPH", true);
    final public static DataKey<Boolean> BLOCK_QUOTE_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("BLOCK_QUOTE_INTERRUPTS_ITEM_PARAGRAPH", true);
    final public static DataKey<Boolean> BLOCK_QUOTE_WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("BLOCK_QUOTE_WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH", true);

    final public static DataKey<Boolean> FENCED_CODE_BLOCK_PARSER = new DataKey<>("FENCED_CODE_BLOCK_PARSER", true);
    final public static DataKey<Boolean> MATCH_CLOSING_FENCE_CHARACTERS = new DataKey<>("MATCH_CLOSING_FENCE_CHARACTERS", true);
    final public static DataKey<Boolean> FENCED_CODE_CONTENT_BLOCK = new DataKey<>("FENCED_CODE_CONTENT_BLOCK", false);

    final public static DataKey<Boolean> CODE_SOFT_LINE_BREAKS = new DataKey<>("CODE_SOFT_LINE_BREAKS", false);
    final public static DataKey<Boolean> HARD_LINE_BREAK_LIMIT = new DataKey<>("HARD_LINE_BREAK_LIMIT", false);

    final public static DataKey<Boolean> HEADING_PARSER = new DataKey<>("HEADING_PARSER", true);
    final public static DataKey<Integer> HEADING_SETEXT_MARKER_LENGTH = new DataKey<>("HEADING_SETEXT_MARKER_LENGTH", 1);
    final public static DataKey<Boolean> HEADING_NO_ATX_SPACE = SharedDataKeys.HEADING_NO_ATX_SPACE;
    // used to set escaping of # at start independent of HEADING_NO_ATX_SPACE setting if desired
    final public static DataKey<Boolean> ESCAPE_HEADING_NO_ATX_SPACE = SharedDataKeys.ESCAPE_HEADING_NO_ATX_SPACE;
    final public static DataKey<Boolean> HEADING_NO_EMPTY_HEADING_WITHOUT_SPACE = new DataKey<>("HEADING_NO_EMPTY_HEADING_WITHOUT_SPACE", false);
    final public static DataKey<Boolean> HEADING_NO_LEAD_SPACE = new DataKey<>("HEADING_NO_LEAD_SPACE", false);
    final public static DataKey<Boolean> HEADING_CAN_INTERRUPT_ITEM_PARAGRAPH = new DataKey<>("HEADING_CAN_INTERRUPT_ITEM_PARAGRAPH", true);

    final public static DataKey<Boolean> HTML_BLOCK_PARSER = new DataKey<>("HTML_BLOCK_PARSER", true);
    final public static DataKey<Boolean> HTML_COMMENT_BLOCKS_INTERRUPT_PARAGRAPH = new DataKey<>("HTML_COMMENT_BLOCKS_INTERRUPT_PARAGRAPH", true);
    final public static DataKey<Boolean> HTML_FOR_TRANSLATOR = SharedDataKeys.HTML_FOR_TRANSLATOR;

    final public static DataKey<Boolean> INLINE_DELIMITER_DIRECTIONAL_PUNCTUATIONS = new DataKey<>("INLINE_DELIMITER_DIRECTIONAL_PUNCTUATIONS", false);

    final public static DataKey<Boolean> INDENTED_CODE_BLOCK_PARSER = new DataKey<>("INDENTED_CODE_BLOCK_PARSER", true);
    final public static DataKey<Boolean> INDENTED_CODE_NO_TRAILING_BLANK_LINES = new DataKey<>("INDENTED_CODE_NO_TRAILING_BLANK_LINES", true);

    final public static DataKey<Boolean> INTELLIJ_DUMMY_IDENTIFIER = SharedDataKeys.INTELLIJ_DUMMY_IDENTIFIER;

    final public static DataKey<Boolean> MATCH_NESTED_LINK_REFS_FIRST = new DataKey<>("MATCH_NESTED_LINK_REFS_FIRST", true);
    final public static DataKey<Boolean> PARSE_INNER_HTML_COMMENTS = SharedDataKeys.PARSE_INNER_HTML_COMMENTS;
    final public static DataKey<Boolean> PARSE_MULTI_LINE_IMAGE_URLS = new DataKey<>("PARSE_MULTI_LINE_IMAGE_URLS", false);
    final public static DataKey<Boolean> PARSE_JEKYLL_MACROS_IN_URLS = new DataKey<>("PARSE_JEKYLL_MACROS_IN_URLS", false);
    final public static DataKey<Boolean> SPACE_IN_LINK_URLS = new DataKey<>("SPACE_IN_LINK_URLS", false);
    final public static DataKey<Boolean> SPACE_IN_LINK_ELEMENTS = new DataKey<>("SPACE_IN_LINK_ELEMENTS", false);
    final public static DataKey<Boolean> WWW_AUTO_LINK_ELEMENT = new DataKey<>("WWW_AUTO_LINK_ELEMENT", false);
    final public static DataKey<Boolean> LINK_TEXT_PRIORITY_OVER_LINK_REF = new DataKey<>("LINK_TEXT_PRIORITY_OVER_LINK_REF", false);   // if true then link text containing link ref is treated as link text with link ref as text, else link is ignored and link ref is used

    final public static DataKey<Boolean> REFERENCE_PARAGRAPH_PRE_PROCESSOR = new DataKey<>("REFERENCE_BLOCK_PRE_PROCESSOR", true);
    final public static DataKey<Boolean> THEMATIC_BREAK_PARSER = new DataKey<>("THEMATIC_BREAK_PARSER", true);
    final public static DataKey<Boolean> THEMATIC_BREAK_RELAXED_START = new DataKey<>("THEMATIC_BREAK_RELAXED_START", true);

    final public static DataKey<Boolean> UNDERSCORE_DELIMITER_PROCESSOR = new DataKey<>("UNDERSCORE_DELIMITER_PROCESSOR", true);
    final public static DataKey<Boolean> BLANK_LINES_IN_AST = SharedDataKeys.BLANK_LINES_IN_AST;
    final public static DataKey<Boolean> USE_HARDCODED_LINK_ADDRESS_PARSER = new DataKey<>("USE_HARDCODED_LINK_ADDRESS_PARSER", true);

    /**
     * STRONG_WRAPS_EMPHASIS default false, when true makes parsing CommonMark Spec 0.27 compliant
     */
    final public static DataKey<Boolean> STRONG_WRAPS_EMPHASIS = new DataKey<>("STRONG_WRAPS_EMPHASIS", false);

    /**
     * LINKS_ALLOW_MATCHED_PARENTHESES default true, when false makes parsing CommonMark Spec 0.27 compliant
     */
    final public static DataKey<Boolean> LINKS_ALLOW_MATCHED_PARENTHESES = new DataKey<>("LINKS_ALLOW_MATCHED_PARENTHESES", true);

    // the meat of differences in emulation
    final public static DataKey<Boolean> LIST_BLOCK_PARSER = new DataKey<>("LIST_BLOCK_PARSER", true);
    final public static DataKey<ParserEmulationProfile> PARSER_EMULATION_PROFILE = new DataKey<>("PARSER_EMULATION_PROFILE", ParserEmulationProfile.COMMONMARK);

    // deep HTML block parsing
    final public static DataKey<Boolean> HTML_BLOCK_DEEP_PARSER = new DataKey<>("HTML_BLOCK_DEEP_PARSER", false);
    final public static DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_NON_BLOCK = new DataKey<>("HTML_BLOCK_DEEP_PARSE_NON_BLOCK", true);
    final public static DataKey<Boolean> HTML_BLOCK_COMMENT_ONLY_FULL_LINE = new DataKey<>("HTML_BLOCK_COMMENT_ONLY_FULL_LINE", false);
    final public static DataKey<Boolean> HTML_BLOCK_START_ONLY_ON_BLOCK_TAGS = new DataKey<>("HTML_BLOCK_START_ONLY_ON_BLOCK_TAGS", HTML_BLOCK_DEEP_PARSER);

    final public static DataKey<List<String>> HTML_BLOCK_TAGS = new DataKey<>("HTML_BLOCK_TAGS", Arrays.asList(
            "address",
            "article",
            "aside",
            "base",
            "basefont",
            "blockquote",
            "body",
            "caption",
            "center",
            "col",
            "colgroup",
            "dd",
            "details",
            "dialog",
            "dir",
            "div",
            "dl",
            "dt",
            "fieldset",
            "figcaption",
            "figure",
            "footer",
            "form",
            "frame",
            "frameset",
            "h1",
            "h2",
            "h3",
            "h4",
            "h5",
            "h6",
            "head",
            "header",
            "hr",
            "html",
            "iframe",
            "legend",
            "li",
            "link",
            "main",
            "math",
            "menu",
            "menuitem",
            "meta",
            "nav",
            "noframes",
            "ol",
            "optgroup",
            "option",
            "p",
            "param",
            "section",
            "source",
            "summary",
            "table",
            "tbody",
            "td",
            "tfoot",
            "th",
            "thead",
            "title",
            "tr",
            "track",
            "ul"
    ));

    /**
     * Blank line interrupts HTML block when not in raw tag, otherwise only when closed
     */
    final public static DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS = new DataKey<>("HTML_BL OCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS", true);

    /**
     * open tags must be contained on one line
     */
    final public static DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE = new DataKey<>("HTML_BL HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE", false);

    /**
     * Other markdown elements can interrupt a closed block without an intervening blank line
     */
    final public static DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED = new DataKey<>("HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED", false);

    /**
     * blank line interrupts partially open tag ie. &lt;TAG without a corresponding &gt;
     */
    final public static DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG = new DataKey<>("HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG", true);

    /**
     * Indented code can interrupt HTML block
     */
    final public static DataKey<Boolean> HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS = new DataKey<>("HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS", false);

    /**
     * Used by formatter for translation parsing
     */
    final public static DataKey<String> TRANSLATION_HTML_BLOCK_TAG_PATTERN = SharedDataKeys.TRANSLATION_HTML_BLOCK_TAG_PATTERN;
    final public static DataKey<String> TRANSLATION_HTML_INLINE_TAG_PATTERN = SharedDataKeys.TRANSLATION_HTML_INLINE_TAG_PATTERN;
    final public static DataKey<String> TRANSLATION_AUTOLINK_TAG_PATTERN = SharedDataKeys.TRANSLATION_AUTOLINK_TAG_PATTERN;

    // LISTS_ITEM_INDENT is also the INDENTED CODE INDENT parser emulation family either does not use it or expects the number of columns to next indent item (in this case indented code is the same)
    // LISTS_CODE_INDENT can be the same as LISTS_ITEM_INDENT or double that where indentation counts from first list item indent
    final public static DataKey<Integer> LISTS_CODE_INDENT = new DataKey<>("LISTS_CODE_INDENT", 4);
    final public static DataKey<Integer> LISTS_ITEM_INDENT = new DataKey<>("LISTS_ITEM_INDENT", 4);

    // new item with content indent >= this value cause an empty item with code indent child, weird standard, so far CommonMark only
    final public static DataKey<Integer> LISTS_NEW_ITEM_CODE_INDENT = new DataKey<>("LISTS_NEW_ITEM_CODE_INDENT", 4);

    // space must follow a list item marker to be recognized as such
    final public static DataKey<Boolean> LISTS_ITEM_MARKER_SPACE = new DataKey<>("LISTS_ITEM_MARKER_SPACE", false);

    // strings for list marker suffixes which offset the content, to properly support gfm task lists with content offset matching the suffix end
    // LIST_ITEM_MARKER_SPACE is applied after the suffix if it is present, and before. Spaces around the suffix are implicitly allowed.
    final public static DataKey<String[]> LISTS_ITEM_MARKER_SUFFIXES = new DataKey<>("LISTS_ITEM_MARKER_SUFFIXES", new String[] { });
    final public static DataKey<Boolean> LISTS_NUMBERED_ITEM_MARKER_SUFFIXED = new DataKey<>("LISTS_NUMBERED_ITEM_MARKER_SUFFIXED", true);

    // List parsing options beyond major parser family
    final public static DataKey<Boolean> LISTS_AUTO_LOOSE = new DataKey<>("LISTS_AUTO_LOOSE", true);
    final public static DataKey<Boolean> LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS = new DataKey<>("LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS", false);
    final public static DataKey<Boolean> LISTS_LOOSE_WHEN_PREV_HAS_TRAILING_BLANK_LINE = new DataKey<>("LISTS_LOOSE_WHEN_PREV_HAS_TRAILING_BLANK_LINE", false);
    final public static DataKey<Boolean> LISTS_LOOSE_WHEN_LAST_ITEM_PREV_HAS_TRAILING_BLANK_LINE = new DataKey<>("LISTS_LOOSE_WHEN_LAST_ITEM_PREV_HAS_TRAILING_BLANK_LINE", false);
    final public static DataKey<Boolean> LISTS_LOOSE_WHEN_HAS_NON_LIST_CHILDREN = new DataKey<>("LISTS_LOOSE_WHEN_HAS_NON_LIST_CHILDREN", false);
    final public static DataKey<Boolean> LISTS_LOOSE_WHEN_BLANK_LINE_FOLLOWS_ITEM_PARAGRAPH = new DataKey<>("LISTS_LOOSE_WHEN_BLANK_LINE_FOLLOWS_ITEM_PARAGRAPH", false);
    final public static DataKey<Boolean> LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM = new DataKey<>("LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM", false);
    final public static DataKey<Boolean> LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE = new DataKey<>("LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE", true);
    final public static DataKey<Boolean> LISTS_LOOSE_WHEN_CONTAINS_BLANK_LINE = new DataKey<>("LISTS_LOOSE_WHEN_CONTAINS_BLANK_LINE", false);
    final public static DataKey<Boolean> LISTS_DELIMITER_MISMATCH_TO_NEW_LIST = new DataKey<>("LISTS_DELIMITER_MISMATCH_TO_NEW_LIST", true);
    final public static DataKey<Boolean> LISTS_END_ON_DOUBLE_BLANK = new DataKey<>("LISTS_END_ON_DOUBLE_BLANK", false);
    final public static DataKey<Boolean> LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST = new DataKey<>("LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST", true);
    final public static DataKey<Boolean> LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST = new DataKey<>("LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST", false);
    final public static DataKey<Boolean> LISTS_ORDERED_ITEM_DOT_ONLY = new DataKey<>("LISTS_ORDERED_ITEM_DOT_ONLY", false);
    final public static DataKey<Boolean> LISTS_ORDERED_LIST_MANUAL_START = new DataKey<>("LISTS_ORDERED_LIST_MANUAL_START", true);
    final public static DataKey<Boolean> LISTS_ITEM_CONTENT_AFTER_SUFFIX = new DataKey<>("LISTS_ITEM_CONTENT_AFTER_SUFFIX", false);

    // List Item paragraph interruption capabilities
    // in general:
    // for empty, empty and non-empty flags must be true for condition to be true
    // for ordered-non-one, ordered-ono-one and ordered non-one must be true for condition to be true
    //
    // so disabling the non-empty flag, also disable the corresponding empty ones
    // so disabling the ordered flag, also disable the corresponding ordered-non-one ones
    //

    final public static DataKey<Boolean> LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH", true);
    final public static DataKey<Boolean> LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH", true);
    final public static DataKey<Boolean> LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH", false);

    final public static DataKey<Boolean> LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_PARAGRAPH", false);
    final public static DataKey<Boolean> LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_PARAGRAPH", false);
    final public static DataKey<Boolean> LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH", false);

    final public static DataKey<Boolean> LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);
    final public static DataKey<Boolean> LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);
    final public static DataKey<Boolean> LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);

    // must also be able to interrupt with corresponding non empty list item type (bullet, ordered, ordered non-one)
    final public static DataKey<Boolean> LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);
    final public static DataKey<Boolean> LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);
    final public static DataKey<Boolean> LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH", true);

    // whether these can start sub-lists when they are not preceded by a blank line and are indented to be interpreted as sub listk
    // must also be able to interrupt item paragraphs of corresponding list item type (bullet, ordered, ordered non-one)
    final public static DataKey<Boolean> LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH", false);
    final public static DataKey<Boolean> LISTS_EMPTY_ORDERED_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH", false);
    final public static DataKey<Boolean> LISTS_EMPTY_ORDERED_NON_ONE_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH = new DataKey<>("LISTS_EMPTY_ORDERED_NON_ONE_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH", false);
    final public static DataKey<String> LISTS_ITEM_PREFIX_CHARS = new DataKey<>("LISTS_ITEM_PREFIX_CHARS", "+*-");

    // these are set by the parser for the loaded extensions
    final public static DataKey<List<SpecialLeadInHandler>> SPECIAL_LEAD_IN_HANDLERS = new DataKey<>("SPECIAL_LEAD_IN_HANDLERS", Collections.emptyList());

    // separate setting for CODE_BLOCK_INDENT
    final public static DataKey<Integer> CODE_BLOCK_INDENT = new DataKey<>("CODE_BLOCK_INDENT", LISTS_ITEM_INDENT);

    final private List<CustomBlockParserFactory> blockParserFactories;
    final private Map<Character, DelimiterProcessor> delimiterProcessors;
    final private BitSet delimiterCharacters;
    final private BitSet specialCharacters;
    final private List<PostProcessorManager.PostProcessorDependencyStage> postProcessorDependencies;
    final private List<List<ParagraphPreProcessorFactory>> paragraphPreProcessorFactories;
    final private List<List<BlockPreProcessorFactory>> blockPreProcessorDependencies;
    final private LinkRefProcessorData linkRefProcessors;
    final private List<InlineParserExtensionFactory> inlineParserExtensionFactories;
    final private InlineParserFactory inlineParserFactory;
    final private @NotNull DataHolder options;

    Parser(Builder builder) {
        DataSet options = builder.toImmutable();
        this.blockParserFactories = DocumentParser.calculateBlockParserFactories(options, builder.blockParserFactories);

        List<SpecialLeadInHandler> specialLeadInHandlers = new ArrayList<>(builder.specialLeadInHandlers);

        for (CustomBlockParserFactory factory : this.blockParserFactories) {
            SpecialLeadInHandler escaper = factory.getLeadInHandler(options);
            if (escaper != null) {
                specialLeadInHandlers.add(escaper);
            }
        }

        MutableDataSet optionsWithSpecialLeadInHandlers = new MutableDataSet(builder);
        optionsWithSpecialLeadInHandlers.set(SPECIAL_LEAD_IN_HANDLERS, specialLeadInHandlers);

        this.options = optionsWithSpecialLeadInHandlers.toImmutable();
        this.inlineParserFactory = builder.inlineParserFactory == null ? DocumentParser.INLINE_PARSER_FACTORY : builder.inlineParserFactory;
        this.paragraphPreProcessorFactories = DocumentParser.calculateParagraphPreProcessors(options, builder.paragraphPreProcessorFactories, this.inlineParserFactory);
        this.blockPreProcessorDependencies = DocumentParser.calculateBlockPreProcessors(options, builder.blockPreProcessorFactories);
        this.delimiterProcessors = InlineParserImpl.calculateDelimiterProcessors(options, builder.delimiterProcessors);
        this.delimiterCharacters = InlineParserImpl.calculateDelimiterCharacters(options, delimiterProcessors.keySet());
        this.linkRefProcessors = InlineParserImpl.calculateLinkRefProcessors(options, builder.linkRefProcessors);
        this.specialCharacters = InlineParserImpl.calculateSpecialCharacters(options, delimiterCharacters);
        this.postProcessorDependencies = PostProcessorManager.calculatePostProcessors(options, builder.postProcessorFactories);
        this.inlineParserExtensionFactories = builder.inlineParserExtensionFactories;
    }

    /**
     * Create a new builder for configuring a {@link Parser}.
     *
     * @return a builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(DataHolder options) {
        return new Builder(options);
    }

    /**
     * Parse the specified input text into a tree of nodes.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the text to parse
     * @return the root node
     */
    public @NotNull Document parse(@NotNull BasedSequence input) {
        // NOTE: parser can only handle contiguous sequences with no out of base characters
        if (input instanceof ReplacedBasedSequence) {
            throw new IllegalArgumentException("" +
                    "Parser.parse() does not support BasedSequences with replaced or non-contiguous segments.\n" +
                    "Use BasedSequence.of(input.toString()) to convert to contiguous based sequence." +
                    "");
        }

        DocumentParser documentParser = new DocumentParser(options
                , blockParserFactories
                , paragraphPreProcessorFactories
                , blockPreProcessorDependencies
                , inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors, linkRefProcessors, inlineParserExtensionFactories));
        Document document = documentParser.parse(input);
        return postProcess(document);
    }

    /**
     * Parse the specified input text into a tree of nodes.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the text to parse
     * @return the root node
     */
    public @NotNull Document parse(@NotNull String input) {
        DocumentParser documentParser = new DocumentParser(options
                , blockParserFactories
                , paragraphPreProcessorFactories
                , blockPreProcessorDependencies
                , inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors, linkRefProcessors, inlineParserExtensionFactories));
        Document document = documentParser.parse(BasedSequence.of(input));
        return postProcess(document);
    }

    /**
     * Parse the specified reader into a tree of nodes. The caller is responsible for closing the reader.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the reader to parse
     * @return the root node
     * @throws IOException when reading throws an exception
     */
    public @NotNull Document parseReader(@NotNull Reader input) throws IOException {
        DocumentParser documentParser = new DocumentParser(options
                , blockParserFactories
                , paragraphPreProcessorFactories
                , blockPreProcessorDependencies
                , inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors, linkRefProcessors, inlineParserExtensionFactories));
        Document document = documentParser.parse(input);
        return postProcess(document);
    }

    private Document postProcess(Document document) {
        document = PostProcessorManager.processDocument(document, postProcessorDependencies);
        return document;
    }

    @Override
    public @NotNull DataHolder getOptions() {
        return options;
    }

    @Override
    public boolean transferReferences(@NotNull Document document, @NotNull Document included, Boolean onlyIfUndefined) {
        // transfer references from included to document
        boolean transferred = false;

        if (options.contains(EXTENSIONS)) {
            for (Extension extension : EXTENSIONS.get(options)) {
                if (extension instanceof ReferenceHoldingExtension) {
                    ReferenceHoldingExtension parserExtension = (ReferenceHoldingExtension) extension;
                    if (parserExtension.transferReferences(document, included)) transferred = true;
                }
            }
        }

        // transfer references
        if (document.contains(REFERENCES) && included.contains(REFERENCES)) {
            if (transferReferences(REFERENCES.get(document), REFERENCES.get(included),
                    onlyIfUndefined != null ? onlyIfUndefined : REFERENCES_KEEP.get(document) == KeepType.FIRST)
            ) {
                transferred = true;
            }
        }

        if (transferred) {
            document.set(HtmlRenderer.RECHECK_UNDEFINED_REFERENCES, true);
        }
        return transferred;
    }

    public static <T extends Node> boolean transferReferences(NodeRepository<T> destination, NodeRepository<T> included, boolean onlyIfUndefined) {
        return NodeRepository.transferReferences(destination, included, onlyIfUndefined, null);
    }

    /**
     * Builder for configuring a {@link Parser}.
     */
    public static class Builder extends BuilderBase<Builder> {
        final List<CustomBlockParserFactory> blockParserFactories = new ArrayList<>();
        final List<DelimiterProcessor> delimiterProcessors = new ArrayList<>();
        final List<PostProcessorFactory> postProcessorFactories = new ArrayList<>();
        final List<ParagraphPreProcessorFactory> paragraphPreProcessorFactories = new ArrayList<>();
        final List<BlockPreProcessorFactory> blockPreProcessorFactories = new ArrayList<>();
        final List<LinkRefProcessorFactory> linkRefProcessors = new ArrayList<>();
        final List<InlineParserExtensionFactory> inlineParserExtensionFactories = new ArrayList<>();
        InlineParserFactory inlineParserFactory = null;
        final List<SpecialLeadInHandler> specialLeadInHandlers = new ArrayList<>();

        public Builder(DataHolder options) {
            super(options);
            loadExtensions();
        }

        public Builder() {
            super();
        }

        /**
         * @return the configured {@link Parser}
         */
        @NotNull
        public Parser build() {
            return new Parser(this);
        }

        @Override
        protected void removeApiPoint(@NotNull Object apiPoint) {
            if (apiPoint instanceof CustomBlockParserFactory) {
                this.blockParserFactories.remove(apiPoint);
            } else if (apiPoint instanceof DelimiterProcessor) {
                this.delimiterProcessors.remove(apiPoint);
            } else if (apiPoint instanceof PostProcessorFactory) {
                this.postProcessorFactories.remove(apiPoint);
            } else if (apiPoint instanceof ParagraphPreProcessorFactory) {
                this.paragraphPreProcessorFactories.remove(apiPoint);
            } else if (apiPoint instanceof BlockPreProcessorFactory) {
                this.blockPreProcessorFactories.remove(apiPoint);
            } else if (apiPoint instanceof LinkRefProcessorFactory) {
                this.linkRefProcessors.remove(apiPoint);
            } else if (apiPoint instanceof SpecialLeadInHandler) {
                this.specialLeadInHandlers.remove(apiPoint);
            } else if (apiPoint instanceof InlineParserExtensionFactory) {
                this.inlineParserExtensionFactories.remove(apiPoint);
            } else if (apiPoint instanceof InlineParserFactory) {
                this.inlineParserFactory = null;
            } else {
                throw new IllegalStateException("Unknown data point type: " + apiPoint.getClass().getName());
            }
        }

        @Override
        protected void preloadExtension(@NotNull Extension extension) {
            if (extension instanceof ParserExtension) {
                ParserExtension parserExtension = (ParserExtension) extension;
                parserExtension.parserOptions(this);
            }
        }

        @Override
        protected boolean loadExtension(@NotNull Extension extension) {
            if (extension instanceof ParserExtension) {
                ParserExtension parserExtension = (ParserExtension) extension;
                parserExtension.extend(this);
                return true;
            }
            return false;
        }

        /**
         * Adds a custom block parser factory.
         * <p>
         * Note that custom factories are applied <em>before</em> the built-in factories. This is so that
         * extensions can change how some syntax is parsed that would otherwise be handled by built-in factories.
         * "With great power comes great responsibility."
         *
         * @param blockParserFactory a block parser factory implementation
         * @return {@code this}
         */
        public Builder customBlockParserFactory(CustomBlockParserFactory blockParserFactory) {
            blockParserFactories.add(blockParserFactory);
            addExtensionApiPoint(blockParserFactory);
            return this;
        }

        public Builder customInlineParserExtensionFactory(InlineParserExtensionFactory inlineParserExtensionFactory) {
            inlineParserExtensionFactories.add(inlineParserExtensionFactory);
            addExtensionApiPoint(inlineParserExtensionFactory);
            return this;
        }

        public Builder customInlineParserFactory(InlineParserFactory blockParserFactory) {
            if (inlineParserFactory != null) {
                throw new IllegalStateException("custom inline parser factory is already set to " + inlineParserFactory.getClass().getName());
            }
            inlineParserFactory = blockParserFactory;
            addExtensionApiPoint(blockParserFactory);
            return this;
        }

        public Builder customDelimiterProcessor(DelimiterProcessor delimiterProcessor) {
            delimiterProcessors.add(delimiterProcessor);
            addExtensionApiPoint(delimiterProcessor);
            return this;
        }

        public Builder postProcessorFactory(PostProcessorFactory postProcessorFactory) {
            postProcessorFactories.add(postProcessorFactory);
            addExtensionApiPoint(postProcessorFactory);
            return this;
        }

        public Builder paragraphPreProcessorFactory(ParagraphPreProcessorFactory paragraphPreProcessorFactory) {
            paragraphPreProcessorFactories.add(paragraphPreProcessorFactory);
            addExtensionApiPoint(paragraphPreProcessorFactory);
            return this;
        }

        public Builder blockPreProcessorFactory(BlockPreProcessorFactory blockPreProcessorFactory) {
            blockPreProcessorFactories.add(blockPreProcessorFactory);
            addExtensionApiPoint(blockPreProcessorFactory);
            return this;
        }

        public Builder linkRefProcessorFactory(LinkRefProcessorFactory linkRefProcessor) {
            linkRefProcessors.add(linkRefProcessor);
            addExtensionApiPoint(linkRefProcessor);
            return this;
        }

        public Builder specialLeadInHandler(SpecialLeadInHandler specialLeadInHandler) {
            specialLeadInHandlers.add(specialLeadInHandler);
            addExtensionApiPoint(specialLeadInHandler);
            return this;
        }
    }

    /**
     * Extension for {@link Parser}.
     * <p>
     * Implementations of this interface should done by all Extensions that extend the core parser
     * <p>
     * Each will be called via {@link ParserExtension#extend(Builder)} method giving it a chance to call back
     * on the builder methods to register parser extension points
     */
    public interface ParserExtension extends Extension {
        /**
         * This method is called first on all extensions so that they can adjust the options that must be common to all extensions.
         *
         * @param options option set that will be used for the builder
         */
        void parserOptions(MutableDataHolder options);

        /**
         * This method is called on all extensions so that they can register their custom processors
         *
         * @param parserBuilder parser builder with which to register extensions
         * @see Builder#customBlockParserFactory(CustomBlockParserFactory)
         * @see Builder#customInlineParserExtensionFactory(InlineParserExtensionFactory)
         * @see Builder#customInlineParserFactory(InlineParserFactory)
         * @see Builder#customDelimiterProcessor(DelimiterProcessor)
         * @see Builder#postProcessorFactory(PostProcessorFactory)
         * @see Builder#paragraphPreProcessorFactory(ParagraphPreProcessorFactory)
         * @see Builder#blockPreProcessorFactory(BlockPreProcessorFactory)
         * @see Builder#linkRefProcessorFactory(LinkRefProcessorFactory)
         * @see Builder#specialLeadInHandler(SpecialLeadInHandler)
         */
        void extend(Builder parserBuilder);
    }

    /**
     * Should be implemented by all extensions that create a node repository or other references in the
     * document. It is used by the parser to transfer references from included document to the document
     * that is doing the inclusion so that during rendering references in the included document will
     * appear as local references to the document being rendered.
     * <p>
     * Extension for {@link Parser}.
     */
    public interface ReferenceHoldingExtension extends Extension {
        /**
         * This method is called to transfer references from included document to the source document
         *
         * @param document destination document for references
         * @param included source document for references
         * @return true if there were references to transfer
         */
        boolean transferReferences(MutableDataHolder document, DataHolder included);
    }

    /**
     * Add extension(s) to the extension list
     *
     * @param options    mutable options holding existing extensions
     * @param extensions extension to add
     * @return mutable options
     */
    public static MutableDataHolder addExtensions(MutableDataHolder options, Extension... extensions) {
        Iterable<Extension> extensionIterable = Parser.EXTENSIONS.get(options);
        ArrayList<Extension> extensionList = new ArrayList<>(Arrays.asList(extensions));

        for (Extension extension : extensionIterable) {
            extensionList.add(extension);
        }

        options.set(Parser.EXTENSIONS, extensionList);
        return options;
    }

    /**
     * Remove extension(s) of given class from the extension list
     *
     * @param options    mutable options holding existing extensions
     * @param extensions extension classes to remove
     * @return mutable options
     */
    public static MutableDataHolder removeExtensions(MutableDataHolder options, Class... extensions) {
        Iterable<Extension> extensionIterable = Parser.EXTENSIONS.get(options);
        HashSet<Extension> extensionList = new HashSet<>();

        for (Extension extension : extensionIterable) {
            boolean keep = true;
            for (Class clazz : extensions) {
                if (clazz.isInstance(extension)) {
                    keep = false;
                    break;
                }
            }
            if (keep) {
                extensionList.add(extension);
            }
        }

        options.set(Parser.EXTENSIONS, extensionList);
        return options;
    }
}
