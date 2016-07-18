package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.options.Attribute;
import com.vladsch.flexmark.internal.util.options.Attributes;

import java.io.IOException;

public class HtmlWriter {
    private final Appendable buffer;
    private final int indentSize;
    private final String indentSizePrefix;
    private NodeRendererContext context;

    private char lastChar = 0;
    private int indent;
    private String indentPrefix = "";
    private Attributes currentAttributes;
    //private int appendCount = 0;
    private boolean delayedIndent = false;
    private boolean delayedEOL = false;
    private boolean indentIndentingChildren = false;
    private boolean lineOnChildText = false;
    private int preNesting = 0;
    private AttributablePart useAttributes = null;

    public HtmlWriter(Appendable out) {
        this(out, 0);
    }

    public HtmlWriter(HtmlWriter other, Appendable out, boolean inheritIndent) {
        this(out, other.indentSize);

        if (inheritIndent) {
            indent = other.indent;
            indentPrefix = other.indentPrefix;
        }
    }

    public HtmlWriter(Appendable out, int indentSize) {
        this.buffer = out;
        this.indentSize = indentSize;

        StringBuilder sb = new StringBuilder(indentSize);
        for (int i = 0; i < indentSize; i++) sb.append(' ');
        indentSizePrefix = sb.toString();
    }
    
    boolean inPre() {
        return preNesting > 0;
    }

    void setContext(NodeRendererContext context) {
        this.context = context;
    }

    public NodeRendererContext getContext() {
        return context;
    }

    public int getIndentSize() {
        return indentSize;
    }

    public HtmlWriter raw(String s) {
        append(s);
        return this;
    }

    public HtmlWriter text(String text) {
        append(Escaping.escapeHtml(text, false));
        return this;
    }

    public HtmlWriter attr(String name, String value) {
        if (currentAttributes == null) {
            currentAttributes = new Attributes();
        }
        currentAttributes.replaceValue(name, value);
        return this;
    }

    public HtmlWriter attr(Attribute attribute) {
        if (currentAttributes == null) {
            currentAttributes = new Attributes();
        }
        currentAttributes.replaceValue(attribute.getName(), attribute.getValue());
        return this;
    }

    public HtmlWriter attr(Attributes attributes) {
        if (!attributes.isEmpty()) {
            if (currentAttributes == null) {
                currentAttributes = new Attributes(attributes);
            } else {
                currentAttributes.replaceValues(attributes);
            }
        }
        return this;
    }

    public HtmlWriter tag(String name) {
        return tag(name, false, false);
    }

    public HtmlWriter tagVoid(String name) {
        return tag(name, true, false);
    }

    public HtmlWriter tagVoidLine(String name) {
        return tag(name, true, true);
    }

    public HtmlWriter withAttr() {
        return withAttr(AttributablePart.NODE);
    }

    public HtmlWriter withAttr(AttributablePart part) {
        useAttributes = part;
        return this;
    }

    public HtmlWriter withAttr(LinkStatus status) {
        attr(Attribute.LINK_STATUS, status.getName());
        return withAttr(AttributablePart.LINK);
    }

    public HtmlWriter withAttr(ResolvedLink resolvedLink) {
        return withAttr(resolvedLink.getStatus());
    }

    public HtmlWriter withCondIndent() {
        indentIndentingChildren = true;
        return this;
    }

    public HtmlWriter withCondLine() {
        lineOnChildText = true;
        return this;
    }

    public HtmlWriter tag(String name, boolean voidElement, boolean voidWithLine) {
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
            for (Attribute attribute : attributes.values()) {
                if (attribute.isNonRendering()) continue;
                append(" ");
                append(Escaping.escapeHtml(attribute.getName(), true));
                append("=\"");
                append(Escaping.escapeHtml(attribute.getValue(), true));
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

    public HtmlWriter tag(String name, Runnable runnable) {
        return tag(name, false, false, runnable);
    }

    public HtmlWriter tagIndent(String name, Runnable runnable) {
        return tag(name, !indentIndentingChildren, false, runnable);
    }

    public HtmlWriter tagLine(String name, Runnable runnable) {
        return tag(name, false, !lineOnChildText, runnable);
    }

    public HtmlWriter tag(String name, boolean indentTag, boolean withLine, Runnable runnable) {
        int indentLevel = indent;
        int preIndentLevel = indent;

        boolean delayedIndent = this.delayedIndent;
        this.delayedIndent = false;

        if (delayedIndent) {
            if (indentTag) {
                indent();
                preIndentLevel = indent;
            }
        }

        if (withLine || indentTag) line();
        tag(name, false, false);

        if (lineOnChildText) {
            delayedEOL = true;
            lineOnChildText = false;
        }

        if (indentTag) indent();

        if (indentIndentingChildren) {
            this.delayedIndent = true;
            indentIndentingChildren = false;
        }

        runnable.run();

        // if not used then not needed
        this.delayedIndent = false;

        boolean hadPreIndent = preIndentLevel < indent;
        if (hadPreIndent) {
            while (preIndentLevel < indent) unIndent();
        }

        // if not used then not needed
        this.delayedEOL = false;

        append("</");
        append(name);
        append(">");

        boolean hadIndent = indentLevel < indent;
        if (hadIndent) {
            while (indentLevel < indent) unIndent();
        }

        if (hadIndent || hadPreIndent || withLine) line();

        return this;
    }

    public HtmlWriter line() {
        if (lastChar != 0 && lastChar != '\n' && !delayedEOL) {
            append("\n");
        }
        return this;
    }

    public HtmlWriter indent() {
        line();
        indent++;
        indentPrefix += indentSizePrefix;
        return this;
    }

    public HtmlWriter unIndent() {
        if (indent > 0) {
            line();
            indent--;
            if (indent * indentSize > 0) indentPrefix = indentPrefix.substring(0, indent * indentSize);
            else indentPrefix = "";
        }
        return this;
    }

    public HtmlWriter openPre() {
        preNesting++;
        return this;
    }

    public HtmlWriter closePre() {
        if (preNesting <= 0) {
            throw new IllegalStateException("Close <pre> context with none open");
        }
        preNesting--;
        return this;
    }

    protected void append(String s) {
        if (s.length() == 0) return;
        //appendCount++;

        if (delayedEOL) {
            delayedEOL = false;
            if (s.charAt(0) != '\n') append("\n");
        }

        if (indentPrefix.length() > 0 && preNesting <= 0) {
            // convert \n to \n + indent except for the last one
            // also if the last is \n then prefix indent size

            try {
                int lastPos = 0;
                boolean lastWasEOL = lastChar == '\n';

                while (lastPos < s.length()) {
                    int pos = s.indexOf('\n', lastPos);
                    if (pos < 0 || pos == s.length() - 1) {
                        if (lastWasEOL) buffer.append(indentPrefix);
                        buffer.append(s.substring(lastPos));
                        break;
                    }

                    if (pos > lastPos) {
                        if (lastWasEOL) buffer.append(indentPrefix);
                        buffer.append(s.substring(lastPos, pos));
                    }

                    buffer.append("\n");
                    lastWasEOL = true;
                    lastPos = pos + 1;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int length = s.length();
            if (length != 0) {
                lastChar = s.charAt(length - 1);
            }
        } else {
            try {
                buffer.append(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int length = s.length();
            if (length != 0) {
                lastChar = s.charAt(length - 1);
            }
        }
    }
}
