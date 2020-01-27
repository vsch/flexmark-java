package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ext.enumerated.reference.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class EnumeratedReferenceNodeRenderer implements PhasedNodeRenderer
        // , PhasedNodeRenderer
{
    final private EnumeratedReferenceOptions options;
    private EnumeratedReferences enumeratedOrdinals;
    private Runnable ordinalRunnable;
    final private HtmlIdGenerator headerIdGenerator; // used for enumerated text reference

    public EnumeratedReferenceNodeRenderer(DataHolder options) {
        this.options = new EnumeratedReferenceOptions(options);
        ordinalRunnable = null;
        headerIdGenerator = new HeaderIdGenerator.Factory().create();
    }

    @Override
    public Set<RenderingPhase> getRenderingPhases() {
        LinkedHashSet<RenderingPhase> phaseSet = new LinkedHashSet<>();
        phaseSet.add(RenderingPhase.HEAD_TOP);
        phaseSet.add(RenderingPhase.BODY_TOP);
        return phaseSet;
    }

    @Override
    public void renderDocument(@NotNull NodeRendererContext context, @NotNull HtmlWriter html, @NotNull Document document, @NotNull RenderingPhase phase) {
        if (phase == RenderingPhase.HEAD_TOP) {
            headerIdGenerator.generateIds(document);
        } else if (phase == RenderingPhase.BODY_TOP) {
            enumeratedOrdinals = EnumeratedReferenceExtension.ENUMERATED_REFERENCE_ORDINALS.get(document);
        }
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        // @formatter:off
        set.add(new NodeRenderingHandler<>(EnumeratedReferenceText.class, EnumeratedReferenceNodeRenderer.this::render));
        set.add(new NodeRenderingHandler<>(EnumeratedReferenceLink.class, EnumeratedReferenceNodeRenderer.this::render));
        set.add(new NodeRenderingHandler<>(EnumeratedReferenceBlock.class, EnumeratedReferenceNodeRenderer.this::render));// ,// zzzoptionszzz(CUSTOM_NODE)
        // @formatter:on
        return set;
    }

    private void render(EnumeratedReferenceLink node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            if (ordinalRunnable != null) ordinalRunnable.run();
        } else {
            enumeratedOrdinals.renderReferenceOrdinals(text, new OrdinalRenderer(this, context, html) {
                @Override
                public void startRendering(EnumeratedReferenceRendering[] renderings) {
                    String title = new EnumRefTextCollectingVisitor().collectAndGetText(node.getChars().getBaseSequence(), renderings, null);
                    html.withAttr().attr("href", "#" + text).attr("title", title).tag("a");
                }

                @Override
                public void endRendering() {
                    html.tag("/a");
                }
            });
        }
    }

    private void render(EnumeratedReferenceText node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            if (ordinalRunnable != null) ordinalRunnable.run();
        } else {
            String type = EnumeratedReferenceRepository.getType(text.toString());

            if (type.isEmpty() || text.equals(type + ":")) {
                Node parent = node.getAncestorOfType(Heading.class);

                if (parent instanceof Heading) {
                    text = (type.isEmpty() ? text : type) + ":" + headerIdGenerator.getId(parent);
                }
            }

            enumeratedOrdinals.renderReferenceOrdinals(text, new OrdinalRenderer(this, context, html));
        }
    }

    private static class OrdinalRenderer implements EnumeratedOrdinalRenderer {
        final EnumeratedReferenceNodeRenderer renderer;
        final NodeRendererContext context;
        final HtmlWriter html;

        public OrdinalRenderer(EnumeratedReferenceNodeRenderer renderer, NodeRendererContext context, HtmlWriter html) {
            this.renderer = renderer;
            this.context = context;
            this.html = html;
        }

        @Override
        public void startRendering(EnumeratedReferenceRendering[] renderings) {

        }

        @Override
        public void setEnumOrdinalRunnable(Runnable runnable) {
            renderer.ordinalRunnable = runnable;
        }

        @Override
        public Runnable getEnumOrdinalRunnable() {
            return renderer.ordinalRunnable;
        }

        @Override
        public void render(int referenceOrdinal, EnumeratedReferenceBlock referenceFormat, String defaultText, boolean needSeparator) {
            Runnable compoundRunnable = renderer.ordinalRunnable;

            if (referenceFormat != null) {
                renderer.ordinalRunnable = () -> {
                    if (compoundRunnable != null) compoundRunnable.run();
                    html.text(String.valueOf(referenceOrdinal));
                    if (needSeparator) html.text(".");
                };

                context.renderChildren(referenceFormat);
            } else {
                html.text(defaultText + " ");
                if (compoundRunnable != null) compoundRunnable.run();
                html.text(String.valueOf(referenceOrdinal));
                if (needSeparator) html.text(".");
            }
        }

        @Override
        public void endRendering() {

        }
    }

    private void render(EnumeratedReferenceBlock node, NodeRendererContext context, HtmlWriter html) {

    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new EnumeratedReferenceNodeRenderer(options);
        }
    }
}
