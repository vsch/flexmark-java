package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.html.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.TagRange;

import java.util.ArrayList;

public class HtmlWriter extends HtmlFormattingAppendableBase<HtmlWriter> {
    private NodeRendererContext context;
    private AttributablePart useAttributes;

    public HtmlWriter(Appendable out) {
        super(out);
    }

    public HtmlWriter(HtmlWriter other, Appendable out, boolean inheritIndent) {
        super(other, out, inheritIndent);
        context = other.context;
    }

    public HtmlWriter(Appendable out, int indentSize) {
        super(out, indentSize, false);
    }

    public HtmlWriter(Appendable out, int indentSize, boolean allFormatOptions) {
        super(out, indentSize, allFormatOptions);
    }

    public HtmlWriter(Appendable out, int indentSize, int formatOptions) {
        super(out, indentSize, formatOptions);
    }

    void setContext(NodeRendererContext context) {
        this.context = context;
    }

    public NodeRendererContext getContext() {
        return context;
    }

    public HtmlWriter srcPos() {
        return srcPos(context.getCurrentNode().getChars());
    }

    public HtmlWriter srcPosWithEOL() {
        return srcPosWithEOL(context.getCurrentNode().getChars());
    }

    public HtmlWriter srcPosWithTrailingEOL() {
        return srcPosWithTrailingEOL(context.getCurrentNode().getChars());
    }

    public HtmlWriter srcPos(BasedSequence sourceText) {
        if (sourceText.isNotNull()) {
            BasedSequence trimmed = sourceText.trimEOL();
            return srcPos(trimmed.getStartOffset(), trimmed.getEndOffset());
        }
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public HtmlWriter srcPosWithEOL(BasedSequence sourceText) {
        if (sourceText.isNotNull()) {
            return srcPos(sourceText.getStartOffset(), sourceText.getEndOffset());
        }
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public HtmlWriter srcPosWithTrailingEOL(BasedSequence sourceText) {
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

    public HtmlWriter srcPos(int startOffset, int endOffset) {
        if (startOffset <= endOffset && !context.getHtmlOptions().sourcePositionAttribute.isEmpty()) {
            super.attr(context.getHtmlOptions().sourcePositionAttribute, startOffset + "-" + endOffset);
        }
        return this;
    }

    public HtmlWriter withAttr() {
        return withAttr(AttributablePart.NODE);
    }

    public HtmlWriter withAttr(AttributablePart part) {
        super.withAttr();
        useAttributes = part;
        return this;
    }

    public HtmlWriter withAttr(LinkStatus status) {
        attr(Attribute.LINK_STATUS_ATTR, status.getName());
        return withAttr(AttributablePart.LINK);
    }

    public HtmlWriter withAttr(ResolvedLink resolvedLink) {
        return withAttr(resolvedLink.getStatus());
    }

    @Override
    public HtmlWriter tag(String name, boolean voidElement) {
        if (useAttributes != null) {
            final Attributes attributes = context.extendRenderingNodeAttributes(useAttributes, getAttributes());
            String sourcePositionAttribute = context.getHtmlOptions().sourcePositionAttribute;
            String attributeValue = attributes.getValue(sourcePositionAttribute);

            if (!attributeValue.isEmpty()) {
                // add to tag ranges
                int pos = attributeValue.indexOf('-');
                int startOffset = -1;
                int endOffset = -1;

                if (pos != -1) {
                    try {
                        startOffset = Integer.valueOf(attributeValue.substring(0, pos));
                    } catch (Throwable ignored) {

                    }
                    try {
                        endOffset = Integer.valueOf(attributeValue.substring(pos + 1));
                    } catch (Throwable ignored) {

                    }
                }

                if (startOffset >= 0 && startOffset < endOffset) {
                    ArrayList<TagRange> tagRanges = context.getDocument().get(HtmlRenderer.TAG_RANGES);
                    tagRanges.add(new TagRange(name, startOffset, endOffset));
                }
            }

            setAttributes(attributes);
            useAttributes = null;
        }

        super.tag(name, voidElement);
        return this;
    }

    //@Override
    //public HtmlFormattingAppendableImpl openPre() {
    //    return super.openPre();
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl closePre() {
    //    return super.closePre();
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl raw(final String s) {
    //    return super.raw(s);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl text(final String text) {
    //    return super.text(text);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl attr(final String name, final String value) {
    //    return super.attr(name, value);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl attr(final Attribute attribute) {
    //    return super.attr(attribute);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl attr(final Attributes attributes) {
    //    return super.attr(attributes);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl setAttributes(final Attributes attributes) {
    //    return super.setAttributes(attributes);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl tag(final String name) {
    //    return super.tag(name);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl tagVoid(final String name) {
    //    return super.tagVoid(name);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl tag(final String name, final Runnable runnable) {
    //    return super.tag(name, runnable);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl tagVoidLine(final String name) {
    //    return super.tagVoidLine(name);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl tagLine(final String name) {
    //    return super.tagLine(name);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl tagLine(final String name, final boolean voidElement) {
    //    return super.tagLine(name, voidElement);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl tagLine(final String name, final Runnable runnable) {
    //    return super.tagLine(name, runnable);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl tagIndent(final String name, final Runnable runnable) {
    //    return super.tagIndent(name, runnable);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl line() {
    //    return super.line();
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl blankLine() {
    //    return super.blankLine();
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl lineIf(final boolean predicate) {
    //    return super.lineIf(predicate);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl indent() {
    //    return super.indent();
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl unIndent() {
    //    return super.unIndent();
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl flush() {
    //    return super.flush();
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl flush(final int maxBlankLines) {
    //    return super.flush(maxBlankLines);
    //}
    //
    //@Override
    //public FormattingAppendable setIndentPrefix(final CharSequence prefix) {
    //    return super.setIndentPrefix(prefix);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl line(final Ref<Boolean> lineRef) {
    //    return super.line(lineRef);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl lineIf(final Ref<Boolean> lineRef) {
    //    return super.lineIf(lineRef);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl blankLineIf(final boolean predicate) {
    //    return super.blankLineIf(predicate);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl blankLine(final int count) {
    //    return super.blankLine(count);
    //}
    //
    //@Override
    //public FormattingAppendable setIndentOffset(final int indentOffset) {
    //    return super.setIndentOffset(indentOffset);
    //}
    //
    //@Override
    //public FormattingAppendable openPreFormatted() {
    //    return super.openPreFormatted();
    //}
    //
    //@Override
    //public FormattingAppendable closePreFormatted() {
    //    return super.closePreFormatted();
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl openConditional(final ConditionalFormatter openFormatter) {
    //    return super.openConditional(openFormatter);
    //}
    //
    //@Override
    //public HtmlFormattingAppendableImpl closeConditional(final ConditionalFormatter closeFormatter) {
    //    return super.closeConditional(closeFormatter);
    //}
}
