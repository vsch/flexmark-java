package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

/**
 * Inline HTML comment element.
 *
 * @see <a href="http://spec.commonmark.org/0.24/#raw-html">CommonMark Spec</a>
 */
public class HtmlInlineComment extends HtmlInline {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        if (getChars().length() <= 10) {
            segmentSpanChars(out, getChars(), "chars");
        } else {
            // give the first 5 and last 5
            segmentSpanChars(out, getChars().getStartOffset(), getChars().getEndOffset(), "chars", getChars().subSequence(0, 5).toVisibleWhitespaceString() + "\"...\"" + getChars().subSequence(getChars().length() - 5).toVisibleWhitespaceString());
        }
    }

    public HtmlInlineComment() {
    }

    public HtmlInlineComment(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
