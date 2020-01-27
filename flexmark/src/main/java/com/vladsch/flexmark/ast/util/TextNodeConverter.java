package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.ArrayList;
import java.util.List;

public class TextNodeConverter {
    final private BasedSequence nodeChars;
    private BasedSequence remainingChars;
    final private ArrayList<Node> list = new ArrayList<>();

    public TextNodeConverter(BasedSequence nodeChars) {
        this.nodeChars = nodeChars;
        this.remainingChars = nodeChars;
    }

    public void appendChild(Node child) {
        BasedSequence childChars = child.getChars();
        assert nodeChars.containsAllOf(childChars) : "child " + child.toAstString(false) + " is not within parent sequence " + Node.toSegmentSpan(nodeChars, null);
        assert remainingChars.containsAllOf(childChars) : "child " + child.toAstString(false) + " is not within remaining sequence " + Node.toSegmentSpan(remainingChars, null);
        child.unlink();
        if (!(child instanceof Text)) {
            if (remainingChars.getStartOffset() < childChars.getStartOffset()) {
                // add preceding chars as Text
                list.add(new Text(remainingChars.subSequence(0, childChars.getStartOffset() - remainingChars.getStartOffset())));
            }

            // punch out remaining node chars
            remainingChars = remainingChars.subSequence(childChars.getEndOffset() - remainingChars.getStartOffset());
            list.add(child);
        }
    }

    public void addChildrenOf(Node parent) {
        Node child = parent.getFirstChild();
        while (child != null) {
            Node nextChild = child.getNext();
            appendChild(child);
            child = nextChild;
        }
    }

    public void appendMergedTo(Node parent) {
        mergeList();
        for (Node child : list) {
            parent.appendChild(child);
        }
        clear();
    }

    public void clear() {
        list.clear();
        remainingChars = BasedSequence.NULL;
    }

    // insert and clear list
    public void insertMergedBefore(Node sibling) {
        mergeList();
        for (Node node : list) {
            sibling.insertBefore(node);
        }
        clear();
    }

    // insert and clear list
    public static void mergeTextNodes(Node parent) {
        Node prevNode = null;
        Node child = parent.getFirstChild();
        while (child != null) {
            Node nextChild = child.getNext();
            if (prevNode instanceof Text && child instanceof Text && prevNode.getChars().isContinuedBy(child.getChars())) {
                // merge them
                child.setChars(prevNode.getChars().spliceAtEnd(child.getChars()));
                prevNode.unlink();
            }
            prevNode = child;
            child = nextChild;
        }
    }

    // insert and clear list
    public void insertMergedAfter(Node sibling) {
        mergeList();
        for (Node node : list) {
            sibling.insertAfter(node);
            sibling = node;
        }
        clear();
    }

    private void mergeList() {
        if (!remainingChars.isEmpty()) {
            list.add(new Text(remainingChars));
            remainingChars = BasedSequence.NULL;
        }
    }

    public List<Node> getMergedList() {
        mergeList();
        return list;
    }
}
