package com.vladsch.flexmark.util.misc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.BitSet;
import java.util.Objects;
import java.util.function.Predicate;

public class ArrayUtils {
    public static <T> boolean contained(T value, T[] array) {
        return indexOf(value, array) != -1;
    }

    @SafeVarargs
    public static <T> T[] append(Class<T> elemClass, T[] array, T... values) {
        if (values.length > 0) {
            //noinspection unchecked
            T[] newInstance = (T[]) Array.newInstance(elemClass, array.length + values.length);
            System.arraycopy(array, 0, newInstance, 0, array.length);
            System.arraycopy(values, 0, newInstance, array.length, values.length);
            return newInstance;
        }
        return array;
    }

    public static boolean contained(int value, int[] array) {
        for (int item : array) {
            if (item == value) return true;
        }
        return false;
    }

    public static <T> T firstOf(T[] ts, Predicate<? super T> predicate) { return firstOf(ts, 0, ts.length, predicate); }

    public static <T> T firstOf(T[] ts, int fromIndex, Predicate<? super T> predicate) { return firstOf(ts, fromIndex, ts.length, predicate); }

    @Nullable
    public static <T> T firstOf(T[] ts, int fromIndex, int endIndex, Predicate<? super T> predicate) {
        int i = indexOf(ts, fromIndex, endIndex, predicate);
        return i == -1 ? null : ts[i];
    }

    public static <T> int indexOf(T t, T[] ts) { return indexOf(t, ts, 0, ts.length); }

    public static <T> int indexOf(T t, T[] ts, int fromIndex) { return indexOf(t, ts, fromIndex, ts.length); }

    public static <T> int indexOf(T t, T[] ts, int fromIndex, int endIndex) {
        return indexOf(ts, fromIndex, endIndex, t1 -> Objects.equals(t, t1));
    }

    public static <T> int indexOf(T[] ts, Predicate<? super T> predicate) { return indexOf(ts, 0, ts.length, predicate); }

    public static <T> int indexOf(T[] ts, int fromIndex, Predicate<? super T> predicate) { return indexOf(ts, fromIndex, ts.length, predicate); }

    /**
     * @param ts        array
     * @param fromIndex the start index from which search in the array. There is no
     *                  restriction on the value of {@code fromIndex}.
     *                  If it is less than 0, it has the same effect as if it were 0.
     *                  If it is greater or equal to length of the array, -1 is returned.
     * @param endIndex  the end index of the array, ie. treat as if array.length was endIndex.
     *                  There is no restriction on the value of {@code endIndex}. If it is
     *                  greater than or equal to the length of this array, it has
     *                  the same effect as if it were equal to length of this array.
     *                  If it is negative, it has the same effect as if it were 0: -1 is returned.
     * @param predicate condition for matching the search
     * @param <T>       type of array
     *
     * @return the index of the next occurrence of a match in the array which is
     *         greater than or equal to {@code fromIndex}, or {@code -1}
     *         if match does not occur after that point.
     */
    public static <T> int indexOf(T[] ts, int fromIndex, int endIndex, Predicate<? super T> predicate) {
        int iMax = ts.length;
        if (endIndex > 0) {
            if (fromIndex < 0) fromIndex = 0;
            if (endIndex > iMax) endIndex = iMax;

            if (fromIndex < endIndex) {
                for (int i = fromIndex; i < endIndex; i++) {
                    if (predicate.test(ts[i])) return i;
                }
            }
        }
        return -1;
    }

    public static <T> T lastOf(T[] ts, Predicate<? super T> predicate) { return lastOf(ts, 0, ts.length, predicate); }

    public static <T> T lastOf(T[] ts, int fromIndex, Predicate<? super T> predicate) { return lastOf(ts, 0, fromIndex, predicate); }

    public static <T> T lastOf(T[] ts, int startIndex, int fromIndex, Predicate<? super T> predicate) {
        int i = lastIndexOf(ts, startIndex, fromIndex, predicate);
        return i == -1 ? null : ts[i];
    }

    public static <T> int lastIndexOf(T t, T[] ts) { return lastIndexOf(t, ts, 0, ts.length); }

    public static <T> int lastIndexOf(T t, T[] ts, int fromIndex) { return lastIndexOf(t, ts, 0, fromIndex); }

    public static <T> int lastIndexOf(T t, T[] ts, int startIndex, int fromIndex) {
        return lastIndexOf(ts, startIndex, fromIndex, t1 -> Objects.equals(t, t1));
    }

    public static <T> int lastIndexOf(T[] ts, Predicate<? super T> predicate) { return lastIndexOf(ts, 0, ts.length, predicate); }

    public static <T> int lastIndexOf(T[] ts, int fromIndex, Predicate<? super T> predicate) { return lastIndexOf(ts, 0, fromIndex, predicate); }

    /**
     * @param ts         array
     * @param startIndex the minimum index to search in the array. There is no
     *                   restriction on the value of {@code startIndex}.
     *                   If it is less than 0, it has the same effect as if it were 0.
     *                   If it is greater or equal to length of the array, -1 is returned.
     * @param fromIndex  the index to start the search from. There is no
     *                   restriction on the value of {@code fromIndex}. If it is
     *                   greater than or equal to the length of this array, it has
     *                   the same effect as if it were equal to one less than the
     *                   length of this array: this entire array may be searched.
     *                   If it is negative, it has the same effect as if it were -1:
     *                   -1 is returned.
     * @param predicate  condition for matching the search
     * @param <T>        type of array
     *
     * @return the index of the last occurrence of a match in the array which is
     *         less than or equal to {@code fromIndex}, or {@code -1}
     *         if match does not occur before that point.
     */
    public static <T> int lastIndexOf(T[] ts, int startIndex, int fromIndex, Predicate<? super T> predicate) {
        int iMax = ts.length;
        if (fromIndex >= 0) {
            if (startIndex < 0) startIndex = 0;
            if (fromIndex >= iMax) fromIndex = iMax - 1;

            if (startIndex < fromIndex) {
                for (int i = fromIndex; i >= startIndex; i--) {
                    if (predicate.test(ts[i])) return i;
                }
            }
        }
        return -1;
    }

    public static int[] toArray(@NotNull BitSet bitSet) {
        int i = bitSet.cardinality();
        int[] bits = new int[i];

        int lastSet = bitSet.length();
        while (lastSet >= 0) {
            lastSet = bitSet.previousSetBit(lastSet - 1);
            if (lastSet < 0) break;

            bits[--i] = lastSet;
        }
        assert i == 0;
        return bits;
    }
}
