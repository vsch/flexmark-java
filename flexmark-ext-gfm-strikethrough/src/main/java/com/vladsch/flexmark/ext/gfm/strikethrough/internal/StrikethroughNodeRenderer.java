package com.vladsch.flexmark.ext.gfm.strikethrough.internal;

import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.Subscript;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class StrikethroughNodeRenderer implements NodeRenderer {

    final private String strikethroughStyleHtmlOpen;
    final private String strikethroughStyleHtmlClose;
    final private String subscriptStyleHtmlOpen;
    final private String subscriptStyleHtmlClose;

    public StrikethroughNodeRenderer(DataHolder options) {
        strikethroughStyleHtmlOpen = StrikethroughSubscriptExtension.STRIKETHROUGH_STYLE_HTML_OPEN.get(options);
        strikethroughStyleHtmlClose = StrikethroughSubscriptExtension.STRIKETHROUGH_STYLE_HTML_CLOSE.get(options);
        subscriptStyleHtmlOpen = StrikethroughSubscriptExtension.SUBSCRIPT_STYLE_HTML_OPEN.get(options);
        subscriptStyleHtmlClose = StrikethroughSubscriptExtension.SUBSCRIPT_STYLE_HTML_CLOSE.get(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(Strikethrough.class, this::render));
        set.add(new NodeRenderingHandler<>(Subscript.class, this::render));
        return set;
    }

    private void render(Strikethrough node, NodeRendererContext context, HtmlWriter html) {
        if (strikethroughStyleHtmlOpen == null || strikethroughStyleHtmlClose == null) {
            if (context.getHtmlOptions().sourcePositionParagraphLines) {
                html.withAttr().tag("del");
            } else {
                html.srcPos(node.getText()).withAttr().tag("del");
            }
            context.renderChildren(node);
            html.tag("/del");
        } else {
            html.raw(strikethroughStyleHtmlOpen);
            context.renderChildren(node);
            html.raw(strikethroughStyleHtmlClose);
        }
    }

    private void render(Subscript node, NodeRendererContext context, HtmlWriter html) {
        if (subscriptStyleHtmlOpen == null || subscriptStyleHtmlClose == null) {
            if (context.getHtmlOptions().sourcePositionParagraphLines) {
                html.withAttr().tag("sub");
            } else {
                html.srcPos(node.getText()).withAttr().tag("sub");
            }
            context.renderChildren(node);
            html.tag("/sub");
        } else {
            html.raw(subscriptStyleHtmlOpen);
            context.renderChildren(node);
            html.raw(subscriptStyleHtmlClose);
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new StrikethroughNodeRenderer(options);
        }
    }
}
