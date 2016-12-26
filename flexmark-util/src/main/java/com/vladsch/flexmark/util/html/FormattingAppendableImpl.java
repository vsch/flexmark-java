package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Ref;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import com.vladsch.flexmark.util.sequence.CharSubSequence;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequence;

import java.io.IOException;
import java.util.Stack;

public class FormattingAppendableImpl implements FormattingAppendable {
    private final Appendable myAppendable;
    private final Stack<ConditionalFrame> myConditionalFrames;
    private final Stack<Integer> myIndentLineCounts;
    private final char myEOL;

    private String myWhitespace;
    private String myWhitespaceEOL;
    private int myOptions;

    // IOExceptions are not thrown, the first one sets this member
    private IOException myIOException;

    // count of append calls, only useful for checking if real data was output since this value was taken last
    private int myModCount;
    private int myOffsetBefore;
    private int myOffsetAfter;

    // pending new lines, 1+ for every pending blank line
    private int myPendingEOL;
    private boolean myPendingPreFormattedPrefix;
    private int myLineCount;
    private int myModCountOfLastEOL;

    // indent level to use after the next \n and before text is appended
    private int myIndent;
    private boolean myWillIndent;
    private int myIndentOffset;
    private BasedSequence myPrefix;
    private BasedSequence myIndentPrefix;

    // pre-formatted nesting level, while >0 all text is passed through as is and not monitored
    private int myPreFormattedNesting;

    // accumulated spaces and tabs that we were not sure would need to be output
    private int myPendingSpaces;

    @SuppressWarnings("WeakerAccess")
    public FormattingAppendableImpl(final Appendable appendable, final boolean allFormatOptions) {
        this(appendable, allFormatOptions ? FORMAT_ALL : 0);
    }

    public FormattingAppendableImpl(final Appendable appendable, final int formatOptions) {
        myAppendable = appendable;
        myConditionalFrames = new Stack<>();
        myIndentLineCounts = new Stack<>();
        myEOL = '\n';
        myOptions = formatOptions;
        myIOException = null;
        myModCount = 0;
        myOffsetBefore = 0;
        myOffsetAfter = 0;
        myPendingEOL = 0;
        myPendingPreFormattedPrefix = false;
        myLineCount = 0;
        myModCountOfLastEOL = 0;
        myIndent = 0;
        myWillIndent = false;
        myIndentOffset = 0;
        myPrefix = BasedSequence.NULL;
        myIndentPrefix = BasedSequence.NULL;
        myPreFormattedNesting = 0;

        setWhitespace();
    }

    private void setWhitespace() {
        myWhitespace = isConvertingTabs() ? BasedSequence.WHITESPACE_NO_EOL_CHARS : " ";
        myWhitespaceEOL = isConvertingTabs() ? BasedSequence.WHITESPACE_CHARS : " \n";
    }

    @Override
    public int getOptions() {
        return myOptions;
    }

    @Override
    public FormattingAppendable setOptions(final int options) {
        myOptions = options;
        setWhitespace();
        return this;
    }

    private boolean haveOptions(int options) {
        return (myOptions & options) != 0;
    }

    private boolean isConvertingTabs() {
        return haveOptions(CONVERT_TABS | COLLAPSE_WHITESPACE);
    }

    private boolean isSuppressingTrailingWhitespace() {
        return haveOptions(SUPPRESS_TRAILING_WHITESPACE);
    }

    private boolean isCollapseWhitespace() {
        return haveOptions(COLLAPSE_WHITESPACE);
    }

    private void appendIndent() throws IOException {
        if (!myPrefix.isEmpty()) {
            myAppendable.append(myPrefix);
            myOffsetAfter += myPrefix.length();
        }

        if (myIndent + myIndentOffset > 0 && !myIndentPrefix.isEmpty()) {
            for (int i = 0; i < myIndent + myIndentOffset; i++) {
                myAppendable.append(myIndentPrefix);
                myOffsetAfter += myIndentPrefix.length();
            }
        }
    }

    private void addPendingSpaces(final int count) {
        if (count > 0) {
            if (myPreFormattedNesting == 0 && (myPendingEOL == 0 && myModCountOfLastEOL != myModCount)) {
                if (isCollapseWhitespace()) {
                    if (myPendingSpaces == 0) {
                        myPendingSpaces = 1;
                    }
                } else {
                    myPendingSpaces += count;
                }
            }
        }
    }

    private void appendSpaces() throws IOException {
        if (myPendingSpaces > 0) {
            while (myPendingSpaces > 0) {
                myAppendable.append(' ');
                myPendingSpaces--;
                myOffsetAfter++;
            }

            myModCount++;
        }
    }

