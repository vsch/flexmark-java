package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.TextCollectingAppendable;
import com.vladsch.flexmark.internal.util.AbstractBlockVisitor;
import com.vladsch.flexmark.internal.util.TextCollectingVisitor;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Heading;
import com.vladsch.flexmark.node.Node;

import java.util.*;

public class TocNodeRenderer implements NodeRenderer {
    private final TocOptions options;

    public TocNodeRenderer(DataHolder options) {
        this.options = new TocOptions(options);
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<>(Arrays.asList(
                TocBlock.class
        ));
    }

    @Override
    public void render(NodeRendererContext context, HtmlWriter html, Node node) {
        if (node instanceof TocBlock) {
            renderTocBlock(context, html, (TocBlock) node);
        }
    }

    private void renderTocBlock(NodeRendererContext context, HtmlWriter html, TocBlock node) {
        if (node.getLevel() < 1 || node.getLevel() > 6) {
            html.raw("<p>").raw(node.getChars().toString()).raw("</p>").line();
        } else {
            HeadingCollectingVisitor visitor = new HeadingCollectingVisitor(node.getLevel());
            visitor.visit(node.getDocument());

            List<Heading> headings = visitor.getHeadings();
            if (headings != null) {
                renderTocHeaders(context, html, node.getLevel(), headings);
            }
        }
    }

    private void renderTocHeaders(NodeRendererContext context, HtmlWriter html, int level, List<Heading> headings) {
        int initLevel = headings.get(0).getLevel();
        int lastLevel = headings.get(0).getLevel();

        html.withAttr().line().tag("ul").indent();

        for (int i = 0; i < headings.size(); ++i) {
            Heading header = headings.get(i);

            // ignore the level less than toc limit
            if (header.getLevel() > level) {
                continue;
            }

            if (lastLevel < header.getLevel()) {
                for (int lv = lastLevel; lv < header.getLevel(); ++lv) {
                    html.withAttr().indent().tag("ul");
                }
            } else if (lastLevel == header.getLevel()) {
                if (i != 0) {
                    html.line().tag("/li");
                }
            } else {
                html.tag("/li");
                for (int lv = header.getLevel(); lv < lastLevel; ++lv) {
                    html.unIndent().tag("/ul")
                            .line().tag("/li");
                }
            }

            String headerText;
            boolean isRaw;
            // need to skip anchor links but render emphasis
            if (options.renderOnlyHeaderText) {
                headerText = new TextCollectingVisitor().visitAndGetText(header);
                isRaw = false;
            } else {
                TextCollectingAppendable out = new TextCollectingAppendable();
                NodeRendererContext subContext = context.getSubContext(out, false);
                subContext.doNotRenderLinks();
                subContext.renderChildren(header);
                headerText = out.getHtml();
                isRaw = true;
            }
            
            html.line().tag("li");
            String headerId = context.getNodeId(header);
            if (headerId == null || context.isDoNotRenderLinks()) {
                // just text
                if (isRaw) html.raw(headerText);
                else html.text(headerText);
            } else {
                html.attr("href", "#" + headerId).withAttr().tag("a");
                if (isRaw) html.raw(headerText);
                else html.text(headerText);
                html.tag("/a");
            }
            lastLevel = header.getLevel();
        }

        for (int i = initLevel - 1; i < lastLevel; ++i) {
            html.tag("/li").unIndent().tag("/ul");
        }
        html.line();
    }

    private static class HeadingCollectingVisitor extends AbstractBlockVisitor {
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
        }
    }
}
