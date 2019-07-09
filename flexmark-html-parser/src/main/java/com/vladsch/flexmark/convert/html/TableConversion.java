package com.vladsch.flexmark.convert.html;

/**
 * @deprecated Use TableConversion from flexmark-html2md-converter module instead.
 * The latter is an extensible version of HTML conversion module with the same features as flexmark-html-parser.
 *
 * This class is here for backward compatibility and is no longer maintained.
 */
@Deprecated
public enum TableConversion {
    NONE,                           // no rendering
    MARKDOWN,                       // convert to markdown, complex content reduced to inline
    MARKDOWN_NO_SINGLE_CELL,        // convert to markdown, except for single cell tables and just render contents of the cell
    MARKDOWN_MACROS,                // convert to markdown, if cell has block content convert to macros
    MARKDOWN_MACROS_NO_SINGLE_CELL, // convert to markdown, except for single cell tables and just render contents of the cell
    TEXT,                           // convert, false to inner text
    HTML;                           // pass through tag as HTML

    boolean isParsed() { return this != HTML; }

    boolean isTextOnly() { return this == TEXT; }

    boolean isSuppressed() { return this == NONE; }

    boolean isUnwrapSingleCell() { return this == MARKDOWN_NO_SINGLE_CELL || this == MARKDOWN_MACROS_NO_SINGLE_CELL; }

    boolean isMacros() { return this == MARKDOWN_MACROS || this == MARKDOWN_MACROS_NO_SINGLE_CELL; }
}
