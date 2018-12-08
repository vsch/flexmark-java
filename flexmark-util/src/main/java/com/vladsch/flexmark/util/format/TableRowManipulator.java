package com.vladsch.flexmark.util.format;

import java.util.ArrayList;

public interface TableRowManipulator {
    final int BREAK = Integer.MIN_VALUE;

    /**
     * manipulate rows in a table
     *
     * @param rows  rows list of section in table
     * @param index index into rows for current row being processed
     * @return action performed: <0 number of rows deleted, 0 - no change to rows, >0 - number of rows added, or BREAK to stop processing rows
     */

    int apply(ArrayList<MarkdownTable.TableRow> rows, int index);
}
