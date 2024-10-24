package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.TableCell;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Table cell separator only used during parsing, not part of the AST, use the {@link
 * TableCell#getOpeningMarker()} and {@link TableCell#getClosingMarker()}
 */
class TableColumnSeparator extends Node implements DoNotDecorate {
  TableColumnSeparator() {}

  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  @Override
  public void getAstExtra(StringBuilder out) {
    astExtraChars(out);
  }

  @Override
  protected String toStringAttributes() {
    return "text=" + getChars();
  }
}
