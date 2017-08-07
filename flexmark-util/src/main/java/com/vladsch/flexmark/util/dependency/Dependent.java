package com.vladsch.flexmark.util.dependency;

import java.util.Set;

public interface Dependent<S> {
    /**
     * @return null or a list of processors that must be executed before calling this one
     * if any of the blocks in the list affect global state then these will be run on ALL blocks of the document
     * before this pre processor is called.
     */
    Set<? extends Class> getAfterDependents();

    /**
     * @return null or a list of processors before which this has to be run
     * if any of the blocks in the list affect global state then these will be run on ALL blocks of the document
     * before this pre processor is called.
     */
    Set<? extends Class> getBeforeDependents();

    /**
     * @return true if this dependent affects the global scope, which means that any that depend on it have to
     * be run after this dependent has run against all elements. Otherwise, the dependent can run on an element after its dependents
     * have processed an element.
     * parsed.
     */
    boolean affectsGlobalScope();
}
