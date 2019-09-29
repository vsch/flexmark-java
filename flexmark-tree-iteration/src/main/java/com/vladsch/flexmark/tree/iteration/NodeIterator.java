package com.vladsch.flexmark.tree.iteration;

import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class NodeIterator<N extends Node> extends MappedIterator<Node, N> {
    public NodeIterator(@NotNull Node element, @NotNull ValueIterationAdapter<? super Node, N> adapter, @NotNull TreeIterator<Node> treeIterator) {
        super(element, adapter, treeIterator);
    }

    // *******************************************************
    //
    // Need Subclass Constructors
    //
    // *******************************************************

    @NotNull
    public NodeIterator<N> getModifiedCopy(Node element, ValueIterationAdapter<? super Node, N> adapter, TreeIterator<Node> treeIterator) {
        return new NodeIterator<>(element, adapter, treeIterator);
    }

    @NotNull
    public <F extends Node> NodeIterator<F> getModifiedCopyF(Node element, ValueIterationAdapter<? super Node, F> adapter, TreeIterator<Node> treeIterator) {
        return new NodeIterator<>(element, adapter, treeIterator);
    }

    // *******************************************************
    //
    // Need Overrides with cast to sub-class
    //
    // *******************************************************

    @NotNull
    @Override
    public NodeIterator<N> reversed() {
        return (NodeIterator<N>) super.reversed();
    }

    @NotNull
    @Override
    public NodeIterator<N> recursive() {
        return (NodeIterator<N>) super.recursive();
    }

    @NotNull
    @Override
    public NodeIterator<N> nonRecursive() {
        return (NodeIterator<N>) super.nonRecursive();
    }

    @NotNull
    @Override
    public NodeIterator<N> recursive(boolean recursive) {
        return (NodeIterator<N>) super.recursive(recursive);
    }

    @NotNull
    @Override
    public NodeIterator<N> nonRecursive(boolean nonRecursive) {
        return (NodeIterator<N>) super.nonRecursive(nonRecursive);
    }

    @NotNull
    @Override
    public NodeIterator<N> recurse(@NotNull Predicate<? super Node> predicate) {
        return (NodeIterator<N>) super.recurse(predicate);
    }

    @NotNull
    @Override
    public NodeIterator<N> recurse(@NotNull Class<? super Node> clazz) {
        return (NodeIterator<N>) super.recurse(clazz);
    }

    @NotNull
    @Override
    public <F extends Node> NodeIterator<N> recurse(@NotNull Class<F> clazz, @NotNull Predicate<? super F> predicate) {
        return (NodeIterator<N>) super.recurse(clazz, predicate);
    }

    @NotNull
    @Override
    public NodeIterator<N> noRecurse(@NotNull Predicate<? super Node> predicate) {
        return (NodeIterator<N>) super.noRecurse(predicate);
    }

    @NotNull
    @Override
    public NodeIterator<N> noRecurse(@NotNull Class<? super Node> clazz) {
        return (NodeIterator<N>) super.noRecurse(clazz);
    }

    @NotNull
    @Override
    public <F extends Node> NodeIterator<N> noRecurse(@NotNull Class<F> clazz, @NotNull Predicate<? super F> predicate) {
        return (NodeIterator<N>) super.noRecurse(clazz, predicate);
    }

    @NotNull
    @Override
    public NodeIterator<N> filterFalse() {
        return (NodeIterator<N>) super.filterFalse();
    }

    @NotNull
    @Override
    public NodeIterator<N> aborted() {
        return (NodeIterator<N>) super.aborted();
    }

    @NotNull
    @Override
    public NodeIterator<N> filterOut(@NotNull Predicate<? super Node> predicate) {
        return (NodeIterator<N>) super.filterOut(predicate);
    }

    @NotNull
    @Override
    public NodeIterator<N> filterOut(@NotNull Class<? super Node> clazz) {
        return (NodeIterator<N>) super.filterOut(clazz);
    }

    @NotNull
    @Override
    public <F extends Node> NodeIterator<N> filterOut(@NotNull Class<F> clazz, @NotNull Predicate<? super F> predicate) {
        return (NodeIterator<N>) super.filterOut(clazz, predicate);
    }

    @NotNull
    @Override
    public NodeIterator<N> filter(@NotNull Predicate<? super Node> predicate) {
        return (NodeIterator<N>) super.filter(predicate);
    }

    @NotNull
    @Override
    public NodeIterator<N> acceptFilter(@NotNull ValueIterationFilter<? super N> filter) {
        return (NodeIterator<N>) super.acceptFilter(filter);
    }

    // *******************************************************
    //
    // Mapping Functions
    //
    // *******************************************************

    @NotNull
    @Override
    public <F extends Node> NodeIterator<F> filter(@NotNull Class<F> clazz) {
        return (NodeIterator<F>) super.filter(clazz);
    }

    @NotNull
    @Override
    public <F extends Node> NodeIterator<F> filter(@NotNull Class<F> clazz, @NotNull Predicate<? super F> predicate) {
        return (NodeIterator<F>) super.filter(clazz, predicate);
    }

    @NotNull
    @Override
    public <F extends Node> NodeIterator<F> adapt(@NotNull Function<? super N, F> adapter) {
        return (NodeIterator<F>) super.adapt(adapter);
    }

    @NotNull
    @Override
    public <F extends Node> NodeIterator<F> adapt(@NotNull ValueIterationAdapter<? super N, F> adapter) {
        return (NodeIterator<F>) super.adapt(adapter);
    }

    // *******************************************************
    //
    // Node Looping specific
    //
    // *******************************************************

    @NotNull
    public NodeIterator<N> recurse(@NotNull Set<Class<? super Node>> nodeClassSet) {
        return getModifiedCopyF(myElement, myAdapter, myTreeIterator.recurse(it -> nodeClassSet.contains(it.getClass())));
    }

    @NotNull
    public NodeIterator<N> filterOut(@NotNull Set<Class<? super Node>> nodeClassSet) {
        return getModifiedCopyF(myElement, myAdapter, myTreeIterator.filterOut(it -> nodeClassSet.contains(it.getClass())));
    }

    @NotNull
    public NodeIterator<N> filter(@NotNull Set<Class<? super Node>> nodeClassSet) {
        return getModifiedCopyF(myElement, myAdapter, myTreeIterator.filter(it -> nodeClassSet.contains(it.getClass())));
    }

    @NotNull
    public MappedIterator<Object, Node> toNodeObjectMapped() {
        return toObjectMapped(Node.class);
    }

    // *******************************************************
    //
    // Static Factories
    //
    // *******************************************************

    public static NodeIterator<Node> of(@NotNull Node element, @NotNull TreeIterator<Node> treeIterator) {
        return new NodeIterator<>(element, ValueIterationAdapterImpl.of(), treeIterator);
    }

    public static NodeIterator<Node> of(@NotNull Node element, @NotNull IterationConditions<Node> constraints) {
        return of(element, new TreeIterator<>(constraints));
    }

    public static NodeIterator<Node> of(@NotNull Node element, @NotNull IterationConditions<Node> constraints, @NotNull Predicate<? super Node> filter) {
        return of(element, new TreeIterator<>(constraints, filter));
    }

    public static NodeIterator<Node> of(@NotNull Node element, @NotNull IterationConditions<Node> constraints, @NotNull Predicate<? super Node> filter, @NotNull Predicate<? super Node> recursion) {
        return of(element, new TreeIterator<>(constraints, filter, recursion));
    }
}

