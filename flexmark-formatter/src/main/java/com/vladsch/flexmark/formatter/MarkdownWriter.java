package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.format.MarkdownWriterBase;

import java.util.function.Consumer;

public class MarkdownWriter extends MarkdownWriterBase<MarkdownWriter, Node, NodeFormatterContext> {
    public MarkdownWriter() {
        this(0);
    }

    public MarkdownWriter(int formatOptions) {
        super(formatOptions);
    }

    @Override
    public boolean isLastBlockQuoteChild() {
        Node node = context.getCurrentNode();
        Node parent = node.getParent();
        return parent instanceof BlockQuote && parent.getLastChild() == node;
    }

    public MarkdownWriter tailBlankLine(int count) {
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

    public MarkdownWriter appendNonTranslating(CharSequence csq) {
        return appendNonTranslating(null, csq, null, null);
    }

    public MarkdownWriter appendNonTranslating(CharSequence prefix, CharSequence csq) {
        return appendNonTranslating(prefix, csq, null, null);
    }

    public MarkdownWriter appendNonTranslating(CharSequence prefix, CharSequence csq, CharSequence suffix) {
        return appendNonTranslating(prefix, csq, suffix, null);
    }

    public MarkdownWriter appendNonTranslating(CharSequence prefix, CharSequence csq, CharSequence suffix, CharSequence suffix2) {
        if (context.isTransformingText()) {
            append(context.transformNonTranslating(prefix, csq, suffix, suffix2));
        } else {
            append(csq);
        }
        return this;
    }

    public MarkdownWriter appendNonTranslating(CharSequence prefix, CharSequence csq, CharSequence suffix, CharSequence suffix2, Consumer<String> placeholderConsumer) {
        if (context.isTransformingText()) {
            append(context.transformNonTranslating(prefix, csq, suffix, suffix2));
        } else {
            append(csq);
        }
        return this;
    }

    public MarkdownWriter appendTranslating(CharSequence csq) {
        return appendTranslating(null, csq, null, null);
    }

    public MarkdownWriter appendTranslating(CharSequence prefix, CharSequence csq) {
        return appendTranslating(prefix, csq, null, null);
    }

    public MarkdownWriter appendTranslating(CharSequence prefix, CharSequence csq, CharSequence suffix) {
        return appendTranslating(prefix, csq, suffix, null);
    }

    public MarkdownWriter appendTranslating(CharSequence prefix, CharSequence csq, CharSequence suffix, CharSequence suffix2) {
        if (context.isTransformingText()) {
            append(context.transformTranslating(prefix, csq, suffix, suffix2));
        } else {
            if (prefix != null) append(prefix);
            append(csq);
            if (suffix != null) append(suffix);
        }
        return this;
    }
}

