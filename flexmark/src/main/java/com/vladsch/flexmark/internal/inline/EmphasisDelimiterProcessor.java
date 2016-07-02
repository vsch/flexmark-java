package com.vladsch.flexmark.internal.inline;

import com.vladsch.flexmark.internal.Delimiter;
import com.vladsch.flexmark.internal.util.sequence.SubSequence;
import com.vladsch.flexmark.node.DelimitedNode;
import com.vladsch.flexmark.node.Emphasis;
import com.vladsch.flexmark.node.StrongEmphasis;
import com.vladsch.flexmark.parser.DelimiterProcessor;

public abstract class EmphasisDelimiterProcessor implements DelimiterProcessor {

    private final char delimiterChar;

    protected EmphasisDelimiterProcessor(char delimiterChar) {
        this.delimiterChar = delimiterChar;
    }

    @Override
    public char getOpeningDelimiterChar() {
        return delimiterChar;
    }

    @Override
    public char getClosingDelimiterChar() {
        return delimiterChar;
    }

    @Override
    public int getMinDelimiterCount() {
        return 1;
    }

    @Override
    public int getDelimiterUse(int openerCount, int closerCount) {
        // calculate actual number of delimiters used from this closer
        if (closerCount < 3 || openerCount < 3) {
            return closerCount <= openerCount ?
                    closerCount : openerCount;
        } else {
            return closerCount % 2 == 0 ? 2 : 1;
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
