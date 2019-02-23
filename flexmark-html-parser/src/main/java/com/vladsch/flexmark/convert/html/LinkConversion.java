package com.vladsch.flexmark.convert.html;

public enum LinkConversion {
    NONE,                           // no rendering
    MARKDOWN_EXPLICIT,              // convert to markdown explicit link/image
    MARKDOWN_REFERENCE,             // convert to markdown ref link/image with reference
    TEXT,                           // convert to text
    HTML;                           // pass through tag as HTML

    boolean isParsed() { return this != HTML; }
    boolean isTextOnly() { return this == TEXT; }
    boolean isSuppressed() { return this == NONE; }
    boolean isReference() { return this == MARKDOWN_REFERENCE; }
}
