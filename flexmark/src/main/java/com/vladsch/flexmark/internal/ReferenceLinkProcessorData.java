package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.parser.ReferenceLinkProcessor;

import java.util.List;

public class ReferenceLinkProcessorData {
    public final List<ReferenceLinkProcessor> processors;  // sorted least nesting level to greatest nesting level
    public final int maxNesting;    // maximum desired reference link nesting level
    public final int[] nestingIndex;        // nesting level to index in ReferenceLinkProcessors for the first processor interested in that nesting level

    public ReferenceLinkProcessorData(List<ReferenceLinkProcessor> processors, int maxNesting, int[] nestingIndex) {
        this.processors = processors;
        this.maxNesting = maxNesting;
        this.nestingIndex = nestingIndex;
    }
}
