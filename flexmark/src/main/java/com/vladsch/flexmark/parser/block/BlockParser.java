package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.BlockContent;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Parser for a specific block node.
 * <p>
 * Implementations should subclass {@link AbstractBlockParser} instead of implementing this directly.
 */
public interface BlockParser {

    /**
     * @return true if the block that is parsed is a container (contains other blocks), or false if it's a leaf.
     */
    boolean isContainer();

    /**
     * @param block new block being started
     * @return true if this block parser's block can contain the given block type, false if it cannot
     */
    boolean canContain(Block block);

    /**
     * @return the block parser's block node instance
     */
    Block getBlock();

    /**
     * See if the block parser can continue parsing the current block
     *
     * @param state current parsing state
     * @return block continue instance
     */
    BlockContinue tryContinue(ParserState state);

    /**
     * Add another line to the block
     *
     * @param state parser state
     * @param line  line sequence
     */
    void addLine(ParserState state, BasedSequence line);

    void closeBlock(ParserState state);

    /**
     * @return true if the block is already closed.
     */
    boolean isClosed();

    /**
     * @param lastMatchedBlockParser last matched block parser instance
     * @return true if the last blank line status should be propagated to parent blocks
     */
    boolean isPropagatingLastBlankLine(BlockParser lastMatchedBlockParser);

    /**
     * @return true if Double blank line should finalize this block parser and its children and reset to parent
     */
    boolean breakOutOnDoubleBlankLine();

    /**
     * @return true if this block parser is the paragraph block parser
     */
    boolean isParagraphParser();

    /**
     * @return get the currently accumulated block content. May or may not be implemented by any parser except for the {@link com.vladsch.flexmark.internal.ParagraphParser} or one that returns true for {@link #isParagraphParser()}
     */
    BlockContent getBlockContent();

    /**
     * Used to clean up and prepare for the next parsing run of the AbstractBlockParser
     * for internal parser house keeping not for BlockParser implementors
     */
    void finalizeClosedBlock();

    /**
     * Do inline processing for the block content using the given inline parser interface
     * 
     * @param inlineParser instance of inline parser
     */
    void parseInlines(InlineParser inlineParser);

    /**
     * @return the data holder for a block parser instance. Implemented by {@link AbstractBlockParser}
     */
    MutableDataHolder getDataHolder();
}
