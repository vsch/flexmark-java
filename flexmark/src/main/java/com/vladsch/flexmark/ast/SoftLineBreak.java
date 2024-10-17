package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.DoNotAttributeDecorate;
import com.vladsch.flexmark.util.ast.DoNotTrim;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;

public class SoftLineBreak extends Node
    implements DoNotAttributeDecorate, DoNotTrim, TextContainer {
  @NotNull
  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  public SoftLineBreak() {}

  public SoftLineBreak(BasedSequence chars) {
    super(chars);
  }

  @Override
  public boolean collectText(
      ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out,
      int flags,
      NodeVisitor nodeVisitor) {
    out.add(getChars());
    return false;
  }
}
