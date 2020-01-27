package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.util.data.DataHolder;

class TableParserOptions {
    final public int maxHeaderRows;
    final public int minHeaderRows;
    final public int minSeparatorDashes;
    final public boolean appendMissingColumns;
    final public boolean discardExtraColumns;
    final public boolean columnSpans;
    final public boolean trimCellWhitespace;
    final public boolean headerSeparatorColumnMatch;
    final public String className;
    final public boolean withCaption;

    TableParserOptions(DataHolder options) {
        this.maxHeaderRows = TablesExtension.MAX_HEADER_ROWS.get(options);
        this.minHeaderRows = TablesExtension.MIN_HEADER_ROWS.get(options);
        this.minSeparatorDashes = TablesExtension.MIN_SEPARATOR_DASHES.get(options);
        this.appendMissingColumns = TablesExtension.APPEND_MISSING_COLUMNS.get(options);
        this.discardExtraColumns = TablesExtension.DISCARD_EXTRA_COLUMNS.get(options);
        this.columnSpans = TablesExtension.COLUMN_SPANS.get(options);
        this.trimCellWhitespace = TablesExtension.TRIM_CELL_WHITESPACE.get(options);
        this.headerSeparatorColumnMatch = TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH.get(options);
        this.className = TablesExtension.CLASS_NAME.get(options);
        this.withCaption = TablesExtension.WITH_CAPTION.get(options);
    }
}
