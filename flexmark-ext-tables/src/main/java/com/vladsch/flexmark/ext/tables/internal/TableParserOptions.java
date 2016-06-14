package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.internal.util.DataHolder;

class TableParserOptions {
    final public int maxHeaderRows;
    final public int minHeaderRows;
    final public boolean appendMissingColumns;
    final public boolean discardExtraColumns;
    final public boolean columnSpans;
    final public boolean headerSeparatorColumns;

    TableParserOptions(DataHolder options) {
        this.maxHeaderRows = options.get(TablesExtension.MAX_HEADER_ROWS);
        this.minHeaderRows = options.get(TablesExtension.MIN_HEADER_ROWS);
        this.appendMissingColumns = options.get(TablesExtension.APPEND_MISSING_COLUMNS);
        this.discardExtraColumns = options.get(TablesExtension.DISCARD_EXTRA_COLUMNS);
        this.columnSpans = options.get(TablesExtension.COLUMN_SPANS);
        this.headerSeparatorColumns = options.get(TablesExtension.HEADER_SEPARATOR_COLUMNS);
    }
}
