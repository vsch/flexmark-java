package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.util.Escaping;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class HtmlWriter {
    private static final Map<String, String> NO_ATTRIBUTES = Collections.emptyMap();

    private final Appendable buffer;
    private final int indentSize;
    private final String indentSizePrefix;
    private NodeRendererContext context;

    private char lastChar = 0;
    private int indent;
    private String indentPrefix = "";
    private LinkedHashMap<String, String> currentAttributes;
    private boolean useAttributes = false;
    private int appendCount = 0;
    private boolean delayedIndent = false;
    private boolean indentIndentingChildren = false;

    public HtmlWriter(Appendable out) {
        this(out, 0);
    }

    public HtmlWriter(Appendable out, int indentSize) {
        this.buffer = out;
        this.indentSize = indentSize;

        StringBuilder sb = new StringBuilder(indentSize);
        for (int i = 0; i < indentSize; i++) sb.append(' ');
        indentSizePrefix = sb.toString();
    }

    void setContext(NodeRendererContext context) {
        this.context = context;
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
            currentAttributes = new LinkedHashMap<>();
        }
        currentAttributes.put(name, value);
        return this;
    }

    public HtmlWriter tag(String name) {
        return tag(name, null, false, false);
    }

    public HtmlWriter tag(String name, Map<String, String> attrs) {
        return tag(name, attrs, false, false);
    }

    public HtmlWriter tagVoid(String name) {
        return tag(name, null, true, false);
    }

    public HtmlWriter tagVoidLine(String name) {
        return tag(name, null, true, true);
    }

    public HtmlWriter withAttr() {
        useAttributes = true;
        return this;
    }

    public HtmlWriter withCondIndent() {
        indentIndentingChildren = true;
        return this;
    }

    public HtmlWriter tag(String name, Map<String, String> attrs, boolean voidElement, boolean voidWithLine) {
        if (useAttributes || attrs != null) {
            if (currentAttributes != null) {
                if (attrs != null) {
                    // assume attrs have already been extended
                    currentAttributes.putAll(attrs);
                    attrs = currentAttributes;
                } else {
                    attrs = context.extendRenderingNodeAttributes(currentAttributes);
                }
            } else if (attrs == null) {
                attrs = context.extendRenderingNodeAttributes(Collections.<String, String>emptyMap());
            }

            currentAttributes = null;
            useAttributes = false;
        }

        if (voidElement && voidWithLine) line();

        append("<");
        append(name);

        if (attrs != null && !attrs.isEmpty()) {
            for (Map.Entry<String, String> attrib : attrs.entrySet()) {
                append(" ");
                append(Escaping.escapeHtml(attrib.getKey(), true));
                append("=\"");
                append(Escaping.escapeHtml(attrib.getValue(), true));
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
        return tag(name, null, false, false, runnable);
    }

    public HtmlWriter tagIndent(String name, Runnable runnable) {
        return tag(name, null, true, false, runnable);
    }

    public HtmlWriter tagLine(String name, Runnable runnable) {
        return tag(name, null, false, true, runnable);
    }

    public HtmlWriter tag(String name, Map<String, String> attrs, boolean indentTag, boolean withLine, Runnable runnable) {
        int indentLevel = indent;
        int preIndentLevel = indent;

        boolean delayedIndent = this.delayedIndent;
        this.delayedIndent = false;

        if (delayedIndent) {
            delayedIndent = false;
            if (indentTag) {
                indent();
                preIndentLevel = indent;
            }
        }
        
        if (withLine || indentTag) line();
        tag(name, attrs, false, false);
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
        if (lastChar != 0 && lastChar != '\n') {
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

    protected void append(String s) {
        if (s.length() == 0) return;
        appendCount++;

        if (indentPrefix.length() > 0) {
            // convert \n to \n + indent except for the last one 
            // also if the last is \n then prefix indent size
            //if (delayedLine) {
            //    delayedLine = false;
            //    if (s.charAt(0) != '\n') append("\n");
            //}

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
