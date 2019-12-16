package com.vladsch.flexmark.parser.core.delimiter;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.parser.delimiter.DelimiterRun;
import com.vladsch.flexmark.util.ast.DelimitedNode;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class Delimiter implements DelimiterRun {
    final private Text node;
    final private BasedSequence input;
    final private char delimiterChar;
    private int index;

    /**
     * Can open emphasis, see spec.
     */
    final private boolean canOpen;

    /**
     * Can close emphasis, see spec.
     */
    final private boolean canClose;

    /**
     * Skip this delimiter when looking for a link/image opener because it was already matched.
     */
    private boolean matched = false;

    private Delimiter previous;
    private Delimiter next;

    private int numDelims = 1;

    @Override
    public Delimiter getPrevious() {
        return previous;
    }

    @Override
    public Delimiter getNext() {
        return next;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public void setPrevious(Delimiter previous) {
        this.previous = previous;
    }

    public void setNext(Delimiter next) {
        this.next = next;
    }

    public void setNumDelims(int numDelims) {
        this.numDelims = numDelims;
    }

    @Override
    public char getDelimiterChar() {
        return delimiterChar;
    }

    public boolean isMatched() {
        return matched;
    }

    public int getNumDelims() {
        return numDelims;
    }

    @Override
    public Text getNode() {
        return node;
    }

    public BasedSequence getInput() {
        return input;
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

    public Delimiter(BasedSequence input, Text node, char delimiterChar, boolean canOpen, boolean canClose, Delimiter previous, int index) {
        this.input = input;
        this.node = node;
        this.delimiterChar = delimiterChar;
        this.canOpen = canOpen;
        this.canClose = canClose;
        this.previous = previous;
        this.index = index;
    }

    public Text getPreviousNonDelimiterTextNode() {
        Node previousNode = node.getPrevious();
        if (previousNode instanceof Text && (this.previous == null || this.previous.node != previousNode)) {
            return (Text) previousNode;
        } else {
            return null;
        }
    }

    public Text getNextNonDelimiterTextNode() {
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

    public void convertDelimitersToText(int delimitersUsed, Delimiter closer) {
        Text openerText = new Text();
        openerText.setChars(getTailChars(delimitersUsed));
        Text closerText = new Text();
        closerText.setChars(closer.getLeadChars(delimitersUsed));

        getNode().insertAfter(openerText);
        closer.getNode().insertBefore(closerText);
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
