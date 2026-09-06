package com.vladsch.flexmark.tree.iteration;


public interface ValueIterationAdapter<N, T> {

    ValueIterationConsumerAdapter<N, T> getConsumerAdapter();

    <V> ValueIterationAdapter<N, V> andThen(ValueIterationAdapter<? super T, V> after);

    ValueIterationAdapter<N, T> compose(ValueIterationAdapter<? super N, N> before);
}
