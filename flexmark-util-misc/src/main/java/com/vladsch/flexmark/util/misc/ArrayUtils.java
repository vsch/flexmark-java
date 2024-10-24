package com.vladsch.flexmark.util.misc;

import java.util.function.Predicate;

public class ArrayUtils {
  public static boolean contained(int value, int[] array) {
    for (int item : array) {
      if (item == value) {
        return true;
      }
    }
    return false;
  }

  private static <T> T firstOf(
      T[] ts, int fromIndex, int endIndex, Predicate<? super T> predicate) {
    int i = indexOf(ts, fromIndex, endIndex, predicate);
    return i == -1 ? null : ts[i];
  }

  /**
   * @param ts array
   * @param fromIndex the start index from which search in the array. There is no restriction on the
   *     value of {@code fromIndex}. If it is less than 0, it has the same effect as if it were 0.
   *     If it is greater or equal to length of the array, -1 is returned.
   * @param endIndex the end index of the array, ie. treat as if array.length was endIndex. There is
   *     no restriction on the value of {@code endIndex}. If it is greater than or equal to the
   *     length of this array, it has the same effect as if it were equal to length of this array.
   *     If it is negative, it has the same effect as if it were 0: -1 is returned.
   * @param predicate condition for matching the search
   * @param <T> type of array
   * @return the index of the next occurrence of a match in the array which is greater than or equal
   *     to {@code fromIndex}, or {@code -1} if match does not occur after that point.
   */
  private static <T> int indexOf(
      T[] ts, int fromIndex, int endIndex, Predicate<? super T> predicate) {
    int iMax = ts.length;
    if (endIndex > 0) {
      if (fromIndex < 0) fromIndex = 0;
      if (endIndex > iMax) endIndex = iMax;

      if (fromIndex < endIndex) {
        for (int i = fromIndex; i < endIndex; i++) {
          if (predicate.test(ts[i])) {
            return i;
          }
        }
      }
    }
    return -1;
  }

  private ArrayUtils() {
    throw new IllegalStateException();
  }
}
