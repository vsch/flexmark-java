package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.node.Text;

public class Delimiter {

    final Text node;
    final int index;

    public Text getNode() {
        return node;
    }

    public int getStartIndex() {
        return index;
    }

    public int getEndIndex() {
        return index + numDelims;
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

    Delimiter(Text node, Delimiter previous, int index) {
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
}
