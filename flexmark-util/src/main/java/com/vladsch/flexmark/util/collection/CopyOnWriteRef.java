package com.vladsch.flexmark.util.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CopyOnWriteRef<T> {
    private @Nullable T value;
    private int referenceCount;
    final private @NotNull Function<T, T> copyFunction;

    public CopyOnWriteRef(@Nullable T value, @NotNull Function<T, T> copyFunction) {
        this.value = value;
        referenceCount = 0;
        this.copyFunction = copyFunction;
    }

    public @Nullable T getPeek() {
        return value;
    }

    public @Nullable T getImmutable() {
        if (value != null) referenceCount++;
        return value;
    }

    public @Nullable T getMutable() {
        if (referenceCount > 0) {
            value = copyFunction.apply(value);
            referenceCount = 0;
        }
        return value;
    }

    public void setValue(@Nullable T value) {
        referenceCount = 0;
        this.value = copyFunction.apply(value);
    }

    public boolean isMutable() {
        return referenceCount == 0;
    }
}
