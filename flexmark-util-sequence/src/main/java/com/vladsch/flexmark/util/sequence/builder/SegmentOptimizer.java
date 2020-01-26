package com.vladsch.flexmark.util.sequence.builder;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.BiFunction;

public interface SegmentOptimizer extends BiFunction<CharSequence, Object[], Object[]> {
    /**
     * Optimize segment BASE parts surrounding TEXT contained in Object[] array.
     *
     * @param chars   base character sequence
     * @param objects parts to optimize
     *                Object[0] - previous BASE Range, will be Range.NULL if no previous range
     *                Object[1] - char sequence of TEXT to optimize
     *                Object[2] - next BASE Range, will be Range.NULL if no next range
     * @return Object[] containing optimized segments, non-null Range(s) are BASE segments, CharSequence(s) are TEXT segments
     *         null entry ignored, an optimal filler for unused entries
     *         Range with -ve start/end or -ve span are skipped
     *         CharSequence with 0 length skipped
     */
    @Override
    Object[] apply(@NotNull CharSequence chars, Object[] objects);

    /**
     * Insert a null at index in given parts array
     *
     * @param parts input array
     * @param index index where to insert
     * @return copy of input array with extra element inserted at index
     */
    @NotNull
    static Object[] insert(@NotNull Object[] parts, int index) {
        if (index < parts.length) {
            Object[] newParts = new Object[parts.length + 1];
            if (index == 0) {
                System.arraycopy(parts, 0, newParts, 1, parts.length);
            } else {
                System.arraycopy(parts, 0, newParts, 0, index);
                System.arraycopy(parts, index, newParts, index + 1, parts.length - index);
            }
            return newParts;
        } else {
            return Arrays.copyOf(parts, parts.length + 1);
        }
    }
}
