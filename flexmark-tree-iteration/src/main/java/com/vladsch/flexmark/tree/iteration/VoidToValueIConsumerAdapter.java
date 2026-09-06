package com.vladsch.flexmark.tree.iteration;


public class VoidToValueIConsumerAdapter<N, R> implements ValueIterationConsumer<N, R> {
    final private VoidIterationConsumer<N> myConsumer;

    public VoidToValueIConsumerAdapter(VoidIterationConsumer<N> consumer) {
        myConsumer = consumer;
    }

    @Override
    public void accept(N it, ValueIteration<R> iteration) {
        myConsumer.accept(it, iteration);
    }

    @Override
    public void beforeStart(ValueIteration<R> iteration) {
        myConsumer.beforeStart(iteration);
    }

    @Override
    public void startRecursion(VoidIteration iteration) {
        myConsumer.startRecursion(iteration);
    }

    @Override
    public void endRecursion(VoidIteration iteration) {
        myConsumer.endRecursion(iteration);
    }

    @Override
    public void afterEnd(ValueIteration<R> iteration) {
        myConsumer.afterEnd(iteration);
    }
}
