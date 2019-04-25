package com.vladsch.flexmark.util.collection;

import java.util.function.Function;

public class CopyOnWriteRef<T> {
    private T myValue;
    private int myReferenceCount;
    private Function<T, T> myCloner;

    public CopyOnWriteRef(T value, Function<T, T> cloner) {
        myValue = value;
        myReferenceCount = 0;
        myCloner = cloner;
    }

    public T getPeek() {
        return myValue;
    }

    public T getImmutable() {
        if (myValue != null) myReferenceCount++;
        return myValue;
    }

    public T getMutable() {
        if (myReferenceCount > 0) {
            myValue = myCloner.apply(myValue);
            myReferenceCount = 0;
        }
        return myValue;
    }

    public void setValue(T value) {
        myReferenceCount = 0;
        myValue = myCloner.apply(value);
    }

    public boolean isMutable() {
        return myReferenceCount == 0;
    }
}
