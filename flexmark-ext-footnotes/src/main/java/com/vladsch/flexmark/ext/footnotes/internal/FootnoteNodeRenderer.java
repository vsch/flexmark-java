package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.PhasedNodeRenderer;
import com.vladsch.flexmark.html.renderer.RenderingPhase;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FootnoteNodeRenderer implements PhasedNodeRenderer {

    final private FootnoteRepository footnoteRepository;
    private final FootnoteOptions options;

    public FootnoteNodeRenderer(DataHolder options) {
        this.options = new FootnoteOptions(options);
        this.footnoteRepository = options.get(FootnoteExtension.FOOTNOTES);
        this.footnoteRepository.resolveFootnoteOrdinals();
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
    public void render(NodeRendererContext context, HtmlWriter html, Node node) {
        if (node instanceof Footnote) {
            renderFootnote(context, html, (Footnote) node);
        } else if (node instanceof FootnoteBlock) {
            renderFootnoteBlock(context, html, (FootnoteBlock) node);
        }
    }

    private void renderFootnoteBlock(NodeRendererContext context, HtmlWriter html, FootnoteBlock node) {

    }

    @Override
    public void renderDocument(NodeRendererContext context, HtmlWriter html, Document document, RenderingPhase phase) {
        if (phase == RenderingPhase.BODY_BOTTOM) {
            // here we dump the footnote blocks that were referenced in the document body, ie. ones with footnoteOrdinal > 0
            if (footnoteRepository.getReferencedFootnoteBlocks().size() > 0) {
                html.attr("class", "footnotes").withAttr().tagIndent("div", () -> {
                    html.tagVoidLine("hr");
                    html.tagIndent("ol", () -> {
                        for (FootnoteBlock footnoteBlock : footnoteRepository.getReferencedFootnoteBlocks()) {
                            int footnoteOrdinal = footnoteBlock.getFootnoteOrdinal();
                            html.attr("id", "fn-" + footnoteOrdinal);
                            html.withAttr().tagIndent("li", () -> {
                                context.renderChildren(footnoteBlock);
                                html.attr("href", "#fnref-" + footnoteOrdinal);
                                if (!options.footnoteBackLinkRefClass.isEmpty()) html.attr("class", options.footnoteBackLinkRefClass);
                                html.withAttr().tag("a");
                                html.raw(options.footnoteBackRefString);
                                html.tag("/a");
                            });
                        }
                    });
                });
            }
        }
    }

    private void renderFootnote(NodeRendererContext context, HtmlWriter html, Footnote node) {
        FootnoteBlock footnoteBlock = node.getFootnoteBlock();
        if (footnoteBlock == null) {
            //just text
            html.raw("[^");
            context.renderChildren(node);
            html.raw("]");
        } else {
            int footnoteOrdinal = footnoteBlock.getFootnoteOrdinal();
            html.attr("id", "fnref-" + footnoteOrdinal);
            html.withAttr().tag("sup", () -> {
                if (!options.footnoteLinkRefClass.isEmpty()) html.attr("class", options.footnoteLinkRefClass);
                html.attr("href", "#fn-" + footnoteOrdinal);
                html.withAttr().tag("a");
                html.raw(options.footnoteRefPrefix + String.valueOf(footnoteOrdinal) + options.footnoteRefSuffix);
                html.tag("/a");
            });
        }
    }
}
