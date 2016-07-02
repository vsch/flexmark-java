package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderHandler;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.TextCollectingAppendable;
import com.vladsch.flexmark.internal.util.HeadingCollectingVisitor;
import com.vladsch.flexmark.internal.util.TextCollectingVisitor;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Heading;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TocNodeRenderer implements NodeRenderer {
    private final TocOptions options;

    public TocNodeRenderer(DataHolder options) {
        this.options = new TocOptions(options);
    }

    @Override
    public Set<NodeRenderHandler<?>> getNodeRenderers() {
        return new HashSet<>(Collections.singletonList(
                new NodeRenderHandler<>(TocBlock.class, this::render)
        ));
    }

    private void render(TocBlock node, NodeRendererContext context, HtmlWriter html) {
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
}
