package com.vladsch.flexmark.util.html;

public class HtmlFormattingAppendableImpl extends HtmlFormattingAppendableBase<HtmlFormattingAppendableImpl> {
    public HtmlFormattingAppendableImpl(Appendable out) {
        super(out);
    }

    public HtmlFormattingAppendableImpl(FormattingAppendable other, Appendable out, boolean inheritIndent) {
        super(other,out, inheritIndent);
    }

    public HtmlFormattingAppendableImpl(Appendable out, int indentSize, final boolean allFormatOptions) {
        super(out,indentSize, allFormatOptions);
    }

    @Override
    protected HtmlFormattingAppendableImpl chaining() {
        return this;
    }
}
