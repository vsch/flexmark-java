package com.vladsch.flexmark.util;

/**
 * Represents an operation that accepts a single input argument and returns no
 * result. Unlike most other functional interfaces, {@code Consumer} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object)}.
 *
 * @param <T> the type of the input to the operation
 * @deprecated use java.util.function.BiConsumer
 */
@Deprecated
public interface Consumer<T> extends java.util.function.Consumer<T> {
    
}
