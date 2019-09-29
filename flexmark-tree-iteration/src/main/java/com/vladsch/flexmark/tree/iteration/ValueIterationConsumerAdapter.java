package com.vladsch.flexmark.tree.iteration;

import org.jetbrains.annotations.NotNull;

public interface ValueIterationConsumerAdapter<P, T> {
    @NotNull <R> ValueIterationConsumer<? super P, R> getConsumer(ValueIterationConsumer<? super T, R> valueConsumer);

    @NotNull <R> ValueIterationConsumer<? super P, R> getConsumer(VoidIterationConsumer<? super T> voidConsumer);
}
