package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.Ref;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.SubSequence;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import static com.vladsch.flexmark.util.Utils.*;

@SuppressWarnings("WeakerAccess")
public class Table {

    public final TableSection heading;
    public final TableSection separator;
    public final TableSection body;
    public TableFormatOptions options;
    private BasedSequence captionOpen;
    private BasedSequence caption;
    private BasedSequence captionClose;
    private boolean isHeading;
    private boolean isSeparator;

    // used by finalization and conversion to text
    private CellAlignment[] alignments;
    private int[] columnWidths;

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
        return separator.getMaxColumns();
    }

    public int getBodyColumns() {
        return body.getMaxColumns();
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

    int maxColumnsWithout(boolean withSeparator, int... skipRows) {
        int columns = 0;
        int index = 0;

        for (TableRow row : allRows(withSeparator)) {
            if (!contained(index, skipRows)) columns = max(columns, row.getTotalColumns());
            index++;
        }
        return columns;
    }

    int minColumnsWithout(boolean withSeparator, int... skipRows) {
        int columns = 0;
        int index = 0;

        for (TableRow row : allRows(withSeparator)) {
            if (!contained(index, skipRows)) columns = min(columns, row.getTotalColumns());
            index++;
        }
        return columns;
    }

    /*
        Table Manipulation Helper API
    */
    public List<TableRow> allRows(boolean withSeparator) {
        ArrayList<TableRow> rows = new ArrayList<>(heading.rows.size() + (withSeparator ? separator.rows.size() : 0) + body.rows.size());
        rows.addAll(heading.rows);
        if (withSeparator) rows.addAll(separator.rows);
        rows.addAll(body.rows);
        return rows;
    }

    public int allRowCount(boolean withSeparator) {
        return heading.rows.size() + (withSeparator ? separator.rows.size() : 0) + body.rows.size();
    }

    public interface TableRowManipulator {
        boolean apply(ArrayList<TableRow> rows, int index);
    }

    public void forAllRows(int rowIndex, int count, boolean withSeparator, TableRowManipulator manipulator) {
        int remaining = count;
        TableSection section = null;

        if (rowIndex < heading.rows.size()) {
            section = heading;
        } else if (withSeparator && rowIndex < separator.rows.size()) {
            section = separator;
            rowIndex -= heading.rows.size();
        } else if (rowIndex < body.rows.size()) {
            section = body;
            rowIndex -= (withSeparator ? separator : heading).rows.size();
        }

        do {
            while (rowIndex < section.rows.size() && remaining > 0) {
                if (!manipulator.apply(section.rows, rowIndex)) {
                    break;
                }
                remaining--;
            }

            if (remaining > 0 && section.rows.isEmpty()) {
                if (section == heading) {
                    section = withSeparator ? separator : body;
                }
                if (section == separator && section.rows.isEmpty()) {
                    section = body;
                }
            }
        } while (remaining > 0 && !section.rows.isEmpty());
    }

    public void deleteRows(int rowIndex, int count, boolean withSeparator) {
        forAllRows(rowIndex, count, withSeparator, new TableRowManipulator() {
            @Override
            public boolean apply(final ArrayList<TableRow> rows, final int index) {
                rows.remove(index);
                return true;
            }
        });
    }

    void insertColumns(final int column, final int count, boolean withSeparator) {
        forAllRows(0, allRowCount(true), true, new TableRowManipulator() {
            @Override
            public boolean apply(final ArrayList<TableRow> rows, final int index) {
                rows.get(index).insertColumns(column, count);
                return true;
            }
        });
    }

    void deleteColumns(final int column, final int count, boolean withSeparator) {
        forAllRows(0, allRowCount(withSeparator), withSeparator, new TableRowManipulator() {
            @Override
            public boolean apply(final ArrayList<TableRow> rows, final int index) {
                rows.get(index).deleteColumns(column, count);
                return true;
            }
        });
    }

    void insertRows(final int rowIndex, final int count, boolean withSeparator) {
        final int maxColumns = getMaxColumns();
        forAllRows(rowIndex, 1, withSeparator, new TableRowManipulator() {
            @Override
            public boolean apply(final ArrayList<TableRow> rows, final int index) {
                TableRow emptyRow = new TableRow();
                emptyRow.appendColumns(maxColumns);
                rows.add(rowIndex, emptyRow);
                return true;
            }
        });
    }

    void moveColumn(final int fromColumn, final int toColumn) {
        forAllRows(0, allRowCount(true), true, new TableRowManipulator() {
            @Override
            public boolean apply(final ArrayList<TableRow> rows, final int index) {
                rows.get(index).moveColumn(fromColumn, toColumn);
                return true;
            }
        });
    }

    boolean isEmptyColumn(int column, boolean withSeparator) {
        for (TableRow row : allRows(withSeparator)) {
            if (!row.isEmptyColumn(column)) {
                return false;
            }
        }
        return true;
    }

    boolean isEmptyRow(int rowIndex, boolean withSeparator) {
        return rowIndex < allRowCount(withSeparator) && allRows(withSeparator).get(rowIndex).isEmpty();
    }

    /*
     * Used during table construction by building the table
     * as the AST visiting process (Flexmark or HTML)
     *
     */

    public boolean isHeading() {
        return isHeading;
    }

    public void setHeading(final boolean heading) {
        isHeading = heading;
    }

    public boolean isSeparator() {
        return isSeparator;
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

    /**
     * @param cell cell to add
     */
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
            int columnSpan = 1;
            while (columnSpan < cell.columnSpan) {
                tableSection.expandTo(tableSection.row + rowSpan, tableSection.column + columnSpan);
                if (tableSection.get(tableSection.row + rowSpan).cells.get(tableSection.column + columnSpan) != null) break;

                tableSection.rows.get(tableSection.row + rowSpan).set(tableSection.column + columnSpan, TableCell.NULL);
                columnSpan++;
            }
            rowSpan++;
        }

        tableSection.column += cell.columnSpan;
    }

    public void finalizeTable(String intellijDummyIdentifier) {
        // remove null cells
        boolean isIntellijDummyIdentifier = !intellijDummyIdentifier.isEmpty();
        String colonTrimChars = ":" + intellijDummyIdentifier;

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
        List<ColumnSpan> columnSpans = new ArrayList<ColumnSpan>();
        Ref<Integer> delta = new Ref<Integer>(0);

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

                    BasedSequence cellText = cellText(cell.text, true, 0, null, delta);
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
                    BasedSequence cellText = cellText(cell.text, false, 0, null, delta);
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
                BasedSequence trim = cell.text.trim(colonTrimChars);
                int dashCount = trim.length();
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
            List<ColumnSpan> newColumnSpans = new ArrayList<ColumnSpan>(columnSpans.size());

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

    public void appendTable(final FormattingAppendable out, final String linePrefix, String intellijDummyIdentifier) {
        // we will prepare the separator based on max columns
        Ref<Integer> delta = new Ref<Integer>(0);
        boolean isIntellijDummyIdentifier = !intellijDummyIdentifier.isEmpty();

        int formatterOptions = out.getOptions();
        out.setOptions(formatterOptions & ~FormattingAppendable.COLLAPSE_WHITESPACE);

        if (heading.rows.size() > 0) {
            for (TableRow row : heading.rows) {
                int j = 0;
                int jSpan = 0;
                delta.value = 0;

                out.append(linePrefix);

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
                    out.append(cellText(cell.text, true, spanWidth(jSpan, cell.columnSpan) - options.spacePad - options.pipeWidth * cell.columnSpan, cellAlignment, delta));

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
            out.append(linePrefix);

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

                boolean handled = false;

                if (isIntellijDummyIdentifier) {
                    int intellijPos = -1;
                    BasedSequence cellText = BasedSequence.NULL;

                    if (separator.rows.size() > 0) {
                        List<TableCell> cells = separator.rows.get(0).cells;
                        if (j < cells.size()) {
                            cellText = cells.get(j).text;
                        }
                    }

                    intellijPos = cellText.indexOf(intellijDummyIdentifier.charAt(0));

                    if (intellijPos != -1) {
                        if (options.leadTrailPipes && j == 0) out.append('|');

                        if (alignment1 == CellAlignment.LEFT || alignment1 == CellAlignment.CENTER) out.append(':');

                        if (intellijPos == 0) {
                            out.append(intellijDummyIdentifier);
                            intellijPos = -1;
                            out.repeat('-', dashCount);
                        } else if (intellijPos < dashCount) {
                            out.repeat('-', intellijPos);
                            out.append(intellijDummyIdentifier);
                            out.repeat('-', dashCount - intellijPos);
                            intellijPos = -1;
                        } else {
                            out.repeat('-', dashCount);
                        }

                        if (intellijPos != -1) {
                            // last chance, we put it at the end
                            out.append(intellijDummyIdentifier);
                            intellijPos = -1;
                        }

                        if (alignment1 == CellAlignment.RIGHT || alignment1 == CellAlignment.CENTER) out.append(':');

                        j++;
                        if (options.leadTrailPipes || j < alignments.length) out.append('|');
                        handled = true;
                    }
                }

                if (!handled) {
                    if (options.leadTrailPipes && j == 0) out.append('|');
                    if (alignment1 == CellAlignment.LEFT || alignment1 == CellAlignment.CENTER) out.append(':');

                    if (isIntellijDummyIdentifier) {
                        // more work, we need to insert the id if it was in the separator 
                    } else {

                    }
                    out.repeat('-', dashCount);
                    if (alignment1 == CellAlignment.RIGHT || alignment1 == CellAlignment.CENTER) out.append(':');
                    j++;
                    if (options.leadTrailPipes || j < alignments.length) out.append('|');
                }
            }
            out.line();
        }

        if (body.rows.size() > 0) {
            for (TableRow row : body.rows) {
                out.append(linePrefix);

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

                    out.append(cellText(cell.text, false, spanWidth(jSpan, cell.columnSpan) - options.spacePad - options.pipeWidth * cell.columnSpan, alignments[jSpan], delta));

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

    private BasedSequence cellText(CharSequence chars, final boolean isHeader, int width, CellAlignment alignment, Ref<Integer> accumulatedDelta) {
        BasedSequence text = BasedSequenceImpl.of(chars);

        final int length = options.charWidthProvider.charWidth(text);
        if (length < width && options.adjustColumnWidth) {
            if (!options.applyColumnAlignment || alignment == null || alignment == CellAlignment.NONE) alignment = isHeader ? CellAlignment.CENTER : CellAlignment.LEFT;
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

    private int spanWidth(int col, int columnSpan) {
        if (columnSpan > 1) {
            int width = 0;
            for (int i = 0; i < columnSpan; i++) {
                width += columnWidths[i + col];
            }
            return width;
        } else {
            return columnWidths[col];
        }
    }

    private int spanFixedWidth(BitSet unfixedColumns, int col, int columnSpan) {
        if (columnSpan > 1) {
            int width = 0;
            for (int i = 0; i < columnSpan; i++) {
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
            this.text = chars.isEmpty() ? PrefixedSubSequence.of(" ", this.openMarker.subSequence(this.openMarker.length(), this.openMarker.length())) : chars;
            this.closeMarker = BasedSequenceImpl.of(closeMarker);
            this.rowSpan = rowSpan;
            this.columnSpan = columnSpan;
            this.alignment = alignment != null ? alignment : CellAlignment.NONE;
        }

        TableCell withColumnSpan(int columnSpan) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment); }

        TableCell withText(CharSequence text) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment); }

        TableCell withRowSpan(int rowSpan) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment); }

        TableCell withAlignment(CellAlignment alignment) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment); }
    }
    
    public static class TableSeparatorRow extends TableRow {
        @Override
        protected TableCell defaultCell() {
            return new TableCell("---", 1,1);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class TableRow {
        protected final List<TableCell> cells;

        TableRow() {
            cells = new ArrayList<TableCell>();
        }

        public List<TableCell> getCells() {
            return cells;
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

        int getTotalColumns() {
            return columnOf(cells.size());
        }

        int columnOf(int index) {
            return columnOfOrNull(index);
        }

        Integer columnOfOrNull(Integer index) {
            if (index == null) return null;

            int columns = 0;

            int iMax = maxLimit(index, cells.size());
            for (int i = 0; i < iMax; i++) {
                TableCell cell = cells.get(i);
                columns += cell.columnSpan;
            }

            return columns;
        }

        void appendColumns(int count) {
            for (int i = 0; i < count; i++) {
                // add empty column
                addColumn(cells.size());
            }
        }
        
        protected TableCell defaultCell() {
            return new TableCell(" ", 1, 1);
        }

        private void addColumn(int index) {
            cells.add(index, defaultCell());
        }

        void insertColumns(int column, int count) {
            if (count <= 0) return;

            int totalColumns = this.getTotalColumns();

            if (column >= getTotalColumns()) {
                // append to the end
                appendColumns(count);
            } else {
                // insert in the middle
                int index = indexOf(column);
                int cellColumn = columnOf(index);

                if (cellColumn > column) {
                    // spanning column, we expand its span
                    TableCell cell = cells.get(index);
                    cells.remove(index);
                    cells.add(index, cell.withColumnSpan(cell.columnSpan + count));
                } else {
                    for (int i = 0; i < count; i++) {
                        addColumn(index);
                    }
                }
            }
        }

        void deleteColumns(int column, int count) {
            int remaining = count;
            int index = indexOf(column);
            while (index < cells.size() && remaining > 0) {
                TableCell cell = cells.get(index);
                cells.remove(index);
                ;
                if (cell.columnSpan > remaining) {
                    cells.add(index, cell.withColumnSpan(cell.columnSpan - remaining));
                }
                remaining -= cell.columnSpan;
            }
        }

        private TableCell[] explicitColumns() {
            TableCell[] explicitColumns = new TableCell[getTotalColumns()];

            int explicitIndex = 0;
            for (TableCell cell : cells) {
                explicitColumns[explicitIndex] = cell;
                explicitIndex += cell.columnSpan;
            }
            return explicitColumns;
        }

        private void addExplicitColumns(TableCell[] explicitColumns) {
            TableCell lastCell = null;

            for (int i = 0; i < explicitColumns.length; i++) {
                TableCell cell = explicitColumns[i];
                if (cell == null) {
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

        void moveColumn(int fromColumn, int toColumn) {
            int maxColumn = getTotalColumns();
            if (fromColumn != toColumn && fromColumn < maxColumn && toColumn < maxColumn) {
                TableCell[] explicitColumns = explicitColumns();

                TableCell fromCell = explicitColumns[fromColumn];
                if (toColumn < fromColumn) {
                    // shift in between columns right
                    System.arraycopy(explicitColumns, toColumn, explicitColumns, toColumn + 1, fromColumn - toColumn);
                } else {
                    // shift in between columns left
                    System.arraycopy(explicitColumns, fromColumn + 1, explicitColumns, fromColumn, toColumn - fromColumn);
                }
                explicitColumns[toColumn] = fromCell;

                // reconstruct cells
                cells.clear();
                addExplicitColumns(explicitColumns);
            }
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

        boolean isEmptyColumn(int column) {
            int index = indexOf(column);
            return index >= cells.size() || cells.get(index).text.isBlank();
        }

        boolean isEmpty() {
            for (TableCell cell : cells) {
                if (!cell.text.isBlank()) return false;
            }
            return true;
        }

        int indexOf(int column) {
            return indexOfOrNull(column);
        }

        Integer indexOfOrNull(Integer column) {
            if (column == null) return null;

            int columns = 0;
            int index = 0;
            for (TableCell cell : cells) {
                if (columns >= column) break;
                columns += cell.columnSpan;
                index++;
            }
            return index;
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

    public static class TableSeparatorSection extends TableSection {
        @Override
        protected TableRow defaultRow() {
            return new TableSeparatorRow();
        }
    }
    
    @SuppressWarnings("WeakerAccess")
    public static class TableSection {
        public final ArrayList<TableRow> rows = new ArrayList<>();
        protected int row;
        protected int column;

        public TableSection() {
            row = 0;
            column = 0;
        }

        public ArrayList<TableRow> getRows() {
            return rows;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
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
                TableRow tableRow = defaultRow();
                rows.add(tableRow);
            }
            return rows.get(row);
        }

        public TableRow expandTo(int row, int column) {
            return expandTo(row, column, null);
        }

        public TableRow expandTo(int row, int column, TableCell cell) {
            while (row >= rows.size()) {
                TableRow tableRow = defaultRow();
                tableRow.expandTo(column, cell);
                rows.add(tableRow);
            }
            return rows.get(row).expandTo(column);
        }

        protected TableRow defaultRow() {
            return new TableRow();
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
