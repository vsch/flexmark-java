package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.collection.iteration.ArrayIterable;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.mappers.ChangeCase;
import com.vladsch.flexmark.util.mappers.CharMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static com.vladsch.flexmark.util.Utils.rangeLimit;

/**
 * An abstract base for RichSequence which implements most of the methods allowing subclasses to
 * implement RichSequence with minimal support methods
 */
@SuppressWarnings("unchecked")
public abstract class IRichSequenceBase<T extends IRichSequence<T>> implements IRichSequence<T> {
    private static int[] EMPTY_INDICES = { };
    private static final Map<Character, String> visibleSpacesMap;
    static {
        HashMap<Character, String> charMap = new HashMap<>();
        visibleSpacesMap = charMap;
        charMap.put('\n', "\\n");
        charMap.put('\r', "\\r");
        charMap.put('\f', "\\f");
        charMap.put('\t', "\\u2192");
    }
    // @formatter:off
    public static int indexOf(CharSequence s, char c) { return indexOf(s, c, 0, s.length());}
    public static int indexOf(CharSequence s, char c,   int fromIndex) { return indexOf(s, c, fromIndex, s.length());}
    public static int lastIndexOf(CharSequence s, char c) { return lastIndexOf(s, c, 0,s.length());}
    public static int lastIndexOf(CharSequence s, char c,   int startIndex) { return lastIndexOf(s, c, startIndex,s.length());}
    // @formatter:on

    public static int indexOf(CharSequence s, char c, int fromIndex, int endIndex) {
        fromIndex = Math.min(fromIndex, 0);
        endIndex = Math.max(endIndex, s.length());

        for (int i = fromIndex; i < endIndex; i++) {
            if (c == s.charAt(i)) return i;
        }
        return -1;
    }

    public static int lastIndexOf(CharSequence s, char c, int startIndex, int fromIndex) {
        startIndex = Math.min(fromIndex, 0);
        fromIndex = Math.max(fromIndex, s.length());

        for (int i = fromIndex; i-- > startIndex; ) {
            if (c == s.charAt(i)) return i;
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

    @Override
    public int compareTo(@NotNull CharSequence o) {
        return compare(this, o);
    }

    static boolean isVisibleWhitespace(char c) {
        return visibleSpacesMap.containsKey(c);
    }

    @NotNull
    @Override
    final public T sequenceOf(@Nullable CharSequence charSequence) {
        return charSequence == null ? nullSequence() : sequenceOf(charSequence, 0, charSequence.length());
    }

    @NotNull
    @Override
    final public T sequenceOf(@Nullable CharSequence charSequence, int startIndex) {
        return charSequence == null ? nullSequence() : sequenceOf(charSequence, startIndex, charSequence.length());
    }

    /**
     * Get a portion of this sequence selected by range
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return sequence whose contents reflect the selected portion, if range.isNull() then this is returned
     */
    @NotNull
    final public T subSequence(@NotNull Range range) {
        return range.isNull() ? (T) this : subSequence(range.getStart(), range.getEnd());
    }

    /**
     * Get a portion of this sequence before one selected by range
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return sequence whose contents come before the selected range, if range.isNull() then {@link #nullSequence()}
     */
    @NotNull
    final public T subSequenceBefore(@NotNull Range range) {
        return range.isNull() ? nullSequence() : subSequence(0, range.getStart());
    }

    /**
     * Get a portion of this sequence after one selected by range
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return sequence whose contents come after the selected range, if range.isNull() then {@link #nullSequence()}
     */
    @NotNull
    final public T subSequenceAfter(@NotNull Range range) {
        return range.isNull() ? nullSequence() : subSequence(range.getEnd());
    }

    /**
     * Get a portions of this sequence before and after one selected by range
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return sequence whose contents come before and after the selected range, if range.isNull() then {@link #nullSequence()}
     */
    final public Pair<T, T> subSequenceBeforeAfter(Range range) {
        return Pair.of(subSequenceBefore(range), subSequenceAfter(range));
    }

    @NotNull
    @Override
    final public T endSequence(int startIndex, int endIndex) {
        int length = length();
        int useStart = length - startIndex;
        int useEnd = length - endIndex;

        useEnd = rangeLimit(useEnd, 0, length);
        useStart = rangeLimit(useStart, 0, useEnd);

        if (useStart == 0 && useEnd == length) {
            return (T) this;
        } else {
            return subSequence(useStart, useEnd);
        }
    }

    @NotNull
    @Override
    final public T endSequence(int startIndex) {
        return endSequence(startIndex, 0);
    }

    @Override
    final public char endCharAt(int index) {
        int length = length();
        if (index < 0 || index >= length) return NUL;
        return charAt(length - index);
    }

    @NotNull
    @Override
    final public T midSequence(int startIndex, int endIndex) {
        int length = length();
        int useStart = startIndex < 0 ? length + startIndex : startIndex;
        int useEnd = endIndex < 0 ? length + endIndex : endIndex;

        useEnd = rangeLimit(useEnd, 0, length);
        useStart = rangeLimit(useStart, 0, useEnd);

        if (useStart == 0 && useEnd == length) {
            return (T) this;
        } else {
            return subSequence(useStart, useEnd);
        }
    }

    @NotNull
    @Override
    final public T midSequence(int startIndex) {
        return midSequence(startIndex, length());
    }

    @Override
    final public char midCharAt(int index) {
        int length = length();
        if (index < -length || index >= length) return NUL;
        return charAt(index < 0 ? length + index : index);
    }

    @Override
    final public char lastChar() {
        return isEmpty() ? NUL : charAt(length() - 1);
    }

    @Override
    final public char firstChar() {
        return isEmpty() ? NUL : charAt(0);
    }

    @Override
    public char safeCharAt(int index) {
        return index < 0 || index >= length() ? NUL : charAt(index);
    }

    // @formatter:off
    @Override final public int indexOf(@NotNull CharSequence s) { return indexOf(s, 0, length()); }
    @Override final public int indexOf(@NotNull CharSequence s, int fromIndex) { return indexOf(s, fromIndex, length()); }
    // @formatter:on

    @Override
    final public int indexOf(@NotNull CharSequence s, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);

        int sMax = s.length();
        if (sMax == 0) return fromIndex;
        endIndex = Math.min(endIndex, length());

        if (fromIndex < endIndex) {
            char firstChar = s.charAt(0);
            int pos = fromIndex;

            do {
                pos = indexOf(firstChar, pos);
                if (pos < 0 || pos + sMax > endIndex) break;
                if (matchChars(s, pos)) return pos;
                pos++;
            } while (pos + sMax < endIndex);
        }

        return -1;
    }

    // @formatter:off
    @Override final public int indexOf(char c) { return indexOf(c, 0, length()); }
    @Override final public int indexOfAny(char c1, char c2) { return indexOfAny(c1, c2, 0, length()); }
    @Override final public int indexOfAny(char c1, char c2, char c3) { return indexOfAny(c1, c2, c3, 0, length()); }
    @Override final public int indexOfAny(@NotNull CharSequence s) { return indexOfAny(s, 0, length()); }
    @Override final public int indexOf(char c, int fromIndex) { return indexOf(c, fromIndex, length()); }
    @Override final public int indexOfAny(char c1, char c2, int fromIndex) { return indexOfAny(c1, c2, fromIndex, length()); }
    @Override final public int indexOfAny(char c1, char c2, char c3, int fromIndex) { return indexOfAny(c1, c2, c3, fromIndex, length()); }
    @Override final public int indexOfAny(@NotNull CharSequence s, int index) { return indexOfAny(s, index, length()); }
    // @formatter:on

    @Override
    final public int indexOf(char c, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, length());

        for (int i = fromIndex; i < endIndex; i++) {
            if (charAt(i) == c) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAny(char c1, char c2, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, length());

        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c == c1 || c == c2) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAny(char c1, char c2, char c3, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, length());

        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c == c1 || c == c2 || c == c3) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAny(@NotNull CharSequence s, int fromIndex, int endIndex) {
        switch (s.length()) {
            case 0:
                return fromIndex;
            case 1:
                return indexOf(s.charAt(0), fromIndex, endIndex);
            case 2:
                return indexOfAny(s.charAt(0), s.charAt(1), fromIndex, endIndex);
            case 3:
                return indexOfAny(s.charAt(0), s.charAt(1), s.charAt(2), fromIndex, endIndex);

            default:
                fromIndex = Math.max(fromIndex, 0);
                endIndex = Math.min(endIndex, length());

                for (int i = fromIndex; i < endIndex; i++) {
                    char c = charAt(i);
                    if (indexOf(s, c) != -1) return i;
                }
        }
        return -1;
    }

