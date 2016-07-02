package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.CustomBlock;
import com.vladsch.flexmark.node.CustomNode;
import com.vladsch.flexmark.node.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract visitor that visits all children by default.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public class AbstractCustomVisitor extends AbstractVisitor {
    final private Map<Class<?>, NodeVisitHandler<?>> myCustomVisitorsMap;

    public AbstractCustomVisitor(NodeVisitHandler<?>... visitors) {
        HashMap<Class<?>, NodeVisitHandler<?>> visitorMap = new HashMap<>();
        for (NodeVisitHandler<?> visitor : visitors) {
            visitorMap.put(visitor.getNodeType(), visitor);
        }
        this.myCustomVisitorsMap = visitorMap;
    }

    public AbstractCustomVisitor(Collection<NodeVisitHandler<?>> visitors) {
        HashMap<Class<?>, NodeVisitHandler<?>> visitorMap = new HashMap<>();
        for (NodeVisitHandler<?> visitor : visitors) {
            visitorMap.put(visitor.getNodeType(), visitor);
        }
        this.myCustomVisitorsMap = visitorMap;
    }

    private void customVisit(Node node) {
        NodeVisitHandler visitor = myCustomVisitorsMap.get(node.getClass());
        if (visitor != null) {
            visitor.visit(node);
        } else {
            visitChildren(node);
        }
    }
    
    // @formatter:off
    // blocks
    @Override public void visit(CustomBlock node) { customVisit(node); } 
    
    // inlines
    @Override public void visit(CustomNode node) { customVisit(node); } 
    // @formatter:on
}
