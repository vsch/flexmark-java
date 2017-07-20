package com.vladsch.flexmark.ext.gfm.issues.internal;

import com.vladsch.flexmark.ext.gfm.issues.GfmIssue;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GfmIssuesJiraRenderer implements NodeRenderer {
    private final GfmIssuesOptions options;

    public GfmIssuesJiraRenderer(DataHolder options) {
        this.options = new GfmIssuesOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<NodeRenderingHandler<? extends com.vladsch.flexmark.ast.Node>>(Arrays.asList(
                // @formatter:off
                new NodeRenderingHandler<GfmIssue>(GfmIssue.class, new CustomNodeRenderer<GfmIssue>() { @Override public void render(GfmIssue node, NodeRendererContext context, HtmlWriter html) { GfmIssuesJiraRenderer.this.render(node, context, html); } })
                // @formatter:on
        ));
    }

    private void render(GfmIssue node, NodeRendererContext context, HtmlWriter html) {
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
            return new GfmIssuesJiraRenderer(options);
        }
    }
}
