package com.vladsch.flexmark.ext.gitlab.internal;

import com.vladsch.flexmark.ext.gitlab.GitLabBlockQuote;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GitLabNodeFormatter implements NodeFormatter {

    public GitLabNodeFormatter(DataHolder options) {

    }

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Collections.singletonList(
                new NodeFormattingHandler<>(GitLabBlockQuote.class, GitLabNodeFormatter.this::render)
        ));
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return new HashSet<>(Collections.singletonList(
                GitLabBlockQuote.class
        ));
    }

    private void render(GitLabBlockQuote node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(">>>").line();
        context.renderChildren(node);
        markdown.append(">>>").line();
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new GitLabNodeFormatter(options);
        }
    }
}
