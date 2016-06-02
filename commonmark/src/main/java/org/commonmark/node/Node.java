package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SubSequence;
import org.commonmark.internal.util.Substring;

public abstract class Node {

    private Node parent = null;
    private Node firstChild = null;
    private Node lastChild = null;
    private Node prev = null;
    private Node next = null;
    protected int offsetInParent = -1;
    protected int textLength = 0;

    public Node() {
    }

    public abstract void accept(Visitor visitor);

    // full document char sequence
    public CharSequence getCharSequence() {
        return parent == null ? Substring.EMPTY : parent.getCharSequence();
    }

    public void setSourcePos(int offsetInParent, int textLength) {
        this.offsetInParent  = offsetInParent;
        this.textLength = textLength;
    }

    public Node(int offsetInParent, int textLength) {
        this.offsetInParent = offsetInParent;
        this.textLength = textLength;
    }

    public Node getNext() {
        return next;
    }

    public int getOffsetInParent() {
        return offsetInParent;
    }

    public int getTextLength() {
        return textLength;
    }

    public int getEndOffset() {
        return getOffset() + textLength;
    }

    public BasedSequence getText(CharSequence charSequence) {
        int offset = getOffset();
        return new SubSequence(charSequence, offset, offset + textLength);
    }

    public int getOffset() {
        int offset = 0;
        Node node = this;
        do {
            offset += node.offsetInParent;
            node = node.parent;
        } while (node != null);

        return offset;
    }

    public Node getPrevious() {
        return prev;
    }

    public Node getFirstChild() {
        return firstChild;
    }

    public Node getLastChild() {
        return lastChild;
    }

    public Node getParent() {
        return parent;
    }

    protected void setParent(Node parent) {
        this.parent = parent;
    }

    public void appendChild(Node child) {
        child.unlink();
        child.setParent(this);
        if (this.lastChild != null) {
            this.lastChild.next = child;
            child.prev = this.lastChild;
            this.lastChild = child;
        } else {
            this.firstChild = child;
            this.lastChild = child;
        }
    }

    public void prependChild(Node child) {
        child.unlink();
        child.setParent(this);
        if (this.firstChild != null) {
            this.firstChild.prev = child;
            child.next = this.firstChild;
            this.firstChild = child;
        } else {
            this.firstChild = child;
            this.lastChild = child;
        }
    }

    public void unlink() {
        if (this.prev != null) {
            this.prev.next = this.next;
        } else if (this.parent != null) {
            this.parent.firstChild = this.next;
        }
        if (this.next != null) {
            this.next.prev = this.prev;
        } else if (this.parent != null) {
            this.parent.lastChild = this.prev;
        }
        this.parent = null;
        this.next = null;
        this.prev = null;
    }

    public void insertAfter(Node sibling) {
        sibling.unlink();
        sibling.next = this.next;
        if (sibling.next != null) {
            sibling.next.prev = sibling;
        }
        sibling.prev = this;
        this.next = sibling;
        sibling.parent = this.parent;
        if (sibling.next == null) {
            sibling.parent.lastChild = sibling;
        }
    }

    public void insertBefore(Node sibling) {
        sibling.unlink();
        sibling.prev = this.prev;
        if (sibling.prev != null) {
            sibling.prev.next = sibling;
        }
        sibling.next = this;
        this.prev = sibling;
        sibling.parent = this.parent;
        if (sibling.prev == null) {
            sibling.parent.firstChild = sibling;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + toStringAttributes() + "}";
    }

    protected String toStringAttributes() {
        return "";
    }
}
