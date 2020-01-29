package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ext.definition.DefinitionItem;
import com.vladsch.flexmark.ext.definition.DefinitionList;
import com.vladsch.flexmark.ext.definition.DefinitionTerm;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.vladsch.flexmark.parser.Parser.BLANK_LINES_IN_AST;

public class DefinitionNodeFormatter implements NodeFormatter {
    final private DefinitionFormatOptions options;
    final private ListOptions listOptions;

    public DefinitionNodeFormatter(DataHolder options) {
        this.options = new DefinitionFormatOptions(options);
        this.listOptions = ListOptions.get(options);
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(DefinitionList.class, DefinitionNodeFormatter.this::render),
                new NodeFormattingHandler<>(DefinitionTerm.class, DefinitionNodeFormatter.this::render),
                new NodeFormattingHandler<>(DefinitionItem.class, DefinitionNodeFormatter.this::render)
        ));
    }

    private void render(DefinitionList node, NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
    }

    private void render(DefinitionTerm node, NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
    }

    private void render(DefinitionItem node, NodeFormatterContext context, MarkdownWriter markdown) {
        BasedSequence openMarkerChars = node.getChars().prefixOf(node.getFirstChild().getChars());
        BasedSequence openMarker = openMarkerChars.subSequence(0, 1);
        BasedSequence openMarkerSpaces = openMarkerChars.subSequence(1);

        if (options.markerSpaces >= 1 && openMarkerSpaces.length() != options.markerSpaces) {
            CharSequence charSequence = RepeatedSequence.repeatOf(' ', options.markerSpaces);
            openMarkerSpaces = BasedSequence.of(charSequence);
        }

        switch (options.markerType) {
            case ANY:
                break;
            case COLON:
                openMarker = BasedSequence.of(":").subSequence(0, ((CharSequence) ":").length());
                break;
            case TILDE:
                openMarker = BasedSequence.of("~").subSequence(0, ((CharSequence) "~").length());
                break;
        }

        markdown.line().append(openMarker).append(openMarkerSpaces);
        int count = context.getFormatterOptions().itemContentIndent ? openMarker.length() + openMarkerSpaces.length() : listOptions.getItemIndent();
        CharSequence prefix = RepeatedSequence.ofSpaces(count);
        markdown.pushPrefix().addPrefix(prefix);
        context.renderChildren(node);
        markdown.popPrefix();

        if (!BLANK_LINES_IN_AST.get(context.getOptions())) {
            // add blank lines after last paragraph item
            Node child = node.getLastChild();
            if (child instanceof Paragraph && ((Paragraph) child).isTrailingBlankLine()) {
                markdown.blankLine();
            }
        }
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new DefinitionNodeFormatter(options);
        }
    }
}
