package com.vladsch.flexmark.util.ast;

import org.jetbrains.annotations.NotNull;

/**
 * Abstract visitor that visits all children by default.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public abstract class NodeVisitorBase {
    protected abstract void visit(@NotNull Node node);

    public void visitChildren(@NotNull Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            Node next = node.getNext();
            visit(node);
            node = next;
        }
    }
}
