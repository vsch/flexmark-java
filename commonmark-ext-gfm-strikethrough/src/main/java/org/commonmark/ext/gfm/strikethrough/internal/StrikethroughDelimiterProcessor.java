package org.commonmark.ext.gfm.strikethrough.internal;

import org.commonmark.ext.gfm.strikethrough.Strikethrough;
import org.commonmark.internal.util.SubSequence;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.DelimiterProcessor;

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
    public void process(Text opener, Text closer, int delimiterUse) {
        // Can happen if a run had 3 or more delimiters, so 1 is left over. Don't turn that into strikethrough, but
        // preserve original character.
        if (delimiterUse == 1) {
            opener.insertAfter(new Text(opener.chars.subSequence(opener.chars.length()-delimiterUse, opener.chars.length())));
            closer.insertBefore(new Text(closer.chars.subSequence(0, delimiterUse)));
            return;
        }

        // Normal case, wrap nodes between delimiters in strikethrough.
        Strikethrough strikethrough = new Strikethrough(opener.chars.subSequence(opener.chars.length()-delimiterUse, opener.chars.length()), SubSequence.EMPTY, closer.chars.subSequence(0, delimiterUse));

        Node tmp = opener.getNext();
        while (tmp != null && tmp != closer) {
            Node next = tmp.getNext();
            strikethrough.appendChild(tmp);
            tmp = next;
        }

        strikethrough.setContent(opener.chars.baseSubSequence(opener.getEndOffset(), closer.getStartOffset()));
        opener.insertAfter(strikethrough);
    }
}
