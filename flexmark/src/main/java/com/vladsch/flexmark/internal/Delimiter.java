package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.ast.DelimitedNode;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.parser.delimiter.DelimiterRun;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class Delimiter implements DelimiterRun {

    final Text node;
    final BasedSequence input;
    final char delimiterChar;
    int index;

    /**
     * Can open emphasis, see spec.
     */
    final boolean canOpen;

    /**
     * Can close emphasis, see spec.
     */
    final boolean canClose;

    /**
     * Skip this delimiter when looking for a link/image opener because it was already matched.
     */
    boolean matched = false;

    Delimiter previous;
    Delimiter next;

    int numDelims = 1;

    public Text getNode() {
        return node;
    }

    public int getStartIndex() {
        return index;
    }

    public int getEndIndex() {
        return index + numDelims;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BasedSequence getTailChars(int delimiterUse) {
        return input.subSequence(getEndIndex() - delimiterUse, getEndIndex());
    }

    public BasedSequence getLeadChars(int delimiterUse) {
        return input.subSequence(getStartIndex(), getStartIndex() + delimiterUse);
    }

    Delimiter(BasedSequence input, Text node, char delimiterChar, boolean canOpen, boolean canClose, Delimiter previous, int index) {
        this.input = input;
        this.node = node;
        this.delimiterChar = delimiterChar;
        this.canOpen = canOpen;
        this.canClose = canClose;
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
            ((Node) delimitedNode).appendChild(tmp);
            tmp = next;
        }
        
        delimitedNode.setText(input.subSequence(getEndIndex(), closer.getStartIndex()));
        getNode().insertAfter((Node) delimitedNode);
    }

    @Override
    public boolean canOpen() {
        return canOpen;
    }

    @Override
    public boolean canClose() {
        return canClose;
    }

    @Override
    public int length() {
        return numDelims;
    }
}
