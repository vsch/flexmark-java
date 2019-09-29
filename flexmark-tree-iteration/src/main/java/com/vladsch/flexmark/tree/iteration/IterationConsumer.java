package com.vladsch.flexmark.tree.iteration;

import org.jetbrains.annotations.NotNull;

public interface IterationConsumer<N> {
    // starting a new recursion iteration
    default void startRecursion(@NotNull VoidIteration iteration) {

    }

    // after recursion is done but before stack is adjusted for new level
    default void endRecursion(@NotNull VoidIteration iteration) {

    }
}
