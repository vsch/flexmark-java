package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.Node;

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

    public void setRenderingNode(final Node renderingNode) {
        this.renderingNode = renderingNode;
    }

    public MarkdownWriter getMarkdown() {
        return markdown;
    }

    public void flush() {
        markdown.line().flush();
    }

    public void flush(int maxBlankLines) {
        markdown.line().flush(maxBlankLines);
    }
}
