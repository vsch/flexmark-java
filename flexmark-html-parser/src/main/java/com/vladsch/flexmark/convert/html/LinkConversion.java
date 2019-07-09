package com.vladsch.flexmark.convert.html;

/**
 * @deprecated Use LinkConversion from flexmark-html2md-converter module instead.
 * The latter is an extensible version of HTML conversion module with the same features as flexmark-html-parser.
 *
 * This class is here for backward compatibility and is no longer maintained.
 */
@Deprecated
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
