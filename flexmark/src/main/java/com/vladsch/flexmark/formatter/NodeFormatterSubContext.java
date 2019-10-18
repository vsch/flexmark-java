package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Node;

import java.io.IOException;

public abstract class NodeFormatterSubContext implements NodeFormatterContext {
    final protected MarkdownWriter markdown;
    Node renderingNode;

    public NodeFormatterSubContext(MarkdownWriter markdown) {
        this.markdown = markdown;
        this.renderingNode = null;
    }

    public Node getRenderingNode() {
        return renderingNode;
    }

    public void setRenderingNode(Node renderingNode) {
        this.renderingNode = renderingNode;
    }

    public MarkdownWriter getMarkdown() {
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
