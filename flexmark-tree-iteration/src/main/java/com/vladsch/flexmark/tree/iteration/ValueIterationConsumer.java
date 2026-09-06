package com.vladsch.flexmark.tree.iteration;


public interface ValueIterationConsumer<N, R> extends IterationConsumer<N> {
    void accept(N it, ValueIteration<R> iteration);

    // before start of all iterations
    default void beforeStart(ValueIteration<R> iteration) {

    }

    // iteration is done, before returning
    default void afterEnd(ValueIteration<R> iteration) {

    }
}
