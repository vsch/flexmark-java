package com.vladsch.flexmark.util.mappers;

import java.util.function.Function;

public class ObjectClassifier implements Function<Object, Class<?>> {
    public static final ObjectClassifier INSTANCE = new ObjectClassifier();

    private ObjectClassifier() {
    }

    @Override
    public Class<?> apply(Object value) {
        return value.getClass();
    }
}
