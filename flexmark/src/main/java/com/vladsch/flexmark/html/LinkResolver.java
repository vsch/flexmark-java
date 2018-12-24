package com.vladsch.flexmark.html;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;

public interface LinkResolver {
    ResolvedLink resolveLink(Node node, LinkResolverContext context, ResolvedLink link);
}
