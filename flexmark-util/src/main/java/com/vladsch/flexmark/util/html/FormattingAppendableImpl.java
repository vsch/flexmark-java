package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Ref;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import com.vladsch.flexmark.util.sequence.CharSubSequence;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class FormattingAppendableImpl implements FormattingAppendable {
    private final LengthTrackingAppendable myAppendable;
    private final Stack<ConditionalFrame> myConditionalFrames;
    private final Stack<Integer> myIndentLineCounts;
    private final char myEOL;
    private final ArrayList<Ref<Integer>> myOffsetBeforeList;
    private final HashMap<Integer, List<Runnable>> myAfterEolRunnables;

    private String myWhitespace;
    private String myWhitespaceEOL;
    private int myOptions;

    // options at time line was issued
    private int myEolOptions;

    // IOExceptions are not thrown, the first one sets this member
    private IOException myIOException;

    // count of append calls, only useful for checking if real data was output since this value was taken last
    private int myModCount;

    // offset before
    private int myOffsetBefore;

    // pending new lines, 1+ for every pending blank line
    private int myPendingEOL;
    private boolean myPendingPreFormattedPrefix;
    private int myLineCount;
    private int myModCountOfLastEOL;
    private int myLastEOLCount;
    private int myLastEOLOffset;

    // indent level to use after the next \n and before text is appended
    private int myIndent;
    private boolean myWillIndent;
    private int myIndentOffset;
    private BasedSequence myPrefix;
    private BasedSequence myIndentPrefix;

    private int mySpacesAfterEOL;

    // pre-formatted nesting level, while >0 all text is passed through as is and not monitored
    private int myPreFormattedNesting;

    // accumulated spaces and tabs that we were not sure would need to be output
    private int myPendingSpaces;
    private final Stack<BasedSequence> myPrefixStack;
    private boolean myPassThrough;

    public FormattingAppendableImpl(final int formatOptions) {
        this(new StringBuilder(), formatOptions);
    }

    public FormattingAppendableImpl(final Appendable appendable, final int formatOptions) {
        myAppendable = new LengthTrackingAppendableImpl(appendable);
        myConditionalFrames = new Stack<ConditionalFrame>();
        myIndentLineCounts = new Stack<Integer>();
        myPrefixStack = new Stack<BasedSequence>();
        myOffsetBeforeList = new ArrayList<Ref<Integer>>();
        myAfterEolRunnables = new HashMap<Integer, List<Runnable>>();
        myEOL = '\n';
        myOptions = formatOptions;
        myIOException = null;
        myModCount = 0;
        myOffsetBefore = 0;
        myPendingEOL = 0;
        myPendingPreFormattedPrefix = false;
        myLineCount = 0;
        myModCountOfLastEOL = 0;
        myLastEOLCount = 0;
        myIndent = 0;
        myWillIndent = false;
        myIndentOffset = 0;
        myPrefix = BasedSequence.NULL;
        myIndentPrefix = BasedSequence.NULL;
        myPreFormattedNesting = 0;
        myEolOptions = myOptions;
        mySpacesAfterEOL = 0;
        myPassThrough = haveOptions(PASS_THROUGH);

        updateLastEolOffset();
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
        myPassThrough = haveOptions(PASS_THROUGH);

        setWhitespace();
        return this;
    }

    private boolean haveOptions(int options) {
        return (myOptions & options) != 0;
    }

    private boolean haveEolOptions(int options) {
        return (myEolOptions & options) != 0;
    }

    private boolean isConvertingTabs() {
        return haveOptions(CONVERT_TABS | COLLAPSE_WHITESPACE);
    }

    private boolean isSuppressingTrailingWhitespace() {
        return haveOptions(SUPPRESS_TRAILING_WHITESPACE);
    }

    private boolean isAllowLeadingWhitespace() {
        return haveOptions(ALLOW_LEADING_WHITESPACE);
    }

    private boolean isCollapseWhitespace() {
        return haveOptions(COLLAPSE_WHITESPACE);
    }

    private boolean isPrefixAfterPendingEol() {
        return haveOptions(PREFIX_AFTER_PENDING_EOL);
    }

    private void appendIndent() throws IOException {
        if (!myPrefix.isEmpty()) {
            myAppendable.append(myPrefix);
        }

        if (myIndent + myIndentOffset > 0 && !myIndentPrefix.isEmpty()) {
            for (int i = 0; i < myIndent + myIndentOffset; i++) {
                myAppendable.append(myIndentPrefix);
            }
        }
    }

    private void addPendingSpaces(final int count) {
        if (count > 0) {
            if (myPreFormattedNesting == 0 && (myPendingEOL == 0 && myModCountOfLastEOL != myModCount || isAllowLeadingWhitespace())) {
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
            int spaces = myPendingSpaces;
            appendSpaces(spaces);

            myPendingSpaces = 0;
            myModCount++;
        }
    }

    private void appendSpaces(int spaces) throws IOException {
        while (spaces > 0) {
            myAppendable.append(' ');
            spaces--;
        }
    }

    public void setPendingEOLRaw(int pendingEOL) {
        if (myPassThrough) {
            if (myAppendable.getLength() > 0) {
                myPendingEOL = pendingEOL;
            }
        } else {
            if (myPreFormattedNesting == 0 && pendingEOL > myPendingEOL) {
                if (myModCountOfLastEOL != myModCount) {
                    myPendingEOL = pendingEOL;
                    myEolOptions = myOptions;
                } else if (myLineCount > 0 && pendingEOL > myLastEOLCount) {
                    myPendingEOL = pendingEOL - myLastEOLCount;
                    myEolOptions = myOptions;
                }
            }
        }

        mySpacesAfterEOL = 0;
    }

    @Override
    public void setPendingEOL(int pendingEOL) {
        myPendingEOL = pendingEOL;
    }

    private void resetPendingEOL() {
        myPendingEOL = 0;
        myPendingSpaces = 0;
        myModCountOfLastEOL = myModCount;
        myEolOptions = myOptions;
    }

    @Override
    public FormattingAppendableImpl addAfterEolRunnable(int atPendingEOL, Runnable runnable) {
        List<Runnable> runnableList = myAfterEolRunnables.get(atPendingEOL);
        if (runnableList == null) {
            runnableList = new ArrayList<Runnable>();
            myAfterEolRunnables.put(atPendingEOL, runnableList);
        }
        runnableList.add(runnable);
        return this;
    }

    private void runAllAfterEol() {
        List<Runnable> runnableList = myAfterEolRunnables.get(myPendingEOL);
        if (runnableList != null) {
            for (Runnable runnable : runnableList) {
                runnable.run();
            }
            myAfterEolRunnables.remove(myPendingEOL);
        }
    }

    private void appendEOL(final boolean withIndent, final boolean withPendingSpaces) throws IOException {
        int lineCount = myLineCount;
        if (myPendingEOL > 0) {
            if (myPendingSpaces > 0 && !haveEolOptions(SUPPRESS_TRAILING_WHITESPACE)) {
                appendSpaces();
            }

            while (myPendingEOL > 0) {
                myAppendable.append(myEOL);
                updateLastEolOffset();
                myLineCount++;
                runAllAfterEol();
                myPendingEOL--;

                if (myPendingEOL > 0 && !myPrefix.isBlank()) {
                    myAppendable.append(myPrefix);
                }
            }

            resetPendingEOL();
            runAllAfterEol();

            // now output any leading spaces accumulated
            appendSpaces(mySpacesAfterEOL);
            mySpacesAfterEOL = 0;

            if (withIndent) appendIndent();
        } else {
            if (myModCountOfLastEOL == myModCount) {
                if (isAllowLeadingWhitespace() && withPendingSpaces) {
                    appendSpaces();
                }
                if (withIndent) appendIndent();
            } else {
                if (withPendingSpaces) {
                    appendSpaces();
                }
            }
        }
        myLastEOLCount = myLineCount - lineCount;
    }

    private void updateLastEolOffset() {
        myLastEOLOffset = myAppendable.getLength();
    }

    @SuppressWarnings("SameParameterValue")
    private void beforeAppendText(final boolean withEOL, final boolean withIndent, final boolean withSpaces) throws IOException {
        myLastEOLCount = 0;
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
                    runAllAfterEol();
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

    private void setOffsetBefore(int offsetBefore) {
        myOffsetBefore = offsetBefore;
        if (!myOffsetBeforeList.isEmpty()) {
            for (Ref<Integer> refOffset : myOffsetBeforeList) {
                refOffset.value = offsetBefore;
            }

            myOffsetBeforeList.clear();
        }
    }

    private void beforePre() throws IOException {
        while (myPendingEOL > 0) {
            myAppendable.append('\n');
            updateLastEolOffset();
            myLineCount++;
            if (myPendingPreFormattedPrefix && !myPrefix.isEmpty()) {
                myAppendable.append(myPrefix);
            }
            myPendingEOL--;
        }
        myPendingPreFormattedPrefix = false;
    }

    private void appendImpl(final char c) throws IOException {
        if (myPassThrough) {
            // track nothing but 1 eol and just output everything else
            if (myPendingEOL-- > 0) {
                myAppendable.append('\n');
            }
            myPendingEOL = 0;
            myModCount++;
            myAppendable.append(c);
            return;
        }

        if (myPreFormattedNesting > 0) {
            setOffsetBefore(myAppendable.getLength());

            beforePre();

            if (myPendingPreFormattedPrefix && !myPrefix.isEmpty()) {
                myAppendable.append(myPrefix);
            }
            myPendingPreFormattedPrefix = false;

            if (c == myEOL) {
                myPendingEOL = 1;
                myPendingPreFormattedPrefix = true;
                mySpacesAfterEOL = 0;
            } else {
                myAppendable.append(c);
                myModCount++;
                resetPendingEOL();
            }
        } else {
            if (c == myEOL) {
                setPendingEOLRaw(1);
            } else {
                if (myWhitespace.indexOf(c) != -1) {
                    addPendingSpaces(1);
                } else {
                    beforeAppendText(true, true, true);
                    setOffsetBefore(myAppendable.getLength());
                    myAppendable.append(c);
                    myModCount++;
                }
            }
        }
    }

    private void appendImpl(final CharSequence csq, final int start, final int end) throws IOException {
        if (myPassThrough) {
            // track nothing but 1 pending eol, just output
            if (myPendingEOL-- > 0) {
                //myLineCount++;
                myAppendable.append('\n');
                //updateLastEolOffset();
            }
            ////myModCountOfLastEOL = myModCount++;
            myPendingEOL = 0;
            myModCount++;
            myAppendable.append(csq, start, end);
            return;
        }

        int lastPos = start;
        BasedSequence seq = BasedSequenceImpl.of(csq);

        if (myPreFormattedNesting > 0) {
            setOffsetBefore(myAppendable.getLength());
            int endNoEOL = start + seq.subSequence(start, end).removeSuffix("\n").length();

            if (lastPos < end) {
                beforePre();
            }

            while (lastPos < endNoEOL) {
                int pos = seq.indexOf(myEOL, lastPos, endNoEOL);
                int endPos = pos == -1 ? endNoEOL : pos + 1;

                if (lastPos < endPos) {
                    if (myPendingPreFormattedPrefix && !myPrefix.isEmpty()) {
                        myAppendable.append(myPrefix);
                    }
                    myPendingPreFormattedPrefix = false;

                    myAppendable.append(csq, lastPos, endPos);
                    lastPos = endPos;
                }

                if (pos == -1) break;

                updateLastEolOffset();
                myLineCount++;
                myPendingPreFormattedPrefix = true;
                lastPos = endPos;
            }
            myModCount++;

            // if EOL is last then we reset pending and eol mod count
            if (lastPos == endNoEOL && endNoEOL != end) {
                myPendingEOL = 1;
                myPendingPreFormattedPrefix = true;
                mySpacesAfterEOL = 0;
            }
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
                        setOffsetBefore(myAppendable.getLength());
                        firstAppend = false;
                    }
                    myAppendable.append(csq, lastPos, spanEnd);
                    myModCount++;
                }

                if (pos == -1) break;

                // spaces and tabs are only output if we don't have a pending EOL and they don't come before an EOL
                int span = seq.countLeading(myWhitespaceEOL, pos, end);
                if (myPendingEOL == 0) {
                    int eolPos = seq.indexOf(myEOL, pos, pos + span);
                    if (eolPos != -1) {
                        // we don't output the spaces after the EOL but make EOL pending
                        if (eolPos > pos && !haveOptions(SUPPRESS_TRAILING_WHITESPACE)) addPendingSpaces(eolPos - pos);
                        setPendingEOLRaw(1);
                    } else {
                        // no eol, make spaces pending
                        addPendingSpaces(span);
                    }
                } else {
                    // we are coming after an EOL, all whitespaces are ignored if suppressed
                    if (isAllowLeadingWhitespace()) mySpacesAfterEOL += span;
                }

                pos += span;
                lastPos = pos;
            }
        }
    }

    // kludge: to try and estimate column position after char sequence is output without touching already fragile code
    private int appendPretendColumn(final CharSequence csq, final int start, final int end) {
        int column = column();
        boolean myPendingPreFormattedPrefix = this.myPendingPreFormattedPrefix;
        int myPendingSpaces = this.myPendingSpaces;
        int mySpacesAfterEOL = this.mySpacesAfterEOL;

        int prefixLength = myPrefix.length();
        if (myPassThrough) {
            if (myPendingEOL > 0) {
                column = 0;
                if (myPendingPreFormattedPrefix && !myPrefix.isEmpty()) {
                    column = prefixLength;
                }
                myPendingPreFormattedPrefix = false;
            }
            return column;
        }

        int lastPos = start;
        BasedSequence seq = BasedSequenceImpl.of(csq);

        if (myPreFormattedNesting > 0) {
            int endNoEOL = start + seq.subSequence(start, end).removeSuffix("\n").length();

            if (lastPos < end) {
                if (myPendingPreFormattedPrefix) {
                    column += prefixLength;
                }
                myPendingPreFormattedPrefix = false;
            }

            while (lastPos < endNoEOL) {
                int pos = seq.indexOf(myEOL, lastPos, endNoEOL);
                int endPos = pos == -1 ? endNoEOL : pos + 1;

                if (lastPos < endPos) {
                    if (myPendingPreFormattedPrefix) {
                        column += prefixLength;
                    }
                    myPendingPreFormattedPrefix = false;

                    column += endPos - lastPos;
                    lastPos = endPos;
                }

                if (pos == -1) break;

                myPendingPreFormattedPrefix = true;
                lastPos = endPos;
            }

            // if EOL is last then we reset pending and eol mod count
            if (lastPos == endNoEOL && endNoEOL != end) {
                myPendingPreFormattedPrefix = true;
            }
        } else {
            // have to handle \n, white spaces, etc
            boolean firstAppend = true;
            while (lastPos < end) {
                int pos = seq.indexOfAny(myWhitespaceEOL, lastPos, end);

                // output what has accumulated before
                int spanEnd = pos == -1 ? end : pos;

                if (lastPos < spanEnd) {
                    //beforeAppendText(true, true, true);

                    int myIndentSize = myIndentPrefix.length() * (myIndent + myIndentOffset);
                    boolean haveIndent = myIndent + myIndentOffset > 0 && !myIndentPrefix.isEmpty();
                    if (myPendingEOL > 0) {
                        //appendIndent();
                        column = 0;
                        if (!myPrefix.isEmpty()) {
                            column += prefixLength;
                        }

                        if (haveIndent) {
                            column += myIndentSize;
                        }
                    } else {
                        if (myModCountOfLastEOL == myModCount) {
                            if (!myPrefix.isEmpty()) {
                                column += prefixLength;
                            }

                            if (haveIndent) {
                                column += myIndentSize;
                            }
                        } else {
                            //appendSpaces();
                            if (myPendingSpaces > 0) column += myPendingSpaces;
                        }
                    }

                    if (firstAppend) {
                        firstAppend = false;
                    }
                    column += spanEnd - lastPos;
                }

                if (pos == -1) break;

                // spaces and tabs are only output if we don't have a pending EOL and they don't come before an EOL
                int span = seq.countLeading(myWhitespaceEOL, pos, end);
                if (myPendingEOL == 0) {
                    int eolPos = seq.indexOf(myEOL, pos, pos + span);
                    boolean handleSpaces = myPreFormattedNesting == 0 && (myPendingEOL == 0 && myModCountOfLastEOL != myModCount);

                    if (eolPos != -1) {
                        // we don't output the spaces after the EOL but make EOL pending
                        if (eolPos > pos && !haveOptions(SUPPRESS_TRAILING_WHITESPACE)) {
                            //addPendingSpaces(eolPos - pos);
                            int count = eolPos - pos;
                            if (count > 0) {
                                if (handleSpaces) {
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
                    } else {
                        // no eol, make spaces pending
                        //addPendingSpaces(span);
                        int count = span;
                        if (count > 0) {
                            if (handleSpaces) {
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
                } else {
                    // we are coming after an EOL, all whitespaces are ignored
                    if (isAllowLeadingWhitespace()) {
                        // output spaces
                        column += mySpacesAfterEOL;
                        mySpacesAfterEOL = 0;
                    }
                }

                pos += span;
                lastPos = pos;
            }
        }
        return column;
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

    public FormattingAppendable repeat(char c, int count) {
        int i = count;
        while (i-- > 0) append(c);
        return this;
    }

    public FormattingAppendable repeat(CharSequence csq, int count) {
        int i = count;
        while (i-- > 0) append(csq);
        return this;
    }

    public FormattingAppendable repeat(CharSequence csq, int start, int end, int count) {
        int i = count;
        while (i-- > 0) append(csq, start, end);
        return this;
    }

    @Override
    public Appendable getAppendable() {
        return myAppendable;
    }

    @Override
    public String getText() {
        Appendable appendable = flush().getAppendable();
        String result = appendable.toString();
        return result;
    }

    @Override
    public String getText(int maxBlankLines) {
        Appendable appendable = flush(maxBlankLines).getAppendable();
        String result = appendable.toString();
        return result;
    }

    @Override
    public FormattingAppendable flush() {
        return flush(0);
    }

    @Override
    public FormattingAppendable flushWhitespaces() {
        try {
            appendSpaces();
        } catch (IOException e) {
            setIOException(e);
        }
        return this;
    }

    @Override
    public FormattingAppendable flush(final int maxBlankLines) {
        assert myConditionalFrames.size() == 0;
        assert myPreFormattedNesting == 0;

        int blankLines = maxBlankLines >= -1 ? maxBlankLines : -1;
        if (myPendingEOL > blankLines + 1) {
            myPendingEOL = maxBlankLines + 1;
        }

        try {
            if (myIOException == null) {
                myOffsetBefore = myAppendable.getLength();
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
        final CharSubSequence fixedPrefix = CharSubSequence.of(prefix);
        if (myPendingEOL > 0 && isPrefixAfterPendingEol()) {
            addAfterEolRunnable(0, new Runnable() {
                @Override
                public void run() {
                    myPrefix = fixedPrefix;
                }
            });
        } else {
            myPrefix = fixedPrefix;
        }
        return this;
    }

    @Override
    public FormattingAppendable addPrefix(final CharSequence prefix) {
        setPrefix(myPrefix.append(prefix));
        return this;
    }

    @Override
    public FormattingAppendable pushPrefix() {
        myPrefixStack.push(myPrefix);
        return this;
    }

    @Override
    public FormattingAppendable popPrefix() {
        if (myPrefixStack.isEmpty()) throw new IllegalStateException("popPrefix with an empty stack");

        BasedSequence prefix = myPrefixStack.pop();
        setPrefix(prefix);
        return this;
    }

    @Override
    public int getPushedPrefixCount() {
        return myPrefixStack.size();
    }

    @Override
    public FormattingAppendable line() {
        setPendingEOLRaw(1);
        return this;
    }

    @Override
    public FormattingAppendable addLine() {
        setPendingEOLRaw(myPendingEOL + 1);
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
        setPendingEOLRaw(1);
        return this;
    }

    @Override
    public FormattingAppendable lineIf(Ref<Boolean> lineRef) {
        if (lineRef.value) line();
        return this;
    }

    @Override
    public FormattingAppendable blankLine() {
        setPendingEOLRaw(2);
        return this;
    }

    @Override
    public FormattingAppendable blankLineIf(final boolean predicate) {
        if (predicate) blankLine();
        return this;
    }

    @Override
    public FormattingAppendable blankLine(final int count) {
        if (count > 0) setPendingEOLRaw(count + 1);
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

        if (!myPassThrough) {
            line();
            myIndentLineCounts.push(myLineCount);
        }

        myWillIndent = false;
        ++myIndent;
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

        if (!myPassThrough) {
            if (myPreFormattedNesting != 0) throw new IllegalStateException("unIndent should not be called inside preFormatted");

            int indentLineCount = myIndentLineCounts.pop();
            if (indentLineCount == myLineCount) {
                myPendingEOL = 0;
                runAllAfterEol();
            } else {
                line();
            }
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
    public FormattingAppendable lastOffset(Ref<Integer> refOffset) {
        myOffsetBeforeList.add(refOffset);
        return this;
    }

    @Override
    public int lastOffset() {
        return myOffsetBefore;
    }

    @Override
    public int offset() {
        return myAppendable.getLength();
    }

    @Override
    public int offsetWithPending() {
        return myAppendable.getLength() + (myModCountOfLastEOL == myModCount && haveOptions(SUPPRESS_TRAILING_WHITESPACE) ? 0 : myPendingSpaces) + myPendingEOL + (myPendingEOL > 0 ? myPrefix.length() : 0);
    }

    @Override
    public int column() {
        return myAppendable.getLength() - myLastEOLOffset;
    }

    @Override
    public int columnWith(final CharSequence csq, final int start, final int end) {
        return appendPretendColumn(csq, start, end);
    }

    @Override
    public FormattingAppendable openPreFormatted(final boolean keepIndent) {
        try {
            setOffsetBefore(myAppendable.getLength());
            if (!keepIndent) {
                myPendingPreFormattedPrefix = myPendingEOL > 0;
            }
            beforeAppendText(true, keepIndent, keepIndent);
        } catch (IOException e) {
            setIOException(e);
        }
        myPendingSpaces = 0;
        myPendingEOL = 0;
        runAllAfterEol();
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
    public boolean isPendingSpace() {
        return myPendingSpaces > 0;
    }

    @Override
    public int getPendingSpace() {
        return myPendingSpaces;
    }

    @Override
    public boolean isPendingEOL() {
        return myPendingEOL > 0;
    }

    @Override
    public int getPendingEOL() {
        return myPendingEOL;
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
