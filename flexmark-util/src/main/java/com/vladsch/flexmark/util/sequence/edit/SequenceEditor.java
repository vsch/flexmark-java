package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.IRichSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface SequenceEditor<T extends SequenceEditor<T, S>, S extends IRichSequence<S>> extends SequenceBuilder<T, S> {
    @NotNull
    @Override
    T subContext();

    @NotNull
    @Override
    T addFrom(@NotNull T other);

    @NotNull
    @Override
    T addAll(@NotNull Collection<? extends CharSequence> sequences);

    @NotNull
    @Override
    T add(@Nullable CharSequence chars);

    @NotNull
    @Override
    S toSequence();

    @Override
    int length();

    @Override
    boolean isEmpty();

    // editing methods
    @NotNull T insert(int index, @NotNull CharSequence chars);
    @NotNull T delete(int startIndex, int endIndex);
    @NotNull T replace(int startIndex, int endIndex, @NotNull CharSequence replacement);

    @NotNull T insertAt(int index, char c);
    @NotNull T deleteAt(int index);
    @NotNull T replaceAt(int index, char c);

    // editing tracked
}
