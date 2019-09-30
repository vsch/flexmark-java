package com.vladsch.flexmark.util.visitor;

/**
 * Abstract visitor that visits all children by default.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public interface AstNodeVisitorBase<Node> extends AstAdapter<Node,Node> {
    void visit(Node node);
    void visitNoChildren(Node node);

    default void visitChildren(Node parent) {
        Node node = getFirstChild(parent);
        while (node != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            Node next = getNext(node);
            visit(node);
            node = next;
        }
    }
}
