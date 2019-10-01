package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.ArrayList;
import java.util.List;

public class TextNodeMergingList {
    private ArrayList<Node> list = new ArrayList<>();
    private boolean isMerged = true;

    public void add(Node node) {
        list.add(node);
        if (node instanceof Text) isMerged = false;
    }

    public void add(BasedSequence nodeChars) {
        if (!nodeChars.isEmpty()) {
            add(new Text(nodeChars));
        }
    }

    public void addChildrenOf(Node parent) {
        Node child = parent.getFirstChild();
        while (child != null) {
            Node nextChild = child.getNext();
            child.unlink();
            add(child);
            child = nextChild;
        }
    }

    public void appendMergedTo(Node parent) {
        mergeList();
        for (Node child : list) {
            parent.appendChild(child);
        }
    }

    public void clear() {
        list.clear();
        isMerged = true;
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
    public void insertMergedAfter(Node sibling) {
        mergeList();
        for (Node node : list) {
            sibling.insertAfter(node);
            sibling = node;
        }
        clear();
    }

    private void mergeList() {
        if (!isMerged) {
            // go through and see if some can be combined
            ArrayList<Node> mergedList = null;

            Node lastText = null;

            for (Node child : list) {
                if (child instanceof Text) {
                    if (!child.getChars().isEmpty()) {
                        if (lastText == null) {
                            lastText = child;
                        } else if (lastText.getChars().isContinuedBy(child.getChars())) {
                            // merge their text
                            lastText.setChars(lastText.getChars().spliceAtEnd(child.getChars()));
                        } else {
                            if (mergedList == null) mergedList = new ArrayList<>();
                            mergedList.add(lastText);
                            lastText = child;
                        }
                    }
                } else {
                    if (mergedList == null) mergedList = new ArrayList<>();
                    if (lastText != null) {
                        mergedList.add(lastText);
                        lastText = null;
                    }
                    mergedList.add(child);
                }
            }

            if (lastText != null) {
                if (mergedList == null) {
                    list.clear();
                    list.add(lastText);
                } else {
                    mergedList.add(lastText);
                }
            }

            if (mergedList != null) {
                list = mergedList;
            }
        }
    }

    public List<Node> getMergedList() {
        mergeList();
        return list;
    }
}
