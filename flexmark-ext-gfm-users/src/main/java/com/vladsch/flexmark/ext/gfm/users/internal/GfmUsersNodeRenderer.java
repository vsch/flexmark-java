package com.vladsch.flexmark.ext.gfm.users.internal;

import com.vladsch.flexmark.ext.gfm.users.GfmUser;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class GfmUsersNodeRenderer implements NodeRenderer
        // , PhasedNodeRenderer
{
    private final GfmUsersOptions options;

    public GfmUsersNodeRenderer(DataHolder options) {
        this.options = new GfmUsersOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        // @formatter:off
        set.add(new NodeRenderingHandler<GfmUser>(GfmUser.class, new CustomNodeRenderer<GfmUser>() { @Override public void render(GfmUser node, NodeRendererContext context, HtmlWriter html) { GfmUsersNodeRenderer.this.render(node, context, html); } }));
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
        @Override
        public NodeRenderer apply(final DataHolder options) {
            return new GfmUsersNodeRenderer(options);
        }
    }
}
