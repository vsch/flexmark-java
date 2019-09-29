package com.vladsch.flexmark.tree.iteration;

public interface ValueIterationFilter<N> {
    boolean filter(N it, VoidIteration loop);
}
