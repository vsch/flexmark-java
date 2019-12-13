package com.vladsch.flexmark.util.html;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Stack;

/**
 * Used to help with HTML output generation and formatting of HTML
 */
@SuppressWarnings("UnusedReturnValue")
public interface HtmlFormattingAppendable extends LineAppendable {
    @Nullable Attributes getAttributes();
    @NotNull HtmlFormattingAppendable setAttributes(@NotNull Attributes attributes);
    boolean inPre();
    @NotNull HtmlFormattingAppendable openPre();
    @NotNull HtmlFormattingAppendable closePre();
    @NotNull HtmlFormattingAppendable raw(@NotNull CharSequence s);
    @NotNull HtmlFormattingAppendable raw(@NotNull CharSequence s, int count);
    @NotNull HtmlFormattingAppendable rawPre(@NotNull CharSequence s);
    @NotNull HtmlFormattingAppendable rawIndentedPre(@NotNull CharSequence s);
    @NotNull HtmlFormattingAppendable text(@NotNull CharSequence s);
    @NotNull HtmlFormattingAppendable attr(@NotNull CharSequence attrName, @NotNull CharSequence value);
    @NotNull HtmlFormattingAppendable attr(@NotNull Attribute... attribute);
    @NotNull HtmlFormattingAppendable attr(@NotNull Attributes attributes);
    @NotNull HtmlFormattingAppendable withAttr();

    // tag tracking
    @NotNull Stack<String> getOpenTags();
    @NotNull List<String> getOpenTagsAfterLast(@NotNull CharSequence latestTag);

    @NotNull HtmlFormattingAppendable withCondLineOnChildText();
    @NotNull HtmlFormattingAppendable withCondIndent();

    @NotNull HtmlFormattingAppendable tagVoid(@NotNull CharSequence tagName);
    @NotNull HtmlFormattingAppendable tag(@NotNull CharSequence tagName);
    @NotNull HtmlFormattingAppendable tag(@NotNull CharSequence tagName, @NotNull Runnable runnable);
    @NotNull HtmlFormattingAppendable tag(@NotNull CharSequence tagName, boolean voidElement);
    @NotNull HtmlFormattingAppendable tag(@NotNull CharSequence tagName, boolean withIndent, boolean withLine, @NotNull Runnable runnable);

    @NotNull HtmlFormattingAppendable tagVoidLine(@NotNull CharSequence tagName);
    @NotNull HtmlFormattingAppendable tagLine(@NotNull CharSequence tagName);
    @NotNull HtmlFormattingAppendable tagLine(@NotNull CharSequence tagName, boolean voidElement);
    @NotNull HtmlFormattingAppendable tagLine(@NotNull CharSequence tagName, @NotNull Runnable runnable);
    @NotNull HtmlFormattingAppendable tagIndent(@NotNull CharSequence tagName, @NotNull Runnable runnable);
    @NotNull HtmlFormattingAppendable tagLineIndent(@NotNull CharSequence tagName, @NotNull Runnable runnable);
    @NotNull HtmlFormattingAppendable closeTag(@NotNull CharSequence tagName);
}
