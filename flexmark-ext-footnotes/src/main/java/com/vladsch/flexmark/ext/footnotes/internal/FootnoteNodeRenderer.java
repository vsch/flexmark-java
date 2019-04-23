package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FootnoteNodeRenderer implements PhasedNodeRenderer {

    private final FootnoteRepository footnoteRepository;
    private final FootnoteOptions options;
    private boolean recheckUndefinedReferences;

    public FootnoteNodeRenderer(DataHolder options) {
        this.options = new FootnoteOptions(options);
        this.footnoteRepository = options.get(FootnoteExtension.FOOTNOTES);
        this.recheckUndefinedReferences = HtmlRenderer.RECHECK_UNDEFINED_REFERENCES.getFrom(options);
        this.footnoteRepository.resolveFootnoteOrdinals();
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<NodeRenderingHandler<? extends Node>>(Arrays.asList(
                new NodeRenderingHandler<Footnote>(Footnote.class, new CustomNodeRenderer<Footnote>() {
                    @Override
                    public void render(Footnote node, NodeRendererContext context, HtmlWriter html) {
                        FootnoteNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<FootnoteBlock>(FootnoteBlock.class, new CustomNodeRenderer<FootnoteBlock>() {
                    @Override
                    public void render(FootnoteBlock node, NodeRendererContext context, HtmlWriter html) {
                        FootnoteNodeRenderer.this.render(node, context, html);
                    }
                })
        ));
    }

    @Override
    public Set<RenderingPhase> getRenderingPhases() {
        Set<RenderingPhase> set = new HashSet<RenderingPhase>();
        set.add(RenderingPhase.BODY_TOP);
        set.add(RenderingPhase.BODY_BOTTOM);
        return set;
    }

    @Override
    public void renderDocument(final NodeRendererContext context, final HtmlWriter html, Document document, RenderingPhase phase) {
        if (phase == RenderingPhase.BODY_TOP) {
            if (recheckUndefinedReferences) {
                // need to see if have undefined footnotes that were defined after parsing
                final boolean[] hadNewFootnotes = { false };
                NodeVisitor visitor = new NodeVisitor(
                        new VisitHandler<Footnote>(Footnote.class, new Visitor<Footnote>() {
                            @Override
                            public void visit(Footnote node) {
                                if (!node.isDefined()) {
                                    FootnoteBlock footonoteBlock = node.getFootnoteBlock(footnoteRepository);

                                    if (footonoteBlock != null) {
                                        footnoteRepository.addFootnoteReference(footonoteBlock, node);
                                        node.setFootnoteBlock(footonoteBlock);
                                        hadNewFootnotes[0] = true;
                                    }
                                }
                            }
                        })
                );

                visitor.visit(document);
                if (hadNewFootnotes[0]) {
                    this.footnoteRepository.resolveFootnoteOrdinals();
                }
            }
        }

        if (phase == RenderingPhase.BODY_BOTTOM) {
            // here we dump the footnote blocks that were referenced in the document body, ie. ones with footnoteOrdinal > 0
            if (footnoteRepository.getReferencedFootnoteBlocks().size() > 0) {
                html.attr("class", "footnotes").withAttr().tagIndent("div", new Runnable() {
                    @Override
                    public void run() {
                        html.tagVoidLine("hr");
                        html.tagIndent("ol", new Runnable() {
                            @Override
                            public void run() {
                                for (final FootnoteBlock footnoteBlock : footnoteRepository.getReferencedFootnoteBlocks()) {
                                    final int footnoteOrdinal = footnoteBlock.getFootnoteOrdinal();
                                    html.attr("id", "fn-" + footnoteOrdinal);
                                    html.withAttr().tagIndent("li", new Runnable() {
                                        @Override
                                        public void run() {
                                            context.renderChildren(footnoteBlock);

                                            int iMax = footnoteBlock.getFootnoteReferences();
                                            for (int i = 0; i < iMax; i++) {
                                                html.attr("href", "#fnref-" + footnoteOrdinal + (i == 0 ? "" : String.format("-%d", i)));
                                                if (!options.footnoteBackLinkRefClass.isEmpty()) html.attr("class", options.footnoteBackLinkRefClass);
                                                html.line().withAttr().tag("a");
                                                html.raw(options.footnoteBackRefString);
                                                html.tag("/a");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    private void render(FootnoteBlock node, NodeRendererContext context, HtmlWriter html) {

    }

    private void render(Footnote node, NodeRendererContext context, final HtmlWriter html) {
        FootnoteBlock footnoteBlock = node.getFootnoteBlock();
        if (footnoteBlock == null) {
            //just text
            html.raw("[^");
            context.renderChildren(node);
            html.raw("]");
        } else {
            final int footnoteOrdinal = footnoteBlock.getFootnoteOrdinal();
            int i = node.getReferenceOrdinal();
            html.attr("id", "fnref-" + footnoteOrdinal + (i == 0 ? "" : String.format("-%d", i)));
            html.srcPos(node.getChars()).withAttr().tag("sup", false, false, new Runnable() {
                @Override
                public void run() {
                    if (!options.footnoteLinkRefClass.isEmpty()) html.attr("class", options.footnoteLinkRefClass);
                    html.attr("href", "#fn-" + footnoteOrdinal);
                    html.withAttr().tag("a");
                    html.raw(options.footnoteRefPrefix + String.valueOf(footnoteOrdinal) + options.footnoteRefSuffix);
                    html.tag("/a");
                }
            });
        }
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer apply(final DataHolder options) {
            return new FootnoteNodeRenderer(options);
        }
    }
}
