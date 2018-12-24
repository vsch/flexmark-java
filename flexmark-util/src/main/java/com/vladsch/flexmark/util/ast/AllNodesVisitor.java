package com.vladsch.flexmark.util.ast;

public abstract class AllNodesVisitor {
    protected abstract void process(Node node);

    public void visit(Node node) {
        visitChildren(node);
    }

    private void visitChildren(final Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            Node next = node.getNext();
            process(node);
            visit(node);
            node = next;
        }
    }
}
