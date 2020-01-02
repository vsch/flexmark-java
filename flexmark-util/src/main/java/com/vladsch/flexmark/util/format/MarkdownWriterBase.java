package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.collection.BitFieldSet;
import com.vladsch.flexmark.util.html.LineAppendable;
import com.vladsch.flexmark.util.html.LineAppendableImpl;
import com.vladsch.flexmark.util.html.LineInfo;
import com.vladsch.flexmark.util.html.LineProcessor;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.StringSequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@SuppressWarnings("unchecked")
public abstract class MarkdownWriterBase<T extends MarkdownWriterBase<T, N, C>, N, C extends NodeContext<N, C>> implements LineAppendable {
    protected final LineAppendableImpl appendable;
    protected C context;

    public MarkdownWriterBase() {
        this(0);
    }

    @Override
    public String toString() {
        return appendable.toString();
    }

    public MarkdownWriterBase(int formatOptions) {
        this(StringSequenceBuilder.emptyBuilder(), formatOptions);
    }

    public MarkdownWriterBase(@NotNull ISequenceBuilder<?, ?> builder, int formatOptions) {
        appendable = new LineAppendableImpl(builder, formatOptions);
        appendable.setOptions(appendable.getOptions() | LineAppendable.F_PREFIX_PRE_FORMATTED);
    }

    public void setContext(C context) {
        this.context = context;
    }

    public C getContext() {
        return context;
    }

    @SuppressWarnings("UnusedReturnValue")
    @NotNull
    public T tailBlankLine() {
        return tailBlankLine(1);
    }

    abstract public boolean isLastBlockQuoteChild();

    @NotNull
    abstract public T tailBlankLine(int count);

