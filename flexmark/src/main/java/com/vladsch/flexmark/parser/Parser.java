package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.internal.DocumentParser;
import com.vladsch.flexmark.internal.InlineParserImpl;
import com.vladsch.flexmark.internal.LinkRefProcessorData;
import com.vladsch.flexmark.internal.PostProcessorManager;
import com.vladsch.flexmark.internal.util.KeepType;
import com.vladsch.flexmark.internal.util.ReferenceRepository;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.options.DataKey;
import com.vladsch.flexmark.internal.util.options.DataSet;
import com.vladsch.flexmark.internal.util.options.MutableDataSet;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.StringSequence;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.block.BlockPreProcessorFactory;
import com.vladsch.flexmark.parser.block.CustomBlockParserFactory;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessorFactory;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Parses input text to a tree of nodes.
 * <p>
 * Start with the {@link #builder} method, configure the parser and build it. Example:
 * <pre><code>
 * Parser parser = Parser.builder().build();
 * Node document = parser.parse("input text");
 * </code></pre>
 */
public class Parser implements IParse {
    final public static DataKey<Boolean> ASTERISK_DELIMITER_PROCESSOR = new DataKey<>("ASTERISK_DELIMITER_PROCESSOR", true);
    final public static DataKey<Boolean> BLOCK_QUOTE_PARSER = new DataKey<>("BLOCK_QUOTE_PARSER", true);
    final public static DataKey<Boolean> FENCED_CODE_BLOCK_PARSER = new DataKey<>("FENCED_CODE_BLOCK_PARSER", true);
    final public static DataKey<Boolean> HEADERS_NO_ATX_SPACE = new DataKey<>("HEADERS_NO_ATX_SPACE", false);
    final public static DataKey<Boolean> HEADERS_NO_LEAD_SPACE = new DataKey<>("HEADERS_NO_LEAD_SPACE", false);
    final public static DataKey<Boolean> HEADING_PARSER = new DataKey<>("HEADING_PARSER", true);
    final public static DataKey<Boolean> HTML_BLOCK_PARSER = new DataKey<>("HTML_BLOCK_PARSER", true);
    final public static DataKey<Boolean> INDENTED_CODE_BLOCK_PARSER = new DataKey<>("INDENTED_CODE_BLOCK_PARSER", true);
    final public static DataKey<Boolean> INDENTED_CODE_NO_TRAILING_BLANK_LINES = new DataKey<>("INDENTED_CODE_NO_TRAILING_BLANK_LINES", false);
    final public static DataKey<Boolean> INTELLIJ_DUMMY_IDENTIFIER = new DataKey<>("INTELLIJ_DUMMY_IDENTIFIER", false);
    final public static DataKey<Boolean> LIST_BLOCK_PARSER = new DataKey<>("LIST_BLOCK_PARSER", true);
    final public static DataKey<Boolean> LIST_INTERRUPTS_PARAGRAPH = new DataKey<>("LIST_INTERRUPTS_PARAGRAPH", true);
    final public static DataKey<Boolean> LISTS_AUTO_LOOSE = new DataKey<>("LISTS_AUTO_LOOSE", true);
    final public static DataKey<Boolean> LISTS_BULLET_MATCH = new DataKey<>("LISTS_BULLET_MATCH", true);
    final public static DataKey<Boolean> LISTS_END_ON_DOUBLE_BLANK = new DataKey<>("LISTS_END_ON_DOUBLE_BLANK", false);
    final public static DataKey<Boolean> MATCH_CLOSING_FENCE_CHARACTERS = new DataKey<>("MATCH_CLOSING_FENCE_CHARACTERS", true);
    final public static DataKey<Boolean> MATCH_NESTED_LINK_REFS_FIRST = new DataKey<>("MATCH_NESTED_LINK_REFS_FIRST", true);
    final public static DataKey<Boolean> ORDERED_LIST_DOT_ONLY = new DataKey<>("ORDERED_LIST_DOT_ONLY", false);
    final public static DataKey<Boolean> ORDERED_LIST_INTERRUPTS_PARAGRAPH = new DataKey<>("ORDERED_LIST_INTERRUPTS_PARAGRAPH", true);
    final public static DataKey<Boolean> ORDERED_LIST_START = new DataKey<>("ORDERED_LIST_START", true);
    final public static DataKey<Boolean> ORDERED_SUBITEM_INTERRUPTS_PARENT_ITEM = new DataKey<>("ORDERED_SUBITEM_INTERRUPTS_PARENT_ITEM", false);
    final public static DataKey<Boolean> PARSE_INNER_HTML_COMMENTS = new DataKey<>("PARSE_INNER_HTML_COMMENTS", false);
    final public static DataKey<Boolean> PARSE_MULTI_LINE_IMAGE_URLS = new DataKey<>("PARSE_MULTI_LINE_IMAGE_URLS", false);
    final public static DataKey<Boolean> REFERENCE_PARAGRAPH_PRE_PROCESSOR = new DataKey<>("REFERENCE_BLOCK_PRE_PROCESSOR", true);
    final public static DataKey<Boolean> THEMATIC_BREAK_PARSER = new DataKey<>("THEMATIC_BREAK_PARSER", true);
    final public static DataKey<Boolean> THEMATIC_BREAK_RELAXED_START = new DataKey<>("THEMATIC_BREAK_RELAXED_START", true);
    final public static DataKey<Boolean> UNDERSCORE_DELIMITER_PROCESSOR = new DataKey<>("UNDERSCORE_DELIMITER_PROCESSOR", true);
    final public static DataKey<Integer> LISTS_FIXED_INDENT = new DataKey<>("LISTS_FIXED_INDENT", 0);
    final public static DataKey<Iterable<? extends Extension>> EXTENSIONS = new DataKey<>("EXTENSIONS", Extension.EMPTY_LIST);
    final public static DataKey<KeepType> REFERENCES_KEEP = new DataKey<>("REFERENCES_KEEP", KeepType.FIRST);
    final public static DataKey<ReferenceRepository> REFERENCES = new DataKey<>("REFERENCES", ReferenceRepository::new);

    private final List<CustomBlockParserFactory> blockParserFactories;
    private final Map<Character, DelimiterProcessor> delimiterProcessors;
    private final BitSet delimiterCharacters;
    private final BitSet specialCharacters;
    private final Builder builder;
    private final PostProcessorManager.PostProcessorDependencies postProcessorDependencies;
    private final DocumentParser.ParagraphPreProcessorDependencies paragraphPreProcessorFactories;
    private final DocumentParser.BlockPreProcessorDependencies blockPreProcessorDependencies;
    private final LinkRefProcessorData linkRefProcessors;
    private final InlineParserFactory inlineParserFactory;
    private final DataHolder options;

    private Parser(Builder builder) {
        this.builder = new Builder(builder); // make a copy to avoid after creation side effects
        this.options = new DataSet(builder);
        this.blockParserFactories = DocumentParser.calculateBlockParserFactories(this.options, builder.blockParserFactories);
        this.inlineParserFactory = builder.inlineParserFactory == null ? DocumentParser.INLINE_PARSER_FACTORY : builder.inlineParserFactory;
        this.paragraphPreProcessorFactories = DocumentParser.calculateParagraphPreProcessors(this.options, builder.paragraphPreProcessorFactories, this.inlineParserFactory);
        this.blockPreProcessorDependencies = DocumentParser.calculateBlockPreProcessors(this.options, builder.blockPreProcessorFactories, this.inlineParserFactory);
        this.delimiterProcessors = InlineParserImpl.calculateDelimiterProcessors(this.options, builder.delimiterProcessors);
        this.delimiterCharacters = InlineParserImpl.calculateDelimiterCharacters(this.options, delimiterProcessors.keySet());
        this.linkRefProcessors = InlineParserImpl.calculateLinkRefProcessors(this.options, builder.linkRefProcessors);
        this.specialCharacters = InlineParserImpl.calculateSpecialCharacters(this.options, delimiterCharacters);
        this.postProcessorDependencies = PostProcessorManager.calculatePostProcessors(this.options, builder.postProcessorFactories);
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
    public Node parse(BasedSequence input) {
        DocumentParser documentParser = new DocumentParser(options, blockParserFactories, paragraphPreProcessorFactories,
                blockPreProcessorDependencies, inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors, linkRefProcessors));
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
    public Node parse(String input) {
        DocumentParser documentParser = new DocumentParser(options, blockParserFactories, paragraphPreProcessorFactories,
                blockPreProcessorDependencies, inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors, linkRefProcessors));
        Document document = documentParser.parse(new StringSequence(input));
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
    public Node parseReader(Reader input) throws IOException {
        DocumentParser documentParser = new DocumentParser(options, blockParserFactories, paragraphPreProcessorFactories,
                blockPreProcessorDependencies, inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors, linkRefProcessors));
        Document document = documentParser.parse(input);
        return postProcess(document);
    }

    private Node postProcess(Document document) {
        document = PostProcessorManager.processDocument(document, postProcessorDependencies);
        return document;
    }

    public Parser withOptions(DataHolder options) {
        return options == null ? this : (options.contains(EXTENSIONS) ? new Parser(new Builder(options)) : new Parser(new Builder(builder, options)));
    }

    /**
     * Builder for configuring a {@link Parser}.
     */
    public static class Builder extends MutableDataSet {
        private final List<CustomBlockParserFactory> blockParserFactories = new ArrayList<>();
        private final List<DelimiterProcessor> delimiterProcessors = new ArrayList<>();
        private final List<PostProcessorFactory> postProcessorFactories = new ArrayList<>();
        private final List<ParagraphPreProcessorFactory> paragraphPreProcessorFactories = new ArrayList<>();
        private final List<BlockPreProcessorFactory> blockPreProcessorFactories = new ArrayList<>();
        private final List<LinkRefProcessorFactory> linkRefProcessors = new ArrayList<>();
        private InlineParserFactory inlineParserFactory = null;
        private final HashSet<ParserExtension> loadedExtensions = new HashSet<>();

        public Builder(DataHolder options) {
            super(options);

            if (contains(Parser.EXTENSIONS)) {
                extensions(get(Parser.EXTENSIONS));
            }
        }

        public Builder() {
            super();
        }

        public Builder(Builder other) {
            super(other);
            blockParserFactories.addAll(other.blockParserFactories);
            delimiterProcessors.addAll(other.delimiterProcessors);
            postProcessorFactories.addAll(other.postProcessorFactories);
            paragraphPreProcessorFactories.addAll(other.paragraphPreProcessorFactories);
            blockPreProcessorFactories.addAll(other.blockPreProcessorFactories);
            linkRefProcessors.addAll(other.linkRefProcessors);
            inlineParserFactory = other.inlineParserFactory;
            loadedExtensions.addAll(other.loadedExtensions);
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
         * @return the configured {@link Parser}
         */
        public Parser build() {
            return new Parser(this);
        }

        /**
         * @param extensions extensions to use on this parser
         * @return {@code this}
         */
        public Builder extensions(Iterable<? extends Extension> extensions) {
            for (Extension extension : extensions) {
                if (extension instanceof ParserExtension) {
                    if (!loadedExtensions.contains(extension)) {
                        ParserExtension parserExtension = (ParserExtension) extension;
                        parserExtension.extend(this);
                        loadedExtensions.add(parserExtension);
                    }
                }
            }
            return this;
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
            return this;
        }

        public Builder customInlineParserFactory(InlineParserFactory blockParserFactory) {
            if (inlineParserFactory != null) {
                throw new IllegalStateException("custom inline parser factory is already set to " + inlineParserFactory.getClass().getName());
            }
            inlineParserFactory = blockParserFactory;
            return this;
        }

        public Builder customDelimiterProcessor(DelimiterProcessor delimiterProcessor) {
            delimiterProcessors.add(delimiterProcessor);
            return this;
        }

        public Builder postProcessorFactory(PostProcessorFactory postProcessorFactory) {
            postProcessorFactories.add(postProcessorFactory);
            return this;
        }

        public Builder paragraphPreProcessorFactory(ParagraphPreProcessorFactory paragraphPreProcessorFactory) {
            paragraphPreProcessorFactories.add(paragraphPreProcessorFactory);
            return this;
        }

        public Builder blockPreProcessorFactory(BlockPreProcessorFactory blockPreProcessorFactory) {
            blockPreProcessorFactories.add(blockPreProcessorFactory);
            return this;
        }

        public Builder linkRefProcessorFactory(LinkRefProcessorFactory linkRefProcessor) {
            linkRefProcessors.add(linkRefProcessor);
            return this;
        }
    }

    /**
     * Extension for {@link Parser}.
     */
    public interface ParserExtension extends Extension {
        void extend(Builder parserBuilder);
    }
}
