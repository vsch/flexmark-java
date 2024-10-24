package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

class YamlFrontMatterValue extends Node {

  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  YamlFrontMatterValue() {}

  YamlFrontMatterValue(BasedSequence chars) {
    super(chars);
  }
}
