package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.AbstractVisitor;
import com.vladsch.flexmark.node.Heading;
import com.vladsch.flexmark.node.Node;

import java.util.*;

public class TocNodeRenderer implements NodeRenderer {
    private final NodeRendererContext context;
    private final HtmlWriter html;
    private final TocOptions options;

    public TocNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
        this.options = new TocOptions(context.getOptions());
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<>(Arrays.asList(
                TocBlock.class
        ));
    }

    @Override
    public void render(Node node) {
        if (node instanceof TocBlock) {
            renderTocBlock((TocBlock) node);
        }
    }

    private void renderTocBlock(TocBlock node) {
        if (node.getLevel() < 1 || node.getLevel() > 6) {
            html.raw("<p>").raw(node.getChars().toString()).raw("</p>").line();
        } else {
            HeadingCollectingVisitor visitor = new HeadingCollectingVisitor(node.getLevel());
            visitor.visit(node.getDocument());

            List<Heading> headings = visitor.getHeadings();
            if (headings != null) {
                int tmp = 0;
                // TODO: output table of contents
                //if (options.tocOption1) html.attr("href", context.encodeUrl(options.tocOption2));
                //
                //html.withAttr().tag("a");
                //html.text(node.getLevelChars().unescape());
                //html.tag("/a");
            }
        }
    }

    private static class HeadingCollectingVisitor extends AbstractVisitor {
        final private int level;
        private ArrayList<Heading> headings;

        public HeadingCollectingVisitor(int level) {
            this.level = level;
            this.headings = null;
        }

        public ArrayList<Heading> getHeadings() {
            return headings;
        }

        @Override
        public void visit(Heading node) {
            if (node.getLevel() <= level) {
                if (headings == null) {
                    headings = new ArrayList<>();
                }
                headings.add(node);
            }
            super.visit(node);
        }
    }
}
