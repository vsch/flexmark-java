package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import com.vladsch.flexmark.util.sequence.CharSubSequence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class FormattingAppendableImpl implements FormattingAppendable {
    private final Appendable myAppendable;
    private final boolean myCollapseSpaces;
    private final Stack<ConditionalFrame> myConditionalFrames;
    private final char myEOL;
    private final String myWhitespace;

    // IOExceptions are not thrown, the first one sets this member
    private IOException myIOException;

    // count of append calls, only useful for checking if real data was output since this value was taken last
    private int myModCount;

    // pending new lines, 1+ for every pending blank line
    private int myPendingEOL;
    private int myLineCount;
    private int myModCountOfLastEOL;

    // indent level to use after the next \n and before text is appended
    private int myIndent;
    private int myIndentOffset;
    private BasedSequence myIndentPrefix;

    // pre-formatted nesting level, while >0 all text is passed through as is and not monitored
    private int myPreFormattedNesting;

    // accumulated spaces and tabs that we were not sure would need to be output
    private int myPendingSpaces;

    @SuppressWarnings("WeakerAccess")
    public FormattingAppendableImpl(final Appendable appendable, final boolean collapseSpaces) {
        myAppendable = appendable;
        myCollapseSpaces = collapseSpaces;
        myConditionalFrames = new Stack<>();
        myEOL = '\n';
        myWhitespace = BasedSequence.WHITESPACE_NO_EOL_CHARS;
        myIOException = null;
        myModCount = 0;
        myPendingEOL = 0;
        myLineCount = 0;
        myModCountOfLastEOL = 0;
        myIndent = 0;
        myIndentOffset = 0;
        myIndentPrefix = BasedSequence.NULL;
        myPreFormattedNesting = 0;
    }

    private void appendIndent() throws IOException {
        if (myIndent + myIndentOffset > 0 && !myIndentPrefix.isEmpty()) {
            for (int i = 0; i < myIndent + myIndentOffset; i++) {
                myAppendable.append(myIndentPrefix);
            }
        }
    }

    private void addPendingSpaces(final int count) {
        if (count > 0 && myPendingEOL == 0 && myModCountOfLastEOL != myModCount) {
            if (myCollapseSpaces) {
                if (myPendingSpaces == 0) {
                    myPendingSpaces = 1;
                }
            } else {
                myPendingSpaces += count;
            }
        }
    }

    private void appendSpaces() throws IOException {
        if (myPendingSpaces > 0) {
            while (myPendingSpaces > 0) {
                myAppendable.append(' ');
                myPendingSpaces--;
            }

            myModCount++;
        }
    }

    private void setPendingEOL(int pendingEOL) {
        if (myModCountOfLastEOL != myModCount && pendingEOL > myPendingEOL) {
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
            while (myPendingEOL > 0) {
                myAppendable.append(myEOL);
                myLineCount++;
                myPendingEOL--;
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
    private void beforeAppendText(final boolean withEOL, final boolean withIndent) throws IOException {
        if (myConditionalFrames.size() > 0) {
            // have conditional
            ConditionalFrame frame = myConditionalFrames.peek();
            if (frame.myModCount == myModCount) {
                // hasn't fired yet, fire it, add modCount so it does not run again, no matter what
                myModCount++;
                frame.myOnIndent = frame.myIndent < myIndent;
                frame.myOnLine = frame.myLineCount < myLineCount + myPendingEOL;
                int indent = myIndent;
                // restore indent to what it was for the frame
                myIndent = frame.myIndent;
                // reset any pending EOLs to allow conditional to apply one if it sees fit
                myPendingEOL = 0;
                int lineCount = myLineCount;

                frame.myOpenFormatter.apply(frame.myOnIndent, frame.myOnLine, true);

                // restore indent increase before apply to indent after apply
                myIndent += indent - frame.myIndent;

                // reset to false if the line count has not changed, ie. conditional formatter suppressed the new line
                if (frame.myLineRef != null) frame.myLineRef.value = lineCount != myLineCount;
            }
        }

        if (withEOL) appendEOL(withIndent, true);
        else appendSpaces();
    }

    private void appendImpl(final char c) throws IOException {
        if (myPreFormattedNesting > 0) {
            appendSpaces();
            myAppendable.append(c);
            myModCount++;
        } else {
            if (c == myEOL) {
                setPendingEOL(1);
            } else {
                if (myWhitespace.indexOf(c) != -1) {
                    addPendingSpaces(1);
                } else {
                    beforeAppendText(true, true);
                    myAppendable.append(c);
                    myModCount++;
                }
            }
        }
    }

    private void appendImpl(final CharSequence csq, final int start, final int end) throws IOException {
        if (myPreFormattedNesting > 0) {
            appendSpaces();
            myAppendable.append(csq, start, end);
            myModCount++;
        } else {
            // have to handle \n, white spaces, etc
            int lastPos = start;
            BasedSequence seq = BasedSequenceImpl.of(csq);

            while (lastPos < end) {
                int pos = seq.indexOfAny(BasedSequence.WHITESPACE_CHARS, lastPos, end);

                // output what has accumulated before
                int spanEnd = pos == -1 ? end : pos;

                if (lastPos < spanEnd) {
                    beforeAppendText(true, true);
                    myAppendable.append(csq, lastPos, spanEnd);
                    myModCount++;
                }

                if (pos == -1) break;

                // spaces and tabs are only output if we don't have a pending EOL and they don't come before an EOL
                int span = seq.countChars(BasedSequence.WHITESPACE_CHARS, pos, end);
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
            if (myIOException == null) appendEOL(false, false);
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
        line();
        ++myIndent;
        return this;
    }

    @Override
    public FormattingAppendable unIndent() {
        if (myIndent <= 0) throw new IllegalStateException("unIndent called with nesting == 0");

        line();
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
    public FormattingAppendable openPreFormatted() {
        myPendingSpaces = 0;
        myPendingEOL = 0;
        ++myPreFormattedNesting;
        return this;
    }

    @Override
    public FormattingAppendable closePreFormatted() {
        if (myPreFormattedNesting <= 0) throw new IllegalStateException("closePreFormatted called with nesting == 0");
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
        closeFormatter.apply(frame.myOnIndent, frame.myOnLine, frame.myModCount != myModCount);
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

        ConditionalFrame(final ConditionalFormatter openFormatter, final int modCount, final int indent, final int lineCount) {
            myOpenFormatter = openFormatter;
            myModCount = modCount;
            myIndent = indent;
            myLineCount = lineCount;
            myLineRef = null;
            myOnIndent = false;
            myOnLine = false;
        }
    }
}
