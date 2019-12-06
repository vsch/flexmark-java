package com.vladsch.flexmark.util.sequence.edit;

public interface IBasedSegmentBuilder<S extends IBasedSegmentBuilder<S>> extends ISegmentBuilder<S> {

    String toStringWithRangesVisibleWhitespace();
    String toStringWithRanges();
    String toStringChars();
}
