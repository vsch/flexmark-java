package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public abstract class NodeFormatterSubContext implements NodeFormatterContext {
    final protected MarkdownWriter markdown;
    Node renderingNode;
    List<NodeFormattingHandler<?>> rendererList = null;
    int rendererIndex = -1;

    public NodeFormatterSubContext(@NotNull MarkdownWriter markdown) {
        this.markdown = markdown;
        this.renderingNode = null;
    }

    public @NotNull Node getRenderingNode() {
        return renderingNode;
    }

    public void setRenderingNode(@NotNull Node renderingNode) {
        this.renderingNode = renderingNode;
    }

    @Override
    public @NotNull MarkdownWriter getMarkdown() {
        return markdown;
    }

    public void flushTo(@NotNull Appendable out, int maxBlankLines) {
        markdown.line();
        try {
            markdown.appendTo(out, maxBlankLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
