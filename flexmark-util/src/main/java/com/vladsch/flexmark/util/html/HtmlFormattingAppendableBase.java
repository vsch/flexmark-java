package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@SuppressWarnings("unchecked")
public class HtmlFormattingAppendableBase<T extends HtmlFormattingAppendableBase> implements HtmlFormattingAppendable {
    private LineFormattingAppendable out;

    private Attributes currentAttributes;
    private boolean indentOnFirstEol = false;
    private boolean lineOnChildText = false;
    private boolean withAttributes = false;
    private boolean suppressOpenTagLine = false;
    private boolean suppressCloseTagLine = false;
    private final Stack<String> myOpenTags = new Stack<>();

    public HtmlFormattingAppendableBase(LineFormattingAppendable other, boolean inheritIndent) {
        this.out = new LineFormattingAppendableImpl(other.getOptions());
        this.out.setIndentPrefix(other.getIndentPrefix());
    }

    public HtmlFormattingAppendableBase(int indentSize, int formatOptions) {
        this.out = new LineFormattingAppendableImpl(formatOptions);
        this.out.setIndentPrefix(RepeatedCharSequence.of(" ", indentSize).toString());
    }

    public boolean isSuppressOpenTagLine() {
        return suppressOpenTagLine;
    }

    public void setSuppressOpenTagLine(boolean suppressOpenTagLine) {
        this.suppressOpenTagLine = suppressOpenTagLine;
    }

    public boolean isSuppressCloseTagLine() {
        return suppressCloseTagLine;
    }

    public T setSuppressCloseTagLine(boolean suppressCloseTagLine) {
        this.suppressCloseTagLine = suppressCloseTagLine;
        return (T) this;
    }

    @Override
    public String toString() {
        return out.toString();
    }

    @Override
    public T openPre() {
        out.openPreFormatted(true);
        return (T) this;
    }

    @Override
    public T closePre() {
        out.closePreFormatted();
        return (T) this;
    }

    @Override
    public boolean inPre() {
        return out.isPreFormatted();
    }

    private boolean haveOptions(int options) {
        return (out.getOptions() & options) != 0;
    }

    @Override
    public T raw(CharSequence s) {
        out.append(s);
        return (T) this;
    }

    public T raw(CharSequence s, int count) {
        int i = count;
        while (i-- > 0) out.append(s);
        return (T) this;
    }

    @Override
    public T rawPre(CharSequence s) {
        // if previous pre-formatted did not have an EOL and this one does, need to transfer the EOL
        // to previous pre-formatted to have proper handling of first/last line, otherwise this opening
        // pre-formatted, blows away previous last line pre-formatted information
        if (out.getPendingEOL() == 0 && s.length() > 0 && s.charAt(0) == '\n') {
            out.line();
            s = s.subSequence(1, s.length());
        }

        out.openPreFormatted(true)
                .append(s)
                .closePreFormatted();
        return (T) this;
    }

    @Override
    public T rawIndentedPre(CharSequence s) {
        out.openPreFormatted(true)
                .append(s)
                .closePreFormatted();
        return (T) this;
    }

    @Override
    public T text(CharSequence s) {
        out.append(Escaping.escapeHtml(s, false));
        return (T) this;
    }

    @Override
    public T attr(CharSequence attrName, CharSequence value) {
        if (currentAttributes == null) {
            currentAttributes = new Attributes();
        }
        currentAttributes.addValue(attrName, value);
        return (T) this;
    }

    @Override
    public T attr(Attribute... attribute) {
        if (currentAttributes == null) {
            currentAttributes = new Attributes();
        }
        for (Attribute attr : attribute) {
            currentAttributes.addValue(attr.getName(), attr.getValue());
        }
        return (T) this;
    }

    @Override
    public T attr(Attributes attributes) {
        if (attributes != null && !attributes.isEmpty()) {
            if (currentAttributes == null) {
                currentAttributes = new Attributes(attributes);
            } else {
                currentAttributes.addValues(attributes);
            }
        }
        return (T) this;
    }

    @Override
    public T withAttr() {
        withAttributes = true;
        return (T) this;
    }

    @Override
    public Attributes getAttributes() {
        return currentAttributes;
    }

