package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface SequenceBuilder<T extends SequenceBuilder<T, S>, S extends IRichSequence<S>> {
    @NotNull T subContext();
    @NotNull T addFrom(@NotNull T builder);
    @NotNull T addAll(@NotNull Collection<? extends CharSequence> list);
    @NotNull T add(@Nullable CharSequence chars);
    @NotNull S toSequence();
    int length();
    boolean isEmpty();

    default boolean isNotEmpty() {
        return !isEmpty();
    }
}
