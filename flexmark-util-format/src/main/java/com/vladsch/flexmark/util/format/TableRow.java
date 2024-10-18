package com.vladsch.flexmark.util.format;

import static com.vladsch.flexmark.util.misc.Utils.minLimit;

import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import java.util.ArrayList;
import java.util.List;

public class TableRow {
  final List<TableCell> cells;
  int beforeOffset = TableCell.NOT_TRACKED;
  int afterOffset = TableCell.NOT_TRACKED;
  private boolean normalized = true;

  public TableRow() {
    cells = new ArrayList<>();
  }

  public List<TableCell> getCells() {
    return cells;
  }

  public int getColumns() {
    return cells.size();
  }

  public int getTotalColumns() {
    return getSpannedColumns();
  }

  public int getSpannedColumns() {
    int columns = 0;
    for (TableCell cell : cells) {
      if (cell == null) {
        continue;
      }
      columns += cell.columnSpan;
    }
    return columns;
  }

  public int getBeforeOffset() {
    return beforeOffset;
  }

  public void setBeforeOffset(int beforeOffset) {
    this.beforeOffset = beforeOffset;
  }

  public int getAfterOffset() {
    return afterOffset;
  }

  public void setAfterOffset(int afterOffset) {
    this.afterOffset = afterOffset;
  }

  void appendColumns(int count) {
    appendColumns(count, null);
  }

  private void appendColumns(int count, TableCell tableCell) {
    if (tableCell == null || tableCell.columnSpan == 0) tableCell = defaultCell();

    for (int i = 0; i < count; i++) {
      // add empty column
      cells.add(cells.size(), tableCell);
    }
  }

  public TableCell defaultCell() {
    return new TableCell(" ", 1, 1);
  }

  /**
   * @param column column index before which to insert
   * @param count number of columns to insert
   */
  void insertColumns(int column, int count) {
    insertColumns(column, count, null);
  }

  /**
   * NOTE: inserting into a cell span has the effect of expanding the span if the cell text is blank
   * or insert count &gt; 1 or splitting the span if it is not blank and count == 1
   *
   * @param column column index before which to insert
   * @param count number of columns to insert
   * @param tableCell table cell to insert, null for default
   */
  private void insertColumns(int column, int count, TableCell tableCell) {
    if (count <= 0 || column < 0) {
      return;
    }

    normalizeIfNeeded();

    if (tableCell == null || tableCell.columnSpan == 0) tableCell = defaultCell();

    int totalColumns = this.getTotalColumns();
    if (column >= totalColumns) {
      // append to the end
      appendColumns(count, tableCell);
    } else {
      // insert in the middle
      MarkdownTable.IndexSpanOffset indexSpan = indexOf(column);
      int index = indexSpan.index;
      int spanOffset = indexSpan.spanOffset;

      if (spanOffset > 0 && index < cells.size()) {
        // spanning column, we expand its span or split into 2
        TableCell cell = cells.get(index);

        if (tableCell.text.isBlank() || count > 1) {
          // expand span
          cells.remove(index);
          cells.add(index, cell.withColumnSpan(cell.columnSpan + count));
        } else {
          // split span into before inserted and after
          cells.remove(index);
          cells.add(index, cell.withColumnSpan(spanOffset));
          cells.add(
              index + 1, tableCell.withColumnSpan(minLimit(1, cell.columnSpan - spanOffset + 1)));
        }
      } else {
        for (int i = 0; i < count; i++) {
          cells.add(index, tableCell);
        }
      }
    }
  }

  /**
   * @param column column index before which to insert
   * @param count number of columns to insert
   */
  void deleteColumns(int column, int count) {
    if (count <= 0 || column < 0) {
      return;
    }

    normalizeIfNeeded();

    int remaining = count;
    MarkdownTable.IndexSpanOffset indexSpan = indexOf(column);
    int index = indexSpan.index;
    int spanOffset = indexSpan.spanOffset;

    while (index < cells.size() && remaining > 0) {
      TableCell cell = cells.get(index);
      cells.remove(index);

      if (spanOffset > 0) {
        // inside the first partial span, truncate it to offset or reduce by remaining
        if (cell.columnSpan - spanOffset > remaining) {
          cells.add(index, cell.withColumnSpan(cell.columnSpan - remaining));
          break;
        }

        // reinsert with reduced span
        cells.add(index, cell.withColumnSpan(spanOffset));
        index++;
      } else if (cell.columnSpan - spanOffset > remaining) {
        // reinsert with reduced span and empty text
        cells.add(index, defaultCell().withColumnSpan(cell.columnSpan - remaining));
        break;
      }

      remaining -= cell.columnSpan - spanOffset;
      spanOffset = 0;
    }
  }