    // @formatter:off
    @Override public void setPrefixLength(int lineIndex, int prefixEndIndex)                                                    { appendable.setPrefixLength(lineIndex, prefixEndIndex); }
    @Override public void setLine(int lineIndex, @NotNull CharSequence prefix, @NotNull CharSequence text)                   { appendable.setLine(lineIndex, prefix, text); }
    @Override public void forAllLines(int maxTrailingBlankLines, int startLine, int endLine, @NotNull LineProcessor processor)  { appendable.forAllLines(maxTrailingBlankLines, 0, Integer.MAX_VALUE, processor ); }
    @Override public <T extends Appendable> T appendTo(@NotNull T out, boolean withPrefixes, int maxBlankLines, int maxTrailingBlankLines, int startLine, int endLine) throws IOException { return appendable.appendTo(out, withPrefixes, maxBlankLines, maxTrailingBlankLines, startLine, endLine); }
    @Override public boolean isPendingSpace()                                                                                   { return appendable.isPendingSpace(); }
    @Override public boolean isPreFormatted()                                                                                   { return appendable.isPreFormatted(); }
    @Override public int column()                                                                                               { return appendable.column(); }
    @Override public int getLineCount()                                                                                         { return appendable.getLineCount(); }
    @Override public int getOptions()                                                                                           { return appendable.getOptions(); }
    @Override public int getPendingSpace()                                                                                      { return appendable.getPendingSpace(); }
    @Override public int getPendingEOL()                                                                                        { return appendable.getPendingEOL(); }
    @Override public int offset()                                                                                               { return appendable.offset(); }
    @Override public int offsetWithPending()                                                                                    { return appendable.offsetWithPending(); }
    @Override public int getAfterEolPrefixDelta()                                                                               { return appendable.getAfterEolPrefixDelta(); }
    @NotNull @Override public ISequenceBuilder<?,?> getBuilder()                                                                { return appendable.getBuilder(); }
    @NotNull @Override public CharSequence getPrefix()                                                                          { return appendable.getPrefix(); }
    @NotNull @Override public CharSequence getBeforeEolPrefix()                                                                 { return appendable.getBeforeEolPrefix(); }
    @NotNull @Override public LineInfo getLineInfo(int lineIndex)                                                               { return appendable.getLineInfo(lineIndex); }
    @NotNull @Override public  BasedSequence getLine(int lineIndex)                                                             { return appendable.getLine(lineIndex); }
    @NotNull @Override public CharSequence getIndentPrefix()                                                                    { return appendable.getIndentPrefix(); }
    @NotNull @Override public CharSequence toSequence(boolean withPrefixes, int maxBlankLines, int maxTrailingBlankLines)       { return appendable.toSequence(withPrefixes, maxBlankLines, maxTrailingBlankLines); }
    @NotNull @Override public String toString(boolean withPrefixes, int maxBlankLines, int maxTrailingBlankLines)               { return appendable.toString(withPrefixes, maxBlankLines, maxTrailingBlankLines); }
    @NotNull @Override public BitFieldSet<Options> getOptionSet()                                                               { return appendable.getOptionSet();}
    @NotNull @Override public T normalizeTo(int maxBlankLines, int maxTrailingBlankLines, int startLine, int endLine)           { appendable.normalizeTo(maxBlankLines, maxTrailingBlankLines, startLine, endLine); return (T) this; }
    @NotNull @Override public T removeLines(int startLine, int endLine)                                                         { appendable.removeLines(startLine, endLine); return (T) this; }
    @NotNull @Override public T pushOptions()                                                                                   { appendable.pushOptions();  return (T) this; }
    @NotNull @Override public T popOptions()                                                                                    { appendable.popOptions();  return (T) this; }
    @NotNull @Override public T changeOptions(int addFlags, int removeFlags)                                                    { appendable.changeOptions(addFlags, removeFlags);  return (T) this; }
    @NotNull @Override public T addIndentOnFirstEOL(@NotNull Runnable runnable)                                                 { appendable.addIndentOnFirstEOL(runnable);  return (T)this; }
    @NotNull @Override public T addPrefix(@NotNull CharSequence prefix)                                                         { appendable.addPrefix(prefix); return (T)this; }
    @NotNull @Override public T addPrefix(@NotNull CharSequence prefix, boolean afterEol)                                       { appendable.addPrefix(prefix, afterEol);  return (T)this; }
    @NotNull @Override public T append(char c)                                                                                  { appendable.append(c); return (T)this; }
    @NotNull @Override public T append(@NotNull CharSequence csq)                                                               { appendable.append(csq); return (T)this; }
    @NotNull @Override public T append(@NotNull CharSequence csq, int start, int end)                                           { appendable.append(csq, start, end); return (T)this; }
    @NotNull @Override public T append(@NotNull LineAppendable lines, int startLine, int endLine)                               { appendable.append(lines, startLine, endLine);  return (T)this; }
    @NotNull @Override public T blankLine()                                                                                     { appendable.blankLine(); return (T)this; }
    @NotNull @Override public T blankLine(int count)                                                                            { appendable.blankLine(count); return (T)this; }
    @NotNull @Override public T blankLineIf(boolean predicate)                                                                  { appendable.blankLineIf(predicate); return (T)this; }
    @NotNull @Override public T closePreFormatted()                                                                             { appendable.closePreFormatted(); return (T)this; }
    @NotNull @Override public T indent()                                                                                        { appendable.indent(); return (T)this; }
    @NotNull @Override public T line()                                                                                          { appendable.line(); return (T)this; }
    @NotNull @Override public T lineIf(boolean predicate)                                                                       { appendable.lineIf(predicate); return (T)this; }
    @NotNull @Override public T lineOnFirstText(boolean value)                                                                  { appendable.lineOnFirstText(value);  return (T)this; }
    @NotNull @Override public T lineWithTrailingSpaces(int count)                                                               { appendable.lineWithTrailingSpaces(count);  return (T)this; }
    @NotNull @Override public T openPreFormatted(boolean keepIndent)                                                            { appendable.openPreFormatted(true); return (T)this; }
    @NotNull @Override public T popPrefix()                                                                                     { appendable.popPrefix(); return (T)this; }
    @NotNull @Override public T popPrefix(boolean afterEol)                                                                     { appendable.popPrefix(afterEol); return (T)this; }
    @NotNull @Override public T pushPrefix()                                                                                    { appendable.pushPrefix(); return (T)this; }
    @NotNull @Override public T removeIndentOnFirstEOL(@NotNull Runnable runnable)                                              { appendable.removeIndentOnFirstEOL(runnable);  return (T)this; }
    @NotNull @Override public T append(char c, int count)                                                                       { appendable.append(c, count); return (T)this; }
    @NotNull @Override public T setIndentPrefix(@Nullable CharSequence prefix)                                                  { appendable.setIndentPrefix(prefix); return (T)this; }
    @NotNull @Override public T setOptions(int flags)                                                                           { appendable.setOptions(flags); return (T)this; }
    @NotNull @Override public T setPrefix(@NotNull CharSequence prefix)                                                         { appendable.setPrefix(prefix); return (T)this; }
    @NotNull @Override public T setPrefix(@Nullable CharSequence prefix, boolean afterEol)                                      { appendable.setPrefix(prefix, afterEol); return (T)this; }
    @NotNull @Override public T unIndent()                                                                                      { appendable.unIndent(); return (T)this; }
    @NotNull @Override public T unIndentNoEol()                                                                                 { appendable.unIndentNoEol();  return (T)this; }
    // @formatter:on
}

