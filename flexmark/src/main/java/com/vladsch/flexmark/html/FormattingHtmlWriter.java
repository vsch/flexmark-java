package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.*;
import com.vladsch.flexmark.util.options.Attribute;
import com.vladsch.flexmark.util.options.Attributes;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequence;
import com.vladsch.flexmark.util.sequence.TagRange;

import java.io.IOException;
import java.util.ArrayList;

public class FormattingHtmlWriter {
    private final FormattingAppendable buffer;
    private NodeRendererContext context;

    private Attributes currentAttributes;
    private boolean indentIndentingChildren = false;
    private boolean lineOnChildText = false;
    private AttributablePart useAttributes = null;

    public FormattingHtmlWriter(Appendable out) {
        this(out, 0);
    }

    public FormattingHtmlWriter(FormattingHtmlWriter other, Appendable out, boolean inheritIndent) {
        this(out, inheritIndent ? other.buffer.getIndent() : 0);
    }

    public FormattingHtmlWriter(Appendable out, int indentSize) {
        this.buffer = new FormattingAppendableImpl(out, true);
        this.buffer.setIndentPrefix(RepeatedCharSequence.of(" ", indentSize).toString());
    }

    public CharSequence getPrefix() {
        return buffer.getIndentPrefix();
    }

    public FormattingHtmlWriter setPrefix(final CharSequence prefix) {
        buffer.setIndentPrefix(prefix);
        return this;
    }

    public int getAppendCount() {
        return buffer.getModCount();
    }

    boolean inPre() {
        return buffer.isPreFormatted();
    }

    void setContext(NodeRendererContext context) {
        this.context = context;
    }

    public NodeRendererContext getContext() {
        return context;
    }

    public int getIndentSize() {
        return buffer.getIndentPrefix().length();
    }

    protected void append(String s) {
        buffer.append(s);
    }

    public FormattingHtmlWriter raw(String s) {
        buffer.openPreFormatted();
        buffer.append(s);
        buffer.closePreFormatted();
        return this;
    }

    public FormattingHtmlWriter text(String text) {
        append(Escaping.escapeHtml(text, false));
        return this;
    }

    public FormattingHtmlWriter attr(String name, String value) {
        if (currentAttributes == null) {
            currentAttributes = new Attributes();
        }
        currentAttributes.replaceValue(name, value);
        return this;
    }

    public FormattingHtmlWriter srcPos() {
        return srcPos(context.getCurrentNode().getChars());
    }

    public FormattingHtmlWriter srcPosWithEOL() {
        return srcPosWithEOL(context.getCurrentNode().getChars());
    }

    public FormattingHtmlWriter srcPosWithTrailingEOL() {
        return srcPosWithTrailingEOL(context.getCurrentNode().getChars());
    }

    public FormattingHtmlWriter srcPos(BasedSequence sourceText) {
        if (sourceText.isNotNull()) {
            BasedSequence trimmed = sourceText.trimEOL();
            return srcPos(trimmed.getStartOffset(), trimmed.getEndOffset());
        }
        return this;
    }

    public FormattingHtmlWriter srcPosWithEOL(BasedSequence sourceText) {
        if (sourceText.isNotNull()) {
            return srcPos(sourceText.getStartOffset(), sourceText.getEndOffset());
        }
        return this;
    }

