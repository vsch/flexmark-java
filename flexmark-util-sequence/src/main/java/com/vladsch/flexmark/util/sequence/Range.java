package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

public class Range {
    final public static Range NULL = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);
    final public static Range EMPTY = new Range(0, 0);

    @NotNull
    public static Range of(int start, int end) {
        return start == NULL.start && end == NULL.end ? NULL : new Range(start, end);
    }

    @NotNull
    public static Range emptyOf(int position) {
        return new Range(position, position);
    }

    @NotNull
    public static Range ofLength(int start, int length) {
        return new Range(start, start + length);
    }

    final private int start;
    final private int end;

    /**
     * Create range
     *
     * @param start start
     * @param end   end
     */
    protected Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @NotNull
    protected Range(@NotNull Range other) {
        start = other.start;
        end = other.end;
    }

    // @formatter:off
    public int getStart() { return start; }
    public int getEnd() { return end; }

    // Kotlin compatibility getters
    public int component1() { return start; }
    public int component2() { return end; }
    // compatibility getters with JetBrains API TextRange
    public int getStartOffset() { return start; }
    public int getEndOffset() { return end; }
    // @formatter:on

    // TEST:
    // @formatter:off
    public Range withStart(int start) { return start == this.start ? this : Range.of(start, end);}
    public Range withEnd(int end) { return end == this.end ? this : Range.of(start, end);}
    public Range endMinus(int delta) { return delta == 0 ? this : Range.of(start, end - delta);}
    public Range endPlus(int delta) { return delta == 0 ? this : Range.of(start, end + delta);}
    public Range startMinus(int delta) { return delta == 0 ? this : Range.of(start - delta, end);}
    public Range startPlus(int delta) { return delta == 0 ? this : Range.of(start + delta, end);}
    public Range withRange(int start, int end) { return start == this.start && end == this.end ? this : Range.of(start, end);}
    public Range shiftLeft(int delta) { return delta == 0 ? this: Range.of(start - delta, end - delta);}
    public Range shiftRight(int delta) { return delta == 0 ? this: Range.of(start + delta, end + delta);}
    // @formatter:on

