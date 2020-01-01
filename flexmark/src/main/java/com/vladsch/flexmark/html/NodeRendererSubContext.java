package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public abstract class NodeRendererSubContext implements NodeRendererContext {
    final HtmlWriter htmlWriter;
    Node renderingNode;
    NodeRenderingHandlerWrapper renderingHandlerWrapper;
    int doNotRenderLinksNesting;

    public NodeRendererSubContext(@NotNull HtmlWriter htmlWriter) {
        this.htmlWriter = htmlWriter;
        this.renderingNode = null;
        this.doNotRenderLinksNesting = 0;
    }

    public @NotNull HtmlWriter getHtmlWriter() {
        return htmlWriter;
    }

    public void flushTo(@NotNull Appendable out, int maxTrailingBlankLines) {
        flushTo(out, getHtmlOptions().maxBlankLines, maxTrailingBlankLines);
    }

    public void flushTo(@NotNull Appendable out, int maxBlankLines, int maxTrailingBlankLines) {
        htmlWriter.line();
        try {
            htmlWriter.appendTo(out, maxBlankLines, maxTrailingBlankLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected int getDoNotRenderLinksNesting() {
        return doNotRenderLinksNesting;
    }

    public boolean isDoNotRenderLinks() {
        return doNotRenderLinksNesting != 0;
    }

    public void doNotRenderLinks(boolean doNotRenderLinks) {
        if (doNotRenderLinks) doNotRenderLinks();
        else doRenderLinks();
    }

    public void doNotRenderLinks() {
        this.doNotRenderLinksNesting++;
    }

    public void doRenderLinks() {
        if (this.doNotRenderLinksNesting == 0) throw new IllegalStateException("Not in do not render links context");
        this.doNotRenderLinksNesting--;
    }
}
