package com.vladsch.flexmark.ext.docusaurus.admonition;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

class DocusaurusAdmonitionNodeRenderer implements NodeRenderer {

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(DocusaurusAdmonitionBlock.class, this::render));
        return set;
    }

    private void render(DocusaurusAdmonitionBlock node, NodeRendererContext context, HtmlWriter html) {

        String type = node.getType().toString();

        html.attr("class", "docusaurus-admonition docusaurus-admonition-" + type);
        html.withAttr().tag("div");

        context.renderChildren(node);

        html.tag("/div");
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public @NotNull NodeRenderer apply(@NotNull DataHolder options) {
            return new DocusaurusAdmonitionNodeRenderer();
        }
    }
}
