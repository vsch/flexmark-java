package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.Node;

public interface CustomNodeVisitor<N extends Node> {
    void visit(N node);
}
