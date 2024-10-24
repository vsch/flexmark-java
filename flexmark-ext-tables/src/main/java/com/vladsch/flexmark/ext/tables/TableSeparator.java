package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.util.ast.DoNotCollectText;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/** Body part of a {@link TableBlock} containing {@link TableRow TableRows}. */
public class TableSeparator extends Node implements DoNotDecorate, DoNotCollectText {

  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  public TableSeparator() {}
}
