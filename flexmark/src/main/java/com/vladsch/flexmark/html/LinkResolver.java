package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.node.Node;

public interface LinkResolver {
    ResolvedLink resolveLink(Node node, NodeRendererContext context, ResolvedLink link);
}
