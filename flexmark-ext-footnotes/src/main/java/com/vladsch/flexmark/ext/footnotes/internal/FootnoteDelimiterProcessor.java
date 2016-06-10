package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.internal.Delimiter;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.parser.DelimiterProcessor;

public class FootnoteDelimiterProcessor implements DelimiterProcessor {

    @Override
    public char getOpeningDelimiterChar() {
        return ':';
    }

    @Override
    public char getClosingDelimiterChar() {
        return ':';
    }

    @Override
    public int getMinDelimiterCount() {
        return 1;
    }

    @Override
    public int getDelimiterUse(int openerCount, int closerCount) {
        if (openerCount >= 1 && closerCount >= 1) {
            return 1;
        } else {
            return 1;
        }
    }

    @Override
    public void process(Delimiter opener, Delimiter closer, int delimiterUse) {
        // Normal case, wrap nodes between delimiters in strikethrough.
        Footnote footnote = new Footnote(opener.getTailChars(delimiterUse), SubSequence.EMPTY, closer.getLeadChars(delimiterUse));
        opener.moveNodesBetweenDelimitersTo(footnote, closer);
    }
}
