package org.commonmark.internal.inline;

import org.commonmark.internal.Delimiter;
import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SubSequence;
import org.commonmark.node.DelimitedNode;
import org.commonmark.node.Emphasis;
import org.commonmark.node.Node;
import org.commonmark.node.StrongEmphasis;
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
    public void process(BasedSequence input, Delimiter opener, Delimiter closer, int delimiterUse) {
        DelimitedNode emphasis = delimiterUse == 1
                ? new Emphasis(input.subSequence(opener.getEndIndex()-delimiterUse, opener.getEndIndex()), SubSequence.EMPTY, input.subSequence(closer.getStartIndex(), closer.getStartIndex() + delimiterUse))
                : new StrongEmphasis(input.subSequence(opener.getEndIndex()-delimiterUse, opener.getEndIndex()), SubSequence.EMPTY, input.subSequence(closer.getStartIndex(), closer.getStartIndex() + delimiterUse));

        Node tmp = opener.getNode().getNext();
        while (tmp != null && tmp != closer.getNode()) {
            Node next = tmp.getNext();
            emphasis.appendChild(tmp);
            tmp = next;
        }

        emphasis.setContent(input.subSequence(opener.getEndIndex(), closer.getStartIndex()));
        opener.getNode().insertAfter(emphasis);
    }
}
