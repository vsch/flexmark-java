package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeAdaptingVisitor;
import com.vladsch.flexmark.formatter.internal.MarkdownWriter;
import com.vladsch.flexmark.formatter.internal.NodeFormatterContext;

public interface CustomNodeFormatter<N extends Node> extends NodeAdaptingVisitor<N> {
    void render(N node, NodeFormatterContext context, MarkdownWriter markdown);
}
