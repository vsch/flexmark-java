package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.util.Utils.escapeJavaString;

/**
 * Representation of a segment part in a segment list for a sequence
 * it is a Range, either in the base sequence or in the out of base characters for the builder.
 * <p>
 * Out of base text offsets are limited to 1GB. Upper bit is used to store repeated and ascii only flags.
 */
class Seg {
    final public static Seg NULL = new Seg(Range.NULL.getStart(), Range.NULL.getEnd());
    final public static Seg ANCHOR_0 = new Seg(0, 0);
    final public static int MAX_TEXT_OFFSET = Integer.MAX_VALUE >> 1;
    final public static int F_TEXT_OPTION = Integer.MAX_VALUE & ~(MAX_TEXT_OFFSET);

    private final int myStart;
    private final int myEnd;

    private Seg(int start, int end) {
        myStart = start;
        myEnd = end;
    }

    public int getStart() {
        return myStart;
    }

    public int getEnd() {
        return myEnd;
    }

    public int getTextStart() {
        return getTextOffset(myStart);
    }

    public static int getTextOffset(int startOffset) {
        return (-startOffset - 1) & MAX_TEXT_OFFSET;
    }

    public int getTextEnd() {
        return getTextOffset(myEnd);
    }

    public boolean isFirst256Start() {
        return isFirst256Start(myStart);
    }

    public static boolean isFirst256Start(int start) {
        return ((-start - 1) & F_TEXT_OPTION) != 0;
    }

    public boolean isRepeatedTextEnd() {
        return isRepeatedTextEnd(myEnd);
    }

    public static boolean isRepeatedTextEnd(int end) {
        return ((-end - 1) & F_TEXT_OPTION) != 0;
    }

    public boolean isText() {
        return myStart < 0 && myEnd < 0 && (myStart & MAX_TEXT_OFFSET) > (myEnd & MAX_TEXT_OFFSET);
    }

    /**
     * Test segment type being from original sequence
     *
     * @return true if it is
     */
    public boolean isBase() {
        return myStart >= 0 && myEnd >= 0 && myStart <= myEnd;
    }

    /**
     * Test segment type being from original sequence
     *
     * @return true if it is
     */
    public boolean isAnchor() {
        return myStart >= 0 && myEnd >= 0 && myStart == myEnd;
    }

    public boolean isNull() {
        return !(isBase() || isText());
    }

    @NotNull
    public Range getRange() {
        assert isBase();
        return Range.of(myStart, myEnd);
    }

    /**
     * Return length of text or if text is null span of range
     *
     * @return length of this part in the sequence
     */
    public int length() {
        return isBase() ? myEnd - myStart : isText() ? (myStart & MAX_TEXT_OFFSET) - (myEnd & MAX_TEXT_OFFSET) : 0;
    }

    public String toString(@NotNull CharSequence allText) {
        if (this.isNull()) {
            return "NULL";
        } else if (isBase()) {
            if (myStart == myEnd) {
                return "[" + myStart + ")";
            } else {
                return "[" + myStart + ", " + myEnd + ")";
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
            if (myStart == myEnd) {
                return "BASE[" + myStart + ")";
            } else {
                return "BASE[" + myStart + ", " + myEnd + ")";
            }
        } else {
            return "TEXT[" + getTextStart() + ", " + getTextEnd() + ")";
        }
    }

    @NotNull
    static Seg segOf(int startOffset, int endOffset) {
        return startOffset == 0 && endOffset == 0 ? ANCHOR_0 : new Seg(startOffset, endOffset);
    }

    static int getTextStart(int startOffset, boolean isFirst256) {
        assert startOffset < MAX_TEXT_OFFSET;
        return -(isFirst256 ? startOffset | F_TEXT_OPTION : startOffset) - 1;
    }

    static int getTextEnd(int startOffset, boolean isRepeatedText) {
        assert startOffset < MAX_TEXT_OFFSET;
        return -(isRepeatedText ? startOffset | F_TEXT_OPTION : startOffset) - 1;
    }

    @NotNull
    static Seg textOf(int startOffset, int endOffset, boolean isFirst256, boolean isRepeatedText) {
        return new Seg(getTextStart(startOffset, isFirst256), getTextEnd(endOffset, isRepeatedText));
    }
}
