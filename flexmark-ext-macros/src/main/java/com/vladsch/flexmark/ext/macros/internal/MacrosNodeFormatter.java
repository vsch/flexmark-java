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

import java.util.*;

public class MacrosNodeFormatter extends NodeRepositoryFormatter<MacroDefinitionRepository, MacroDefinitionBlock, MacroReference> {
    public static final DataKey<Map<String, String>> FOOTNOTE_TRANSLATION_MAP = new DataKey<Map<String, String>>("FOOTNOTE_TRANSLATION_MAP", new HashMap<String, String>()); // assign attributes to text if previous is not a space
    private final MacroFormatOptions options;

    public MacrosNodeFormatter(DataHolder options) {
        super(options, FOOTNOTE_TRANSLATION_MAP);
        this.options = new MacroFormatOptions(options);
    }

    @Override
    public MacroDefinitionRepository getRepository(DataHolder options) {
        return MacrosExtension.MACRO_DEFINITIONS.getFrom(options);
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

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<?>>(Arrays.asList(
                new NodeFormattingHandler<MacroReference>(MacroReference.class, new CustomNodeFormatter<MacroReference>() {
                    @Override
                    public void render(MacroReference node, NodeFormatterContext context, MarkdownWriter markdown) {
                        MacrosNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<MacroDefinitionBlock>(MacroDefinitionBlock.class, new CustomNodeFormatter<MacroDefinitionBlock>() {
                    @Override
                    public void render(MacroDefinitionBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        MacrosNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        if (options.macrosPlacement != ElementPlacement.AS_IS && options.macrosSort != ElementPlacementSort.SORT_UNUSED_LAST) return null;
        //noinspection unchecked,ArraysAsListWithZeroOrOneArgument
        return new HashSet<Class<?>>(Arrays.asList(
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
            context.nonTranslatingSpan(new TranslatingSpanRender() {
                @Override
                public void render(NodeFormatterContext context, MarkdownWriter markdown) {
                    markdown.append(referenceId);
                }
            });
        } else {
            markdown.append(node.getText());
        }
        markdown.append(">>>");
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(DataHolder options) {
            return new MacrosNodeFormatter(options);
        }
    }
}
