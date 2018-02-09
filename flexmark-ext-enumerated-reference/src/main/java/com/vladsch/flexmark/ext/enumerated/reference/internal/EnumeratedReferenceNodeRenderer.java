package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceBlock;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceLink;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceText;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.vladsch.flexmark.html.renderer.RenderingPhase.BODY_TOP;

public class EnumeratedReferenceNodeRenderer implements PhasedNodeRenderer
        // , PhasedNodeRenderer
{
    private final EnumeratedReferenceOptions options;
    private EnumeratedReferences enumeratedOrdinals;
    private int ordinal;

    public EnumeratedReferenceNodeRenderer(DataHolder options) {
        this.options = new EnumeratedReferenceOptions(options);
        ordinal = 0;
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
            enumeratedOrdinals = EnumeratedReferenceExtension.ENUMERATED_REFERENCE_ORDINALS.getFrom(document);
        }
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        // @formatter:off
        set.add(new NodeRenderingHandler<EnumeratedReferenceText>(EnumeratedReferenceText.class, new CustomNodeRenderer<EnumeratedReferenceText>() { @Override public void render(EnumeratedReferenceText node, NodeRendererContext context, HtmlWriter html) { EnumeratedReferenceNodeRenderer.this.render(node, context, html); } }));
        set.add(new NodeRenderingHandler<EnumeratedReferenceLink>(EnumeratedReferenceLink.class, new CustomNodeRenderer<EnumeratedReferenceLink>() { @Override public void render(EnumeratedReferenceLink node, NodeRendererContext context, HtmlWriter html) { EnumeratedReferenceNodeRenderer.this.render(node, context, html); } }));
        set.add(new NodeRenderingHandler<EnumeratedReferenceBlock>(EnumeratedReferenceBlock.class, new CustomNodeRenderer<EnumeratedReferenceBlock>() { @Override public void render(EnumeratedReferenceBlock node, NodeRendererContext context, HtmlWriter html) { EnumeratedReferenceNodeRenderer.this.render(node, context, html); } }));// ,// zzzoptionszzz(CUSTOM_NODE)
        // @formatter:on
        return set;
    }

    private void render(EnumeratedReferenceLink node, NodeRendererContext context, HtmlWriter html) {
        final String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            html.text(String.valueOf(ordinal));
        } else {
            Node referenceFormat = enumeratedOrdinals.getFormatNode(text);
            int wasOrdinal = ordinal;
            ordinal = enumeratedOrdinals.getOrdinal(text);
            if (referenceFormat != null) {
                String title = new EnumRefTextCollectingVisitor(ordinal).collectAndGetText(referenceFormat);
                html.withAttr().attr("href", "#" + text).attr("title", title).tag("a");
                context.renderChildren(referenceFormat);
                html.tag("/a");
            } else {
                // no format, just output type space ordinal
                final String defaultText = String.format("%s %d", EnumeratedReferences.getType(text), ordinal);
                html.withAttr().attr("href", "#" + text).attr("title", defaultText).tag("a");
                html.text(defaultText);
                html.tag("/a");
            }
            ordinal = wasOrdinal;
        }
    }

    private void render(EnumeratedReferenceText node, NodeRendererContext context, HtmlWriter html) {
        final String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            html.text(String.valueOf(ordinal));
        } else {
            Node referenceFormat = enumeratedOrdinals.getFormatNode(text);
            int wasOrdinal = ordinal;
            ordinal = enumeratedOrdinals.getOrdinal(text);

            final String defaultText = String.format("%s %d", EnumeratedReferences.getType(text), ordinal);

            if (referenceFormat != null) {
                context.renderChildren(referenceFormat);
            } else {
                // no format, just output ordinal
                html.text(defaultText);
            }
            ordinal = wasOrdinal;
        }
    }

    private void render(EnumeratedReferenceBlock node, NodeRendererContext context, HtmlWriter html) {

    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new EnumeratedReferenceNodeRenderer(options);
        }
    }
}
