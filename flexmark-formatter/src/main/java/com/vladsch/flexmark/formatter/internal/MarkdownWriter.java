package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.Consumer;
import com.vladsch.flexmark.util.Ref;
import com.vladsch.flexmark.util.html.ConditionalFormatter;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.html.FormattingAppendableImpl;

import java.io.IOException;

public class MarkdownWriter implements FormattingAppendable {
    private final FormattingAppendable myAppendable;
    private NodeFormatterContext context;

    public MarkdownWriter(Appendable out) {
        this(out, 0);
    }

    @Override
    public String toString() {
        return myAppendable.getAppendable().toString();
    }

    public MarkdownWriter(Appendable out, int formatOptions) {
        myAppendable = new FormattingAppendableImpl(out, formatOptions);
    }

    public void setContext(NodeFormatterContext context) {
        this.context = context;
    }

    public NodeFormatterContext getContext() {
        return context;
    }

    public MarkdownWriter tailBlankLine() {
        return tailBlankLine(1);
    }

    public boolean isLastBlockQuoteChild(Node node) {
        Node parent = node.getParent();
        return parent instanceof BlockQuote && parent.getLastChild() == node;
    }

    public MarkdownWriter tailBlankLine(int count) {
        Node node = context.getCurrentNode();
        if (isLastBlockQuoteChild(node)) {
            if (getPushedPrefixCount() > 0) {
                flush(-1); // clear pending lines so pop prefix is not delayed, if PREFIX_AFTER_PENDING_EOL is enabled
                popPrefix();
                pushPrefix();
            }
        }
        blankLine(count);
        return this;
    }

    public MarkdownWriter appendNonTranslating(final CharSequence csq) {
        return appendNonTranslating(null, csq, null, null);
    }

    public MarkdownWriter appendNonTranslating(final CharSequence prefix, final CharSequence csq) {
        return appendNonTranslating(prefix, csq, null, null);
    }

    public MarkdownWriter appendNonTranslating(final CharSequence prefix, final CharSequence csq, final CharSequence suffix) {
        return appendNonTranslating(prefix, csq, suffix, null);
    }

    public MarkdownWriter appendNonTranslating(final CharSequence prefix, final CharSequence csq, final CharSequence suffix, final CharSequence suffix2) {
        if (context.isTransformingText()) {
            append(context.transformNonTranslating(prefix, csq, suffix, suffix2));
        } else {
            append(csq);
        }
        return this;
    }

    public MarkdownWriter appendNonTranslating(final CharSequence prefix, final CharSequence csq, final CharSequence suffix, final CharSequence suffix2, Consumer<String> placeholderConsumer) {
        if (context.isTransformingText()) {
            append(context.transformNonTranslating(prefix, csq, suffix, suffix2));
        } else {
            append(csq);
        }
        return this;
    }

    public MarkdownWriter appendTranslating(final CharSequence csq) {
        return appendTranslating(null, csq, null, null);
    }

    public MarkdownWriter appendTranslating(final CharSequence prefix, final CharSequence csq) {
        return appendTranslating(prefix, csq, null, null);
    }

    public MarkdownWriter appendTranslating(final CharSequence prefix, final CharSequence csq, final CharSequence suffix) {
        return appendTranslating(prefix, csq, suffix, null);
    }

    public MarkdownWriter appendTranslating(final CharSequence prefix, final CharSequence csq, final CharSequence suffix, final CharSequence suffix2) {
        if (context.isTransformingText()) {
            append(context.transformTranslating(prefix, csq, suffix, suffix2));
        } else {
            if (prefix != null) append(prefix);
            append(csq);
            if (suffix != null) append(suffix);
        }
        return this;
    }

    // @formatter:off
    @Override public Appendable getAppendable()                                                                                 { return myAppendable.getAppendable(); }
    @Override public boolean isPendingEOL()                                                                                     { return myAppendable.isPendingEOL(); }
    @Override public boolean isPendingSpace()                                                                                   { return myAppendable.isPendingSpace(); }
    @Override public boolean isPreFormatted()                                                                                   { return myAppendable.isPreFormatted(); }
    @Override public CharSequence getIndentPrefix()                                                                             { return myAppendable.getIndentPrefix(); }
    @Override public CharSequence getPrefix()                                                                                   { return myAppendable.getPrefix(); }
    @Override public CharSequence getTotalIndentPrefix()                                                                        { return myAppendable.getTotalIndentPrefix(); }
    @Override public int getIndent()                                                                                            { return myAppendable.getIndent(); }
    @Override public int getLineCount()                                                                                         { return myAppendable.getLineCount(); }
    @Override public int getModCount()                                                                                          { return myAppendable.getModCount(); }
    @Override public int offset()                                                                                               { return myAppendable.offset(); }
    @Override public int offsetWithPending()                                                                                    { return myAppendable.offsetWithPending(); }
    @Override public int column()                                                                                               { return myAppendable.column(); }
    @Override public int columnWith(final CharSequence csq, final int start, final int end)                                     { return myAppendable.columnWith(csq,start,end); }
    @Override public int lastOffset()                                                                                           { return myAppendable.lastOffset(); }
    @Override public int getOptions()                                                                                           { return myAppendable.getOptions(); }
    @Override public int getPushedPrefixCount()                                                                                 { return myAppendable.getPushedPrefixCount(); }
    @Override public int getPendingEOL()                                                                                        { return myAppendable.getPendingEOL(); }
    @Override public IOException getIOException()                                                                               { return myAppendable.getIOException(); }
    @Override public String getText()                                                                                           { return myAppendable.getText(); }
    @Override public String getText(final int maxBlankLines)                                                                    { return myAppendable.getText(maxBlankLines); }

