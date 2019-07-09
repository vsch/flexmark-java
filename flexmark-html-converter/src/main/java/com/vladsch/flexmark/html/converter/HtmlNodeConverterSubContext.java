package com.vladsch.flexmark.html.converter;

import org.jsoup.nodes.Node;

import java.io.IOException;

public abstract class HtmlNodeConverterSubContext implements HtmlNodeConverterContext {
    final protected HtmlMarkdownWriter markdown;
    NodeRenderingHandlerWrapper renderingHandlerWrapper;
    Node myRenderingNode;

    public HtmlNodeConverterSubContext(HtmlMarkdownWriter markdown) {
        this.markdown = markdown;
        this.myRenderingNode = null;
        this.markdown.setContext(this);
    }

    public Node getRenderingNode() {
        return myRenderingNode;
    }

    public void setRenderingNode(Node renderingNode) {
        this.myRenderingNode = renderingNode;
    }

    public HtmlMarkdownWriter getMarkdown() {
        return markdown;
    }

    public void flushTo(Appendable out, int maxBlankLines) {
        markdown.line();
        try {
            markdown.appendTo(out, maxBlankLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
