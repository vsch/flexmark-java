package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.LinkRendering;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;

public interface LinkResolver {
    LinkRendering renderLink(LinkRendering linkRendering, NodeRendererContext context);
}
