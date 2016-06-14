package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.DelimitedNode;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.node.Text;

public class Delimiter {

    final Text node;
    final int index;
    final BasedSequence input;

    public Text getNode() {
        return node;
    }

    public int getStartIndex() {
        return index;
    }

    public int getEndIndex() {
        return index + numDelims;
    }

    public BasedSequence getTailChars(int delimiterUse) {
        return input.subSequence(getEndIndex() - delimiterUse, getEndIndex());
    }

    public BasedSequence getLeadChars(int delimiterUse) {
        return input.subSequence(getStartIndex(), getStartIndex() + delimiterUse);
    }

    Delimiter previous;
    Delimiter next;

    char delimiterChar;
    int numDelims = 1;

    /**
     * Can open emphasis, see spec.
     */
    boolean canOpen = true;

    /**
     * Can close emphasis, see spec.
     */
    boolean canClose = false;

    /**
     * Whether this delimiter is allowed to form a link/image.
     */
    boolean allowed = true;

    /**
     * Skip this delimiter when looking for a link/image opener because it was already matched.
     */
    boolean matched = false;

    Delimiter(BasedSequence input, Text node, Delimiter previous, int index) {
        this.input = input;
        this.node = node;
        this.previous = previous;
        this.index = index;
    }

    Text getPreviousNonDelimiterTextNode() {
        Node previousNode = node.getPrevious();
        if (previousNode instanceof Text && (this.previous == null || this.previous.node != previousNode)) {
            return (Text) previousNode;
        } else {
            return null;
        }
    }

    Text getNextNonDelimiterTextNode() {
        Node nextNode = node.getNext();
        if (nextNode instanceof Text && (this.next == null || this.next.node != nextNode)) {
            return (Text) nextNode;
        } else {
            return null;
        }
    }

    public void moveNodesBetweenDelimitersTo(DelimitedNode delimitedNode, Delimiter closer) {
        Node tmp = getNode().getNext();
        while (tmp != null && tmp != closer.getNode()) {
            Node next = tmp.getNext();
            ((Node)delimitedNode).appendChild(tmp);
            tmp = next;
        }

        delimitedNode.setText(input.subSequence(getEndIndex(), closer.getStartIndex()));
        getNode().insertAfter((Node) delimitedNode);
    }

    public boolean isStraddling(BasedSequence nodeChars) {
        // first see if we have any closers in our span
        int startOffset = nodeChars.getStartOffset();
        int endOffset = nodeChars.getEndOffset();
        Delimiter inner = this.next;
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
