package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.util.HeadingCollectingVisitor;
import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.SubSequence;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TocNodeRenderer implements NodeRenderer {
    final static public AttributablePart TOC_CONTENT = TocUtils.TOC_CONTENT;
    
    private final TocOptions options;

    public TocNodeRenderer(DataHolder options) {
        this.options = new TocOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Collections.singletonList(
                new NodeRenderingHandler<>(TocBlock.class, this::render)
        ));
    }

    private void render(TocBlock node, NodeRendererContext context, HtmlWriter html) {
        HeadingCollectingVisitor visitor = new HeadingCollectingVisitor();
        List<Heading> headings = visitor.collectAndGetHeadings(node.getDocument());
        if (headings != null) {
            TocOptionsParser optionsParser = new TocOptionsParser();
            TocOptions options = optionsParser.parseOption(node.getStyle(), this.options.withTitle(""), null).getFirst();
            renderTocHeaders(context, html, node, headings, options);
        }
    }

    private void renderTocHeaders(NodeRendererContext context, HtmlWriter html, Node node, List<Heading> headings, TocOptions options) {
        List<Heading> filteredHeadings = TocUtils.filteredHeadings(headings, options);
        List<String> headingTexts = TocUtils.htmlHeaderTexts(context, filteredHeadings, options);
        TocUtils.renderHtmlToc(html, context.getHtmlOptions().sourcePositionAttribute.isEmpty() ? SubSequence.NULL : node.getChars(), filteredHeadings, headingTexts, options);
    }
}
