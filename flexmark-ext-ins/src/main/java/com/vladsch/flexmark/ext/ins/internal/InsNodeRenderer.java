package com.vladsch.flexmark.ext.ins.internal;

import com.vladsch.flexmark.ext.ins.Ins;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class InsNodeRenderer implements NodeRenderer {
    final private String insStyleHtmlOpen;
    final private String insStyleHtmlClose;

    public InsNodeRenderer(DataHolder options) {
        insStyleHtmlOpen = InsExtension.INS_STYLE_HTML_OPEN.get(options);
        insStyleHtmlClose = InsExtension.INS_STYLE_HTML_CLOSE.get(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(Ins.class, InsNodeRenderer.this::render));
        return set;
    }

    private void render(Ins node, NodeRendererContext context, HtmlWriter html) {
        if (insStyleHtmlOpen == null || insStyleHtmlClose == null) {
            if (context.getHtmlOptions().sourcePositionParagraphLines) {
                html.withAttr().tag("ins");
            } else {
                html.srcPos(node.getText()).withAttr().tag("ins");
            }
            context.renderChildren(node);
            html.tag("/ins");
        } else {
            html.raw(insStyleHtmlOpen);
            context.renderChildren(node);
            html.raw(insStyleHtmlClose);
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new InsNodeRenderer(options);
        }
    }
}
