package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.util.HeadingCollectingVisitor;
import com.vladsch.flexmark.ext.toc.SimTocBlock;
import com.vladsch.flexmark.ext.toc.SimTocContent;
import com.vladsch.flexmark.ext.toc.SimTocOption;
import com.vladsch.flexmark.ext.toc.SimTocOptionList;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.Paired;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimTocNodeRenderer implements NodeRenderer {
    private final TocOptions options;

    public SimTocNodeRenderer(DataHolder options) {
        this.options = new TocOptions(options, true);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<NodeRenderingHandler<? extends Node>>(Arrays.asList(
                new NodeRenderingHandler<SimTocBlock>(SimTocBlock.class, this::render),
                new NodeRenderingHandler<SimTocContent>(SimTocContent.class, this::render),
                new NodeRenderingHandler<SimTocOptionList>(SimTocOptionList.class, this::render),
                new NodeRenderingHandler<SimTocOption>(SimTocOption.class, this::render)
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
        List<Heading> headings = visitor.collectAndGetHeadings(node.getDocument());
        if (headings != null) {
            SimTocOptionsParser optionsParser = new SimTocOptionsParser();
            TocOptions options = optionsParser.parseOption(node.getStyle(), this.options, null).getFirst();

            if (node.getTitle().isNotNull()) {
                options = options.withTitle(node.getTitle().unescape());
            }
            renderTocHeaders(context, html, node, headings, options);
        }
    }

    private void renderTocHeaders(NodeRendererContext context, HtmlWriter html, Node node, List<Heading> headings, TocOptions options) {
        List<Heading> filteredHeadings = TocUtils.filteredHeadings(headings, options);
        Paired<List<Heading>, List<String>> paired = TocUtils.htmlHeadingTexts(context, filteredHeadings, options);
        TocUtils.renderHtmlToc(html, context.getHtmlOptions().sourcePositionAttribute.isEmpty() ? BasedSequence.NULL : node.getChars(), paired.getFirst(), paired.getSecond(), options);
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer apply(DataHolder options) {
            return new SimTocNodeRenderer(options);
        }
    }
}
