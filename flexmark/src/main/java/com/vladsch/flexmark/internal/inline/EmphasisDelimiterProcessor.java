package com.vladsch.flexmark.internal.inline;

import com.vladsch.flexmark.internal.Delimiter;
import com.vladsch.flexmark.internal.util.sequence.SubSequence;
import com.vladsch.flexmark.node.DelimitedNode;
import com.vladsch.flexmark.node.Emphasis;
import com.vladsch.flexmark.node.StrongEmphasis;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.parser.delimiter.DelimiterRun;

public abstract class EmphasisDelimiterProcessor implements DelimiterProcessor {

    private final char delimiterChar;

    protected EmphasisDelimiterProcessor(char delimiterChar) {
        this.delimiterChar = delimiterChar;
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
    public void process(Delimiter opener, Delimiter closer, int delimiterUse) {
        DelimitedNode emphasis = delimiterUse == 1
                ? new Emphasis(opener.getTailChars(delimiterUse), SubSequence.NULL, closer.getLeadChars(delimiterUse))
                : new StrongEmphasis(opener.getTailChars(delimiterUse), SubSequence.NULL, closer.getLeadChars(delimiterUse));

        opener.moveNodesBetweenDelimitersTo(emphasis, closer);
    }
}
