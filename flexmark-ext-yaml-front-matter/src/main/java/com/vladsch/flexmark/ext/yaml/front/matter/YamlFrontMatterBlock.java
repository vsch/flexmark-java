package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class YamlFrontMatterBlock extends Block {

  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }
}
