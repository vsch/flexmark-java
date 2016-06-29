package com.vladsch.flexmark.ext.gfm.strikethrough.internal;

import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.internal.Delimiter;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.node.Text;
import com.vladsch.flexmark.parser.DelimiterProcessor;

public class StrikethroughDelimiterProcessor implements DelimiterProcessor {

    @Override
    public char getOpeningDelimiterChar() {
        return '~';
    }

    @Override
    public char getClosingDelimiterChar() {
        return '~';
    }

    @Override
    public int getMinDelimiterCount() {
        return 2;
    }

    @Override
    public int getDelimiterUse(int openerCount, int closerCount) {
        if (openerCount >= 2 && closerCount >= 2) {
            return 2;
        } else {
            // Can happen if a run had 3 delimiters before, and we blockRemoved 2 of them in an earlier processing step.
            // So just use 1 of them, see corresponding handling in process method.
            return 1;
        }
    }

    @Override
    public void process(Delimiter opener, Delimiter closer, int delimiterUse) {
        // Can happen if a run had 3 or more delimiters, so 1 is left over. Don't turn that into strikethrough, but
        // preserve original character.
        if (delimiterUse == 1) {
            opener.getNode().insertAfter(new Text(opener.getTailChars(delimiterUse)));
            closer.getNode().insertBefore(new Text(closer.getLeadChars(delimiterUse)));
            return;
        }

        // Normal case, wrap nodes between delimiters in strikethrough.
        Strikethrough strikethrough = new Strikethrough(opener.getTailChars(delimiterUse), SubSequence.NULL, closer.getLeadChars(delimiterUse));
        opener.moveNodesBetweenDelimitersTo(strikethrough, closer);
    }
}
