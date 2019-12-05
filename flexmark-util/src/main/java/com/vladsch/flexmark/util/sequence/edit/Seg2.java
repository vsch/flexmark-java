package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.util.Utils.escapeJavaString;

/**
 * Representation of a segment part in a segment list for a sequence
 * it is a Range with non-null text.
 * <p>
 * NULL range means the segment has no offset information
 * empty text means the segment represents characters from original sequence
 */
class Seg2 {
    final public static Seg2 NULL = new Seg2(Range.NULL.getStart(), Range.NULL.getEnd());

    // OPTIMIZE: can add N anchors from 0 to N for most frequent offset anchors
    final public static Seg2 ANCHOR_0 = new Seg2(0, 0);

    private final int myStart;
    private final int myEnd;

    private Seg2(int start, int end) {
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
        return -myStart - 1;
    }

    public int getTextEnd() {
        return -myEnd - 1;
    }

    public boolean isText() {
        return myStart < 0 && myEnd < 0 && myStart > myEnd;
    }

    /**
     * Test segment type being from original sequence
     *
     * @return true if it is
     */
    public boolean isBase() {
        return myStart >= 0 && myEnd >= 0 && myStart <= myEnd;
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
        return isBase() ? myEnd - myStart : isText() ? myStart - myEnd : 0;
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
            return "'" + escapeJavaString(allText.subSequence(getTextStart(), getTextEnd())) + "'";
        }
    }

    @NotNull
    static Seg2 segOf(int startOffset, int endOffset) {
        return new Seg2(startOffset, endOffset);
    }
}
