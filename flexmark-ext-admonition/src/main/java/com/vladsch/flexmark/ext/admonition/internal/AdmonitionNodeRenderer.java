package com.vladsch.flexmark.ext.admonition.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ext.admonition.AdmonitionBlock;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.options.DataHolder;

import java.io.*;
import java.util.*;

import static com.vladsch.flexmark.html.renderer.RenderingPhase.BODY_TOP;

public class AdmonitionNodeRenderer implements PhasedNodeRenderer {
    private final AdmonitionOptions options;

    public AdmonitionNodeRenderer(DataHolder options) {
        this.options = new AdmonitionOptions(options);
    }

    @Override
    public Set<RenderingPhase> getRenderingPhases() {
        LinkedHashSet<RenderingPhase> phaseSet = new LinkedHashSet<>();
        phaseSet.add(BODY_TOP);
        return phaseSet;
    }

    @Override
    public void renderDocument(final NodeRendererContext context, final HtmlWriter html, final Document document, final RenderingPhase phase) {
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
                html.line().raw("<svg xmlns=http://www.w3.org/2000/svg class=\"adm-hidden\">").indent().line();
                for (String info : resolvedQualifiers) {
                    String svgContent = options.typeSvgMap.get(info);
                    if (svgContent != null && !svgContent.isEmpty()) {
                        html.raw("<symbol id=\"adm-").raw(info).raw("\">").indent().line()
                                .raw("<svg enable-background=\"new 0 0 24 24\" viewBox=\"0 0 24 24\" xmlns=\"http://www.w3.org/2000/svg\">")
                                .raw(svgContent).line()
                                .unIndent().raw("</symbol>").line();
                    }
                }
                html.unIndent().raw("</svg>").line();
            }
        }
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        // @formatter:off
        // set.add(new NodeRenderingHandler<Admonition>(Admonition.class, new CustomNodeRenderer<Admonition>() { @Override public void render(Admonition node, NodeRendererContext context, HtmlWriter html) { AdmonitionNodeRenderer.this.render(node, context, html); } }));
        set.add(new NodeRenderingHandler<AdmonitionBlock>(AdmonitionBlock.class, new CustomNodeRenderer<AdmonitionBlock>() { @Override public void render(AdmonitionBlock node, NodeRendererContext context, HtmlWriter html) { AdmonitionNodeRenderer.this.render(node, context, html); } }));// ,// zzzoptionszzz(CUSTOM_NODE)
        // @formatter:on
        return set;
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

        Attributes attributes = new Attributes();
        attributes.addValue(Attribute.CLASS_ATTR, "adm-block");
        attributes.addValue(Attribute.CLASS_ATTR, "adm-" + type);

        if (title.isEmpty()) {
            html
                    .srcPos(node.getChars()).attr(attributes).withAttr().tag("div", false).line()
                    .raw("<div class=\"adm-body\">").indent().line();

            context.renderChildren(node);

            html
                    .unIndent().raw("</div>").line()
                    .closeTag("div").line()
            ;
        } else {
            if (openClose != null) attributes.addValue(Attribute.CLASS_ATTR, openClose);

            html
                    .srcPos(node.getChars()).attr(attributes).withAttr().tag("div", false).line()
                    .raw("<div class=\"adm-heading\">").line()
                    .raw("<svg class=\"adm-icon\"><use xlink:href=\"#adm-").raw(type).raw("\" /></svg><span>").text(title).raw("</span>").line()
                    .raw("</div>").line()
                    .raw("<div class=\"adm-body\">").indent().line()
            ;

            context.renderChildren(node);

            html
                    .unIndent().raw("</div>").line()
                    .raw("</div>").line()
                    .closeTag("div").line()
            ;
        }
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new AdmonitionNodeRenderer(options);
        }
    }
}
