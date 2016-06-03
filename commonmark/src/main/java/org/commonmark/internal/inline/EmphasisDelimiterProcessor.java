package org.commonmark.internal.inline;

import org.commonmark.internal.util.SubSequence;
import org.commonmark.node.*;
import org.commonmark.parser.DelimiterProcessor;

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
    public void process(Text opener, Text closer, int delimiterUse) {
        DelimitedNode emphasis = delimiterUse == 1
                ? new Emphasis(opener.chars.subSequence(opener.chars.length()-delimiterUse, opener.chars.length()), SubSequence.EMPTY, closer.chars.subSequence(0, delimiterUse))
                : new StrongEmphasis(opener.chars.subSequence(opener.chars.length()-delimiterUse, opener.chars.length()), SubSequence.EMPTY, closer.chars.subSequence(0, delimiterUse));

        Node tmp = opener.getNext();
        while (tmp != null && tmp != closer) {
            Node next = tmp.getNext();
            emphasis.appendChild(tmp);
            tmp = next;
        }

        emphasis.setContent(opener.chars.baseSubSequence(opener.getEndOffset(), closer.getStartOffset()));
        opener.insertAfter(emphasis);
    }
}
