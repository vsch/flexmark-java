package com.vladsch.flexmark.util.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CopyOnWriteRef<T> {
    private @Nullable T myValue;
    private int myReferenceCount;
    private @NotNull Function<T, T> myCloner;

    public CopyOnWriteRef(@Nullable T value, @NotNull Function<T, T> cloner) {
        myValue = value;
        myReferenceCount = 0;
        myCloner = cloner;
    }

    public @Nullable T getPeek() {
        return myValue;
    }

    public @Nullable T getImmutable() {
        if (myValue != null) myReferenceCount++;
        return myValue;
    }

    public @Nullable T getMutable() {
        if (myReferenceCount > 0) {
            myValue = myCloner.apply(myValue);
            myReferenceCount = 0;
        }
        return myValue;
    }

    public void setValue(@Nullable T value) {
        myReferenceCount = 0;
        myValue = myCloner.apply(value);
    }

    public boolean isMutable() {
        return myReferenceCount == 0;
    }
}
