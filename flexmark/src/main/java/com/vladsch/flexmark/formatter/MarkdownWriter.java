package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.BlankLine;
import com.vladsch.flexmark.util.ast.BlockQuoteLike;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.format.MarkdownWriterBase;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class MarkdownWriter extends MarkdownWriterBase<MarkdownWriter, Node, NodeFormatterContext> {
  public MarkdownWriter() {
    this(null, 0);
  }

  public MarkdownWriter(int formatOptions) {
    this(null, formatOptions);
  }

  MarkdownWriter(Appendable appendable, int formatOptions) {
    super(appendable, formatOptions);
  }

  @Override
  public MarkdownWriter getEmptyAppendable() {
    return new MarkdownWriter(appendable, appendable.getOptions());
  }

  @Override
  public BasedSequence lastBlockQuoteChildPrefix(BasedSequence prefix) {
    Node node = context.getCurrentNode();
    while (node != null && node.getNextAnyNot(BlankLine.class) == null) {
      Node parent = node.getParent();
      if (parent == null || parent instanceof Document) {
        break;
      }
      if (parent instanceof BlockQuoteLike) {
        int pos = prefix.lastIndexOfAny(context.getBlockQuoteLikePrefixPredicate());
        if (pos >= 0) {
          prefix =
              prefix
                  .getBuilder()
                  .append(prefix.subSequence(0, pos))
                  .append(' ')
                  .append(prefix.subSequence(pos + 1))
                  .toSequence();
        }
      }
      node = parent;
    }
    return prefix;
  }

  public MarkdownWriter appendNonTranslating(CharSequence csq) {
    return appendNonTranslating(null, csq, null, null);
  }

  public MarkdownWriter appendNonTranslating(
      CharSequence prefix, CharSequence csq, CharSequence suffix) {
    return appendNonTranslating(prefix, csq, suffix, null);
  }

  public MarkdownWriter appendNonTranslating(
      CharSequence prefix, CharSequence csq, CharSequence suffix, CharSequence suffix2) {
    if (context.isTransformingText()) {
      append(context.transformNonTranslating(prefix, csq, suffix, suffix2));
    } else {
      append(csq);
    }
    return this;
  }

  public MarkdownWriter appendTranslating(CharSequence csq) {
    return appendTranslating(null, csq, null, null);
  }

  public MarkdownWriter appendTranslating(
      CharSequence prefix, CharSequence csq, CharSequence suffix) {
    return appendTranslating(prefix, csq, suffix, null);
  }

  public MarkdownWriter appendTranslating(
      CharSequence prefix, CharSequence csq, CharSequence suffix, CharSequence suffix2) {
    if (context.isTransformingText()) {
      append(context.transformTranslating(prefix, csq, suffix, suffix2));
    } else {
      if (prefix != null) append(prefix);
      append(csq);
      if (suffix != null) append(suffix);
      if (suffix2 != null) append(suffix2);
    }
    return this;
  }
}
