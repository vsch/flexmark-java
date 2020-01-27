package com.vladsch.flexmark.ext.superscript.internal;

import com.vladsch.flexmark.ext.superscript.Superscript;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class SuperscriptNodeRenderer implements NodeRenderer {
    final private String superscriptStyleHtmlOpen;
    final private String superscriptStyleHtmlClose;

    public SuperscriptNodeRenderer(DataHolder options) {
        superscriptStyleHtmlOpen = SuperscriptExtension.SUPERSCRIPT_STYLE_HTML_OPEN.get(options);
        superscriptStyleHtmlClose = SuperscriptExtension.SUPERSCRIPT_STYLE_HTML_CLOSE.get(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(Superscript.class, this::render));
        return set;
    }

    private void render(Superscript node, NodeRendererContext context, HtmlWriter html) {
        if (superscriptStyleHtmlOpen == null || superscriptStyleHtmlClose == null) {
            if (context.getHtmlOptions().sourcePositionParagraphLines) {
                html.withAttr().tag("sup");
            } else {
                html.srcPos(node.getText()).withAttr().tag("sup");
            }
            context.renderChildren(node);
            html.tag("/sup");
        } else {
            html.raw(superscriptStyleHtmlOpen);
            context.renderChildren(node);
            html.raw(superscriptStyleHtmlClose);
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new SuperscriptNodeRenderer(options);
        }
    }
}
