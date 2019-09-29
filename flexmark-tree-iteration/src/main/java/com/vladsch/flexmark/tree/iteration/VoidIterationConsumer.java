package com.vladsch.flexmark.tree.iteration;

import org.jetbrains.annotations.NotNull;

public interface VoidIterationConsumer<N> extends IterationConsumer<N> {
    void accept(@NotNull N it, @NotNull VoidIteration loop);

    // before start of all iterations
    default void beforeStart(@NotNull VoidIteration iteration) {

    }

    // iteration is done, before returning
    default void afterEnd(@NotNull VoidIteration iteration) {

    }
}