    @Override
    public T setAttributes(Attributes attributes) {
        currentAttributes = attributes;
        return (T) this;
    }

    @Override
    public T withCondLineOnChildText() {
        lineOnChildText = true;
        return (T) this;
    }

    @Override
    public T withCondIndent() {
        indentOnFirstEol = true;
        return (T) this;
    }

    @Override
    public T tag(CharSequence tagName) {
        return tag(tagName, false);
    }

    @Override
    public T tag(CharSequence tagName, Runnable runnable) {
        return tag(tagName, false, false, runnable);
    }

    @Override
    public T tagVoid(CharSequence tagName) {
        return tag(tagName, true);
    }

    protected String getOpenTagText() {
        return Utils.splice(myOpenTags, ", ", true);
    }

    protected void pushTag(CharSequence tagName) {
        myOpenTags.push(String.valueOf(tagName));
    }

    protected void popTag(CharSequence tagName) {
        if (myOpenTags.isEmpty()) throw new IllegalStateException("Close tag '" + tagName + "' with no tags open");
        String openTag = myOpenTags.peek();
        if (!openTag.equals(String.valueOf(tagName)))
            throw new IllegalStateException("Close tag '" + tagName + "' does not match '" + openTag + "' in " + getOpenTagText());
        myOpenTags.pop();
    }

    protected void tagOpened(CharSequence tagName) {
        pushTag(tagName);
    }

    protected void tagClosed(CharSequence tagName) {
        popTag(tagName);
    }

    @Override
    public Stack<String> getOpenTags() {
        return myOpenTags;
    }

    @Override
    public List<String> getOpenTagsAfterLast(CharSequence latestTag) {
        if (myOpenTags.isEmpty()) return Collections.EMPTY_LIST;

        List<String> tagList = new ArrayList<>(myOpenTags);
        int iMax = tagList.size();
        int lastPos = iMax;
        String lastTag = String.valueOf(latestTag);
        for (int i = iMax; i-- > 0; ) {
            if (tagList.get(i).equals(lastTag)) {
                lastPos = i + 1;
                break;
            }
        }
        return tagList.subList(lastPos, iMax);
    }

    @Override
    public T tag(CharSequence tagName, boolean voidElement) {
        if (tagName.length() == 0 || tagName.charAt(0) == '/') return closeTag(tagName);

        Attributes attributes = null;

        if (withAttributes) {
            attributes = currentAttributes;
            currentAttributes = null;
            withAttributes = false;
        }

        out.append("<");
        out.append(tagName);

        if (attributes != null && !attributes.isEmpty()) {
            for (Attribute attribute : attributes.values()) {
                CharSequence attributeValue = attribute.getValue();

                if (attribute.isNonRendering()) continue;

                out.append(" ");
                out.append(Escaping.escapeHtml(attribute.getName(), true));
                out.append("=\"");
                out.append(Escaping.escapeHtml(attributeValue, true));
                out.append("\"");
            }
        }

        if (voidElement) {
            out.append(" />");
        } else {
            out.append(">");
            tagOpened(tagName);
        }

        return (T) this;
    }

    @Override
    public T closeTag(CharSequence tagName) {
        if (tagName.length() == 0) throw new IllegalStateException("closeTag called with tag:'" + tagName + "'");

        if (tagName.charAt(0) == '/') {
            out.append("<").append(tagName).append(">");
            tagClosed(tagName.subSequence(1, tagName.length()));
        } else {
            out.append("</").append(tagName).append(">");
            tagClosed(tagName);
        }
        return (T) this;
    }

