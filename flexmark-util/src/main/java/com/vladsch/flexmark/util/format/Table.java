package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.Ref;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Table {

    public final TableSection heading;
    public final TableSection separator;
    public final TableSection body;
    public final TableFormatOptions options;
    private BasedSequence captionOpen;
    private BasedSequence caption;
    private BasedSequence captionClose;
    private boolean isHeading;
    private boolean isSeparator;

    public CellAlignment[] alignments;
    public int[] columnWidths;

    public Table(DataHolder options) {
        this(new TableFormatOptions(options));
    }

    public Table(TableFormatOptions options) {
        heading = new TableSection();
        separator = new TableSection();
        body = new TableSection();
        isHeading = true;
        isSeparator = false;
        this.options = options;
    }

    public boolean isHeading() {
        return isHeading;
    }

    public void setHeading(final boolean heading) {
        isHeading = heading;
    }

    public boolean isSeparator() {
        return isSeparator;
    }

    public BasedSequence getCaptionOpen() {
        return captionOpen;
    }

    public BasedSequence getCaption() {
        return caption;
    }

    public void setCaption(final CharSequence caption) {
        this.caption = BasedSequenceImpl.of(caption);
    }

    public void setCaption(final CharSequence captionOpen, final CharSequence caption, final CharSequence captionClose) {
        this.captionOpen = BasedSequenceImpl.of(captionOpen);
        this.caption = BasedSequenceImpl.of(caption);
        this.captionClose = BasedSequenceImpl.of(captionClose);
    }

    public BasedSequence getCaptionClose() {
        return captionClose;
    }

    public void setSeparator(final boolean separator) {
        isSeparator = separator;
    }

    public void nextRow() {
        if (isSeparator) throw new IllegalStateException("Only one separator row allowed");
        if (isHeading) {
            heading.nextRow();
        } else {
            body.nextRow();
        }
    }

    public int getHeadingRows() {
        return heading.rows.size();
    }

    public int getBodyRows() {
        return body.rows.size();
    }

    public int getHeadingColumns() {
        return heading.getMaxColumns();
    }

    public int getSeparatorColumns() {
        return body.getMaxColumns();
    }

    public int getBodyColumns() {
        return body.getMaxColumns();
    }

    public void addCell(TableCell cell) {
        TableSection tableSection = isSeparator ? separator : isHeading ? heading : body;

        if (isSeparator && (cell.columnSpan != 1 || cell.rowSpan != 1))
            throw new IllegalStateException("Separator columns cannot span rows/columns");

        final TableRow currentRow = tableSection.get(tableSection.row);

        // skip cells that are already set
        while (tableSection.column < currentRow.cells.size() && currentRow.cells.get(tableSection.column) != null) tableSection.column++;

        int rowSpan = 0;
        while (rowSpan < cell.rowSpan) {
            tableSection.get(tableSection.row + rowSpan).set(tableSection.column, cell);

            // set the rest to NULL cells up to null column
            int colSpan = 1;
            while (colSpan < cell.columnSpan) {
                tableSection.expandTo(tableSection.row + rowSpan, tableSection.column + colSpan);
                if (tableSection.get(tableSection.row + rowSpan).cells.get(tableSection.column + colSpan) != null) break;

                tableSection.rows.get(tableSection.row + rowSpan).set(tableSection.column + colSpan, TableCell.NULL);
                colSpan++;
            }
            rowSpan++;
        }

        tableSection.column += cell.columnSpan;
    }

    public int getMinColumns() {
        int headingColumns = heading.getMinColumns();
        int separatorColumns = separator.getMinColumns();
        int bodyColumns = body.getMinColumns();
        return Utils.min(headingColumns, separatorColumns, bodyColumns);
    }

    public int getMaxColumns() {
        int headingColumns = heading.getMaxColumns();
        int separatorColumns = separator.getMaxColumns();
        int bodyColumns = body.getMaxColumns();
        return Utils.max(headingColumns, separatorColumns, bodyColumns);
    }

    public BasedSequence cellText(CharSequence chars, int width, CellAlignment alignment, Ref<Integer> accumulatedDelta) {
        BasedSequence text = BasedSequenceImpl.of(chars);

        final int length = options.charWidthProvider.charWidth(text);
        if (length < width && options.adjustColumnWidth) {
            if (!options.applyColumnAlignment || alignment == null || alignment == CellAlignment.NONE) alignment = CellAlignment.LEFT;
            int diff = width - length;
            int spaceCount = diff / options.spaceWidth;
            if (accumulatedDelta.value * 2 >= options.spaceWidth) {
                spaceCount++;
                accumulatedDelta.value -= options.spaceWidth;
            }

            switch (alignment) {
                case LEFT:
                    text = text.append(PrefixedSubSequence.repeatOf(" ", spaceCount, text.subSequence(0, 0)));
                    break;
                case RIGHT:
                    text = PrefixedSubSequence.repeatOf(" ", spaceCount, text);
                    break;
                case CENTER:
                    int count = spaceCount / 2;
                    text = PrefixedSubSequence.repeatOf(" ", count, text).append(PrefixedSubSequence.repeatOf(" ", spaceCount - count, text.subSequence(0, 0)));
                    break;
            }
        }

        return text;
    }

    public int spanWidth(int col, int colSpan) {
        if (colSpan > 1) {
            int width = 0;
            for (int i = 0; i < colSpan; i++) {
                width += columnWidths[i + col];
            }
            return width;
        } else {
            return columnWidths[col];
        }
    }

    public int spanFixedWidth(BitSet unfixedColumns, int col, int colSpan) {
        if (colSpan > 1) {
            int width = 0;
            for (int i = 0; i < colSpan; i++) {
                if (!unfixedColumns.get(i)) {
                    width += columnWidths[i + col];
                }
            }
            return width;
        } else {
            return columnWidths[col];
        }
    }

    private static class ColumnSpan {
        final int startColumn;
        final int columnSpan;
        final int width;
        int additionalWidth;

        public ColumnSpan(final int startColumn, final int columnSpan, final int width) {
            this.startColumn = startColumn;
            this.columnSpan = columnSpan;
            this.width = width;
            this.additionalWidth = 0;
        }
    }

    private CellAlignment adjustCellAlignment(CellAlignment alignment) {
        switch (options.leftAlignMarker) {
            case ADD:
                if (alignment == null || alignment == CellAlignment.NONE) alignment = CellAlignment.LEFT;
                break;
            case REMOVE:
                if (alignment == CellAlignment.LEFT) alignment = CellAlignment.NONE;
                break;

            default:
                break;
        }
        return alignment;
    }

    public void finalizeTable() {
        // remove null cells
        heading.cleanup();
        body.cleanup();

        if (options.fillMissingColumns) {
            int minColumns = getMinColumns();
            int maxColumns = getMaxColumns();
            if (minColumns < maxColumns) {
                // add empty cells to rows that have less
                TableCell empty = new TableCell("", 1, 1);
                for (TableRow row : heading.rows) {
                    row.expandTo(maxColumns - 1, empty);
                }
                for (TableRow row : body.rows) {
                    row.expandTo(maxColumns - 1, empty);
                }
            }
        }

        int sepColumns = getMaxColumns();
        alignments = new CellAlignment[sepColumns];
        columnWidths = new int[sepColumns];
        BitSet spanAlignment = new BitSet(sepColumns);
        List<ColumnSpan> columnSpans = new ArrayList<>();
        Ref<Integer> delta = new Ref<>(0);

        if (separator.rows.size() > 0) {
            TableRow row = separator.rows.get(0);
            int j = 0;
            int jSpan = 0;
            delta.value = 0;
            for (TableCell cell : row.cells) {
                // set alignment if not already set or was set by a span and this column is not a span
                if ((alignments[jSpan] == null || cell.columnSpan == 1 && spanAlignment.get(jSpan)) && cell.alignment != CellAlignment.NONE) {
                    alignments[jSpan] = cell.alignment;
                    if (cell.columnSpan > 1) spanAlignment.set(jSpan);
                }
                j++;
                jSpan += cell.columnSpan;
            }
        }

        if (heading.rows.size() > 0) {
            int i = 0;
            for (TableRow row : heading.rows) {
                int j = 0;
                int jSpan = 0;
                delta.value = 0;
                for (TableCell cell : row.cells) {
                    // set alignment if not already set or was set by a span and this column is not a span
                    if ((alignments[jSpan] == null || cell.columnSpan == 1 && spanAlignment.get(jSpan)) && cell.alignment != CellAlignment.NONE) {
                        alignments[jSpan] = cell.alignment;
                        if (cell.columnSpan > 1) spanAlignment.set(jSpan);
                    }

                    BasedSequence cellText = cellText(cell.text, 0, null, delta);
                    int width = options.charWidthProvider.charWidth(cellText) + options.spacePad + options.pipeWidth * cell.columnSpan;
                    if (cell.columnSpan > 1) {
                        columnSpans.add(new ColumnSpan(j, cell.columnSpan, width));
                    } else {
                        if (columnWidths[jSpan] < width) columnWidths[jSpan] = width;
                    }

                    j++;
                    jSpan += cell.columnSpan;
                }
                i++;
            }
        }

        if (body.rows.size() > 0) {
            int i = 0;
            delta.value = 0;
            for (TableRow row : body.rows) {
                int j = 0;
                int jSpan = 0;
                for (TableCell cell : row.cells) {
                    BasedSequence cellText = cellText(cell.text, 0, null, delta);
                    int width = options.charWidthProvider.charWidth(cellText) + options.spacePad + options.pipeWidth * cell.columnSpan;
                    if (cell.columnSpan > 1) {
                        columnSpans.add(new ColumnSpan(jSpan, cell.columnSpan, width));
                    } else {
                        if (columnWidths[jSpan] < width) columnWidths[jSpan] = width;
                    }

                    j++;
                    jSpan += cell.columnSpan;
                }
                i++;
            }
        }

        // add separator column widths to the calculation
        if (separator.rows.size() == 0 || body.rows.size() > 0 || heading.rows.size() > 0) {
            int j = 0;
            delta.value = 0;
            for (CellAlignment alignment : alignments) {
                CellAlignment alignment1 = adjustCellAlignment(alignment);
                int colonCount = alignment1 == CellAlignment.LEFT || alignment1 == CellAlignment.RIGHT ? 1 : alignment1 == CellAlignment.CENTER ? 2 : 0;
                int dashCount = 0;
                int dashesOnly = Utils.minLimit(dashCount, options.minSeparatorColumnWidth - colonCount, options.minSeparatorDashes);
                if (dashCount < dashesOnly) dashCount = dashesOnly;
                int width = dashCount * options.dashWidth + colonCount * options.colonWidth + options.pipeWidth;
                if (columnWidths[j] < width) columnWidths[j] = width;
                j++;
            }
        } else {
            // keep as is
            int j = 0;
            delta.value = 0;
            for (TableCell cell : separator.rows.get(0).cells) {
                CellAlignment alignment = adjustCellAlignment(cell.alignment);
                int colonCount = alignment == CellAlignment.LEFT || alignment == CellAlignment.RIGHT ? 1 : alignment == CellAlignment.CENTER ? 2 : 0;
                int dashCount = cell.text.trim(":").length();
                int dashesOnly = Utils.minLimit(dashCount, options.minSeparatorColumnWidth - colonCount, options.minSeparatorDashes);
                if (dashCount < dashesOnly) dashCount = dashesOnly;
                int width = dashCount * options.dashWidth + colonCount * options.colonWidth + options.pipeWidth;
                if (columnWidths[j] < width) columnWidths[j] = width;
                j++;
            }
        }

        if (!columnSpans.isEmpty()) {
            // now need to distribute extra width from spans to contained columns
            int[] additionalWidths = new int[sepColumns];
            BitSet unfixedColumns = new BitSet(sepColumns);
            List<ColumnSpan> newColumnSpans = new ArrayList<>(columnSpans.size());

            for (ColumnSpan columnSpan : columnSpans) {
                int spanWidth = spanWidth(columnSpan.startColumn, columnSpan.columnSpan);
                if (spanWidth < columnSpan.width) {
                    // not all fits, need to distribute the remainder
                    unfixedColumns.set(columnSpan.startColumn, columnSpan.startColumn + columnSpan.columnSpan);
                    newColumnSpans.add(columnSpan);
                }
            }

            // we now distribute additional width equally between columns that are spanned to unfixed columns
            while (!newColumnSpans.isEmpty()) {
                columnSpans = newColumnSpans;

                BitSet fixedColumns = new BitSet(sepColumns);
                newColumnSpans.clear();

                // remove spans that already fit into fixed columns
                for (ColumnSpan columnSpan : columnSpans) {
                    int spanWidth = spanWidth(columnSpan.startColumn, columnSpan.columnSpan);
                    int fixedWidth = spanFixedWidth(unfixedColumns, columnSpan.startColumn, columnSpan.columnSpan);

                    if (spanWidth <= fixedWidth) {
                        fixedColumns.set(columnSpan.startColumn, columnSpan.startColumn + columnSpan.columnSpan);
                    } else {
                        newColumnSpans.add(columnSpan);
                    }
                }

                // reset fixed columns
                unfixedColumns.andNot(fixedColumns);
                columnSpans = newColumnSpans;
                newColumnSpans.clear();

                for (ColumnSpan columnSpan : columnSpans) {
                    int spanWidth = spanWidth(columnSpan.startColumn, columnSpan.columnSpan);
                    int fixedWidth = spanFixedWidth(unfixedColumns, columnSpan.startColumn, columnSpan.columnSpan);

                    if (spanWidth > fixedWidth) {
                        // not all fits, need to distribute the remainder to unfixed columns
                        int distributeWidth = spanWidth - fixedWidth;
                        int unfixedColumnCount = unfixedColumns.get(columnSpan.startColumn, columnSpan.startColumn + columnSpan.columnSpan).cardinality();
                        int perSpanWidth = distributeWidth / unfixedColumnCount;
                        int extraWidth = distributeWidth - perSpanWidth * unfixedColumnCount;

                        for (int i = 0; i < columnSpan.columnSpan; i++) {
                            if (unfixedColumns.get(columnSpan.startColumn + i)) {
                                columnWidths[columnSpan.startColumn + i] += perSpanWidth;
                                if (extraWidth > 0) {
                                    columnWidths[columnSpan.startColumn + i]++;
                                    extraWidth--;
                                }
                            }
                        }
                        newColumnSpans.add(columnSpan);
                    }
                }
            }
        }
    }

    public void appendTable(final FormattingAppendable out) {
        // we will prepare the separator based on max columns
        int formatterOptions = out.getOptions();
        out.setOptions(formatterOptions & ~FormattingAppendable.COLLAPSE_WHITESPACE);
        Ref<Integer> delta = new Ref<>(0);

        if (heading.rows.size() > 0) {
            for (TableRow row : heading.rows) {
                int j = 0;
                int jSpan = 0;
                delta.value = 0;
                for (TableCell cell : row.cells) {
                    if (j == 0) {
                        if (options.leadTrailPipes) {
                            out.append('|');
                            if (options.spaceAroundPipes) out.append(' ');
                        }
                    } else {
                        if (options.spaceAroundPipes) out.append(' ');
                    }

                    CellAlignment cellAlignment = cell.alignment != CellAlignment.NONE ? cell.alignment : alignments[jSpan];
                    out.append(cellText(cell.text, spanWidth(jSpan, cell.columnSpan) - options.spacePad - options.pipeWidth * cell.columnSpan, cellAlignment, delta));

                    j++;
                    jSpan += cell.columnSpan;

                    if (j < alignments.length) {
                        if (options.spaceAroundPipes) out.append(' ');
                        out.repeat('|', cell.columnSpan);
                    } else if (options.leadTrailPipes) {
                        if (options.spaceAroundPipes) out.append(' ');
                        out.repeat('|', cell.columnSpan);
                    } else {
                        if (options.spaceAroundPipes) out.append(' ');
                        out.repeat('|', cell.columnSpan - 1);
                    }
                }

                if (j > 0) out.line();
            }
        }

        {
            int j = 0;
            delta.value = 0;
            for (CellAlignment alignment : alignments) {
                CellAlignment alignment1 = adjustCellAlignment(alignment);
                int colonCount = alignment1 == CellAlignment.LEFT || alignment1 == CellAlignment.RIGHT ? 1 : alignment1 == CellAlignment.CENTER ? 2 : 0;
                int dashCount = (columnWidths[j] - colonCount * options.colonWidth - options.pipeWidth) / options.dashWidth;
                int dashesOnly = Utils.minLimit(dashCount, options.minSeparatorColumnWidth - colonCount, options.minSeparatorDashes);
                if (dashCount < dashesOnly) dashCount = dashesOnly;

                if (delta.value * 2 >= options.dashWidth) {
                    dashCount++;
                    delta.value -= options.dashWidth;
                }

                if (options.leadTrailPipes && j == 0) out.append('|');
                if (alignment1 == CellAlignment.LEFT || alignment1 == CellAlignment.CENTER) out.append(':');
                out.repeat('-', dashCount);
                if (alignment1 == CellAlignment.RIGHT || alignment1 == CellAlignment.CENTER) out.append(':');
                j++;
                if (options.leadTrailPipes || j < alignments.length) out.append('|');
            }
            out.line();
        }

        if (body.rows.size() > 0) {
            for (TableRow row : body.rows) {
                int j = 0;
                int jSpan = 0;
                delta.value = 0;
                for (TableCell cell : row.cells) {
                    if (j == 0) {
                        if (options.leadTrailPipes) {
                            out.append('|');
                            if (options.spaceAroundPipes) out.append(' ');
                        }
                    } else {
                        if (options.spaceAroundPipes) out.append(' ');
                    }

                    out.append(cellText(cell.text, spanWidth(jSpan, cell.columnSpan) - options.spacePad - options.pipeWidth * cell.columnSpan, alignments[jSpan], delta));

                    j++;
                    jSpan += cell.columnSpan;

                    if (j < alignments.length) {
                        if (options.spaceAroundPipes) out.append(' ');
                        out.repeat('|', cell.columnSpan);
                    } else if (options.leadTrailPipes) {
                        if (options.spaceAroundPipes) out.append(' ');
                        out.repeat('|', cell.columnSpan);
                    } else {
                        if (options.spaceAroundPipes) out.append(' ');
                        out.repeat('|', cell.columnSpan - 1);
                    }
                }

                if (j > 0) out.line();
            }
        }

        out.setOptions(formatterOptions);

        if (caption != null && !options.removeCaption) {
            out.line().append('[').append(caption).append(']').line();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class TableCell {
        public final static TableCell NULL = new TableCell(SubSequence.NULL, " ", BasedSequence.NULL, 1, 0, CellAlignment.NONE);

        public final BasedSequence openMarker;
        public final BasedSequence text;
        public final BasedSequence closeMarker;
        public final int columnSpan;
        public final int rowSpan;
        public final CellAlignment alignment;

        public TableCell(final CharSequence text, final int rowSpan, final int columnSpan) {
            this(BasedSequence.NULL, text, BasedSequence.NULL, rowSpan, columnSpan, CellAlignment.NONE);
        }

        public TableCell(final CharSequence text, final int rowSpan, final int columnSpan, final CellAlignment alignment) {
            this(BasedSequence.NULL, text, BasedSequence.NULL, rowSpan, columnSpan, alignment);
        }

        public TableCell(CharSequence openMarker, final CharSequence text, CharSequence closeMarker, final int rowSpan, final int columnSpan) {
            this(openMarker, text, closeMarker, rowSpan, columnSpan, CellAlignment.NONE);
        }

        public TableCell(
                final CharSequence openMarker,
                final CharSequence text,
                final CharSequence closeMarker,
                final int rowSpan,
                final int columnSpan,
                final CellAlignment alignment
        ) {
            BasedSequence chars = BasedSequenceImpl.of(text);
            this.openMarker = BasedSequenceImpl.of(openMarker);
            this.text = chars.isEmpty() ? BasedSequence.SPACE : chars;
            this.closeMarker = BasedSequenceImpl.of(closeMarker);
            this.rowSpan = rowSpan;
            this.columnSpan = columnSpan;
            this.alignment = alignment != null ? alignment : CellAlignment.NONE;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class TableRow {
        public final List<TableCell> cells;

        TableRow() {
            cells = new ArrayList<>();
        }

        public int getColumns() {
            return cells.size();
        }

        public int getSpannedColumns() {
            int columns = 0;
            for (TableCell cell : cells) {
                if (cell == null) continue;
                columns += cell.columnSpan;
            }
            return columns;
        }

        public TableRow expandTo(int column) {
            return expandTo(column, null);
        }

        public TableRow expandTo(int column, TableCell cell) {
            while (column >= cells.size()) {
                cells.add(cell);
            }
            return this;
        }

        public void set(int column, TableCell cell) {
            expandTo(column, null);
            cells.set(column, cell);
        }

        public void cleanup() {
            int column = 0;
            while (column < cells.size()) {
                final TableCell cell = cells.get(column);
                if (cell == null || cell == TableCell.NULL) cells.remove(column);
                else column++;
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class TableSection {
        public final List<TableRow> rows = new ArrayList<>();
        public int row;
        public int column;

        public TableSection() {
            row = 0;
            column = 0;
        }

        public void nextRow() {
            row++;
            column = 0;
        }

        public void setCell(int row, int column, TableCell cell) {
            expandTo(row).set(column, cell);
        }

        public void cleanup() {
            for (TableRow row : rows) {
                row.cleanup();
            }
        }

        public TableRow expandTo(int row) {
            return expandTo(row, null);
        }

        public TableRow expandTo(int row, TableCell cell) {
            while (row >= rows.size()) {
                TableRow tableRow = new TableRow();
                rows.add(tableRow);
            }
            return rows.get(row);
        }

        public TableRow expandTo(int row, int column) {
            return expandTo(row, column, null);
        }

        public TableRow expandTo(int row, int column, TableCell cell) {
            while (row >= rows.size()) {
                TableRow tableRow = new TableRow();
                tableRow.expandTo(column, cell);
                rows.add(tableRow);
            }
            return rows.get(row).expandTo(column);
        }

        public TableRow get(int row) {
            return expandTo(row, null);
        }

        public int getMaxColumns() {
            int columns = 0;
            for (TableRow row : rows) {
                int spans = row.getSpannedColumns();
                if (columns < spans) columns = spans;
            }
            return columns;
        }

        public int getMinColumns() {
            int columns = 0;
            for (TableRow row : rows) {
                int spans = row.getSpannedColumns();
                if (columns > spans || columns == 0) columns = spans;
            }
            return columns;
        }
    }
}
