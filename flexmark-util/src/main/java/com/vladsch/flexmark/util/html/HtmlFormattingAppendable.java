package com.vladsch.flexmark.util.html;

/**
 * Used to help with HTML output generation and formatting of HTML
 */
public interface HtmlFormattingAppendable extends FormattingAppendable {
    Attributes getAttributes();
    HtmlFormattingAppendable setAttributes(Attributes attributes);
    boolean inPre();
    HtmlFormattingAppendable openPre();
    HtmlFormattingAppendable closePre();
    HtmlFormattingAppendable raw(String s);
    HtmlFormattingAppendable rawPre(String s);
    HtmlFormattingAppendable rawIndentedPre(String s);
    HtmlFormattingAppendable text(String text);
    HtmlFormattingAppendable attr(String name, String value);
    HtmlFormattingAppendable attr(Attribute... attribute);
    HtmlFormattingAppendable attr(Attributes attributes);
    HtmlFormattingAppendable withAttr();

    HtmlFormattingAppendable withCondLine();
    HtmlFormattingAppendable withCondIndent();

    HtmlFormattingAppendable tagVoid(String name);
    HtmlFormattingAppendable tag(String name);
    HtmlFormattingAppendable tag(String name, boolean voidElement);
    HtmlFormattingAppendable tag(String name, final boolean withIndent, final boolean withLine, Runnable runnable);

    HtmlFormattingAppendable tagVoidLine(String name);
    HtmlFormattingAppendable tagLine(String name);
    HtmlFormattingAppendable tagLine(String name, boolean voidElement);
    HtmlFormattingAppendable tagLine(String name, Runnable runnable);
    HtmlFormattingAppendable tagIndent(String name, Runnable runnable);
    HtmlFormattingAppendable tagLineIndent(String name, Runnable runnable);
}
