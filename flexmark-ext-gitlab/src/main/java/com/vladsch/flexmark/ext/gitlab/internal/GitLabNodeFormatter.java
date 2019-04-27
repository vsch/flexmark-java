package com.vladsch.flexmark.ext.gitlab.internal;

import com.vladsch.flexmark.ext.gitlab.GitLabBlockQuote;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GitLabNodeFormatter implements NodeFormatter {

    public GitLabNodeFormatter(DataHolder options) {

    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<?>>(Arrays.asList(
                new NodeFormattingHandler<GitLabBlockQuote>(GitLabBlockQuote.class, new CustomNodeFormatter<GitLabBlockQuote>() {
                    @Override
                    public void render(GitLabBlockQuote node, NodeFormatterContext context, MarkdownWriter markdown) {
                        GitLabNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return new HashSet<Class<?>>(Arrays.asList(
                GitLabBlockQuote.class
        ));
    }

    private void render(GitLabBlockQuote node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(">>>").line();
        context.renderChildren(node);
        markdown.append("<<<").line();
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new GitLabNodeFormatter(options);
        }
    }
}
