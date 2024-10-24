package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class OrderedList extends ListBlock {
  private int startNumber;
  private char delimiter;

  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  public OrderedList() {}

  @Override
  public void getAstExtra(StringBuilder out) {
    super.getAstExtra(out);
    if (startNumber > 1) out.append(" start:").append(startNumber);
    out.append(" delimiter:'").append(delimiter).append("'");
  }

  public int getStartNumber() {
    return startNumber;
  }

  public void setStartNumber(int startNumber) {
    this.startNumber = startNumber;
  }

  public char getDelimiter() {
    return delimiter;
  }

  public void setDelimiter(char delimiter) {
    this.delimiter = delimiter;
  }
}