    @Override
    public T tag(CharSequence tagName, boolean withIndent, boolean withLine, Runnable runnable) {
        boolean isLineOnChildText = lineOnChildText;
        boolean isIndentOnFirstEol = indentOnFirstEol;
        lineOnChildText = false;
        indentOnFirstEol = false;

        if (withIndent && !suppressOpenTagLine) {
            out.line();
        }

        tag(tagName, false);

        if (withIndent && !isIndentOnFirstEol) out.indent();

        if ((out.getOptions() & PASS_THROUGH) != 0) {
            runnable.run();
        } else {
            boolean[] hadConditionalIndent = new boolean[] { false };
            Runnable indentOnFirstEol = () -> hadConditionalIndent[0] = true;

            if (isLineOnChildText) out.setLineOnFirstText();

            if (isIndentOnFirstEol) {
                out.addIndentOnFirstEOL(indentOnFirstEol);
            }

            runnable.run();

            if (isLineOnChildText) out.clearLineOnFirstText();

            if (hadConditionalIndent[0]) {
                out.unIndentNoEol();
            } else {
                out.removeIndentOnFirstEOL(indentOnFirstEol);
            }
        }

        if (withIndent && !isIndentOnFirstEol) out.unIndent();

        // don't rely on unIndent() doing a line, it will only do so if there was text since indent()
        if (withLine && !suppressCloseTagLine) out.line();

        closeTag(tagName);

        if (withIndent && !suppressCloseTagLine) {
            out.line();
        }

        return (T) this;
    }

    @Override
    public T tagVoidLine(CharSequence tagName) {
        lineIf(!suppressOpenTagLine).tagVoid(tagName).lineIf(!suppressCloseTagLine);
        return (T) this;
    }

    @Override
    public T tagLine(CharSequence tagName) {
        lineIf(!suppressOpenTagLine).tag(tagName).lineIf(!suppressCloseTagLine);
        return (T) this;
    }

    @Override
    public T tagLine(CharSequence tagName, boolean voidElement) {
        lineIf(!suppressOpenTagLine).tag(tagName, voidElement).lineIf(!suppressCloseTagLine);
        return (T) this;
    }

    @Override
    public T tagLine(CharSequence tagName, Runnable runnable) {
        lineIf(!suppressOpenTagLine).tag(tagName, false, false, runnable).lineIf(!suppressCloseTagLine);
        return (T) this;
    }

    @Override
    public T tagIndent(CharSequence tagName, Runnable runnable) {
        tag(tagName, true, false, runnable);
        return (T) this;
    }

    @Override
    public T tagLineIndent(CharSequence tagName, Runnable runnable) {
        tag(tagName, true, true, runnable);
        return (T) this;
    }

