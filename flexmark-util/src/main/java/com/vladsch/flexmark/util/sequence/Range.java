package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

public class Range {
    public static final Range NULL = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);
    public static final Range EMPTY = new Range(0, 0);

    @NotNull
    public static Range of(int start, int end) {
        return start == NULL.myStart && end == NULL.myEnd ? NULL : new Range(start, end);
    }

    @NotNull
    public static Range emptyOf(int position) {
        return new Range(position, position);
    }

    @NotNull
    public static Range ofLength(int start, int length) {
        return new Range(start, start + length);
    }

    private final int myStart;
    private final int myEnd;

    /**
     * Create range
     *
     * @param start start
     * @param end   end
     * @deprecated use {@link Range#of(int, int)}
     */
    @Deprecated
    public Range(int start, int end) {
        myStart = start;
        myEnd = end;
    }

    @NotNull
    protected Range(@NotNull Range other) {
        myStart = other.myStart;
        myEnd = other.myEnd;
    }

    // @formatter:off
    public int getStart() { return myStart; }
    public int getEnd() { return myEnd; }

    // Kotlin compatibility getters
    public int component1() { return myStart; }
    public int component2() { return myEnd; }
    // compatibility getters with JetBrains API TextRange
    public int getStartOffset() { return myStart; }
    public int getEndOffset() { return myEnd; }
    // @formatter:on

    // TEST:
    // @formatter:off
    public Range withStart(int start) { return start == myStart ? this : Range.of(start, myEnd);}
    public Range withEnd(int end) { return end == myEnd ? this : Range.of(myStart, end);}
    public Range endMinus(int delta) { return delta == 0 ? this : Range.of(myStart, myEnd - delta);}
    public Range endPlus(int delta) { return delta == 0 ? this : Range.of(myStart, myEnd + delta);}
    public Range startMinus(int delta) { return delta == 0 ? this : Range.of(myStart - delta, myEnd);}
    public Range startPlus(int delta) { return delta == 0 ? this : Range.of(myStart + delta, myEnd);}
    public Range withRange(int start, int end) { return start == myStart && end == myEnd ? this : Range.of(start, end);}
    public Range shiftLeft(int delta) { return delta == 0 ? this: Range.of(myStart - delta, myEnd - delta);}
    public Range shiftRight(int delta) { return delta == 0 ? this: Range.of(myStart + delta, myEnd + delta);}
    // @formatter:on

