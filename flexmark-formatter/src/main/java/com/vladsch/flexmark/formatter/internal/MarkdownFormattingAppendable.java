package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.FormattingAppendable;

/**
 * Used to help with HTML output generation and formatting of HTML
 */
public interface MarkdownFormattingAppendable extends FormattingAppendable {
    Attributes getAttributes();
    MarkdownFormattingAppendable setAttributes(Attributes attributes);
    boolean inPre();
    MarkdownFormattingAppendable openPre();
    MarkdownFormattingAppendable closePre();
    MarkdownFormattingAppendable raw(CharSequence s);
    MarkdownFormattingAppendable raw(CharSequence s, int count);
    MarkdownFormattingAppendable rawPre(CharSequence s);
    MarkdownFormattingAppendable rawIndentedPre(CharSequence s);
    MarkdownFormattingAppendable text(CharSequence s);
    MarkdownFormattingAppendable attr(CharSequence attrName, CharSequence value);
    MarkdownFormattingAppendable attr(Attribute... attribute);
    MarkdownFormattingAppendable attr(Attributes attributes);
    MarkdownFormattingAppendable withAttr();

    MarkdownFormattingAppendable withCondLine();
    MarkdownFormattingAppendable withCondIndent();

    MarkdownFormattingAppendable tagVoid(CharSequence tagName);
    MarkdownFormattingAppendable tag(CharSequence tagName);
    MarkdownFormattingAppendable tag(CharSequence tagName, boolean voidElement);
    MarkdownFormattingAppendable tag(CharSequence tagName, final boolean withIndent, final boolean withLine, Runnable runnable);

    MarkdownFormattingAppendable tagVoidLine(CharSequence tagName);
    MarkdownFormattingAppendable tagLine(CharSequence tagName);
    MarkdownFormattingAppendable tagLine(CharSequence tagName, boolean voidElement);
    MarkdownFormattingAppendable tagLine(CharSequence tagName, Runnable runnable);
    MarkdownFormattingAppendable tagIndent(CharSequence tagName, Runnable runnable);
    MarkdownFormattingAppendable tagLineIndent(CharSequence tagName, Runnable runnable);
    MarkdownFormattingAppendable closeTag(CharSequence tagName);
}
