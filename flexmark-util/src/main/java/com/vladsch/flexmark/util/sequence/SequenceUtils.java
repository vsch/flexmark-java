package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public interface SequenceUtils {
    String EOL = "\n";
    String SPACE = " ";
    String ANY_EOL = "\r\n";

    @Deprecated
    String EOL_CHARS = ANY_EOL;
    char EOL_CHAR = ANY_EOL.charAt(1);
    char EOL_CHAR1 = ANY_EOL.charAt(0);
    char EOL_CHAR2 = ANY_EOL.charAt(1);
    char SPC = ' ';
    char NUL = '\0';
    char ENC_NUL = '\uFFFD';
    char NBSP = '\u00A0';
    char LS = '\u2028'; // line separator
    char US = '\u001f';  // US or USEP - Unit Separator, also used as IntelliJDummyIdentifier in Parsings, used as a tracked offset marker in the sequence
    char MRK = US;       // same as US but use in code signals it being related to offset marker handling
    String LINE_SEP = Character.toString(LS);
    String SPACE_TAB = " \t";

    @Deprecated
    String WHITESPACE_NO_EOL_CHARS = SPACE_TAB;
    String US_CHARS = Character.toString(US);
    String MARKER_CHARS = US_CHARS;  // same as US_CHARS but use in code signals it being related to offset marker handling
    String WHITESPACE = " \t\r\n";

    @Deprecated
    String WHITESPACE_CHARS = WHITESPACE;
    String WHITESPACE_NBSP = " \t\r\n\u00A0";

    @Deprecated
    String WHITESPACE_NBSP_CHARS = WHITESPACE_NBSP;
    CharPredicate SPACE_SET = CharPredicate.SPACE;
    CharPredicate TAB_SET = CharPredicate.TAB;
    CharPredicate EOL_SET = CharPredicate.EOL;
    CharPredicate SPACE_TAB_SET = CharPredicate.SPACE_TAB;
    CharPredicate SPACE_TAB_NBSP_SET = CharPredicate.SPACE_TAB_NBSP;
    CharPredicate ANY_EOL_SET = CharPredicate.ANY_EOL;
    CharPredicate WHITESPACE_SET = CharPredicate.WHITESPACE;
    CharPredicate WHITESPACE_NBSP_SET = CharPredicate.WHITESPACE_NBSP;
    CharPredicate BACKSLASH_SET = CharPredicate.BACKSLASH;
    CharPredicate US_SET = value -> value == US;
    @NotNull CharPredicate HASH_SET = CharPredicate.HASH;

    /**
     * Line Separator, used in paragraph wrapping to force start of new line
     *
     * @deprecated use {@link #LS} instead as it is named in Unicode
     */
    @Deprecated
    char LSEP = LS;

    int SPLIT_INCLUDE_DELIMS = 1;
    int SPLIT_TRIM_PARTS = 2;
    int SPLIT_SKIP_EMPTY = 4;
    int SPLIT_INCLUDE_DELIM_PARTS = 8;
    int SPLIT_TRIM_SKIP_EMPTY = SPLIT_TRIM_PARTS | SPLIT_SKIP_EMPTY;

    static Map<Character, String> getVisibleSpacesMap() {
        HashMap<Character, String> charMap = new HashMap<>();
        charMap.put('\n', "\\n");
        charMap.put('\r', "\\r");
        charMap.put('\f', "\\f");
        charMap.put('\t', "\\u2192");
        return charMap;
    }

    Map<Character, String> visibleSpacesMap = getVisibleSpacesMap();

    int[] EMPTY_INDICES = { };

    // @formatter:off
    static int indexOf(@NotNull CharSequence thizz, @NotNull CharSequence s)                                                 { return indexOf(thizz, s, 0, thizz.length()); }
    static int indexOf(@NotNull CharSequence thizz, @NotNull CharSequence s, int fromIndex)                                  { return indexOf(thizz, s, fromIndex, thizz.length()); }
    static int indexOf(@NotNull CharSequence thizz, char c)                                                                  { return indexOf(thizz, c, 0, thizz.length()); }
    static int indexOf(@NotNull CharSequence thizz, char c, int fromIndex)                                                   { return indexOf(thizz, c, fromIndex, thizz.length()); }
    static int indexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s)                                             { return indexOfAny(thizz, s, 0, thizz.length()); }
    static int indexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s, int index)                                  { return indexOfAny(thizz, s, index, thizz.length()); }
    static int indexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s)                                          { return indexOfAny(thizz, s.negate(), 0, thizz.length()); }
    static int indexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s, int fromIndex)                           { return indexOfAny(thizz, s.negate(), fromIndex, thizz.length()); }
    static int indexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s, int fromIndex, int endIndex)             { return indexOfAny(thizz, s.negate(), fromIndex, endIndex);}
    static int indexOfNot(@NotNull CharSequence thizz,  char c)                                                              { return indexOfNot(thizz, c, 0, thizz.length()); }
    static int indexOfNot(@NotNull CharSequence thizz,  char c, int fromIndex)                                               { return indexOfNot(thizz, c, fromIndex, thizz.length()); }

    static int lastIndexOf(@NotNull CharSequence thizz, @NotNull CharSequence s)                                             { return lastIndexOf(thizz, s, 0, thizz.length()); }
    static int lastIndexOf(@NotNull CharSequence thizz, @NotNull CharSequence s, int fromIndex)                              { return lastIndexOf(thizz, s, 0, fromIndex); }
    static int lastIndexOf(@NotNull CharSequence thizz, char c)                                                              { return lastIndexOf(thizz, c, 0, thizz.length());}
    static int lastIndexOf(@NotNull CharSequence thizz, char c, int fromIndex)                                               { return lastIndexOf(thizz, c, 0, fromIndex);}
    static int lastIndexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s)                                         { return lastIndexOfAny(thizz, s, 0, thizz.length()); }
    static int lastIndexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s, int fromIndex)                          { return lastIndexOfAny(thizz, s, 0, fromIndex); }
    static int lastIndexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s)                                      { return lastIndexOfAny(thizz, s.negate(), 0, thizz.length()); }
    static int lastIndexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s, int fromIndex)                       { return lastIndexOfAny(thizz, s.negate(), 0, fromIndex); }
    static int lastIndexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s, int startIndex, int fromIndex)       { return lastIndexOfAny(thizz, s.negate(), startIndex, fromIndex);}
    static int lastIndexOfNot(@NotNull CharSequence thizz,  char c)                                                          { return lastIndexOfNot(thizz, c, 0, thizz.length()); }
    static int lastIndexOfNot(@NotNull CharSequence thizz,  char c, int fromIndex)                                           { return lastIndexOfNot(thizz, c, 0, fromIndex); }
    // @formatter:on

    static int indexOf(@NotNull CharSequence thizz, char c, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, thizz.length());

        for (int i = fromIndex; i < endIndex; i++) {
            if (c == thizz.charAt(i)) return i;
        }
        return -1;
    }

    // TEST:
    static int indexOf(@NotNull CharSequence thizz, @NotNull CharSequence s, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);

        int sMax = s.length();
        if (sMax == 0) return fromIndex;
        endIndex = Math.min(endIndex, thizz.length());

        if (fromIndex < endIndex) {
            char firstChar = s.charAt(0);
            int pos = fromIndex;

            do {
                pos = indexOf(thizz, firstChar, pos);
                if (pos < 0 || pos + sMax > endIndex) break;
                if (matchChars(thizz, s, pos)) return pos;
                pos++;
            } while (pos + sMax < endIndex);
        }

        return -1;
    }

    static int lastIndexOf(@NotNull CharSequence thizz, char c, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, thizz.length());

        for (int i = fromIndex; i-- > startIndex; ) {
            if (c == thizz.charAt(i)) return i;
        }
        return -1;
    }

    static int indexOfNot(@NotNull CharSequence thizz, char c, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, thizz.length());

        for (int i = fromIndex; i < endIndex; i++) {
            if (thizz.charAt(i) != c) return i;
        }
        return -1;
    }

    static int indexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, thizz.length());

        for (int i = fromIndex; i < endIndex; i++) {
            char c = thizz.charAt(i);
            if (s.test(c)) return i;
        }
        return -1;
    }

    // TEST:
    static int lastIndexOf(@NotNull CharSequence thizz, @NotNull CharSequence s, int startIndex, int fromIndex) {
        startIndex = Math.max(startIndex, 0);

        int sMax = s.length();
        if (sMax == 0) return startIndex;

        fromIndex = Math.min(fromIndex, thizz.length());

        if (startIndex < fromIndex) {
            int pos = fromIndex;
            char lastChar = s.charAt(sMax - 1);

            do {
                pos = lastIndexOf(thizz, lastChar, pos);
                if (pos + 1 < startIndex + sMax) break;
                if (matchCharsReversed(thizz, s, pos)) return pos + 1 - sMax;
                pos--;
            } while (pos + 1 >= startIndex + sMax);
        }

        return -1;
    }

    static int lastIndexOfNot(@NotNull CharSequence thizz, char c, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, thizz.length());

        for (int i = fromIndex; i-- > startIndex; ) {
            if (thizz.charAt(i) != c) return i;
        }
        return -1;
    }

    static int lastIndexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, thizz.length());

        for (int i = fromIndex; i-- > startIndex; ) {
            char c = thizz.charAt(i);
            if (s.test(c)) return i;
        }
        return -1;
    }

    static int compareReversed(@Nullable CharSequence o1, @Nullable CharSequence o2) {
        return compare(o2, o1);
    }

    static int compare(@Nullable CharSequence o1, @Nullable CharSequence o2) {
        if (o1 == null || o2 == null) return o1 == null && o2 == null ? 0 : o1 == null ? -1 : 1;

        int len1 = o1.length();
        int len2 = o2.length();
        int iMax = Math.min(len1, len2);
        for (int i = 0; i < iMax; i++) {
            char c1 = o1.charAt(i);
            char c2 = o2.charAt(i);
            if (c1 != c2) {
                return c1 - c2;
            }
        }
        return len1 - len2;
    }

    @NotNull
    static String[] toStringArray(CharSequence... sequences) {
        String[] result = new String[sequences.length];
        int i = 0;
        for (CharSequence sequence : sequences) {
            result[i] = sequences[i] == null ? null : sequences[i].toString();
            i++;
        }
        return result;
    }

    static boolean isVisibleWhitespace(char c) {
        return visibleSpacesMap.containsKey(c);
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

    // TEST:
    // @formatter:off
    static boolean matches(@NotNull CharSequence thizz, @NotNull CharSequence chars, boolean ignoreCase) {return chars.length() == thizz.length() && matchChars(thizz, chars, 0, ignoreCase);}
    static boolean matches(@NotNull CharSequence thizz, @NotNull CharSequence chars) {return chars.length() == thizz.length() && matchChars(thizz, chars, 0, false);}
    static boolean matchesIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars) {return chars.length() == thizz.length() && matchChars(thizz, chars, 0, true);}

    static boolean matchChars(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, boolean ignoreCase) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), true, ignoreCase) == chars.length(); }
    static boolean matchChars(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex) {return matchChars(thizz, chars, startIndex, false);}
    static boolean matchCharsIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex) {return matchChars(thizz, chars, startIndex, true);}

    static boolean matchChars(@NotNull CharSequence thizz, @NotNull CharSequence chars, boolean ignoreCase) {return matchChars(thizz, chars, 0, ignoreCase);}
    static boolean matchChars(@NotNull CharSequence thizz, @NotNull CharSequence chars) {return matchChars(thizz, chars, 0, false);}
    static boolean matchCharsIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars) {return matchChars(thizz, chars, 0, true);}

    static boolean matchCharsReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int endIndex, boolean ignoreCase) {return endIndex + 1 >= chars.length() && matchChars(thizz, chars, endIndex + 1 - chars.length(), ignoreCase);}
    static boolean matchCharsReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int endIndex) {return endIndex + 1 >= chars.length() && matchChars(thizz, chars, endIndex + 1 - chars.length(), false);}
    static boolean matchCharsReversedIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int endIndex) {return endIndex + 1 >= chars.length() && matchChars(thizz, chars, endIndex + 1 - chars.length(), true);}

    static int matchedCharCount(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int endIndex, boolean ignoreCase) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, ignoreCase); }
    static int matchedCharCount(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, boolean ignoreCase) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, ignoreCase); }
    static int matchedCharCount(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int endIndex) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, false); }
    static int matchedCharCount(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, false); }
    static int matchedCharCountIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int endIndex) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, true); }
    static int matchedCharCountIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, true); }

    static int matchedCharCountReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int fromIndex) { return matchedCharCountReversed(thizz, chars, startIndex, fromIndex, false); }
    static int matchedCharCountReversedIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int fromIndex) { return matchedCharCountReversed(thizz, chars, startIndex, fromIndex, true); }

    static int matchedCharCountReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int fromIndex, boolean ignoreCase) { return matchedCharCountReversed(thizz, chars, 0, fromIndex, ignoreCase); }
    static int matchedCharCountReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int fromIndex) { return matchedCharCountReversed(thizz, chars, 0, fromIndex, false); }
    static int matchedCharCountReversedIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int fromIndex) { return matchedCharCountReversed(thizz, chars, 0, fromIndex, true); }
    // @formatter:on

    static int matchedCharCount(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int endIndex, boolean fullMatchOnly, boolean ignoreCase) {
        int length = chars.length();
        endIndex = Math.min(thizz.length(), endIndex);
        int iMax = Math.min(endIndex - startIndex, length);
        if (fullMatchOnly && iMax < length) return 0;

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
                if (chars.charAt(i) != thizz.charAt(i + startIndex)) return i;
            }
        }
        return iMax;
    }

    // TEST:
    static int matchedCharCountReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int fromIndex, boolean ignoreCase) {
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
                if (chars.charAt(i) != thizz.charAt(offset + i)) return iMax - i - 1;
            }
        }
        return iMax;
    }

    // @formatter:off
    static int countOfSpaceTab(@NotNull CharSequence thizz)                                                                     { return countOfAny(thizz, SequenceUtils.SPACE_TAB_SET, 0, thizz.length()); }
    static int countOfNotSpaceTab(@NotNull CharSequence thizz)                                                                  { return countOfAny(thizz, SequenceUtils.SPACE_TAB_SET.negate(), 0, thizz.length()); }

    static int countOfWhitespace(@NotNull CharSequence thizz)                                                                   { return countOfAny(thizz, SequenceUtils.WHITESPACE_SET, thizz.length()); }
    static int countOfNotWhitespace(@NotNull CharSequence thizz)                                                                { return countOfAny(thizz, SequenceUtils.WHITESPACE_SET.negate(), 0, thizz.length()); }

    static int countOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate chars, int fromIndex)                             { return countOfAny(thizz, chars, fromIndex, thizz.length()); }
    static int countOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate chars)                                            { return countOfAny(thizz, chars, 0, thizz.length()); }

    static int countOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate chars, int fromIndex, int endIndex)            { return countOfAny(thizz, chars.negate(), fromIndex, endIndex); }
    static int countOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate chars, int fromIndex)                          { return countOfAny(thizz, chars.negate(), fromIndex, thizz.length()); }
    static int countOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate chars)                                         { return countOfAny(thizz, chars.negate(), 0, thizz.length()); }
    // @formatter:on

    static int countOfAny(CharSequence thizz, @NotNull CharPredicate s, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, thizz.length());

        int count = 0;
        for (int i = fromIndex; i < endIndex; i++) {
            char c = thizz.charAt(i);
            if (s.test(c)) count++;
        }
        return count;
    }
}
