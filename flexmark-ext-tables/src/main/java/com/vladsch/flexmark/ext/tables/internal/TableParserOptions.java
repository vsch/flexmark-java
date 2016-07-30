package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.internal.util.options.DataHolder;

class TableParserOptions {
    final public int maxHeaderRows;
    final public int minHeaderRows;
    final public boolean appendMissingColumns;
    final public boolean discardExtraColumns;
    final public boolean columnSpans;
    final public boolean trimCellWhitespace;
    final public boolean headerSeparatorColumnMatch;

    TableParserOptions(DataHolder options) {
        this.maxHeaderRows = TablesExtension.MAX_HEADER_ROWS.getFrom(options);
        this.minHeaderRows = TablesExtension.MIN_HEADER_ROWS.getFrom(options);
        this.appendMissingColumns = TablesExtension.APPEND_MISSING_COLUMNS.getFrom(options);
        this.discardExtraColumns = TablesExtension.DISCARD_EXTRA_COLUMNS.getFrom(options);
        this.columnSpans = TablesExtension.COLUMN_SPANS.getFrom(options);
        this.trimCellWhitespace = TablesExtension.TRIM_CELL_WHITESPACE.getFrom(options);
        this.headerSeparatorColumnMatch = TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH.getFrom(options);
    }
}
