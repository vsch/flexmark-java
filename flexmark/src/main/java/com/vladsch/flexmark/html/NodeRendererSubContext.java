package com.vladsch.flexmark.html;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;

public abstract class NodeRendererSubContext implements NodeRendererContext {
    final HtmlWriter htmlWriter;
    Node renderingNode;
    int doNotRenderLinksNesting;

    public NodeRendererSubContext(HtmlWriter htmlWriter) {
        this.htmlWriter = htmlWriter;
        this.renderingNode = null;
        this.doNotRenderLinksNesting = 0;
    }

    public HtmlWriter getHtmlWriter() {
        return htmlWriter;
    }

    public void flush() {
        htmlWriter.flush();
    }

    public void flush(int maxBlankLines) {
        htmlWriter.flush(maxBlankLines);
    }

    protected int getDoNotRenderLinksNesting() {
        return doNotRenderLinksNesting;
    }

    public boolean isDoNotRenderLinks() {
        return doNotRenderLinksNesting != 0;
    }

    public void doNotRenderLinks(boolean doNotRenderLinks) {
        if (doNotRenderLinks) doNotRenderLinks();
        else doNotRenderLinks();
    }

    public void doNotRenderLinks() {
        this.doNotRenderLinksNesting++;
    }

    public void doRenderLinks() {
        if (this.doNotRenderLinksNesting == 0) throw new IllegalStateException("Not in do not render links context");
        this.doNotRenderLinksNesting--;
    }
}
