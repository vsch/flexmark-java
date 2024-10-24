package com.vladsch.flexmark.util.sequence;

public class Range {
  public static final Range NULL = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);

  public static Range of(int start, int end) {
    return start == NULL.start && end == NULL.end ? NULL : new Range(start, end);
  }

  public static Range emptyOf(int position) {
    return new Range(position, position);
  }

  public static Range ofLength(int start, int length) {
    return new Range(start, start + length);
  }

  private final int start;
  private final int end;

  /**
   * Create range
   *
   * @param start start
   * @param end end
   */
  protected Range(int start, int end) {
    this.start = start;
    this.end = end;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  public Range withStart(int start) {
    return start == this.start ? this : Range.of(start, end);
  }

  public Range withEnd(int end) {
    return end == this.end ? this : Range.of(start, end);
  }

  public Range endPlus(int delta) {
    return delta == 0 ? this : Range.of(start, end + delta);
  }

  public Range startMinus(int delta) {
    return delta == 0 ? this : Range.of(start - delta, end);
  }

  public Range withRange(int start, int end) {
    return start == this.start && end == this.end ? this : Range.of(start, end);
  }

  public int getSpan() {
    return isNull() ? 0 : end - start;
  }

  // NOTE: change to equal NULL instead of instance otherwise inheriting makes null equality
  // impossible
  public boolean isNull() {
    return this.start == NULL.start && this.end == NULL.end;
  }

  public boolean isNotNull() {
    return !isNull();
  }

  public boolean isEmpty() {
    return start >= end;
  }

  public boolean isNotEmpty() {
    return start < end;
  }

  public boolean contains(int index) {
    return start <= index && index < end;
  }

  public boolean isAdjacentBefore(Range other) {
    return end == other.start;
  }

  public Range expandToInclude(Range other) {
    return expandToInclude(other.start, other.end);
  }

  private Range expandToInclude(int start, int end) {
    return withRange(Math.min(this.start, start), Math.max(this.end, end));
  }

  public BasedSequence basedSubSequence(CharSequence charSequence) {
    return BasedSequence.of(charSequence).subSequence(start, end);
  }

  CharSequence safeSubSequence(CharSequence charSequence) {
    int end = Math.min(charSequence.length(), this.end);
    return isNull()
        ? charSequence.subSequence(0, 0)
        : charSequence.subSequence(Math.min(end, Math.max(0, start)), end);
  }

  @Override
  public String toString() {
    return "[" + start + ", " + end + ")";
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Range)) {
      return false;
    }
    Range range = (Range) object;
    return start == range.start && end == range.end;
  }

  @Override
  public int hashCode() {
    int result = start;
    result = 31 * result + end;
    return result;
  }
}
