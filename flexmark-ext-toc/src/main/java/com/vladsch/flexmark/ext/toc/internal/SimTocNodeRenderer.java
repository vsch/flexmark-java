package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.util.HeadingCollectingVisitor;
import com.vladsch.flexmark.ext.toc.SimTocBlock;
import com.vladsch.flexmark.ext.toc.SimTocContent;
import com.vladsch.flexmark.ext.toc.SimTocOption;
import com.vladsch.flexmark.ext.toc.SimTocOptionList;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.Paired;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimTocNodeRenderer implements NodeRenderer {
    public static final AttributablePart TOC_CONTENT = TocUtils.TOC_CONTENT;

    private final TocOptions options;

    public SimTocNodeRenderer(DataHolder options) {
        this.options = new TocOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(SimTocBlock.class, new CustomNodeRenderer<SimTocBlock>() {
                    @Override
                    public void render(SimTocBlock node, NodeRendererContext context, HtmlWriter html) {
                        SimTocNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(SimTocContent.class, new CustomNodeRenderer<SimTocContent>() {
                    @Override
                    public void render(SimTocContent node, NodeRendererContext context, HtmlWriter html) {
                        SimTocNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(SimTocOptionList.class, new CustomNodeRenderer<SimTocOptionList>() {
                    @Override
                    public void render(SimTocOptionList node, NodeRendererContext context, HtmlWriter html) {
                        SimTocNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(SimTocOption.class, new CustomNodeRenderer<SimTocOption>() {
                    @Override
                    public void render(SimTocOption node, NodeRendererContext context, HtmlWriter html) {
                        SimTocNodeRenderer.this.render(node, context, html);
                    }
                })
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
        final Paired<List<Heading>, List<String>> paired = TocUtils.htmlHeadingTexts(context, filteredHeadings, options);
        TocUtils.renderHtmlToc(html, context.getHtmlOptions().sourcePositionAttribute.isEmpty() ? BasedSequence.NULL : node.getChars(), paired.getFirst(), paired.getSecond(), options);
    }
}
