package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.HtmlAppendableBase;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.TagRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class HtmlWriter extends HtmlAppendableBase<HtmlWriter> {
    private @Nullable NodeRendererContext context;
    private @Nullable AttributablePart useAttributes;

    public HtmlWriter(int indentSize, int formatOptions) {
        super(indentSize, formatOptions);
    }

    public HtmlWriter(HtmlWriter other, boolean inheritIndent) {
        super(other, inheritIndent);
        context = other.context;
    }

    public HtmlWriter(int indentSize, int formatOptions, boolean suppressOpenTagLine, boolean suppressCloseTagLine) {
        this(null, indentSize, formatOptions, suppressOpenTagLine, suppressCloseTagLine);
    }

    public HtmlWriter(@Nullable Appendable appendable, int indentSize, int formatOptions, boolean suppressOpenTagLine, boolean suppressCloseTagLine) {
        super(appendable, indentSize, formatOptions);
        setSuppressOpenTagLine(suppressOpenTagLine);
        setSuppressCloseTagLine(suppressCloseTagLine);
    }

    void setContext(@NotNull NodeRendererContext context) {
        this.context = context;
    }

    public @NotNull NodeRendererContext getContext() {
        assert context != null;
        return context;
    }

    public @NotNull HtmlWriter srcPos() {
        return context == null ? this : srcPos(context.getCurrentNode().getChars());
    }

    public @NotNull HtmlWriter srcPosWithEOL() {
        return context == null ? this : srcPosWithEOL(context.getCurrentNode().getChars());
    }

    public @NotNull HtmlWriter srcPosWithTrailingEOL() {
        return context == null ? this : srcPosWithTrailingEOL(context.getCurrentNode().getChars());
    }

    public @NotNull HtmlWriter srcPos(@NotNull BasedSequence sourceText) {
        if (sourceText.isNotNull()) {
            BasedSequence trimmed = sourceText.trimEOL();
            return srcPos(trimmed.getStartOffset(), trimmed.getEndOffset());
        }
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public @NotNull HtmlWriter srcPosWithEOL(@NotNull BasedSequence sourceText) {
        if (sourceText.isNotNull()) {
            return srcPos(sourceText.getStartOffset(), sourceText.getEndOffset());
        }
        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public @NotNull HtmlWriter srcPosWithTrailingEOL(@NotNull BasedSequence sourceText) {
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

    public @NotNull HtmlWriter srcPos(int startOffset, int endOffset) {
        if (startOffset <= endOffset && context != null && !context.getHtmlOptions().sourcePositionAttribute.isEmpty()) {
            super.attr(context.getHtmlOptions().sourcePositionAttribute, startOffset + "-" + endOffset);
        }
        return this;
    }

    @NotNull
    public HtmlWriter withAttr() {
        return withAttr(AttributablePart.NODE);
    }

    public @NotNull HtmlWriter withAttr(@NotNull AttributablePart part) {
        super.withAttr();
        useAttributes = part;
        return this;
    }

    public @NotNull HtmlWriter withAttr(@NotNull LinkStatus status) {
        attr(Attribute.LINK_STATUS_ATTR, status.getName());
        return withAttr(AttributablePart.LINK);
    }

    public @NotNull HtmlWriter withAttr(@NotNull ResolvedLink resolvedLink) {
        return withAttr(resolvedLink.getStatus());
    }

    @NotNull
    @Override
    public HtmlWriter tag(@NotNull CharSequence tagName, boolean voidElement) {
        if (useAttributes != null) {
            String attributeValue;
            final Attributes attributes;
            
            if (context != null) {
                attributes = context.extendRenderingNodeAttributes(useAttributes, getAttributes());
                String sourcePositionAttribute = context.getHtmlOptions().sourcePositionAttribute;
                attributeValue = attributes.getValue(sourcePositionAttribute);
            } else {
                attributeValue = "";
                attributes = new Attributes();
            }
                
            if (!attributeValue.isEmpty()) {
                // add to tag ranges
                int pos = attributeValue.indexOf('-');
                int startOffset = -1;
                int endOffset = -1;

                if (pos != -1) {
                    try {
                        startOffset = Integer.parseInt(attributeValue.substring(0, pos));
                    } catch (Throwable ignored) {

                    }
                    try {
                        endOffset = Integer.parseInt(attributeValue.substring(pos + 1));
                    } catch (Throwable ignored) {

                    }
                }

                if (startOffset >= 0 && startOffset < endOffset) {
                    ArrayList<TagRange> tagRanges = HtmlRenderer.TAG_RANGES.get(context.getDocument());
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
