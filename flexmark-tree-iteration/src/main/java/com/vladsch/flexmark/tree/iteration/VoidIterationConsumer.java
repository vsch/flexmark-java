package com.vladsch.flexmark.tree.iteration;


public interface VoidIterationConsumer<N> extends IterationConsumer<N> {
    void accept(N it, VoidIteration loop);

    // before start of all iterations
    default void beforeStart(VoidIteration iteration) {

    }

    // iteration is done, before returning
    default void afterEnd(VoidIteration iteration) {

    }
}

