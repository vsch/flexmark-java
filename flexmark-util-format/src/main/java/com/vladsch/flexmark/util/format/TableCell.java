package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import org.jetbrains.annotations.NotNull;

public class TableCell {
  static final TableCell NULL =
      new TableCell(null, BasedSequence.NULL, " ", BasedSequence.NULL, 1, 0, CellAlignment.NONE);
  public static final TableCell DEFAULT_CELL =
      new TableCell(null, BasedSequence.NULL, " ", BasedSequence.NULL, 1, 1, CellAlignment.NONE);
  public static final int NOT_TRACKED = Integer.MAX_VALUE;

  final Node tableCellNode; // node if needed for finer text manipulation
  public final BasedSequence openMarker;
  final BasedSequence text;
  public final BasedSequence closeMarker;
  final int columnSpan;
  final int rowSpan;
  final CellAlignment alignment;
  final int trackedTextOffset; // offset in the text
  final int spanTrackedOffset; // offset in the span if span > 1
  final int trackedTextAdjust; // adjustment to the resulting tracked position due to alignment
  final boolean afterSpace; // if adjustment should be done after space
  final boolean afterDelete; // if adjustment should be done as if after delete

  TableCell(CharSequence text, int rowSpan, int columnSpan) {
    this(
        null,
        BasedSequence.NULL,
        text,
        BasedSequence.NULL,
        rowSpan,
        columnSpan,
        CellAlignment.NONE);
  }

  TableCell(
      Node tableCellNode,
      CharSequence openMarker,
      CharSequence text,
      CharSequence closeMarker,
      int rowSpan,
      int columnSpan) {
    this(tableCellNode, openMarker, text, closeMarker, rowSpan, columnSpan, CellAlignment.NONE);
  }

  public TableCell(
      Node tableCellNode,
      CharSequence openMarker,
      CharSequence text,
      CharSequence closeMarker,
      int rowSpan,
      int columnSpan,
      CellAlignment alignment) {
    this(
        tableCellNode,
        openMarker,
        text,
        closeMarker,
        rowSpan,
        columnSpan,
        alignment,
        NOT_TRACKED,
        NOT_TRACKED,
        0,
        false,
        false);
  }

  private TableCell(
      Node tableCellNode,
      CharSequence openMarker,
      CharSequence text,
      CharSequence closeMarker,
      int rowSpan,
      int columnSpan,
      CellAlignment alignment,
      int trackedTextOffset,
      int spanTrackedOffset,
      int trackedTextAdjust,
      boolean afterSpace,
      boolean afterDelete) {
    BasedSequence chars = BasedSequence.of(text);
    this.tableCellNode = tableCellNode;
    this.openMarker = BasedSequence.of(openMarker);
    this.closeMarker = BasedSequence.of(closeMarker);
    BasedSequence useMarker =
        this.openMarker.isEmpty()
            ? this.closeMarker.subSequence(0, 0)
            : this.openMarker.subSequence(this.openMarker.length());
    this.text =
        chars.isEmpty() && chars != BasedSequence.NULL
            ? PrefixedSubSequence.prefixOf(" ", useMarker)
            : chars;
    this.rowSpan = rowSpan;
    this.columnSpan = columnSpan;
    this.alignment = alignment != null ? alignment : CellAlignment.NONE;
    this.trackedTextOffset = trackedTextOffset;
    this.spanTrackedOffset = spanTrackedOffset;
    this.trackedTextAdjust = trackedTextAdjust;
    this.afterSpace = afterSpace;
    this.afterDelete = afterDelete;
  }

  TableCell(
      @NotNull TableCell other,
      boolean copyNode,
      int rowSpan,
      int columnSpan,
      CellAlignment alignment) {
    this.tableCellNode = copyNode ? other.tableCellNode : null;
    this.openMarker = other.openMarker;
    this.closeMarker = other.closeMarker;
    BasedSequence useMarker =
        this.openMarker.isEmpty()
            ? this.closeMarker.subSequence(0, 0)
            : this.openMarker.subSequence(this.openMarker.length());
    this.text =
        other.text == BasedSequence.NULL
            ? PrefixedSubSequence.prefixOf(" ", useMarker)
            : other.text;
    this.rowSpan = rowSpan;
    this.columnSpan = columnSpan;
    this.alignment = alignment != null ? alignment : CellAlignment.NONE;
    this.trackedTextOffset = other.trackedTextOffset;
    this.spanTrackedOffset = other.spanTrackedOffset;
    this.trackedTextAdjust = other.trackedTextAdjust;
    this.afterSpace = other.afterSpace;
    this.afterDelete = other.afterDelete;
  }

  TableCell withColumnSpan(int columnSpan) {
    return new TableCell(
        tableCellNode,
        openMarker,
        text,
        closeMarker,
        rowSpan,
        columnSpan,
        alignment,
        trackedTextOffset,
        spanTrackedOffset == NOT_TRACKED ? NOT_TRACKED : Utils.min(spanTrackedOffset, columnSpan),
        trackedTextAdjust,
        afterSpace,
        afterDelete);
  }

