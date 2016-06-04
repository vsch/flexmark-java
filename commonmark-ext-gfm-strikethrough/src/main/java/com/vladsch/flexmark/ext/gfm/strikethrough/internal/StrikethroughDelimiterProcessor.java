package com.vladsch.flexmark.ext.gfm.strikethrough.internal;

import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.internal.Delimiter;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.node.Node;
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
            // Can happen if a run had 3 delimiters before, and we removed 2 of them in an earlier processing step.
            // So just use 1 of them, see corresponding handling in process method.
            return 1;
        }
    }

    @Override
    public void process(BasedSequence input, Delimiter opener, Delimiter closer, int delimiterUse) {
        // Can happen if a run had 3 or more delimiters, so 1 is left over. Don't turn that into strikethrough, but
        // preserve original character.
        if (delimiterUse == 1) {
            opener.getNode().insertAfter(new Text(input.subSequence(opener.getEndIndex()-delimiterUse, opener.getEndIndex())));
            closer.getNode().insertBefore(new Text(input.subSequence(closer.getStartIndex(), closer.getStartIndex() + delimiterUse)));
            return;
        }

        // Normal case, wrap nodes between delimiters in strikethrough.
        Strikethrough strikethrough = new Strikethrough(input.subSequence(opener.getEndIndex()-delimiterUse, opener.getEndIndex()), SubSequence.EMPTY, input.subSequence(closer.getStartIndex(), closer.getStartIndex() + delimiterUse));

        Node tmp = opener.getNode().getNext();
        while (tmp != null && tmp != closer.getNode()) {
            Node next = tmp.getNext();
            strikethrough.appendChild(tmp);
            tmp = next;
        }

        strikethrough.setContent(input.subSequence(opener.getEndIndex(), closer.getStartIndex()));
        opener.getNode().insertAfter(strikethrough);
    }
}
