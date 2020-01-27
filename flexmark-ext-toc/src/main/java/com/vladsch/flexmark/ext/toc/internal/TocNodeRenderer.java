package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.util.HeadingCollectingVisitor;
import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.toc.TocUtils;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TocNodeRenderer implements NodeRenderer {
    final private TocOptions options;
    final private boolean haveTitle;

    public TocNodeRenderer(DataHolder options) {
        this.haveTitle = options != null && options.contains(TocExtension.TITLE);
        this.options = new TocOptions(options, false);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(TocBlock.class, this::render));
        return set;
    }

    private void render(TocBlock node, NodeRendererContext context, HtmlWriter html) {
        HeadingCollectingVisitor visitor = new HeadingCollectingVisitor();
        List<Heading> headings = visitor.collectAndGetHeadings(context.getDocument());
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
        List<Integer> headingLevels = paired.getFirst().stream().map(Heading::getLevel).collect(Collectors.toList());
        List<String> headingRefIds = paired.getFirst().stream().map(Heading::getAnchorRefId).collect(Collectors.toList());
        TocUtils.renderHtmlToc(html, context.getHtmlOptions().sourcePositionAttribute.isEmpty() ? BasedSequence.NULL : node.getChars(), headingLevels, paired.getSecond(), headingRefIds, options);
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new TocNodeRenderer(options);
        }
    }
}
