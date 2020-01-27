package com.vladsch.flexmark.parser.core.delimiter;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Opening bracket for links (<code>[</code>) or images (<code>![</code>).
 */
public class Bracket {
    final private Text node;
    final private int index;
    final private boolean image;

    /**
     * Previous bracket.
     */
    final private Bracket previous;

    /**
     * Previous delimiter (emphasis, etc) before this bracket.
     */
    final private Delimiter previousDelimiter;

    /**
     * Whether this bracket is allowed to form a link/image (also known as "active").
     */
    private boolean allowed = true;

    /**
     * Whether there is an unescaped bracket (opening or closing) anywhere after this opening bracket.
     * determined by next != null
     */
    private boolean bracketAfter = false;

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public boolean isBracketAfter() {
        return bracketAfter;
    }

    public void setBracketAfter(boolean bracketAfter) {
        this.bracketAfter = bracketAfter;
    }

    public Bracket getPrevious() {
        return previous;
    }

    public boolean isImage() {
        return image;
    }

    public Delimiter getPreviousDelimiter() {
        return previousDelimiter;
    }

    public int getStartIndex() {
        return index;
    }

    public int getEndIndex() {
        return image ? index + 2 : index + 1;
    }

    public Text getNode() {
        return node;
    }

    public static Bracket link(BasedSequence input, Text node, int index, Bracket previous, Delimiter previousDelimiter) {
        return new Bracket(input, node, index, previous, previousDelimiter, false);
    }

    public static Bracket image(BasedSequence input, Text node, int index, Bracket previous, Delimiter previousDelimiter) {
        return new Bracket(input, node, index, previous, previousDelimiter, true);
    }

    private Bracket(BasedSequence input, Text node, int index, Bracket previous, Delimiter previousDelimiter, boolean image) {
        this.node = node;
        this.index = index;
        this.image = image;
        this.previous = previous;
        this.previousDelimiter = previousDelimiter;
    }

    public boolean isStraddling(BasedSequence nodeChars) {
        // first see if we have any closers in our span
        int startOffset = nodeChars.getStartOffset();
        int endOffset = nodeChars.getEndOffset();
        Delimiter inner = previousDelimiter == null ? null : previousDelimiter.getNext();
        while (inner != null) {
            int innerOffset = inner.getEndIndex();
            if (innerOffset >= endOffset) break;
            if (innerOffset >= startOffset) {
                // inside our region, if unmatched then we are straddling the region
                if (!inner.isMatched()) return true;
            }
            inner = inner.getNext();
        }
        return false;
    }
}
