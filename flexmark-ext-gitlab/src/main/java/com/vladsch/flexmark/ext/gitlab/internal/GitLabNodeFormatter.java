package com.vladsch.flexmark.ext.gitlab.internal;

import com.vladsch.flexmark.ext.gitlab.GitLabBlockQuote;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GitLabNodeFormatter implements NodeFormatter {

    public GitLabNodeFormatter(DataHolder options) {

    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Collections.singletonList(
                new NodeFormattingHandler<>(GitLabBlockQuote.class, GitLabNodeFormatter.this::render)
        ));
    }

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
        @Override
        public NodeFormatter create(DataHolder options) {
            return new GitLabNodeFormatter(options);
        }
    }
}
