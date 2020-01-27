package com.vladsch.flexmark.parser.core.delimiter;

import com.vladsch.flexmark.ast.Emphasis;
import com.vladsch.flexmark.ast.StrongEmphasis;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.parser.delimiter.DelimiterRun;
import com.vladsch.flexmark.util.ast.DelimitedNode;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public abstract class EmphasisDelimiterProcessor implements DelimiterProcessor {
    final private char delimiterChar;
    final private int multipleUse;

    protected EmphasisDelimiterProcessor(char delimiterChar, boolean strongWrapsEmphasis) {
        this.delimiterChar = delimiterChar;
        this.multipleUse = strongWrapsEmphasis ? 1 : 2;
    }

    @Override
    public char getOpeningCharacter() {
        return delimiterChar;
    }

    @Override
    public char getClosingCharacter() {
        return delimiterChar;
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
    public Node unmatchedDelimiterNode(InlineParser inlineParser, DelimiterRun delimiter) {
        return null;
    }

    @Override
    public int getDelimiterUse(DelimiterRun opener, DelimiterRun closer) {
        // "multiple of 3" rule for internal delimiter runs
        if ((opener.canClose() || closer.canOpen()) && (opener.length() + closer.length()) % 3 == 0) {
            return 0;
        }

        // calculate actual number of delimiters used from this closer
        if (opener.length() < 3 || closer.length() < 3) {
            return Utils.min(closer.length(), opener.length());
        } else {
            // default to latest spec
            return closer.length() % 2 == 0 ? 2 : multipleUse;
        }
    }

    @Override
    public void process(Delimiter opener, Delimiter closer, int delimitersUsed) {
        DelimitedNode emphasis = delimitersUsed == 1
                ? new Emphasis(opener.getTailChars(delimitersUsed), BasedSequence.NULL, closer.getLeadChars(delimitersUsed))
                : new StrongEmphasis(opener.getTailChars(delimitersUsed), BasedSequence.NULL, closer.getLeadChars(delimitersUsed));

        opener.moveNodesBetweenDelimitersTo(emphasis, closer);
    }
}
