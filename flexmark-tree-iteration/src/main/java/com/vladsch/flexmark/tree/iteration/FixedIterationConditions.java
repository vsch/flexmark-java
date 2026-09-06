package com.vladsch.flexmark.tree.iteration;


import java.util.function.Function;

public class FixedIterationConditions<N> implements IterationConditions<N> {
    final private Function<? super N, N> initializer;
    final private Function<? super N, N> iterator;
    final private Function<? super N, N> reverseInitializer;
    final private Function<? super N, N> reverseIterator;

    public FixedIterationConditions(Function<? super N, N> initializer, Function<? super N, N> iterator, Function<? super N, N> reverseInitializer, Function<? super N, N> reverseIterator) {
        this.initializer = initializer;
        this.iterator = iterator;
        this.reverseInitializer = reverseInitializer;
        this.reverseIterator = reverseIterator;
    }

    @Override
    public Function<? super N, N> getInitializer() {
        return initializer;
    }

    @Override
    public Function<? super N, N> getIterator() {
        return iterator;
    }

    @Override
    public IterationConditions<N> getReversed() {
        return new FixedIterationConditions<>(reverseInitializer, reverseIterator, initializer, iterator);
    }

    public static <B, T> Function<? super B, B> getAdapter(Function<? super T, T> function, Function<? super B, T> adaptBtoT, Function<? super T, B> adaptTtoB) {
        return adaptBtoT.andThen(function).andThen(adaptTtoB);
    }

    public static <B, T> FixedIterationConditions<B> mapTtoB(IterationConditions<T> constraints, Function<? super B, T> adaptBtoT, Function<? super T, B> adaptTtoB) {
        return new FixedIterationConditions<>(
                getAdapter(constraints.getInitializer(), adaptBtoT, adaptTtoB),
                getAdapter(constraints.getIterator(), adaptBtoT, adaptTtoB),
                getAdapter(constraints.getReversed().getInitializer(), adaptBtoT, adaptTtoB),
                getAdapter(constraints.getReversed().getIterator(), adaptBtoT, adaptTtoB)
        );
    }
}
