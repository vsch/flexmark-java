package com.vladsch.flexmark.ext.gfm.users.internal;

import com.vladsch.flexmark.ext.gfm.users.GfmUser;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GfmUsersJiraRenderer implements NodeRenderer {
    private final GfmUsersOptions options;

    public GfmUsersJiraRenderer(DataHolder options) {
        this.options = new GfmUsersOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<NodeRenderingHandler<? extends com.vladsch.flexmark.ast.Node>>(Arrays.asList(
                // @formatter:off
                new NodeRenderingHandler<GfmUser>(GfmUser.class, new CustomNodeRenderer<GfmUser>() { @Override public void render(GfmUser node, NodeRendererContext context, HtmlWriter html) { GfmUsersJiraRenderer.this.render(node, context, html); } })
                // @formatter:on
        ));
    }

    private void render(GfmUser node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            html.raw(node.getChars());
        } else {
            StringBuilder sb = new StringBuilder();

            sb.append(options.gitHubIssuesUrlRoot).append(options.gitHubIssueUrlPrefix).append(node.getText()).append(options.gitHubIssueUrlSuffix);

            html.raw("[");
            html.raw(node.getChars());
            html.raw("|").raw(sb.toString()).raw("]");
        }
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new GfmUsersJiraRenderer(options);
        }
    }
}
