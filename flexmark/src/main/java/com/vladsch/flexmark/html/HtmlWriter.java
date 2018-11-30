package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.HtmlFormattingAppendableBase;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.TagRange;

import java.util.ArrayList;

public class HtmlWriter extends HtmlFormattingAppendableBase<HtmlWriter> {
    private NodeRendererContext context;
    private AttributablePart useAttributes;

    //public HtmlWriter(Appendable out) {
    //    super(out, 0, 0);
    //}
    //
    //public HtmlWriter(Appendable out, int indentSize) {
    //    super(out, indentSize, 0);
    //}

    //public HtmlWriter(Appendable out, int indentSize, boolean allFormatOptions) {
    //    super(out, indentSize, 0);
    //}
    //

    public HtmlWriter(Appendable out, int indentSize, int formatOptions) {
        super(out, indentSize, formatOptions);
    }

    public HtmlWriter(HtmlWriter other, Appendable out, boolean inheritIndent) {
        super(other, out, inheritIndent);
        context = other.context;
    }

    public HtmlWriter(Appendable out, int indentSize, int formatOptions, boolean suppressOpenTagLine, boolean suppressCloseTagLine) {
        super(out, indentSize, formatOptions);
        setSuppressOpenTagLine(suppressOpenTagLine);
        setSuppressCloseTagLine(suppressCloseTagLine);
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
    public HtmlWriter tag(CharSequence tagName, boolean voidElement) {
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
                    tagRanges.add(new TagRange(tagName, startOffset, endOffset));
                }
            }

            setAttributes(attributes);
            useAttributes = null;
        }

        super.tag(tagName, voidElement);
        return this;
    }
}
