package com.vladsch.flexmark.ext.aside.internal;

import com.vladsch.flexmark.ext.aside.AsideBlock;
import com.vladsch.flexmark.formatter.FormatterUtils;
import com.vladsch.flexmark.formatter.MarkdownWriter;
import com.vladsch.flexmark.formatter.NodeFormatter;
import com.vladsch.flexmark.formatter.NodeFormatterContext;
import com.vladsch.flexmark.formatter.NodeFormatterFactory;
import com.vladsch.flexmark.formatter.NodeFormattingHandler;
import com.vladsch.flexmark.formatter.FormatterOptions;
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.html.LineAppendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class AsideNodeFormatter implements NodeFormatter {

    public AsideNodeFormatter(DataHolder options) {

    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Override
    public char getBlockQuoteLikePrefixChar() {
        return '|';
    }

    // only registered if assignTextAttributes is enabled
    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        HashSet<NodeFormattingHandler<?>> set = new HashSet<>();
        set.add(new NodeFormattingHandler<>(AsideBlock.class, AsideNodeFormatter.this::render));
        return set;
    }

    private void render(AsideBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        FormatterOptions formatterOptions = context.getFormatterOptions();

        String combinedPrefix = FormatterUtils.getBlockLikePrefix(node, context, markdown, formatterOptions.blockQuoteMarkers);

        markdown.pushPrefix();

        if (!FormatterUtils.FIRST_LIST_ITEM_CHILD.get(node.getDocument())) {
            if (formatterOptions.blockQuoteBlankLines) {
                markdown.blankLine();
            }

            markdown.setPrefix(combinedPrefix, false);
        } else {
            markdown.pushOptions().removeOptions(LineAppendable.F_WHITESPACE_REMOVAL).append(combinedPrefix).popOptions();
        }

        int lines = markdown.getLineCount();
        context.renderChildren(node);
        markdown.popPrefix();
        if (formatterOptions.blockQuoteBlankLines && (lines < markdown.getLineCount() || !FormatterUtils.FIRST_LIST_ITEM_CHILD.get(node.getDocument())))
            markdown.tailBlankLine();
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new AsideNodeFormatter(options);
        }
    }
}
