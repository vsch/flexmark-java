package com.vladsch.flexmark.tree.iteration;

import org.jetbrains.annotations.NotNull;

public class VoidToValueIConsumerAdapter<N, R> implements ValueIterationConsumer<N, R> {
    final private @NotNull VoidIterationConsumer<N> myConsumer;

    public VoidToValueIConsumerAdapter(@NotNull VoidIterationConsumer<N> consumer) {
        myConsumer = consumer;
    }

    @Override
    public void accept(@NotNull N it, @NotNull ValueIteration<R> iteration) {
        myConsumer.accept(it, iteration);
    }

    @Override
    public void beforeStart(@NotNull ValueIteration<R> iteration) {
        myConsumer.beforeStart(iteration);
    }

    @Override
    public void startRecursion(@NotNull VoidIteration iteration) {
        myConsumer.startRecursion(iteration);
    }

    @Override
    public void endRecursion(@NotNull VoidIteration iteration) {
        myConsumer.endRecursion(iteration);
    }

    @Override
    public void afterEnd(@NotNull ValueIteration<R> iteration) {
        myConsumer.afterEnd(iteration);
    }
}
