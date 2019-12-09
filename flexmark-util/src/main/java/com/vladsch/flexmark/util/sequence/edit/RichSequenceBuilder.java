package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.RichSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Builder for non based strings. Just a string builder wrapped in a sequence builder interface
 */
public final class RichSequenceBuilder implements SequenceBuilder<RichSequenceBuilder, RichSequence> {
    private final StringBuilder segments;

    public RichSequenceBuilder() {
        this.segments = new StringBuilder();
    }

    public RichSequenceBuilder(int initialCapacity) {
        this.segments = new StringBuilder(initialCapacity);
    }

    @NotNull
    public RichSequenceBuilder subContext() {
        return new RichSequenceBuilder();
    }

    @NotNull
    @Override
    public RichSequenceBuilder add(@Nullable CharSequence chars) {
        if (chars != null && chars.length() > 0) {
            segments.append(chars);
        }
        return this;
    }

    @NotNull
    @Override
    public RichSequenceBuilder append(char c) {
        segments.append(c);
        return this;
    }

    @NotNull
    @Override
    public RichSequenceBuilder append(char c, int count) {
        while (count-- > 0) segments.append(c);
        return this;
    }

    @NotNull
    @Override
    public RichSequence getSingleBasedSequence() {
        return toSequence();
    }

    @NotNull
    @Override
    public RichSequenceBuilder addAll(Iterable<? extends CharSequence> sequences) {
        for (CharSequence chars : sequences) {
            add(chars);
        }
        return this;
    }

    @NotNull
    @Override
    public RichSequence toSequence() {
        return RichSequence.of(segments);
    }

    @Override
    public int length() {
        return segments.length();
    }

    @Override
    public boolean isEmpty() {
        return segments.length() > 0;
    }

    @Override
    public String toString() {
        return segments.toString();
    }
}
