package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/** A Definition block node */
public class DefinitionTerm extends ListItem {
  @Override
  public void getAstExtra(StringBuilder out) {}

  @Override
  public BasedSequence[] getSegments() {
    return Node.EMPTY_SEGMENTS;
  }

  public DefinitionTerm() {}

  @Override
  public boolean isParagraphWrappingDisabled(
      Paragraph node, ListOptions listOptions, DataHolder options) {
    return true;
  }
}
