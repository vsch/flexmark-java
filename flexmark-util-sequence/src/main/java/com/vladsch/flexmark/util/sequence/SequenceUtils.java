package com.vladsch.flexmark.util.sequence;

import static com.vladsch.flexmark.util.misc.Utils.rangeLimit;

import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Pair;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface SequenceUtils {
  String EOL = "\n";
  String SPACE = " ";
  String ANY_EOL = "\r\n";

  char EOL_CHAR = ANY_EOL.charAt(1);
  char SPC = ' ';
  char NUL = '\0';
  char ENC_NUL = '\uFFFD';
  char NBSP = '\u00A0';
  char LS = '\u2028'; // line separator
  char US =
      '\u001f'; // US or USEP - Unit Separator, also used as IntelliJDummyIdentifier in Parsings,
  // used as a tracked offset marker in the sequence

  String LINE_SEP = Character.toString(LS);
  String US_CHARS = Character.toString(US);

  int SPLIT_INCLUDE_DELIMS = 1; // include delimiters as part of the split out part
  int SPLIT_TRIM_PARTS = 2; // trim split parts
  int SPLIT_SKIP_EMPTY = 4; // skip empty trimmed parts
  int SPLIT_INCLUDE_DELIM_PARTS = 8; // include split out delimiters as parts themselves

  static Map<Character, String> getVisibleSpacesMap() {
    Map<Character, String> charMap = new HashMap<>();
    charMap.put('\n', "\\n");
    charMap.put('\r', "\\r");
    charMap.put('\f', "\\f");
    charMap.put('\t', "\\u2192");
    charMap.put(LS, "\u27a5");
    return charMap;
  }

  Map<Character, String> visibleSpacesMap = getVisibleSpacesMap();

  int[] EMPTY_INDICES = {};

  /**
   * Get a portion of this sequence selected by range
   *
   * @param <T> type of character sequence
   * @param thizz char sequence
   * @param range range to get, coordinates offset form start of this sequence
   * @return sequence whose contents reflect the selected portion, if range.isNull() then this is
   *     returned
   */
  static <T extends CharSequence> T subSequence(T thizz, Range range) {
    return range.isNull() ? (T) thizz : (T) thizz.subSequence(range.getStart(), range.getEnd());
  }

  static int indexOf(CharSequence thizz, CharSequence s) {
    return indexOf(thizz, s, 0, Integer.MAX_VALUE);
  }

  static int indexOf(CharSequence thizz, CharSequence s, int fromIndex) {
    return indexOf(thizz, s, fromIndex, Integer.MAX_VALUE);
  }

  static int indexOf(CharSequence thizz, char c) {
    return indexOf(thizz, c, 0, Integer.MAX_VALUE);
  }

  static int indexOf(CharSequence thizz, char c, int fromIndex) {
    return indexOf(thizz, c, fromIndex, Integer.MAX_VALUE);
  }

  static int indexOfAny(CharSequence thizz, CharPredicate s) {
    return indexOfAny(thizz, s, 0, Integer.MAX_VALUE);
  }

  static int indexOfAny(CharSequence thizz, CharPredicate s, int index) {
    return indexOfAny(thizz, s, index, Integer.MAX_VALUE);
  }

  static int indexOfAnyNot(CharSequence thizz, CharPredicate s) {
    return indexOfAny(thizz, s.negate(), 0, Integer.MAX_VALUE);
  }

  static int indexOfAnyNot(CharSequence thizz, CharPredicate s, int fromIndex) {
    return indexOfAny(thizz, s.negate(), fromIndex, Integer.MAX_VALUE);
  }

  static int indexOfAnyNot(CharSequence thizz, CharPredicate s, int fromIndex, int endIndex) {
    return indexOfAny(thizz, s.negate(), fromIndex, endIndex);
  }

  static int indexOfNot(CharSequence thizz, char c) {
    return indexOfNot(thizz, c, 0, Integer.MAX_VALUE);
  }

  static int indexOfNot(CharSequence thizz, char c, int fromIndex) {
    return indexOfNot(thizz, c, fromIndex, Integer.MAX_VALUE);
  }

  static int lastIndexOf(CharSequence thizz, CharSequence s) {
    return lastIndexOf(thizz, s, 0, Integer.MAX_VALUE);
  }

  static int lastIndexOf(CharSequence thizz, CharSequence s, int fromIndex) {
    return lastIndexOf(thizz, s, 0, fromIndex);
  }

  static int lastIndexOf(CharSequence thizz, char c) {
    return lastIndexOf(thizz, c, 0, Integer.MAX_VALUE);
  }

  static int lastIndexOf(CharSequence thizz, char c, int fromIndex) {
    return lastIndexOf(thizz, c, 0, fromIndex);
  }

  static int lastIndexOfAny(CharSequence thizz, CharPredicate s) {
    return lastIndexOfAny(thizz, s, 0, Integer.MAX_VALUE);
  }

  static int lastIndexOfAny(CharSequence thizz, CharPredicate s, int fromIndex) {
    return lastIndexOfAny(thizz, s, 0, fromIndex);
  }

  static int lastIndexOfAnyNot(CharSequence thizz, CharPredicate s) {
    return lastIndexOfAny(thizz, s.negate(), 0, Integer.MAX_VALUE);
  }

  static int lastIndexOfAnyNot(CharSequence thizz, CharPredicate s, int fromIndex) {
    return lastIndexOfAny(thizz, s.negate(), 0, fromIndex);
  }

  static int lastIndexOfAnyNot(CharSequence thizz, CharPredicate s, int startIndex, int fromIndex) {
    return lastIndexOfAny(thizz, s.negate(), startIndex, fromIndex);
  }

  static int lastIndexOfNot(CharSequence thizz, char c) {
    return lastIndexOfNot(thizz, c, 0, Integer.MAX_VALUE);
  }

  static int lastIndexOfNot(CharSequence thizz, char c, int fromIndex) {
    return lastIndexOfNot(thizz, c, 0, fromIndex);
  }

  static int indexOf(CharSequence thizz, char c, int fromIndex, int endIndex) {
    fromIndex = Math.max(fromIndex, 0);
    endIndex = Math.min(thizz.length(), endIndex);

    for (int i = fromIndex; i < endIndex; i++) {
      if (c == thizz.charAt(i)) {
        return i;
      }
    }
    return -1;
  }

  static int indexOf(CharSequence thizz, CharSequence s, int fromIndex, int endIndex) {
    fromIndex = Math.max(fromIndex, 0);

    int sMax = s.length();
    if (sMax == 0) {
      return fromIndex;
    }
    endIndex = Math.min(thizz.length(), endIndex);

    if (fromIndex < endIndex) {
      char firstChar = s.charAt(0);
      int pos = fromIndex;

      do {
        pos = indexOf(thizz, firstChar, pos);
        if (pos < 0 || pos + sMax > endIndex) {
          break;
        }
        if (matchChars(thizz, s, pos)) {
          return pos;
        }
        pos++;
      } while (pos + sMax < endIndex);
    }

    return -1;
  }

  static int lastIndexOf(CharSequence thizz, char c, int startIndex, int fromIndex) {
    fromIndex = Math.min(fromIndex, thizz.length() - 1);
    fromIndex++;

    startIndex = Math.max(startIndex, 0);

    for (int i = fromIndex; i-- > startIndex; ) {
      if (c == thizz.charAt(i)) {
        return i;
      }
    }
    return -1;
  }

  static int indexOfNot(CharSequence thizz, char c, int fromIndex, int endIndex) {
    fromIndex = Math.max(fromIndex, 0);
    endIndex = Math.min(endIndex, thizz.length());

    for (int i = fromIndex; i < endIndex; i++) {
      if (thizz.charAt(i) != c) {
        return i;
      }
    }
    return -1;
  }

  static int indexOfAny(CharSequence thizz, CharPredicate s, int fromIndex, int endIndex) {
    fromIndex = Math.max(fromIndex, 0);
    endIndex = Math.min(endIndex, thizz.length());

    for (int i = fromIndex; i < endIndex; i++) {
      char c = thizz.charAt(i);
      if (s.test(c)) {
        return i;
      }
    }
    return -1;
  }

  // TEST:
  static int lastIndexOf(CharSequence thizz, CharSequence s, int startIndex, int fromIndex) {
    startIndex = Math.max(startIndex, 0);

    int sMax = s.length();
    if (sMax == 0) {
      return startIndex;
    }

    fromIndex = Math.min(fromIndex, thizz.length());

    if (startIndex < fromIndex) {
      int pos = fromIndex;
      char lastChar = s.charAt(sMax - 1);

      do {
        pos = lastIndexOf(thizz, lastChar, pos);
        if (pos + 1 < startIndex + sMax) {
          break;
        }
        if (matchCharsReversed(thizz, s, pos)) {
          return pos + 1 - sMax;
        }
        pos--;
      } while (pos + 1 >= startIndex + sMax);
    }

    return -1;
  }

  // TEST:
  static int lastIndexOfNot(CharSequence thizz, char c, int startIndex, int fromIndex) {
    fromIndex = Math.min(fromIndex, thizz.length() - 1);
    fromIndex++;

    startIndex = Math.max(startIndex, 0);

    for (int i = fromIndex; i-- > startIndex; ) {
      if (thizz.charAt(i) != c) {
        return i;
      }
    }
    return -1;
  }

  // TEST:
  static int lastIndexOfAny(CharSequence thizz, CharPredicate s, int startIndex, int fromIndex) {
    fromIndex = Math.min(fromIndex, thizz.length() - 1);
    fromIndex++;

    startIndex = Math.max(startIndex, 0);

    for (int i = fromIndex; i-- > startIndex; ) {
      char c = thizz.charAt(i);
      if (s.test(c)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Equality comparison based on character content of this sequence, with quick fail resorting to
   * content comparison only if length and hashCodes are equal
   *
   * @param thizz char sequence to test for equality
   * @param o any character sequence
   * @return true if character contents are equal
   */
  static boolean equals(CharSequence thizz, Object o) {
    // do quick failure of equality
    if (o == thizz) {
      return true;
    }
    if (!(o instanceof CharSequence)) {
      return false;
    }

    CharSequence chars = (CharSequence) o;
    if (chars.length() != thizz.length()) {
      return false;
    }

    if (o instanceof String) {
      String other = (String) o;
      if (other.hashCode() != thizz.hashCode()) {
        return false;
      }

      // fall through to slow content comparison
    } else if (o instanceof IRichSequence) {
      IRichSequence<?> other = (IRichSequence<?>) o;
      if (other.hashCode() != thizz.hashCode()) {
        return false;
      }

      // fall through to slow content comparison
    }

    return matchChars(thizz, chars, 0, false);
  }

  public static int hashCode(CharSequence thizz) {
    int h = 0;
    int length = thizz.length();
    if (length > 0) {
      for (int i = 0; i < length; i++) {
        h = 31 * h + thizz.charAt(i);
      }
    }
    return h;
  }

  static int compare(CharSequence o1, CharSequence o2) {
    return compare(o1, o2, false);
  }

  static int compare(CharSequence o1, CharSequence o2, boolean ignoreCase) {
    return compare(o1, o2, ignoreCase, null);
  }

  static int compare(
      CharSequence o1, CharSequence o2, boolean ignoreCase, CharPredicate ignoreChars) {
    if (o1 == null || o2 == null) {
      return o1 == null && o2 == null ? 0 : o1 == null ? -1 : 1;
    }

    int len1 = o1.length();
    int len2 = o2.length();
    int iMax = Math.min(len1, len2);
    if (ignoreCase) {
      for (int i = 0; i < iMax; i++) {
        char c1 = o1.charAt(i);
        char c2 = o2.charAt(i);
        if (c1 != c2) {
          char u1 = Character.toUpperCase(c1);
          char u2 = Character.toUpperCase(c2);
          if (u1 == u2) {
            continue;
          }

          // Unfortunately, conversion to uppercase does not work properly
          // for the Georgian alphabet, which has strange rules about case
          // conversion. So we need to make one last check before exiting.
          if (Character.toLowerCase(u1) == Character.toLowerCase(u2)) {
            continue;
          }

          // NOTE: if both chars are in the ignore set, then it is a match
          if (ignoreChars == null || !(ignoreChars.test(c1) && ignoreChars.test(c2))) {
            return c1 - c2;
          }
        }
      }
    } else {
      for (int i = 0; i < iMax; i++) {
        char c1 = o1.charAt(i);
        char c2 = o2.charAt(i);
        if (c1 != c2) {
          // NOTE: if both chars are in the ignore set, then it is a match
          if (ignoreChars == null || !(ignoreChars.test(c1) && ignoreChars.test(c2))) {
            return c1 - c2;
          }
        }
      }
    }
    return len1 - len2;
  }

  static int columnsToNextTabStop(int column) {
    // Tab stop is 4
    return 4 - (column % 4);
  }

  static int[] expandTo(int[] indices, int length, int step) {
    int remainder = length & step;
    int next = length + (remainder != 0 ? step : 0);
    if (indices.length < next) {
      int[] replace = new int[next];
      System.arraycopy(indices, 0, replace, 0, indices.length);
      return replace;
    }
    return indices;
  }

  static int[] truncateTo(int[] indices, int length) {
    if (indices.length > length) {
      int[] replace = new int[length];
      System.arraycopy(indices, 0, replace, 0, length);
      return replace;
    }
    return indices;
  }

  static int[] indexOfAll(CharSequence thizz, CharSequence s) {
    int length = s.length();
    if (length == 0) {
      return SequenceUtils.EMPTY_INDICES;
    }
    int pos = indexOf(thizz, s);
    if (pos == -1) {
      return SequenceUtils.EMPTY_INDICES;
    }

    int iMax = 0;
    int[] indices = new int[32];
    indices[iMax++] = pos;

    while (true) {
      pos = indexOf(thizz, s, pos + length);
      if (pos == -1) {
        break;
      }
      if (indices.length <= iMax) {
        indices = expandTo(indices, iMax + 1, 32);
      }
      indices[iMax++] = pos;
    }
    return truncateTo(indices, iMax);
  }

  static boolean matches(CharSequence thizz, CharSequence chars, boolean ignoreCase) {
    return chars.length() == thizz.length() && matchChars(thizz, chars, 0, ignoreCase);
  }

  static boolean matches(CharSequence thizz, CharSequence chars) {
    return chars.length() == thizz.length() && matchChars(thizz, chars, 0, false);
  }

  static boolean matchesIgnoreCase(CharSequence thizz, CharSequence chars) {
    return chars.length() == thizz.length() && matchChars(thizz, chars, 0, true);
  }

  static boolean matchChars(
      CharSequence thizz, CharSequence chars, int startIndex, boolean ignoreCase) {
    return matchedCharCount(thizz, chars, startIndex, Integer.MAX_VALUE, true, ignoreCase)
        == chars.length();
  }

  static boolean matchChars(CharSequence thizz, CharSequence chars, int startIndex) {
    return matchChars(thizz, chars, startIndex, false);
  }

  static boolean matchCharsIgnoreCase(CharSequence thizz, CharSequence chars, int startIndex) {
    return matchChars(thizz, chars, startIndex, true);
  }

  static boolean matchChars(CharSequence thizz, CharSequence chars, boolean ignoreCase) {
    return matchChars(thizz, chars, 0, ignoreCase);
  }

  static boolean matchChars(CharSequence thizz, CharSequence chars) {
    return matchChars(thizz, chars, 0, false);
  }

  static boolean matchCharsIgnoreCase(CharSequence thizz, CharSequence chars) {
    return matchChars(thizz, chars, 0, true);
  }

  static boolean matchCharsReversed(
      CharSequence thizz, CharSequence chars, int endIndex, boolean ignoreCase) {
    return endIndex + 1 >= chars.length()
        && matchChars(thizz, chars, endIndex + 1 - chars.length(), ignoreCase);
  }

  static boolean matchCharsReversed(CharSequence thizz, CharSequence chars, int endIndex) {
    return endIndex + 1 >= chars.length()
        && matchChars(thizz, chars, endIndex + 1 - chars.length(), false);
  }

  static boolean matchCharsReversedIgnoreCase(
      CharSequence thizz, CharSequence chars, int endIndex) {
    return endIndex + 1 >= chars.length()
        && matchChars(thizz, chars, endIndex + 1 - chars.length(), true);
  }

  static int matchedCharCount(
      CharSequence thizz, CharSequence chars, int startIndex, int endIndex, boolean ignoreCase) {
    return matchedCharCount(thizz, chars, startIndex, Integer.MAX_VALUE, false, ignoreCase);
  }

  static int matchedCharCount(
      CharSequence thizz, CharSequence chars, int startIndex, boolean ignoreCase) {
    return matchedCharCount(thizz, chars, startIndex, Integer.MAX_VALUE, false, ignoreCase);
  }

  static int matchedCharCount(
      CharSequence thizz, CharSequence chars, int startIndex, int endIndex) {
    return matchedCharCount(thizz, chars, startIndex, Integer.MAX_VALUE, false, false);
  }

  static int matchedCharCount(CharSequence thizz, CharSequence chars, int startIndex) {
    return matchedCharCount(thizz, chars, startIndex, Integer.MAX_VALUE, false, false);
  }

  static int matchedCharCountIgnoreCase(
      CharSequence thizz, CharSequence chars, int startIndex, int endIndex) {
    return matchedCharCount(thizz, chars, startIndex, Integer.MAX_VALUE, false, true);
  }

  static int matchedCharCountIgnoreCase(CharSequence thizz, CharSequence chars, int startIndex) {
    return matchedCharCount(thizz, chars, startIndex, Integer.MAX_VALUE, false, true);
  }

  static int matchedCharCountReversed(
      CharSequence thizz, CharSequence chars, int startIndex, int fromIndex) {
    return matchedCharCountReversed(thizz, chars, startIndex, fromIndex, false);
  }

  static int matchedCharCountReversedIgnoreCase(
      CharSequence thizz, CharSequence chars, int startIndex, int fromIndex) {
    return matchedCharCountReversed(thizz, chars, startIndex, fromIndex, true);
  }

  static int matchedCharCountReversed(
      CharSequence thizz, CharSequence chars, int fromIndex, boolean ignoreCase) {
    return matchedCharCountReversed(thizz, chars, 0, fromIndex, ignoreCase);
  }

  static int matchedCharCountReversed(CharSequence thizz, CharSequence chars, int fromIndex) {
    return matchedCharCountReversed(thizz, chars, 0, fromIndex, false);
  }

  static int matchedCharCountReversedIgnoreCase(
      CharSequence thizz, CharSequence chars, int fromIndex) {
    return matchedCharCountReversed(thizz, chars, 0, fromIndex, true);
  }

  static int matchedCharCount(
      CharSequence thizz,
      CharSequence chars,
      int startIndex,
      int endIndex,
      boolean fullMatchOnly,
      boolean ignoreCase) {
    int length = chars.length();
    endIndex = Math.min(thizz.length(), endIndex);
    int iMax = Math.min(endIndex - startIndex, length);
    if (fullMatchOnly && iMax < length) {
      return 0;
    }

    if (ignoreCase) {
      for (int i = 0; i < iMax; i++) {
        char c1 = chars.charAt(i);
        char c2 = thizz.charAt(i + startIndex);
        if (c1 != c2) {
          char u1 = Character.toUpperCase(c1);
          char u2 = Character.toUpperCase(c2);
          if (u1 == u2) {
            continue;
          }

          // Unfortunately, conversion to uppercase does not work properly
          // for the Georgian alphabet, which has strange rules about case
          // conversion. So we need to make one last check before exiting.
          if (Character.toLowerCase(u1) == Character.toLowerCase(u2)) {
            continue;
          }
          return i;
        }
      }
    } else {
      for (int i = 0; i < iMax; i++) {
        if (chars.charAt(i) != thizz.charAt(i + startIndex)) {
          return i;
        }
      }
    }
    return iMax;
  }

  static int matchedCharCountReversed(
      CharSequence thizz, CharSequence chars, int startIndex, int fromIndex, boolean ignoreCase) {
    startIndex = Math.max(0, startIndex);
    fromIndex = Math.max(0, Math.min(thizz.length(), fromIndex));

    int length = chars.length();
    int iMax = Math.min(fromIndex - startIndex, length);

    int offset = fromIndex - iMax;
    if (ignoreCase) {
      for (int i = iMax; i-- > 0; ) {
        char c1 = chars.charAt(i);
        char c2 = thizz.charAt(offset + i);
        if (c1 != c2) {
          char u1 = Character.toUpperCase(c1);
          char u2 = Character.toUpperCase(c2);
          if (u1 == u2) {
            continue;
          }

          // Unfortunately, conversion to uppercase does not work properly
          // for the Georgian alphabet, which has strange rules about case
          // conversion.  So we need to make one last check before exiting.
          if (Character.toLowerCase(u1) == Character.toLowerCase(u2)) {
            continue;
          }
          return iMax - i - 1;
        }
      }
    } else {
      for (int i = iMax; i-- > 0; ) {
        if (chars.charAt(i) != thizz.charAt(offset + i)) {
          return iMax - i - 1;
        }
      }
    }
    return iMax;
  }

  static int countOfSpaceTab(CharSequence thizz) {
    return countOfAny(thizz, CharPredicate.SPACE_TAB, 0, Integer.MAX_VALUE);
  }

  static int countOfNotSpaceTab(CharSequence thizz) {
    return countOfAny(thizz, CharPredicate.SPACE_TAB.negate(), 0, Integer.MAX_VALUE);
  }

  static int countOfWhitespace(CharSequence thizz) {
    return countOfAny(thizz, CharPredicate.WHITESPACE, Integer.MAX_VALUE);
  }

  static int countOfNotWhitespace(CharSequence thizz) {
    return countOfAny(thizz, CharPredicate.WHITESPACE.negate(), 0, Integer.MAX_VALUE);
  }

  static int countOfAny(CharSequence thizz, CharPredicate chars, int fromIndex) {
    return countOfAny(thizz, chars, fromIndex, Integer.MAX_VALUE);
  }

  static int countOfAny(CharSequence thizz, CharPredicate chars) {
    return countOfAny(thizz, chars, 0, Integer.MAX_VALUE);
  }

  static int countOfAnyNot(CharSequence thizz, CharPredicate chars, int fromIndex, int endIndex) {
    return countOfAny(thizz, chars.negate(), fromIndex, endIndex);
  }

  static int countOfAnyNot(CharSequence thizz, CharPredicate chars, int fromIndex) {
    return countOfAny(thizz, chars.negate(), fromIndex, Integer.MAX_VALUE);
  }

  static int countOfAnyNot(CharSequence thizz, CharPredicate chars) {
    return countOfAny(thizz, chars.negate(), 0, Integer.MAX_VALUE);
  }

  static int countOfAny(CharSequence thizz, CharPredicate s, int fromIndex, int endIndex) {
    fromIndex = Math.max(fromIndex, 0);
    endIndex = Math.min(endIndex, thizz.length());

    int count = 0;
    for (int i = fromIndex; i < endIndex; i++) {
      char c = thizz.charAt(i);
      if (s.test(c)) count++;
    }
    return count;
  }

  static int countLeadingSpace(CharSequence thizz) {
    return countLeading(thizz, CharPredicate.SPACE, 0, Integer.MAX_VALUE);
  }

  static int countLeadingSpace(CharSequence thizz, int startIndex) {
    return countLeading(thizz, CharPredicate.SPACE, startIndex, Integer.MAX_VALUE);
  }

  static int countLeadingSpace(CharSequence thizz, int startIndex, int endIndex) {
    return countLeading(thizz, CharPredicate.SPACE, startIndex, endIndex);
  }

  static int countLeadingNotSpace(CharSequence thizz) {
    return countLeading(thizz, CharPredicate.SPACE.negate(), 0, Integer.MAX_VALUE);
  }

  static int countLeadingNotSpace(CharSequence thizz, int startIndex) {
    return countLeading(thizz, CharPredicate.SPACE.negate(), startIndex, Integer.MAX_VALUE);
  }

  static int countLeadingNotSpace(CharSequence thizz, int startIndex, int endIndex) {
    return countLeading(thizz, CharPredicate.SPACE.negate(), startIndex, endIndex);
  }

  static int countTrailingSpace(CharSequence thizz) {
    return countTrailing(thizz, CharPredicate.SPACE, 0, Integer.MAX_VALUE);
  }

  static int countTrailingSpace(CharSequence thizz, int fromIndex) {
    return countTrailing(thizz, CharPredicate.SPACE, 0, fromIndex);
  }

  static int countTrailingSpace(CharSequence thizz, int startIndex, int fromIndex) {
    return countTrailing(thizz, CharPredicate.SPACE, startIndex, fromIndex);
  }

  static int countTrailingNotSpace(CharSequence thizz) {
    return countTrailing(thizz, CharPredicate.SPACE.negate(), 0, Integer.MAX_VALUE);
  }

  static int countTrailingNotSpace(CharSequence thizz, int fromIndex) {
    return countTrailing(thizz, CharPredicate.SPACE.negate(), 0, fromIndex);
  }

  static int countTrailingNotSpace(CharSequence thizz, int startIndex, int fromIndex) {
    return countTrailing(thizz, CharPredicate.SPACE.negate(), startIndex, fromIndex);
  }

  static int countLeadingSpaceTab(CharSequence thizz) {
    return countLeading(thizz, CharPredicate.SPACE_TAB, 0, Integer.MAX_VALUE);
  }

  static int countLeadingSpaceTab(CharSequence thizz, int startIndex) {
    return countLeading(thizz, CharPredicate.SPACE_TAB, startIndex, Integer.MAX_VALUE);
  }

  static int countLeadingSpaceTab(CharSequence thizz, int startIndex, int endIndex) {
    return countLeading(thizz, CharPredicate.SPACE_TAB, startIndex, endIndex);
  }

  static int countLeadingNotSpaceTab(CharSequence thizz) {
    return countLeading(thizz, CharPredicate.SPACE_TAB.negate(), 0, Integer.MAX_VALUE);
  }

  static int countLeadingNotSpaceTab(CharSequence thizz, int startIndex) {
    return countLeading(thizz, CharPredicate.SPACE_TAB.negate(), startIndex, Integer.MAX_VALUE);
  }

  static int countLeadingNotSpaceTab(CharSequence thizz, int startIndex, int endIndex) {
    return countLeading(thizz, CharPredicate.SPACE_TAB.negate(), startIndex, endIndex);
  }

  static int countTrailingSpaceTab(CharSequence thizz) {
    return countTrailing(thizz, CharPredicate.SPACE_TAB, 0, Integer.MAX_VALUE);
  }

  static int countTrailingSpaceTab(CharSequence thizz, int fromIndex) {
    return countTrailing(thizz, CharPredicate.SPACE_TAB, 0, fromIndex);
  }

  static int countTrailingSpaceTab(CharSequence thizz, int startIndex, int fromIndex) {
    return countTrailing(thizz, CharPredicate.SPACE_TAB, startIndex, fromIndex);
  }

  static int countTrailingNotSpaceTab(CharSequence thizz) {
    return countTrailing(thizz, CharPredicate.SPACE_TAB.negate(), 0, Integer.MAX_VALUE);
  }

  static int countTrailingNotSpaceTab(CharSequence thizz, int fromIndex) {
    return countTrailing(thizz, CharPredicate.SPACE_TAB.negate(), 0, fromIndex);
  }

  static int countTrailingNotSpaceTab(CharSequence thizz, int startIndex, int fromIndex) {
    return countTrailing(thizz, CharPredicate.SPACE_TAB.negate(), startIndex, fromIndex);
  }

  static int countLeadingWhitespace(CharSequence thizz) {
    return countLeading(thizz, CharPredicate.WHITESPACE, 0, Integer.MAX_VALUE);
  }

  static int countLeadingWhitespace(CharSequence thizz, int startIndex) {
    return countLeading(thizz, CharPredicate.WHITESPACE, startIndex, Integer.MAX_VALUE);
  }

  static int countLeadingWhitespace(CharSequence thizz, int startIndex, int endIndex) {
    return countLeading(thizz, CharPredicate.WHITESPACE, startIndex, endIndex);
  }

  static int countLeadingNotWhitespace(CharSequence thizz) {
    return countLeading(thizz, CharPredicate.WHITESPACE.negate(), 0, Integer.MAX_VALUE);
  }

  static int countLeadingNotWhitespace(CharSequence thizz, int startIndex) {
    return countLeading(thizz, CharPredicate.WHITESPACE.negate(), startIndex, Integer.MAX_VALUE);
  }

  static int countLeadingNotWhitespace(CharSequence thizz, int startIndex, int endIndex) {
    return countLeading(thizz, CharPredicate.WHITESPACE.negate(), startIndex, endIndex);
  }

  static int countTrailingWhitespace(CharSequence thizz) {
    return countTrailing(thizz, CharPredicate.WHITESPACE, 0, Integer.MAX_VALUE);
  }

  static int countTrailingWhitespace(CharSequence thizz, int fromIndex) {
    return countTrailing(thizz, CharPredicate.WHITESPACE, 0, fromIndex);
  }

  static int countTrailingWhitespace(CharSequence thizz, int startIndex, int fromIndex) {
    return countTrailing(thizz, CharPredicate.WHITESPACE, startIndex, fromIndex);
  }

  static int countTrailingNotWhitespace(CharSequence thizz) {
    return countTrailing(thizz, CharPredicate.WHITESPACE.negate(), 0, Integer.MAX_VALUE);
  }

  static int countTrailingNotWhitespace(CharSequence thizz, int fromIndex) {
    return countTrailing(thizz, CharPredicate.WHITESPACE.negate(), 0, fromIndex);
  }

  static int countTrailingNotWhitespace(CharSequence thizz, int startIndex, int fromIndex) {
    return countTrailing(thizz, CharPredicate.WHITESPACE.negate(), startIndex, fromIndex);
  }

  static int countLeading(CharSequence thizz, CharPredicate chars) {
    return countLeading(thizz, chars, 0, Integer.MAX_VALUE);
  }

  static int countLeading(CharSequence thizz, CharPredicate chars, int fromIndex) {
    return countLeading(thizz, chars, fromIndex, Integer.MAX_VALUE);
  }

  static int countLeadingNot(CharSequence thizz, CharPredicate chars) {
    return countLeading(thizz, chars.negate(), 0, Integer.MAX_VALUE);
  }

  static int countLeadingNot(CharSequence thizz, CharPredicate chars, int fromIndex) {
    return countLeading(thizz, chars.negate(), fromIndex, Integer.MAX_VALUE);
  }

  static int countTrailing(CharSequence thizz, CharPredicate chars) {
    return countTrailing(thizz, chars, 0, Integer.MAX_VALUE);
  }

  static int countTrailing(CharSequence thizz, CharPredicate chars, int fromIndex) {
    return countTrailing(thizz, chars, 0, fromIndex);
  }

  static int countTrailingNot(CharSequence thizz, CharPredicate chars) {
    return countTrailing(thizz, chars.negate(), 0, Integer.MAX_VALUE);
  }

  static int countTrailingNot(CharSequence thizz, CharPredicate chars, int fromIndex) {
    return countTrailing(thizz, chars.negate(), 0, fromIndex);
  }

  static int countLeadingNot(
      CharSequence thizz, CharPredicate chars, int startIndex, int endIndex) {
    return countLeading(thizz, chars.negate(), startIndex, endIndex);
  }

  static int countTrailingNot(
      CharSequence thizz, CharPredicate chars, int startIndex, int endIndex) {
    return countTrailing(thizz, chars.negate(), startIndex, endIndex);
  }

  static int countLeading(CharSequence thizz, CharPredicate chars, int fromIndex, int endIndex) {
    endIndex = Math.min(endIndex, thizz.length());
    fromIndex = rangeLimit(fromIndex, 0, endIndex);

    int index = indexOfAnyNot(thizz, chars, fromIndex, endIndex);
    return index == -1 ? endIndex - fromIndex : index - fromIndex;
  }

  static int countLeadingColumns(CharSequence thizz, int startColumn, CharPredicate chars) {
    int fromIndex = 0;
    int endIndex = thizz.length();
    int index = indexOfAnyNot(thizz, chars, fromIndex, endIndex);

    // expand tabs
    int end = index == -1 ? endIndex : index;
    int columns = index == -1 ? endIndex - fromIndex : index - fromIndex;
    int tab = indexOf(thizz, '\t', fromIndex, end);
    if (tab != -1) {
      int delta = startColumn;
      do {
        delta += tab + columnsToNextTabStop(tab + delta);
        tab = indexOf(thizz, '\t', tab + 1);
      } while (tab >= 0 && tab < endIndex);
      columns += delta;
    }
    return columns;
  }

  static int countTrailing(CharSequence thizz, CharPredicate chars, int startIndex, int fromIndex) {
    fromIndex = Math.min(fromIndex, thizz.length());
    startIndex = rangeLimit(startIndex, 0, fromIndex);

    int index = lastIndexOfAnyNot(thizz, chars, startIndex, fromIndex - 1);
    return index == -1 ? fromIndex - startIndex : fromIndex <= index ? 0 : fromIndex - index - 1;
  }

  static <T extends CharSequence> T trim(T thizz, CharPredicate chars) {
    return trim(thizz, 0, chars);
  }

  static <T extends CharSequence> T trimEnd(T thizz) {
    return trimEnd(thizz, 0, CharPredicate.WHITESPACE);
  }

  static <T extends CharSequence> T trim(T thizz) {
    return trim(thizz, 0, CharPredicate.WHITESPACE);
  }

  static <T extends CharSequence> T trimEnd(T thizz, int keep, CharPredicate chars) {
    return subSequence(thizz, trimEndRange(thizz, keep, chars));
  }

  static <T extends CharSequence> T trim(T thizz, int keep, CharPredicate chars) {
    return subSequence(thizz, trimRange(thizz, keep, chars));
  }

  static Range trimStartRange(CharSequence thizz, CharPredicate chars) {
    return trimStartRange(thizz, 0, chars);
  }

  static Range trimEndRange(CharSequence thizz, CharPredicate chars) {
    return trimEndRange(thizz, 0, chars);
  }

  static Range trimRange(CharSequence thizz, CharPredicate chars) {
    return trimRange(thizz, 0, chars);
  }

  static Range trimStartRange(CharSequence thizz, int keep) {
    return trimStartRange(thizz, keep, CharPredicate.WHITESPACE);
  }

  static Range trimEndRange(CharSequence thizz, int keep) {
    return trimEndRange(thizz, keep, CharPredicate.WHITESPACE);
  }

  static Range trimRange(CharSequence thizz, int keep) {
    return trimRange(thizz, keep, CharPredicate.WHITESPACE);
  }

  static Range trimStartRange(CharSequence thizz) {
    return trimStartRange(thizz, 0, CharPredicate.WHITESPACE);
  }

  static Range trimEndRange(CharSequence thizz) {
    return trimEndRange(thizz, 0, CharPredicate.WHITESPACE);
  }

  static Range trimRange(CharSequence thizz) {
    return trimRange(thizz, 0, CharPredicate.WHITESPACE);
  }

  static Range trimStartRange(CharSequence thizz, int keep, CharPredicate chars) {
    int length = thizz.length();
    int trim = countLeading(thizz, chars, 0, length);
    return trim > keep ? Range.of(trim - keep, length) : Range.NULL;
  }

  static Range trimEndRange(CharSequence thizz, int keep, CharPredicate chars) {
    int length = thizz.length();
    int trim = countTrailing(thizz, chars, 0, length);
    return trim > keep ? Range.of(0, length - trim + keep) : Range.NULL;
  }

  static Range trimRange(CharSequence thizz, int keep, CharPredicate chars) {
    int length = thizz.length();
    if (keep >= length) {
      return Range.NULL;
    }

    int trimStart = countLeading(thizz, chars, 0, length);
    if (trimStart > keep) {
      int trimEnd = countTrailing(thizz, chars, trimStart - keep, length);
      return trimEnd > keep
          ? Range.of(trimStart - keep, length - trimEnd + keep)
          : Range.of(trimStart - keep, length);
    }

    int trimEnd = countTrailing(thizz, chars, trimStart, length);
    return trimEnd > keep ? Range.of(0, length - trimEnd + keep) : Range.NULL;
  }

  static String toVisibleWhitespaceString(CharSequence thizz) {
    StringBuilder sb = new StringBuilder();
    int iMax = thizz.length();
    for (int i = 0; i < iMax; i++) {
      char c = thizz.charAt(i);
      String s = SequenceUtils.visibleSpacesMap.get(c);

      if (s != null) {
        sb.append(s);
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  // *****************************************************************
  // EOL Helpers
  // *****************************************************************

  static char safeCharAt(CharSequence thizz, int index) {
    return index < 0 || index >= thizz.length() ? SequenceUtils.NUL : thizz.charAt(index);
  }

  static int eolEndLength(CharSequence thizz) {
    return eolEndLength(thizz, thizz.length());
  }

  static int eolEndLength(CharSequence thizz, int eolEnd) {
    int pos = Math.min(eolEnd - 1, thizz.length() - 1);
    if (pos < 0) {
      return 0;
    }

    int len = 0;
    char c = thizz.charAt(pos);
    if (c == '\r') {
      if (safeCharAt(thizz, pos + 1) != '\n') {
        len = 1;
      }
    } else if (c == '\n') {
      if (safeCharAt(thizz, pos - 1) == '\r') {
        len = 2;
      } else {
        len = 1;
      }
    }
    return len;
  }

  static int eolStartLength(CharSequence thizz, int eolStart) {
    int length = thizz.length();
    int pos = Math.min(eolStart, length);

    int len = 0;

    if (pos >= 0 && pos < length) {
      char c = thizz.charAt(pos);
      if (c == '\r') {
        if (safeCharAt(thizz, pos + 1) == '\n') {
          len = 2;
        } else {
          len = 1;
        }
      } else if (c == '\n') {
        if (safeCharAt(thizz, pos - 1) != '\r') {
          len = 1;
        }
      }
    }

    return len;
  }

  static int endOfLine(CharSequence thizz, int index) {
    return endOfDelimitedBy(thizz, SequenceUtils.EOL, index);
  }

  static int endOfLineAnyEOL(CharSequence thizz, int index) {
    return endOfDelimitedByAny(thizz, CharPredicate.ANY_EOL, index);
  }

  static int startOfLine(CharSequence thizz, int index) {
    return startOfDelimitedBy(thizz, SequenceUtils.EOL, index);
  }

  static int startOfLineAnyEOL(CharSequence thizz, int index) {
    return startOfDelimitedByAny(thizz, CharPredicate.ANY_EOL, index);
  }

  static int startOfDelimitedBy(CharSequence thizz, CharSequence s, int index) {
    index = rangeLimit(index, 0, thizz.length());
    int offset = lastIndexOf(thizz, s, index - 1);
    return offset == -1 ? 0 : offset + 1;
  }

  static int startOfDelimitedByAny(CharSequence thizz, CharPredicate s, int index) {
    index = rangeLimit(index, 0, thizz.length());
    int offset = lastIndexOfAny(thizz, s, index - 1);
    return offset == -1 ? 0 : offset + 1;
  }

  static int endOfDelimitedBy(CharSequence thizz, CharSequence s, int index) {
    int length = thizz.length();
    index = rangeLimit(index, 0, length);
    int offset = indexOf(thizz, s, index);
    return offset == -1 ? length : offset;
  }

  static int endOfDelimitedByAny(CharSequence thizz, CharPredicate s, int index) {
    int length = thizz.length();
    index = rangeLimit(index, 0, length);
    int offset = indexOfAny(thizz, s, index);
    return offset == -1 ? length : offset;
  }

  static Range lineRangeAt(CharSequence thizz, int index) {
    return Range.of(startOfLine(thizz, index), endOfLine(thizz, index));
  }

  static Range lineRangeAtAnyEOL(CharSequence thizz, int index) {
    return Range.of(startOfLineAnyEOL(thizz, index), endOfLineAnyEOL(thizz, index));
  }

  static Range eolEndRange(CharSequence thizz, int eolEnd) {
    int eolLength = eolEndLength(thizz, eolEnd);
    return eolLength == 0 ? Range.NULL : Range.of(eolEnd - eolLength, eolEnd);
  }

  static Range eolStartRange(CharSequence thizz, int eolStart) {
    int eolLength = eolStartLength(thizz, eolStart);
    return eolLength == 0 ? Range.NULL : Range.of(eolStart, eolStart + eolLength);
  }

  static <T extends CharSequence> T trimmedEOL(T thizz) {
    int eolLength = eolEndLength(thizz);
    return eolLength > 0 ? (T) thizz.subSequence(thizz.length() - eolLength, thizz.length()) : null;
  }

  static Range leadingBlankLinesRange(CharSequence thizz) {
    return leadingBlankLinesRange(thizz, CharPredicate.EOL, 0, Integer.MAX_VALUE);
  }

  static Range leadingBlankLinesRange(CharSequence thizz, int startIndex) {
    return leadingBlankLinesRange(thizz, CharPredicate.EOL, startIndex, Integer.MAX_VALUE);
  }

  static Range leadingBlankLinesRange(CharSequence thizz, int fromIndex, int endIndex) {
    return leadingBlankLinesRange(thizz, CharPredicate.EOL, fromIndex, endIndex);
  }

  static Range trailingBlankLinesRange(CharSequence thizz) {
    return trailingBlankLinesRange(thizz, CharPredicate.EOL, 0, Integer.MAX_VALUE);
  }

  static Range trailingBlankLinesRange(CharSequence thizz, int fromIndex) {
    return trailingBlankLinesRange(thizz, CharPredicate.EOL, fromIndex, Integer.MAX_VALUE);
  }

  static Range trailingBlankLinesRange(CharSequence thizz, int startIndex, int fromIndex) {
    return trailingBlankLinesRange(thizz, CharPredicate.EOL, startIndex, fromIndex);
  }

  static Range trailingBlankLinesRange(
      CharSequence thizz, CharPredicate eolChars, int startIndex, int fromIndex) {
    fromIndex = Math.min(fromIndex, thizz.length());
    startIndex = rangeLimit(startIndex, 0, fromIndex);

    int iMax = fromIndex;
    int lastEOL = iMax;
    int i;

    for (i = iMax; i-- > startIndex; ) {
      char c = thizz.charAt(i);
      if (eolChars.test(c)) {
        lastEOL = Math.min(i + Math.min(eolStartLength(thizz, i), 1), fromIndex);
      } else if (c != ' ' && c != '\t') {
        break;
      }
    }

    if (i < startIndex) {
      return Range.of(startIndex, fromIndex);
    } else if (lastEOL != iMax) {
      return Range.of(lastEOL, fromIndex);
    } else {
      return Range.NULL;
    }
  }

  static Range leadingBlankLinesRange(
      CharSequence thizz, CharPredicate eolChars, int fromIndex, int endIndex) {
    endIndex = Math.min(endIndex, thizz.length());
    fromIndex = rangeLimit(fromIndex, 0, endIndex);

    int iMax = endIndex;
    int lastEOL = -1;
    int i;

    for (i = fromIndex; i < iMax; i++) {
      char c = thizz.charAt(i);
      if (eolChars.test(c)) {
        lastEOL = i;
      } else if (c != ' ' && c != '\t') {
        break;
      }
    }

    if (i == iMax) {
      return Range.of(fromIndex, endIndex);
    } else if (lastEOL >= 0) {
      return Range.of(
          fromIndex, Math.min(lastEOL + Math.min(eolStartLength(thizz, lastEOL), 1), endIndex));
    } else {
      return Range.NULL;
    }
  }

  static List<Range> blankLinesRemovedRanges(CharSequence thizz) {
    return blankLinesRemovedRanges(thizz, CharPredicate.EOL, 0, Integer.MAX_VALUE);
  }

  static List<Range> blankLinesRemovedRanges(CharSequence thizz, int fromIndex) {
    return blankLinesRemovedRanges(thizz, CharPredicate.EOL, fromIndex, Integer.MAX_VALUE);
  }

  static List<Range> blankLinesRemovedRanges(CharSequence thizz, int fromIndex, int endIndex) {
    return blankLinesRemovedRanges(thizz, CharPredicate.EOL, fromIndex, endIndex);
  }

  static List<Range> blankLinesRemovedRanges(
      CharSequence thizz, CharPredicate eolChars, int fromIndex, int endIndex) {
    endIndex = Math.min(endIndex, thizz.length());
    fromIndex = rangeLimit(fromIndex, 0, endIndex);
    int lastPos = fromIndex;
    List<Range> ranges = new ArrayList<>();

    while (lastPos < endIndex) {
      Range blankLines = leadingBlankLinesRange(thizz, eolChars, lastPos, endIndex);
      if (blankLines.isNull()) {
        int endOfLine = Math.min(endOfLine(thizz, lastPos) + 1, endIndex);
        if (lastPos < endOfLine) ranges.add(Range.of(lastPos, endOfLine));
        lastPos = endOfLine;
      } else {
        if (lastPos < blankLines.getStart()) ranges.add(Range.of(lastPos, blankLines.getStart()));
        lastPos = blankLines.getEnd();
      }
    }
    return ranges;
  }

  static boolean isEmpty(CharSequence thizz) {
    return thizz.length() == 0;
  }

  static boolean isBlank(CharSequence thizz) {
    return isEmpty(thizz)
        || countLeading(thizz, CharPredicate.WHITESPACE, 0, Integer.MAX_VALUE) == thizz.length();
  }

  static boolean isNotEmpty(CharSequence thizz) {
    return thizz.length() != 0;
  }

  static boolean isNotBlank(CharSequence thizz) {
    return !isBlank(thizz);
  }

  static boolean endsWith(CharSequence thizz, CharSequence suffix) {
    return thizz.length() > 0 && matchCharsReversed(thizz, suffix, thizz.length() - 1, false);
  }

  static boolean endsWith(CharSequence thizz, CharSequence suffix, boolean ignoreCase) {
    return thizz.length() > 0 && matchCharsReversed(thizz, suffix, thizz.length() - 1, ignoreCase);
  }

  static boolean startsWith(CharSequence thizz, CharSequence prefix) {
    return thizz.length() > 0 && matchChars(thizz, prefix, 0, false);
  }

  static boolean startsWith(CharSequence thizz, CharSequence prefix, boolean ignoreCase) {
    return thizz.length() > 0 && matchChars(thizz, prefix, 0, ignoreCase);
  }

  static boolean endsWith(CharSequence thizz, CharPredicate chars) {
    return countTrailing(thizz, chars) > 0;
  }

  static boolean startsWith(CharSequence thizz, CharPredicate chars) {
    return countLeading(thizz, chars) > 0;
  }

  static boolean endsWithEOL(CharSequence thizz) {
    return endsWith(thizz, CharPredicate.EOL);
  }

  static boolean endsWithAnyEOL(CharSequence thizz) {
    return endsWith(thizz, CharPredicate.ANY_EOL);
  }

  static boolean endsWithSpace(CharSequence thizz) {
    return endsWith(thizz, CharPredicate.SPACE);
  }

  static boolean endsWithSpaceTab(CharSequence thizz) {
    return endsWith(thizz, CharPredicate.SPACE_TAB);
  }

  static boolean endsWithWhitespace(CharSequence thizz) {
    return endsWith(thizz, CharPredicate.WHITESPACE);
  }

  static boolean startsWithEOL(CharSequence thizz) {
    return startsWith(thizz, CharPredicate.EOL);
  }

  static boolean startsWithAnyEOL(CharSequence thizz) {
    return startsWith(thizz, CharPredicate.ANY_EOL);
  }

  static boolean startsWithSpace(CharSequence thizz) {
    return startsWith(thizz, CharPredicate.SPACE);
  }

  static boolean startsWithSpaceTab(CharSequence thizz) {
    return startsWith(thizz, CharPredicate.SPACE_TAB);
  }

  static boolean startsWithWhitespace(CharSequence thizz) {
    return startsWith(thizz, CharPredicate.WHITESPACE);
  }

  static <T extends CharSequence> List<T> splitList(
      T thizz, CharSequence delimiter, int limit, int flags, CharPredicate trimChars) {
    if (trimChars == null) {
      trimChars = CharPredicate.WHITESPACE;
    } else {
      flags |= SPLIT_TRIM_PARTS;
    }

    if (limit < 1) {
      limit = Integer.MAX_VALUE;
    }

    boolean includeDelimiterParts = (flags & SPLIT_INCLUDE_DELIM_PARTS) != 0;
    int includeDelimiter =
        !includeDelimiterParts && (flags & SPLIT_INCLUDE_DELIMS) != 0 ? delimiter.length() : 0;
    boolean trimParts = (flags & SPLIT_TRIM_PARTS) != 0;
    boolean skipEmpty = (flags & SPLIT_SKIP_EMPTY) != 0;
    List<T> items = new ArrayList<>();

    int lastPos = 0;
    int length = thizz.length();
    if (limit > 1) {
      while (lastPos < length) {
        int pos = indexOf(thizz, delimiter, lastPos);
        if (pos < 0) {
          break;
        }

        if (lastPos < pos || !skipEmpty) {
          T item = (T) thizz.subSequence(lastPos, pos + includeDelimiter);
          if (trimParts) item = trim(item, trimChars);
          if (!isEmpty(item) || !skipEmpty) {
            items.add(item);
            if (includeDelimiterParts) {
              items.add((T) thizz.subSequence(pos, pos + delimiter.length()));
            }
            if (items.size() >= limit - 1) {
              lastPos = pos + 1;
              break;
            }
          }
        }
        lastPos = pos + 1;
      }
    }

    if (lastPos < length) {
      T item = (T) thizz.subSequence(lastPos, length);
      if (trimParts) item = trim(item, trimChars);
      if (!isEmpty(item) || !skipEmpty) {
        items.add(item);
      }
    }
    return items;
  }

  static int columnAtIndex(CharSequence thizz, int index) {
    int lineStart = lastIndexOfAny(thizz, CharPredicate.ANY_EOL, index);
    return index - (lineStart == -1 ? 0 : lineStart + eolStartLength(thizz, lineStart));
  }

  static Pair<Integer, Integer> lineColumnAtIndex(CharSequence thizz, int index) {
    int iMax = thizz.length();
    if (index < 0 || index > iMax) {
      throw new IllegalArgumentException("Index: " + index + " out of range [0, " + iMax + "]");
    }

    boolean hadCr = false;
    int line = 0;
    int col = 0;
    for (int i = 0; i < index; i++) {
      char c1 = thizz.charAt(i);
      if (c1 == '\r') {
        col = 0;
        line++;
        hadCr = true;
      } else if (c1 == '\n') {
        if (!hadCr) {
          line++;
        }
        col = 0;
        hadCr = false;
      } else {
        col++;
      }
    }

    return new Pair<>(line, col);
  }

  static void validateIndex(int index, int length) {
    if (index < 0 || index >= length) {
      throw new StringIndexOutOfBoundsException(
          "String index: " + index + " out of range: [0, " + length + ")");
    }
  }

  static void validateIndexInclusiveEnd(int index, int length) {
    if (index < 0 || index > length) {
      throw new StringIndexOutOfBoundsException(
          "index: " + index + " out of range: [0, " + length + "]");
    }
  }

  static void validateStartEnd(int startIndex, int endIndex, int length) {
    if (startIndex < 0 || startIndex > length) {
      throw new StringIndexOutOfBoundsException(
          "startIndex: " + startIndex + " out of range: [0, " + length + ")");
    }

    if (endIndex < startIndex || endIndex > length) {
      throw new StringIndexOutOfBoundsException(
          "endIndex: " + endIndex + " out of range: [" + startIndex + ", " + length + "]");
    }
  }

  static Long parseLongOrNull(String text, int radix) {
    try {
      return Long.parseLong(text, radix);
    } catch (NumberFormatException ignored) {
      return null;
    }
  }

  /**
   * Parse number from text
   *
   * <p>Will parse 0x, 0b, octal if starts with 0, decimal
   *
   * @param text text containing the number to parse
   * @param suffixTester predicate to test number suffix, if null or predicate returns true then
   *     sequence will be accepted as valid
   * @return null or parsed number with unparsed suffix
   */
  static Pair<Number, String> parseNumberPrefixOrNull(String text, Predicate<String> suffixTester) {
    if (text == null) {
      return null;
    }

    if (text.startsWith("0x")) {
      int digits = countLeading(text.substring(2), CharPredicate.HEXADECIMAL_DIGITS);
      String suffix = text.substring(2 + digits);
      if (digits > 0 && (suffix.isEmpty() || suffixTester == null || suffixTester.test(suffix))) {
        return new Pair<>(parseLongOrNull(text.substring(2, 2 + digits), 16), suffix);
      }
    } else if (text.startsWith("0b")) {
      int digits = countLeading(text.substring(2), CharPredicate.BINARY_DIGITS);
      String suffix = text.substring(2 + digits);
      if (digits > 0 && (suffix.isEmpty() || suffixTester == null || suffixTester.test(suffix))) {
        return new Pair<>(parseLongOrNull(text.substring(2, 2 + digits), 2), suffix);
      }
    } else if (text.startsWith("0")) {
      int digits = countLeading(text.substring(1), CharPredicate.OCTAL_DIGITS);
      int decimalDigits = countLeading(text.substring(1), CharPredicate.DECIMAL_DIGITS);
      if (digits == decimalDigits) {
        String suffix = text.substring(1 + digits);
        if (digits > 0 && (suffix.isEmpty() || suffixTester == null || suffixTester.test(suffix))) {
          return new Pair<>(parseLongOrNull(text.substring(1, 1 + digits), 8), suffix);
        }
      }
    }

    NumberFormat numberFormat = NumberFormat.getInstance();
    ParsePosition pos = new ParsePosition(0);
    Number number = numberFormat.parse(text, pos);
    String suffix = text.substring(pos.getIndex());
    if (pos.getIndex() > 0
        && (suffix.isEmpty() || suffixTester == null || suffixTester.test(suffix))) {
      return new Pair<>(number, suffix);
    }

    return null;
  }

  static <T extends CharSequence> boolean containedBy(T[] items, CharSequence element) {
    for (T item : items) {
      if (equals(element, item)) {
        return true;
      }
    }
    return false;
  }

  static boolean containedBy(Collection<? extends CharSequence> items, CharSequence element) {
    for (CharSequence item : items) {
      if (equals(element, item)) {
        return true;
      }
    }
    return false;
  }
}
