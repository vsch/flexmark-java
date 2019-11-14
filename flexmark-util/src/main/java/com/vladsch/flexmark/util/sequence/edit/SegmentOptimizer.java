package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.IRichSequence;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public interface SegmentOptimizer<S extends IRichSequence<S>> extends BiFunction<S, SegmentParams, SegmentParams> {
    @NotNull
    @Override
    SegmentParams apply(@NotNull S chars, @NotNull SegmentParams params);
}
