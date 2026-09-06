package com.vladsch.flexmark.tree.iteration;


import java.util.function.Function;

public interface IterationConditions<N> {
    Function<? super N, N> getInitializer();

    Function<? super N, N> getIterator();

    default IterationConditions<N> getReversed() {
        throw new IllegalStateException("Method not implemented");
    }

    default IterationConditions<N> getAborted() {
        Function<? super N, N> function = n -> null;
        return new FixedIterationConditions<>(function, function, function, function);
    }
}
