package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.internal.util.Options;

class TableParserOptions {
    final public int maxHeaderRows;
    final public boolean appendMissingColumns;
    final public boolean discardExtraColumns;
    final public boolean columnSpans;

    TableParserOptions(Options options) {
        this.maxHeaderRows = options.getOrDefault(TablesExtension.MAX_HEADER_ROWS);
        this.appendMissingColumns = options.getOrDefault(TablesExtension.APPEND_MISSING_COLUMNS);
        this.discardExtraColumns = options.getOrDefault(TablesExtension.DISCARD_EXTRA_COLUMNS);
        this.columnSpans = options.getOrDefault(TablesExtension.COLUMN_SPANS);
    }

    TableParserOptions(TableParserOptions other) {
        this.maxHeaderRows = other.maxHeaderRows;
        this.appendMissingColumns = other.appendMissingColumns;
        this.discardExtraColumns = other.discardExtraColumns;
        this.columnSpans = other.columnSpans;
    }
}
