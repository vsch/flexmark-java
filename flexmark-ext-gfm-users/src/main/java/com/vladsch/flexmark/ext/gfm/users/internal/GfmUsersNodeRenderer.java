package com.vladsch.flexmark.ext.gfm.users.internal;

import com.vladsch.flexmark.ext.gfm.users.GfmUser;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class GfmUsersNodeRenderer implements NodeRenderer
        // , PhasedNodeRenderer
{
    final private GfmUsersOptions options;

    public GfmUsersNodeRenderer(DataHolder options) {
        this.options = new GfmUsersOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        // @formatter:off
        set.add(new NodeRenderingHandler<>(GfmUser.class, GfmUsersNodeRenderer.this::render));
        // @formatter:on
        return set;
    }

    private void render(GfmUser node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            html.text(node.getChars());
        } else {
            StringBuilder sb = new StringBuilder();

            sb.append(options.gitHubIssuesUrlRoot).append(options.gitHubIssueUrlPrefix).append(node.getText()).append(options.gitHubIssueUrlSuffix);

            html.srcPos(node.getChars()).attr("href", sb.toString()).withAttr().tag("a");
            html.raw(options.gitHubUserTextPrefix);
            html.text(node.getChars());
            html.raw(options.gitHubUserTextSuffix);
            html.tag("/a");
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new GfmUsersNodeRenderer(options);
        }
    }
}
