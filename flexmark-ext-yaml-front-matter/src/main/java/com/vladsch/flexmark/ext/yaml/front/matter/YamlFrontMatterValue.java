package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

class YamlFrontMatterValue extends Node {
  @NotNull
  @Override
  public BasedSequence[] getSegments() {
    return EMPTY_SEGMENTS;
  }

  YamlFrontMatterValue() {}

  YamlFrontMatterValue(BasedSequence chars) {
    super(chars);
  }
}
