package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.List;

public abstract class Block extends ContentNode {
  protected Block() {}

  protected Block(BasedSequence chars) {
    super(chars);
  }

  protected Block(BasedSequence chars, List<BasedSequence> lineSegments) {
    super(chars, lineSegments);
  }

  protected Block(List<BasedSequence> lineSegments) {
    super(lineSegments);
  }

  @Override
  public Block getParent() {
    return (Block) super.getParent();
  }

  @Override
  protected void setParent(Node parent) {
    if (parent != null && !(parent instanceof Block)) {
      throw new IllegalArgumentException("Parent of block must also be block (can not be inline)");
    }
    super.setParent(parent);
  }
}
