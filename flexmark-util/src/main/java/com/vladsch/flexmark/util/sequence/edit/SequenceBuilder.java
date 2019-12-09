package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.IRichSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SequenceBuilder<T extends SequenceBuilder<T, S>, S extends IRichSequence<S>> {
    /**
     * NOTE: returns non-null value if the underlying {@link IBasedSegmentBuilder#getBaseSubSequenceRange()} returns non-null value
     *
     * @return sub-sequence of base representing the single segment or null if sequence not representable by a single subsequence
     */
    @Nullable S getSingleBasedSequence();
    @NotNull T subContext();
    @NotNull T addAll(Iterable<? extends CharSequence> sequences);
    @NotNull T add(@Nullable CharSequence chars);
    @NotNull T append(char c);
    @NotNull T append(char c, int count);
    @NotNull S toSequence();
    int length();
    boolean isEmpty();

    default boolean isNotEmpty() {
        return !isEmpty();
    }
}
