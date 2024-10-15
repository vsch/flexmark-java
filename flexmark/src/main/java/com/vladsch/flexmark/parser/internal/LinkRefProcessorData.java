package com.vladsch.flexmark.parser.internal;

import com.vladsch.flexmark.parser.LinkRefProcessorFactory;
import java.util.List;

public class LinkRefProcessorData {
  final List<LinkRefProcessorFactory>
      processors; // sorted least nesting level to greatest nesting level
  final int maxNesting; // maximum desired reference link nesting level
  final int[]
      nestingIndex; // nesting level to index in ReferenceLinkProcessors for the first processor

  // interested in that nesting level

  LinkRefProcessorData(
      List<LinkRefProcessorFactory> processors, int maxNesting, int[] nestingIndex) {
    this.processors = processors;
    this.maxNesting = maxNesting;
    this.nestingIndex = nestingIndex;
  }
}
