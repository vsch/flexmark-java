package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.IRichSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface SequenceBuilder<T extends SequenceBuilder<T, S>, S extends IRichSequence<S>> {
    @NotNull T subContext();
    @NotNull T addAll(@NotNull Collection<? extends CharSequence> sequences);
    @NotNull T add(@Nullable CharSequence chars);
    @NotNull S toSequence();
    int length();
    boolean isEmpty();

    default boolean isNotEmpty() {
        return !isEmpty();
    }
}
