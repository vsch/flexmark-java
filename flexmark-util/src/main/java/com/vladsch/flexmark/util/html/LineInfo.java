package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.collection.BitFieldSet;
import org.jetbrains.annotations.NotNull;

/**
 * Line information in LineAppendable
 */
public class LineInfo {
    public enum Flags {
        BLANK_PREFIX,
        BLANK_TEXT,
        PREFORMATTED,
        HAS_EOL,
    }

    final public static Flags O_BLANK_PREFIX = Flags.BLANK_PREFIX;
    final public static Flags O_BLANK_TEXT = Flags.BLANK_TEXT;
    final public static Flags O_PREFORMATTED = Flags.PREFORMATTED;
    final public static Flags O_HAS_EOL = Flags.HAS_EOL;

    final public static int F_BLANK_PREFIX = BitFieldSet.intMask(Flags.BLANK_PREFIX);
    final public static int F_BLANK_TEXT = BitFieldSet.intMask(Flags.BLANK_TEXT);
    final public static int F_PREFORMATTED = BitFieldSet.intMask(Flags.PREFORMATTED);
    final public static int F_HAS_EOL = BitFieldSet.intMask(Flags.HAS_EOL);

    final public static LineInfo NULL = new LineInfo(-1, 0, 0, 0, 0, 0, 0, true, true, true);

    final public int index;              // line index
    final public int prefixLength;       // line's prefix length
    final public int textLength;         // line's text length
    final public int length;             // line's length (including EOL if any)
    final public int sumPrefixLength;    // total length of previous lines' prefixes
    final public int sumTextLength;      // total length of previous lines' text
    final public int sumLength;          // total length of previous lines
    final public int flags;

    private LineInfo(int index, int prefixLength, int textLength, int length, int sumPrefixLength, int sumTextLength, int sumLength, boolean isBlankPrefix, boolean isBlankText, boolean isPreformatted) {
        this.index = index;
        this.prefixLength = prefixLength;
        this.textLength = textLength;
        this.length = length;
        this.sumPrefixLength = sumPrefixLength;
        this.sumTextLength = sumTextLength;
        this.sumLength = sumLength;
        this.flags = (isBlankPrefix ? F_BLANK_PREFIX : 0) | (isBlankText ? F_BLANK_TEXT : 0) | (isPreformatted ? F_PREFORMATTED : 0) | (prefixLength + textLength < length ? F_HAS_EOL : 0);
    }

    public boolean isBlankPrefix() {
        return BitFieldSet.any(flags, F_BLANK_PREFIX);
    }

    public boolean isBlankText() {
        return BitFieldSet.any(flags, F_BLANK_TEXT);
    }

    public boolean isPreformatted() {
        return BitFieldSet.any(flags, F_PREFORMATTED);
    }

    public boolean isNull() {
        return this == NULL;
    }

    public boolean isNotNull() {
        return this != NULL;
    }

    /**
     * NOTE: a line which consists of any prefix and blank text is considered a blank line
     *
     * @return true if the line is a blank line
     */
    public boolean isBlankLine() {
        return BitFieldSet.all(flags, F_BLANK_TEXT);
    }

    public boolean endsWithEOL() {
        return BitFieldSet.any(flags, F_HAS_EOL);
    }

    @NotNull
    public static LineInfo create(int prefixLength, int textLength, int length, boolean isBlankPrefix, boolean isBlankText, boolean isPreformatted) {
        return new LineInfo(0, prefixLength, textLength, length, 0, 0, 0, isBlankPrefix, isBlankText, isPreformatted);
    }

    @NotNull
    public static LineInfo create(@NotNull LineInfo prevInfo, int prefixLength, int textLength, int length, boolean isBlankPrefix, boolean isBlankText, boolean isPreformatted) {
        return new LineInfo(
                prevInfo.index + 1,
                prefixLength,
                textLength,
                length,
                prevInfo.sumPrefixLength + prevInfo.prefixLength,
                prevInfo.sumTextLength + prevInfo.textLength,
                prevInfo.sumLength + prevInfo.length,
                isBlankPrefix,
                isBlankText,
                isPreformatted
        );
    }
}
