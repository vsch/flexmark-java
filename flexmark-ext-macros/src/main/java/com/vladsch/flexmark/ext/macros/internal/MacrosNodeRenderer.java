package com.vladsch.flexmark.ext.macros.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ext.macros.MacroDefinitionBlock;
import com.vladsch.flexmark.ext.macros.MacroReference;
import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class MacrosNodeRenderer implements PhasedNodeRenderer {
    final private MacrosOptions options;
    final MacroDefinitionRepository repository;
    final private boolean recheckUndefinedReferences;

    public MacrosNodeRenderer(DataHolder options) {
        this.options = new MacrosOptions(options);
        this.repository = MacrosExtension.MACRO_DEFINITIONS.get(options);
        this.recheckUndefinedReferences = HtmlRenderer.RECHECK_UNDEFINED_REFERENCES.get(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        // @formatter:off
        set.add(new NodeRenderingHandler<>(MacroReference.class, this::render));
        set.add(new NodeRenderingHandler<>(MacroDefinitionBlock.class, this::render));
        // @formatter:on
        return set;
    }

    @Override
    public Set<RenderingPhase> getRenderingPhases() {
        Set<RenderingPhase> set = new HashSet<>();
        set.add(RenderingPhase.BODY_TOP);
        //set.add(RenderingPhase.BODY_BOTTOM);
        return set;
    }

    @Override
    public void renderDocument(@NotNull NodeRendererContext context, @NotNull HtmlWriter html, @NotNull Document document, @NotNull RenderingPhase phase) {
        if (phase == RenderingPhase.BODY_TOP) {
            if (recheckUndefinedReferences) {
                // need to see if have undefined footnotes that were defined after parsing
                boolean[] hadNewFootnotes = { false };
                NodeVisitor visitor = new NodeVisitor(
                        new VisitHandler<>(MacroReference.class, node -> {
                            if (!node.isDefined()) {
                                MacroDefinitionBlock macroDefinitionBlock = node.getMacroDefinitionBlock(repository);

                                if (macroDefinitionBlock != null) {
                                    repository.addMacrosReference(macroDefinitionBlock, node);
                                    node.setMacroDefinitionBlock(macroDefinitionBlock);
                                    hadNewFootnotes[0] = true;
                                }
                            }
                        })
                );

                visitor.visit(document);
                if (hadNewFootnotes[0]) {
                    this.repository.resolveMacrosOrdinals();
                }
            }
        }
    }

    private void render(MacroReference node, NodeRendererContext context, HtmlWriter html) {
        // render contents of macro definition
        MacroDefinitionBlock macroDefinitionBlock = repository.get(repository.normalizeKey(node.getText()));
        if (macroDefinitionBlock != null) {
            if (macroDefinitionBlock.hasChildren() && !macroDefinitionBlock.isInExpansion()) {
                try {
                    macroDefinitionBlock.setInExpansion(true);
                    Node child = macroDefinitionBlock.getFirstChild();
                    if (child instanceof Paragraph && child == macroDefinitionBlock.getLastChild()) {
                        // if a single paragraph then we unwrap it and output only its children as inline text
                        if (options.sourceWrapMacroReferences) {
                            html.srcPos(node.getChars()).withAttr(AttributablePart.NODE_POSITION).tag("span");
                            context.renderChildren(child);
                            html.tag("/span");
                        } else {
                            context.renderChildren(child);
                        }
                    } else {
                        if (options.sourceWrapMacroReferences) {
                            html.srcPos(node.getChars()).withAttr(AttributablePart.NODE_POSITION).tag("div").indent().line();
                            context.renderChildren(macroDefinitionBlock);
                            html.unIndent().tag("/div");
                        } else {
                            context.renderChildren(macroDefinitionBlock);
                        }
                    }
                } finally {
                    macroDefinitionBlock.setInExpansion(false);
                }
            }
        } else {
            html.text(node.getChars());
        }
    }

    private void render(MacroDefinitionBlock node, NodeRendererContext context, HtmlWriter html) {
        // nothing to render
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new MacrosNodeRenderer(options);
        }
    }
}