    public FormattingHtmlWriter srcPosWithTrailingEOL(BasedSequence sourceText) {
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

    public FormattingHtmlWriter srcPos(int startOffset, int endOffset) {
        if (startOffset <= endOffset && !context.getHtmlOptions().sourcePositionAttribute.isEmpty()) {
            if (currentAttributes == null) {
                currentAttributes = new Attributes();
            }
            currentAttributes.replaceValue(context.getHtmlOptions().sourcePositionAttribute, startOffset + "-" + endOffset);
        }
        return this;
    }

    public FormattingHtmlWriter attr(Attribute attribute) {
        if (currentAttributes == null) {
            currentAttributes = new Attributes();
        }
        currentAttributes.replaceValue(attribute.getName(), attribute.getValue());
        return this;
    }

    public FormattingHtmlWriter attr(Attributes attributes) {
        if (!attributes.isEmpty()) {
            if (currentAttributes == null) {
                currentAttributes = new Attributes(attributes);
            } else {
                currentAttributes.replaceValues(attributes);
            }
        }
        return this;
    }

    public FormattingHtmlWriter tag(String name) {
        return tag(name, false, false);
    }

    public FormattingHtmlWriter tagVoid(String name) {
        return tag(name, true, false);
    }

    public FormattingHtmlWriter tagVoidLine(String name) {
        return tag(name, true, true);
    }

    public FormattingHtmlWriter withAttr() {
        return withAttr(AttributablePart.NODE);
    }

    public FormattingHtmlWriter withAttr(AttributablePart part) {
        useAttributes = part;
        return this;
    }

    public FormattingHtmlWriter withAttr(LinkStatus status) {
        attr(Attribute.LINK_STATUS, status.getName());
        return withAttr(AttributablePart.LINK);
    }

    public FormattingHtmlWriter withAttr(ResolvedLink resolvedLink) {
        return withAttr(resolvedLink.getStatus());
    }

    public FormattingHtmlWriter tag(String name, boolean voidElement, boolean voidWithLine) {
        Attributes attributes = null;

        if (useAttributes != null) {
            attributes = context.extendRenderingNodeAttributes(useAttributes, currentAttributes);
            currentAttributes = null;
            useAttributes = null;
        }

        if (voidElement && voidWithLine) line();

        append("<");
        append(name);

        if (attributes != null && !attributes.isEmpty()) {
            String sourcePositionAttribute = context.getHtmlOptions().sourcePositionAttribute;

            for (Attribute attribute : attributes.values()) {
                String attributeValue = attribute.getValue();

                if (!sourcePositionAttribute.isEmpty() && attribute.getName().equals(sourcePositionAttribute)) {
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

                if (attribute.isNonRendering()) continue;

                append(" ");
                append(Escaping.escapeHtml(attribute.getName(), true));
                append("=\"");
                append(Escaping.escapeHtml(attributeValue, true));
                append("\"");
            }
        }

        if (voidElement) {
            append(" />");
            if (voidWithLine) line();
        } else {
            append(">");
        }

        return this;
    }

    public FormattingHtmlWriter tag(String name, Runnable runnable) {
        return tag(name, false, false, runnable);
    }

    public FormattingHtmlWriter tagIndent(String name, Runnable runnable) {
        return tag(name, !indentIndentingChildren, false, runnable);
    }

    public FormattingHtmlWriter tagLine(String name, Runnable runnable) {
        return tag(name, false, !lineOnChildText, runnable);
    }

    public FormattingHtmlWriter tag(String name, boolean indentTag, boolean withLine, Runnable runnable) {
        if (indentTag) indent();
        else if (withLine) line();

        tag(name, false, false);

        final boolean isLineOnChildText = lineOnChildText;
        final boolean isIndentIndentingChildren = indentIndentingChildren;
        lineOnChildText = false;
        indentIndentingChildren = false;

        if (isLineOnChildText || isIndentIndentingChildren) {
            buffer.openConditional(new ConditionalFormatter() {
                @Override
                public void apply(final boolean onIndent, final boolean onLine, final boolean onText) {
                    if (onIndent) {
                        if (isIndentIndentingChildren) buffer.indent();
                        else buffer.line();
                    } else if (isLineOnChildText) {
                        buffer.line();
                    }
                }
            });
        }

        runnable.run();

        if (isLineOnChildText || isIndentIndentingChildren) {
            buffer.closeConditional(new ConditionalFormatter() {
                @Override
                public void apply(final boolean onIndent, final boolean onLine, final boolean onText) {
                    if (onIndent && isIndentIndentingChildren) {
                        buffer.unIndent();
                    } else if (onText && isLineOnChildText) {
                        buffer.line();
                    }
                }
            });
        }

        append("</");
        append(name);
        append(">");

        if (indentTag) unIndent();
        else if (withLine) line();

        return this;
    }

    public FormattingHtmlWriter line() {
        buffer.line();
        return this;
    }

    public FormattingHtmlWriter blankLine() {
        buffer.blankLine();
        return this;
    }

    public FormattingHtmlWriter lineIf(boolean predicate) {
        buffer.lineIf(predicate);
        return this;
    }

    public FormattingHtmlWriter indent() {
        buffer.indent();
        return this;
    }

    public FormattingHtmlWriter unIndent() {
        buffer.unIndent();
        return this;
    }

    public FormattingHtmlWriter withCondIndent() {
        indentIndentingChildren = true;
        return this;
    }

    public FormattingHtmlWriter withCondLine() {
        lineOnChildText = true;
        return this;
    }

    public FormattingHtmlWriter openPre() {
        buffer.openPreFormatted();
        return this;
    }

    public FormattingHtmlWriter closePre() {
        buffer.closePreFormatted();
        return this;
    }

    public IOException getIOException() {
        return buffer.getIOException();
    }

    public FormattingHtmlWriter flush() {
        buffer.flush();
        return this;
    }

    public FormattingHtmlWriter flush(final int maxBlankLines) {
        buffer.flush(maxBlankLines);
        return this;
    }

    public FormattingHtmlWriter line(final Ref<Boolean> lineRef) {
        buffer.line(lineRef);
        return this;
    }

    public FormattingHtmlWriter lineIf(final Ref<Boolean> lineRef) {
        buffer.lineIf(lineRef);
        return this;
    }

    public FormattingHtmlWriter blankLineIf(final boolean predicate) {
        buffer.blankLineIf(predicate);
        return this;
    }

    public FormattingHtmlWriter blankLine(final int count) {
        buffer.blankLine(count);
        return this;
    }

    public int HtmlWriter() {
        return buffer.getLineCount();
    }

    public FormattingHtmlWriter openConditional(final ConditionalFormatter openFormatter) {
        buffer.openConditional(openFormatter);
        return this;
    }

    public FormattingHtmlWriter closeConditional(final ConditionalFormatter closeFormatter) {
        buffer.closeConditional(closeFormatter);
        return this;
    }
}
