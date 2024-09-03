package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class CodeBlock extends Block {
  @NotNull
  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  public CodeBlock() {}

  public CodeBlock(BasedSequence chars) {
    super(chars);
  }

  public CodeBlock(BasedSequence chars, List<BasedSequence> segments) {
    super(chars, segments);
  }

  public CodeBlock(BlockContent blockContent) {
    super(blockContent);
  }
}
