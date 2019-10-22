package com.vladsch.flexmark.util.ast;

import org.jetbrains.annotations.NotNull;

public abstract class AllNodesVisitor {
    protected abstract void process(@NotNull Node node);

    public void visit(@NotNull Node node) {
        visitChildren(node);
    }

    private void visitChildren(@NotNull Node parent) {
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
