package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.visitor.AstAction;
import com.vladsch.flexmark.util.visitor.AstHandler;
import org.jetbrains.annotations.NotNull;

public class NodeRenderingHandler<N extends Node> extends AstHandler<N, NodeRenderingHandler.CustomNodeRenderer<N>> {
    public NodeRenderingHandler(@NotNull Class<N> aClass, @NotNull CustomNodeRenderer<N> adapter) {
        super(aClass, adapter);
    }

    public void render(@NotNull Node node, @NotNull NodeRendererContext context, @NotNull HtmlWriter html) {
        //noinspection unchecked
        getAdapter().render((N) node, context, html);
    }

    public interface CustomNodeRenderer<N extends Node> extends AstAction<N> {
        void render(@NotNull N node, @NotNull NodeRendererContext context, @NotNull HtmlWriter html);
    }
}
