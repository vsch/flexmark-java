package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

public interface SequenceBuilder<T extends SequenceBuilder<T,S>, S extends IRichSequence<S>> {
    @NotNull T subContext();
    @NotNull T append(@NotNull T other);
    @NotNull T append(@NotNull CharSequence s);
    @NotNull S toSequence();
    int length();
    boolean isEmpty();
}
