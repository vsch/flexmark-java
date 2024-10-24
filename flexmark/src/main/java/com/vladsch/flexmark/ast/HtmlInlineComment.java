package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Inline HTML comment element.
 *
 * @see <a href="http://spec.commonmark.org/0.24/#raw-html">CommonMark Spec</a>
 */
public class HtmlInlineComment extends HtmlInlineBase {

  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  @Override
  public void getAstExtra(StringBuilder out) {
    astExtraChars(out);
  }

  public HtmlInlineComment() {}

  public HtmlInlineComment(BasedSequence chars) {
    super(chars);
  }
}
