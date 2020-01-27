package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;

import java.util.ArrayList;
import java.util.List;

import static com.vladsch.flexmark.util.format.TableCellManipulator.BREAK;
import static com.vladsch.flexmark.util.misc.Utils.maxLimit;
import static com.vladsch.flexmark.util.misc.Utils.minLimit;

@SuppressWarnings("WeakerAccess")
public class TableRow {
    protected final List<TableCell> cells;
    protected int beforeOffset = TableCell.NOT_TRACKED;
    protected int afterOffset = TableCell.NOT_TRACKED;
    private boolean normalized = true;

    public TableRow() {
        cells = new ArrayList<>();
    }

    public List<TableCell> getCells() {
        return cells;
    }

    public void forAllCells(TableCellConsumer consumer) {
        forAllCells(0, Integer.MAX_VALUE, consumer);
    }

    public void forAllCells(int startIndex, TableCellConsumer consumer) {
        forAllCells(startIndex, Integer.MAX_VALUE, consumer);
    }

    public void forAllCells(int startIndex, int count, TableCellConsumer consumer) {
        forAllCells(startIndex, count, (cell, cellIndex, cellColumn, allCellIndex) -> {
            consumer.accept(cell, cellIndex, cellColumn);
            return 0;
        });
    }

    public void forAllCells(TableCellManipulator manipulator) {
        forAllCells(0, Integer.MAX_VALUE, manipulator);
    }

    public void forAllCells(int startIndex, TableCellManipulator manipulator) {
        forAllCells(startIndex, Integer.MAX_VALUE, manipulator);
    }

    public void forAllCells(int startIndex, int count, TableCellManipulator manipulator) {
        int iMax = cells.size();
        if (startIndex < iMax && count > 0) {
            int column = 0;
            int remaining = count;
            int allCellsIndex = 0;

            for (int i = 0; i < iMax; ) {
                TableCell cell = cells.get(i);

                if (i >= startIndex) {
                    int result = manipulator.apply(cell, i, column, allCellsIndex);

                    if (result == BREAK) return;

                    if (result < 0) {
                        allCellsIndex -= result; // adjust for deleted cells
                        remaining += result;
                        iMax += result;
                    } else {
                        i += result + 1;
                        column += cell.columnSpan;
                        remaining--;
                        iMax += result;
                    }

                    allCellsIndex++;

                    if (remaining <= 0) break;
                } else {
                    i++;
                    allCellsIndex++;
                    column += cell.columnSpan;
                }
            }
        }
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
            if (cell == null) continue;
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

    public int columnOf(int index) {
        return columnOfOrNull(index);
    }

    public Integer columnOfOrNull(Integer index) {
        if (index == null) return null;

        int columns = 0;

        int iMax = maxLimit(index, cells.size());
        for (int i = 0; i < iMax; i++) {
            TableCell cell = cells.get(i);
            columns += cell.columnSpan;
        }

        return columns;
    }

    public void appendColumns(int count) {
        appendColumns(count, null);
    }

    public void appendColumns(int count, TableCell tableCell) {
        if (tableCell == null || tableCell.columnSpan == 0) tableCell = defaultCell();

        for (int i = 0; i < count; i++) {
            // add empty column
            cells.add(cells.size(), tableCell);
        }
    }

    public TableCell defaultCell() {
        return new TableCell(" ", 1, 1);
    }

    public void addColumn(int index) {
        cells.add(index, defaultCell());
    }

    /**
     * @param column column index before which to insert
     * @param count  number of columns to insert
     */
    public void insertColumns(int column, int count) {
        insertColumns(column, count, null);
    }

    /**
     * NOTE: inserting into a cell span has the effect of expanding the span if the cell text is blank or insert count &gt; 1
     * or splitting the span if it is not blank and count == 1
     *
     * @param column    column index before which to insert
     * @param count     number of columns to insert
     * @param tableCell table cell to insert, null for default
     */
    public void insertColumns(int column, int count, TableCell tableCell) {
        if (count <= 0 || column < 0) return;

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

                //if (cell.columnSpan == 0) throw new IllegalStateException("TableRow.insertColumns must be called only after 0-span dummy columns have been removed by calling normalize() on table, section or row");
                if (tableCell.text.isBlank() || count > 1) {
                    // expand span
                    cells.remove(index);
                    cells.add(index, cell.withColumnSpan(cell.columnSpan + count));
                } else {
                    // split span into before inserted and after
                    cells.remove(index);
                    cells.add(index, cell.withColumnSpan(spanOffset));
                    cells.add(index + 1, tableCell.withColumnSpan(minLimit(1, cell.columnSpan - spanOffset + 1)));
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
     * @param count  number of columns to insert
     */
    public void deleteColumns(int column, int count) {
        if (count <= 0 || column < 0) return;

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
                } else {
                    // reinsert with reduced span
                    cells.add(index, cell.withColumnSpan(spanOffset));
                    index++;
                }
            } else if (cell.columnSpan - spanOffset > remaining) {
                // reinsert with reduced span and empty text
                cells.add(index, defaultCell().withColumnSpan(cell.columnSpan - remaining));
                break;
            }

            remaining -= cell.columnSpan - spanOffset;
            spanOffset = 0;
        }
    }

    public void moveColumn(int fromColumn, int toColumn) {
        if (fromColumn < 0 || toColumn < 0) return;

        normalizeIfNeeded();

        int maxColumn = getTotalColumns();

        if (fromColumn >= maxColumn) return;
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
//            } else {
//                // moving within a span, do nothing
            }
        }
    }

    public TableRow expandTo(int column) {
        return expandTo(column, TableCell.NULL);
    }

    public TableRow expandTo(int column, TableCell cell) {
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
                // diagnostic/3095, text is not the right source for the sequence if closeMarker is not empty
                empty = empty.withText(PrefixedSubSequence.prefixOf(" ", prevCell.getLastSegment().getBaseSequence(), endOffset, endOffset));

                cells.add(Math.min(columnIndex, cells.size()), empty);
                prevCell = empty;
                columnIndex++;
            }
        }
    }

    public void set(int column, TableCell cell) {
        expandTo(column, null);
        cells.set(column, cell);
    }

    public boolean isEmptyColumn(int column) {
        int index = indexOf(column).index;
        return index >= cells.size() || cells.get(index).text.isBlank();
    }

    public boolean isEmpty() {
        for (TableCell cell : cells) {
            if (cell != null && !cell.text.isBlank()) return false;
        }
        return true;
    }

    public MarkdownTable.IndexSpanOffset indexOf(int column) {
        return indexOfOrNull(column);
    }

    public MarkdownTable.IndexSpanOffset indexOfOrNull(Integer column) {
        if (column == null) return null;

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

    public void normalizeIfNeeded() {
        if (!normalized) {
            normalize();
        }
    }

    public void normalize() {
        int column = 0;
        while (column < cells.size()) {
            TableCell cell = cells.get(column);
            if (cell == null || cell == TableCell.NULL) cells.remove(column);
            else column++;
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
        return this.getClass().getName().substring(getClass().getPackage().getName().length() + 1) + "{" +
                " beforeOffset=" + beforeOffset +
                ", afterOffset=" + afterOffset +
                ", normalized=" + normalized +
                ", cells=[\n" + dumpCells() + "    ]\n" +
                "  }";
    }
}
