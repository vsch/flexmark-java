package com.vladsch.flexmark.experimental.util.mappers;

import java.util.function.Function;

public class ObjectClassifier implements Function<Object, Class<?>> {
    final public static ObjectClassifier INSTANCE = new ObjectClassifier();

    private ObjectClassifier() {
    }

    @Override
    public Class<?> apply(Object value) {
        return value.getClass();
    }
}
