package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.SimTocBlock;
import com.vladsch.flexmark.ext.toc.SimTocContent;
import com.vladsch.flexmark.ext.toc.SimTocOption;
import com.vladsch.flexmark.ext.toc.SimTocOptionList;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.internal.util.HeadingCollectingVisitor;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.node.Heading;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimTocNodeRenderer implements NodeRenderer {
    private final TocOptions options;

    public SimTocNodeRenderer(DataHolder options) {
        this.options = new TocOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(SimTocBlock.class, this::render),
                new NodeRenderingHandler<>(SimTocContent.class, this::render),
                new NodeRenderingHandler<>(SimTocOptionList.class, this::render),
                new NodeRenderingHandler<>(SimTocOption.class, this::render)
        ));
    }

    private void render(SimTocContent node, NodeRendererContext context, HtmlWriter html) {
        // we don't render this or its children
    }

    private void render(SimTocOptionList node, NodeRendererContext context, HtmlWriter html) {
        // we don't render this or its children
    }

    private void render(SimTocOption node, NodeRendererContext context, HtmlWriter html) {
        // we don't render this or its children
    }

    private void render(SimTocBlock node, NodeRendererContext context, HtmlWriter html) {
        HeadingCollectingVisitor visitor = new HeadingCollectingVisitor();
        visitor.visit(node.getDocument());

        List<Heading> headings = visitor.getHeadings();
        if (headings != null) {
            SimTocOptionsParser optionsParser = new SimTocOptionsParser();
            TocOptions options = optionsParser.parseOption(node.getStyle(), this.options, null).getFirst();
            
            if (node.getTitle().isNotNull()) {
                options = options.withTitle(node.getTitle().toString());
            }
            renderTocHeaders(context, html, headings, options);
        }
    }

    private void renderTocHeaders(NodeRendererContext context, HtmlWriter html, List<Heading> headings, TocOptions options) {
        List<Heading> filteredHeadings = TocUtils.filteredHeadings(headings, options);
        List<String> headingTexts = TocUtils.htmlHeaderTexts(context, filteredHeadings, options);
        TocUtils.renderHtmlToc(html, filteredHeadings, headingTexts, options);
    }
}
