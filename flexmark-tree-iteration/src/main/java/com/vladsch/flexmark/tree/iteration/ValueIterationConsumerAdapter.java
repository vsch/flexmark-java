package com.vladsch.flexmark.tree.iteration;


public interface ValueIterationConsumerAdapter<P, T> {
    <R> ValueIterationConsumer<? super P, R> getConsumer(ValueIterationConsumer<? super T, R> valueConsumer);

    <R> ValueIterationConsumer<? super P, R> getConsumer(VoidIterationConsumer<? super T> voidConsumer);
}
