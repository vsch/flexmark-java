package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.IRichSequence;
import org.jetbrains.annotations.NotNull;

public interface SegmentOptimizer<S extends IRichSequence<S>> {
    void accept(@NotNull SegmentBuilder builder, @NotNull S chars, @NotNull SegmentPosition params);
}
