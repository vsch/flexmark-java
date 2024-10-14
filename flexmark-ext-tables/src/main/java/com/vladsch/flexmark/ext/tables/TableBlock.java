package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.util.ast.BlankLineBreakNode;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/** Table block containing a {@link TableHead} and optionally a {@link TableBody}. */
public class TableBlock extends Block implements BlankLineBreakNode {
  public TableBlock() {}

  public TableBlock(List<BasedSequence> lineSegments) {
    super(lineSegments);
  }

  @NotNull
  @Override
  public BasedSequence[] getSegments() {
    return new BasedSequence[0];
  }
}
