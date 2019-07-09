package com.vladsch.flexmark.convert.html;

/**
 * @deprecated Use public enum ExtensionConversion { from flexmark-html2md-converter module instead.
 * The latter is an extensible version of HTML conversion module with the same features as flexmark-html-parser.
 *
 * This class is here for backward compatibility and is no longer maintained.
 */
@Deprecated
public enum ExtensionConversion {
    NONE,                           // no rendering
    MARKDOWN,                       // convert to markdown
    TEXT,                           // convert to text
    HTML;                           // pass through tag as HTML

    boolean isParsed() { return this != HTML; }

    boolean isTextOnly() { return this == TEXT; }

    boolean isSuppressed() { return this == NONE; }
}
