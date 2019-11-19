package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.vladsch.flexmark.util.Utils.escapeJavaString;

/**
 * Representation of a segment part in a segment list for a sequence
 * it is a Range with non-null text.
 * <p>
 * NULL range means the segment has no offset information
 * empty text means the segment represents characters from original sequence
 */
public class Seg {
    final public static Seg NULL = new Seg(Range.NULL.getStart(), Range.NULL.getEnd(), "");
    // OPTIMIZE: can add N anchors from 0 to N for most frequent offset anchors
    final public static Seg ANCHOR_0 = new Seg(0, 0, "");

    private final int myStart;
    private final int myEnd;
    final private @NotNull String myText;

    private Seg(int start, int end, @NotNull String text) {
        myStart = start;
        myEnd = end;
        myText = text;
    }

    public int getStart() {
        return myStart;
    }

    public int getEnd() {
        return myEnd;
    }

    @NotNull
    public String getText() {
        return myText;
    }

    public boolean isNullRange() {
        return myStart == Range.NULL.getStart() && myEnd == Range.NULL.getEnd();
    }

    public boolean isNullString() {
        return myText.isEmpty();
    }

    public boolean isNull() {
        return isNullRange() && isNullString();
    }

    @Nullable
    public Range getRangeOrNull() {
        return isNullRange() ? null : Range.of(myStart, myEnd);
    }

    @NotNull
    public Range getRange() {
        return isNullRange() ? Range.NULL : Range.of(myStart, myEnd);
    }

    public int getSpan() {
        return myStart < myEnd ? myEnd - myStart : 0;
    }

    @Nullable
    public String getTextOrNull() {
        return myText.isEmpty() ? null : myText;
    }

    /**
     * Test segment type being from original sequence
     *
     * @return true if it is
     */
    public boolean isBase() {
        return !isNullRange() && myText.isEmpty();
    }

    /**
     * Test segment type is an un-anchored string from outside original sequence
     *
     * @return true if it is
     */
    public boolean isString() {
        return isNull() && !myText.isEmpty();
    }

    /**
     * Test segment type is an anchor for position in the original sequence
     *
     * @return true if it is
     */
    public boolean isAnchor() {
        return !isNullRange() && getSpan() == 0 && myText.isEmpty();
    }

    /**
     * Test segment type is text from outside the original sequence anchored at the start at an offset
     * with an end offset in original sequence which may be the same as start
     *
     * @return true if it is
     */
    public boolean isText() {
        return !isNullRange() && !myText.isEmpty();
    }

    /**
     * Test segment type is text or string from outside the original sequence anchored at the start at an offset
     * with an end offset in original sequence which may be the same as start
     *
     * @return true if it is
     */
    public boolean isTextOrString() {
        return !myText.isEmpty();
    }

    @NotNull
    public Seg withTextSuffix(@NotNull String text) {
        return text.isEmpty() ? this : new Seg(myStart, myEnd, myText + text);
    }

    @NotNull
    public Seg withTextPrefix(@NotNull String text) {
        return text.isEmpty() ? this : new Seg(myStart, myEnd, text + myText);
    }

    @NotNull
    public Seg withRange(@NotNull Range other) {
        return this.myStart == other.getStart() && this.myEnd == other.getEnd() ? this : new Seg(other.getStart(), other.getEnd(), myText);
    }

    @NotNull
    public Seg withRange(int start, int end) {
        return this.myStart == start && this.myEnd == end ? this : new Seg(start, end, myText);
    }

    @NotNull
    public Seg withStart(int start) {
        return this.myStart == start ? this : start == Range.NULL.getStart() ? stringSeg(myText) : new Seg(start, isNullRange() ? start : myEnd, myText);
    }

    @NotNull
    public Seg withEnd(int end) {
        return this.myEnd == end ? this : end == Range.NULL.getEnd() ? stringSeg(myText) : new Seg(isNullRange() ? end : myStart, end, myText);
    }

    @NotNull
    public Seg withText(@NotNull String text) {
        return this.myText.equals(text) ? this : new Seg(myStart, myEnd, text);
    }

    /**
     * Return length of text or if text is null span of range
     *
     * @return length of this part in the sequence
     */
    public int length() {
        return isBase() ? getSpan() : myText.length();
    }

    @Override
    public String toString() {
        if (this.isNull()) {
            return "NULL";
        } else if (myText.isEmpty()) {
            if (myStart == myEnd) {
                return "[" + myStart + ")";
            } else {
                return "[" + myStart + ", " + myEnd + ")";
            }
        } else if (isNullRange()) {
            return "'" + escapeJavaString(myText) + "'";
        } else {
            if (myStart == myEnd) {
                return "[" + myStart + ", '" + escapeJavaString(myText) + "')";
            } else {
                return "[" + myStart + ", " + myEnd + ", '" + escapeJavaString(myText) + "')";
            }
        }
    }

    @NotNull
    static Seg baseSeg(int startOffset, int endOffset) {
        return new Seg(startOffset, endOffset, "");
    }

    @NotNull
    static Seg anchorSeg(int offset) {
        return offset == 0 ? ANCHOR_0 : new Seg(offset, offset, "");
    }

    @NotNull
    static Seg baseSeg(@NotNull Range range) {
        return range.isNull() ? NULL : new Seg(range.getStart(), range.getEnd(), "");
    }

    @NotNull
    static Seg textSeg(int offset, @NotNull String text) {
        return new Seg(offset, offset, text);
    }

    @NotNull
    static Seg textSeg(int startOffset, int endOffset, @NotNull String text) {
        return new Seg(startOffset, endOffset, text);
    }

    @NotNull
    static Seg stringSeg(@NotNull String text) {
        return text.isEmpty() ? NULL : new Seg(Range.NULL.getStart(), Range.NULL.getEnd(), text);
    }
}
