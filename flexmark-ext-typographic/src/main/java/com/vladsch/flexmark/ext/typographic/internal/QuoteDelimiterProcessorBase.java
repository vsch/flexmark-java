package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ext.typographic.TypographicQuotes;
import com.vladsch.flexmark.ext.typographic.TypographicSmarts;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.core.delimiter.Delimiter;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.parser.delimiter.DelimiterRun;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class QuoteDelimiterProcessorBase implements DelimiterProcessor {
    protected final TypographicOptions myOptions;
    protected final char myOpenDelimiter;
    protected final char myCloseDelimiter;
    protected final String myOpener;
    protected final String myCloser;
    protected final String myUnmatched;

    public QuoteDelimiterProcessorBase(TypographicOptions options, char openDelimiter, char closeDelimiter, String opener, String closer, String unmatched) {
        myOptions = options;
        myOpenDelimiter = openDelimiter;
        myCloseDelimiter = closeDelimiter;
        myOpener = opener;
        myCloser = closer;
        myUnmatched = unmatched;
    }

    @Override
    final public char getOpeningCharacter() {
        return myOpenDelimiter;
    }

    @Override
    final public char getClosingCharacter() {
        return myCloseDelimiter;
    }

    @Override
    public int getMinLength() {
        return 1;
    }

    @Override
    public boolean canBeOpener(String before, String after, boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
        return leftFlanking;
    }

    @Override
    public boolean canBeCloser(String before, String after, boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
        return rightFlanking;
    }

    @Override
    public boolean skipNonOpenerCloser() {
        return false;
    }

    protected boolean havePreviousOpener(DelimiterRun opener) {
        DelimiterRun previous = opener.getPrevious();
        int minLength = getMinLength();
        while (previous != null) {
            if (previous.getDelimiterChar() == myOpenDelimiter) {
                return canOpen(previous, minLength);
            }

            previous = previous.getPrevious();
        }
        return false;
    }

    protected boolean haveNextCloser(DelimiterRun closer) {
        DelimiterRun next = closer.getNext();
        int minLength = getMinLength();
        while (next != null) {
            if (next.getDelimiterChar() == myCloseDelimiter) {
                return canClose(next, minLength);
            }
            next = next.getNext();
        }
        return false;
    }

    protected boolean canClose(DelimiterRun closer, int minLength) {
        if (closer.canClose()) {
            BasedSequence closerChars = closer.getNode().getChars();
            if (closer.getNext() != null && closerChars.isContinuationOf(closer.getNext().getNode().getChars()) || closerChars.getEndOffset() >= closerChars.getBaseSequence().length() || isAllowed(closerChars.getBaseSequence(), closerChars.getEndOffset() + minLength - 1)) {
                return true;
            }
        }
        return false;
    }

    protected boolean canOpen(DelimiterRun opener, int minLength) {
        if (opener.canOpen()) {
            BasedSequence openerChars = opener.getNode().getChars();
            if (opener.getPrevious() != null && opener.getPrevious().getNode().getChars().isContinuationOf(openerChars) || openerChars.getStartOffset() == 0 || isAllowed(openerChars.getBaseSequence(), openerChars.getStartOffset() - minLength)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("WeakerAccess")
    protected boolean isAllowed(char c) {
        return !Character.isLetterOrDigit(c);
    }

    @SuppressWarnings("WeakerAccess")
    protected boolean isAllowed(CharSequence seq, int index) {
        return index < 0 || index >= seq.length() || !Character.isLetterOrDigit(seq.charAt(index));
    }

    @Override
    public int getDelimiterUse(DelimiterRun opener, DelimiterRun closer) {
        //if (haveNextCloser(closer)) {
        //    // have one that is enclosed
        //    return 0;
        //}
        //if (havePreviousOpener(opener)) {
        //    // have one that is enclosed
        //    return 0;
        //}

        int minLength = getMinLength();
        if (opener.length() >= minLength && closer.length() >= minLength) {
            if (canOpen(opener, minLength) && canClose(closer, minLength)) return minLength;
        }
        return 0;
    }

    @Override
    public Node unmatchedDelimiterNode(InlineParser inlineParser, DelimiterRun delimiter) {
        if (myUnmatched != null && myOptions.typographicSmarts) {
            BasedSequence chars = delimiter.getNode().getChars();
            if (chars.length() == 1) {
                return new TypographicSmarts(chars, myUnmatched);
            }
        }
        return null;
    }

    @Override
    public void process(Delimiter opener, Delimiter closer, int delimitersUsed) {
        // Normal case, wrap nodes between delimiters in strikethrough.
        TypographicQuotes node = new TypographicQuotes(opener.getTailChars(delimitersUsed), BasedSequence.NULL, closer.getLeadChars(delimitersUsed));
        node.setTypographicOpening(myOpener);
        node.setTypographicClosing(myCloser);
        opener.moveNodesBetweenDelimitersTo(node, closer);
    }
}
