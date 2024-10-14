package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ast.ListBlock;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/** A DefinitionList block node */
public class DefinitionList extends ListBlock {
  public DefinitionList() {}

  @NotNull
  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }
}
