package org.commonmark.node;

/**
 * Inline HTML element.
 *
 * @see <a href="http://spec.commonmark.org/0.24/#raw-html">CommonMark Spec</a>
 */
public class HtmlInline extends Node {
    public HtmlInline() {
    }

    public HtmlInline(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
