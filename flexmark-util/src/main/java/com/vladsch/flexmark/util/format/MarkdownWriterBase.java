package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.collection.BitEnumSet;
import com.vladsch.flexmark.util.html.LineFormattingAppendable;
import com.vladsch.flexmark.util.html.LineFormattingAppendableImpl;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@SuppressWarnings("unchecked")
public abstract class MarkdownWriterBase<M extends MarkdownWriterBase<M, N, C>, N, C extends NodeContext<N, C>> implements LineFormattingAppendable {
    protected final LineFormattingAppendable appendable;
    protected C context;

    public MarkdownWriterBase() {
        this(0);
    }

    @Override
    public String toString() {
        return appendable.toString();
    }

    public MarkdownWriterBase(int formatOptions) {
        this(formatOptions,null);
    }

    public MarkdownWriterBase(int formatOptions, @Nullable SequenceBuilder builder) {
        appendable = new LineFormattingAppendableImpl(builder, formatOptions);
        appendable.setOptions(appendable.getOptions() | LineFormattingAppendable.F_PREFIX_PRE_FORMATTED);
    }

    public void setContext(C context) {
        this.context = context;
    }

    public C getContext() {
        return context;
    }

    public M tailBlankLine() {
        return tailBlankLine(1);
    }

    public abstract boolean isLastBlockQuoteChild();
    public abstract M tailBlankLine(int count);

