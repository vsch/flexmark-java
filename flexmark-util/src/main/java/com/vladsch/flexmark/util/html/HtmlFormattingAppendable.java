package com.vladsch.flexmark.util.html;

import java.util.List;
import java.util.Stack;

/**
 * Used to help with HTML output generation and formatting of HTML
 */
public interface HtmlFormattingAppendable extends FormattingAppendable {
    Attributes getAttributes();
    HtmlFormattingAppendable setAttributes(Attributes attributes);
    boolean inPre();
    HtmlFormattingAppendable openPre();
    HtmlFormattingAppendable closePre();
    HtmlFormattingAppendable raw(CharSequence s);
    HtmlFormattingAppendable raw(CharSequence s, int count);
    HtmlFormattingAppendable rawPre(CharSequence s);
    HtmlFormattingAppendable rawIndentedPre(CharSequence s);
    HtmlFormattingAppendable text(CharSequence s);
    HtmlFormattingAppendable attr(CharSequence attrName, CharSequence value);
    HtmlFormattingAppendable attr(Attribute... attribute);
    HtmlFormattingAppendable attr(Attributes attributes);
    HtmlFormattingAppendable withAttr();

    // tag tracking
    public Stack<String> getOpenTags();
    public List<String> getOpenTagsAfterLast(CharSequence latestTag);

    HtmlFormattingAppendable withCondLine();
    HtmlFormattingAppendable withCondIndent();

    HtmlFormattingAppendable tagVoid(CharSequence tagName);
    HtmlFormattingAppendable tag(CharSequence tagName);
    HtmlFormattingAppendable tag(CharSequence tagName, boolean voidElement);
    HtmlFormattingAppendable tag(CharSequence tagName, final boolean withIndent, final boolean withLine, Runnable runnable);

    HtmlFormattingAppendable tagVoidLine(CharSequence tagName);
    HtmlFormattingAppendable tagLine(CharSequence tagName);
    HtmlFormattingAppendable tagLine(CharSequence tagName, boolean voidElement);
    HtmlFormattingAppendable tagLine(CharSequence tagName, Runnable runnable);
    HtmlFormattingAppendable tagIndent(CharSequence tagName, Runnable runnable);
    HtmlFormattingAppendable tagLineIndent(CharSequence tagName, Runnable runnable);
    HtmlFormattingAppendable closeTag(CharSequence tagName);
}
