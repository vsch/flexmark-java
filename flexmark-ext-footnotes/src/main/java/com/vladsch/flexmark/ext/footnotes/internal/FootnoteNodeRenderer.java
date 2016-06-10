package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.PhasedNodeRenderer;
import com.vladsch.flexmark.html.renderer.RenderingPhase;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FootnoteNodeRenderer implements PhasedNodeRenderer {
    private final NodeRendererContext context;
    private final HtmlWriter html;

    public FootnoteNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<>(Arrays.asList(
                Footnote.class,
                FootnoteBlock.class
        ));
    }

    @Override
    public Set<RenderingPhase> getRenderingPhases() {
        return new HashSet<>(Collections.singletonList(RenderingPhase.BODY_BOTTOM));
    }

    @Override
    public void render(Node node) {
        if (node instanceof Footnote) {
            renderFootnote((Footnote) node);
        } else if (node instanceof FootnoteBlock) {
            renderFootnoteBlock((FootnoteBlock) node);
        }
    }

    private void renderFootnoteBlock(FootnoteBlock node) {

    }

    @Override
    public void render(Node node, RenderingPhase phase) {
        if (phase == RenderingPhase.BODY_BOTTOM) {
            // here we dump the footnote blocks that were referenced in the document body, ie. ones with footnoteOrdinal > 0
            Document document = node.getDocument();
            FootnoteRepository footnoteRepository = document.getValueOrDefault(FootnoteRepository.PROPERTY_KEY);

            if (footnoteRepository.getReferencedFootnoteBlocks().size() > 0) {
                html.attr("class", "footnotes").tag("div", () -> {
                    html.indent()
                        .tag("hr", null, true).line()
                        .tag("ol", () -> {
                            html.indent();
                            for (FootnoteBlock footnoteBlock : footnoteRepository.getReferencedFootnoteBlocks()) {
                                int footnoteOrdinal = footnoteBlock.getFootnoteOrdinal();
                                if (footnoteOrdinal > 0) {
                                    // output this one

                                    html.attr("id", "fn-" + footnoteOrdinal)
                                        .tag("li", () -> {
                                            html.indent();
                                            context.renderChildren(footnoteBlock);
                                            html.attr("href", "#fnref-" + footnoteOrdinal)
                                                .attr("class", "footnote-backref")
                                                .tag("a", () -> {
                                                    html.raw("&#8617;");
                                                });
                                        });
                                }
                            }
                        });
                });
            }
        }
    }

    private void renderFootnote(Footnote node) {
        FootnoteBlock footnoteBlock = node.getFootnoteBlock();
        if (footnoteBlock == null) {
            //just text
            html.raw("[^");
            context.renderChildren(node);
            html.raw("]");
        } else {
            int footnoteOrdinal = footnoteBlock.getFootnoteOrdinal();
            html.attr("id", "fnref-" + footnoteOrdinal)
                .tag("sup", () -> {
                    html.attr("class", "footnote-ref")
                        .attr("href", "#fn-" + footnoteOrdinal)
                        .tag("a", () -> {
                            html.raw(String.valueOf(footnoteOrdinal));
                        });
                });
        }
    }
}
