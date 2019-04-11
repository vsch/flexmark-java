package com.vladsch.flexmark.util;

/**
 * Represents a function that accepts two arguments and produces a result.
 * This is the two-arity specialization of {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @deprecated use java.util.function.BiConsumer
 */
@Deprecated
public interface BiFunction<T, U, R> extends java.util.function.BiFunction<T, U, R> {
    
}
