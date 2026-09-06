package com.vladsch.flexmark.tree.iteration;

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

    public Predicate<N> getPredicate(Class<? super N> clazz) {
        return clazz::isInstance;
    }

    public <F> Predicate<N> getPredicate(Class<F> clazz, Predicate<? super F> predicate) {
        return (it) -> clazz.isInstance(it) && predicate.test(clazz.cast(it));
    }

    public IterationConditions<N> getConstraints() {
        return myConstraints;
    }

    public Predicate<? super N> getRecursion() {
        return myRecursion;
    }

    public Predicate<? super N> getFilter() {
        return myFilter;
    }

    public TreeIterator<N> modifiedCopy(IterationConditions<N> constraints, Predicate<? super N> filter, Predicate<? super N> recursion) {
        return new TreeIterator<>(constraints, filter, recursion);
    }

    public TreeIterator<N> reversed() {
        return modifiedCopy(myConstraints.getReversed(), myFilter, myRecursion);
    }

    public TreeIterator<N> recursive() {
        //noinspection unchecked
        return modifiedCopy(myConstraints, myFilter, (Predicate<N>) TRUE);
    }

    public TreeIterator<N> nonRecursive() {
        //noinspection unchecked
        return modifiedCopy(myConstraints, myFilter, (Predicate<N>) FALSE);
    }

    public TreeIterator<N> recurse(Predicate<? super N> predicate) {
        return modifiedCopy(myConstraints, myFilter, it -> myRecursion.test(it) || predicate.test(it));
    }

    public TreeIterator<N> recurse(Class<? super N> clazz) {
        return recurse(getPredicate(clazz));
    }

    public <F> TreeIterator<N> recurse(Class<F> clazz, Predicate<? super F> predicate) {
        return recurse(getPredicate(clazz, predicate));
    }

    public TreeIterator<N> noRecurse(Predicate<? super N> predicate) {
        return modifiedCopy(myConstraints, myFilter, it -> myRecursion.test(it) && !predicate.test(it));
    }

    public TreeIterator<N> noRecurse(Class<? super N> clazz) {
        return noRecurse(getPredicate(clazz));
    }

    public <F> TreeIterator<N> noRecurse(Class<F> clazz, Predicate<? super F> predicate) {
        return noRecurse(getPredicate(clazz, predicate));
    }

    public TreeIterator<N> aborted() {
        return modifiedCopy(myConstraints.getAborted(), myFilter, myRecursion);
    }

    public TreeIterator<N> filterOut(Predicate<? super N> predicate) {
        return modifiedCopy(myConstraints, it -> myFilter.test(it) && !predicate.test(it), myRecursion);
    }

    public TreeIterator<N> filterOut(Class<? super N> clazz) {
        return filterOut(getPredicate(clazz));
    }

    public <F> TreeIterator<N> filterOut(Class<F> clazz, Predicate<? super F> predicate) {
        return filterOut(getPredicate(clazz, predicate));
    }

    public TreeIterator<N> filter(Predicate<? super N> predicate) {
        return modifiedCopy(myConstraints, it -> myFilter.test(it) && predicate.test(it), myRecursion);
    }

    public TreeIterator<N> filter(Class<? super N> clazz) {
        return filter(getPredicate(clazz));
    }

    public <F> TreeIterator<N> filter(Class<F> clazz, Predicate<? super F> predicate) {
        return filter(getPredicate(clazz, predicate));
    }

    public static <N> TreeIterator<N> of(IterationConditions<N> constraints) {
        return new TreeIterator<>(constraints);
    }

    public static <N> TreeIterator<N> of(IterationConditions<N> constraints, Predicate<? super N> filter) {
        return new TreeIterator<>(constraints, filter);
    }

    public static <N> TreeIterator<N> of(IterationConditions<N> constraints, Predicate<? super N> filter, Predicate<? super N> recursion) {
        return new TreeIterator<>(constraints, filter, recursion);
    }

    public static <N> Predicate<N> TRUE() {
        return n -> true;
    }

    public static <N> Predicate<N> FALSE() {
        return n -> true;
    }

    public <R> ValueIteration<R> iterate(N element, R defaultValue, ValueIterationConsumer<? super N, R> consumer) {
        IteratorInstance<N, R> instance = new IteratorInstance<>(getConstraints(), getFilter(), getRecursion(), element, defaultValue);
        instance.iterate(consumer);
        return instance;
    }

    public <T, R> ValueIteration<R> iterate(N element, R defaultValue, ValueIterationAdapter<? super N, T> adapter, ValueIterationConsumer<? super T, R> consumer) {
        IteratorInstance<N, R> instance = new IteratorInstance<>(getConstraints(), getFilter(), getRecursion(), element, defaultValue);
        instance.iterate(adapter.getConsumerAdapter().getConsumer(consumer));
        return instance;
    }

    public <R> VoidIteration iterate(N element, VoidIterationConsumer<? super N> consumer) {
        IteratorInstance<N, R> instance = new IteratorInstance<>(getConstraints(), getFilter(), getRecursion(), element);
        instance.iterate(consumer);
        return instance;
    }

    public <T, R> VoidIteration iterate(N element, ValueIterationAdapter<? super N, T> adapter, VoidIterationConsumer<? super T> consumer) {
        IteratorInstance<N, R> instance = new IteratorInstance<>(getConstraints(), getFilter(), getRecursion(), element);
        instance.iterate(adapter.getConsumerAdapter().getConsumer(consumer));
        return instance;
    }

    public <R> R doLoop(N element, R defaultValue, ValueIterationConsumer<? super N, R> consumer) {
        return iterate(element, defaultValue, consumer).getResult();
    }

    public void doLoop(N element, VoidIterationConsumer<? super N> consumer) {
        iterate(element, consumer);
    }

    public <T, R> R doLoop(N element, R defaultValue, ValueIterationAdapter<? super N, T> adapter, ValueIterationConsumer<? super T, R> consumer) {
        return iterate(element, defaultValue, adapter, consumer).getResult();
    }

    public <T, R> void doLoop(N element, ValueIterationAdapter<? super N, T> adapter, VoidIterationConsumer<? super T> consumer) {
        iterate(element, adapter, consumer);
    }
}
