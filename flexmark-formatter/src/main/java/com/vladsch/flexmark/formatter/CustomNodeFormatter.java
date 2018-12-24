package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeAdaptingVisitor;

public interface CustomNodeFormatter<N extends Node> extends NodeAdaptingVisitor<N> {
    void render(N node, NodeFormatterContext context, MarkdownWriter markdown);
}
