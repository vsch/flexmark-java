package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.CustomNode;

import java.util.Collection;

/**
 * Abstract visitor that visits all children by default.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public class AbstractCustomBlockVisitor extends AbstractCustomVisitor {
    public AbstractCustomBlockVisitor(NodeVisitHandler<?>... visitors) {
        super(visitors);
    }

    public AbstractCustomBlockVisitor(Collection<NodeVisitHandler<?>> visitors) {
        super(visitors);
    }

    // @formatter:off
    // blocks
    @Override public void visit(CustomNode node) {  }
    // @formatter:on
}
