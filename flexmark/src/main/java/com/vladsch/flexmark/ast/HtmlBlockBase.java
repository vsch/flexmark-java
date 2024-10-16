package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * HTML block
 *
 * @see <a href="http://spec.commonmark.org/0.18/#html-blocks">CommonMark Spec</a>
 */
public abstract class HtmlBlockBase extends Block {
  @NotNull
  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  protected HtmlBlockBase() {}

  HtmlBlockBase(BasedSequence chars) {
    super(chars);
  }
}
