package com.vladsch.flexmark.ext.admonition.internal;

import com.vladsch.flexmark.ext.admonition.AdmonitionBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.html.Attribute;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.vladsch.flexmark.html.renderer.RenderingPhase.BODY_TOP;

public class AdmonitionNodeRenderer implements PhasedNodeRenderer {
    public static AttributablePart ADMONITION_SVG_OBJECT_PART = new AttributablePart("ADMONITION_SVG_OBJECT_PART");
    public static AttributablePart ADMONITION_HEADING_PART = new AttributablePart("ADMONITION_HEADING_PART");
    public static AttributablePart ADMONITION_ICON_PART = new AttributablePart("ADMONITION_ICON_PART");
    public static AttributablePart ADMONITION_TITLE_PART = new AttributablePart("ADMONITION_TITLE_PART");
    public static AttributablePart ADMONITION_BODY_PART = new AttributablePart("ADMONITION_BODY_PART");

    final private AdmonitionOptions options;

    public AdmonitionNodeRenderer(DataHolder options) {
        this.options = new AdmonitionOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(AdmonitionBlock.class, this::render));
        return set;
    }

    @Override
    public Set<RenderingPhase> getRenderingPhases() {
        LinkedHashSet<RenderingPhase> phaseSet = new LinkedHashSet<>();
        phaseSet.add(BODY_TOP);
        return phaseSet;
    }

    @Override
    public void renderDocument(@NotNull NodeRendererContext context, @NotNull HtmlWriter html, @NotNull Document document, @NotNull RenderingPhase phase) {
        if (phase == BODY_TOP) {
            // dump out the SVG used by the rest of the nodes

            HashSet<String> resolvedQualifiers = new HashSet<>();

            Set<String> referencedQualifiers = new AdmonitionCollectingVisitor().collectAndGetQualifiers(document);
            for (String qualifier : referencedQualifiers) {
                String resolvedQualifier = this.options.qualifierTypeMap.get(qualifier);
                if (resolvedQualifier == null) resolvedQualifier = options.unresolvedQualifier;
                resolvedQualifiers.add(resolvedQualifier);
            }

            if (!resolvedQualifiers.isEmpty()) {
                html.line().attr("xmlns", "http://www.w3.org/2000/svg").attr(Attribute.CLASS_ATTR, "adm-hidden").withAttr(ADMONITION_SVG_OBJECT_PART)
                        .tag("svg").indent().line();
                for (String info : resolvedQualifiers) {
                    String svgContent = options.typeSvgMap.get(info);
                    if (svgContent != null && !svgContent.isEmpty()) {
                        html.raw("<symbol id=\"adm-").raw(info).raw("\">").indent().line()
                                .raw(svgContent).line()
                                .unIndent().raw("</symbol>").line();
                    }
                }
                html.unIndent().closeTag("svg").line();
            }
        }
    }

    private void render(AdmonitionBlock node, NodeRendererContext context, HtmlWriter html) {
        String info = node.getInfo().toString().toLowerCase();
        String type = this.options.qualifierTypeMap.get(info);
        if (type == null) {
            type = options.unresolvedQualifier;
        }

        String title;
        if (node.getTitle().isNull()) {
            title = this.options.qualifierTitleMap.get(info);
            if (title == null) {
                title = info.substring(0, 1).toUpperCase() + info.substring(1);
            }
        } else {
            title = node.getTitle().toString();
        }

        String openClose;
        if (node.getOpeningMarker().equals("???")) openClose = " adm-collapsed";
        else if (node.getOpeningMarker().equals("???+")) openClose = "adm-open";
        else openClose = null;

        if (title.isEmpty()) {
            html.srcPos(node.getChars()).withAttr()
                    .attr(Attribute.CLASS_ATTR, "adm-block")
                    .attr(Attribute.CLASS_ATTR, "adm-" + type)
                    .tag("div", false).line();

            html.attr(Attribute.CLASS_ATTR, "adm-body").withAttr(ADMONITION_BODY_PART).tag("div").indent().line();

            context.renderChildren(node);

            html.unIndent().closeTag("div").line();
            html.closeTag("div").line();
        } else {
            html.srcPos(node.getChars())
                    .attr(Attribute.CLASS_ATTR, "adm-block").attr(Attribute.CLASS_ATTR, "adm-" + type);

            if (openClose != null) {
                html.attr(Attribute.CLASS_ATTR, openClose).attr(Attribute.CLASS_ATTR, "adm-" + type);
            }

            html.withAttr().tag("div", false).line();
            html.attr(Attribute.CLASS_ATTR, "adm-heading").withAttr(ADMONITION_HEADING_PART).tag("div").line();
            html.attr(Attribute.CLASS_ATTR, "adm-icon").withAttr(ADMONITION_ICON_PART).tag("svg").raw("<use xlink:href=\"#adm-").raw(type).raw("\" />").closeTag("svg");
            html.withAttr(ADMONITION_TITLE_PART).tag("span").text(title).closeTag("span").line();
            html.closeTag("div").line();

            html.attr(Attribute.CLASS_ATTR, "adm-body").withAttr(ADMONITION_BODY_PART).withCondIndent().tagLine("div", () -> context.renderChildren(node));

            html.closeTag("div").line();
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new AdmonitionNodeRenderer(options);
        }
    }
}
