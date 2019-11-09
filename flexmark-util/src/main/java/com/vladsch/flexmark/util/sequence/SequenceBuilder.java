package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface SequenceBuilder<T extends SequenceBuilder<T, S>, S extends IRichSequence<S>> {
    @NotNull T subContext();
    @NotNull T addAll(@NotNull T builder);
    @NotNull T addAll(@NotNull Collection<S> list);
    @NotNull T add(@NotNull CharSequence chars);
    @NotNull S toSequence();
    int length();
    boolean isEmpty();

    default boolean isNotEmpty() {
        return !isEmpty();
    }
}
