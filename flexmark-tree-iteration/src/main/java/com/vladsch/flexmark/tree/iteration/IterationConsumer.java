package com.vladsch.flexmark.tree.iteration;


public interface IterationConsumer<N> {
    // starting a new recursion iteration
    default void startRecursion(VoidIteration iteration) {

    }

    // after recursion is done but before stack is adjusted for new level
    default void endRecursion(VoidIteration iteration) {

    }
}
