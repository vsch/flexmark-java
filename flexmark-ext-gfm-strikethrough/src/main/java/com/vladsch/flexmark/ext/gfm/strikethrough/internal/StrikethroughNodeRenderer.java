package com.vladsch.flexmark.ext.gfm.strikethrough.internal;

import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.Subscript;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class StrikethroughNodeRenderer implements NodeRenderer {

    private final String strikethroughStyleHtmlOpen;
    private final String strikethroughStyleHtmlClose;
    private final String subscriptStyleHtmlOpen;
    private final String subscriptStyleHtmlClose;

    public StrikethroughNodeRenderer(DataHolder options) {
        strikethroughStyleHtmlOpen = StrikethroughSubscriptExtension.STRIKETHROUGH_STYLE_HTML_OPEN.getFrom(options);
        strikethroughStyleHtmlClose = StrikethroughSubscriptExtension.STRIKETHROUGH_STYLE_HTML_CLOSE.getFrom(options);
        subscriptStyleHtmlOpen = StrikethroughSubscriptExtension.SUBSCRIPT_STYLE_HTML_OPEN.getFrom(options);
        subscriptStyleHtmlClose = StrikethroughSubscriptExtension.SUBSCRIPT_STYLE_HTML_CLOSE.getFrom(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        set.add(new NodeRenderingHandler<Strikethrough>(Strikethrough.class, new CustomNodeRenderer<Strikethrough>() {
            @Override
            public void render(Strikethrough node, NodeRendererContext context, HtmlWriter html) {
                StrikethroughNodeRenderer.this.render(node, context, html);
            }
        }));
        set.add(new NodeRenderingHandler<Subscript>(Subscript.class, new CustomNodeRenderer<Subscript>() {
            @Override
            public void render(Subscript node, NodeRendererContext context, HtmlWriter html) {
                StrikethroughNodeRenderer.this.render(node, context, html);
            }
        }));

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
        @Override
        public NodeRenderer apply(final DataHolder options) {
            return new StrikethroughNodeRenderer(options);
        }
    }
}
