package com.vladsch.flexmark.ext.jekyll.tag.internal;

import com.vladsch.flexmark.ext.jekyll.tag.JekyllTag;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagBlock;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JekyllTagNodeRenderer implements NodeRenderer {
    private final boolean enabledRendering;
    private final Map<String, String> includeContent;

    public JekyllTagNodeRenderer(DataHolder options) {
        enabledRendering = JekyllTagExtension.ENABLE_RENDERING.get(options);
        includeContent = JekyllTagExtension.INCLUDED_HTML.get(options);
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
        if (enabledRendering) html.text(node.getChars());
        else if (node.getTag().equals("include") && includeContent != null && !node.getParameters().isEmpty()) {
            String content = includeContent.get(node.getParameters().toString());
            if (content != null && !content.isEmpty()) {
                html.rawPre(content);
            }
        }
    }

    private void render(JekyllTagBlock node, NodeRendererContext context, HtmlWriter html) {
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
