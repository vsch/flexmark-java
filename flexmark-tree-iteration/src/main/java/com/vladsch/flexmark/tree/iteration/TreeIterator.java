package com.vladsch.flexmark.tree.iteration;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.function.Predicate;

public class TreeIterator<N> {
    final public static Logger LOG = LoggerFactory.getILoggerFactory().getLogger("com.vladsch.treeIteration.util.looping");
    final public static Logger LOG_INFO = LoggerFactory.getILoggerFactory().getLogger("com.vladsch.treeIteration.util.looping-summary");
    final public static Logger LOG_TRACE = LoggerFactory.getILoggerFactory().getLogger("com.vladsch.treeIteration.util.looping-detailed");

    final public static Predicate<Object> TRUE = o -> true;
    final public static Predicate<Object> FALSE = o -> false;
    final public static Predicate<Object> NOT_NULL = Objects::nonNull;

    final private IterationConditions<N> myConstraints;
    final private Predicate<? super N> myRecursion;
    protected final Predicate<? super N> myFilter;

    public TreeIterator(IterationConditions<N> constraints, Predicate<? super N> filter) {
        this(constraints, filter, FALSE);
    }

    public TreeIterator(IterationConditions<N> constraints) {
        this(constraints, TRUE, FALSE);
    }

    public TreeIterator(
            IterationConditions<N> constraints,
            Predicate<? super N> filter,
            Predicate<? super N> recursion
    ) {
        myConstraints = constraints;
        myRecursion = recursion;
        myFilter = filter;
    }

    @NotNull
    public Predicate<N> getPredicate(@NotNull Class<? super N> clazz) {
        return clazz::isInstance;
    }

    @NotNull
    public <F> Predicate<N> getPredicate(@NotNull Class<F> clazz, @NotNull Predicate<? super F> predicate) {
        return (it) -> clazz.isInstance(it) && predicate.test(clazz.cast(it));
    }

    @NotNull
    public IterationConditions<N> getConstraints() {
        return myConstraints;
    }

    public Predicate<? super N> getRecursion() {
        return myRecursion;
    }

    public Predicate<? super N> getFilter() {
        return myFilter;
    }

    @NotNull
    public TreeIterator<N> modifiedCopy(@NotNull IterationConditions<N> constraints, @NotNull Predicate<? super N> filter, @NotNull Predicate<? super N> recursion) {
        return new TreeIterator<>(constraints, filter, recursion);
    }

    @NotNull
    public TreeIterator<N> reversed() {
        return modifiedCopy(myConstraints.getReversed(), myFilter, myRecursion);
    }

    @NotNull
    public TreeIterator<N> recursive() {
        //noinspection unchecked
        return modifiedCopy(myConstraints, myFilter, (Predicate<N>) TRUE);
    }

    @NotNull
    public TreeIterator<N> nonRecursive() {
        //noinspection unchecked
        return modifiedCopy(myConstraints, myFilter, (Predicate<N>) FALSE);
    }

    @NotNull
    public TreeIterator<N> recurse(@NotNull Predicate<? super N> predicate) {
        return modifiedCopy(myConstraints, myFilter, it -> myRecursion.test(it) || predicate.test(it));
    }

    @NotNull
    public TreeIterator<N> recurse(@NotNull Class<? super N> clazz) {
        return recurse(getPredicate(clazz));
    }

    @NotNull
    public <F> TreeIterator<N> recurse(@NotNull Class<F> clazz, @NotNull Predicate<? super F> predicate) {
        return recurse(getPredicate(clazz, predicate));
    }

    @NotNull
    public TreeIterator<N> noRecurse(@NotNull Predicate<? super N> predicate) {
        return modifiedCopy(myConstraints, myFilter, it -> myRecursion.test(it) && !predicate.test(it));
    }

    @NotNull
    public TreeIterator<N> noRecurse(@NotNull Class<? super N> clazz) {
        return noRecurse(getPredicate(clazz));
    }

    @NotNull
    public <F> TreeIterator<N> noRecurse(@NotNull Class<F> clazz, @NotNull Predicate<? super F> predicate) {
        return noRecurse(getPredicate(clazz, predicate));
    }

    @NotNull
    public TreeIterator<N> aborted() {
        return modifiedCopy(myConstraints.getAborted(), myFilter, myRecursion);
    }