    @Override public MarkdownWriter addAfterEolRunnable(final int atPendingEOL, final Runnable runnable)                        { myAppendable.addAfterEolRunnable(atPendingEOL, runnable); return this; }
    @Override public MarkdownWriter addLine()                                                                                   { myAppendable.addLine(); return this; }
    @Override public MarkdownWriter addPrefix(final CharSequence prefix)                                                        { myAppendable.addPrefix(prefix); return this; }
    @Override public MarkdownWriter append(final char c)                                                                        { myAppendable.append(c); return this; }
    @Override public MarkdownWriter append(final CharSequence csq)                                                              { myAppendable.append(csq); return this; }
    @Override public MarkdownWriter append(final CharSequence csq, final int start, final int end)                              { myAppendable.append(csq, start, end); return this; }
    @Override public MarkdownWriter blankLine()                                                                                 { myAppendable.blankLine(); return this; }
    @Override public MarkdownWriter blankLine(final int count)                                                                  { myAppendable.blankLine(count); return this; }
    @Override public MarkdownWriter blankLineIf(final boolean predicate)                                                        { myAppendable.blankLineIf(predicate); return this; }
    @Override public MarkdownWriter closeConditional(final ConditionalFormatter closeFormatter)                                 { myAppendable.closeConditional(closeFormatter); return this; }
    @Override public MarkdownWriter closePreFormatted()                                                                         { myAppendable.closePreFormatted(); return this; }
    @Override public MarkdownWriter flush()                                                                                     { myAppendable.flush(); return this; }
    @Override public MarkdownWriter flushWhitespaces()                                                                          { myAppendable.flushWhitespaces(); return this; }
    @Override public MarkdownWriter flush(final int maxBlankLines)                                                              { myAppendable.flush(maxBlankLines); return this; }
    @Override public MarkdownWriter lastOffset(final Ref<Integer> refOffset)                                                    { myAppendable.lastOffset(refOffset); return this; }
    @Override public MarkdownWriter indent()                                                                                    { myAppendable.indent(); return this; }
    @Override public MarkdownWriter line()                                                                                      { myAppendable.line(); return this; }
    @Override public MarkdownWriter line(final Ref<Boolean> lineRef)                                                            { myAppendable.line(lineRef); return this; }
    @Override public MarkdownWriter lineIf(final boolean predicate)                                                             { myAppendable.lineIf(predicate); return this; }
    @Override public MarkdownWriter lineIf(final Ref<Boolean> lineRef)                                                          { myAppendable.lineIf(lineRef); return this; }
    @Override public MarkdownWriter openConditional(final ConditionalFormatter openFormatter)                                   { myAppendable.openConditional(openFormatter); return this; }
    @Override public MarkdownWriter openPreFormatted(final boolean keepIndent)                                                  { myAppendable.openPreFormatted(keepIndent); return this; }
    @Override public MarkdownWriter popPrefix()                                                                                 { myAppendable.popPrefix(); return this; }
    @Override public MarkdownWriter pushPrefix()                                                                                { myAppendable.pushPrefix(); return this; }
    @Override public MarkdownWriter repeat(final char c, final int count)                                                       { myAppendable.repeat(c, count); return this; }
    @Override public MarkdownWriter repeat(final CharSequence csq, final int count)                                             { myAppendable.repeat(csq, count); return this; }
    @Override public MarkdownWriter repeat(final CharSequence csq, final int start, final int end, final int count)             { myAppendable.repeat(csq, start, end, count); return this; }
    @Override public MarkdownWriter setIndentOffset(final int indentOffset)                                                     { myAppendable.setIndentOffset(indentOffset); return this; }
    @Override public MarkdownWriter setIndentPrefix(final CharSequence prefix)                                                  { myAppendable.setIndentPrefix(prefix); return this; }
    @Override public MarkdownWriter setOptions(final int options)                                                               { myAppendable.setOptions(options); return this; }
    @Override public MarkdownWriter setPrefix(final CharSequence prefix)                                                        { myAppendable.setPrefix(prefix); return this; }
    @Override public MarkdownWriter unIndent()                                                                                  { myAppendable.unIndent(); return this; }
    @Override public MarkdownWriter willIndent()                                                                                { myAppendable.willIndent(); return this; }
    // @formatter:on
}

