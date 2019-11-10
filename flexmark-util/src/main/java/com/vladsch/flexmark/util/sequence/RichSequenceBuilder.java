package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * A Builder for Segmented BasedSequences
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
    public RichSequenceBuilder addFrom(@NotNull RichSequenceBuilder builder) {
        segments.append(builder.segments);
        return this;
    }

    @NotNull
    @Override
    public RichSequenceBuilder add(@NotNull CharSequence chars) {
        segments.append(chars);
        return this;
    }

    @NotNull
    @Override
    public RichSequenceBuilder addAll(@NotNull Collection<RichSequence> list) {
        for (RichSequence chars : list) {
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
