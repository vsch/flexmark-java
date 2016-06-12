package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.internal.DocumentParser;
import com.vladsch.flexmark.internal.InlineParserImpl;
import com.vladsch.flexmark.internal.util.*;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.block.BlockParserFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

/**
 * Parses input text to a tree of nodes.
 * <p>
 * Start with the {@link #builder} method, configure the parser and build it. Example:
 * <pre><code>
 * Parser parser = Parser.builder().build();
 * Node document = parser.parse("input text");
 * </code></pre>
 */
public class Parser {
    final static public DataKey<Boolean> BLOCK_QUOTE_PARSER = new DataKey<>("BLOCK_QUOTE_PARSER", true);
    final static public DataKey<Boolean> HEADING_PARSER = new DataKey<>("HEADING_PARSER", true);
    final static public DataKey<Boolean> FENCED_CODE_BLOCK_PARSER = new DataKey<>("FENCED_CODE_BLOCK_PARSER", true);
    final static public DataKey<Boolean> HTML_BLOCK_PARSER = new DataKey<>("HTML_BLOCK_PARSER", true);
    final static public DataKey<Boolean> THEMATIC_BREAK_PARSER = new DataKey<>("THEMATIC_BREAK_PARSER", true);
    final static public DataKey<Boolean> LIST_BLOCK_PARSER = new DataKey<>("LIST_BLOCK_PARSER", true);
    final static public DataKey<Boolean> INDENTED_CODE_BLOCK_PARSER = new DataKey<>("INDENTED_CODE_BLOCK_PARSER", true);
    final static public DataKey<Boolean> REFERENCE_BLOCK_PRE_PROCESSOR = new DataKey<>("REFERENCE_BLOCK_PRE_PROCESSOR", true);
    final static public DataKey<Boolean> ASTERISK_DELIMITER_PROCESSOR = new DataKey<>("ASTERISK_DELIMITER_PROCESSOR", true);
    final static public DataKey<Boolean> UNDERSCORE_DELIMITER_PROCESSOR = new DataKey<>("UNDERSCORE_DELIMITER_PROCESSOR", true);
    public final static DataKey<ReferenceRepository> REFERENCES = new DataKey<>("REFERENCES", ReferenceRepository::new);
    public final static DataKey<KeepType> REFERENCES_KEEP = new DataKey<>("REFERENCES_KEEP", KeepType.FIRST);

    private final List<BlockParserFactory> blockParserFactories;
    private final Map<Character, DelimiterProcessor> delimiterProcessors;
    private final BitSet delimiterCharacters;
    private final BitSet specialCharacters;
    private final List<PostProcessor> postProcessors;
    private final List<BlockPreProcessor> blockPreProcessors;
    private final InlineParserFactory inlineParserFactory;
    private final DataHolder options;

    private Parser(Builder builder) {
        this.options = new DataSet(builder);
        this.blockParserFactories = DocumentParser.calculateBlockParserFactories(this.options, builder.blockParserFactories);
        this.blockPreProcessors = DocumentParser.calculateParagraphProcessors(this.options, builder.blockPreProcessors);
        this.delimiterProcessors = InlineParserImpl.calculateDelimiterProcessors(this.options, builder.delimiterProcessors);
        this.delimiterCharacters = InlineParserImpl.calculateDelimiterCharacters(this.options, delimiterProcessors.keySet());
        this.specialCharacters = InlineParserImpl.calculateSpecialCharacters(this.options, delimiterCharacters);
        this.postProcessors = builder.postProcessors;
        this.inlineParserFactory = builder.inlineParserFactory == null ? DocumentParser.inlineParserFactory() : builder.inlineParserFactory;
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
     * @param input        the text to parse
     * @return the root node
     */
    public Node parse(BasedSequence input) {
        DocumentParser documentParser = new DocumentParser(options, blockParserFactories, blockPreProcessors, inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors));
        Node document = documentParser.parse(input);
        return postProcess(document);
    }

    /**
     * Parse the specified input text into a tree of nodes.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input        the text to parse
     * @return the root node
     */
    public Node parse(String input) {
        DocumentParser documentParser = new DocumentParser(options, blockParserFactories, blockPreProcessors, inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors));
        Node document = documentParser.parse(new StringSequence(input));
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
        DocumentParser documentParser = new DocumentParser(options, blockParserFactories, blockPreProcessors, inlineParserFactory.inlineParser(options, specialCharacters, delimiterCharacters, delimiterProcessors));
        Node document = documentParser.parse(input);
        return postProcess(document);
    }

    private Node postProcess(Node document) {
        for (PostProcessor postProcessor : postProcessors) {
            document = postProcessor.process(document);
        }
        return document;
    }

    /**
     * Builder for configuring a {@link Parser}.
     */
    public static class Builder extends MutableDataSet {
        private final List<BlockParserFactory> blockParserFactories = new ArrayList<>();
        private final List<DelimiterProcessor> delimiterProcessors = new ArrayList<>();
        private final List<PostProcessor> postProcessors = new ArrayList<>();
        private final List<BlockPreProcessor> blockPreProcessors = new ArrayList<>();
        private InlineParserFactory inlineParserFactory = null;

        public Builder(DataHolder options) {
            super(options);
        }

        public Builder() {
            super();
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
                    ParserExtension parserExtension = (ParserExtension) extension;
                    parserExtension.extend(this);
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
        public Builder customBlockParserFactory(BlockParserFactory blockParserFactory) {
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

        public Builder postProcessor(PostProcessor postProcessor) {
            postProcessors.add(postProcessor);
            return this;
        }

        public Builder blockPreProcessor(BlockPreProcessor blockPreProcessor) {
            blockPreProcessors.add(blockPreProcessor);
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