    @NotNull
    public TreeIterator<N> filterOut(@NotNull Predicate<? super N> predicate) {
        return modifiedCopy(myConstraints, it -> myFilter.test(it) && !predicate.test(it), myRecursion);
    }

    @NotNull
    public TreeIterator<N> filterOut(@NotNull Class<? super N> clazz) {
        return filterOut(getPredicate(clazz));
    }

    @NotNull
    public <F> TreeIterator<N> filterOut(@NotNull Class<F> clazz, @NotNull Predicate<? super F> predicate) {
        return filterOut(getPredicate(clazz, predicate));
    }

    @NotNull
    public TreeIterator<N> filter(@NotNull Predicate<? super N> predicate) {
        return modifiedCopy(myConstraints, it -> myFilter.test(it) && predicate.test(it), myRecursion);
    }

    @NotNull
    public TreeIterator<N> filter(@NotNull Class<? super N> clazz) {
        return filter(getPredicate(clazz));
    }

    @NotNull
    public <F> TreeIterator<N> filter(@NotNull Class<F> clazz, @NotNull Predicate<? super F> predicate) {
        return filter(getPredicate(clazz, predicate));
    }

    @NotNull
    public static <N> TreeIterator<N> of(@NotNull IterationConditions<N> constraints) {
        return new TreeIterator<>(constraints);
    }

    @NotNull
    public static <N> TreeIterator<N> of(@NotNull IterationConditions<N> constraints, @NotNull Predicate<? super N> filter) {
        return new TreeIterator<>(constraints, filter);
    }

    @NotNull
    public static <N> TreeIterator<N> of(@NotNull IterationConditions<N> constraints, @NotNull Predicate<? super N> filter, @NotNull Predicate<? super N> recursion) {
        return new TreeIterator<>(constraints, filter, recursion);
    }

    @NotNull
    public static <N> Predicate<N> TRUE() {
        return n -> true;
    }

    @NotNull
    public static <N> Predicate<N> FALSE() {
        return n -> true;
    }

    public <R> ValueIteration<R> iterate(@NotNull N element, @NotNull R defaultValue, @NotNull ValueIterationConsumer<? super N, R> consumer) {
        IteratorInstance<N, R> instance = new IteratorInstance<>(getConstraints(), getFilter(), getRecursion(), element, defaultValue);
        instance.iterate(consumer);
        return instance;
    }

    public <T, R> ValueIteration<R> iterate(@NotNull N element, @NotNull R defaultValue, @NotNull ValueIterationAdapter<? super N, T> adapter, @NotNull ValueIterationConsumer<? super T, R> consumer) {
        IteratorInstance<N, R> instance = new IteratorInstance<>(getConstraints(), getFilter(), getRecursion(), element, defaultValue);
        instance.iterate(adapter.getConsumerAdapter().getConsumer(consumer));
        return instance;
    }

    public <R> VoidIteration iterate(@NotNull N element, @NotNull VoidIterationConsumer<? super N> consumer) {
        IteratorInstance<N, R> instance = new IteratorInstance<>(getConstraints(), getFilter(), getRecursion(), element);
        instance.iterate(consumer);
        return instance;
    }

    public <T, R> VoidIteration iterate(@NotNull N element, @NotNull ValueIterationAdapter<? super N, T> adapter, @NotNull VoidIterationConsumer<? super T> consumer) {
        IteratorInstance<N, R> instance = new IteratorInstance<>(getConstraints(), getFilter(), getRecursion(), element);
        instance.iterate(adapter.getConsumerAdapter().getConsumer(consumer));
        return instance;
    }

    @NotNull
    public <R> R doLoop(@NotNull N element, @NotNull R defaultValue, @NotNull ValueIterationConsumer<? super N, R> consumer) {
        return iterate(element, defaultValue, consumer).getResult();
    }

    public void doLoop(@NotNull N element, @NotNull VoidIterationConsumer<? super N> consumer) {
        iterate(element, consumer);
    }

    @NotNull
    public <T, R> R doLoop(@NotNull N element, @NotNull R defaultValue, @NotNull ValueIterationAdapter<? super N, T> adapter, @NotNull ValueIterationConsumer<? super T, R> consumer) {
        return iterate(element, defaultValue, adapter, consumer).getResult();
    }

    public <T, R> void doLoop(@NotNull N element, @NotNull ValueIterationAdapter<? super N, T> adapter, @NotNull VoidIterationConsumer<? super T> consumer) {
        iterate(element, adapter, consumer);
    }
}
