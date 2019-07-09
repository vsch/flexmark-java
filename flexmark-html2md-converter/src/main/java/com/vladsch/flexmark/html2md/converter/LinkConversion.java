package com.vladsch.flexmark.html2md.converter;

public enum LinkConversion {
    NONE,                           // no rendering
    MARKDOWN_EXPLICIT,              // convert to markdown explicit link/image
    MARKDOWN_REFERENCE,             // convert to markdown ref link/image with reference
    TEXT,                           // convert to text
    HTML;                           // pass through tag as HTML

    public boolean isParsed() { return this != HTML; }

    public boolean isTextOnly() { return this == TEXT; }

    public boolean isSuppressed() { return this == NONE; }

    public boolean isReference() { return this == MARKDOWN_REFERENCE; }
}
