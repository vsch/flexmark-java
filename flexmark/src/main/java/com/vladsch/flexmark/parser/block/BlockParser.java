package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.options.MutableDataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.parser.InlineParser;

/**
 * Parser for a specific block node.
 * <p>
 * Implementations should subclass {@link AbstractBlockParser} instead of implementing this directly.
 */
public interface BlockParser {

    /**
     * Return true if the block that is parsed is a container (contains other blocks), or false if it's a leaf.
     */
    boolean isContainer();

    boolean canContain(Block block);

    Block getBlock();

    BlockContinue tryContinue(ParserState parserState);

    void addLine(ParserState state, BasedSequence line);

    void closeBlock(ParserState parserState);

    /**
     * @return  true if Double blank line should finalize this block parser and its children and reset to parent 
     */
    boolean breakOutOnDoubleBlankLine();
    boolean isParagraphParser();
    BlockContent getBlockContent();

    /**
     * Used to clean up and prepare for the next parsing run of the AbstractBlockParser
     * for internal parser house keeping not for BlockParser implementors
     */
    void finalizeClosedBlock();

    void parseInlines(InlineParser inlineParser);

    MutableDataHolder getDataHolder();
}
