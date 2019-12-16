package com.vladsch.flexmark.tree.iteration;

import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

public interface VoidIteration {
    Object NULL = new Object();

    /**
     * Complete current iteration, ie. isComplete() will report true and isIncomplete() false
     * <p>
     * Purely a convenience feature to use without needing to track if break or continue have been executed
     */
    void doComplete();

    /**
     * Continue with the next iteration of the given recursion level
     *
     * @param recursionLevel 0 current, &lt;0 previous recursion levels, gt;0 actual recursion level
     */
    void doContinue(int recursionLevel);

    /**
     * Break out of given recursion level
     *
     * @param recursionLevel 0 current, lt;0 previous recursion levels, gt;0 actual recursion level
     */
    void doBreak(int recursionLevel);

    /**
     * Break out of all recursion levels and return current result value for the loop if value loop
     */
    void doReturn();

    /**
     * Continue with next iteration of current recursion level
     */
    default void doContinue() { doContinue(0); }

    /**
     * Break the current recursion level, if last level then same as {@link #doReturn()}
     */
    default void doBreak() { doBreak(0); }

    /**
     * @return true if have next element, does not mean it matches filters, just raw next from loop iterator, fast
     *         check
     */
    boolean getHaveNext();

    /**
     * @return true if have next element and it passes element filters.
     *         <p>
     *         NOTE: {@link ValueIterationConsumerAdapter} not invoked. It is part of the consumer.accept() call
     *         hierarchy and can have code side-effects. Only predicate filters are tested. This does not mean the final
     *         consumer will see this value.
     */
    boolean getHaveAcceptableNext();

    /**
     * @return true if looping terminated by {@link #doReturn()}, or {@link #doBreak()} of the last recursion level.
     */
    boolean isTerminated();

    /**
     * @return true if current iteration is complete ie. had ( Break(), Continue(), Return(), Complete())
     */
    boolean isComplete();

    /**
     * @return true if current iteration is not complete, ie. need to continue processing
     */
    boolean isIncomplete();

    /**
     * Run the passed code only if the current iteration is not complete
     *
     * @param runnable to run
     */
    void ifIncomplete(@NotNull Runnable runnable);

    /**
     * @return times through the loop of the current recursion level, includes skipped elements due to filtering
     */
    int getLoopCount();

    /**
     * @return total times consumer was invoked, ie. valid elements
     */
    int getAcceptCount();

    /**
     * @return current loop count across all recursions
     */
    int getTotalLoopCount();

    /**
     * @return accept count across all recursions
     */
    int getTotalAcceptCount();

    /**
     * @return count of recursion level, can use in Break(recursionLevel) or Continue(recursionLevel) to break/continue
     *         a particular recursion
     */
    int getRecursionLevel();

    /**
     * Per loop instance data instance can be used to store context information
     * <p>
     * Exists between {@link VoidIterationConsumer#beforeStart(VoidIteration)} and {@link ValueIterationConsumer#afterEnd(ValueIteration) }
     * for consumer and
     * <p>
     * lifetime of {@link IteratorInstance} for caller of {@link TreeIterator#iterate}
     *
     * @return per loop instance mutable data holder
     */
    MutableDataHolder getData();
}
