package com.vladsch.flexmark.util.ast;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * Used to visit only block nodes, non block nodes or children of non-block nodes are not visited
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
@SuppressWarnings("rawtypes")
public class BlockNodeVisitor extends NodeVisitor {
    public BlockNodeVisitor() {
    }

    public BlockNodeVisitor(@NotNull VisitHandler... handlers) {
        super(handlers);
    }

    public BlockNodeVisitor(@NotNull VisitHandler[]... handlers) {
        super(handlers);
    }

    public BlockNodeVisitor(@NotNull Collection<VisitHandler> handlers) {
        super(handlers);
    }

    @Override
    public void processNode(@NotNull Node node, boolean withChildren, @NotNull BiConsumer<Node, Visitor<Node>> processor) {
        if (node instanceof Block) {
            super.processNode(node, withChildren, processor);
        }
    }
}
