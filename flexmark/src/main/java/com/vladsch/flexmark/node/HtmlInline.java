package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;

/**
 * Inline HTML element.
 *
 * @see <a href="http://spec.commonmark.org/0.24/#raw-html">CommonMark Spec</a>
 */
public class HtmlInline extends Node {
    public HtmlInline() {
    }

    public HtmlInline(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
