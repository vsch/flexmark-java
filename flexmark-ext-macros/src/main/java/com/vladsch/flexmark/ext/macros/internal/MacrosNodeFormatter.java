package com.vladsch.flexmark.ext.macros.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ext.macros.MacroDefinitionBlock;
import com.vladsch.flexmark.ext.macros.MacroReference;
import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MacrosNodeFormatter extends NodeRepositoryFormatter<MacroDefinitionRepository, MacroDefinitionBlock, MacroReference> {
    final public static DataKey<Map<String, String>> MACROS_TRANSLATION_MAP = new DataKey<>("MACROS_TRANSLATION_MAP", new HashMap<>());
    final public static DataKey<Map<String, String>> MACROS_UNIQUIFICATION_MAP = new DataKey<>("MACROS_UNIQUIFICATION_MAP", new HashMap<>()); // uniquified references
    final private MacroFormatOptions options;

    public MacrosNodeFormatter(DataHolder options) {
        super(options, MACROS_TRANSLATION_MAP, MACROS_UNIQUIFICATION_MAP);
        this.options = new MacroFormatOptions(options);
    }

    @Override
    public MacroDefinitionRepository getRepository(DataHolder options) {
        return MacrosExtension.MACRO_DEFINITIONS.get(options);
    }

    @Override
    public ElementPlacement getReferencePlacement() {
        return options.macrosPlacement;
    }

    @Override
    public ElementPlacementSort getReferenceSort() {
        return options.macrosSort;
    }

    @Override
    public void renderReferenceBlock(MacroDefinitionBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine().append(">>>").append(transformReferenceId(node.getName().toString(), context)).line();
        Node child = node.getFirstChild();
        if (child instanceof Paragraph && child == node.getLastChild()) {
            // if a single paragraph then we unwrap it and output only its children as inline text
            context.renderChildren(child);
        } else {
            context.renderChildren(node);
        }
        markdown.line().append("<<<").blankLine();
    }

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(MacroReference.class, MacrosNodeFormatter.this::render),
                new NodeFormattingHandler<>(MacroDefinitionBlock.class, MacrosNodeFormatter.this::render)
        ));
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        if (options.macrosPlacement.isNoChange() || !options.macrosSort.isUnused()) return null;
        // noinspection ArraysAsListWithZeroOrOneArgument
        return new HashSet<>(Arrays.asList(
                MacroReference.class
        ));
    }

    private void render(MacroDefinitionBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderReference(node, context, markdown);
    }

    private void render(MacroReference node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append("<<<");
        if (context.isTransformingText()) {
            String referenceId = transformReferenceId(node.getText().toString(), context);
            context.nonTranslatingSpan((context1, markdown1) -> markdown1.append(referenceId));
        } else {
            markdown.append(node.getText());
        }
        markdown.append(">>>");
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new MacrosNodeFormatter(options);
        }
    }
}
