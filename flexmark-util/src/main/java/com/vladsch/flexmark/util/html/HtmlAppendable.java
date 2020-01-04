package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.sequence.LineAppendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Stack;

/**
 * Used to help with HTML output generation and formatting of HTML
 */
@SuppressWarnings("UnusedReturnValue")
public interface HtmlAppendable extends LineAppendable {
    @Nullable Attributes getAttributes();
    @NotNull HtmlAppendable setAttributes(@NotNull Attributes attributes);
    boolean inPre();
    @NotNull HtmlAppendable openPre();
    @NotNull HtmlAppendable closePre();
    @NotNull HtmlAppendable raw(@NotNull CharSequence s);
    @NotNull HtmlAppendable raw(@NotNull CharSequence s, int count);
    @NotNull HtmlAppendable rawPre(@NotNull CharSequence s);
    @NotNull HtmlAppendable rawIndentedPre(@NotNull CharSequence s);
    @NotNull HtmlAppendable text(@NotNull CharSequence s);
    @NotNull HtmlAppendable attr(@NotNull CharSequence attrName, @NotNull CharSequence value);
    @NotNull HtmlAppendable attr(@NotNull Attribute... attribute);
    @NotNull HtmlAppendable attr(@NotNull Attributes attributes);
    @NotNull HtmlAppendable withAttr();

    // tag tracking
    @NotNull Stack<String> getOpenTags();
    @NotNull List<String> getOpenTagsAfterLast(@NotNull CharSequence latestTag);

    @NotNull HtmlAppendable withCondLineOnChildText();
    @NotNull HtmlAppendable withCondIndent();

    @NotNull HtmlAppendable tagVoid(@NotNull CharSequence tagName);
    @NotNull HtmlAppendable tag(@NotNull CharSequence tagName);
    @NotNull HtmlAppendable tag(@NotNull CharSequence tagName, @NotNull Runnable runnable);
    @NotNull HtmlAppendable tag(@NotNull CharSequence tagName, boolean voidElement);
    @NotNull HtmlAppendable tag(@NotNull CharSequence tagName, boolean withIndent, boolean withLine, @NotNull Runnable runnable);

    @NotNull HtmlAppendable tagVoidLine(@NotNull CharSequence tagName);
    @NotNull HtmlAppendable tagLine(@NotNull CharSequence tagName);
    @NotNull HtmlAppendable tagLine(@NotNull CharSequence tagName, boolean voidElement);
    @NotNull HtmlAppendable tagLine(@NotNull CharSequence tagName, @NotNull Runnable runnable);
    @NotNull HtmlAppendable tagIndent(@NotNull CharSequence tagName, @NotNull Runnable runnable);
    @NotNull HtmlAppendable tagLineIndent(@NotNull CharSequence tagName, @NotNull Runnable runnable);
    @NotNull HtmlAppendable closeTag(@NotNull CharSequence tagName);
}
