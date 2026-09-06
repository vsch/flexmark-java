package com.vladsch.flexmark.tree.iteration;

import com.vladsch.flexmark.util.ast.Node;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class NodeIterator<N extends Node> extends MappedIterator<Node, N> {
    public NodeIterator(Node element, ValueIterationAdapter<? super Node, N> adapter, TreeIterator<Node> treeIterator) {
        super(element, adapter, treeIterator);
    }

    // *******************************************************
    //
    // Need Subclass Constructors
    //
    // *******************************************************

    public NodeIterator<N> getModifiedCopy(Node element, ValueIterationAdapter<? super Node, N> adapter, TreeIterator<Node> treeIterator) {
        return new NodeIterator<>(element, adapter, treeIterator);
    }

    public <F extends Node> NodeIterator<F> getModifiedCopyF(Node element, ValueIterationAdapter<? super Node, F> adapter, TreeIterator<Node> treeIterator) {
        return new NodeIterator<>(element, adapter, treeIterator);
    }

    // *******************************************************
    //
    // Need Overrides with cast to sub-class
    //
    // *******************************************************

    @Override
    public NodeIterator<N> reversed() {
        return (NodeIterator<N>) super.reversed();
    }

    @Override
    public NodeIterator<N> recursive() {
        return (NodeIterator<N>) super.recursive();
    }

    @Override
    public NodeIterator<N> nonRecursive() {
        return (NodeIterator<N>) super.nonRecursive();
    }

    @Override
    public NodeIterator<N> recursive(boolean recursive) {
        return (NodeIterator<N>) super.recursive(recursive);
    }

    @Override
    public NodeIterator<N> nonRecursive(boolean nonRecursive) {
        return (NodeIterator<N>) super.nonRecursive(nonRecursive);
    }

    @Override
    public NodeIterator<N> recurse(Predicate<? super Node> predicate) {
        return (NodeIterator<N>) super.recurse(predicate);
    }

    @Override
    public NodeIterator<N> recurse(Class<? super Node> clazz) {
        return (NodeIterator<N>) super.recurse(clazz);
    }

    @Override
    public <F extends Node> NodeIterator<N> recurse(Class<F> clazz, Predicate<? super F> predicate) {
        return (NodeIterator<N>) super.recurse(clazz, predicate);
    }

    @Override
    public NodeIterator<N> noRecurse(Predicate<? super Node> predicate) {
        return (NodeIterator<N>) super.noRecurse(predicate);
    }

    @Override
    public NodeIterator<N> noRecurse(Class<? super Node> clazz) {
        return (NodeIterator<N>) super.noRecurse(clazz);
    }

    @Override
    public <F extends Node> NodeIterator<N> noRecurse(Class<F> clazz, Predicate<? super F> predicate) {
        return (NodeIterator<N>) super.noRecurse(clazz, predicate);
    }

    @Override
    public NodeIterator<N> filterFalse() {
        return (NodeIterator<N>) super.filterFalse();
    }

    @Override
    public NodeIterator<N> aborted() {
        return (NodeIterator<N>) super.aborted();
    }

    @Override
    public NodeIterator<N> filterOut(Predicate<? super Node> predicate) {
        return (NodeIterator<N>) super.filterOut(predicate);
    }

    @Override
    public NodeIterator<N> filterOut(Class<? super Node> clazz) {
        return (NodeIterator<N>) super.filterOut(clazz);
    }

    @Override
    public <F extends Node> NodeIterator<N> filterOut(Class<F> clazz, Predicate<? super F> predicate) {
        return (NodeIterator<N>) super.filterOut(clazz, predicate);
    }

    @Override
    public NodeIterator<N> filter(Predicate<? super Node> predicate) {
        return (NodeIterator<N>) super.filter(predicate);
    }

    @Override
    public NodeIterator<N> acceptFilter(ValueIterationFilter<? super N> filter) {
        return (NodeIterator<N>) super.acceptFilter(filter);
    }

    // *******************************************************
    //
    // Mapping Functions
    //
    // *******************************************************

    @Override
    public <F extends Node> NodeIterator<F> filter(Class<F> clazz) {
        return (NodeIterator<F>) super.filter(clazz);
    }

    @Override
    public <F extends Node> NodeIterator<F> filter(Class<F> clazz, Predicate<? super F> predicate) {
        return (NodeIterator<F>) super.filter(clazz, predicate);
    }

    @Override
    public <F extends Node> NodeIterator<F> adapt(Function<? super N, F> adapter) {
        return (NodeIterator<F>) super.adapt(adapter);
    }

    @Override
    public <F extends Node> NodeIterator<F> adapt(ValueIterationAdapter<? super N, F> adapter) {
        return (NodeIterator<F>) super.adapt(adapter);
    }

    // *******************************************************
    //
    // Node Looping specific
    //
    // *******************************************************

    public NodeIterator<N> recurse(Set<Class<? super Node>> nodeClassSet) {
        return getModifiedCopyF(myElement, myAdapter, myTreeIterator.recurse(it -> nodeClassSet.contains(it.getClass())));
    }

    public NodeIterator<N> filterOut(Set<Class<? super Node>> nodeClassSet) {
        return getModifiedCopyF(myElement, myAdapter, myTreeIterator.filterOut(it -> nodeClassSet.contains(it.getClass())));
    }

    public NodeIterator<N> filter(Set<Class<? super Node>> nodeClassSet) {
        return getModifiedCopyF(myElement, myAdapter, myTreeIterator.filter(it -> nodeClassSet.contains(it.getClass())));
    }

    public MappedIterator<Object, Node> toNodeObjectMapped() {
        return toObjectMapped(Node.class);
    }

    // *******************************************************
    //
    // Static Factories
    //
    // *******************************************************

    public static NodeIterator<Node> of(Node element, TreeIterator<Node> treeIterator) {
        return new NodeIterator<>(element, ValueIterationAdapterImpl.of(), treeIterator);
    }

    public static NodeIterator<Node> of(Node element, IterationConditions<Node> constraints) {
        return of(element, new TreeIterator<>(constraints));
    }

    public static NodeIterator<Node> of(Node element, IterationConditions<Node> constraints, Predicate<? super Node> filter) {
        return of(element, new TreeIterator<>(constraints, filter));
    }

    public static NodeIterator<Node> of(Node element, IterationConditions<Node> constraints, Predicate<? super Node> filter, Predicate<? super Node> recursion) {
        return of(element, new TreeIterator<>(constraints, filter, recursion));
    }
}

