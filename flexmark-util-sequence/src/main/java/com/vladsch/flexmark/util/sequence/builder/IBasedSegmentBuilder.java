package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public interface IBasedSegmentBuilder<S extends IBasedSegmentBuilder<S>>
    extends ISegmentBuilder<S> {

  BasedSequence getBaseSequence();

  String toStringWithRangesVisibleWhitespace();

  String toStringWithRanges();

  String toStringChars();
}
