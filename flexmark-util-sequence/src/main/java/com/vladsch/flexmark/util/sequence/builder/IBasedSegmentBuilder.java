package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public interface IBasedSegmentBuilder<S extends IBasedSegmentBuilder<S>> extends ISegmentBuilder<S> {
    @NotNull BasedSequence getBaseSequence();
    @NotNull String toStringWithRangesVisibleWhitespace();
    @NotNull String toStringWithRanges();
    @NotNull String toStringChars();
}
