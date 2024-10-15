package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class Link extends InlineLinkNode {
  public Link() {}

  @Override
  public void setTextChars(BasedSequence textChars) {
    int textCharsLength = textChars.length();
    this.textOpeningMarker = textChars.subSequence(0, 1);
    this.text = textChars.subSequence(1, textCharsLength - 1).trim();
    this.textClosingMarker = textChars.subSequence(textCharsLength - 1, textCharsLength);
  }
}
