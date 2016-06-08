package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.PropertyHolder;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.BlockPreProcessor;
import com.vladsch.flexmark.parser.InlineParser;

/**
 * State of the parser that is used in block parsers.
 * <p><em>This interface is not intended to be implemented by clients.</em></p>
 */
public interface ParserState extends BlockPreProcessor {

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
     * @return the deepest open block parser
     */
    BlockParser getActiveBlockParser();

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

    boolean endsWithBlankLine(Node block);
    boolean isLastLineBlank(Node node);

    PropertyHolder getPropertyHolder();
}
