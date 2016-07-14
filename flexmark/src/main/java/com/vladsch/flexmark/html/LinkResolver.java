package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;

public interface LinkResolver {
    ResolvedLink resolveLink(NodeRendererContext context, ResolvedLink link);
}
