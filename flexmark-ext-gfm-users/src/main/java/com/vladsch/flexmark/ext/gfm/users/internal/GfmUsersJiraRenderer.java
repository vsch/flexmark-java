package com.vladsch.flexmark.ext.gfm.users.internal;

import com.vladsch.flexmark.ext.gfm.users.GfmUser;
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

public class GfmUsersJiraRenderer implements NodeRenderer {
    final private GfmUsersOptions options;

    public GfmUsersJiraRenderer(DataHolder options) {
        this.options = new GfmUsersOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Collections.singletonList(
                new NodeRenderingHandler<>(GfmUser.class, this::render)
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
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new GfmUsersJiraRenderer(options);
        }
    }
}
