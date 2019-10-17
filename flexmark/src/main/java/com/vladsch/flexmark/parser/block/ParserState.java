package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockTracker;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

/**
 * State of the parser that is used in block parsers.
 * <p><em>This interface is not intended to be implemented by clients.</em></p>
 */
public interface ParserState extends BlockTracker, BlockParserTracker {

    /**
     * @return the current line
     */
    BasedSequence getLine();

    /**
     * @return the current line with EOL
     */
    BasedSequence getLineWithEOL();

    /**
     * @return the current index within the line (0-based)
     */
    int getIndex();

    /**
     * @return the index of the next non-space character starting from {@link #getIndex()} (may be the same) (0-based)
     */
    int getNextNonSpaceIndex();

    /**
     * The column is the position within the line after tab characters have been processed as 4-space tab stops.
     * If the line doesn't contain any tabs, it's the same as the {@link #getIndex()}. If the line starts with a tab,
     * followed by text, then the column for the first character of the text is 4 (the index is 1).
     *
     * @return the current column within the line (0-based)
     */
    int getColumn();

    /**
     * @return the indentation in columns (either by spaces or tab stop of 4), starting from {@link #getColumn()}
     */
    int getIndent();

    /**
     * @return true if the current line is blank starting from the index
     */
    boolean isBlank();

    /**
     * @return true if the current line is blank starting from the index
     */
    boolean isBlankLine();

    /**
     * @return the deepest open block parser
     */
    BlockParser getActiveBlockParser();

    /**
     * @return the current list of active block parsers, deepest is last
     */
    List<BlockParser> getActiveBlockParsers();

    /**
     * @param node block node for which to get the active block parser
     * @return an active block parser for the node or null if not found or the block is already closed.
     */
    BlockParser getActiveBlockParser(Block node);

    /**
     * @return inline parser instance for the parser state
     */
    InlineParser getInlineParser();
    /**
     * @return The 0 based current line number within the input
     */
    int getLineNumber();

    /**
     * @return the start of line offset into the input stream corresponding to current index into the line
     */
    int getLineStart();

    /**
     * @return the EOL offset into the input stream corresponding to current index into the line
     */
    int getLineEolLength();

    /**
     * @return the end of line offset into the input stream corresponding to current index into the line, including the EOL
     */
    int getLineEndIndex();

    /**
     * Test the block to see if it ends in a blank line. The blank line can be in the block or its last child.
     *
     * @param block block to be tested
     * @return true if the block ends in a blank line
     */
    boolean endsWithBlankLine(Node block);

    /**
     * Test a block to see if the last line of the block is blank. Children not tested.
     *
     * @param node block instance to test
     * @return true if the block's last line is blank
     */
    boolean isLastLineBlank(Node node);

    /**
     * @return document properties of the document being parsed
     */
    MutableDataHolder getProperties();

    /**
     * Get the current parser phase
     *
     * @return the current parser phase {@link ParserPhase}
     */
    ParserPhase getParserPhase();

    /**
     * @return strings and patterns class adjusted for options {@link Parsing}
     */
    Parsing getParsing();

    /**
     * Returns a list of document lines encountered this far in the parsing process
     *
     * @return list of line sequences (including EOLs)
     */
    List<BasedSequence> getLineSegments();
}

