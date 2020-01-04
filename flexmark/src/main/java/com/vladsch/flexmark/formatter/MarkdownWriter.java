package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.BlankLine;
import com.vladsch.flexmark.util.ast.BlockQuoteLike;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.format.MarkdownWriterBase;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnusedReturnValue")
public class MarkdownWriter extends MarkdownWriterBase<MarkdownWriter, Node, NodeFormatterContext> {
    public MarkdownWriter() {
        this(null, 0);
    }

    public MarkdownWriter(int formatOptions) {
        this(null, formatOptions);
    }

    public MarkdownWriter(@Nullable Appendable appendable, int formatOptions) {
        super(appendable, formatOptions);
    }

    @NotNull
    @Override
    public MarkdownWriter getEmptyAppendable() {
        return new MarkdownWriter(appendable, appendable.getOptions());
    }

    @Override
    public @NotNull BasedSequence lastBlockQuoteChildPrefix(BasedSequence prefix) {
        Node node = context.getCurrentNode();
        while (node != null && node.getNextAnyNot(BlankLine.class) == null) {
            Node parent = node.getParent();
            if (parent == null || parent instanceof Document) break;
            if (parent instanceof BlockQuoteLike) {
                int pos = prefix.lastIndexOfAny(context.getBlockQuoteLikePrefixPredicate());
                if (pos >= 0) {
                    prefix = prefix.getBuilder().append(prefix.subSequence(0, pos)).append(' ').append(prefix.subSequence(pos + 1)).toSequence();
//                } else {
//                    // NOTE: occurs if continuation block prefix is remove
                }
            }
            node = parent;
        }
        return prefix;
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

