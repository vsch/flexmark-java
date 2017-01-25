package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class MarkdownWriter extends MarkdownFormattingAppendableBase<MarkdownWriter> {
    private NodeFormatterContext context;

    public MarkdownWriter(Appendable out) {
        super(out);
    }

    public MarkdownWriter(MarkdownWriter other, Appendable out, boolean inheritIndent) {
        super(other, out, inheritIndent);
        context = other.context;
    }

    public MarkdownWriter(Appendable out, int indentSize) {
        super(out, indentSize, false);
    }

    public MarkdownWriter(Appendable out, int indentSize, boolean allFormatOptions) {
        super(out, indentSize, allFormatOptions);
    }

    public MarkdownWriter(Appendable out, int indentSize, int formatOptions) {
        super(out, indentSize, formatOptions);
    }

    void setContext(NodeFormatterContext context) {
        this.context = context;
    }

    public NodeFormatterContext getContext() {
        return context;
    }

    public MarkdownWriter srcPos() {
        return srcPos(context.getCurrentNode().getChars());
    }

    public MarkdownWriter srcPosWithEOL() {
        return srcPosWithEOL(context.getCurrentNode().getChars());
    }

    public MarkdownWriter srcPosWithTrailingEOL() {
        return srcPosWithTrailingEOL(context.getCurrentNode().getChars());
    }

    public MarkdownWriter srcPos(BasedSequence sourceText) {
        if (sourceText.isNotNull()) {
            BasedSequence trimmed = sourceText.trimEOL();
            return srcPos(trimmed.getStartOffset(), trimmed.getEndOffset());
        }
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public MarkdownWriter srcPosWithEOL(BasedSequence sourceText) {
        if (sourceText.isNotNull()) {
            return srcPos(sourceText.getStartOffset(), sourceText.getEndOffset());
        }
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public MarkdownWriter srcPosWithTrailingEOL(BasedSequence sourceText) {
        if (sourceText.isNotNull()) {
            int endOffset = sourceText.getEndOffset();
            BasedSequence base = sourceText.getBaseSequence();

            while (endOffset < base.length()) {
                char c = base.charAt(endOffset);
                if (c != ' ' && c != '\t') break;
                endOffset++;
            }

            if (endOffset < base.length() && base.charAt(endOffset) == '\r') {
                endOffset++;
            }

            if (endOffset < base.length() && base.charAt(endOffset) == '\n') {
                endOffset++;
            }
            return srcPos(sourceText.getStartOffset(), endOffset);
        }
        return this;
    }

    public MarkdownWriter srcPos(int startOffset, int endOffset) {
        return this;
    }

    public MarkdownWriter withAttr() {
        return withAttr(AttributablePart.NODE);
    }

    public MarkdownWriter withAttr(AttributablePart part) {
        super.withAttr();
        return this;
    }

    public MarkdownWriter withAttr(LinkStatus status) {
        attr(Attribute.LINK_STATUS_ATTR, status.getName());
        return withAttr(AttributablePart.LINK);
    }

    public MarkdownWriter withAttr(ResolvedLink resolvedLink) {
        return withAttr(resolvedLink.getStatus());
    }

    @Override
    public MarkdownWriter tag(CharSequence tagName, boolean voidElement) {
        super.tag(tagName, voidElement);
        return this;
    }
}
