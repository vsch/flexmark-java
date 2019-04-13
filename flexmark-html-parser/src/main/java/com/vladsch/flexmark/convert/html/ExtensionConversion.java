package com.vladsch.flexmark.convert.html;

public enum ExtensionConversion {
    NONE,                           // no rendering
    MARKDOWN,                       // convert to markdown
    TEXT,                           // convert to text
    HTML;                           // pass through tag as HTML

    boolean isParsed() { return this != HTML; }

    boolean isTextOnly() { return this == TEXT; }

    boolean isSuppressed() { return this == NONE; }
}
