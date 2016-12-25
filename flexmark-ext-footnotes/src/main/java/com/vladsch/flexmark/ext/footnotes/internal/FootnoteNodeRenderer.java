package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.html.renderer.PhasedNodeRenderer;
import com.vladsch.flexmark.html.renderer.RenderingPhase;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FootnoteNodeRenderer implements PhasedNodeRenderer {

    private final FootnoteRepository footnoteRepository;
    private final FootnoteOptions options;

    public FootnoteNodeRenderer(DataHolder options) {
        this.options = new FootnoteOptions(options);
        this.footnoteRepository = options.get(FootnoteExtension.FOOTNOTES);
        this.footnoteRepository.resolveFootnoteOrdinals();
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(Footnote.class, new CustomNodeRenderer<Footnote>() {
                    @Override
                    public void render(Footnote node, NodeRendererContext context, HtmlWriter html) {
                        FootnoteNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(FootnoteBlock.class, new CustomNodeRenderer<FootnoteBlock>() {
                    @Override
                    public void render(FootnoteBlock node, NodeRendererContext context, HtmlWriter html) {
                        FootnoteNodeRenderer.this.render(node, context, html);
                    }
                })
        ));
    }

    @Override
    public Set<RenderingPhase> getRenderingPhases() {
        return new HashSet<>(Collections.singletonList(RenderingPhase.BODY_BOTTOM));
    }

    @Override
    public void renderDocument(final NodeRendererContext context, final HtmlWriter html, Document document, RenderingPhase phase) {
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
                                            html.attr("href", "#fnref-" + footnoteOrdinal);
                                            if (!options.footnoteBackLinkRefClass.isEmpty()) html.attr("class", options.footnoteBackLinkRefClass);
                                            html.withAttr().tag("a");
                                            html.raw(options.footnoteBackRefString);
                                            html.tag("/a");
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
            html.attr("id", "fnref-" + footnoteOrdinal);
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
}
