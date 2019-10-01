package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.SimTocBlock;
import com.vladsch.flexmark.ext.toc.SimTocContent;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.format.TableFormatOptions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SimTocNodeFormatter implements NodeFormatter {
    private final TableFormatOptions options;

    private MarkdownTable myTable;

    public SimTocNodeFormatter(DataHolder options) {
        this.options = new TableFormatOptions(options);
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(SimTocBlock.class, SimTocNodeFormatter.this::render),
                new NodeFormattingHandler<>(SimTocContent.class, SimTocNodeFormatter.this::render)
        ));
    }

    private void render(SimTocBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.openPreFormatted(false).append(node.getChars()).closePreFormatted();
    }

    private void render(SimTocContent node, NodeFormatterContext context, MarkdownWriter markdown) {
        return;
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(DataHolder options) {
            return new SimTocNodeFormatter(options);
        }
    }
}
