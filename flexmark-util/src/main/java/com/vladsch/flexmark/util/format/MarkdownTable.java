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
public class MarkdownTable {
    public final TableSection heading;
    public final TableSection separator;
    public final TableSection body;
    public TableFormatOptions options;

    private BasedSequence captionOpen = BasedSequence.NULL;
    private BasedSequence caption = BasedSequence.NULL;
    private BasedSequence captionClose = BasedSequence.NULL;
    private boolean isHeading;
    private boolean isSeparator;

    // used by finalization and conversion to text
    private CellAlignment[] alignments;
    private int[] columnWidths;

    public MarkdownTable(DataHolder options) {
        this(new TableFormatOptions(options));
    }

    public MarkdownTable(TableFormatOptions options) {
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

    /**
     * Used by visitor during table creation
     *
     * @param captionOpen
     * @param caption
     * @param captionClose
     */
    public void setCaptionWithMarkers(final CharSequence captionOpen, final CharSequence caption, final CharSequence captionClose) {
        this.captionOpen = BasedSequenceImpl.of(captionOpen);
        this.caption = BasedSequenceImpl.of(caption);
        this.captionClose = BasedSequenceImpl.of(captionClose);
    }

    public BasedSequence getCaptionClose() {
        return captionClose;
    }

    public int getHeadingRowCount() {
        return heading.rows.size();
    }

    public int getBodyRowCount() {
        return body.rows.size();
    }

    public int getMaxHeadingColumns() {
        return heading.getMaxColumns();
    }

    public int getMaxSeparatorColumns() {
        return separator.getMaxColumns();
    }

    public int getMaxBodyColumns() {
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

    public int getMaxColumnsWithout(boolean withSeparator, int... skipRows) {
        int columns = 0;
        int index = 0;

        for (TableRow row : allRows(withSeparator)) {
            if (!contained(index, skipRows)) columns = max(columns, row.getTotalColumns());
            index++;
        }
        return columns;
    }

    public int getMinColumnsWithout(boolean withSeparator, int... skipRows) {
        int columns = Integer.MAX_VALUE;
        int index = 0;

        for (TableRow row : allRows(withSeparator)) {
            if (!contained(index, skipRows)) columns = min(columns, row.getTotalColumns());
            index++;
        }
        return columns == Integer.MAX_VALUE ? 0 : columns;
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

    public int allRowCount() {
        return heading.rows.size() + body.rows.size();
    }

    public void forAllRows(TableRowManipulator manipulator) {
        forAllRows(0, allRowCount(), manipulator);
    }

    public void forAllRows(int startIndex, TableRowManipulator manipulator) {
        forAllRows(startIndex, allRowCount(), manipulator);
    }

    public void forAllRows(int startIndex, int count, TableRowManipulator manipulator) {
        if (count <= 0) return;

        int remaining = count;
        int currentIndex = 0;
        TableSection section = null;

        if (startIndex < heading.rows.size()) {
            section = heading;
            currentIndex = startIndex;
        }
        startIndex -= heading.rows.size();

        if (section == null) {
            if (startIndex < body.rows.size()) {
                section = body;
                currentIndex = startIndex;
            }
        }

        if (section == null) return;

        while (true) {
            while (currentIndex < section.rows.size()) {
                int result = manipulator.apply(section.rows, currentIndex);
                if (result == TableRowManipulator.BREAK) return;
                if (result < 0) remaining += result;
                else {
                    currentIndex += result + 1;
                    remaining--;
                }
                if (remaining <= 0) return;
            }

            if (section == heading) {
                section = body;
                currentIndex = 0;
                continue;
            }

            break;
        }
    }

    public void deleteRows(int rowIndex, int count) {
        forAllRows(rowIndex, count, new TableRowManipulator() {
            @Override
            public int apply(final ArrayList<TableRow> rows, final int index) {
                rows.remove(index);
                return -1;
            }
        });
    }

    public void insertColumns(final int column, final int count) {
        forAllRows(new TableRowManipulator() {
            @Override
            public int apply(final ArrayList<TableRow> rows, final int index) {
                rows.get(index).insertColumns(column, count);
                return 0;
            }
        });
    }

    public void deleteColumns(final int column, final int count) {
        forAllRows(new TableRowManipulator() {
            @Override
            public int apply(final ArrayList<TableRow> rows, final int index) {
                rows.get(index).deleteColumns(column, count);
                return 0;
            }
        });

        // delete separator columns separately, since they are not reduced in finalize
        for (TableRow row : separator.rows) {
            row.deleteColumns(column, count);
        }
    }

    public void insertRows(final int rowIndex, final int count) {
        final int maxColumns = getMaxColumns();
        final boolean handled[] = new boolean[] { false };
        forAllRows(rowIndex, 1, new TableRowManipulator() {
            @Override
            public int apply(final ArrayList<TableRow> rows, final int index) {
                MarkdownTable.this.insertRows(rows, index, count, maxColumns);
                handled[0] = true;
                return BREAK;
            }
        });

        if (!handled[0]) {
            insertRows(body.rows, body.rows.size(), count, maxColumns);
        }
    }

    private void insertRows(final ArrayList<TableRow> rows, final int index, final int count, final int maxColumns) {
        int i = count;
        while (i-- > 0) {
            TableRow emptyRow = new TableRow();
            emptyRow.appendColumns(maxColumns);
            if (index >= rows.size()) {
                rows.add(emptyRow);
            } else {
                rows.add(index, emptyRow);
            }
        }
    }

    public void moveColumn(final int fromColumn, final int toColumn) {
        forAllRows(new TableRowManipulator() {
            @Override
            public int apply(final ArrayList<TableRow> rows, final int index) {
                rows.get(index).moveColumn(fromColumn, toColumn);
                return 0;
            }
        });
    }

    public boolean isAllRowsSeparator(int index) {
        return index >= heading.rows.size() && index < heading.rows.size() + separator.rows.size();
    }

    /**
     * Test all rows for having given column empty. All columns after row's max column are empty
     *
     * @param column index in allRows list
     * @return true if column is empty for all rows, separator row excluded
     */
    public boolean isEmptyColumn(int column) {
        for (TableRow row : allRows(false)) {
            if (!row.isEmptyColumn(column)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test a row for having all empty columns
     *
     * @param row           index in allRows list
     * @param withSeparator true if separator row is included in rows index
     * @return true if row is empty or is a separator row
     */
    public boolean isEmptyRow(int row, final boolean withSeparator) {
        if (row < heading.rows.size()) return heading.rows.get(row).isEmpty();
        row -= heading.rows.size();

        if (withSeparator) {
            if (row < separator.rows.size()) return false;
            row -= separator.rows.size();
        }

        return row < body.rows.size() && body.rows.get(row).isEmpty();
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

    public void finalizeTable() {
        // remove null cells
        boolean isIntellijDummyIdentifier = options.intellijDummyIdentifier;
        String colonTrimChars = isIntellijDummyIdentifier ? ":" + TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER : ":";

        normalize();

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

    public void appendTable(final FormattingAppendable out) {
        // we will prepare the separator based on max columns
        Ref<Integer> delta = new Ref<Integer>(0);
        boolean isIntellijDummyIdentifier = options.intellijDummyIdentifier;
        String intellijDummyIdentifier = isIntellijDummyIdentifier ? TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER : "";
        String linePrefix = options.formatTableIndentPrefix;

        int formatterOptions = out.getOptions();
        out.setOptions(formatterOptions & ~FormattingAppendable.COLLAPSE_WHITESPACE);

        appendRows(out, heading.rows, true, linePrefix, delta);

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

        appendRows(out, body.rows, false, linePrefix, delta);

        out.setOptions(formatterOptions);
        appendFormattedCaption(out, caption, options);
    }

    public static void appendFormattedCaption(final FormattingAppendable out, final BasedSequence caption, final TableFormatOptions options) {
        String formattedCaption = formattedCaption(caption, options);
        if (formattedCaption != null) {
            out.line().append('[').append(formattedCaption).append(']').line();
        }
    }

    public static String formattedCaption(final BasedSequence caption, final TableFormatOptions options) {
        boolean append = caption.isNotNull();

        switch (options.formatTableCaption) {
            case ADD:
                append = true;
                break;

            case REMOVE_EMPTY:
                append = !(caption.isBlank());
                break;

            case REMOVE:
                append = false;
                break;

            default:
            case AS_IS:
                if (options.removeCaption) append = false;
                break;
        }

        if (append) {
            String captionSpaces = "";
            BasedSequence useCaption = caption;

            switch (options.formatTableCaptionSpaces) {
                case ADD:
                    captionSpaces = " ";
                    useCaption = caption.trim();
                    break;

                case REMOVE:
                    useCaption = caption.trim();
                    break;

                default:
                case AS_IS:
                    break;
            }
            return captionSpaces + caption.toString() + captionSpaces;
        }
        return null;
    }

    private boolean pipeNeedsSpaceBefore(TableCell cell) {
        return cell.text.equals(" ") || !cell.text.endsWith(" ");
    }

    private boolean pipeNeedsSpaceAfter(TableCell cell) {
        return cell.text.equals(" ") || !cell.text.startsWith(" ");
    }

    private void appendRows(final FormattingAppendable out, List<TableRow> rows, final boolean isHeader, final String linePrefix, final Ref<Integer> delta) {
        for (TableRow row : rows) {
            int j = 0;
            int jSpan = 0;
            delta.value = 0;

            out.append(linePrefix);

            for (TableCell cell : row.cells) {
                if (j == 0) {
                    if (options.leadTrailPipes) {
                        out.append('|');
                        if (options.spaceAroundPipes && pipeNeedsSpaceAfter(cell)) out.append(' ');
                    }
                } else {
                    if (options.spaceAroundPipes && pipeNeedsSpaceAfter(cell)) out.append(' ');
                }

                CellAlignment cellAlignment = isHeader && cell.alignment != CellAlignment.NONE ? cell.alignment : alignments[jSpan];
                out.append(cellText(cell.text, isHeader, spanWidth(jSpan, cell.columnSpan) - options.spacePad - options.pipeWidth * cell.columnSpan, cellAlignment, delta));

                j++;
                jSpan += cell.columnSpan;

                if (j < alignments.length) {
                    if (options.spaceAroundPipes && pipeNeedsSpaceBefore(cell)) out.append(' ');
                    out.repeat('|', cell.columnSpan);
                } else if (options.leadTrailPipes) {
                    if (options.spaceAroundPipes && pipeNeedsSpaceBefore(cell)) out.append(' ');
                    out.repeat('|', cell.columnSpan);
                } else {
                    if (options.spaceAroundPipes && pipeNeedsSpaceBefore(cell)) out.append(' ');
                    out.repeat('|', cell.columnSpan - 1);
                }
            }

            if (j > 0) out.line();
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

    public void normalize() {
        heading.normalize();
        separator.normalize();
        body.normalize();
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

        public TableCell withColumnSpan(int columnSpan) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment); }

        public TableCell withText(CharSequence text) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment); }

        public TableCell withRowSpan(int rowSpan) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment); }

        public TableCell withAlignment(CellAlignment alignment) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment); }
    }

    public static class TableSeparatorRow extends TableRow {
        @Override
        public TableCell defaultCell() {
            return new TableCell("---", 1, 1);
        }
    }

    public static class IndexSpanOffset {
        public final int index;
        public final int spanOffset;

        public IndexSpanOffset(final int index, final int spanOffset) {
            this.index = index;
            this.spanOffset = spanOffset;
        }

        @Override
        public String toString() {
            return "IndexSpanOffset{" +
                    "index=" + index +
                    ", spanOffset=" + spanOffset +
                    '}';
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class TableRow {
        protected final List<TableCell> cells;
        protected boolean normalized = true;

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

        public int getTotalColumns() {
            return columnOf(cells.size());
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
                IndexSpanOffset indexSpan = indexOf(column);
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
            IndexSpanOffset indexSpan = indexOf(column);
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
                IndexSpanOffset fromIndexSpan = indexOf(fromColumn);
                int fromIndex = fromIndexSpan.index;
                int fromSpanOffset = fromIndexSpan.spanOffset;
                TableCell cell = cells.get(fromIndex).withColumnSpan(1);

                IndexSpanOffset toIndexSpan = indexOf(toColumn);
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

                //TableCell[] explicitColumns = explicitColumns();
                //
                //TableCell fromCell = explicitColumns[fromColumn];
                //if (toColumn < fromColumn) {
                //    // shift in between columns right
                //    System.arraycopy(explicitColumns, toColumn, explicitColumns, toColumn + 1, fromColumn - toColumn);
                //} else {
                //    // shift in between columns left
                //    System.arraycopy(explicitColumns, fromColumn + 1, explicitColumns, fromColumn, toColumn - fromColumn);
                //}
                //explicitColumns[toColumn] = fromCell;
                //
                //// reconstruct cells
                //cells.clear();
                //addExplicitColumns(explicitColumns);
            }
        }

        public TableCell[] explicitColumns() {
            TableCell[] explicitColumns = new TableCell[getTotalColumns()];

            int explicitIndex = 0;
            for (TableCell cell : cells) {
                if (cell == null || cell.columnSpan == 0) continue;
                explicitColumns[explicitIndex] = cell;
                explicitIndex += cell.columnSpan;
            }
            return explicitColumns;
        }

        public void addExplicitColumns(TableCell[] explicitColumns) {
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

        public void set(int column, TableCell cell) {
            expandTo(column, null);
            cells.set(column, cell);
        }

        boolean isEmptyColumn(int column) {
            int index = indexOf(column).index;
            return index >= cells.size() || cells.get(index).text.isBlank();
        }

        boolean isEmpty() {
            for (TableCell cell : cells) {
                if (cell != null && !cell.text.isBlank()) return false;
            }
            return true;
        }

        public IndexSpanOffset indexOf(int column) {
            return indexOfOrNull(column);
        }

        public IndexSpanOffset indexOfOrNull(Integer column) {
            if (column == null) return null;

            int remainingColumns = column;
            int index = 0;

            for (TableCell cell : cells) {
                if (cell.columnSpan > remainingColumns) {
                    int spanOffset = remainingColumns;
                    return new IndexSpanOffset(index, spanOffset);
                }

                remainingColumns -= cell.columnSpan;

                if (cell.columnSpan > 0) index++;
            }

            return new IndexSpanOffset(index, 0);
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

        public void normalize() {
            for (TableRow row : rows) {
                row.normalize();
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
