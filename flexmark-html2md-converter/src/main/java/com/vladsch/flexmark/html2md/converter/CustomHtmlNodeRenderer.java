package com.vladsch.flexmark.html2md.converter;

import org.jsoup.nodes.Node;

public interface CustomHtmlNodeRenderer<N extends Node> {
    void render(N node, HtmlNodeConverterContext context, HtmlMarkdownWriter markdown);
}
