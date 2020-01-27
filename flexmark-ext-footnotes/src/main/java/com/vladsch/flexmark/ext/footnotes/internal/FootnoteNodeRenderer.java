package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class FootnoteNodeRenderer implements PhasedNodeRenderer {

    final private FootnoteRepository footnoteRepository;
    final private FootnoteOptions options;
    final private boolean recheckUndefinedReferences;

    public FootnoteNodeRenderer(DataHolder options) {
        this.options = new FootnoteOptions(options);
        this.footnoteRepository = FootnoteExtension.FOOTNOTES.get(options);
        this.recheckUndefinedReferences = HtmlRenderer.RECHECK_UNDEFINED_REFERENCES.get(options);
        this.footnoteRepository.resolveFootnoteOrdinals();
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(Footnote.class, this::render),
                new NodeRenderingHandler<>(FootnoteBlock.class, this::render)
        ));
    }

    @Override
    public Set<RenderingPhase> getRenderingPhases() {
        Set<RenderingPhase> set = new HashSet<>();
        set.add(RenderingPhase.BODY_TOP);
        set.add(RenderingPhase.BODY_BOTTOM);
        return set;
    }

    @Override
    public void renderDocument(@NotNull NodeRendererContext context, @NotNull HtmlWriter html, @NotNull Document document, @NotNull RenderingPhase phase) {
        if (phase == RenderingPhase.BODY_TOP) {
            if (recheckUndefinedReferences) {
                // need to see if have undefined footnotes that were defined after parsing
                boolean[] hadNewFootnotes = { false };
                NodeVisitor visitor = new NodeVisitor(
                        new VisitHandler<>(Footnote.class, node -> {
                            if (!node.isDefined()) {
                                FootnoteBlock footonoteBlock = node.getFootnoteBlock(footnoteRepository);

                                if (footonoteBlock != null) {
                                    footnoteRepository.addFootnoteReference(footonoteBlock, node);
                                    node.setFootnoteBlock(footonoteBlock);
                                    hadNewFootnotes[0] = true;
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
                html.attr("class", "footnotes").withAttr().tagIndent("div", () -> {
                    html.tagVoidLine("hr");
                    html.tagIndent("ol", () -> {
                        for (FootnoteBlock footnoteBlock : footnoteRepository.getReferencedFootnoteBlocks()) {
                            int footnoteOrdinal = footnoteBlock.getFootnoteOrdinal();
                            html.attr("id", "fn-" + footnoteOrdinal);
                            html.withAttr().tagIndent("li", () -> {
                                context.renderChildren(footnoteBlock);

                                int iMax = footnoteBlock.getFootnoteReferences();
                                for (int i = 0; i < iMax; i++) {
                                    html.attr("href", "#fnref-" + footnoteOrdinal + (i == 0 ? "" : String.format(Locale.US, "-%d", i)));
                                    if (!options.footnoteBackLinkRefClass.isEmpty()) html.attr("class", options.footnoteBackLinkRefClass);
                                    html.line().withAttr().tag("a");
                                    html.raw(options.footnoteBackRefString);
                                    html.tag("/a");
                                }
                            });
                        }
                    });
                });
            }
        }
    }

    private void render(FootnoteBlock node, NodeRendererContext context, HtmlWriter html) {

    }

    private void render(Footnote node, NodeRendererContext context, HtmlWriter html) {
        FootnoteBlock footnoteBlock = node.getFootnoteBlock();
        if (footnoteBlock == null) {
            //just text
            html.raw("[^");
            context.renderChildren(node);
            html.raw("]");
        } else {
            int footnoteOrdinal = footnoteBlock.getFootnoteOrdinal();
            int i = node.getReferenceOrdinal();
            html.attr("id", "fnref-" + footnoteOrdinal + (i == 0 ? "" : String.format(Locale.US, "-%d", i)));
            html.srcPos(node.getChars()).withAttr().tag("sup", false, false, () -> {
                if (!options.footnoteLinkRefClass.isEmpty()) html.attr("class", options.footnoteLinkRefClass);
                html.attr("href", "#fn-" + footnoteOrdinal);
                html.withAttr().tag("a");
                html.raw(options.footnoteRefPrefix + footnoteOrdinal + options.footnoteRefSuffix);
                html.tag("/a");
            });
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new FootnoteNodeRenderer(options);
        }
    }
}
