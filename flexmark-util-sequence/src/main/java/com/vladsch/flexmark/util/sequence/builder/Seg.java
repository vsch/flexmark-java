package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.util.misc.Utils.escapeJavaString;

/**
 * Representation of a segment part in a segment list for a sequence
 * it is a Range, either in the base sequence or in the out of base characters for the builder.
 * <p>
 * Out of base text offsets are limited to 1GB. Upper bit is used to store repeated and ascii only flags.
 */
public class Seg {
    final public static Seg NULL = new Seg(Range.NULL.getStart(), Range.NULL.getEnd());
    final public static Seg ANCHOR_0 = new Seg(0, 0);
    final public static int MAX_TEXT_OFFSET = Integer.MAX_VALUE >> 1;
    final public static int F_TEXT_OPTION = Integer.MAX_VALUE & ~(MAX_TEXT_OFFSET);

    final private int start;
    final private int end;

    private Seg(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getSegStart() {
        return isText() ? getTextStart() : start;
    }

    public int getSegEnd() {
        return isText() ? getTextEnd() : end;
    }

    public int getTextStart() {
        return getTextOffset(start);
    }

    public static int getTextOffset(int startOffset) {
        return (-startOffset - 1) & MAX_TEXT_OFFSET;
    }

    public int getTextEnd() {
        return getTextOffset(end);
    }

    public boolean isFirst256Start() {
        return isFirst256Start(start);
    }

    public static boolean isFirst256Start(int start) {
        return ((-start - 1) & F_TEXT_OPTION) != 0;
    }

    public boolean isRepeatedTextEnd() {
        return isRepeatedTextEnd(end);
    }

    public static boolean isRepeatedTextEnd(int end) {
        return ((-end - 1) & F_TEXT_OPTION) != 0;
    }

    public boolean isText() {
        return start < 0 && end < 0 && (start & MAX_TEXT_OFFSET) > (end & MAX_TEXT_OFFSET);
    }

    /**
     * Test segment type being from original sequence
     *
     * @return true if it is
     */
    public boolean isBase() {
        return start >= 0 && end >= 0 && start <= end;
    }

    /**
     * Test segment type being from original sequence
     *
     * @return true if it is
     */
    public boolean isAnchor() {
        return start >= 0 && end >= 0 && start == end;
    }

    public boolean isNull() {
        return !(isBase() || isText());
    }

    @NotNull
    public Range getRange() {
        assert isBase();
        return Range.of(start, end);
    }

    /**
     * Return length of text or if text is null span of range
     *
     * @return length of this part in the sequence
     */
    public int length() {
        return isBase() ? end - start : isText() ? (start & MAX_TEXT_OFFSET) - (end & MAX_TEXT_OFFSET) : 0;
    }

    public String toString(@NotNull CharSequence allText) {
        if (this.isNull()) {
            return "NULL";
        } else if (isBase()) {
            if (start == end) {
                return "[" + start + ")";
            } else {
                return "[" + start + ", " + end + ")";
            }
        } else {
            CharSequence charSequence = allText.subSequence(getTextStart(), getTextEnd());

            if (isRepeatedTextEnd() && length() > 1) {
                if (isFirst256Start()) {
                    return "a:" + (length() + "x'" + escapeJavaString(charSequence.subSequence(0, 1)) + "'");
                } else {
                    return "" + (length() + "x'" + escapeJavaString(charSequence.subSequence(0, 1)) + "'");
                }
            } else {
                String chars = length() <= 20 ? charSequence.toString() : charSequence.subSequence(0, 10).toString() + "…" + charSequence.subSequence(length() - 10, length()).toString();
                if (isFirst256Start()) {
                    return "a:'" + escapeJavaString(chars) + "'";
                } else {
                    return "'" + escapeJavaString(chars) + "'";
                }
            }
        }
    }

    @Override
    public String toString() {
        if (this.isNull()) {
            return "NULL";
        } else if (isBase()) {
            if (start == end) {
                return "BASE[" + start + ")";
            } else {
                return "BASE[" + start + ", " + end + ")";
            }
        } else {
            return "TEXT[" + getTextStart() + ", " + getTextEnd() + ")";
        }
    }

    @NotNull
    public static Seg segOf(int startOffset, int endOffset) {
        return startOffset == 0 && endOffset == 0 ? ANCHOR_0 : new Seg(startOffset, endOffset);
    }

    public static int getTextStart(int startOffset, boolean isFirst256) {
        assert startOffset < MAX_TEXT_OFFSET;
        return -(isFirst256 ? startOffset | F_TEXT_OPTION : startOffset) - 1;
    }

    public static int getTextEnd(int startOffset, boolean isRepeatedText) {
        assert startOffset < MAX_TEXT_OFFSET;
        return -(isRepeatedText ? startOffset | F_TEXT_OPTION : startOffset) - 1;
    }

    @NotNull
    public static Seg textOf(int startOffset, int endOffset, boolean isFirst256, boolean isRepeatedText) {
        return new Seg(getTextStart(startOffset, isFirst256), getTextEnd(endOffset, isRepeatedText));
    }
}
