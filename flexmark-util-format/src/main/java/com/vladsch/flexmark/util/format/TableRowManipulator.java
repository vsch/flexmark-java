package com.vladsch.flexmark.util.format;

import java.util.ArrayList;

public interface TableRowManipulator {
    int BREAK = Integer.MIN_VALUE;

    /**
     * manipulate rows in a table
     *
     * @param row             row for the operation
     * @param allRowsIndex    row's index in all rows of the request reflects indices at time of
     *                        request, when rows are deleted those rows will not be processed and
     *                        their indices will skipped
     * @param sectionRows     rows for the section of the row
     * @param sectionRowIndex index for the row in the section's rows
     * @return action performed: &lt;0 number of rows deleted, 0 - no change to rows, &gt;0 -
     *         number of rows added, or BREAK to stop processing rows
     */

    int apply(TableRow row, int allRowsIndex, ArrayList<TableRow> sectionRows, int sectionRowIndex);
}
