package com.vladsch.flexmark.internal.util.ast;

import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.node.Node;

public interface LinkResolvingVisitor<N extends Node> extends NodeAdaptingVisitor<N> {
    ResolvedLink resolveLink(N node, NodeRendererContext context, ResolvedLink link);
}
