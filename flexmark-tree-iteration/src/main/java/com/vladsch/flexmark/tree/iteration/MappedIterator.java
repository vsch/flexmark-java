package com.vladsch.flexmark.tree.iteration;


import java.util.function.Function;
import java.util.function.Predicate;

public class MappedIterator<B, T extends B> {
    protected final B myElement;
    protected final ValueIterationAdapter<? super B, T> myAdapter;
    protected final TreeIterator<B> myTreeIterator;

    public MappedIterator(B element, ValueIterationAdapter<? super B, T> adapter, TreeIterator<B> treeIterator) {
        myElement = element;
        myAdapter = adapter;
        myTreeIterator = treeIterator;
    }

    // *******************************************************
    //
    // Looping delegated
    //
    // *******************************************************

    final public TreeIterator<B> getTreeIterator() {
        return myTreeIterator;
    }

    final public <R> R doLoop(R defaultValue, ValueIterationConsumer<? super T, R> consumer) {
        return myTreeIterator.doLoop(myElement, defaultValue, myAdapter, consumer);
    }

    final public void doLoop(VoidIterationConsumer<? super T> consumer) {
        myTreeIterator.doLoop(myElement, myAdapter, consumer);
    }

    // *******************************************************
    //
    // Need Subclass Constructors
    //
    // *******************************************************

    public MappedIterator<B, T> getModifiedCopy(B element, ValueIterationAdapter<? super B, T> adapter, TreeIterator<B> treeIterator) {
        return new MappedIterator<>(element, adapter, treeIterator);
    }

    public <F extends B> MappedIterator<B, F> getModifiedCopyF(B element, ValueIterationAdapter<? super B, F> adapter, TreeIterator<B> treeIterator) {
        return new MappedIterator<>(element, adapter, treeIterator);
    }

    // *******************************************************
    //
    // Need Overrides with cast to sub-class
    //
    // *******************************************************

    public MappedIterator<B, T> reversed() {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.reversed());
    }


    public MappedIterator<B, T> recursive() {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.recursive());
    }

    public MappedIterator<B, T> nonRecursive() {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.nonRecursive());
    }

    public MappedIterator<B, T> recursive(boolean recursive) {
        return recursive ? recursive() : nonRecursive();
    }

    public MappedIterator<B, T> nonRecursive(boolean nonRecursive) {
        return nonRecursive ? nonRecursive() : recursive();
    }

    public MappedIterator<B, T> recurse(Predicate<? super B> predicate) {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.recurse(predicate));
    }

    public MappedIterator<B, T> recurse(Class<? super B> clazz) {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.recurse(clazz));
    }

    public <F extends B> MappedIterator<B, T> recurse(Class<F> clazz, Predicate<? super F> predicate) {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.recurse(clazz, predicate));
    }

    public MappedIterator<B, T> noRecurse(Predicate<? super B> predicate) {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.noRecurse(predicate));
    }

    public MappedIterator<B, T> noRecurse(Class<? super B> clazz) {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.noRecurse(clazz));
    }

    public <F extends B> MappedIterator<B, T> noRecurse(Class<F> clazz, Predicate<? super F> predicate) {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.recurse(clazz, predicate));
    }

    public MappedIterator<B, T> filterFalse() {
        return aborted();
    }

    public MappedIterator<B, T> aborted() {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.aborted());
    }

    public MappedIterator<B, T> filterOut(Predicate<? super B> predicate) {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.filterOut(predicate));
    }

    public MappedIterator<B, T> filterOut(Class<? super B> clazz) {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.filterOut(clazz));
    }

    public <F extends B> MappedIterator<B, T> filterOut(Class<F> clazz, Predicate<? super F> predicate) {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.filterOut(clazz, predicate));
    }

    public MappedIterator<B, T> filter(Predicate<? super B> predicate) {
        return getModifiedCopy(myElement, myAdapter, myTreeIterator.filter(predicate));
    }

    public MappedIterator<B, T> acceptFilter(ValueIterationFilter<? super T> filter) {
        return getModifiedCopy(myElement, myAdapter.andThen(ValueIterationAdapterImpl.of(filter)), myTreeIterator);
    }

    // *******************************************************
    //
    // Mapping Functions
    //
    // *******************************************************

    public <F extends B> MappedIterator<B, F> filter(Class<F> clazz) {
        return getModifiedCopyF(myElement, myAdapter.andThen(ValueIterationAdapterImpl.of(clazz)), myTreeIterator);
    }

    public <F extends B> MappedIterator<B, F> filter(Class<F> clazz, Predicate<? super F> predicate) {
        return getModifiedCopyF(myElement, myAdapter.andThen(ValueIterationAdapterImpl.of(clazz, predicate)), myTreeIterator);
    }

    public <F extends B> MappedIterator<B, F> adapt(Function<? super T, F> adapter) {
        return getModifiedCopyF(myElement, myAdapter.andThen(ValueIterationAdapterImpl.of(adapter)), myTreeIterator);
    }

    public <F extends B> MappedIterator<B, F> adapt(ValueIterationAdapter<? super T, F> adapter) {
        return getModifiedCopyF(myElement, myAdapter.andThen(adapter), myTreeIterator);
    }

    public MappedIterator<Object, B> toObjectMapped(Class<B> clazz) {
        Function<Object, B> objectToB = it -> clazz.isInstance(it) ? clazz.cast(it) : null;
        Function<B, Object> tToObject = it -> it;
        FixedIterationConditions<Object> constraints = FixedIterationConditions.mapTtoB(myTreeIterator.getConstraints(), objectToB, tToObject);
        return new MappedIterator<>(myElement, new ValueIterationAdapterImpl<>(objectToB), new TreeIterator<>(constraints));
    }

    // *******************************************************
    //
    // Subclass specific
    //
    // *******************************************************

    // *******************************************************
    //
    // Static Factories
    //
    // *******************************************************

    public static <N> MappedIterator<N, N> create(N element, TreeIterator<N> treeIterator) {
        return new MappedIterator<>(element, ValueIterationAdapterImpl.of(), treeIterator);
    }
}
