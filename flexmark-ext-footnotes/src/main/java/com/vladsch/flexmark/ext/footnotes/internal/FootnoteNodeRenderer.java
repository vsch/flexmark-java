package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.PhasedNodeRenderer;
import com.vladsch.flexmark.html.renderer.RenderingPhase;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FootnoteNodeRenderer implements PhasedNodeRenderer {

    final private NodeRendererContext context;
    final private HtmlWriter html;
    final private FootnoteRepository footnoteRepository;
    final private String footnoteRefPrefix;
    final private String footnoteRefSuffix;
    final private String footnoteBackRefString;
    final private String footnoteLinkRefClass;
    final private String footnoteBackLinkRefClass;

    public FootnoteNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
        DataHolder options = context.getOptions();
        this.footnoteRepository = options.get(FootnoteExtension.FOOTNOTES);
        this.footnoteRepository.resolveFootnoteOrdinals();
        this.footnoteRefPrefix = options.get(FootnoteExtension.FOOTNOTE_REF_PREFIX);
        this.footnoteRefSuffix = options.get(FootnoteExtension.FOOTNOTE_REF_SUFFIX);
        this.footnoteBackRefString = options.get(FootnoteExtension.FOOTNOTE_BACK_REF_STRING);
        this.footnoteLinkRefClass = options.get(FootnoteExtension.FOOTNOTE_LINK_REF_CLASS);
        this.footnoteBackLinkRefClass = options.get(FootnoteExtension.FOOTNOTE_BACK_LINK_REF_CLASS);
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
                                if (!footnoteBackLinkRefClass.isEmpty()) html.attr("class", footnoteBackLinkRefClass);
                                html.withAttr().tag("a");
                                html.raw(footnoteBackRefString);
                                html.tag("/a");
                            });
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
            html.attr("id", "fnref-" + footnoteOrdinal);
            html.withAttr().tag("sup", () -> {
                if (!footnoteLinkRefClass.isEmpty()) html.attr("class", footnoteLinkRefClass);
                html.attr("href", "#fn-" + footnoteOrdinal);
                html.withAttr().tag("a");
                html.raw(footnoteRefPrefix + String.valueOf(footnoteOrdinal) + footnoteRefSuffix);
                html.tag("/a");
            });
        }
    }
}