  void moveColumn(int fromColumn, int toColumn) {
    if (fromColumn < 0 || toColumn < 0) {
      return;
    }

    normalizeIfNeeded();

    int maxColumn = getTotalColumns();

    if (fromColumn >= maxColumn) {
      return;
    }
    if (toColumn >= maxColumn) toColumn = maxColumn - 1;

    if (fromColumn != toColumn && toColumn < maxColumn) {
      MarkdownTable.IndexSpanOffset fromIndexSpan = indexOf(fromColumn);
      int fromIndex = fromIndexSpan.index;
      int fromSpanOffset = fromIndexSpan.spanOffset;
      TableCell cell = cells.get(fromIndex).withColumnSpan(1);

      MarkdownTable.IndexSpanOffset toIndexSpan = indexOf(toColumn);
      int toIndex = toIndexSpan.index;

      if (toIndex != fromIndex) {
        if (fromSpanOffset > 0) {
          // from inside the span is same as a blank column
          insertColumns(toColumn + (fromColumn <= toColumn ? 1 : 0), 1, defaultCell());
        } else {
          insertColumns(toColumn + (fromColumn <= toColumn ? 1 : 0), 1, cell.withColumnSpan(1));
        }
        deleteColumns(fromColumn + (toColumn <= fromColumn ? 1 : 0), 1);
      }
    }
  }

  TableRow expandTo(int column) {
    return expandTo(column, TableCell.NULL);
  }

  TableRow expandTo(int column, TableCell cell) {
    if (cell == null || cell.columnSpan == 0) normalized = false;

    while (column >= cells.size()) {
      cells.add(cell);
    }
    return this;
  }

  void fillMissingColumns(Integer minColumn, int maxColumns) {
    int columns = getSpannedColumns();
    if (columns < maxColumns) {
      int columnIndex = minColumn == null ? cells.size() : minColumn;
      int count = maxColumns - columns;

      if (minColumn == null || minColumn >= columns) {
        columnIndex = cells.size();
      }

      TableCell empty = defaultCell();
      TableCell prevCell = columnIndex > 0 ? cells.get(columnIndex - 1) : empty;

      while (count-- > 0) {
        // need to change its text to previous cell's end
        int endOffset = prevCell.getEndOffset();
        // diagnostic/3095, text is not the right source for the sequence if closeMarker is not
        // empty
        empty =
            empty.withText(
                PrefixedSubSequence.prefixOf(
                    " ", prevCell.getLastSegment().getBaseSequence(), endOffset, endOffset));

        cells.add(Math.min(columnIndex, cells.size()), empty);
        prevCell = empty;
        columnIndex++;
      }
    }
  }

  void set(int column, TableCell cell) {
    expandTo(column, null);
    cells.set(column, cell);
  }

  boolean isEmptyColumn(int column) {
    int index = indexOf(column).index;
    return index >= cells.size() || cells.get(index).text.isBlank();
  }

  public boolean isEmpty() {
    for (TableCell cell : cells) {
      if (cell != null && !cell.text.isBlank()) {
        return false;
      }
    }
    return true;
  }

  public MarkdownTable.IndexSpanOffset indexOf(int column) {
    return indexOfOrNull(column);
  }

  private MarkdownTable.IndexSpanOffset indexOfOrNull(Integer column) {
    if (column == null) {
      return null;
    }

    int remainingColumns = column;
    int index = 0;

    for (TableCell cell : cells) {
      if (cell.columnSpan > remainingColumns) {
        return new MarkdownTable.IndexSpanOffset(index, remainingColumns);
      }

      remainingColumns -= cell.columnSpan;

      if (cell.columnSpan > 0) index++;
    }

    return new MarkdownTable.IndexSpanOffset(index, 0);
  }

  void normalizeIfNeeded() {
    if (!normalized) {
      normalize();
    }
  }

  void normalize() {
    int column = 0;
    while (column < cells.size()) {
      TableCell cell = cells.get(column);
      if (cell == null || cell == TableCell.NULL) {
        cells.remove(column);
      } else {
        column++;
      }
    }
    normalized = true;
  }

  private CharSequence dumpCells() {
    StringBuilder sb = new StringBuilder();
    for (TableCell cell : cells) {
      sb.append("    ").append(cell.toString()).append("\n");
    }
    return sb;
  }

  @Override
  public String toString() {
    // NOTE: show not simple name but name of container class if any
    return this.getClass().getName().substring(getClass().getPackage().getName().length() + 1)
        + "{"
        + " beforeOffset="
        + beforeOffset
        + ", afterOffset="
        + afterOffset
        + ", normalized="
        + normalized
        + ", cells=[\n"
        + dumpCells()
        + "    ]\n"
        + "  }";
  }
}
