package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.util.options.DataHolder;

public class FormatterOptions {
    public final int formatFlags;
    public final int maxTrailingBlankLines;

    public FormatterOptions(DataHolder options) {
        formatFlags = Formatter.FORMAT_FLAGS.getFrom(options);
        maxTrailingBlankLines = Formatter.MAX_TRAILING_BLANK_LINES.getFrom(options);
    }
}
