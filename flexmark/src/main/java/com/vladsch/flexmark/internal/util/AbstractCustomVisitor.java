package com.vladsch.flexmark.internal.util;

import java.util.Collection;

/**
 * Abstract visitor that visits all children by default.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public class AbstractCustomVisitor extends AbstractCustomVisitorBase {
    public AbstractCustomVisitor(NodeVisitHandler<?>... visitors) {
        super(visitors);
    }

    public AbstractCustomVisitor(Collection<NodeVisitHandler<?>> visitors) {
        super(visitors);
    }

    public AbstractCustomVisitor(Computable<NodeVisitHandler<?>[], Object>... visitors) {
        super(visitors);
    }
}