  TableCell withText(CharSequence text) {
    return new TableCell(
        tableCellNode,
        openMarker,
        text,
        closeMarker,
        rowSpan,
        columnSpan,
        alignment,
        NOT_TRACKED,
        spanTrackedOffset,
        trackedTextAdjust,
        afterSpace,
        afterDelete);
  }

  TableCell withText(CharSequence openMarker, CharSequence text, CharSequence closeMarker) {
    return new TableCell(
        tableCellNode,
        openMarker,
        text,
        closeMarker,
        rowSpan,
        columnSpan,
        alignment,
        NOT_TRACKED,
        spanTrackedOffset,
        trackedTextAdjust,
        afterSpace,
        afterDelete);
  }

  TableCell withTrackedOffset(int trackedTextOffset) {
    return new TableCell(
        tableCellNode,
        openMarker,
        text,
        closeMarker,
        rowSpan,
        columnSpan,
        alignment,
        trackedTextOffset,
        spanTrackedOffset,
        trackedTextAdjust,
        afterSpace,
        afterDelete);
  }

  TableCell withTrackedOffset(int trackedTextOffset, boolean afterSpace, boolean afterDelete) {
    return new TableCell(
        tableCellNode,
        openMarker,
        text,
        closeMarker,
        rowSpan,
        columnSpan,
        alignment,
        trackedTextOffset,
        spanTrackedOffset,
        trackedTextAdjust,
        afterSpace,
        afterDelete);
  }

  TableCell withSpanTrackedOffset(int spanTrackedOffset) {
    return new TableCell(
        tableCellNode,
        openMarker,
        text,
        closeMarker,
        rowSpan,
        columnSpan,
        alignment,
        trackedTextOffset,
        spanTrackedOffset,
        trackedTextAdjust,
        afterSpace,
        afterDelete);
  }

  TableCell withTrackedTextAdjust(int trackedTextAdjust) {
    return new TableCell(
        tableCellNode,
        openMarker,
        text,
        closeMarker,
        rowSpan,
        columnSpan,
        alignment,
        trackedTextOffset,
        spanTrackedOffset,
        trackedTextAdjust,
        afterSpace,
        afterDelete);
  }

  BasedSequence getLastSegment() {
    return !closeMarker.isEmpty() ? closeMarker : text;
  }

  public int getEndOffset() {
    return !closeMarker.isEmpty() ? closeMarker.getEndOffset() : text.getEndOffset();
  }

  int getStartOffset(TableCell previousCell) {
    return previousCell != null
        ? previousCell.getEndOffset()
        : !openMarker.isEmpty() ? openMarker.getStartOffset() : text.getStartOffset();
  }

  int getInsideStartOffset(TableCell previousCell) {
    return previousCell != null
        ? previousCell.getEndOffset()
        : !openMarker.isEmpty() ? openMarker.getEndOffset() : text.getStartOffset();
  }

  int getTextStartOffset(TableCell previousCell) {
    if (!text.isEmpty()) {
      return text.getStartOffset();
    } else if (!openMarker.isEmpty()) {
      return openMarker.getEndOffset() + 1;
    } else if (previousCell != null) {
      return previousCell.getEndOffset() + 1;
    } else {
      return closeMarker.getStartOffset() - 1;
    }
  }

  int getTextEndOffset(TableCell previousCell) {
    if (!text.isEmpty()) {
      return text.getEndOffset();
    } else if (!openMarker.isEmpty()) {
      return openMarker.getEndOffset() + 1;
    } else if (previousCell != null) {
      return previousCell.getEndOffset() + 1;
    } else {
      return closeMarker.getStartOffset() - 1;
    }
  }

  public int getInsideEndOffset() {
    return !closeMarker.isEmpty() ? closeMarker.getStartOffset() : text.getEndOffset();
  }

  private static CharSequence dumpSequence(BasedSequence sequence) {
    StringBuilder sb = new StringBuilder();
    sb.append("{ \"")
        .append(sequence.replace("\"", "\\\""))
        .append("\"")
        .append(" [")
        .append(sequence.getStartOffset())
        .append(", ")
        .append(sequence.getEndOffset())
        .append("), length=")
        .append(sequence.length())
        .append("}");
    return sb;
  }

  @Override
  public String toString() {
    // NOTE: show not simple name but name of container class if any
    return this.getClass().getName().substring(getClass().getPackage().getName().length() + 1)
        + "{"
        + "openMarker="
        + dumpSequence(openMarker)
        + ", text="
        + dumpSequence(text)
        + ", closeMarker="
        + dumpSequence(closeMarker)
        + ", columnSpan="
        + columnSpan
        + ", rowSpan="
        + rowSpan
        + ", alignment="
        + alignment
        + ", trackedTextOffset="
        + trackedTextOffset
        + ", spanTrackedOffset="
        + spanTrackedOffset
        + ", trackedTextAdjust="
        + trackedTextAdjust
        + ", afterSpace="
        + afterSpace
        + ", afterDelete="
        + afterDelete
        + '}';
  }
}
