package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.visitor.AstAction;
import com.vladsch.flexmark.util.visitor.AstHandler;
import org.jetbrains.annotations.NotNull;

public class NodeFormattingHandler<N extends Node> extends AstHandler<N, NodeFormattingHandler.CustomNodeFormatter<N>> {
    public NodeFormattingHandler(@NotNull Class<N> aClass, @NotNull CustomNodeFormatter<N> adapter) {
        super(aClass, adapter);
    }

    public void render(@NotNull Node node, @NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown) {
        //noinspection unchecked
        getAdapter().render((N) node, context, markdown);
    }

    public static interface CustomNodeFormatter<N extends Node> extends AstAction<N> {
        void render(@NotNull N node, @NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown);
    }
}