//    /**
//     * @return span of this range: end-start,
//     * @deprecated  use {@link #getSpan()}
//     */
//    @Deprecated
//    public int length() { return getSpan();}

    // TEST:
    // @formatter:off
    public int getSpan() { return myEnd - myStart; }

    // NOTE: change to equal NULL instead of instance otherwise inheriting makes null equality impossible
    public boolean isNull() { return this.myStart == NULL.myStart && this.myEnd == NULL.myEnd;}
    public boolean isNotNull() { return !isNull();}
    public boolean isEmpty() { return myStart >= myEnd; }
    public boolean isNotEmpty() { return myStart < myEnd; }
    // @formatter:on

    // TEST:
    // @formatter:off
    public boolean contains(@NotNull Range other) { return myEnd >= other.myEnd && myStart <= other.myStart; }
    public boolean doesContain(@NotNull Range other) { return myEnd >= other.myEnd && myStart <= other.myStart; }

    public boolean contains(int index) { return myStart <= index && index < myEnd;}
    public boolean doesContain(int index) { return index >= myStart && index < myEnd; }

    public boolean contains(int start, int end) { return myStart <= start && end <= myEnd;}
    public boolean doesContain(int start, int end) { return myStart <= start && end <= myEnd;}

    public boolean overlaps(@NotNull Range other) { return !(other.myEnd <= myStart || other.myStart >= myEnd); }
    public boolean doesOverlap(@NotNull Range other) { return !(other.myEnd <= myStart || other.myStart >= myEnd); }
    public boolean doesNotOverlap(@NotNull Range other) { return other.myEnd <= myStart || other.myStart >= myEnd; }

    public boolean overlapsOrAdjacent(@NotNull Range other) { return !(other.myEnd < myStart || other.myStart > myEnd); }
    public boolean doesOverlapOrAdjacent(@NotNull Range other) { return !(other.myEnd < myStart || other.myStart > myEnd); }
    public boolean doesNotOverlapOrAdjacent(@NotNull Range other) { return other.myEnd < myStart || other.myStart > myEnd; }
    public boolean doesNotOverlapNorAdjacent(@NotNull Range other) { return other.myEnd < myStart || other.myStart > myEnd; }

    public boolean properlyContains(@NotNull Range other) { return myEnd > other.myEnd && myStart < other.myStart; }
    public boolean doesProperlyContain(@NotNull Range other) { return myEnd > other.myEnd && myStart < other.myStart; }

    public boolean isAdjacent(int index) { return index == myStart - 1 || index == myEnd;}
    public boolean isAdjacentAfter(int index) { return myStart - 1 == index;}
    public boolean isAdjacentBefore(int index) { return myEnd == index;}
    public boolean isAdjacent(@NotNull Range other) { return myStart == other.myEnd || myEnd == other.myStart;}
    public boolean isAdjacentBefore(@NotNull Range other) { return myEnd == other.myStart;}
    public boolean isAdjacentAfter(@NotNull Range other) { return myStart == other.myEnd;}

    public boolean isContainedBy(@NotNull Range other) { return other.myEnd >= myEnd && other.myStart <= myStart; }
    public boolean isContainedBy(int start, int end) { return end >= myEnd && start <= myStart; }
    public boolean isProperlyContainedBy(@NotNull Range other) { return other.myEnd > myEnd && other.myStart < myStart; }
    public boolean isProperlyContainedBy(int start, int end) { return end > myEnd && start < myStart; }

    public boolean isEqual(@NotNull Range other) { return myEnd == other.myEnd && myStart == other.myStart; }

    public boolean isValidIndex(int index) { return index >= myStart && index <= myEnd; }
    public boolean isStart(int index) { return index == myStart;}
    public boolean isEnd(int index) { return index == myEnd;}
    public boolean isLast(int index) { return index >= myStart && index == myEnd - 1;}

    public boolean leadBy(int index) { return index <= myStart;}
    public boolean leads(int index) { return myEnd <= index;}
    public boolean trailedBy(int index) { return myEnd <= index;}
    public boolean trails(int index) { return index <= myStart;}
    // @formatter:on

    @NotNull
    public Range intersect(@NotNull Range other) {
        int thisStart = Math.max(myStart, other.myStart);
        int thisEnd = Math.min(myEnd, other.myEnd);

        if (thisStart >= thisEnd) thisStart = thisEnd;
        return withRange(thisStart, thisEnd);
    }

    @NotNull
    public Range exclude(@NotNull Range other) {
        int thisStart = myStart;
        if (thisStart >= other.myStart && thisStart < other.myEnd) thisStart = other.myEnd;

        int thisEnd = myEnd;
        if (thisEnd <= other.myEnd && thisEnd > other.myStart) thisEnd = other.myStart;

        if (thisStart >= thisEnd) thisStart = thisEnd = 0;
        return withRange(thisStart, thisEnd);
    }

    public int compare(@NotNull Range other) {
        if (myStart < other.myStart) {
            return -1;
        } else if (myStart > other.myStart) {
            return 1;
        } else if (myEnd > other.myEnd) {
            return -1;
        } else if (myEnd < other.myEnd) {
            return 1;
        }
        return 0;
    }

    @NotNull
    public Range include(@NotNull Range other) {
        return other.isNull() ? (this.isNull() ? NULL : this) : expandToInclude(other);
    }

    @NotNull
    public Range include(int pos) {
        return include(pos, pos);
    }

    @NotNull
    public Range include(int start, int end) {
        return this.isNull() ? Range.of(start, end) : expandToInclude(start, end);
    }

    @NotNull
    public Range expandToInclude(@NotNull Range other) {
        return expandToInclude(other.myStart, other.myEnd);
    }

    @NotNull
    public Range expandToInclude(int start, int end) {
        return withRange(Math.min(myStart, start), Math.max(myEnd, end));
    }

    /**
     * Return a based subsequence of sequence given by this range
     *
     * @param charSequence char sequence from which to extract the range
     * @return resulting based subsequence
     * @deprecated use {@link #basedSubSequence(CharSequence)} instead
     */
    @NotNull
    @Deprecated
    public BasedSequence subSequence(@NotNull CharSequence charSequence) {
        return basedSubSequence(charSequence);
    }

    @NotNull
    public BasedSequence basedSubSequence(@NotNull CharSequence charSequence) {
        return BasedSequence.of(charSequence).subSequence(myStart, myEnd);
    }

    @NotNull
    public BasedSequence basedSafeSubSequence(@NotNull CharSequence charSequence) {
        int end = Math.min(charSequence.length(), myEnd);
        return isNull() ? BasedSequence.NULL : BasedSequence.of(charSequence).subSequence(Math.min(end, Math.max(0, myStart)), end);
    }

    @NotNull
    public RichSequence richSubSequence(@NotNull CharSequence charSequence) {
        return RichSequence.of(charSequence.subSequence(myStart, myEnd));
    }

    @NotNull
    public RichSequence richSafeSubSequence(@NotNull CharSequence charSequence) {
        int end = Math.min(charSequence.length(), myEnd);
        return isNull() ? RichSequence.NULL : RichSequence.of(charSequence, Math.min(end, Math.max(0, myStart)), end);
    }

    @NotNull
    public CharSequence charSubSequence(@NotNull CharSequence charSequence) {
        return charSequence.subSequence(myStart, myEnd);
    }

    @NotNull
    public CharSequence safeSubSequence(@NotNull CharSequence charSequence) {
        int end = Math.min(charSequence.length(), myEnd);
        return isNull() ? charSequence.subSequence(0, 0) : charSequence.subSequence(Math.min(end, Math.max(0, myStart)), end);
    }

    @Override
    public String toString() {
        return "[" + myStart + ", " + myEnd + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Range)) return false;
        Range range = (Range) o;
        return myStart == range.myStart && myEnd == range.myEnd;
    }

    @Override
    public int hashCode() {
        int result = myStart;
        result = 31 * result + myEnd;
        return result;
    }
}
