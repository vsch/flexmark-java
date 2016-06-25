package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.internal.Delimiter;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.parser.DelimiterProcessor;

public class ZzzzzzDelimiterProcessor implements DelimiterProcessor {

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
        Zzzzzz zzzzzz = new Zzzzzz(opener.getTailChars(delimiterUse), SubSequence.NULL, closer.getLeadChars(delimiterUse));
        opener.moveNodesBetweenDelimitersTo(zzzzzz, closer);
    }
}
