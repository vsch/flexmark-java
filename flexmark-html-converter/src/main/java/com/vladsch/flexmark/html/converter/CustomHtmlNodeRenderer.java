package com.vladsch.flexmark.html.converter;

import org.jsoup.nodes.Node;

public interface CustomHtmlNodeRenderer<N extends Node> {
    void render(N node, HtmlNodeConverterContext context, HtmlMarkdownWriter markdown);
}
