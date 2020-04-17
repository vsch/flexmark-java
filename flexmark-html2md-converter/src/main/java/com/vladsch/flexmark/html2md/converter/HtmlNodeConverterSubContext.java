package com.vladsch.flexmark.html2md.converter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Node;

import java.io.IOException;

public abstract class HtmlNodeConverterSubContext implements HtmlNodeConverterContext {
    final protected HtmlMarkdownWriter markdown;
    NodeRenderingHandlerWrapper<?> renderingHandlerWrapper;
    @Nullable Node myRenderingNode;

    public HtmlNodeConverterSubContext(@NotNull HtmlMarkdownWriter markdown) {
        this.markdown = markdown;
        this.myRenderingNode = null;
        this.markdown.setContext(this);
    }

    public @Nullable Node getRenderingNode() {
        return myRenderingNode;
    }

    public void setRenderingNode(@Nullable Node renderingNode) {
        this.myRenderingNode = renderingNode;
    }

    public @NotNull HtmlMarkdownWriter getMarkdown() {
        return markdown;
    }

    public void flushTo(@NotNull Appendable out, int maxTrailingBlankLines) {
        flushTo(out, getHtmlConverterOptions().maxBlankLines, maxTrailingBlankLines);
    }

    public void flushTo(@NotNull Appendable out, int maxBlankLines, int maxTrailingBlankLines) {
        markdown.line();
        try {
            markdown.appendTo(out, maxBlankLines, maxTrailingBlankLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
