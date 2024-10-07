package com.vladsch.flexmark.util.sequence;

public class TagRange extends Range {
  private final String tag;

  public TagRange(CharSequence tag, int start, int end) {
    super(start, end);
    this.tag = String.valueOf(tag);
  }

  public String getTag() {
    return tag;
  }

  @Override
  public TagRange withStart(int start) {
    return start == getStart() ? this : new TagRange(getTag(), start, getEnd());
  }

  @Override
  public TagRange withEnd(int end) {
    return end == getEnd() ? this : new TagRange(getTag(), getStart(), end);
  }

  @Override
  public TagRange withRange(int start, int end) {
    return start == getStart() && end == getEnd() ? this : new TagRange(getTag(), start, end);
  }
}
