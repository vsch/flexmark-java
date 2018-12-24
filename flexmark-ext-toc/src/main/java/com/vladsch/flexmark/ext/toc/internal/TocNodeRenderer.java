package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.ast.util.HeadingCollectingVisitor;
import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.Paired;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TocNodeRenderer implements NodeRenderer {
    private final TocOptions options;
    private final boolean haveTitle;

    public TocNodeRenderer(DataHolder options) {
        this.haveTitle = options != null && options.contains(TocExtension.TITLE);
        this.options = new TocOptions(options, false);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        set.add(
                new NodeRenderingHandler<TocBlock>(TocBlock.class, new CustomNodeRenderer<TocBlock>() {
                    @Override
                    public void render(TocBlock node, NodeRendererContext context, HtmlWriter html) {
                        TocNodeRenderer.this.render(node, context, html);
                    }
                })
        );
        return set;
    }

    private void render(TocBlock node, NodeRendererContext context, HtmlWriter html) {
        HeadingCollectingVisitor visitor = new HeadingCollectingVisitor();
        List<Heading> headings = visitor.collectAndGetHeadings(node.getDocument());
        if (headings != null) {
            TocOptionsParser optionsParser = new TocOptionsParser();
            TocOptions titleOptions = haveTitle ? this.options : this.options.withTitle("");
            TocOptions options = optionsParser.parseOption(node.getStyle(), titleOptions, null).getFirst();
            renderTocHeaders(context, html, node, headings, options);
        }
    }

    private void renderTocHeaders(NodeRendererContext context, HtmlWriter html, Node node, List<Heading> headings, TocOptions options) {
        List<Heading> filteredHeadings = TocUtils.filteredHeadings(headings, options);
        final Paired<List<Heading>, List<String>> paired = TocUtils.htmlHeadingTexts(context, filteredHeadings, options);
        TocUtils.renderHtmlToc(html, context.getHtmlOptions().sourcePositionAttribute.isEmpty() ? BasedSequence.NULL : node.getChars(), paired.getFirst(), paired.getSecond(), options);
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(DataHolder options) {
            return new TocNodeRenderer(options);
        }
    }
}
