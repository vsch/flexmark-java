package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import java.util.ArrayList;
import java.util.List;

public class BlockContent {
  // list of line text
  private final List<BasedSequence> lines = new ArrayList<>();
  private final List<Integer> lineIndents = new ArrayList<>();

  public BasedSequence getSpanningChars() {
    return !lines.isEmpty()
        ? lines
            .get(0)
            .baseSubSequence(
                lines.get(0).getStartOffset(), lines.get(lines.size() - 1).getEndOffset())
        : BasedSequence.NULL;
  }

  public List<BasedSequence> getLines() {
    return lines;
  }

  public List<Integer> getLineIndents() {
    return lineIndents;
  }

  public int getLineCount() {
    return lines.size();
  }

  public BlockContent() {}

  public int getStartOffset() {
    return !lines.isEmpty() ? lines.get(0).getStartOffset() : -1;
  }

  public int getEndOffset() {
    return !lines.isEmpty() ? lines.get(lines.size() - 1).getEndOffset() : -1;
  }

  public int getLineIndent() {
    return !lines.isEmpty() ? lineIndents.get(0) : 0;
  }

  public int getSourceLength() {
    return !lines.isEmpty()
        ? lines.get(lines.size() - 1).getEndOffset() - lines.get(0).getStartOffset()
        : -1;
  }

  public void add(BasedSequence lineWithEOL, int lineIndent) {
    lines.add(lineWithEOL);
    lineIndents.add(lineIndent);
  }

  public void addAll(List<BasedSequence> lines, List<Integer> lineIndents) {
    this.lines.addAll(lines);
    this.lineIndents.addAll(lineIndents);
  }

  public BasedSequence getContents() {
    if (lines.isEmpty()) {
      return BasedSequence.NULL;
    }
    return getContents(0, lines.size());
  }

  private BasedSequence getContents(int startLine, int endLine) {
    if (lines.isEmpty()) {
      return BasedSequence.NULL;
    }

    if (startLine < 0) {
      throw new IndexOutOfBoundsException("startLine must be at least 0");
    }
    if (endLine < 0) {
      throw new IndexOutOfBoundsException("endLine must be at least 0");
    }
    if (endLine < startLine) {
      throw new IndexOutOfBoundsException("endLine must not be less than startLine");
    }
    if (endLine > lines.size()) {
      throw new IndexOutOfBoundsException("endLine must not be greater than line cardinality");
    }

    return SegmentedSequence.create(lines.get(0), lines.subList(startLine, endLine));
  }

  public String getString() {
    if (lines.isEmpty()) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    for (BasedSequence line : lines) {
      sb.append(line.trimEOL());
      sb.append('\n');
    }

    return sb.toString();
  }
}
