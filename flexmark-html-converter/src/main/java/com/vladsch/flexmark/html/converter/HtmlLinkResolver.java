package com.vladsch.flexmark.html.converter;

import com.vladsch.flexmark.html.renderer.ResolvedLink;
import org.jsoup.nodes.Node;

public interface HtmlLinkResolver {
    ResolvedLink resolveLink(Node node, HtmlNodeConverterContext context, ResolvedLink link);
}
