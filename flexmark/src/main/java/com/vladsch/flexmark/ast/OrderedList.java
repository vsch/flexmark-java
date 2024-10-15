package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class OrderedList extends ListBlock {
  private int startNumber;
  private char delimiter;

  @NotNull
  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  public OrderedList() {}

  @Override
  public void getAstExtra(@NotNull StringBuilder out) {
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
