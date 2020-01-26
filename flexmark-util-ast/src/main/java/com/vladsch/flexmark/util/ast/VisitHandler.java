package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.visitor.AstHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Node visit handler for specific node type
 */
public class VisitHandler<N extends Node> extends AstHandler<N, Visitor<N>> implements Visitor<Node> {
    public VisitHandler(@NotNull Class<N> klass, @NotNull Visitor<N> adapter) {
        super(klass, adapter);
    }

    public void visit(@NotNull Node node) {
        //noinspection unchecked
        getAdapter().visit((N) node);
    }
}
