package com.vladsch.flexmark.html;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;

public interface LinkResolver {
    ResolvedLink resolveLink(Node node, NodeRendererContext context, ResolvedLink link);
}
