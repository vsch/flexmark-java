package com.vladsch.flexmark.ext.jekyll.tag.internal;

import com.vladsch.flexmark.ext.jekyll.tag.JekyllTag;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagBlock;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JekyllTagNodeRenderer implements NodeRenderer {
    final private boolean embedIncludes;
    final private Map<String, String> includeContent;

    public JekyllTagNodeRenderer(DataHolder options) {
        includeContent = JekyllTagExtension.INCLUDED_HTML.get(options);
        embedIncludes = JekyllTagExtension.EMBED_INCLUDED_CONTENT.get(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        // @formatter:off
        set.add(new NodeRenderingHandler<>(JekyllTag.class, this::render));
        set.add(new NodeRenderingHandler<>(JekyllTagBlock.class, this::render));
        // @formatter:on
        return set;
    }

    private void render(JekyllTag node, NodeRendererContext context, HtmlWriter html) {
        if (embedIncludes) {
            context.renderChildren(node);
        } else {
            // nothing to do since includes are not rendered
        }
    }

    private void render(JekyllTagBlock node, NodeRendererContext context, HtmlWriter html) {
//        if (embedIncludes) {
//            // remove jekyll tag node and just leave the included content
//            Node child = node.getFirstChild();
//
//            if (child != null) child = child.getNextAnyNot(JekyllTag.class);
//
//            while (child != null) {
//                Node next = child.getNextAnyNot(JekyllTag.class);
//                context.render(child);
//                child = next;
//            }
//        } else {
//            Node child = node.getFirstChild();
//            while (child != null) {
//                Node next = child.getNextAny(JekyllTag.class);
//                context.render(child);
//                child = next;
//            }
//        }
        context.renderChildren(node);
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new JekyllTagNodeRenderer(options);
        }
    }
}
