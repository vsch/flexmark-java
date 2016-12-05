package com.vladsch.flexmark.util.mappers;

import com.vladsch.flexmark.util.Computable;

public class ObjectClassifier implements Computable<Class<?>, Object> {
    public static final ObjectClassifier INSTANCE = new ObjectClassifier();

    private ObjectClassifier() {
    }

    @Override
    public Class<?> compute(Object value) {
        return value.getClass();
    }
}
