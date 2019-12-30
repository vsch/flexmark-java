package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.util.ast.BlockQuoteLike;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.format.MarkdownWriterBase;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnusedReturnValue")
public class MarkdownWriter extends MarkdownWriterBase<MarkdownWriter, Node, NodeFormatterContext> {
    public MarkdownWriter() {
        this(0);
    }

    public MarkdownWriter(int formatOptions) {
        super(formatOptions);
    }

    public MarkdownWriter(int formatOptions, @Nullable SequenceBuilder builder) {
        super(formatOptions, builder);
    }

    @Override
    public boolean isLastBlockQuoteChild() {
        Node node = context.getCurrentNode();
        Node parent = node.getParent();
        return parent instanceof BlockQuoteLike && parent.getLastChild() == node;
    }

    public @NotNull MarkdownWriter tailBlankLine(int count) {
        if (isLastBlockQuoteChild()) {
            // Needed to not add block quote prefix to trailing blank lines
            CharSequence prefix = getPrefix();
            popPrefix();
            blankLine(count);
            pushPrefix();
            setPrefix(prefix, false);
        } else {
            blankLine(count);
        }
        return this;
    }

    public @NotNull MarkdownWriter appendNonTranslating(@NotNull CharSequence csq) {
        return appendNonTranslating(null, csq, null, null);
    }

    public @NotNull MarkdownWriter appendNonTranslating(@Nullable CharSequence prefix, @NotNull CharSequence csq) {
        return appendNonTranslating(prefix, csq, null, null);
    }

    public @NotNull MarkdownWriter appendNonTranslating(@Nullable CharSequence prefix, @NotNull CharSequence csq, @Nullable CharSequence suffix) {
        return appendNonTranslating(prefix, csq, suffix, null);
    }

    public @NotNull MarkdownWriter appendNonTranslating(@Nullable CharSequence prefix, @NotNull CharSequence csq, @Nullable CharSequence suffix, @Nullable CharSequence suffix2) {
        if (context.isTransformingText()) {
            append(context.transformNonTranslating(prefix, csq, suffix, suffix2));
        } else {
            append(csq);
        }
        return this;
    }

    public @NotNull MarkdownWriter appendTranslating(@NotNull CharSequence csq) {
        return appendTranslating(null, csq, null, null);
    }

    public @NotNull MarkdownWriter appendTranslating(@Nullable CharSequence prefix, @NotNull CharSequence csq) {
        return appendTranslating(prefix, csq, null, null);
    }

    public @NotNull MarkdownWriter appendTranslating(@Nullable CharSequence prefix, @NotNull CharSequence csq, @Nullable CharSequence suffix) {
        return appendTranslating(prefix, csq, suffix, null);
    }

    public @NotNull MarkdownWriter appendTranslating(@Nullable CharSequence prefix, @NotNull CharSequence csq, @Nullable CharSequence suffix, @Nullable CharSequence suffix2) {
        if (context.isTransformingText()) {
            append(context.transformTranslating(prefix, csq, suffix, suffix2));
        } else {
            if (prefix != null) append(prefix);
            append(csq);
            if (suffix != null) append(suffix);
            if (suffix2 != null) append(suffix2);
        }
        return this;
    }
}

