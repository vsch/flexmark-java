package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.util.ast.DelimitedNode;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/** A strikethrough node containing text and other inline nodes nodes as children. */
public class Strikethrough extends Node implements DelimitedNode {
  private BasedSequence openingMarker = BasedSequence.NULL;
  private BasedSequence text = BasedSequence.NULL;
  private BasedSequence closingMarker = BasedSequence.NULL;

  @Override
  public BasedSequence[] getSegments() {
    return new BasedSequence[] {openingMarker, text, closingMarker};
  }

  @Override
  public void getAstExtra(StringBuilder out) {
    delimitedSegmentSpan(out, openingMarker, text, closingMarker, "text");
  }

  public Strikethrough() {}

  public Strikethrough(
      BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
    super(
        openingMarker.baseSubSequence(
            openingMarker.getStartOffset(), closingMarker.getEndOffset()));
    this.openingMarker = openingMarker;
    this.text = text;
    this.closingMarker = closingMarker;
  }

  @Override
  public BasedSequence getOpeningMarker() {
    return openingMarker;
  }

  @Override
  public void setOpeningMarker(BasedSequence openingMarker) {
    this.openingMarker = openingMarker;
  }

  @Override
  public BasedSequence getText() {
    return text;
  }

  @Override
  public void setText(BasedSequence text) {
    this.text = text;
  }

  @Override
  public BasedSequence getClosingMarker() {
    return closingMarker;
  }

  @Override
  public void setClosingMarker(BasedSequence closingMarker) {
    this.closingMarker = closingMarker;
  }
}
