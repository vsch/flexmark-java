package com.vladsch.flexmark.html2md.converter;

public enum ExtensionConversion {
    NONE,                           // no rendering
    MARKDOWN,                       // convert to markdown
    TEXT,                           // convert to text
    HTML;                           // pass through tag as HTML

    public boolean isParsed() { return this != HTML; }

    public boolean isTextOnly() { return this == TEXT; }

    public boolean isSuppressed() { return this == NONE; }
}
