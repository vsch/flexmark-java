package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.options.DataHolder;

/**
 * @deprecated use MarkdownTable instead
 */
@Deprecated
public class Table extends MarkdownTable {
    public Table(final DataHolder options) {
        super(options);
    }

    public Table(final TableFormatOptions options) {
        super(options);
    }
}
