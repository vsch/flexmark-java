package com.vladsch.flexmark.util.sequence.builder;

import org.jspecify.annotations.Nullable;

public interface ISequenceBuilder<T extends ISequenceBuilder<T, S>, S extends CharSequence> extends Appendable {
    /**
     * NOTE: returns non-null value if the underlying {@link IBasedSegmentBuilder#getBaseSubSequenceRange()} returns non-null value
     *
     * @return sub-sequence of base representing the single segment or null if sequence not representable by a single subsequence
     */
    @Nullable S getSingleBasedSequence();

    T getBuilder();

    default T addAll(Iterable<? extends CharSequence> sequences) {
        return append(sequences);
    }

    char charAt(int index);

    default T append(Iterable<? extends CharSequence> sequences) {
        for (CharSequence chars : sequences) {
            append(chars, 0, chars.length());
        }
        //noinspection unchecked
        return (T) this;
    }

    default T add(@Nullable CharSequence chars) {
        return append(chars);
    }

    default T append(@Nullable CharSequence chars) {
        if (chars != null) {
            return append(chars, 0, chars.length());
        }
        //noinspection unchecked
        return (T) this;
    }

    default T append(@Nullable CharSequence chars, int startIndex) {
        if (chars != null) {
            return append(chars, startIndex, chars.length());
        }
        //noinspection unchecked
        return (T) this;
    }

    T append(@Nullable CharSequence chars, int startIndex, int endIndex);

    T append(char c);

    T append(char c, int count);

    S toSequence();

    int length();

    default boolean isEmpty() {
        return length() <= 0;
    }

    default boolean isNotEmpty() {
        return length() > 0;
    }
}
