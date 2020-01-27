package com.vladsch.flexmark.parser.internal;

import com.vladsch.flexmark.parser.LinkRefProcessorFactory;

import java.util.List;

public class LinkRefProcessorData {
    final public List<LinkRefProcessorFactory> processors;  // sorted least nesting level to greatest nesting level
    final public int maxNesting;    // maximum desired reference link nesting level
    final public int[] nestingIndex;        // nesting level to index in ReferenceLinkProcessors for the first processor interested in that nesting level

    public LinkRefProcessorData(List<LinkRefProcessorFactory> processors, int maxNesting, int[] nestingIndex) {
        this.processors = processors;
        this.maxNesting = maxNesting;
        this.nestingIndex = nestingIndex;
    }
}
