package com.vladsch.flexmark.convert.html;

public enum ExtensionConversion {
    NONE(true, false, true),  // convert to markdown
    MARKDOWN(true, false, false),  // convert to markdown
    TEXT(true, true, false),       // convert, false to inner text
    HTML(false, false, false);     // pass through tag

    final public boolean isParsed;
    final public boolean isTextOnly;
    final public boolean isSuppressed;

    ExtensionConversion(final boolean isParsed, final boolean isTextOnly, final boolean isSuppressed) {
        this.isParsed = isParsed;
        this.isTextOnly = isTextOnly;
        this.isSuppressed = isSuppressed;
    }
}
