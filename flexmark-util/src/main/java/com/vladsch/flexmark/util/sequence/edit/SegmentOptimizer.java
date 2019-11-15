package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.IRichSequence;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface SegmentOptimizer<S extends IRichSequence<S>> extends BiConsumer<S, SegmentPosition> {
    @Override
    void accept(@NotNull S chars, @NotNull SegmentPosition params);
}
