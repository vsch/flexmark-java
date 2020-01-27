package com.vladsch.flexmark.ext.gfm.issues.internal;

import com.vladsch.flexmark.ext.gfm.issues.GfmIssue;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GfmIssuesJiraRenderer implements NodeRenderer {
    final private GfmIssuesOptions options;

    public GfmIssuesJiraRenderer(DataHolder options) {
        this.options = new GfmIssuesOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Collections.singletonList(
                new NodeRenderingHandler<>(GfmIssue.class, this::render)
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
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new GfmIssuesJiraRenderer(options);
        }
    }
}
