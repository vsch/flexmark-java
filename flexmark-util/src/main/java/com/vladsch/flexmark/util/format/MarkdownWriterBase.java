package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.html.LineFormattingAppendable;
import com.vladsch.flexmark.util.html.LineFormattingAppendableImpl;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public abstract class MarkdownWriterBase<M extends MarkdownWriterBase<M, N, C>, N, C extends NodeContext<N, C>> implements LineFormattingAppendable {
    protected final LineFormattingAppendable myAppendable;
    protected C context;

    public MarkdownWriterBase() {
        this(0);
    }

    @Override
    public String toString() {
        return myAppendable.toString();
    }

    public MarkdownWriterBase(int formatOptions) {
        this(formatOptions,null);
    }

    public MarkdownWriterBase(int formatOptions, @Nullable SequenceBuilder builder) {
        myAppendable = new LineFormattingAppendableImpl(formatOptions, builder);
        myAppendable.setOptions(myAppendable.getOptions() | LineFormattingAppendable.PREFIX_PRE_FORMATTED);
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
    @Override public boolean isPendingSpace()                                                                                                       { return myAppendable.isPendingSpace(); }
    @Override public boolean isPreFormatted()                                                                                                       { return myAppendable.isPreFormatted(); }
    @Override public boolean isPreFormattedLine(int line)                                                                                           { return myAppendable.isPreFormattedLine(line); }
    @NotNull@Override public CharSequence getIndentPrefix()                                                                                                 { return myAppendable.getIndentPrefix(); }
    @Override public CharSequence getPrefix()                                                                                                       { return myAppendable.getPrefix(); }
    @Override public int column()                                                                                                                   { return myAppendable.column(); }
    @Override public int getLineCount()                                                                                                             { return myAppendable.getLineCount(); }
    @Override public int getOptions()                                                                                                               { return myAppendable.getOptions(); }
    @Override public int getPendingEOL()                                                                                                            { return myAppendable.getPendingEOL(); }
    @Override public int getPendingSpace()                                                                                                          { return myAppendable.getPendingSpace(); }
    @Override public int offset()                                                                                                                   { return myAppendable.offset(); }
    @Override public int offsetWithPending()                                                                                                        { return myAppendable.offsetWithPending(); }
    @Override public int textOnlyOffset()                                                                                                           { return myAppendable.textOnlyOffset(); }
    @Override public int textOnlyOffsetWithPending()                                                                                                { return myAppendable.textOnlyOffsetWithPending(); }
    @NotNull@Override public List<BasedSequence> getLinePrefixes(int startLine, int endLine)                                                                { return myAppendable.getLinePrefixes(startLine, endLine); }
    @NotNull@Override public List<CharSequence> getLineContents(int startLine, int endLine)                                                                 { return myAppendable.getLineContents(startLine, endLine); }
    @NotNull@Override public List<CharSequence> getLines(int startLine, int endLine)                                                                        { return myAppendable.getLines(startLine, endLine); }
    @NotNull@Override public M addIndentOnFirstEOL(@NotNull Runnable runnable)                                                                          { myAppendable.addIndentOnFirstEOL(runnable); return (M)this; }
    @NotNull@Override public M addLine()                                                                                                       { myAppendable.addLine(); return (M)this; }
    @NotNull@Override public M addPrefix(@NotNull CharSequence prefix)                                                                                  { myAppendable.addPrefix(prefix); return (M)this; }
    @NotNull@Override public M addPrefix(@NotNull CharSequence prefix, boolean afterEol)                                                                { myAppendable.addPrefix(prefix, afterEol); return (M)this; }
    @NotNull@Override public M append(char c)                                                                                                  { myAppendable.append(c); return (M)this; }
    @NotNull@Override public M append(@NotNull CharSequence csq)                                                                                        { myAppendable.append(csq); return (M)this; }
    @NotNull@Override public M append(@NotNull CharSequence csq, int start, int end)                                                                    { myAppendable.append(csq, start, end); return (M)this; }
    @NotNull@Override public M append(@NotNull LineFormattingAppendable lineAppendable, int startLine, int endLine)                                     { myAppendable.append(lineAppendable, startLine, endLine); return (M)this; }
    @NotNull@Override public M appendTo(@NotNull Appendable out, int maxBlankLines, CharSequence prefix, int startLine, int endLine) throws IOException { myAppendable.appendTo(out, maxBlankLines, prefix, startLine, endLine); return (M)this; }
    @NotNull@Override public M blankLine()                                                                                                     { myAppendable.blankLine(); return (M)this; }
    @NotNull@Override public M blankLine(int count)                                                                                            { myAppendable.blankLine(count); return (M)this; }
    @NotNull@Override public M blankLineIf(boolean predicate)                                                                                  { myAppendable.blankLineIf(predicate); return (M)this; }
    @NotNull@Override public M closePreFormatted()                                                                                             { myAppendable.closePreFormatted(); return (M)this; }
    @NotNull@Override public M indent()                                                                                                        { myAppendable.indent(); return (M)this; }
    @NotNull@Override public M line()                                                                                                          { myAppendable.line(); return (M)this; }
    @NotNull@Override public M lineIf(boolean predicate)                                                                                       { myAppendable.lineIf(predicate); return (M)this; }
    @NotNull@Override public M lineOnFirstText(boolean value)                                                                                  { myAppendable.lineOnFirstText(value); return (M)this; }
    @NotNull@Override public M lineWithTrailingSpaces(int count)                                                                               { myAppendable.lineWithTrailingSpaces(count); return (M)this; }
    @NotNull@Override public M openPreFormatted(boolean keepIndent)                                                                            { myAppendable.openPreFormatted(keepIndent); return (M)this; }
    @NotNull@Override public M popPrefix()                                                                                                     { myAppendable.popPrefix(); return (M)this; }
    @NotNull@Override public M popPrefix(boolean afterEol)                                                                                     { myAppendable.popPrefix(afterEol); return (M)this; }
    @NotNull@Override public M prefixLines(@NotNull CharSequence prefix, boolean addAfterLinePrefix, int startLine, int endLine)                        { myAppendable.prefixLines(prefix, addAfterLinePrefix, startLine, endLine); return (M)this; }
    @NotNull@Override public M pushPrefix()                                                                                                    { myAppendable.pushPrefix(); return (M)this; }
    @NotNull@Override public M removeIndentOnFirstEOL(@NotNull Runnable runnable)                                                                       { myAppendable.removeIndentOnFirstEOL(runnable); return (M)this; }
    @NotNull@Override public M removeLines(int startLine, int endLine)                                                                         { myAppendable.removeLines(startLine, endLine); return (M)this; }
    @NotNull@Override public M repeat(char c, int count)                                                                                       { myAppendable.repeat(c, count); return (M)this; }
    @NotNull@Override public M repeat(@NotNull CharSequence csq, int count)                                                                             { myAppendable.repeat(csq, count); return (M)this; }
    @NotNull@Override public M repeat(@NotNull CharSequence csq, int start, int end, int count)                                                         { myAppendable.repeat(csq, start, end, count); return (M)this; }
    @NotNull@Override public M setIndentPrefix(@NotNull CharSequence prefix)                                                                            { myAppendable.setIndentPrefix(prefix); return (M)this; }
    @NotNull@Override public M setOptions(int options)                                                                                         { myAppendable.setOptions(options); return (M)this; }
    @NotNull@Override public M setPrefix(@NotNull CharSequence prefix)                                                                                  { myAppendable.setPrefix(prefix); return (M)this; }
    @NotNull@Override public M setPrefix(@NotNull CharSequence prefix, boolean afterEol)                                                                { myAppendable.setPrefix(prefix, afterEol); return (M)this; }
    @NotNull@Override public M unIndent()                                                                                                      { myAppendable.unIndent(); return (M)this; }
    @NotNull@Override public M unIndentNoEol()                                                                                                 { myAppendable.unIndentNoEol(); return (M)this; }
    @Override public String toString(int maxBlankLines)                                                                                { return myAppendable.toString(maxBlankLines); }
    @Override public void toBuilder(@NotNull SequenceBuilder builder, int maxBlankLines)                                          { myAppendable.toBuilder(builder,maxBlankLines);}
    // @formatter:on
}

