package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.sequence.LineAppendable;
import java.util.List;
import java.util.Stack;

/** Used to help with HTML output generation and formatting of HTML */
interface HtmlAppendable extends LineAppendable {

  Attributes getAttributes();

  HtmlAppendable setAttributes(Attributes attributes);

  boolean inPre();

  HtmlAppendable openPre();

  HtmlAppendable closePre();

  HtmlAppendable raw(CharSequence s);

  HtmlAppendable raw(CharSequence s, int count);

  HtmlAppendable rawPre(CharSequence s);

  HtmlAppendable rawIndentedPre(CharSequence s);

  HtmlAppendable text(CharSequence s);

  HtmlAppendable attr(CharSequence attrName, CharSequence value);

  HtmlAppendable attr(Attribute... attribute);

  HtmlAppendable attr(Attributes attributes);

  HtmlAppendable withAttr();

  // tag tracking

  Stack<String> getOpenTags();

  List<String> getOpenTagsAfterLast(CharSequence latestTag);

  HtmlAppendable withCondLineOnChildText();

  HtmlAppendable withCondIndent();

  HtmlAppendable tagVoid(CharSequence tagName);

  HtmlAppendable tag(CharSequence tagName);

  HtmlAppendable tag(CharSequence tagName, Runnable runnable);

  HtmlAppendable tag(CharSequence tagName, boolean voidElement);

  HtmlAppendable tag(CharSequence tagName, boolean withIndent, boolean withLine, Runnable runnable);

  HtmlAppendable tagVoidLine(CharSequence tagName);

  HtmlAppendable tagLine(CharSequence tagName);

  HtmlAppendable tagLine(CharSequence tagName, boolean voidElement);

  HtmlAppendable tagLine(CharSequence tagName, Runnable runnable);

  HtmlAppendable tagIndent(CharSequence tagName, Runnable runnable);

  HtmlAppendable tagLineIndent(CharSequence tagName, Runnable runnable);

  HtmlAppendable closeTag(CharSequence tagName);
}
