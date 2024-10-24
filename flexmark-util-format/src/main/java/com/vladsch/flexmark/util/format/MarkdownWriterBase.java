package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.misc.BitFieldSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import com.vladsch.flexmark.util.sequence.LineAppendableImpl;
import com.vladsch.flexmark.util.sequence.LineInfo;
import com.vladsch.flexmark.util.sequence.Options;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import java.io.IOException;
import java.util.Iterator;

public abstract class MarkdownWriterBase<
        T extends MarkdownWriterBase<T, N, C>, N, C extends NodeContext<N, C>>
    implements LineAppendable {
  protected final LineAppendableImpl appendable;
  protected C context;

  protected MarkdownWriterBase() {
    this(0);
  }

  @Override
  public String toString() {
    return appendable.toString();
  }

  private MarkdownWriterBase(int formatOptions) {
    this(null, formatOptions);
  }

  protected MarkdownWriterBase(Appendable builder, int formatOptions) {
    appendable = new LineAppendableImpl(builder, formatOptions);
    appendable.setOptions(appendable.getOptions() | LineAppendable.F_PREFIX_PRE_FORMATTED);
  }

  public void setContext(C context) {
    this.context = context;
  }

  public C getContext() {
    return context;
  }

  public T tailBlankLine() {
    return tailBlankLine(1);
  }

  public abstract BasedSequence lastBlockQuoteChildPrefix(BasedSequence prefix);

  private T tailBlankLine(int count) {
    BasedSequence prefix = appendable.getPrefix();
    BasedSequence replacedPrefix = lastBlockQuoteChildPrefix(prefix);
    if (!replacedPrefix.equals(prefix)) {
      // Needed to not add block quote prefix to trailing blank lines
      appendable.setPrefix(replacedPrefix, false);
      appendable.blankLine(count);
      appendable.setPrefix(prefix, false);
    } else {
      appendable.blankLine(count);
    }

    return (T) this;
  }

  @Override
  public Iterator<LineInfo> iterator() {
    return appendable.iterator();
  }

  @Override
  public Iterable<BasedSequence> getLines(
      int maxTrailingBlankLines, int startLine, int endLine, boolean withPrefixes) {
    return appendable.getLines(maxTrailingBlankLines, startLine, endLine, true);
  }

  @Override
  public Iterable<LineInfo> getLinesInfo(int maxTrailingBlankLines, int startLine, int endLine) {
    return appendable.getLinesInfo(maxTrailingBlankLines, startLine, endLine);
  }

  @Override
  public void setPrefixLength(int lineIndex, int prefixEndIndex) {
    appendable.setPrefixLength(lineIndex, prefixEndIndex);
  }

  @Override
  public void insertLine(int lineIndex, CharSequence prefix, CharSequence text) {
    appendable.insertLine(lineIndex, prefix, text);
  }

  @Override
  public void setLine(int lineIndex, CharSequence prefix, CharSequence text) {
    appendable.setLine(lineIndex, prefix, text);
  }

  @Override
  public <T extends Appendable> T appendTo(
      T out,
      boolean withPrefixes,
      int maxBlankLines,
      int maxTrailingBlankLines,
      int startLine,
      int endLine)
      throws IOException {
    return appendable.appendTo(
        out, withPrefixes, maxBlankLines, maxTrailingBlankLines, startLine, endLine);
  }

  @Override
  public boolean endsWithEOL() {
    return appendable.endsWithEOL();
  }

  @Override
  public boolean isPendingSpace() {
    return appendable.isPendingSpace();
  }

  @Override
  public boolean isPreFormatted() {
    return appendable.isPreFormatted();
  }

  @Override
  public int getTrailingBlankLines(int endLine) {
    return appendable.getTrailingBlankLines(endLine);
  }

  @Override
  public int column() {
    return appendable.column();
  }

  @Override
  public int getLineCount() {
    return appendable.getLineCount();
  }

  @Override
  public int getLineCountWithPending() {
    return appendable.getLineCountWithPending();
  }

  @Override
  public int getOptions() {
    return appendable.getOptions();
  }

  @Override
  public int getPendingSpace() {
    return appendable.getPendingSpace();
  }

  @Override
  public int getPendingEOL() {
    return appendable.getPendingEOL();
  }

  @Override
  public int offset() {
    return appendable.offset();
  }

  @Override
  public int offsetWithPending() {
    return appendable.offsetWithPending();
  }

  @Override
  public int getAfterEolPrefixDelta() {
    return appendable.getAfterEolPrefixDelta();
  }

  @Override
  public ISequenceBuilder<?, ?> getBuilder() {
    return appendable.getBuilder();
  }

  @Override
  public BasedSequence getPrefix() {
    return appendable.getPrefix();
  }

  @Override
  public BasedSequence getBeforeEolPrefix() {
    return appendable.getBeforeEolPrefix();
  }

  @Override
  public LineInfo getLineInfo(int lineIndex) {
    return appendable.getLineInfo(lineIndex);
  }

  @Override
  public BasedSequence getLine(int lineIndex) {
    return appendable.getLine(lineIndex);
  }

  @Override
  public BasedSequence getIndentPrefix() {
    return appendable.getIndentPrefix();
  }

  @Override
  public CharSequence toSequence(
      int maxBlankLines, int maxTrailingBlankLines, boolean withPrefixes) {
    return appendable.toSequence(maxBlankLines, maxTrailingBlankLines, withPrefixes);
  }

  @Override
  public String toString(int maxBlankLines, int maxTrailingBlankLines, boolean withPrefixes) {
    return appendable.toString(maxBlankLines, maxTrailingBlankLines, withPrefixes);
  }

  @Override
  public BitFieldSet<Options> getOptionSet() {
    return appendable.getOptionSet();
  }

  @Override
  public T removeExtraBlankLines(
      int maxBlankLines, int maxTrailingBlankLines, int startLine, int endLine) {
    appendable.removeExtraBlankLines(maxBlankLines, maxTrailingBlankLines, startLine, endLine);
    return (T) this;
  }

  @Override
  public T removeLines(int startLine, int endLine) {
    appendable.removeLines(startLine, endLine);
    return (T) this;
  }

  @Override
  public T pushOptions() {
    appendable.pushOptions();
    return (T) this;
  }

  @Override
  public T popOptions() {
    appendable.popOptions();
    return (T) this;
  }

  @Override
  public T changeOptions(int addFlags, int removeFlags) {
    appendable.changeOptions(addFlags, removeFlags);
    return (T) this;
  }

  @Override
  public T addIndentOnFirstEOL(Runnable listener) {
    appendable.addIndentOnFirstEOL(listener);
    return (T) this;
  }

  @Override
  public T addPrefix(CharSequence prefix) {
    appendable.addPrefix(prefix);
    return (T) this;
  }

  @Override
  public T addPrefix(CharSequence prefix, boolean afterEol) {
    appendable.addPrefix(prefix, afterEol);
    return (T) this;
  }

  @Override
  public T append(char c) {
    appendable.append(c);
    return (T) this;
  }

  @Override
  public T append(CharSequence csq) {
    appendable.append(csq);
    return (T) this;
  }

  @Override
  public T append(CharSequence csq, int start, int end) {
    appendable.append(csq, start, end);
    return (T) this;
  }

  @Override
  public T append(LineAppendable lines, int startLine, int endLine, boolean withPrefixes) {
    appendable.append(lines, startLine, endLine, withPrefixes);
    return (T) this;
  }

  @Override
  public T blankLine() {
    appendable.blankLine();
    return (T) this;
  }

  @Override
  public T blankLine(int count) {
    appendable.blankLine(count);
    return (T) this;
  }

  @Override
  public T blankLineIf(boolean predicate) {
    appendable.blankLineIf(predicate);
    return (T) this;
  }

  @Override
  public T closePreFormatted() {
    appendable.closePreFormatted();
    return (T) this;
  }

  @Override
  public T indent() {
    appendable.indent();
    return (T) this;
  }

  @Override
  public T line() {
    appendable.line();
    return (T) this;
  }

  @Override
  public T lineIf(boolean predicate) {
    appendable.lineIf(predicate);
    return (T) this;
  }

  @Override
  public T lineOnFirstText(boolean value) {
    appendable.lineOnFirstText(value);
    return (T) this;
  }

  @Override
  public T lineWithTrailingSpaces(int count) {
    appendable.lineWithTrailingSpaces(count);
    return (T) this;
  }

  @Override
  public T openPreFormatted(boolean keepIndent) {
    appendable.openPreFormatted(keepIndent);
    return (T) this;
  }

  @Override
  public T popPrefix() {
    appendable.popPrefix();
    return (T) this;
  }

  @Override
  public T popPrefix(boolean afterEol) {
    appendable.popPrefix(afterEol);
    return (T) this;
  }

  @Override
  public T pushPrefix() {
    appendable.pushPrefix();
    return (T) this;
  }

  @Override
  public T removeIndentOnFirstEOL(Runnable listener) {
    appendable.removeIndentOnFirstEOL(listener);
    return (T) this;
  }

  @Override
  public T append(char c, int count) {
    appendable.append(c, count);
    return (T) this;
  }

  @Override
  public T setIndentPrefix(CharSequence prefix) {
    appendable.setIndentPrefix(prefix);
    return (T) this;
  }

  @Override
  public T setOptions(int flags) {
    appendable.setOptions(flags);
    return (T) this;
  }

  @Override
  public T setPrefix(CharSequence prefix) {
    appendable.setPrefix(prefix);
    return (T) this;
  }

  @Override
  public T setPrefix(CharSequence prefix, boolean afterEol) {
    appendable.setPrefix(prefix, afterEol);
    return (T) this;
  }

  @Override
  public T unIndent() {
    appendable.unIndent();
    return (T) this;
  }

  @Override
  public T unIndentNoEol() {
    appendable.unIndentNoEol();
    return (T) this;
  }
}
