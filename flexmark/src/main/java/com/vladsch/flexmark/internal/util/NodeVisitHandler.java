package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.Node;

public class NodeVisitHandler<N extends Node> implements CustomNodeVisitor<N> {
    final Class<? extends N> myClass;
    final CustomNodeVisitor<N> myVisitor;

    public NodeVisitHandler(Class<? extends N> aClass, CustomNodeVisitor<N> visitor) {
        myClass = aClass;
        myVisitor = visitor;
    }

    public Class<? extends N> getNodeType() {
        return myClass;
    }

    public CustomNodeVisitor<N> getNodeVisitor() {
        return myVisitor;
    }

    @Override
    public void visit(Node node) {
        myVisitor.visit((N)node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeVisitHandler<?> entry = (NodeVisitHandler<?>) o;

        if (!myClass.equals(entry.myClass)) return false;
        return myVisitor.equals(entry.myVisitor);
    }

    @Override
    public int hashCode() {
        int result = myClass.hashCode();
        result = 31 * result + myVisitor.hashCode();
        return result;
    }
}
