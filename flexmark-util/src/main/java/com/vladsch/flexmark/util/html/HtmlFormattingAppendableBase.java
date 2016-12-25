package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.*;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequence;

import java.io.IOException;

public abstract class HtmlFormattingAppendableBase<T extends HtmlFormattingAppendableBase> implements HtmlFormattingAppendable {
    private final FormattingAppendable out;

    private Attributes currentAttributes;
    private boolean indentIndentingChildren = false;
    private boolean lineOnChildText = false;
    private boolean withAttributes = false;

    public HtmlFormattingAppendableBase(Appendable out) {
        this(out, 0, false);
    }

    public HtmlFormattingAppendableBase(FormattingAppendable other, Appendable out, boolean inheritIndent) {
        this(out, inheritIndent ? other.getIndentPrefix().length() : 0, false);
    }

    public HtmlFormattingAppendableBase(Appendable out, int indentSize, final boolean allFormatOptions) {
        this.out = new FormattingAppendableImpl(out, allFormatOptions);
        this.out.setIndentPrefix(RepeatedCharSequence.of(" ", indentSize).toString());
    }

    public HtmlFormattingAppendableBase(Appendable out, int indentSize, final int formatOptions) {
        this.out = new FormattingAppendableImpl(out, formatOptions);
        this.out.setIndentPrefix(RepeatedCharSequence.of(" ", indentSize).toString());
    }

    abstract protected T chaining();

    @Override
    public T openPre() {
        out.openPreFormatted(true);
        return chaining();
    }

    @Override
    public T closePre() {
        out.closePreFormatted();
        return chaining();
    }

    @Override
    public boolean inPre() {
        return out.isPreFormatted();
    }

    private boolean haveOptions(int options) {
        return (out.getOptions() & options) != 0;
    }

    @Override
    public T raw(String s) {
        out.append(s);
        return chaining();
    }

    @Override
    public T rawPre(String s) {
        out.openPreFormatted(true)
                .append(s)
                .closePreFormatted();
        return chaining();
    }

    @Override
    public T rawIndentedPre(String s) {
        CharSequence prefix = out.getPrefix();
        out.setPrefix(out.getTotalIndentPrefix());
        out.openPreFormatted(false)
                .append(s)
                .closePreFormatted();
        out.setPrefix(prefix);
        return chaining();
    }

    @Override
    public T text(String text) {
        out.append(Escaping.escapeHtml(text, false));
        return chaining();
    }

    @Override
    public T attr(String name, String value) {
        if (currentAttributes == null) {
            currentAttributes = new Attributes();
        }
        currentAttributes.replaceValue(name, value);
        return chaining();
    }

    @Override
    public T attr(Attribute attribute) {
        if (currentAttributes == null) {
            currentAttributes = new Attributes();
        }
        currentAttributes.replaceValue(attribute.getName(), attribute.getValue());
        return chaining();
    }

    @Override
    public T attr(Attributes attributes) {
        if (!attributes.isEmpty()) {
            if (currentAttributes == null) {
                currentAttributes = new Attributes(attributes);
            } else {
                currentAttributes.replaceValues(attributes);
            }
        }
        return chaining();
    }

    @Override
    public HtmlFormattingAppendable withAttr() {
        withAttributes = true;
        return chaining();
    }

    @Override
    public Attributes getAttributes() {
        return currentAttributes;
    }

    @Override
    public T setAttributes(Attributes attributes) {
        currentAttributes = attributes;
        return chaining();
    }

    @Override
    public T withCondLine() {
        lineOnChildText = true;
        return chaining();
    }

    @Override
    public T withCondIndent() {
        indentIndentingChildren = true;
        return chaining();
    }

    @Override
    public T tag(String name) {
        return tag(name, false);
    }

    @Override
    public T tagVoid(String name) {
        return tag(name, true);
    }

