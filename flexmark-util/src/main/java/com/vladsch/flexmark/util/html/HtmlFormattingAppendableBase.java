package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Ref;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@SuppressWarnings("unchecked")
public class HtmlFormattingAppendableBase<T extends HtmlFormattingAppendableBase> implements HtmlFormattingAppendable {
    private final FormattingAppendable out;

    private Attributes currentAttributes;
    private boolean indentIndentingChildren = false;
    private boolean lineOnChildText = false;
    private boolean withAttributes = false;
    private boolean suppressOpenTagLine = false;
    private boolean suppressCloseTagLine = false;
    private final Stack<String> myOpenTags = new Stack<String>();

    public HtmlFormattingAppendableBase(FormattingAppendable other, Appendable out, boolean inheritIndent) {
        this(out, inheritIndent ? other.getIndentPrefix().length() : 0, other.getOptions());
    }

    public HtmlFormattingAppendableBase(Appendable out, int indentSize, final int formatOptions) {
        this.out = new FormattingAppendableImpl(out, formatOptions);
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
        out.openPreFormatted(true)
                .append(s)
                .closePreFormatted();
        return (T) this;
    }

    @Override
    public T rawIndentedPre(CharSequence s) {
        CharSequence prefix = out.getPrefix();
        out.setPrefix(out.getTotalIndentPrefix());
        out.openPreFormatted(false)
                .append(s)
                .closePreFormatted();
        out.setPrefix(prefix);
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
    public T withCondLine() {
        lineOnChildText = true;
        return (T) this;
    }

    @Override
    public T withCondIndent() {
        indentIndentingChildren = true;
        return (T) this;
    }

    @Override
    public T tag(CharSequence tagName) {
        return tag(tagName, false);
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
    public List<String> getOpenTagsAfterLast(final CharSequence latestTag) {
        if (myOpenTags.isEmpty()) return Collections.EMPTY_LIST;

        List<String> tagList = new ArrayList<String>(myOpenTags);
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
    public T closeTag(final CharSequence tagName) {
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
    public T tag(CharSequence tagName, final boolean withIndent, final boolean withLine, Runnable runnable) {
        if (withIndent && !suppressOpenTagLine) {
            out.willIndent();
            out.line();
        }

        tag(tagName, false);

        if (withIndent) out.indent();

        if ((out.getOptions() & PASS_THROUGH) != 0) {
            runnable.run();
        } else {
            final boolean isLineOnChildText = lineOnChildText;
            final boolean isIndentIndentingChildren = indentIndentingChildren;
            lineOnChildText = false;
            indentIndentingChildren = false;

            if (isLineOnChildText || isIndentIndentingChildren) {
                out.openConditional(new ConditionalFormatter() {
                    @Override
                    public void apply(final boolean firstAppend, final boolean onIndent, final boolean onLine, final boolean onText) {
                        if (onIndent) {
                            if (isIndentIndentingChildren) out.indent();
                            else out.line();
                        } else if (firstAppend) {
                            if (isLineOnChildText) {
                                out.line();
                            } else if (onLine) {
                                // don't suppress the child new line
                                out.line();
                            }
                        }
                    }
                });
            }

            runnable.run();

            if (isLineOnChildText || isIndentIndentingChildren) {
                out.closeConditional(new ConditionalFormatter() {
                    @Override
                    public void apply(final boolean firstAppend, final boolean onIndent, final boolean onLine, final boolean onText) {
                        if (onIndent) {
                            if (isIndentIndentingChildren) out.unIndent();
                            //else buffer.line();
                        } else if (onText && isLineOnChildText) {
                            out.line();
                        }
                    }
                });
            }
        }

        if (withIndent) out.unIndent();

        // don't rely on unIndent() doing a line, it will only do so if there was text since indent()
        if (withLine && !suppressCloseTagLine) out.line();

        closeTag(tagName);

        if (withIndent && !suppressCloseTagLine) out.line();

        return (T) this;
    }

    @Override
    public T tagVoidLine(final CharSequence tagName) {
        lineIf(!suppressOpenTagLine).tagVoid(tagName).lineIf(!suppressCloseTagLine);
        return (T) this;
    }

    @Override
    public T tagLine(final CharSequence tagName) {
        lineIf(!suppressOpenTagLine).tag(tagName).lineIf(!suppressCloseTagLine);
        return (T) this;
    }

    @Override
    public T tagLine(final CharSequence tagName, final boolean voidElement) {
        lineIf(!suppressOpenTagLine).tag(tagName, voidElement).lineIf(!suppressCloseTagLine);
        return (T) this;
    }

    @Override
    public T tagLine(final CharSequence tagName, final Runnable runnable) {
        lineIf(!suppressOpenTagLine).tag(tagName, false, false, runnable).lineIf(!suppressCloseTagLine);
        return (T) this;
    }

    @Override
    public T tagIndent(final CharSequence tagName, final Runnable runnable) {
        tag(tagName, true, false, runnable);
        return (T) this;
    }

    @Override
    public T tagLineIndent(final CharSequence tagName, final Runnable runnable) {
        tag(tagName, true, true, runnable);
        return (T) this;
    }

    // delegated to FormattingAppendable

    @Override
    public Appendable getAppendable() { return out.getAppendable(); }

    @Override
    public int getOptions() { return out.getOptions(); }

    @Override
    public T setOptions(final int options) {
        out.setOptions(options);
        return (T) this;
    }

    public int getModCount() {
        return out.getModCount();
    }

    public boolean isPreFormatted() {
        return out.isPreFormatted();
    }

    public T line() {
        out.line();
        return (T) this;
    }

    public T addLine() {
        out.addLine();
        return (T) this;
    }

    @Override
    public int getPushedPrefixCount() {
        return out.getPushedPrefixCount();
    }

    public T blankLine() {
        out.blankLine();
        return (T) this;
    }

    public T blankLine(final int count) {
        out.blankLine(count);
        return (T) this;
    }

    public T blankLineIf(final boolean predicate) {
        out.blankLineIf(predicate);
        return (T) this;
    }

    public T lineIf(boolean predicate) {
        out.lineIf(predicate);
        return (T) this;
    }

    public T indent() {
        out.indent();
        return (T) this;
    }

    public T willIndent() {
        out.willIndent();
        return (T) this;
    }

    public T unIndent() {
        out.unIndent();
        return (T) this;
    }

    public IOException getIOException() {
        return out.getIOException();
    }

    @Override
    public T append(final CharSequence csq) {
        out.append(csq);
        return (T) this;
    }

    @Override
    public T append(final CharSequence csq, final int start, final int end) {
        out.append(csq, start, end);
        return (T) this;
    }

    @Override
    public T append(final char c) {
        out.append(c);
        return (T) this;
    }

    @Override
    public String getText() {
        return out.getText();
    }

    @Override
    public String getText(int maxBlankLines) {
        return out.getText(maxBlankLines);
    }

    public T flush() {
        out.flush();
        return (T) this;
    }

    public T flushWhitespaces() {
        out.flush();
        return (T) this;
    }

    public T flush(final int maxBlankLines) {
        out.flush(maxBlankLines);
        return (T) this;
    }

    @Override
    public CharSequence getIndentPrefix() { return out.getIndentPrefix(); }

    @Override
    public T setIndentPrefix(final CharSequence prefix) {
        out.setIndentPrefix(prefix);
        return (T) this;
    }

    @Override
    public CharSequence getPrefix() { return out.getPrefix(); }

    @Override
    public T setPrefix(final CharSequence prefix) {
        out.setPrefix(prefix);
        return (T) this;
    }

    @Override
    public T addPrefix(final CharSequence prefix) {
        out.addPrefix(prefix);
        return (T) this;
    }

    @Override
    public T pushPrefix() {
        out.pushPrefix();
        return (T) this;
    }

    @Override
    public T popPrefix() {
        out.popPrefix();
        return (T) this;
    }

    @Override
    public T addAfterEolRunnable(final int atPendingEOL, final Runnable runnable) {
        out.addAfterEolRunnable(atPendingEOL, runnable);
        return (T) this;
    }

    @Override
    public CharSequence getTotalIndentPrefix() {
        return out.getTotalIndentPrefix();
    }

    public T line(final Ref<Boolean> lineRef) {
        out.line(lineRef);
        return (T) this;
    }

    public T lineIf(final Ref<Boolean> lineRef) {
        out.lineIf(lineRef);
        return (T) this;
    }

    @Override
    public T repeat(final char c, final int count) {
        out.repeat(c, count);
        return (T) this;
    }

    @Override
    public T repeat(final CharSequence csq, final int count) {
        out.repeat(csq, count);
        return (T) this;
    }

    @Override
    public T repeat(final CharSequence csq, final int start, final int end, final int count) {
        out.repeat(csq, start, end, count);
        return (T) this;
    }

    @Override
    public boolean isPendingSpace() {
        return out.isPendingSpace();
    }

    @Override
    public int getPendingSpace() {
        return out.getPendingSpace();
    }

    @Override
    public boolean isPendingEOL() {
        return out.isPendingSpace();
    }

    @Override
    public int getPendingEOL() {
        return out.getPendingEOL();
    }

    @Override
    public void setPendingEOL(final int pendingEOL) {
        out.setPendingEOL(pendingEOL);
    }

    @Override
    public int getIndent() { return out.getIndent(); }

    @Override
    public T setIndentOffset(final int indentOffset) {
        out.setIndentOffset(indentOffset);
        return (T) this;
    }

    public int getLineCount() {
        return out.getLineCount();
    }

    @Override
    public T lastOffset(final Ref<Integer> refOffset) {
        out.lastOffset(refOffset);
        return (T) this;
    }

    @Override
    public int lastOffset() { return out.lastOffset(); }

    @Override
    public int offset() { return out.offset(); }

    @Override
    public int offsetWithPending() { return out.offsetWithPending(); }

    @Override
    public int column() { return out.column(); }

    @Override
    public int columnWith(final CharSequence csq, final int start, final int end) { return out.columnWith(csq, start, end); }

    @Override
    public T openPreFormatted(final boolean keepIndent) {
        out.openPreFormatted(true);
        return (T) this;
    }

    @Override
    public T closePreFormatted() {
        out.closePreFormatted();
        return (T) this;
    }

    public T openConditional(final ConditionalFormatter openFormatter) {
        out.openConditional(openFormatter);
        return (T) this;
    }

    public T closeConditional(final ConditionalFormatter closeFormatter) {
        out.closeConditional(closeFormatter);
        return (T) this;
    }
}
