package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.util.options.DataHolder;

class FormatterOptions {
    public final int formatFlags;
    public final int maxTrailingBlankLines;

    public FormatterOptions(DataHolder options) {
        formatFlags = FormattingRenderer.FORMAT_FLAGS.getFrom(options);
        maxTrailingBlankLines = FormattingRenderer.MAX_TRAILING_BLANK_LINES.getFrom(options);
    }
}
