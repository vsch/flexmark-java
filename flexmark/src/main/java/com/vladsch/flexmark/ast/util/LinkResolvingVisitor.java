package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeAdaptingVisitor;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;

public interface LinkResolvingVisitor<N extends Node> extends NodeAdaptingVisitor<N> {
    ResolvedLink resolveLink(N node, NodeRendererContext context, ResolvedLink link);
}
