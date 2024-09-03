package com.vladsch.flexmark.ast;

public interface ParagraphContainer {
  boolean isParagraphEndWrappingDisabled(Paragraph node);

  boolean isParagraphStartWrappingDisabled(Paragraph node);
}
