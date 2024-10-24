package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

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

  protected HtmlInlineBase() {}

  HtmlInlineBase(BasedSequence chars) {
    super(chars);
  }
}
