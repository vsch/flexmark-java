package com.vladsch.flexmark.tree.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface IterationConditions<N> {
    @NotNull
    Function<? super N, N> getInitializer();

    @NotNull
    Function<? super N, N> getIterator();

    @NotNull
    default IterationConditions<N> getReversed() {
        throw new IllegalStateException("Method not implemented");
    }

    @NotNull
    default IterationConditions<N> getAborted() {
        Function<? super N, N> function = n -> null;
        return new FixedIterationConditions<>(function, function, function, function);
    }
}
