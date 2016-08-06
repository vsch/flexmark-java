package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.Computable;

public class CopyOnWriteRef<T> {
    private T myValue; 
    private int myReferenceCount;
    private Computable<T,T> myCloner;
    private boolean myMutable;

    public CopyOnWriteRef(T value, Computable<T,T> cloner) {
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
            myValue = myCloner.compute(myValue);
            myReferenceCount = 0;
        }
        return myValue;
    }
    
    public void setValue(T value) {
        myReferenceCount = 0;
        myValue = myCloner.compute(value);
    }

    public boolean isMutable() {
        return myReferenceCount == 0;
    }
}