    private void setPendingEOL(int pendingEOL) {
        if (myPreFormattedNesting == 0 && myModCountOfLastEOL != myModCount && pendingEOL > myPendingEOL) {
            myPendingEOL = pendingEOL;
        }
    }

    private void resetPendingEOL() {
        myPendingEOL = 0;
        myPendingSpaces = 0;
        myModCountOfLastEOL = myModCount;
    }

    private void appendEOL(final boolean withIndent, final boolean withPendingSpaces) throws IOException {
        if (myPendingEOL > 0) {
            if (myPendingSpaces > 0 && !isSuppressingTrailingWhitespace()) {
                appendSpaces();
            }

            while (myPendingEOL > 0) {
                myAppendable.append(myEOL);
                myLineCount++;
                myPendingEOL--;
                myOffsetAfter++;

                if (myPendingEOL > 0 && !myPrefix.isEmpty()) {
                    myAppendable.append(myPrefix);
                    myOffsetAfter += myPrefix.length();
                }
            }

            resetPendingEOL();

            if (withIndent) appendIndent();
        } else {
            if (myModCountOfLastEOL == myModCount) {
                myPendingSpaces = 0;
                if (withIndent) appendIndent();
            } else {
                if (withPendingSpaces) {
                    appendSpaces();
                }
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void beforeAppendText(final boolean withEOL, final boolean withIndent, final boolean withSpaces) throws IOException {
        if (myConditionalFrames.size() > 0) {
            // have conditional
            ConditionalFrame frame = myConditionalFrames.peek();
            if (!frame.myInFormatter) {
                // hasn't fired yet, fire it, add modCount so it does not run again unless it is an indent
                boolean firstApply = frame.myModCount == myModCount;
                if (firstApply) {
                    myModCount++;
                }

                if (firstApply || !frame.myOnIndent && (myWillIndent || frame.myIndent < myIndent)) {
                    frame.myInFormatter = true;

                    frame.myOnIndent = myWillIndent || frame.myIndent < myIndent;
                    frame.myOnLine = frame.myLineCount < myLineCount + myPendingEOL;
                    int indent = myIndent;
                    // restore indent to what it was for the frame
                    myIndent = frame.myIndent;
                    // reset any pending EOLs to allow conditional to apply one if it sees fit
                    myPendingEOL = 0;
                    int lineCount = myLineCount;

                    frame.myOpenFormatter.apply(firstApply, frame.myOnIndent, frame.myOnLine, true);

                    // restore indent increase before apply to indent after apply
                    myIndent += indent - frame.myIndent;

                    // reset to false if the line count has not changed, ie. conditional formatter suppressed the new line
                    if (frame.myLineRef != null && firstApply) frame.myLineRef.value = lineCount != myLineCount;

                    frame.myInFormatter = false;
                }
            }
        }

        if (withEOL) appendEOL(withIndent, withSpaces);
        else if (withSpaces) appendSpaces();
    }

    private void appendImpl(final char c) throws IOException {
        if (myPreFormattedNesting > 0) {
            myOffsetBefore = myOffsetAfter;

            if (myPendingPreFormattedPrefix && !myPrefix.isEmpty()) {
                myAppendable.append(myPrefix);
                myOffsetAfter += myPrefix.length();
            }
            myPendingPreFormattedPrefix = false;

            myAppendable.append(c);
            myOffsetAfter++;
            myModCount++;

            if (c == myEOL) {
                myLineCount++;
                myPendingPreFormattedPrefix = true;
            }

            resetPendingEOL();
        } else {
            if (c == myEOL) {
                setPendingEOL(1);
            } else {
                if (myWhitespace.indexOf(c) != -1) {
                    addPendingSpaces(1);
                } else {
                    beforeAppendText(true, true, true);
                    myOffsetBefore = myOffsetAfter;
                    myAppendable.append(c);
                    myOffsetAfter++;
                    myModCount++;
                }
            }
        }
    }

    private void appendImpl(final CharSequence csq, final int start, final int end) throws IOException {
        int lastPos = start;
        BasedSequence seq = BasedSequenceImpl.of(csq);

        if (myPreFormattedNesting > 0) {
            myOffsetBefore = myOffsetAfter;

            while (lastPos < end) {
                int pos = seq.indexOf(myEOL, lastPos, end);
                int endPos = pos == -1 ? csq.length() : pos + 1;

                if (lastPos < endPos) {
                    if (myPendingPreFormattedPrefix && !myPrefix.isEmpty()) {
                        myAppendable.append(myPrefix);
                        myOffsetAfter += myPrefix.length();
                    }
                    myPendingPreFormattedPrefix = false;

                    myAppendable.append(csq, lastPos, endPos);
                    myOffsetAfter += endPos - lastPos;
                }

                if (pos == -1) break;

                myLineCount++;
                myPendingPreFormattedPrefix = true;

                lastPos = endPos;
            }
            myModCount++;
            // if EOL is last then we reset pending and eol mod count
            if (lastPos == end) resetPendingEOL();
        } else {
            // have to handle \n, white spaces, etc
            boolean firstAppend = true;
            while (lastPos < end) {
                int pos = seq.indexOfAny(myWhitespaceEOL, lastPos, end);

                // output what has accumulated before
                int spanEnd = pos == -1 ? end : pos;

                if (lastPos < spanEnd) {
                    beforeAppendText(true, true, true);
                    if (firstAppend) {
                        myOffsetBefore = myOffsetAfter;
                        firstAppend = false;
                    }
                    myAppendable.append(csq, lastPos, spanEnd);
                    myModCount++;
                    myOffsetAfter += spanEnd - lastPos;
                }

                if (pos == -1) break;

                // spaces and tabs are only output if we don't have a pending EOL and they don't come before an EOL
                int span = seq.countChars(myWhitespaceEOL, pos, end);
                if (myPendingEOL == 0) {
                    if (seq.indexOf(myEOL, pos, pos + span) != -1) {
                        // we don't output the spaces after the EOL but make EOL pending
                        setPendingEOL(1);
                    } else {
                        // no eol, make spaces pending
                        addPendingSpaces(span);
                    }
                } else {
                    // we are coming after an EOL, all whitespaces are ignored
                }

                pos += span;
                lastPos = pos;
            }
        }
    }

    @Override
    public IOException getIOException() {
        return myIOException;
    }

    private void setIOException(IOException e) {
        if (myIOException == null) {
            myIOException = e;
        }
    }

    @Override
    public FormattingAppendable append(final CharSequence csq) {
        try {
            if (myIOException == null) appendImpl(csq, 0, csq.length());
        } catch (IOException e) {
            setIOException(e);
        }
        return this;
    }

    @Override
    public FormattingAppendable append(final CharSequence csq, final int start, final int end) {
        try {
            if (myIOException == null) appendImpl(csq, start, end);
        } catch (IOException e) {
            setIOException(e);
        }
        return this;
    }

    @Override
    public FormattingAppendable append(final char c) {
        try {
            if (myIOException == null) appendImpl(c);
        } catch (IOException e) {
            setIOException(e);
        }
        return this;
    }

    @Override
    public Appendable getAppendable() {
        return myAppendable;
    }

    @Override
    public FormattingAppendable flush() {
        return flush(0);
    }

    @Override
    public FormattingAppendable flush(final int maxBlankLines) {
        assert myConditionalFrames.size() == 0;
        assert myPreFormattedNesting == 0;

        setPendingEOL(1);

        int blankLines = maxBlankLines >= 0 ? maxBlankLines : 0;
        if (myPendingEOL > blankLines + 1) {
            myPendingEOL = maxBlankLines + 1;
        }

        try {
            if (myIOException == null) {
                myOffsetBefore = myOffsetAfter;
                appendEOL(false, false);
            }
        } catch (IOException e) {
            setIOException(e);
        }
        return this;
    }

    @Override
    public CharSequence getIndentPrefix() {
        return myIndentPrefix;
    }

    @Override
    public FormattingAppendable setIndentPrefix(final CharSequence prefix) {
        myIndentPrefix = CharSubSequence.of(prefix);
        return this;
    }

    @Override
    public BasedSequence getPrefix() {
        return myPrefix;
    }

    @Override
    public CharSequence getTotalIndentPrefix() {
        StringBuilder sb = new StringBuilder();
        sb.append(RepeatedCharSequence.of(myIndentPrefix, myIndent));
        return sb.toString();
    }

    @Override
    public FormattingAppendable setPrefix(final CharSequence prefix) {
        myPrefix = CharSubSequence.of(prefix);
        return this;
    }

    @Override
    public FormattingAppendable line() {
        setPendingEOL(1);
        return this;
    }

    @Override
    public FormattingAppendable lineIf(final boolean predicate) {
        if (predicate) line();
        return this;
    }

    @Override
    public FormattingAppendable line(Ref<Boolean> lineRef) {
        // assume the line will increment, if it does not then the frame handler will reset this reference
        lineRef.value = true;
        if (myConditionalFrames.size() > 0) {
            // add this lineRef conditional to the frame
            ConditionalFrame frame = myConditionalFrames.peek();
            if (frame.myModCount == myModCount) {
                // hasn't fired yet, may fire
                // if there is a lineRef already, then set it to false, since it did not cause the conditional to fire
                if (frame.myLineRef != null) frame.myLineRef.value = false;
                frame.myLineRef = lineRef;
            }
        }
        setPendingEOL(1);
        return this;
    }

    @Override
    public FormattingAppendable lineIf(Ref<Boolean> lineRef) {
        if (lineRef.value) line();
        return this;
    }

    @Override
    public FormattingAppendable blankLine() {
        setPendingEOL(2);
        return this;
    }

    @Override
    public FormattingAppendable blankLineIf(final boolean predicate) {
        if (predicate) blankLine();
        return this;
    }

    @Override
    public FormattingAppendable blankLine(final int count) {
        if (count > 0) setPendingEOL(count + 1);
        return this;
    }

    @Override
    public int getIndent() {
        return myIndent + myIndentOffset;
    }

    @Override
    public FormattingAppendable setIndentOffset(final int indentOffset) {
        myIndentOffset = indentOffset;
        return this;
    }

    @Override
    public FormattingAppendable indent() {
        if (myPreFormattedNesting != 0) throw new IllegalStateException("indent should not be called inside preFormatted");
        line();
        ++myIndent;
        myIndentLineCounts.push(myLineCount);
        myWillIndent = false;
        return this;
    }

    @Override
    public FormattingAppendable willIndent() {
        myWillIndent = true;
        return this;
    }

    @Override
    public FormattingAppendable unIndent() {
        if (myIndent <= 0) throw new IllegalStateException("unIndent called with nesting == 0");
        if (myPreFormattedNesting != 0) throw new IllegalStateException("unIndent should not be called inside preFormatted");

        int indentLineCount = myIndentLineCounts.pop();
        if (indentLineCount == myLineCount) {
            myPendingEOL = 0;
        } else {
            line();
        }

        --myIndent;
        return this;
    }

    @Override
    public int getModCount() {
        return myModCount;
    }

    @Override
    public int getLineCount() {
        return myLineCount;
    }

    @Override
    public int getOffsetBefore() {
        return myOffsetBefore;
    }

    @Override
    public int getOffsetAfter() {
        return myOffsetAfter;
    }

    @Override
    public FormattingAppendable openPreFormatted(final boolean keepIndent) {
        try {
            myOffsetBefore = myOffsetAfter;
            if (!keepIndent) {
                myPendingPreFormattedPrefix = myPendingEOL > 0;
            }
            beforeAppendText(true, keepIndent, keepIndent);
        } catch (IOException e) {
            setIOException(e);
        }
        myPendingSpaces = 0;
        myPendingEOL = 0;
        ++myPreFormattedNesting;
        return this;
    }

    @Override
    public FormattingAppendable closePreFormatted() {
        if (myPreFormattedNesting <= 0) throw new IllegalStateException("closePreFormatted called with nesting == 0");
        myPendingPreFormattedPrefix = false;
        --myPreFormattedNesting;
        return this;
    }

    @Override
    public boolean isPreFormatted() {
        return myPreFormattedNesting > 0;
    }

    @Override
    public FormattingAppendable openConditional(final ConditionalFormatter openFormatter) {
        ConditionalFrame frame = new ConditionalFrame(openFormatter, myModCount, myIndent, myLineCount);
        myConditionalFrames.push(frame);
        return this;
    }

    @Override
    public FormattingAppendable closeConditional(final ConditionalFormatter closeFormatter) {
        if (myConditionalFrames.size() == 0) throw new IllegalStateException("closeConditional called with no conditionals open");

        ConditionalFrame frame = myConditionalFrames.pop();
        // if modCount changed then it was invoked
        closeFormatter.apply(true, frame.myOnIndent, frame.myOnLine, frame.myModCount != myModCount);
        return this;
    }

    private static class ConditionalFrame {
        final ConditionalFormatter myOpenFormatter;
        final int myModCount;
        final int myIndent;
        final int myLineCount;
        Ref<Boolean> myLineRef;
        boolean myOnIndent;
        boolean myOnLine;
        boolean myInFormatter;

        ConditionalFrame(final ConditionalFormatter openFormatter, final int modCount, final int indent, final int lineCount) {
            myOpenFormatter = openFormatter;
            myModCount = modCount;
            myIndent = indent;
            myLineCount = lineCount;
            myLineRef = null;
            myOnIndent = false;
            myOnLine = false;
            myInFormatter = false;
        }
    }
}
