package com.vladsch.flexmark.util.sequence;

public class Range {
    public static final Range NULL = new Range(0, 0);

    public static Range of(int start, int end) {
        return new Range(start, end);
    }

    private final int myStart;
    private final int myEnd;

    public int getStart() { return myStart; }

    public int getEnd() { return myEnd; }

    public int component1() { return myStart; }

    public int component2() { return myEnd; }

    //public TextRange asTextRange() {
    //    return new TextRange(myStart, myEnd);
    //}

    public Range(int start, int end) {
        myStart = start;
        myEnd = end;
    }

    public Range(Range other) {
        myStart = other.myStart;
        myEnd = other.myEnd;
    }

    public Range withStart(int start) {
        return start == myStart ? this : new Range(start, myEnd);
    }

    public Range withEnd(int end) {
        return end == myEnd ? this : new Range(myStart, end);
    }

    public Range withRange(int start, int end) {
        return start == myStart && end == myEnd ? this : new Range(start, end);
    }

    public BasedSequence subSequence(CharSequence charSequence) {
        return BasedSequenceImpl.of(charSequence, myStart, myEnd);
    }

    public boolean contains(int index) {
        return myStart <= index && index < myEnd;
    }

    public boolean contains(int start, int end) {
        return myStart <= start && end <= myEnd;
    }

    public boolean leads(int index) {
        return myEnd <= index;
    }

    public boolean trails(int index) {
        return myStart > index;
    }

    public boolean leadBy(int index) {
        return myStart <= index && index < myEnd;
    }

    public boolean trailedBy(int index) {
        return myStart <= index && index < myEnd;
    }

    public boolean doesNotOverlap(Range other) { return other.myEnd <= myStart || other.myStart >= myEnd; }

    public boolean doesOverlap(Range other) { return !(other.myEnd <= myStart || other.myStart >= myEnd); }

    public boolean isEqual(Range other) { return myEnd == other.myEnd && myStart == other.myStart; }

    public boolean doesContain(Range other) { return myEnd >= other.myEnd && myStart <= other.myStart; }

    public boolean doesProperlyContain(Range other) { return myEnd > other.myEnd && myStart < other.myStart; }

    public boolean isEmpty() { return myStart >= myEnd; }

    public boolean isContainedBy(int start, int end) { return end >= myEnd && start <= myStart; }

    public boolean isProperlyContainedBy(int start, int end) { return end > myEnd && start < myStart; }

    public boolean isContainedBy(Range other) { return other.myEnd >= myEnd && other.myStart <= myStart; }

    public boolean isProperlyContainedBy(Range other) { return other.myEnd > myEnd && other.myStart < myStart; }

    public boolean doesContain(int index) {
        return index >= myStart && index < myEnd;
    }

    public boolean isAdjacent(int index) {
        return index == myStart - 1 || index == myEnd;
    }

    public boolean isStart(int index) {
        return index == myStart;
    }

    public boolean isEnd(int index) {
        return index == myEnd;
    }

    public boolean isLast(int index) {
        return index >= myStart && index == myEnd - 1;
    }

    public boolean isAdjacentBefore(int index) {
        return myEnd == index;
    }

    public boolean isAdjacentAfter(int index) {
        return myStart - 1 == index;
    }

    public Range intersect(Range other) {
        int thisStart = myStart;
        if (thisStart < other.myStart) thisStart = other.myStart;
        int thisEnd = myEnd;
        if (thisEnd > other.myEnd) thisEnd = other.myEnd;

        if (thisStart >= thisEnd) thisStart = thisEnd = 0;
        return withRange(thisStart, thisEnd);
    }

    public Range exclude(Range other) {
        int thisStart = myStart;
        if (thisStart >= other.myStart && thisStart < other.myEnd) thisStart = other.myEnd;

        int thisEnd = myEnd;
        if (thisEnd <= other.myEnd && thisEnd > other.myStart) thisEnd = other.myStart;

        if (thisStart >= thisEnd) thisStart = thisEnd = 0;
        return withRange(thisStart, thisEnd);
    }

    public int compare(Range other) {
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

    public int getSpan() {
        return myEnd - myStart;
    }

    public int length() {
        return myEnd - myStart;
    }

    public boolean isNull() {
        return this == NULL;
    }

    public boolean isNotNull() {
        return this != NULL;
    }

    @Override
    public String toString() {
        return "[" + myStart + ", " + myEnd + ")";
    }

    public boolean isAdjacent(Range other) {
        return myStart == other.myEnd || myEnd == other.myStart;
    }

    public boolean isAdjacentBefore(Range other) {
        return myEnd == other.myStart;
    }

    public boolean isAdjacentAfter(Range other) {
        return myStart == other.myEnd;
    }

    public Range include( Range other) {
        return other.isNull() ? (this.isNull() ? NULL : this) : expandToInclude(other);
    }

    public Range include(int pos) {
        return include(pos, pos);
    }

    public Range include(int start, int end) {
        return this.isNull() ? new Range(start, end) : expandToInclude(start, end);
    }

    public Range expandToInclude( Range other) {
        return expandToInclude(other.myStart, other.myEnd);
    }

    public Range expandToInclude(int start, int end) {
        return withRange(myStart > start ? start : myStart, myEnd < end ? end : myEnd);
    }

    //public boolean equals(TextRange o) {
    //    return myStart == o.getStartOffset() && myEnd == o.getEndOffset();
    //}

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
