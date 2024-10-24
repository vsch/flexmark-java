package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/** Body part of a {@link TableBlock} containing {@link TableRow TableRows}. */
public class TableBody extends Node {

  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  public TableBody() {}

  public TableBody(BasedSequence chars) {
    super(chars);
  }
}