    @Override
    public T tag(String name, boolean voidElement) {
        Attributes attributes = null;

        if (withAttributes) {
            attributes = currentAttributes;
            currentAttributes = null;
            withAttributes = false;
        }

        out.append("<");
        out.append(name);

        if (attributes != null && !attributes.isEmpty()) {
            for (Attribute attribute : attributes.values()) {
                String attributeValue = attribute.getValue();

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
        }

        return chaining();
    }

    @Override
    public T tag(String name, final boolean withIndent, final boolean withLine, Runnable runnable) {
        if (withIndent) {
            out.willIndent();
            out.line();
        }

        tag(name, false);

        if (withIndent) out.indent();

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

        if (withIndent) out.unIndent();

        // don't rely on unIndent() doing a line, it will only do so if there was text since indent()
        if (withLine) out.line();

        out.append("</");
        out.append(name);
        out.append(">");

        if (withIndent) line();

        return chaining();
    }

    @Override
    public T tagVoidLine(final String name) {
        line().tagVoid(name).line();
        return chaining();
    }

    @Override
    public T tagLine(final String name) {
        line().tag(name).line();
        return chaining();
    }

    @Override
    public T tagLine(final String name, final boolean voidElement) {
        line().tag(name, voidElement).line();
        return chaining();
    }

    @Override
    public T tagLine(final String name, final Runnable runnable) {
        line().tag(name, false, false, runnable).line();
        return chaining();
    }

    @Override
    public T tagIndent(final String name, final Runnable runnable) {
        tag(name, true, false, runnable);
        return chaining();
    }

    @Override
    public T tagLineIndent(final String name, final Runnable runnable) {
        tag(name, true, true, runnable);
        return chaining();
    }

    // delegated to FormattingAppendable

    @Override
    public int getOptions() { return out.getOptions(); }

    @Override
    public T setOptions(final int options) {
        out.setOptions(options);
        return chaining();
    }

    public int getModCount() {
        return out.getModCount();
    }

    public boolean isPreFormatted() {
        return out.isPreFormatted();
    }

    public T line() {
        out.line();
        return chaining();
    }

    public T blankLine() {
        out.blankLine();
        return chaining();
    }

    public T lineIf(boolean predicate) {
        out.lineIf(predicate);
        return chaining();
    }

    public T indent() {
        out.indent();
        return chaining();
    }

    public T willIndent() {
        out.willIndent();
        return chaining();
    }

    public T unIndent() {
        out.unIndent();
        return chaining();
    }

    public IOException getIOException() {
        return out.getIOException();
    }

    @Override
    public T append(final CharSequence csq) {
        out.append(csq);
        return chaining();
    }

    @Override
    public T append(final CharSequence csq, final int start, final int end) {
        out.append(csq, start, end);
        return chaining();
    }

    @Override
    public T append(final char c) {
        out.append(c);
        return chaining();
    }

    public T flush() {
        out.flush();
        return chaining();
    }

    public T flush(final int maxBlankLines) {
        out.flush(maxBlankLines);
        return chaining();
    }

    @Override
    public CharSequence getIndentPrefix() { return out.getIndentPrefix(); }

    @Override
    public T setIndentPrefix(final CharSequence prefix) {
        out.setIndentPrefix(prefix);
        return chaining();
    }

    @Override
    public CharSequence getPrefix() { return out.getPrefix(); }

    @Override
    public T setPrefix(final CharSequence prefix) {
        out.setPrefix(prefix);
        return chaining();
    }

    @Override
    public CharSequence getTotalIndentPrefix() {
        return out.getTotalIndentPrefix();
    }

    public T line(final Ref<Boolean> lineRef) {
        out.line(lineRef);
        return chaining();
    }

    public T lineIf(final Ref<Boolean> lineRef) {
        out.lineIf(lineRef);
        return chaining();
    }

    public T blankLineIf(final boolean predicate) {
        out.blankLineIf(predicate);
        return chaining();
    }

    public T blankLine(final int count) {
        out.blankLine(count);
        return chaining();
    }

    @Override
    public int getIndent() { return out.getIndent(); }

    @Override
    public T setIndentOffset(final int indentOffset) {
        out.setIndentOffset(indentOffset);
        return chaining();
    }

    public int getLineCount() {
        return out.getLineCount();
    }

    @Override
    public int getOffsetBefore() { return out.getOffsetBefore(); }

    @Override
    public int getOffsetAfter() { return out.getOffsetAfter(); }

    @Override
    public T openPreFormatted(final boolean keepIndent) {
        out.openPreFormatted(true);
        return chaining();
    }

    @Override
    public T closePreFormatted() {
        out.closePreFormatted();
        return chaining();
    }

    public T openConditional(final ConditionalFormatter openFormatter) {
        out.openConditional(openFormatter);
        return chaining();
    }

    public T closeConditional(final ConditionalFormatter closeFormatter) {
        out.closeConditional(closeFormatter);
        return chaining();
    }
}
