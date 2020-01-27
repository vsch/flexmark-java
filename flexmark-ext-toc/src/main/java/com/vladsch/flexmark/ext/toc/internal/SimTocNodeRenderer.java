package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.util.HeadingCollectingVisitor;
import com.vladsch.flexmark.ext.toc.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.Paired;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SimTocNodeRenderer implements NodeRenderer {
    final private TocOptions options;

    public SimTocNodeRenderer(DataHolder options) {
        this.options = new TocOptions(options, true);
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
        List<Heading> headings = visitor.collectAndGetHeadings(context.getDocument());
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
        List<Integer> headingLevels = paired.getFirst().stream().map(Heading::getLevel).collect(Collectors.toList());
        List<String> headingRefIds = paired.getFirst().stream().map(Heading::getAnchorRefId).collect(Collectors.toList());
        TocUtils.renderHtmlToc(html, context.getHtmlOptions().sourcePositionAttribute.isEmpty() ? BasedSequence.NULL : node.getChars(), headingLevels, paired.getSecond(), headingRefIds, options);
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new SimTocNodeRenderer(options);
        }
    }
}
