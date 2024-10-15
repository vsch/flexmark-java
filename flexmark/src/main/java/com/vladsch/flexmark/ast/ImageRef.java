package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class ImageRef extends RefNode {
  public ImageRef() {}

  @Override
  public void setTextChars(BasedSequence textChars) {
    int textCharsLength = textChars.length();
    this.textOpeningMarker = textChars.subSequence(0, 2);
    this.text = textChars.subSequence(2, textCharsLength - 1).trim();
    this.textClosingMarker = textChars.subSequence(textCharsLength - 1, textCharsLength);
  }
}
