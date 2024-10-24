package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class BulletList extends ListBlock {
  private char openingMarker;

  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  public BulletList() {}

  public char getOpeningMarker() {
    return openingMarker;
  }

  public void setOpeningMarker(char openingMarker) {
    this.openingMarker = openingMarker;
  }
}
