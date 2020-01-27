package com.vladsch.flexmark.util.dependency;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface Dependent {
    /**
     * @return null or a list of dependents that must be executed before calling this one
     *         if any of the blocks in the list affect global state then these will be run on ALL blocks of the document
     *         before this preprocessor is called.
     */
    @Nullable Set<Class<?>> getAfterDependents();

    /**
     * @return null or a list of dependents that must be executed after calling this one
     *         if any of the blocks in the list affect global state then these will be run on ALL blocks of the document
     *         before this preprocessor is called.
     */
    @Nullable Set<Class<?>> getBeforeDependents();

    /**
     * @return true if this dependent affects the global scope, which means that any that depend on it have to
     *         be run after this dependent has run against all elements. Otherwise, the dependent can run on an element after its dependents
     *         have processed an element.
     *         parsed.
     */
    boolean affectsGlobalScope();
}
