package com.vladsch.flexmark.tree.iteration;

import org.jetbrains.annotations.NotNull;

public interface ValueIterationConsumer<N, R> extends IterationConsumer<N> {
    void accept(@NotNull N it, @NotNull ValueIteration<R> iteration);

    // before start of all iterations
    default void beforeStart(@NotNull ValueIteration<R> iteration) {

    }

    // iteration is done, before returning
    default void afterEnd(@NotNull ValueIteration<R> iteration) {

    }
}
