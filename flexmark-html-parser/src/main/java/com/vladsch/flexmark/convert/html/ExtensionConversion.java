package com.vladsch.flexmark.convert.html;

public enum ExtensionConversion {
    MARKDOWN(true, false),  // convert to markdown
    TEXT(true, true),       // convert to inner text
    HTML(false, false);     // pass through tag

    final public boolean isParsed;
    final public boolean isTextOnly;

    ExtensionConversion(final boolean isParsed, final boolean isTextOnly) {
        this.isParsed = isParsed;
        this.isTextOnly = isTextOnly;
    }
}