//    /**
//     * @return span of this range: end-start,
//     * @deprecated  use {@link #getSpan()}
//     */
//    @Deprecated
//    public int length() { return getSpan();}

    // TEST:
    // @formatter:off
    public int getSpan() { return isNull() ? 0 : end - start; }

    // NOTE: change to equal NULL instead of instance otherwise inheriting makes null equality impossible
    public boolean isNull() { return this.start == NULL.start && this.end == NULL.end;}
    public boolean isNotNull() { return !isNull();}
    public boolean isEmpty() { return start >= end; }
    public boolean isNotEmpty() { return start < end; }
    // @formatter:on

    // TEST:
    // @formatter:off
    public boolean contains(@NotNull Range other) { return end >= other.end && start <= other.start; }
    public boolean doesContain(@NotNull Range other) { return end >= other.end && start <= other.start; }

    public boolean contains(int index) { return start <= index && index < end;}
    public boolean doesContain(int index) { return start <= index && index < end; }

    public boolean contains(int start, int end) { return this.start <= start && end <= this.end;}
    public boolean doesContain(int start, int end) { return this.start <= start && end <= this.end;}

    public boolean overlaps(@NotNull Range other) { return !(other.end <= start || other.start >= end); }
    public boolean doesOverlap(@NotNull Range other) { return !(other.end <= start || other.start >= end); }
    public boolean doesNotOverlap(@NotNull Range other) { return other.end <= start || other.start >= end; }

    public boolean overlapsOrAdjacent(@NotNull Range other) { return !(other.end < start || other.start > end); }
    public boolean doesOverlapOrAdjacent(@NotNull Range other) { return !(other.end < start || other.start > end); }
    public boolean doesNotOverlapOrAdjacent(@NotNull Range other) { return other.end < start || other.start > end; }
    public boolean doesNotOverlapNorAdjacent(@NotNull Range other) { return other.end < start || other.start > end; }

    public boolean properlyContains(@NotNull Range other) { return end > other.end && start < other.start; }
    public boolean doesProperlyContain(@NotNull Range other) { return end > other.end && start < other.start; }

    public boolean isAdjacent(int index) { return index == start - 1 || index == end;}
    public boolean isAdjacentAfter(int index) { return start - 1 == index;}
    public boolean isAdjacentBefore(int index) { return end == index;}
    public boolean isAdjacent(@NotNull Range other) { return start == other.end || end == other.start;}
    public boolean isAdjacentBefore(@NotNull Range other) { return end == other.start;}
    public boolean isAdjacentAfter(@NotNull Range other) { return start == other.end;}

    public boolean isContainedBy(@NotNull Range other) { return other.end >= end && other.start <= start; }
    public boolean isContainedBy(int start, int end) { return end >= this.end && start <= this.start; }
    public boolean isProperlyContainedBy(@NotNull Range other) { return other.end > end && other.start < start; }
    public boolean isProperlyContainedBy(int start, int end) { return end > this.end && start < this.start; }

    public boolean isEqual(@NotNull Range other) { return end == other.end && start == other.start; }

    public boolean isValidIndex(int index) { return index >= start && index <= end; }
    public boolean isStart(int index) { return index == start;}
    public boolean isEnd(int index) { return index == end;}
    public boolean isLast(int index) { return index >= start && index == end - 1;}

    public boolean leadBy(int index) { return index <= start;}
    public boolean leads(int index) { return end <= index;}
    public boolean trailedBy(int index) { return end <= index;}
    public boolean trails(int index) { return index <= start;}
    // @formatter:on

    @NotNull
    public Range intersect(@NotNull Range other) {
        int thisStart = Math.max(start, other.start);
        int thisEnd = Math.min(end, other.end);

        if (thisStart >= thisEnd) thisStart = thisEnd;
        return withRange(thisStart, thisEnd);
    }

    @NotNull
    public Range exclude(@NotNull Range other) {
        int thisStart = start;
        if (thisStart >= other.start && thisStart < other.end) thisStart = other.end;

        int thisEnd = end;
        if (thisEnd <= other.end && thisEnd > other.start) thisEnd = other.start;

        if (thisStart >= thisEnd) thisStart = thisEnd = 0;
        return withRange(thisStart, thisEnd);
    }

    public int compare(@NotNull Range other) {
        if (start < other.start) {
            return -1;
        } else if (start > other.start) {
            return 1;
        } else if (end > other.end) {
            return -1;
        } else if (end < other.end) {
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
        return expandToInclude(other.start, other.end);
    }

    @NotNull
    public Range expandToInclude(int start, int end) {
        return withRange(Math.min(this.start, start), Math.max(this.end, end));
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
        return BasedSequence.of(charSequence).subSequence(start, end);
    }

    @NotNull
    public BasedSequence basedSafeSubSequence(@NotNull CharSequence charSequence) {
        int end = Math.min(charSequence.length(), this.end);
        return isNull() ? BasedSequence.NULL : BasedSequence.of(charSequence).subSequence(Math.min(end, Math.max(0, start)), end);
    }

    @NotNull
    public RichSequence richSubSequence(@NotNull CharSequence charSequence) {
        return RichSequence.of(charSequence.subSequence(start, end));
    }

    @NotNull
    public RichSequence richSafeSubSequence(@NotNull CharSequence charSequence) {
        int end = Math.min(charSequence.length(), this.end);
        return isNull() ? RichSequence.NULL : RichSequence.of(charSequence, Math.min(end, Math.max(0, start)), end);
    }

    @NotNull
    public CharSequence charSubSequence(@NotNull CharSequence charSequence) {
        return charSequence.subSequence(start, end);
    }

    @NotNull
    public CharSequence safeSubSequence(@NotNull CharSequence charSequence) {
        int end = Math.min(charSequence.length(), this.end);
        return isNull() ? charSequence.subSequence(0, 0) : charSequence.subSequence(Math.min(end, Math.max(0, start)), end);
    }

    @Override
    public String toString() {
        return "[" + start + ", " + end + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Range)) return false;
        Range range = (Range) o;
        return start == range.start && end == range.end;
    }

    @Override
    public int hashCode() {
        int result = start;
        result = 31 * result + end;
        return result;
    }
}
