package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.node.Node;

public class NodeRenderingHandler<N extends Node> implements CustomNodeRenderer<N> {
    final Class<? extends N> myClass;
    final CustomNodeRenderer<N> myRender;

    public NodeRenderingHandler(Class<? extends N> aClass, CustomNodeRenderer<N> render) {
        myClass = aClass;
        myRender = render;
    }

    public Class<? extends N> getNodeType() {
        return myClass;
    }

    public CustomNodeRenderer<N> getNodeRenderer() {
        return myRender;
    }

    @Override
    public void render(Node node, NodeRendererContext context, HtmlWriter html) {
        myRender.render((N)node, context, html);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeRenderingHandler<?> entry = (NodeRenderingHandler<?>) o;

        if (!myClass.equals(entry.myClass)) return false;
        return myRender.equals(entry.myRender);
    }

    @Override
    public int hashCode() {
        int result = myClass.hashCode();
        result = 31 * result + myRender.hashCode();
        return result;
    }
}