    // @formatter:off
    @Override public boolean isPendingSpace()                                                                                                                   { return appendable.isPendingSpace(); }
    @Override public boolean isPreFormatted()                                                                                                                   { return appendable.isPreFormatted(); }
    @Override public boolean isPreFormattedLine(int line)                                                                                                       { return appendable.isPreFormattedLine(line); }
    @NotNull @Override public CharSequence getIndentPrefix()                                                                                                    { return appendable.getIndentPrefix(); }
    @Override public CharSequence getPrefix()                                                                                                                   { return appendable.getPrefix(); }
    @Override public int column()                                                                                                                               { return appendable.column(); }
    @Override public int getLineCount()                                                                                                                         { return appendable.getLineCount(); }
    @Override public int getOptions()                                                                                                                           { return appendable.getOptions(); }
    @Override public int getPendingEOL()                                                                                                                        { return appendable.getPendingEOL(); }
    @Override public int getPendingSpace()                                                                                                                      { return appendable.getPendingSpace(); }
    @Override public int offset()                                                                                                                               { return appendable.offset(); }
    @Override public int offsetWithPending()                                                                                                                    { return appendable.offsetWithPending(); }
    @Override public int textOnlyOffset()                                                                                                                       { return appendable.textOnlyOffset(); }
    @Override public int textOnlyOffsetWithPending()                                                                                                            { return appendable.textOnlyOffsetWithPending(); }
    @Override public int getLinePrefixIndex(int lineIndex)                                                                                                      { return appendable.getLinePrefixIndex(lineIndex); }
    @Override public void setLinePrefixIndex(int lineIndex, int prefixEndIndex)                                                                                 { appendable.setLinePrefixIndex(lineIndex, prefixEndIndex); }
    @Override public void setLinePrefixIndex(int lineIndex, @NotNull CharSequence prefix, @NotNull CharSequence content)                                        { appendable.setLinePrefixIndex(lineIndex, prefix, content); }
    @NotNull @Override public int[] getLinesPrefixIndex(int startLine, int endLine)                                                                             { return appendable.getLinesPrefixIndex(startLine, endLine); }
    @NotNull @Override public CharSequence[] getLinesPrefix(int startLine, int endLine)                                                                         { return appendable.getLinesPrefix(startLine, endLine); }
    @NotNull @Override public CharSequence[] getLinesContent(int startLine, int endLine)                                                                        { return appendable.getLinesContent(startLine, endLine); }
    @NotNull @Override public CharSequence[] getLines(int startLine, int endLine)                                                                               { return appendable.getLines(startLine, endLine); }
    @Override @NotNull public BitEnumSet<Options> getOptionSet()                                                                                                { return appendable.getOptionSet();}
    @NotNull @Override public M addIndentOnFirstEOL(@NotNull Runnable runnable)                                                                                 { appendable.addIndentOnFirstEOL(runnable); return (M)this; }
    @NotNull @Override public M addPrefix(@NotNull CharSequence prefix)                                                                                         { appendable.addPrefix(prefix); return (M)this; }
    @NotNull @Override public M addPrefix(@NotNull CharSequence prefix, boolean afterEol)                                                                       { appendable.addPrefix(prefix, afterEol); return (M)this; }
    @NotNull @Override public M append(char c)                                                                                                                  { appendable.append(c); return (M)this; }
    @NotNull @Override public M append(@NotNull CharSequence csq)                                                                                               { appendable.append(csq); return (M)this; }
    @NotNull @Override public M append(@NotNull CharSequence csq, int start, int end)                                                                           { appendable.append(csq, start, end); return (M)this; }
    @NotNull @Override public M append(@NotNull LineFormattingAppendable lineAppendable, int startLine, int endLine)                                            { appendable.append(lineAppendable, startLine, endLine); return (M)this; }
    @NotNull @Override public M appendTo(@NotNull Appendable out, int maxBlankLines, CharSequence prefix, int startLine, int endLine) throws IOException        { appendable.appendTo(out, maxBlankLines, prefix, startLine, endLine); return (M)this; }
    @NotNull @Override public M blankLine()                                                                                                                     { appendable.blankLine(); return (M)this; }
    @NotNull @Override public M blankLine(int count)                                                                                                            { appendable.blankLine(count); return (M)this; }
    @NotNull @Override public M blankLineIf(boolean predicate)                                                                                                  { appendable.blankLineIf(predicate); return (M)this; }
    @NotNull @Override public M closePreFormatted()                                                                                                             { appendable.closePreFormatted(); return (M)this; }
    @NotNull @Override public M indent()                                                                                                                        { appendable.indent(); return (M)this; }
    @NotNull @Override public M line()                                                                                                                          { appendable.line(); return (M)this; }
    @NotNull @Override public M lineIf(boolean predicate)                                                                                                       { appendable.lineIf(predicate); return (M)this; }
    @NotNull @Override public M lineOnFirstText(boolean value)                                                                                                  { appendable.lineOnFirstText(value); return (M)this; }
    @NotNull @Override public M lineWithTrailingSpaces(int count)                                                                                               { appendable.lineWithTrailingSpaces(count); return (M)this; }
    @NotNull @Override public M openPreFormatted(boolean keepIndent)                                                                                            { appendable.openPreFormatted(keepIndent); return (M)this; }
    @NotNull @Override public M popPrefix()                                                                                                                     { appendable.popPrefix(); return (M)this; }
    @NotNull @Override public M popPrefix(boolean afterEol)                                                                                                     { appendable.popPrefix(afterEol); return (M)this; }
    @NotNull @Override public M prefixLinesWith(@NotNull CharSequence prefix, boolean addAfterLinePrefix, int startLine, int endLine)                           { appendable.prefixLinesWith(prefix, addAfterLinePrefix, startLine, endLine); return (M)this; }
    @NotNull @Override public M pushPrefix()                                                                                                                    { appendable.pushPrefix(); return (M)this; }
    @NotNull @Override public M removeIndentOnFirstEOL(@NotNull Runnable runnable)                                                                              { appendable.removeIndentOnFirstEOL(runnable); return (M)this; }
    @NotNull @Override public M removeLines(int startLine, int endLine)                                                                                         { appendable.removeLines(startLine, endLine); return (M)this; }
    @NotNull @Override public M append(char c, int count)                                                                                                       { appendable.append(c, count); return (M)this; }
    @NotNull @Override public M setIndentPrefix(@Nullable CharSequence prefix)                                                                                  { appendable.setIndentPrefix(prefix); return (M)this; }
    @NotNull @Override public M setOptions(int options)                                                                                                         { appendable.setOptions(options); return (M)this; }
    @NotNull @Override public M setPrefix(@NotNull CharSequence prefix)                                                                                         { appendable.setPrefix(prefix); return (M)this; }
    @NotNull @Override public M setPrefix(@Nullable CharSequence prefix, boolean afterEol)                                                                      { appendable.setPrefix(prefix, afterEol); return (M)this; }
    @NotNull @Override public M unIndent()                                                                                                                      { appendable.unIndent(); return (M)this; }
    @NotNull @Override public M unIndentNoEol()                                                                                                                 { appendable.unIndentNoEol(); return (M)this; }
    @Override public String toString(int maxBlankLines)                                                                                                         { return appendable.toString(maxBlankLines); }
    @Override public void toBuilder(@NotNull SequenceBuilder builder, int maxBlankLines)                                                                        { appendable.toBuilder(builder,maxBlankLines);}
    // @formatter:on
}

