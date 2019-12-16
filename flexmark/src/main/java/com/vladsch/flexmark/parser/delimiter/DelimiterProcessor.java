package com.vladsch.flexmark.parser.delimiter;

import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.core.delimiter.Delimiter;
import com.vladsch.flexmark.util.ast.Node;

/**
 * Custom delimiter processor for additional delimiters besides {@code _} and {@code *}.
 * <p>
 * Note that implementations of this need to be thread-safe, the same instance may be used by multiple parsers.
 */
public interface DelimiterProcessor {

    /**
     * @return the character that marks the beginning of a delimited node, must not clash with any built-in special
     *         characters
     */
    char getOpeningCharacter();

    /**
     * @return the character that marks the the ending of a delimited node, must not clash with any built-in special
     *         characters. Note that for a symmetric delimiter such as "*", this is the same as the opening.
     */
    char getClosingCharacter();

    /**
     * @return Minimum number of delimiter characters that are needed to activate this. Must be at least 1.
     */
    int getMinLength();

    /**
     * Determine how many (if any) of the delimiter characters should be used.
     * <p>
     * This allows implementations to decide how many characters to use based on the properties of the delimiter runs.
     * An implementation can also return 0 when it doesn't want to allow this particular combination of delimiter runs.
     *
     * @param opener the opening delimiter run
     * @param closer the closing delimiter run
     * @return how many delimiters should be used; must not be greater than length of either opener or closer
     */
    int getDelimiterUse(DelimiterRun opener, DelimiterRun closer);

    /**
     * Process the matched delimiters, e.g. by wrapping the nodes between opener and closer in a new node, or appending
     * a new node after the opener.
     * <p>
     * Note that removal of the delimiter from the delimiter nodes and unlinking them is done by the caller.
     *
     * @param opener         the delimiter with text node that contained the opening delimiter
     * @param closer         the delimiter with text node that contained the closing delimiter
     * @param delimitersUsed the number of delimiters that were used
     */
    void process(Delimiter opener, Delimiter closer, int delimitersUsed);

    /**
     * Allow delimiter processor to substitute unmatched delimiters by custom nodes
     *
     * @param inlineParser inline parser instance
     * @param delimiter    delimiter run that was not matched
     * @return node to replace unmatched delimiter, null or delimiter.getNode() to replace with delimiter text
     */
    Node unmatchedDelimiterNode(InlineParser inlineParser, DelimiterRun delimiter);

    /**
     * Decide whether this delimiter can be an open delimiter
     *
     * @param before              string before delimiter or '\n' if none
     * @param after               string after delimiter or '\n' if none
     * @param leftFlanking        is left flanking delimiter
     * @param rightFlanking       is right flanking delimiter
     * @param beforeIsPunctuation is punctuation before
     * @param afterIsPunctuation  is punctuation after
     * @param beforeIsWhitespace  is whitespace before
     * @param afterIsWhiteSpace   is whitespace after
     * @return true if can be open delimiter
     */
    boolean canBeOpener(String before, String after, boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace);

    /**
     * Decide whether this delimiter can be a close delimiter
     *
     * @param before              string before delimiter or '\n' if none
     * @param after               string after delimiter or '\n' if none
     * @param leftFlanking        is left flanking delimiter
     * @param rightFlanking       is right flanking delimiter
     * @param beforeIsPunctuation is punctuation before
     * @param afterIsPunctuation  is punctuation after
     * @param beforeIsWhitespace  is whitespace before
     * @param afterIsWhiteSpace   is whitespace after
     * @return true if can be open delimiter
     */
    boolean canBeCloser(String before, String after, boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace);

    /**
     * Whether to skip delimiters that cannot be openers or closers
     *
     * @return true if to skip
     */
    boolean skipNonOpenerCloser();
}
