package com.vladsch.flexmark.html;

import com.vladsch.flexmark.internal.util.Escaping;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class HtmlWriter {
    private static final Map<String, String> NO_ATTRIBUTES = Collections.emptyMap();

    private final Appendable buffer;
    private char lastChar = 0;
    private int indent;
    private int indentSize = 2;
    private String indentPrefix = "";
    private String indentSizePrefix = "  ";
    private LinkedHashMap<String, String> currentAttributes;

    public HtmlWriter(Appendable out) {
        this.buffer = out;
    }

    public int getIndentSize() {
        return indentSize;
    }

    public void setIndentSize(int indentSize) {
        this.indentSize = indentSize;
        if (indentSizePrefix.length() != indentSize) {
            StringBuilder sb = new StringBuilder(indentSize);
            for (int i = 0; i < indentSize; i++) sb.append(' ');
            indentSizePrefix = sb.toString();

            if (indent > 0) {
                StringBuilder sbi = new StringBuilder(indent * indentSize);
                for (int i = 0; i < indent; i++) sbi.append(sb);
                indentPrefix = sbi.toString();
            }
        }
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
        return tag(name, NO_ATTRIBUTES);
    }

    public HtmlWriter tag(String name, Map<String, String> attrs) {
        return tag(name, attrs, false);
    }

    public HtmlWriter tag(String name, boolean voidElement) {
        return tag(name, null, voidElement);
    }

    public HtmlWriter tag(String name, Map<String, String> attrs, boolean voidElement) {
        if (currentAttributes != null) {
            if (attrs != null) {
                currentAttributes.putAll(attrs);
            }
            attrs = currentAttributes;
        }

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
            append(" /");
        }

        append(">");
        currentAttributes = null;

        return this;
    }

    public HtmlWriter tag(String name, Runnable runnable) {
        return tag(name, null, runnable);
    }

    public HtmlWriter tag(String name, Map<String, String> attrs, Runnable runnable) {
        tag(name, attrs, false);
        int indentLevel = indent;
        runnable.run();
        while (indentLevel < indent) unIndent();
        append("</");
        append(name);
        append(">");
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
        if (indentPrefix.length() > 0) {
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