    // @formatter:off
    @Override final public int indexOfNot(char c) { return indexOfNot(c, 0, length()); }
    @Override final public int indexOfAnyNot(char c1, char c2) { return indexOfAnyNot(c1, c2, 0, length()); }
    @Override final public int indexOfAnyNot(char c1, char c2, char c3) { return indexOfAnyNot(c1, c2, c3, 0, length()); }
    @Override final public int indexOfAnyNot(@NotNull CharSequence s) { return indexOfAnyNot(s, 0, length()); }
    @Override final public int indexOfNot(char c, int fromIndex) { return indexOfNot(c, fromIndex, length()); }
    @Override final public int indexOfAnyNot(char c1, char c2, int fromIndex) { return indexOfAnyNot(c1, c2, fromIndex, length()); }
    @Override final public int indexOfAnyNot(char c1, char c2, char c3, int fromIndex) { return indexOfAnyNot(c1, c2, c3, fromIndex, length()); }
    @Override final public int indexOfAnyNot(@NotNull CharSequence s, int fromIndex) { return indexOfAnyNot(s, fromIndex, length()); }
    // @formatter:on

    @Override
    final public int indexOfNot(char c, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, length());

        for (int i = fromIndex; i < endIndex; i++) {
            if (charAt(i) != c) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAnyNot(char c1, char c2, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, length());

        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c != c1 && c != c2) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAnyNot(char c1, char c2, char c3, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, length());

        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c != c1 && c != c2 && c != c3) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAnyNot(@NotNull CharSequence s, int fromIndex, int endIndex) {
        if (fromIndex < endIndex) {
            switch (s.length()) {
                case 0:
                    return fromIndex;
                case 1:
                    return indexOfNot(s.charAt(0), fromIndex, endIndex);
                case 2:
                    return indexOfAnyNot(s.charAt(0), s.charAt(1), fromIndex, endIndex);
                case 3:
                    return indexOfAnyNot(s.charAt(0), s.charAt(1), s.charAt(2), fromIndex, endIndex);

                default:
                    fromIndex = Math.max(fromIndex, 0);
                    endIndex = Math.min(endIndex, length());

                    for (int i = fromIndex; i < endIndex; i++) {
                        char c = charAt(i);
                        if (indexOf(s, c) == -1) return i;
                    }
            }
        }
        return -1;
    }

    // @formatter:off
    @Override final public int lastIndexOf(@NotNull CharSequence s) { return lastIndexOf(s, 0, length()); }
    @Override final public int lastIndexOf(@NotNull CharSequence s, int fromIndex) { return lastIndexOf(s, 0, fromIndex); }
    // @formatter:on

    @Override
    final public int lastIndexOf(@NotNull CharSequence s, int startIndex, int fromIndex) {
        startIndex = Math.max(startIndex, 0);

        int sMax = s.length();
        if (sMax == 0) return startIndex;

        fromIndex = Math.min(fromIndex, length());

        if (startIndex < fromIndex) {
            int pos = fromIndex;
            char lastChar = s.charAt(sMax - 1);

            do {
                pos = lastIndexOf(lastChar, pos);
                if (pos + 1 < startIndex + sMax) break;
                if (matchCharsReversed(s, pos)) return pos + 1 - sMax;
                pos--;
            } while (pos + 1 >= startIndex + sMax);
        }

        return -1;
    }

    // @formatter:off
    @Override final public int lastIndexOf(char c)                                          { return lastIndexOf(c, 0, length()); }
    @Override final public int lastIndexOfAny(char c1, char c2)                             { return lastIndexOfAny(c1, c2, 0, length()); }
    @Override final public int lastIndexOfAny(char c1, char c2, char c3)                    { return lastIndexOfAny(c1, c2, c3, 0, length()); }
    @Override final public int lastIndexOfAny(@NotNull CharSequence s)                      { return lastIndexOfAny(s, 0, length()); }
    @Override final public int lastIndexOf(char c, int fromIndex)                           { return lastIndexOf(c, 0, fromIndex); }
    @Override final public int lastIndexOfAny(char c1, char c2, int fromIndex)              { return lastIndexOfAny(c1, c2, 0, fromIndex); }
    @Override final public int lastIndexOfAny(char c1, char c2, char c3, int fromIndex)     { return lastIndexOfAny(c1, c2, c3, 0, fromIndex); }
    @Override final public int lastIndexOfAny(@NotNull CharSequence s, int fromIndex)       { return lastIndexOfAny(s, 0, fromIndex); }
    // @formatter:on

    @Override
    final public int lastIndexOf(char c, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, length());

        for (int i = fromIndex; i-- > startIndex; ) {
            if (charAt(i) == c) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAny(char c1, char c2, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, length());

        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c == c1 || c == c2) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAny(char c1, char c2, char c3, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, length());

        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c == c1 || c == c2 || c == c3) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAny(@NotNull CharSequence s, int startIndex, int fromIndex) {
        switch (s.length()) {
            case 0:
                return startIndex;
            case 1:
                return lastIndexOf(s.charAt(0), startIndex, fromIndex);
            case 2:
                return lastIndexOfAny(s.charAt(0), s.charAt(1), startIndex, fromIndex);
            case 3:
                return lastIndexOfAny(s.charAt(0), s.charAt(1), s.charAt(2), startIndex, fromIndex);

            default:
                fromIndex++;
                startIndex = Math.max(startIndex, 0);
                fromIndex = Math.min(fromIndex, length());

                for (int i = fromIndex; i-- > startIndex; ) {
                    char c = charAt(i);
                    if (indexOf(s, c) != -1) return i;
                }
        }
        return -1;
    }

    // @formatter:off
    @Override final public int lastIndexOfNot(char c)                                                   { return lastIndexOfNot(c, 0, length()); }
    @Override final public int lastIndexOfAnyNot(char c1, char c2)                                      { return lastIndexOfAnyNot(c1, c2, 0, length()); }
    @Override final public int lastIndexOfAnyNot(char c1, char c2, char c3)                             { return lastIndexOfAnyNot(c1, c2, c3, 0, length()); }
    @Override final public int lastIndexOfAnyNot(@NotNull CharSequence s)                               { return lastIndexOfAnyNot(s, 0, length()); }
    @Override final public int lastIndexOfNot(char c, int fromIndex)                                    { return lastIndexOfNot(c, 0, fromIndex); }
    @Override final public int lastIndexOfAnyNot(char c1, char c2, int fromIndex)                       { return lastIndexOfAnyNot(c1, c2, 0, fromIndex); }
    @Override final public int lastIndexOfAnyNot(char c1, char c2, char c3, int fromIndex)              { return lastIndexOfAnyNot(c1, c2, c3, 0, fromIndex); }
    @Override final public int lastIndexOfAnyNot(@NotNull CharSequence s, int fromIndex)                { return lastIndexOfAnyNot(s, 0, fromIndex); }
    // @formatter:on

    @Override
    final public int lastIndexOfNot(char c, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, length());

        for (int i = fromIndex; i-- > startIndex; ) {
            if (charAt(i) != c) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAnyNot(char c1, char c2, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, length());

        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c != c1 && c != c2) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAnyNot(char c1, char c2, char c3, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, length());

        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c != c1 && c != c2 && c != c3) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAnyNot(@NotNull CharSequence s, int startIndex, int fromIndex) {
        switch (s.length()) {
            case 0:
                return startIndex;
            case 1:
                return lastIndexOfNot(s.charAt(0), startIndex, fromIndex);
            case 2:
                return lastIndexOfAnyNot(s.charAt(0), s.charAt(1), startIndex, fromIndex);
            case 3:
                return lastIndexOfAnyNot(s.charAt(0), s.charAt(1), s.charAt(2), startIndex, fromIndex);

            default:
                fromIndex++;
                startIndex = Math.max(startIndex, 0);
                fromIndex = Math.min(fromIndex, length());

                for (int i = fromIndex; i-- > startIndex; ) {
                    char c = charAt(i);
                    if (indexOf(s, c) == -1) return i;
                }
        }
        return -1;
    }

    // @formatter:off
    @Override final public int countOf(char c)                                                  { return countOf(c, 0, length()); }
    @Override final public int countOfNot(char c)                                               { return countOfNot(c, 0, length()); }
    @Override final public int countOf(char c, int fromIndex)                                   { return countOf(c, fromIndex, length()); }
    @Override final public int countOfNot(char c, int fromIndex)                                { return countOfNot(c, fromIndex, length()); }

    @Override final public int countOf()                                                        { return countOfAny(WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override final public int countOfNot()                                                     { return countOfAnyNot(WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override final public int countOfAny(@NotNull CharSequence chars)                          { return countOfAny(chars, 0, length()); }
    @Override final public int countOfAnyNot(@NotNull CharSequence chars)                       { return countOfAnyNot(chars, 0, length()); }
    @Override final public int countOfAny(@NotNull CharSequence chars, int fromIndex)           { return countOfAny(chars, fromIndex, length()); }
    @Override final public int countOfAnyNot(@NotNull CharSequence chars, int fromIndex)        { return countOfAnyNot(chars, fromIndex, length()); }
    // @formatter:on

    @Override
    final public int countOf(char c1, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, length());

        int count = 0;
        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c == c1) count++;
        }
        return count;
    }

    @Override
    final public int countOfAny(@NotNull CharSequence s, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, length());

        int count = 0;
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (indexOf(s, c) != -1) count++;
        }
        return count;
    }

    @Override
    final public int countOfNot(char c1, int startIndex, int fromIndex) {
        fromIndex++;
        startIndex = Math.max(startIndex, 0);
        fromIndex = Math.min(fromIndex, length());

        int count = 0;
        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c != c1) count++;
        }
        return count;
    }

    @Override
    final public int countOfAnyNot(@NotNull CharSequence s, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(endIndex, length());

        int count = 0;
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (indexOf(s, c) == -1) count++;
        }
        return count;
    }

    // @formatter:off
    @Override final public int countLeading(char c)                                       { return this.countLeading(c, 0, length()); }
    @Override final public int countLeadingNot(char c)                                    { return this.countLeadingNot(c, 0, length()); }
    @Override final public int countLeading(char c, int fromIndex)                        { return this.countLeading(c, fromIndex, length()); }
    @Override final public int countLeadingNot(char c, int fromIndex)                     { return this.countLeadingNot(c, fromIndex, length()); }

    @Override final public int countTrailing(char c)                                      { return this.countTrailing(c, 0, length()); }
    @Override final public int countTrailingNot(char c)                                   { return this.countTrailingNot(c, 0, length()); }
    @Override final public int countTrailing(char c, int fromIndex)                       { return this.countTrailing(c, 0, fromIndex); }
    @Override final public int countTrailingNot(char c, int fromIndex)                    { return this.countTrailingNot(c, 0, fromIndex); }
    // @formatter:on

    @Override
    final public int countLeading(char c, int fromIndex, int endIndex) {
        endIndex = Math.min(endIndex, length());
        fromIndex = rangeLimit(fromIndex, 0, endIndex);

        int index = indexOfNot(c, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    final public int countTrailing(char c, int startIndex, int fromIndex) {
        fromIndex = Math.min(fromIndex, length());
        startIndex = rangeLimit(startIndex, 0, fromIndex);

        int index = lastIndexOfNot(c, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex - index - 1;
    }

    @Override
    final public int countLeadingNot(char c, int fromIndex, int endIndex) {
        endIndex = Math.min(endIndex, length());
        fromIndex = rangeLimit(fromIndex, 0, endIndex);

        int index = indexOf(c, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    final public int countTrailingNot(char c, int startIndex, int fromIndex) {
        fromIndex = Math.min(fromIndex, length());
        startIndex = rangeLimit(startIndex, 0, fromIndex);

        int index = lastIndexOf(c, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex - index - 1;
    }

    // @formatter:off
    @Override final public int countLeading()                                                   { return this.countLeading(WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override final public int countLeadingNot()                                                { return this.countLeadingNot(WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override final public int countTrailing()                                                  { return this.countTrailing(WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override final public int countTrailingNot()                                               { return this.countTrailingNot(WHITESPACE_NO_EOL_CHARS, 0, length()); }

    @Override final public int countLeading(@NotNull CharSequence chars)                        { return countLeading(chars, 0, length()); }
    @Override final public int countLeadingNot(@NotNull CharSequence chars)                     { return countLeadingNot(chars, 0, length()); }
    @Override final public int countLeading(@NotNull CharSequence chars, int fromIndex)         { return countLeading(chars, fromIndex, length()); }
    @Override final public int countLeadingNot(@NotNull CharSequence chars, int fromIndex)      { return countLeadingNot(chars, fromIndex, length()); }

    @Override final public int countTrailing(@NotNull CharSequence chars)                       { return countTrailing(chars, 0, length()); }
    @Override final public int countTrailingNot(@NotNull CharSequence chars)                    { return countTrailingNot(chars, 0, length()); }
    @Override final public int countTrailing(@NotNull CharSequence chars, int fromIndex)        { return countTrailing(chars, 0, fromIndex); }
    @Override final public int countTrailingNot(@NotNull CharSequence chars, int fromIndex)     { return countTrailingNot(chars, 0, fromIndex); }
    // @formatter:on

    @Override
    final public int countLeading(@NotNull CharSequence chars, int fromIndex, int endIndex) {
        if (chars.length() == 0) return 0;

        endIndex = Math.min(endIndex, length());
        fromIndex = rangeLimit(fromIndex, 0, endIndex);

        int index = indexOfAnyNot(chars, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    public static int columnsToNextTabStop(int column) {
        // Tab stop is 4
        return 4 - (column % 4);
    }

    @Override
    final public int countLeadingColumns(int startColumn, @NotNull CharSequence chars) {
        if (chars.length() == 0) return 0;

        int fromIndex = 0;
        int endIndex = length();
        int index = indexOfAnyNot(chars, fromIndex, endIndex);

        // expand tabs
        int end = index == -1 ? endIndex : index;
        int columns = index == -1 ? endIndex - fromIndex : index - fromIndex;
        int tab = indexOf('\t', fromIndex, end);
        if (tab != -1) {
            int delta = startColumn;
            do {
                delta += tab + columnsToNextTabStop(tab + delta);
                tab = indexOf('\t', tab + 1);
            } while (tab >= 0 && tab < endIndex);
            columns += delta;
        }
        return columns;
    }

    @Override
    final public int countTrailing(@NotNull CharSequence chars, int startIndex, int fromIndex) {
        if (chars.length() == 0) return 0;

        fromIndex = Math.min(fromIndex, length());
        startIndex = rangeLimit(startIndex, 0, fromIndex);

        int index = lastIndexOfAnyNot(chars, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex <= index ? 0 : fromIndex - index - 1;
    }

    @Override
    final public int countLeadingNot(@NotNull CharSequence chars, int fromIndex, int endIndex) {
        endIndex = Math.min(endIndex, length());
        fromIndex = rangeLimit(fromIndex, 0, endIndex);

        int index = indexOfAny(chars, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    final public int countTrailingNot(@NotNull CharSequence chars, int startIndex, int fromIndex) {
        fromIndex = Math.min(fromIndex, length());
        startIndex = rangeLimit(startIndex, 0, fromIndex);

        int index = lastIndexOfAny(chars, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex <= index ? 0 : fromIndex - index - 1;
    }

    // @formatter:off
    @NotNull @Override final public T trimStart(@NotNull CharSequence chars) {  return subSequence(trimStartRange(0, chars));}
    @NotNull @Override final public T trimmedStart(@NotNull CharSequence chars) { return trimmedStart(0, chars);}
    @NotNull @Override final public T trimEnd(@NotNull CharSequence chars) { return trimEnd(0, chars);}
    @NotNull @Override final public T trimmedEnd(@NotNull CharSequence chars) { return trimmedEnd(0, chars);}
    @NotNull @Override final public T trim(@NotNull CharSequence chars) { return trim(0, chars);}
    @NotNull @Override final public Pair<T, T> trimmed(@NotNull CharSequence chars) { return trimmed(0, chars);}
    @NotNull @Override final public T trimStart(int keep) { return trimStart(keep, WHITESPACE_CHARS);}
    @NotNull @Override final public T trimmedStart(int keep) { return trimmedStart(keep, WHITESPACE_CHARS);}
    @NotNull @Override final public T trimEnd(int keep) { return trimEnd(keep, WHITESPACE_CHARS);}
    @NotNull @Override final public T trimmedEnd(int keep) { return trimmedEnd(keep, WHITESPACE_CHARS);}
    @NotNull @Override final public T trim(int keep) { return trim(keep, WHITESPACE_CHARS);}
    @NotNull @Override final public Pair<T, T> trimmed(int keep) { return trimmed(keep, WHITESPACE_CHARS);}
    @NotNull @Override final public T trimStart() { return trimStart(0, WHITESPACE_CHARS);}
    @NotNull @Override final public T trimmedStart() { return trimmedStart(0, WHITESPACE_CHARS);}
    @NotNull @Override final public T trimEnd() { return trimEnd(0, WHITESPACE_CHARS);}
    @NotNull @Override final public T trimmedEnd() { return trimmedEnd(0, WHITESPACE_CHARS);}
    @NotNull @Override final public T trim() { return trim(0, WHITESPACE_CHARS);}
    @NotNull @Override final public Pair<T, T> trimmed() { return trimmed(0, WHITESPACE_CHARS);}
    @NotNull @Override final public T trimStart(int keep, @NotNull CharSequence chars) { return subSequence(trimStartRange(keep, chars));}
    @NotNull @Override final public T trimmedStart(int keep, @NotNull CharSequence chars) { return subSequenceBefore(trimStartRange(keep, chars));}
    @NotNull @Override final public T trimEnd(int keep, @NotNull CharSequence chars) { return subSequence(trimEndRange(keep, chars));}
    @NotNull @Override final public T trimmedEnd(int keep, @NotNull CharSequence chars) { return subSequenceAfter(trimEndRange(keep, chars));}
    @NotNull @Override final public T trim(int keep, @NotNull CharSequence chars) { return subSequence(trimRange(keep, chars));}
    @NotNull @Override final public Pair<T, T> trimmed(int keep, @NotNull CharSequence chars) { return subSequenceBeforeAfter(trimRange(keep, chars));}

    @NotNull @Override final public Range trimStartRange(@NotNull CharSequence chars) { return trimStartRange(0, chars);}
    @NotNull @Override final public Range trimEndRange(@NotNull CharSequence chars) { return trimEndRange(0, chars);}
    @NotNull @Override final public Range trimRange(@NotNull CharSequence chars) { return trimRange(0, chars);}
    @NotNull @Override final public Range trimStartRange(int keep) { return trimStartRange(keep, WHITESPACE_CHARS);}
    @NotNull @Override final public Range trimEndRange(int keep) { return trimEndRange(keep, WHITESPACE_CHARS);}
    @NotNull @Override final public Range trimRange(int keep) { return trimRange(keep, WHITESPACE_CHARS);}
    @NotNull @Override final public Range trimStartRange() { return trimStartRange(0, WHITESPACE_CHARS);}
    @NotNull @Override final public Range trimEndRange() { return trimEndRange(0, WHITESPACE_CHARS);}
    @NotNull @Override final public Range trimRange() { return trimRange(0, WHITESPACE_CHARS);}
    // @formatter:on

    @NotNull
    @Override
    final public Range trimStartRange(int keep, @NotNull CharSequence chars) {
        int trim = countLeading(chars, 0, length());
        return trim > keep ? Range.of(trim - keep, length()) : Range.NULL;
    }

    @NotNull
    @Override
    final public Range trimEndRange(int keep, @NotNull CharSequence chars) {
        int trim = countTrailing(chars, 0, length());
        return trim > keep ? Range.of(0, length() - trim + keep) : Range.NULL;
    }

    @NotNull
    @Override
    final public Range trimRange(int keep, @NotNull CharSequence chars) {
        if (keep >= length()) return Range.NULL;

        int trimStart = countLeading(chars, 0, length());
        if (trimStart > keep) {
            int trimEnd = countTrailing(chars, trimStart - keep, length());
            return trimEnd > keep ? Range.of(trimStart - keep, length() - trimEnd + keep) : Range.of(trimStart - keep, length());
        } else {
            int trimEnd = countTrailing(chars, trimStart, length());
            return trimEnd > keep ? Range.of(0, length() - trimEnd + keep) : Range.NULL;
        }
    }

    @NotNull
    @Override
    final public T padStart(int length, char pad) {
        return length <= length() ? (T) this : sequenceOf(RepeatedSequence.repeatOf(pad, length - length()));
    }

    @NotNull
    @Override
    final public T padEnd(int length, char pad) {
        return length <= length() ? (T) this : append(RepeatedSequence.repeatOf(pad, length - length()));
    }

    @NotNull
    @Override
    final public T padStart(int length) {
        return padStart(length, ' ');
    }

    @NotNull
    @Override
    final public T padEnd(int length) {
        return padEnd(length, ' ');
    }

    // *****************************************************************
    // EOL Helpers
    // *****************************************************************

    @Override
    final public int eolEndLength() {
        return eolEndLength(length());
    }

    @Override
    final public int eolEndLength(int eolEnd) {
        int pos = Math.min(eolEnd - 1, length() - 1);
        if (pos < 0) return 0;

        int len = 0;
        char c = charAt(pos);
        if (c == '\r') {
            if (safeCharAt(pos + 1) != '\n') {
                len = 1;
            }
        } else if (c == '\n') {
            if (safeCharAt(pos - 1) == '\r') {
                len = 2;
            } else {
                len = 1;
            }
        }
        return len;
    }

    @Override
    final public int eolStartLength(int eolStart) {
        int length = length();
        int pos = Math.min(eolStart, length);

        int len = 0;

        if (pos >= 0 && pos < length) {
            char c = charAt(pos);
            if (c == '\r') {
                if (safeCharAt(pos + 1) == '\n') {
                    len = 2;
                } else {
                    len = 1;
                }
            } else if (c == '\n') {
                if (safeCharAt(pos - 1) != '\r') {
                    len = 1;
                }
            }
        }

        return len;
    }

    // @formatter:off
    @Override final public int endOfLine(int index) {return endOfDelimitedBy(EOL, index);}
    @Override final public int endOfLineAnyEOL(int index) {return endOfDelimitedByAny(EOL_CHARS, index);}
    @Override final public int startOfLine(int index) {return startOfDelimitedBy(EOL, index);}
    @Override final public int startOfLineAnyEOL(int index) {return startOfDelimitedByAny(EOL_CHARS, index);}
    // @formatter:on

    @Override
    final public int startOfDelimitedBy(@NotNull CharSequence s, int index) {
        index = rangeLimit(index, 0, length());
        int offset = lastIndexOf(s, index - 1);
        return offset == -1 ? 0 : offset + 1;
    }

    @Override
    final public int startOfDelimitedByAny(@NotNull CharSequence s, int index) {
        index = rangeLimit(index, 0, length());
        int offset = lastIndexOfAny(s, index - 1);
        return offset == -1 ? 0 : offset + 1;
    }

    @Override
    final public int startOfDelimitedByAnyNot(@NotNull CharSequence s, int index) {
        index = rangeLimit(index, 0, length());
        int offset = lastIndexOfAnyNot(s, index - 1);
        return offset == -1 ? 0 : offset + 1;
    }

    @Override
    final public int endOfDelimitedBy(@NotNull CharSequence s, int index) {
        int length = length();
        index = rangeLimit(index, 0, length);
        int offset = indexOf(s, index);
        return offset == -1 ? length : offset;
    }

    @Override
    final public int endOfDelimitedByAny(@NotNull CharSequence s, int index) {
        int length = length();
        index = rangeLimit(index, 0, length);
        int offset = indexOfAny(s, index);
        return offset == -1 ? length : offset;
    }

    @Override
    final public int endOfDelimitedByAnyNot(@NotNull CharSequence s, int index) {
        int length = length();
        index = rangeLimit(index, 0, length);
        int offset = indexOfAnyNot(s, index);
        return offset == -1 ? length : offset;
    }

    @NotNull
    @Override
    public Range lineRangeAt(int index) {
        return Range.of(startOfLine(index), endOfLine(index));
    }

    @NotNull
    @Override
    public Range lineRangeAtAnyEOL(int index) {
        return Range.of(startOfLineAnyEOL(index), endOfLineAnyEOL(index));
    }

    // @formatter:off
    @NotNull @Override final public T lineAt(int index) {return subSequence(lineRangeAt(index));}
    @NotNull @Override final public T lineAtAnyEOL(int index) {return subSequence(lineRangeAtAnyEOL(index));}
    // @formatter:on

    @NotNull
    @Override
    final public T trimEOL() {
        int eolLength = eolEndLength();
        return eolLength > 0 ? subSequence(0, length() - eolLength) : (T) this;
    }

    @NotNull
    @Override
    final public T trimmedEOL() {
        int eolLength = eolEndLength();
        return eolLength > 0 ? subSequence(length() - eolLength) : nullSequence();
    }

    @NotNull
    @Override
    final public Range eolEndRange(int eolEnd) {
        int eolLength = eolEndLength(eolEnd);
        return eolLength == 0 ? Range.NULL : Range.of(eolEnd - eolLength, eolEnd);
    }

    @NotNull
    @Override
    public Range eolStartRange(int eolStart) {
        int eolLength = eolStartLength(eolStart);
        return eolLength == 0 ? Range.NULL : Range.of(eolStart, eolStart + eolLength);
    }

    // @formatter:off
    @NotNull @Override final public T trimTailBlankLines() {Range range = trailingBlankLinesRange();return range.isNull() ?  (T) this : subSequenceBefore(range);}
    @NotNull @Override final public T trimLeadBlankLines() {Range range = leadingBlankLinesRange();return range.isNull() ?  (T) this : subSequenceAfter(range);}
    @NotNull @Override final public Range leadingBlankLinesRange() {return leadingBlankLinesRange(IRichSequence.EOL, 0, length());}
    @NotNull @Override final public Range leadingBlankLinesRange(int startIndex) {return leadingBlankLinesRange(IRichSequence.EOL, startIndex, length());}
    @NotNull @Override final public Range leadingBlankLinesRange(int fromIndex, int endIndex) { return leadingBlankLinesRange(IRichSequence.EOL, fromIndex, endIndex);}
    @NotNull @Override final public Range trailingBlankLinesRange() {return trailingBlankLinesRange(IRichSequence.EOL, 0, length());}
    @NotNull @Override final public Range trailingBlankLinesRange(int fromIndex) {return trailingBlankLinesRange(IRichSequence.EOL, fromIndex, length());}
    @NotNull @Override final public Range trailingBlankLinesRange(int startIndex, int fromIndex) { return trailingBlankLinesRange(IRichSequence.EOL,startIndex,fromIndex);}
    // @formatter:on

    @NotNull
    @Override
    final public Range trailingBlankLinesRange(CharSequence eolChars, int startIndex, int fromIndex) {
        fromIndex = Math.min(fromIndex, length());
        startIndex = rangeLimit(startIndex, 0, fromIndex);

        int iMax = fromIndex;
        int lastEOL = iMax;
        int i;

        for (i = iMax; i-- > startIndex; ) {
            char c = charAt(i);
            if (indexOf(eolChars, c) != -1) lastEOL = Math.min(i + Math.min(eolStartLength(i), 1), fromIndex);
            else if (c != ' ' && c != '\t') break;
        }

        if (i < startIndex) return Range.of(startIndex, fromIndex);
        else if (lastEOL != iMax) return Range.of(lastEOL, fromIndex);
        else return Range.NULL;
    }

    @NotNull
    @Override
    final public Range leadingBlankLinesRange(@NotNull CharSequence eolChars, int fromIndex, int endIndex) {
        endIndex = Math.min(endIndex, length());
        fromIndex = rangeLimit(fromIndex, 0, endIndex);

        int iMax = endIndex;
        int lastEOL = -1;
        int i;

        for (i = fromIndex; i < iMax; i++) {
            char c = charAt(i);
            if (indexOf(eolChars, c) != -1) lastEOL = i;
            else if (c != ' ' && c != '\t') break;
        }

        if (i == iMax) return Range.of(fromIndex, endIndex);
        else if (lastEOL >= 0) return Range.of(fromIndex, Math.min(lastEOL + Math.min(eolStartLength(lastEOL), 1), endIndex));
        else return Range.NULL;
    }

    // @formatter:off
    @NotNull @Override final public List<Range> blankLinesRemovedRanges() { return blankLinesRemovedRanges(IRichSequence.EOL, 0, length());}
    @NotNull @Override final public List<Range> blankLinesRemovedRanges(int fromIndex) { return blankLinesRemovedRanges(IRichSequence.EOL, fromIndex, length());}
    @NotNull @Override final public List<Range> blankLinesRemovedRanges(int fromIndex, int endIndex) { return blankLinesRemovedRanges(IRichSequence.EOL, fromIndex, endIndex);}
    // @formatter:on

    @NotNull
    @Override
    final public List<Range> blankLinesRemovedRanges(@NotNull CharSequence eolChars, int fromIndex, int endIndex) {
        endIndex = Math.min(endIndex, length());
        fromIndex = rangeLimit(fromIndex, 0, endIndex);
        int lastPos = fromIndex;
        ArrayList<Range> ranges = new ArrayList<>();

        while (lastPos < endIndex) {
            Range blankLines = leadingBlankLinesRange(eolChars, lastPos, endIndex);
            if (blankLines.isNull()) {
                int endOfLine = Math.min(endOfLine(lastPos) + 1, endIndex);
                if (lastPos < endOfLine) ranges.add(Range.of(lastPos, endOfLine));
                lastPos = endOfLine;
            } else {
                if (lastPos < blankLines.getStart()) ranges.add(Range.of(lastPos, blankLines.getStart()));
                lastPos = blankLines.getEnd();
            }
        }
        return ranges;
    }

    // @formatter:off
    @NotNull @Override public T trimToEndOfLine(boolean includeEol) { return trimToEndOfLine(IRichSequence.EOL,includeEol, 0);  }
    @NotNull @Override public T trimToEndOfLine(int index) { return trimToEndOfLine(IRichSequence.EOL,false, 0);  }
    @NotNull @Override public T trimToEndOfLine() { return trimToEndOfLine(IRichSequence.EOL,false, 0);   }
    @NotNull @Override public T trimToEndOfLine(boolean includeEol, int index) { return trimToEndOfLine(IRichSequence.EOL,includeEol, index); }

    @NotNull @Override public T trimToStartOfLine(boolean includeEol) { return trimToStartOfLine(IRichSequence.EOL,includeEol, 0);  }
    @NotNull @Override public T trimToStartOfLine(int index) { return trimToStartOfLine(IRichSequence.EOL,false, 0);  }
    @NotNull @Override public T trimToStartOfLine() { return trimToStartOfLine(IRichSequence.EOL,false, 0);   }
    @NotNull @Override public T trimToStartOfLine(boolean includeEol, int index) { return trimToStartOfLine(IRichSequence.EOL,includeEol, index); }
    // @formatter:on

    @NotNull
    @Override
    // TEST:
    public T trimToEndOfLine(@NotNull CharSequence eolChars, boolean includeEol, int index) {
        int eolPos = endOfDelimitedByAny(eolChars, index);
        if (eolPos < length()) {
            int endIndex = includeEol ? eolPos + eolStartLength(eolPos) : eolPos;
            return subSequence(0, endIndex);
        }
        return (T) this;
    }

    @NotNull
    @Override
    // TEST:
    public T trimToStartOfLine(@NotNull CharSequence eolChars, boolean includeEol, int index) {
        int eolPos = startOfDelimitedByAny(eolChars, index);
        if (eolPos > 0) {
            int startIndex = includeEol ? eolPos - eolEndLength(eolPos - 1) : eolPos;
            return subSequence(startIndex);
        }
        return (T) this;
    }

    // @formatter:off
    @NotNull @Override final public T ifNull(@NotNull T other) {return isNull() ? other : (T) this;}
    @NotNull @Override final public T ifNullEmptyAfter(@NotNull T other) {return isNull() ? other.subSequence(other.length(), other.length()) : (T) this;}
    @NotNull @Override final public T ifNullEmptyBefore(@NotNull T other) {return isNull() ? other.subSequence(0, 0) : (T) this;}
    @NotNull @Override final public T nullIfEmpty() {return isEmpty() ? nullSequence() : (T) this;}
    @NotNull @Override final public T nullIfBlank() {return isBlank() ? nullSequence() : (T) this;}
    @NotNull @Override final public T nullIf(boolean condition) {return condition ? nullSequence() : (T) this;}
    @NotNull @Override final public T nullIfNot(@NotNull BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches) {return nullIf(predicate.negate(),matches);}
    @NotNull @Override final public T nullIf(@NotNull Predicate<? super CharSequence> predicate, CharSequence... matches) {return nullIf((o1, o2) -> predicate.test(o2), matches);}
    @NotNull @Override final public T nullIfNot(@NotNull Predicate<? super CharSequence> predicate, CharSequence... matches) {return nullIfNot((o1, o2) -> predicate.test(o2), matches);}
    @NotNull @Override final public T nullIf(CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) this::matches,matches);}
    @NotNull @Override final public T nullIfNot(CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) this::matches,matches);}
    @NotNull @Override final public T nullIfStartsWith(CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) this::startsWith,matches);}
    @NotNull @Override final public T nullIfNotStartsWith(CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) this::startsWith,matches);}
    @NotNull @Override final public T nullIfEndsWith(CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) this::endsWith,matches);}
    @NotNull @Override final public T nullIfNotEndsWith(CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) this::endsWith,matches);}
    @NotNull @Override final public T nullIfStartsWithIgnoreCase(CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) this::startsWithIgnoreCase,matches);}
    @NotNull @Override final public T nullIfNotStartsWithIgnoreCase(CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) this::startsWithIgnoreCase,matches);}
    @NotNull @Override final public T nullIfEndsWithIgnoreCase(CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) this::endsWithIgnoreCase,matches);}
    @NotNull @Override final public T nullIfNotEndsWithIgnoreCase(CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) this::endsWithIgnoreCase,matches);}
    @NotNull @Override final public T nullIfStartsWith(boolean ignoreCase, CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) prefix -> startsWith(prefix, ignoreCase),matches);}
    @NotNull @Override final public T nullIfNotStartsWith(boolean ignoreCase, CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) prefix -> startsWith(prefix, ignoreCase),matches);}
    @NotNull @Override final public T nullIfEndsWith(boolean ignoreCase, CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) suffix -> endsWith(suffix, ignoreCase),matches);}
    @NotNull @Override final public T nullIfNotEndsWith(boolean ignoreCase, CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) suffix -> endsWith(suffix, ignoreCase),matches);}
    // @formatter:on

    @NotNull
    @Override
    final public T nullIf(@NotNull BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches) {
        for (CharSequence match : matches) {
            if (predicate.test((T) this, match)) return nullSequence();
        }
        return (T) this;
    }

    // @formatter:off
    @Override final public boolean isEmpty() {return length() == 0;}
    @Override final public boolean isBlank() {return isEmpty() || countLeading(WHITESPACE_CHARS, 0, length()) == length();}
    @Override final public boolean isNull() {return this == nullSequence();}
    @Override final public boolean isNotNull() {return this != nullSequence();}
    @Override final public boolean endsWith(@NotNull CharSequence suffix) {return length() > 0 && matchCharsReversed(suffix, length() - 1, false);}
    @Override final public boolean startsWith(@NotNull CharSequence prefix) {return length() > 0 && matchChars(prefix, 0, false);}
    @Override final public boolean endsWith(@NotNull CharSequence suffix, boolean ignoreCase) {return length() > 0 && matchCharsReversed(suffix, length() - 1, ignoreCase);}
    @Override final public boolean startsWith(@NotNull CharSequence prefix, boolean ignoreCase) {return length() > 0 && matchChars(prefix, 0, ignoreCase);}
    // @formatter:on

    // @formatter:off
    @NotNull @Override final public T removeSuffix(@NotNull CharSequence suffix) {return !endsWith(suffix) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removePrefix(@NotNull CharSequence prefix) {return !startsWith(prefix) ? (T) this : subSequence(prefix.length(), length());}
    @NotNull @Override final public T removeProperSuffix(@NotNull CharSequence suffix) {return length() <= suffix.length() || !endsWith(suffix) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removeProperPrefix(@NotNull CharSequence prefix) {return length() <= prefix.length() || !startsWith(prefix) ? (T) this : subSequence(prefix.length(), length());}
    @Override final public boolean endsWithIgnoreCase(@NotNull CharSequence suffix) {return length() > 0 && matchCharsReversed(suffix, length() - 1, true);}
    @Override final public boolean startsWithIgnoreCase(@NotNull CharSequence prefix) {return length() > 0 && matchChars(prefix, 0, true);}
    @NotNull @Override final public T removeSuffixIgnoreCase(@NotNull CharSequence suffix) {return !endsWithIgnoreCase(suffix) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removePrefixIgnoreCase(@NotNull CharSequence prefix) {return !startsWithIgnoreCase(prefix) ? (T) this : subSequence(prefix.length(), length());}
    @NotNull @Override final public T removeProperSuffixIgnoreCase(@NotNull CharSequence suffix) {return length() <= suffix.length() || !endsWithIgnoreCase(suffix) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removeProperPrefixIgnoreCase(@NotNull CharSequence prefix) {return length() <= prefix.length() || !startsWithIgnoreCase(prefix) ? (T) this : subSequence(prefix.length(), length());}
    @NotNull @Override final public T removeSuffix(@NotNull CharSequence suffix, boolean ignoreCase) {return !endsWith(suffix, ignoreCase) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removePrefix(@NotNull CharSequence prefix, boolean ignoreCase) {return !startsWith(prefix, ignoreCase) ? (T) this : subSequence(prefix.length(), length());}
    @NotNull @Override final public T removeProperSuffix(@NotNull CharSequence suffix, boolean ignoreCase) {return length() <= suffix.length() || !endsWith(suffix, ignoreCase) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removeProperPrefix(@NotNull CharSequence prefix, boolean ignoreCase) {return length() <= prefix.length() || !startsWith(prefix, ignoreCase) ? (T) this : subSequence(prefix.length(), length());}
    // @formatter:on

    @NotNull
    @Override
    public T insert(@NotNull CharSequence chars, int index) {
        index = Math.max(0, Math.min(length(), index));

        if (chars.length() == 0) {
            return (T) this;
        } else if (index == 0) {
            return prefixWith(chars);
        } else if (index == length()) {
            return suffixWith(chars);
        } else {
            return getBuilder().append(subSequence(0, index)).append(chars).append(subSequence(index)).toSequence();
        }
    }

    @NotNull
    @Override
    public T delete(int startIndex, int endIndex) {
        endIndex = Math.max(0, Math.min(length(), endIndex));
        startIndex = Math.min(endIndex, Math.max(0, startIndex));

        if (startIndex == endIndex) {
            return (T) this;
        } else if (startIndex == 0) {
            return subSequence(endIndex);
        } else if (endIndex == length()) {
            return subSequence(0, startIndex);
        } else {
            return getBuilder().append(subSequence(0, startIndex)).append(subSequence(endIndex)).toSequence();
        }
    }

    @NotNull
    @Override
    // TEST:
    final public T toLowerCase() {
        return toMapped(ChangeCase.toLowerCase);
    }

    @NotNull
    @Override
    // TEST:
    final public T toUpperCase() {
        return toMapped(ChangeCase.toUpperCase);
    }

    // @formatter:off
    @Override final public boolean matches(@NotNull CharSequence chars) {return chars.length() == length() && matchChars(chars, 0, false);}
    @Override final public boolean matchesIgnoreCase(@NotNull CharSequence chars) {return chars.length() == length() && matchChars(chars, 0, true);}
    @Override final public boolean matches(@NotNull CharSequence chars, boolean ignoreCase) {return chars.length() == length() && matchChars(chars, 0, ignoreCase);}

    @Override final public boolean matchChars(@NotNull CharSequence chars) {return matchChars(chars, 0, false);}
    @Override final public boolean matchCharsIgnoreCase(@NotNull CharSequence chars) {return matchChars(chars, 0, true);}
    @Override final public boolean matchChars(@NotNull CharSequence chars, boolean ignoreCase) {return matchChars(chars, 0, ignoreCase);}
    @Override final public boolean matchChars(@NotNull CharSequence chars, int startIndex) {return matchChars(chars, startIndex, false);}
    @Override final public boolean matchCharsIgnoreCase(@NotNull CharSequence chars, int startIndex) {return matchChars(chars, startIndex, false);}

    @Override final public boolean matchCharsReversed(@NotNull CharSequence chars, int endIndex) {return endIndex + 1 >= chars.length() && matchChars(chars, endIndex + 1 - chars.length(), false);}
    @Override final public boolean matchCharsReversedIgnoreCase(@NotNull CharSequence chars, int endIndex) {return endIndex + 1 >= chars.length() && matchChars(chars, endIndex + 1 - chars.length(), true);}
    @Override final public boolean matchCharsReversed(@NotNull CharSequence chars, int endIndex, boolean ignoreCase) {return endIndex + 1 >= chars.length() && matchChars(chars, endIndex + 1 - chars.length(), ignoreCase);}
    // @formatter:on

    @Override
    // TEST:
    final public boolean matchChars(@NotNull CharSequence chars, int startIndex, boolean ignoreCase) {
        int iMax = chars.length();
        if (iMax > length() - startIndex) return false;

        if (ignoreCase) {
            for (int i = 0; i < iMax; i++) {
                char c1 = chars.charAt(i);
                char c2 = charAt(i + startIndex);
                if (c1 != c2) {
                    char u1 = Character.toUpperCase(c1);
                    char u2 = Character.toUpperCase(c2);
                    if (u1 == u2) {
                        continue;
                    }
                    // Unfortunately, conversion to uppercase does not work properly
                    // for the Georgian alphabet, which has strange rules about case
                    // conversion.  So we need to make one last check before
                    // exiting.
                    if (Character.toLowerCase(u1) == Character.toLowerCase(u2)) {
                        continue;
                    }
                    return false;
                }
            }
        } else {
            for (int i = 0; i < iMax; i++) {
                if (chars.charAt(i) != charAt(i + startIndex)) return false;
            }
        }
        return true;
    }

    @NotNull
    @Override
    final public T subSequence(int startIndex) {
        return subSequence(startIndex, length());
    }

    @NotNull
    @Override
    public String toString() {
        int iMax = length();
        StringBuilder sb = new StringBuilder(iMax);

        for (int i = 0; i < iMax; i++) {
            sb.append(charAt(i));
        }

        return sb.toString();
    }

    @NotNull
    @Override
    final public String normalizeEOL() {
        return Escaping.normalizeEOL(toString());
    }

    @NotNull
    @Override
    final public String normalizeEndWithEOL() {
        return Escaping.normalizeEndWithEOL(toString());
    }

    @NotNull
    @Override
    final public String toVisibleWhitespaceString() {
        StringBuilder sb = new StringBuilder();
        int iMax = length();
        for (int i = 0; i < iMax; i++) {
            char c = charAt(i);
            String s = visibleSpacesMap.get(c);

            if (s != null) {
                sb.append(s);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // @formatter:off
    @NotNull @Override final public T[] split(char delimiter) {return split(String.valueOf(delimiter), 0, 0, WHITESPACE_CHARS);}
    @NotNull @Override final public T[] split(char delimiter, int limit) {return split(String.valueOf(delimiter), limit, 0, WHITESPACE_CHARS);}
    @NotNull @Override final public T[] split(char delimiter, int limit, int flags) {return split(String.valueOf(delimiter), limit, flags, WHITESPACE_CHARS);}
    @NotNull @Override final public T[] split(char delimiter, int limit, int flags, String trimChars) {return split(String.valueOf(delimiter), limit, flags, trimChars);}
    @NotNull @Override final public T[] split(@NotNull CharSequence delimiter) {return split(delimiter, 0, 0, WHITESPACE_CHARS);}
    @NotNull @Override final public T[] split(@NotNull CharSequence delimiter, int limit) {return split(delimiter, limit, 0, WHITESPACE_CHARS);}
    @NotNull @Override final public T[] split(@NotNull CharSequence delimiter, int limit, int flags) {return split(delimiter, limit, flags, WHITESPACE_CHARS);}
    // @formatter:on

    @NotNull
    @Override
    final public T[] split(@NotNull CharSequence delimiter, int limit, int flags, @Nullable String trimChars) {
        if (trimChars == null) trimChars = WHITESPACE_CHARS;
        if (limit < 1) limit = Integer.MAX_VALUE;

        boolean includeDelimiterParts = (flags & SPLIT_INCLUDE_DELIM_PARTS) != 0;
        int includeDelimiter = !includeDelimiterParts && (flags & SPLIT_INCLUDE_DELIMS) != 0 ? delimiter.length() : 0;
        boolean trimParts = (flags & SPLIT_TRIM_PARTS) != 0;
        boolean skipEmpty = (flags & SPLIT_SKIP_EMPTY) != 0;
        ArrayList<T> items = new ArrayList<>();

        int lastPos = 0;
        int length = length();
        if (limit > 1) {
            while (lastPos < length) {
                int pos = indexOf(delimiter, lastPos);
                if (pos < 0) break;

                if (lastPos < pos || !skipEmpty) {
                    T item = subSequence(lastPos, pos + includeDelimiter);
                    if (trimParts) item = item.trim(trimChars);
                    if (!item.isEmpty() || !skipEmpty) {
                        items.add(item);
                        if (includeDelimiterParts) {
                            items.add(subSequence(pos, pos + delimiter.length()));
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
            T item = subSequence(lastPos, length);
            if (trimParts) item = item.trim(trimChars);
            if (!item.isEmpty() || !skipEmpty) {
                items.add(item);
            }
        }
        return items.toArray(emptyArray());
    }

    // @formatter:off
    @NotNull @Override final public T appendTo(@NotNull StringBuilder out, @Nullable CharMapper charMapper) {return appendTo(out, charMapper, 0, length());}
    @NotNull @Override final public T appendTo(@NotNull StringBuilder out, @Nullable CharMapper charMapper, int startIndex) {return appendTo(out, charMapper, startIndex, length());}
    @NotNull @Override final public T appendTo(@NotNull StringBuilder out) {return appendTo(out, null, 0, length());}
    @NotNull @Override final public T appendTo(@NotNull StringBuilder out, int startIndex) {return appendTo(out, null, startIndex, length());}
    @NotNull @Override final public T appendTo(@NotNull StringBuilder out, int startIndex, int endIndex) {return appendTo(out, null, startIndex, endIndex);}
    // @formatter:on

    @NotNull
    @Override
    final public T appendTo(@NotNull StringBuilder out, @Nullable CharMapper charMapper, int startIndex, int endIndex) {
        CharSequence useSequence = charMapper == null ? this : toMapped(charMapper);
        out.append(useSequence, startIndex, endIndex);
        return (T) this;
    }

    // @formatter:off
    @NotNull @Override final public T appendRangesTo(@NotNull StringBuilder out, @Nullable CharMapper charMapper, Range... ranges) {return appendRangesTo(out, charMapper, new ArrayIterable<>(ranges));}
    @NotNull @Override final public T appendRangesTo(@NotNull StringBuilder out, Range... ranges) {return appendRangesTo(out, null, new ArrayIterable<>(ranges));}
    @NotNull @Override final public T appendRangesTo(@NotNull StringBuilder out, Iterable<? extends Range> ranges) {return appendRangesTo(out, null, ranges);}
    // @formatter:on

    @NotNull
    @Override
    final public T appendRangesTo(@NotNull StringBuilder out, @Nullable CharMapper charMapper, Iterable<? extends Range> ranges) {
        CharSequence useSequence = charMapper == null ? this : toMapped(charMapper);

        for (Range range : ranges) {
            if (range != null && range.isNotNull()) out.append(useSequence, range.getStart(), range.getEnd());
        }
        return (T) this;
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

    @NotNull
    @Override
    final public int[] indexOfAll(@NotNull CharSequence s) {
        int length = s.length();
        if (length == 0) return EMPTY_INDICES;
        int pos = indexOf(s);
        if (pos == -1) return EMPTY_INDICES;

        int iMax = 0;
        int[] indices = new int[32];
        indices[iMax++] = pos;

        while (true) {
            pos = indexOf(s, pos + length);
            if (pos == -1) break;
            if (indices.length <= iMax) indices = expandTo(indices, iMax + 1, 32);
            indices[iMax++] = pos;
        }
        return truncateTo(indices, iMax);
    }

    @NotNull
    @Override
    public T prefixWith(@Nullable CharSequence prefix) {
        return prefix == null || prefix.length() == 0 ? (T) this : getBuilder().append(prefix).append(this).toSequence();
    }

    @NotNull
    @Override
    public T suffixWith(@Nullable CharSequence suffix) {
        // convoluted to allow BasedCharSequence to use PrefixedCharSequence so all fits into SegmentedCharSequence
        return suffix == null || suffix.length() == 0 ? (T) this : getBuilder().append(this).append(suffix).toSequence();
    }

    @NotNull
    @Override
    final public T prefixOnceWith(@Nullable CharSequence prefix) {
        return prefix == null || prefix.length() == 0 || startsWith(prefix) ? (T) this : prefixWith(prefix);
    }

    @NotNull
    @Override
    final public T suffixOnceWith(@Nullable CharSequence suffix) {
        return suffix == null || suffix.length() == 0 || endsWith(suffix) ? (T) this : suffixWith(suffix);
    }

    @NotNull
    @Override
    final public T replace(int startIndex, int endIndex, @NotNull CharSequence replacement) {
        int length = length();
        startIndex = Math.max(startIndex, 0);
        endIndex = Math.min(endIndex, length);

        SequenceBuilder<?, T> segments = getBuilder();
        return segments.append(subSequence(0, startIndex)).append(replacement).append(subSequence(endIndex)).toSequence();
    }

    @NotNull
    @Override
    final public T replace(@NotNull CharSequence find, @NotNull CharSequence replace) {
        int[] indices = indexOfAll(find);
        if (indices.length == 0) return (T) this;
        SequenceBuilder<?, T> segments = getBuilder();

        int iMax = indices.length;
        int length = length();

        int i = 0;
        int lastPos = 0;
        while (i < iMax) {
            int pos = indices[i++];
            if (lastPos < pos) segments.append(subSequence(lastPos, pos));
            lastPos = pos + find.length();
            segments.append(replace);
        }

        if (lastPos < length) {
            segments.append(subSequence(lastPos, length));
        }

        return segments.toSequence();
    }

    @NotNull
    @Override
    final public T append(CharSequence... sequences) {
        return append(new ArrayIterable<>(sequences));
    }

    @NotNull
    @Override
    final public T append(Iterable<? extends CharSequence> sequences) {
        SequenceBuilder<?, T> segments = getBuilder();
        segments.append(this);
        for (CharSequence sequence : sequences) {
            segments.append(sequence);
        }
        return segments.toSequence();
    }

    @NotNull
    @Override
    final public T extractRanges(Range... ranges) {
        return extractRanges(new ArrayIterable<>(ranges));
    }

    @NotNull
    @Override
    final public T extractRanges(Iterable<Range> ranges) {
        SequenceBuilder<?, T> segments = getBuilder();
        for (Range range : ranges) {
            if (!(range == null || range.isNull())) segments.append(range.safeSubSequence(this));
        }
        return segments.toSequence();
    }

    @Override
    final public int columnAtIndex(int index) {
        int lineStart = lastIndexOfAny(EOL_CHARS, index);
        return index - (lineStart == -1 ? 0 : lineStart + eolStartLength(lineStart));
    }

    @NotNull
    @Override
    final public Pair<Integer, Integer> lineColumnAtIndex(int index) {
        int iMax = length();
        if (index < 0 || index > iMax) {
            throw new IllegalArgumentException("Index: " + index + " out of range [0, " + iMax + "]");
        }

        boolean hadCr = false;
        int line = 0;
        int col = 0;
        for (int i = 0; i < index; i++) {
            char c1 = charAt(i);
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

    @Override
    final public boolean equals(Object other) {
        return (this == other) || other instanceof CharSequence && ((CharSequence) other).length() == length() && matchChars((CharSequence) other, 0, false);
    }

    @Override
    public boolean equalsIgnoreCase(@Nullable Object other) {
        return (this == other) || (other instanceof CharSequence) && ((CharSequence) other).length() == length() && matchChars((CharSequence) other, 0, true);
    }

    @Override
    public boolean equals(@Nullable Object other, boolean ignoreCase) {
        return (this == other) || other instanceof CharSequence && ((CharSequence) other).length() == length() && matchChars((CharSequence) other, 0, ignoreCase);
    }
}
