package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Opening bracket for links (<code>[</code>) or images (<code>![</code>).
 */
public class Bracket {
    final Text node;
    final int index;
    final boolean image;
    final BasedSequence input;

    /**
     * Previous bracket.
     */
    final Bracket previous;

    /**
     * Previous delimiter (emphasis, etc) before this bracket.
     */
    final Delimiter previousDelimiter;

    /**
     * Whether this bracket is allowed to form a link/image (also known as "active").
     */
    boolean allowed = true;

    /**
     * Whether there is an unescaped bracket (opening or closing) anywhere after this opening bracket.
     * determined by next != null
     */
    boolean bracketAfter = false;

    int getStartIndex() {
        return index;
    }

    int getEndIndex() {
        return image ? index + 2 : index + 1;
    }

    static Bracket link(BasedSequence input, Text node, int index, Bracket previous, Delimiter previousDelimiter) {
        return new Bracket(input, node, index, previous, previousDelimiter, false);
    }

    static Bracket image(BasedSequence input, Text node, int index, Bracket previous, Delimiter previousDelimiter) {
        return new Bracket(input, node, index, previous, previousDelimiter, true);
    }

    private Bracket(BasedSequence input, Text node, int index, Bracket previous, Delimiter previousDelimiter, boolean image) {
        this.node = node;
        this.index = index;
        this.image = image;
        this.previous = previous;
        this.previousDelimiter = previousDelimiter;
        this.input = input;
    }

    public boolean isStraddling(BasedSequence nodeChars) {
        // first see if we have any closers in our span
        int startOffset = nodeChars.getStartOffset();
        int endOffset = nodeChars.getEndOffset();
        Delimiter inner = previousDelimiter == null ? null : previousDelimiter.next;
        while (inner != null) {
            int innerOffset = inner.getEndIndex();
            if (innerOffset >= endOffset) break;
            if (innerOffset >= startOffset) {
                // inside our region, if unmatched then we are straddling the region
                if (!inner.matched) return true;
            }
            inner = inner.next;
        }
        return false;
    }
}
