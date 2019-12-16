package com.vladsch.flexmark.ext.gfm.strikethrough.internal;

import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.ext.gfm.strikethrough.Subscript;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.core.delimiter.Delimiter;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.parser.delimiter.DelimiterRun;
import com.vladsch.flexmark.util.ast.DelimitedNode;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class StrikethroughSubscriptDelimiterProcessor implements DelimiterProcessor {

    @Override
    public char getOpeningCharacter() {
        return '~';
    }

    @Override
    public char getClosingCharacter() {
        return '~';
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

    @Override
    public int getDelimiterUse(DelimiterRun opener, DelimiterRun closer) {
        // "multiple of 3" rule for internal delimiter runs
        if ((opener.canClose() || closer.canOpen()) && (opener.length() + closer.length()) % 3 == 0) {
            return 0;
        }
        // calculate actual number of delimiters used from this closer
        if (opener.length() < 3 || closer.length() < 3) {
            return closer.length() <= opener.length() ?
                    closer.length() : opener.length();
        } else {
            return closer.length() % 2 == 0 ? 2 : 1;
        }
    }

    @Override
    public Node unmatchedDelimiterNode(InlineParser inlineParser, DelimiterRun delimiter) {
        return null;
    }

    @Override
    public void process(Delimiter opener, Delimiter closer, int delimitersUsed) {
        // wrap nodes between delimiters in strikethrough.
        DelimitedNode emphasis = delimitersUsed == 1
                ? new Subscript(opener.getTailChars(delimitersUsed), BasedSequence.NULL, closer.getLeadChars(delimitersUsed))
                : new Strikethrough(opener.getTailChars(delimitersUsed), BasedSequence.NULL, closer.getLeadChars(delimitersUsed));

        opener.moveNodesBetweenDelimitersTo(emphasis, closer);
    }
}
