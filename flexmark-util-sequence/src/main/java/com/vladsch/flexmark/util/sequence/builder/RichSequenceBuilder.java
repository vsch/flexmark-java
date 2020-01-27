package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.sequence.RichSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Builder for non based strings. Just a string builder wrapped in a sequence builder interface and wrapping result in RichSequence
 */
final public class RichSequenceBuilder implements ISequenceBuilder<RichSequenceBuilder, RichSequence> {
    @NotNull
    public static RichSequenceBuilder emptyBuilder() {
        return new RichSequenceBuilder();
    }

    final private StringBuilder segments;

    private RichSequenceBuilder() {
        this.segments = new StringBuilder();
    }

    public RichSequenceBuilder(int initialCapacity) {
        this.segments = new StringBuilder(initialCapacity);
    }

    @NotNull
    public RichSequenceBuilder getBuilder() {
        return new RichSequenceBuilder();
    }

    @Override
    public char charAt(int index) {
        return segments.charAt(index);
    }

    @NotNull
    @Override
    public RichSequenceBuilder append(@Nullable CharSequence chars, int startIndex, int endIndex) {
        if (chars != null && chars.length() > 0 && startIndex < endIndex) {
            segments.append(chars, startIndex, endIndex);
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
    public RichSequence toSequence() {
        return RichSequence.of(segments);
    }

    @Override
    public int length() {
        return segments.length();
    }

    @Override
    public String toString() {
        return segments.toString();
    }
}
