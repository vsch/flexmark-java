package com.vladsch.flexmark.internal.util.mappers;

import com.vladsch.flexmark.internal.util.Computable;

public class ObjectClassifier implements Computable<Class<?>, Object> {
    final public static ObjectClassifier INSTANCE = new ObjectClassifier();

    private ObjectClassifier() {
    }

    @Override
    public Class<?> compute(Object value) {
        return value.getClass();
    }
}
