package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SequenceUtils {
    static final Map<Character, String> visibleSpacesMap;
    static {
        HashMap<Character, String> charMap = new HashMap<>();
        visibleSpacesMap = charMap;
        charMap.put('\n', "\\n");
        charMap.put('\r', "\\r");
        charMap.put('\f', "\\f");
        charMap.put('\t', "\\u2192");
    }

    public static int[] EMPTY_INDICES = { };

    // @formatter:off
    public static int indexOf(@NotNull CharSequence thizz, @NotNull CharSequence s)                                                 { return indexOf(thizz, s, 0, thizz.length()); }
    public static int indexOf(@NotNull CharSequence thizz, @NotNull CharSequence s, int fromIndex)                                  { return indexOf(thizz, s, fromIndex, thizz.length()); }
    public static int indexOf(@NotNull CharSequence thizz, char c)                                                                  { return indexOf(thizz, c, 0, thizz.length()); }
    public static int indexOf(@NotNull CharSequence thizz, char c, int fromIndex)                                                   { return indexOf(thizz, c, fromIndex, thizz.length()); }
    public static int indexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s)                                             { return indexOfAny(thizz, s, 0, thizz.length()); }
    public static int indexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s, int index)                                  { return indexOfAny(thizz, s, index, thizz.length()); }
    public static int indexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s)                                          { return indexOfAny(thizz, s.negate(), 0, thizz.length()); }
    public static int indexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s, int fromIndex)                           { return indexOfAny(thizz, s.negate(), fromIndex, thizz.length()); }
    public static int indexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s, int fromIndex, int endIndex)             { return indexOfAny(thizz, s.negate(), fromIndex, endIndex);}
    public static int indexOfNot(@NotNull CharSequence thizz,  char c)                                                              { return indexOfNot(thizz, c, 0, thizz.length()); }
    public static int indexOfNot(@NotNull CharSequence thizz,  char c, int fromIndex)                                               { return indexOfNot(thizz, c, fromIndex, thizz.length()); }

    public static int lastIndexOf(@NotNull CharSequence thizz, @NotNull CharSequence s)                                             { return lastIndexOf(thizz, s, 0, thizz.length()); }
    public static int lastIndexOf(@NotNull CharSequence thizz, @NotNull CharSequence s, int fromIndex)                              { return lastIndexOf(thizz, s, 0, fromIndex); }
    public static int lastIndexOf(@NotNull CharSequence thizz, char c)                                                              { return lastIndexOf(thizz, c, 0, thizz.length());}
    public static int lastIndexOf(@NotNull CharSequence thizz, char c, int fromIndex)                                    { return lastIndexOf(thizz, c, 0, fromIndex);}
    public static int lastIndexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s)                                         { return lastIndexOfAny(thizz, s, 0, thizz.length()); }
    public static int lastIndexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s, int fromIndex)                          { return lastIndexOfAny(thizz, s, 0, fromIndex); }
    public static int lastIndexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s)                                      { return lastIndexOfAny(thizz, s.negate(), 0, thizz.length()); }
    public static int lastIndexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s, int fromIndex)                       { return lastIndexOfAny(thizz, s.negate(), 0, fromIndex); }
    public static int lastIndexOfAnyNot(@NotNull CharSequence thizz, @NotNull CharPredicate s, int startIndex, int fromIndex)       { return lastIndexOfAny(thizz, s.negate(), startIndex, fromIndex);}
    public static int lastIndexOfNot(@NotNull CharSequence thizz,  char c)                                                          { return lastIndexOfNot(thizz, c, 0, thizz.length()); }
    public static int lastIndexOfNot(@NotNull CharSequence thizz,  char c, int fromIndex)                                           { return lastIndexOfNot(thizz, c, 0, fromIndex); }
    // @formatter:on

    public static int indexOf(@NotNull CharSequence thizz, char c, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, thizz.length());

        for (int i = fromIndex; i < endIndex; i++) {
            if (c == thizz.charAt(i)) return i;
        }
        return -1;
    }

    // TEST:
    public static int indexOf(@NotNull CharSequence thizz, @NotNull CharSequence s, int fromIndex, int endIndex) {
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

    public static int lastIndexOf(@NotNull CharSequence thizz, char c, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, thizz.length());

        for (int i = fromIndex; i-- > startIndex; ) {
            if (c == thizz.charAt(i)) return i;
        }
        return -1;
    }

    public static int indexOfNot(@NotNull CharSequence thizz, char c, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, thizz.length());

        for (int i = fromIndex; i < endIndex; i++) {
            if (thizz.charAt(i) != c) return i;
        }
        return -1;
    }

    public static int indexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, thizz.length());

        for (int i = fromIndex; i < endIndex; i++) {
            char c = thizz.charAt(i);
            if (s.test(c)) return i;
        }
        return -1;
    }

    // TEST:
    public static int lastIndexOf(@NotNull CharSequence thizz, @NotNull CharSequence s, int startIndex, int fromIndex) {
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

    public static int lastIndexOfNot(@NotNull CharSequence thizz, char c, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, thizz.length());

        for (int i = fromIndex; i-- > startIndex; ) {
            if (thizz.charAt(i) != c) return i;
        }
        return -1;
    }

    public static int lastIndexOfAny(@NotNull CharSequence thizz, @NotNull CharPredicate s, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, thizz.length());

        for (int i = fromIndex; i-- > startIndex; ) {
            char c = thizz.charAt(i);
            if (s.test(c)) return i;
        }
        return -1;
    }

    public static int compareReversed(@Nullable CharSequence o1, @Nullable CharSequence o2) {
        return compare(o2, o1);
    }

    public static int compare(@Nullable CharSequence o1, @Nullable CharSequence o2) {
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
    public static String[] toStringArray(CharSequence... sequences) {
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

    public static int columnsToNextTabStop(int column) {
        // Tab stop is 4
        return 4 - (column % 4);
    }

    public static int[] expandTo(int[] indices, int length, int step) {
        int remainder = length & step;
        int next = length + (remainder != 0 ? step : 0);
        if (indices.length < next) {
            int[] replace = new int[next];
            System.arraycopy(indices, 0, replace, 0, indices.length);
            return replace;
        }
        return indices;
    }

    public static int[] truncateTo(int[] indices, int length) {
        if (indices.length > length) {
            int[] replace = new int[length];
            System.arraycopy(indices, 0, replace, 0, length);
            return replace;
        }
        return indices;
    }

    // TEST:
    // @formatter:off
    public static boolean matches(@NotNull CharSequence thizz, @NotNull CharSequence chars, boolean ignoreCase) {return chars.length() == thizz.length() && matchChars(thizz, chars, 0, ignoreCase);}
    public static boolean matches(@NotNull CharSequence thizz, @NotNull CharSequence chars) {return chars.length() == thizz.length() && matchChars(thizz, chars, 0, false);}
    public static boolean matchesIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars) {return chars.length() == thizz.length() && matchChars(thizz, chars, 0, true);}

    public static boolean matchChars(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, boolean ignoreCase) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), true, ignoreCase) == chars.length(); }
    public static boolean matchChars(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex) {return matchChars(thizz, chars, startIndex, false);}
    public static boolean matchCharsIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex) {return matchChars(thizz, chars, startIndex, true);}

    public static boolean matchChars(@NotNull CharSequence thizz, @NotNull CharSequence chars, boolean ignoreCase) {return matchChars(thizz, chars, 0, ignoreCase);}
    public static boolean matchChars(@NotNull CharSequence thizz, @NotNull CharSequence chars) {return matchChars(thizz, chars, 0, false);}
    public static boolean matchCharsIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars) {return matchChars(thizz, chars, 0, true);}

    public static boolean matchCharsReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int endIndex, boolean ignoreCase) {return endIndex + 1 >= chars.length() && matchChars(thizz, chars, endIndex + 1 - chars.length(), ignoreCase);}
    public static boolean matchCharsReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int endIndex) {return endIndex + 1 >= chars.length() && matchChars(thizz, chars, endIndex + 1 - chars.length(), false);}
    public static boolean matchCharsReversedIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int endIndex) {return endIndex + 1 >= chars.length() && matchChars(thizz, chars, endIndex + 1 - chars.length(), true);}

    public static int matchedCharCount(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int endIndex, boolean ignoreCase) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, ignoreCase); }
    public static int matchedCharCount(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, boolean ignoreCase) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, ignoreCase); }
    public static int matchedCharCount(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int endIndex) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, false); }
    public static int matchedCharCount(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, false); }
    public static int matchedCharCountIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int endIndex) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, true); }
    public static int matchedCharCountIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex) { return matchedCharCount(thizz, chars, startIndex, thizz.length(), false, true); }

    public static int matchedCharCountReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int fromIndex) { return matchedCharCountReversed(thizz, chars, startIndex, fromIndex, false); }
    public static int matchedCharCountReversedIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int fromIndex) { return matchedCharCountReversed(thizz, chars, startIndex, fromIndex, true); }

    public static int matchedCharCountReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int fromIndex, boolean ignoreCase) { return matchedCharCountReversed(thizz, chars, 0, fromIndex, ignoreCase); }
    public static int matchedCharCountReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int fromIndex) { return matchedCharCountReversed(thizz, chars, 0, fromIndex, false); }
    public static int matchedCharCountReversedIgnoreCase(@NotNull CharSequence thizz, @NotNull CharSequence chars, int fromIndex) { return matchedCharCountReversed(thizz, chars, 0, fromIndex, true); }
    // @formatter:on

    public static int matchedCharCount(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int endIndex, boolean fullMatchOnly, boolean ignoreCase) {
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
    public static int matchedCharCountReversed(@NotNull CharSequence thizz, @NotNull CharSequence chars, int startIndex, int fromIndex, boolean ignoreCase) {
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
}
