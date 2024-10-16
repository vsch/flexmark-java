package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Paragraph extends Block implements TextContainer {
  private static final int[] EMPTY_INDENTS = new int[0];
  private int[] lineIndents = EMPTY_INDENTS;
  private boolean trailingBlankLine = false;
  private boolean hasTableSeparator;

  @NotNull
  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  @Override
  public void getAstExtra(@NotNull StringBuilder out) {
    super.getAstExtra(out);
    if (trailingBlankLine) out.append(" isTrailingBlankLine");
  }

  public Paragraph() {}

  private void setLineIndents(List<Integer> lineIndents) {
    this.lineIndents = new int[lineIndents.size()];
    int i = 0;
    for (int indent : lineIndents) {
      this.lineIndents[i++] = indent;
    }
  }

  @Override
  public void setContent(@NotNull BlockContent blockContent) {
    super.setContent(blockContent);
    setLineIndents(blockContent.getLineIndents());
  }

  public void setContent(Paragraph other, int startLine, int endLine) {
    super.setContent(other.getContentLines(startLine, endLine));
    if (endLine > startLine) {
      int[] lineIndents = new int[endLine - startLine];
      System.arraycopy(other.lineIndents, startLine, lineIndents, 0, endLine - startLine);
      this.lineIndents = lineIndents;
    } else {
      this.lineIndents = EMPTY_INDENTS;
    }
  }

  public void setLineIndents(int[] lineIndents) {
    this.lineIndents = lineIndents;
  }

  public int getLineIndent(int line) {
    return lineIndents[line];
  }

  public int[] getLineIndents() {
    return lineIndents;
  }

  public boolean isTrailingBlankLine() {
    return trailingBlankLine;
  }

  public void setTrailingBlankLine(boolean trailingBlankLine) {
    this.trailingBlankLine = trailingBlankLine;
  }

  public void setHasTableSeparator(boolean hasTableSeparator) {
    this.hasTableSeparator = hasTableSeparator;
  }

  public boolean hasTableSeparator() {
    return hasTableSeparator;
  }

  @Override
  public boolean collectText(
      ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out,
      int flags,
      NodeVisitor nodeVisitor) {
    return true;
  }

  @Override
  public void collectEndText(
      ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out,
      int flags,
      NodeVisitor nodeVisitor) {
    if (trailingBlankLine) {
      out.add("\n");
    }
  }
}
