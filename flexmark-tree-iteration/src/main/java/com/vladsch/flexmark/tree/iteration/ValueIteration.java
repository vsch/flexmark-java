package com.vladsch.flexmark.tree.iteration;

import org.jetbrains.annotations.NotNull;

public interface ValueIteration<R> extends VoidIteration {
    /**
     * Set the result which will be returned by the loop if terminated without {@link #Return(Object)} call.
     *
     * @param value value to return from the loop
     */
    void setResult(@NotNull R value);

    /**
     * @return true if the current loop result value was never set (ie. only set at loop instantiation with
     *         defaultValue)
     *         <p>
     *         NOTE: does not test current value with equality to default value, set by {@link #setResult(Object)} or {@link
     *         #Return(Object)}
     */
    boolean isDefaultResult();       // true if value never set other than initial default setting

    /**
     * @return defaultValue passed to loop instance
     */
    @NotNull
    R getDefaultValue();

    /**
     * @return current result value
     */
    @NotNull
    R getResult();

    /**
     * Set result value and terminate all recursions
     *
     * @param value value to return for the result of the loop
     */
    void Return(@NotNull R value);
}
