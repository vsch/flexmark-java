package com.vladsch.flexmark.util;

/**
 * Represents a function that accepts one argument and produces a result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @since 1.8
 * @deprecated use java.util.function.BiConsumer
 */
@Deprecated
public interface Function<T, R> extends java.util.function.Function<T, R> {

}