    // delegated to LineFormattingAppendable
    // @formatter:off
    @Override public boolean isPendingSpace()                                                                                           { return out.isPendingSpace(); }
    @Override public boolean isPreFormatted()                                                                                           { return out.isPreFormatted(); }
    @Override public boolean isPreFormattedLine(int line)                                                                               { return out.isPreFormattedLine(line); }
    @Override public CharSequence getIndentPrefix()                                                                                     { return out.getIndentPrefix(); }
    @Override public CharSequence getPrefix()                                                                                           { return out.getPrefix(); }
    @Override public int column()                                                                                                       { return out.column(); }
    @Override public int getLineCount()                                                                                                 { return out.getLineCount(); }
    @Override public int getOptions()                                                                                                   { return out.getOptions(); }
    @Override public int getPendingEOL()                                                                                                { return out.getPendingEOL(); }
    @Override public int getPendingSpace()                                                                                              { return out.getPendingSpace(); }
    @Override public int offset()                                                                                                       { return out.offset(); }
    @Override public int offsetWithPending()                                                                                            { return out.offsetWithPending(); }
    @Override public int textOnlyOffset()                                                                                               { return out.textOnlyOffset(); }
    @Override public int textOnlyOffsetWithPending()                                                                                    { return out.textOnlyOffsetWithPending(); }
    @Override public List<BasedSequence> getLinePrefixes(int startLine, int endLine)                                                    { return out.getLinePrefixes(startLine, endLine); }
    @Override public List<CharSequence> getLineContents(int startLine, int endLine)                                                     { return out.getLineContents(startLine, endLine); }
    @Override public List<CharSequence> getLines(int startLine, int endLine)                                                            { return out.getLines(startLine, endLine); }
    @Override public String toString(int maxBlankLines)                                                                                 { return out.toString(maxBlankLines); }
    @Override public T addIndentOnFirstEOL(Runnable runnable)                                                                           { out.addIndentOnFirstEOL(runnable);  return (T)this; }
    @Override public T addLine()                                                                                                        { out.addLine(); return (T)this; }
    @Override public T addPrefix(CharSequence prefix)                                                                                   { out.addPrefix(prefix); return (T)this; }
    @Override public T addPrefix(CharSequence prefix, boolean afterEol)                                                                 { out.addPrefix(prefix, afterEol);  return (T)this; }
    @Override public T append(char c)                                                                                                   { out.append(c); return (T)this; }
    @Override public T append(CharSequence csq)                                                                                         { out.append(csq); return (T)this; }
    @Override public T append(CharSequence csq, int start, int end)                                                                     { out.append(csq, start, end); return (T)this; }
    @Override public T append(LineFormattingAppendable lines, int startLine, int endLine)                                               { out.append(lines, startLine, endLine);  return (T)this; }
    @Override public T appendTo(Appendable out, int maxBlankLines, CharSequence prefix, int startLine, int endLine) throws IOException  { this.out.appendTo(out, maxBlankLines, prefix, startLine, endLine);  return (T)this; }
    @Override public T blankLine()                                                                                                      { out.blankLine(); return (T)this; }
    @Override public T blankLine(int count)                                                                                             { out.blankLine(count); return (T)this; }
    @Override public T blankLineIf(boolean predicate)                                                                                   { out.blankLineIf(predicate); return (T)this; }
    @Override public T closePreFormatted()                                                                                              { out.closePreFormatted(); return (T)this; }
    @Override public T indent()                                                                                                         { out.indent(); return (T)this; }
    @Override public T line()                                                                                                           { out.line(); return (T)this; }
    @Override public T lineIf(boolean predicate)                                                                                        { out.lineIf(predicate); return (T)this; }
    @Override public T lineOnFirstText(boolean value)                                                                                   { out.lineOnFirstText(value);  return (T)this; }
    @Override public T lineWithTrailingSpaces(int count)                                                                                { out.lineWithTrailingSpaces(count);  return (T)this; }
    @Override public T openPreFormatted()                                                                                               { out.openPreFormatted();  return (T)this; }
    @Override public T openPreFormatted(boolean keepIndent)                                                                             { out.openPreFormatted(true); return (T)this; }
    @Override public T popPrefix()                                                                                                      { out.popPrefix(); return (T)this; }
    @Override public T popPrefix(boolean afterEol)                                                                                      { out.popPrefix(afterEol); return (T)this; }
    @Override public T prefixLines(CharSequence prefix, boolean addAfterLinePrefix, int startLine, int endLine)                         { out.prefixLines(prefix, addAfterLinePrefix, startLine, endLine);  return (T)this; }
    @Override public T prefixLines(CharSequence prefix, int startLine, int endLine)                                                     { out.prefixLines(prefix, startLine, endLine);  return (T)this; }
    @Override public T pushPrefix()                                                                                                     { out.pushPrefix(); return (T)this; }
    @Override public T removeIndentOnFirstEOL(Runnable runnable)                                                                        { out.removeIndentOnFirstEOL(runnable);  return (T)this; }
    @Override public T removeLines(int startLine, int endLine)                                                                          { out.removeLines(startLine, endLine);  return (T)this; }
    @Override public T repeat(char c, int count)                                                                                        { out.repeat(c, count); return (T)this; }
    @Override public T repeat(CharSequence csq, int count)                                                                              { out.repeat(csq, count); return (T)this; }
    @Override public T repeat(CharSequence csq, int start, int end, int count)                                                          { out.repeat(csq, start, end, count); return (T)this; }
    @Override public T setIndentPrefix(CharSequence prefix)                                                                             { out.setIndentPrefix(prefix); return (T)this; }
    @Override public T setOptions(int options)                                                                                          { out.setOptions(options); return (T)this; }
    @Override public T setPrefix(CharSequence prefix)                                                                                   { out.setPrefix(prefix); return (T)this; }
    @Override public T setPrefix(CharSequence prefix, boolean afterEol)                                                                 { out.setPrefix(prefix, afterEol); return (T)this; }
    @Override public T unIndent()                                                                                                       { out.unIndent(); return (T)this; }
    @Override public T unIndentNoEol()                                                                                                  { out.unIndentNoEol();  return (T)this; }
    // @formatter:on
}
