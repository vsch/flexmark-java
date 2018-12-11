package com.vladsch.flexmark.util.format;

import java.util.ArrayList;
import java.util.List;

import static com.vladsch.flexmark.util.Utils.maxLimit;
import static com.vladsch.flexmark.util.Utils.minLimit;

@SuppressWarnings("WeakerAccess")
public class TableRow {
    protected final List<TableCell> cells;
    protected int beforeOffset = TableCell.NOT_TRACKED;
    protected int afterOffset = TableCell.NOT_TRACKED;
    private boolean normalized = true;

    public TableRow() {
        cells = new ArrayList<TableCell>();
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
            if (cell == null) continue;
            columns += cell.columnSpan;
        }
        return columns;
    }

    public int getBeforeOffset() {
        return beforeOffset;
    }

    public void setBeforeOffset(final int beforeOffset) {
        this.beforeOffset = beforeOffset;
    }

    public int getAfterOffset() {
        return afterOffset;
    }

    public void setAfterOffset(final int afterOffset) {
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
     * NOTE: inserting into a cell span has the effect of expanding the span if the cell text is blank or insert count > 1
     * or splitting the span if it is not blank and count == 1
     *
     * @param column column index before which to insert
     * @param count  number of columns to insert
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
                TableCell cell = cells.get(index);
                //if (cell.columnSpan == 0) throw new IllegalStateException("TableRow.insertColumns must be called only after 0-span dummy columns have been removed by calling normalize() on table, section or row");
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

            //if (cell.columnSpan == 0) throw new IllegalStateException("TableRow.deleteColumns must be called only after 0-span dummy columns have been removed by calling normalize() on table, section or row");

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

        if (fromColumn != toColumn && fromColumn < maxColumn && toColumn < maxColumn) {
            MarkdownTable.IndexSpanOffset fromIndexSpan = indexOf(fromColumn);
            int fromIndex = fromIndexSpan.index;
            int fromSpanOffset = fromIndexSpan.spanOffset;
            TableCell cell = cells.get(fromIndex).withColumnSpan(1);

            MarkdownTable.IndexSpanOffset toIndexSpan = indexOf(toColumn);
            int toIndex = toIndexSpan.index;
            int toSpanOffset = toIndexSpan.spanOffset;

            if (toIndex == fromIndex) {
                // moving within a span, do nothing
                int tmp = 0;
            } else {
                if (fromSpanOffset > 0) {
                    // from inside the span is same as a blank column
                    insertColumns(toColumn + (fromColumn <= toColumn ? 1 : 0), 1, defaultCell());
                    deleteColumns(fromColumn + (toColumn <= fromColumn ? 1 : 0), 1);
                } else {
                    insertColumns(toColumn + (fromColumn <= toColumn ? 1 : 0), 1, cell.withColumnSpan(1));
                    deleteColumns(fromColumn + (toColumn <= fromColumn ? 1 : 0), 1);
                }
            }
        }
    }

/*
    public TableCell[] denormalizedColumns() {
        TableCell[] explicitColumns = new TableCell[getTotalColumns()];

        int explicitIndex = 0;
        for (TableCell cell : cells) {
            if (cell == null || cell.columnSpan == 0) continue;
            explicitColumns[explicitIndex] = cell;
            explicitIndex += cell.columnSpan;
        }
        return explicitColumns;
    }

    public void addDenormalizedColumns(TableCell[] explicitColumns) {
        TableCell lastCell = null;

        for (int i = 0; i < explicitColumns.length; i++) {
            TableCell cell = explicitColumns[i];
            if (cell == null || cell.columnSpan == 0) {
                if (lastCell == null) {
                    lastCell = new TableCell("", 0, 1);
                } else {
                    lastCell = lastCell.withColumnSpan(lastCell.columnSpan + 1);
                }
            } else {
                if (lastCell != null) cells.add(lastCell);
                cell.withColumnSpan(1);
            }
        }

        if (lastCell != null) {
            cells.add(lastCell);
        }
    }
*/

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
    
    void fillMissingColumns(Integer minColumn, int maxColumns, TableCell empty) {
        int columns = getSpannedColumns();
        if (columns < maxColumns) {
            if (minColumn == null || minColumn >= columns) {
                // just add at the end
                int count = cells.size() - 1 + maxColumns - columns;
                expandTo(count, empty);
            } else {
                // insert at minColumn
                int i = maxColumns - columns;
                while (i-- > 0) {
                    cells.add(minColumn, empty);
                }
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
                int spanOffset = remainingColumns;
                return new MarkdownTable.IndexSpanOffset(index, spanOffset);
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
            final TableCell cell = cells.get(column);
            if (cell == null || cell == TableCell.NULL) cells.remove(column);
            else column++;
        }
        normalized = true;
    }
}
