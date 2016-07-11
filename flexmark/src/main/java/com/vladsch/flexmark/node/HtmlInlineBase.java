package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

/**
 * Inline HTML element.
 *
 * @see <a href="http://spec.commonmark.org/0.24/#raw-html">CommonMark Spec</a>
 */
public abstract class HtmlInlineBase extends Node {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        astExtraChars(out);
    }
    
    public HtmlInlineBase() {
    }

    public HtmlInlineBase(BasedSequence chars) {
        super(chars);
    }
}
