package com.vladsch.flexmark.util.sequence.builder.tree;

final class SegmentTreePos {
  final int pos;
  final int startIndex;
  private final int iterations;

  SegmentTreePos(int pos, int startIndex, int iterations) {
    this.pos = pos;
    this.startIndex = startIndex;
    this.iterations = iterations;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof SegmentTreePos)) {
      return false;
    }

    SegmentTreePos pos1 = (SegmentTreePos) object;

    if (pos != pos1.pos) {
      return false;
    }
    return startIndex == pos1.startIndex;
  }

  @Override
  public int hashCode() {
    int result = pos;
    result = 31 * result + startIndex;
    return result;
  }

  @Override
  public String toString() {
    return "{" + pos + ", s: " + startIndex + ", i: " + iterations + '}';
  }
}
