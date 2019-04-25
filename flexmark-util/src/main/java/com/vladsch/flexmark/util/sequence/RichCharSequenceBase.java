package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.mappers.CharMapper;
import com.vladsch.flexmark.util.mappers.LowerCaseMapper;
import com.vladsch.flexmark.util.mappers.UpperCaseMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A CharSequence that wraps original char sequence and maps '\0' to '\uFFFD'
 * and adds rich set of string manipulation and test functions.
 */
public abstract class RichCharSequenceBase<T extends RichCharSequence<T>> implements RichCharSequence<T> {
    private static int[] EMPTY_INDICES = { };
    private static final Map<Character, String> visibleSpacesMap;
    static {
        HashMap<Character, String> charMap = new HashMap<Character, String>();
        visibleSpacesMap = charMap;
        charMap.put('\n', "\\n");
        charMap.put('\r', "\\r");
        charMap.put('\f', "\\f");
        charMap.put('\t', "\\u2192");
    }
    public <S extends RichCharSequence<S>> S firstNonNull(S... sequences) {
        if (sequences.length > 0) {
            S NULL = sequences[0].nullSequence();

            for (S sequence : sequences) {
                if (sequence != null && sequence.isNotNull()) return sequence;
            }

            return (S) NULL;
        }

        return null;
    }

    static boolean isVisibleWhitespace(char c) {
        return visibleSpacesMap.containsKey(c);
    }

    @Override
    public T sequenceOf(CharSequence charSequence) {
        return sequenceOf(charSequence, 0, charSequence.length());
    }

    /**
     * Factory function
     *
     * @param charSequence char sequence from which to construct a rich char sequence
     * @param startIndex   start index of the sequence to use
     * @return rich char sequence from given inputs
     */
    @Override
    final public T sequenceOf(CharSequence charSequence, int startIndex) {
        return sequenceOf(charSequence, startIndex, charSequence.length());
    }

    @Override
    final public T endSequence(int start, int end) {
        int length = length();
        int useStart = length - start;
        int useEnd = length - end;

        if (useStart < 0) useStart = 0;
        else if (useStart > length) useStart = length;
        if (useEnd < 0) useEnd = 0;
        else if (useEnd > length) useEnd = length;

        if (useStart > useEnd) useStart = useEnd;

        if (useStart == 0 && useEnd == length) {
            return (T) this;
        } else {
            return subSequence(useStart, useEnd);
        }
    }

    @Override
    final public T endSequence(int start) {
        int length = length();
        if (start <= 0) {
            return subSequence(length, length);
        } else if (start >= length) {
            return (T) this;
        } else {
            return subSequence(length - start, length);
        }
    }

    @Override
    final public char endCharAt(int index) {
        if (index < 0 || index >= length()) return '\0';
        return charAt(length() - index);
    }

    @Override
    final public T midSequence(int start, int end) {
        int length = length();
        int useStart = start < 0 ? length + start : start;
        int useEnd = end < 0 ? length + end : end;

        if (useStart < 0) useStart = 0;
        else if (useStart > length) useStart = length;
        if (useEnd < 0) useEnd = 0;
        else if (useEnd > length) useEnd = length;

        if (useStart > useEnd) useStart = useEnd;
        if (useStart == 0 && useEnd == length) {
            return (T) this;
        } else {
            return subSequence(useStart, useEnd);
        }
    }

    @Override
    final public T midSequence(int start) {
        int length = length();
        int useStart = start < 0 ? length + start : start;
        if (useStart <= 0) {
            return (T) this;
        } else if (useStart >= length) {
            return subSequence(length, length);
        } else {
            return subSequence(useStart, length);
        }
    }

    @Override
    final public char midCharAt(int index) {
        if (index < -length() || index >= length()) return '\0';
        return charAt(index < 0 ? length() + index : index);
    }

    /**
     * @return the last character of the sequence or '\0' if empty
     */
    @Override
    final public char lastChar() {
        return isEmpty() ? '\0' : charAt(length() - 1);
    }

    /**
     * @return the first character of the sequence or '\0' if empty
     */
    @Override
    final public char firstChar() {
        return isEmpty() ? '\0' : charAt(0);
    }

    // @formatter:off
    @Override final public int indexOf(CharSequence s) { return indexOf(s, 0, length()); }
    @Override final public int indexOf(CharSequence s, int fromIndex) { return indexOf(s, fromIndex, length()); }
    // @formatter:on

    @Override
    final public int indexOf(CharSequence s, int fromIndex, int endIndex) {
        int sMax = s.length();
        if (sMax == 0) return fromIndex;
        if (endIndex > length()) endIndex = length();
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
    @Override final public int indexOfAny(CharSequence s) { return indexOfAny(s, 0, length()); }
    @Override final public int indexOf(char c, int fromIndex) { return indexOf(c, fromIndex, length()); }
    @Override final public int indexOfAny(char c1, char c2, int fromIndex) { return indexOfAny(c1, c2, fromIndex, length()); }
    @Override final public int indexOfAny(char c1, char c2, char c3, int fromIndex) { return indexOfAny(c1, c2, c3, fromIndex, length()); }
    @Override final public int indexOfAny(CharSequence s, int index) { return indexOfAny(s, index, length()); }
    // @formatter:on

    @Override
    final public int indexOf(char c, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            if (charAt(i) == c) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAny(char c1, char c2, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c == c1 || c == c2) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAny(char c1, char c2, char c3, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c == c1 || c == c2 || c == c3) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAny(CharSequence s, int fromIndex, int endIndex) {
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
                T sequence = sequenceOf(s);
                if (fromIndex < 0) fromIndex = 0;
                if (endIndex > length()) endIndex = length();
                for (int i = fromIndex; i < endIndex; i++) {
                    char c = charAt(i);
                    if (sequence.indexOf(c) != -1) return i;
                }
        }
        return -1;
    }

    // @formatter:off
    @Override final public int indexOfNot(char c) { return indexOfNot(c, 0, length()); }
    @Override final public int indexOfAnyNot(char c1, char c2) { return indexOfAnyNot(c1, c2, 0, length()); }
    @Override final public int indexOfAnyNot(char c1, char c2, char c3) { return indexOfAnyNot(c1, c2, c3, 0, length()); }
    @Override final public int indexOfAnyNot(CharSequence s) { return indexOfAnyNot(s, 0, length()); }
    @Override final public int indexOfNot(char c, int fromIndex) { return indexOfNot(c, fromIndex, length()); }
    @Override final public int indexOfAnyNot(char c1, char c2, int fromIndex) { return indexOfAnyNot(c1, c2, fromIndex, length()); }
    @Override final public int indexOfAnyNot(char c1, char c2, char c3, int fromIndex) { return indexOfAnyNot(c1, c2, c3, fromIndex, length()); }
    @Override final public int indexOfAnyNot(CharSequence s, int fromIndex) { return indexOfAnyNot(s, fromIndex, length()); }
    // @formatter:on

    @Override
    final public int indexOfNot(char c, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            if (charAt(i) != c) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAnyNot(char c1, char c2, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c != c1 && c != c2) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAnyNot(char c1, char c2, char c3, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c != c1 && c != c2 && c != c3) return i;
        }
        return -1;
    }

    @Override
    final public int indexOfAnyNot(CharSequence s, int fromIndex, int endIndex) {
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
                T sequence = sequenceOf(s);
                if (fromIndex < 0) fromIndex = 0;
                if (endIndex > length()) endIndex = length();
                for (int i = fromIndex; i < endIndex; i++) {
                    char c = charAt(i);
                    if (sequence.indexOf(c) == -1) return i;
                }
        }
        return -1;
    }

    // @formatter:off
    @Override final public int lastIndexOf(CharSequence s) { return lastIndexOf(s, 0, length()); }
    @Override final public int lastIndexOf(CharSequence s, int fromIndex) { return lastIndexOf(s, 0, fromIndex); }
    // @formatter:on

    @Override
    final public int lastIndexOf(CharSequence s, int startIndex, int fromIndex) {
        int sMax = s.length();
        if (sMax == 0) return startIndex;

        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();

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
    @Override final public int lastIndexOf(char c)                                        { return lastIndexOf(c, 0, length()); }
    @Override final public int lastIndexOfAny(char c1, char c2)                           { return lastIndexOfAny(c1, c2, 0, length()); }
    @Override final public int lastIndexOfAny(char c1, char c2, char c3)                  { return lastIndexOfAny(c1, c2, c3, 0, length()); }
    @Override final public int lastIndexOfAny(CharSequence s)                             { return lastIndexOfAny(s, 0, length()); }
    @Override final public int lastIndexOf(char c, int fromIndex)                        { return lastIndexOf(c, 0, fromIndex); }
    @Override final public int lastIndexOfAny(char c1, char c2, int fromIndex)           { return lastIndexOfAny(c1, c2, 0, fromIndex); }
    @Override final public int lastIndexOfAny(char c1, char c2, char c3, int fromIndex)  { return lastIndexOfAny(c1, c2, c3, 0, fromIndex); }
    @Override final public int lastIndexOfAny(CharSequence s, int fromIndex)             { return lastIndexOfAny(s, 0, fromIndex); }
    // @formatter:on

    @Override
    final public int lastIndexOf(char c, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            if (charAt(i) == c) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAny(char c1, char c2, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c == c1 || c == c2) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAny(char c1, char c2, char c3, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c == c1 || c == c2 || c == c3) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAny(CharSequence s, int startIndex, int fromIndex) {
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
                T sequence = sequenceOf(s);
                if (startIndex < 0) startIndex = 0;
                if (fromIndex >= length()) fromIndex = length();
                else fromIndex++;
                for (int i = fromIndex; i-- > startIndex; ) {
                    char c = charAt(i);
                    if (sequence.indexOf(c) != -1) return i;
                }
        }
        return -1;
    }

    // @formatter:off
    @Override final public int lastIndexOfNot(char c)                                         { return lastIndexOfNot(c, 0, length()); }
    @Override final public int lastIndexOfAnyNot(char c1, char c2)                            { return lastIndexOfAnyNot(c1, c2, 0, length()); }
    @Override final public int lastIndexOfAnyNot(char c1, char c2, char c3)                   { return lastIndexOfAnyNot(c1, c2, c3, 0, length()); }
    @Override final public int lastIndexOfAnyNot(CharSequence s)                              { return lastIndexOfAnyNot(s, 0, length()); }
    @Override final public int lastIndexOfNot(char c, int fromIndex)                         { return lastIndexOfNot(c, 0, fromIndex); }
    @Override final public int lastIndexOfAnyNot(char c1, char c2, int fromIndex)            { return lastIndexOfAnyNot(c1, c2, 0, fromIndex); }
    @Override final public int lastIndexOfAnyNot(char c1, char c2, char c3, int fromIndex)   { return lastIndexOfAnyNot(c1, c2, c3, 0, fromIndex); }
    @Override final public int lastIndexOfAnyNot(CharSequence s, int fromIndex)              { return lastIndexOfAnyNot(s, 0, fromIndex); }
    // @formatter:on

    @Override
    final public int lastIndexOfNot(char c, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            if (charAt(i) != c) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAnyNot(char c1, char c2, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c != c1 && c != c2) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAnyNot(char c1, char c2, char c3, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c != c1 && c != c2 && c != c3) return i;
        }
        return -1;
    }

    @Override
    final public int lastIndexOfAnyNot(CharSequence s, int startIndex, int fromIndex) {
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
                T sequence = sequenceOf(s);
                if (startIndex < 0) startIndex = 0;
                if (fromIndex >= length()) fromIndex = length();
                else fromIndex++;
                for (int i = fromIndex; i-- > startIndex; ) {
                    char c = charAt(i);
                    if (sequence.indexOf(c) == -1) return i;
                }
        }
        return -1;
    }

    // @formatter:off
    @Override final public int countOf(char c)                                        { return countOf(c, 0, length()); }
    @Override final public int countOfNot(char c)                                     { return countOfNot(c, 0, length()); }
    @Override final public int countOf(char c, int fromIndex)                         { return countOf(c, fromIndex, length()); }
    @Override final public int countOfNot(char c, int fromIndex)                      { return countOfNot(c, fromIndex, length()); }

    @Override final public int countOf()                                              { return countOfAny(WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override final public int countOfNot()                                           { return countOfAnyNot(WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override final public int countOfAny(CharSequence chars)                         { return countOfAny(chars, 0, length()); }
    @Override final public int countOfAnyNot(CharSequence chars)                      { return countOfAnyNot(chars, 0, length()); }
    @Override final public int countOfAny(CharSequence chars, int fromIndex)          { return countOfAny(chars, fromIndex, length()); }
    @Override final public int countOfAnyNot(CharSequence chars, int fromIndex)       { return countOfAnyNot(chars, fromIndex, length()); }
    // @formatter:on

    @Override
    final public int countOf(char c1, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        int count = 0;
        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c == c1) count++;
        }
        return count;
    }

    @Override
    final public int countOfAny(CharSequence s, int fromIndex, int endIndex) {
        T sequence = sequenceOf(s);
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        int count = 0;
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (sequence.indexOf(c) != -1) count++;
        }
        return count;
    }

    @Override
    final public int countOfNot(char c1, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        int count = 0;
        for (int i = fromIndex; i-- > startIndex; ) {
            char c = charAt(i);
            if (c != c1) count++;
        }
        return count;
    }

    @Override
    final public int countOfAnyNot(CharSequence s, int fromIndex, int endIndex) {
        T sequence = sequenceOf(s);
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        int count = 0;
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (sequence.indexOf(c) == -1) count++;
        }
        return count;
    }

    @Override
    final public int startOfDelimitedBy(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = lastIndexOf(s, index - 1);
        return offset == -1 ? 0 : offset + 1;
    }

    @Override
    final public int startOfDelimitedByAny(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = lastIndexOfAny(s, index - 1);
        return offset == -1 ? 0 : offset + 1;
    }

    @Override
    final public int startOfDelimitedByAnyNot(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = lastIndexOfAnyNot(s, index - 1);
        return offset == -1 ? 0 : offset + 1;
    }

    @Override
    final public int endOfDelimitedBy(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = indexOf(s, index);
        return offset == -1 ? length() : offset;
    }

    @Override
    final public int endOfDelimitedByAny(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = indexOfAny(s, index);
        return offset == -1 ? length() : offset;
    }

    @Override
    final public int endOfDelimitedByAnyNot(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = indexOfAnyNot(s, index);
        return offset == -1 ? length() : offset;
    }

    @Override
    final public int endOfLine(int index) {
        return endOfDelimitedBy(EOL, index);
    }

    @Override
    final public int endOfLineAnyEOL(int index) {
        return endOfDelimitedByAny(EOL_CHARS, index);
    }

    @Override
    final public int startOfLine(int index) {
        return startOfDelimitedBy(EOL, index);
    }

    @Override
    final public int startOfLineAnyEOL(int index) {
        return startOfDelimitedByAny(EOL_CHARS, index);
    }

    @Override
    final public T lineAt(int index) {
        return subSequence(startOfLine(index), endOfLine(index));
    }

    @Override
    final public T lineAtAnyEOL(int index) {
        return subSequence(startOfLineAnyEOL(index), endOfLineAnyEOL(index));
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
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        if (fromIndex > endIndex) fromIndex = endIndex;
        int index = indexOfNot(c, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    final public int countTrailing(char c, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        if (startIndex > fromIndex) startIndex = fromIndex;
        int index = lastIndexOfNot(c, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex - index - 1;
    }

    @Override
    final public int countLeadingNot(char c, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        if (fromIndex > endIndex) fromIndex = endIndex;
        int index = indexOf(c, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    final public int countTrailingNot(char c, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        if (startIndex > fromIndex) startIndex = fromIndex;
        int index = lastIndexOf(c, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex - index - 1;
    }

    // @formatter:off
    @Override final public int countLeading()                                          { return this.countLeading(WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override final public int countLeadingNot()                                       { return this.countLeadingNot(WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override final public int countTrailing()                                         { return this.countTrailing(WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override final public int countTrailingNot()                                      { return this.countTrailingNot(WHITESPACE_NO_EOL_CHARS, 0, length()); }

    @Override final public int countLeading(CharSequence chars)                        { return countLeading(chars, 0, length()); }
    @Override final public int countLeadingNot(CharSequence chars)                     { return countLeadingNot(chars, 0, length()); }
    @Override final public int countLeading(CharSequence chars, int fromIndex)         { return countLeading(chars, fromIndex, length()); }
    @Override final public int countLeadingNot(CharSequence chars, int fromIndex)      { return countLeadingNot(chars, fromIndex, length()); }

    @Override final public int countTrailing(CharSequence chars)                       { return countTrailing(chars, 0, length()); }
    @Override final public int countTrailingNot(CharSequence chars)                    { return countTrailingNot(chars, 0, length()); }
    @Override final public int countTrailing(CharSequence chars, int fromIndex)        { return countTrailing(chars, 0, fromIndex); }
    @Override final public int countTrailingNot(CharSequence chars, int fromIndex)     { return countTrailingNot(chars, 0, fromIndex); }
    // @formatter:on

    @Override
    final public int countLeading(CharSequence chars, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        if (fromIndex > endIndex) fromIndex = endIndex;
        int index = indexOfAnyNot(chars, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    public static int columnsToNextTabStop(int column) {
        // Tab stop is 4
        return 4 - (column % 4);
    }

    @Override
    final public int countLeadingColumns(int startColumn, CharSequence chars) {
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
    final public int countTrailing(CharSequence chars, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        if (startIndex > fromIndex) startIndex = fromIndex;
        int index = lastIndexOfAnyNot(chars, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex <= index ? 0 : fromIndex - index - 1;
    }

    @Override
    final public int countLeadingNot(CharSequence chars, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        if (fromIndex > endIndex) fromIndex = endIndex;
        int index = indexOfAny(chars, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    final public int countTrailingNot(CharSequence chars, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        if (startIndex > fromIndex) startIndex = fromIndex;
        int index = lastIndexOfAny(chars, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex <= index ? 0 : fromIndex - index - 1;
    }

    @Override
    final public T trimStart(int keepLength, CharSequence chars) {
        int trim = Utils.maxLimit(countLeading(chars, 0, length()), length() - keepLength);
        return trim > 0 ? subSequence(trim, length()) : (T) this;
    }

    @Override
    final public T trimmedStart(int keepLength, CharSequence chars) {
        int trim = Utils.maxLimit(countLeading(chars, 0, length()), length() - keepLength);
        return trim > 0 ? subSequence(0, trim) : nullSequence();
    }

    @Override
    final public T trimEnd(int keepLength, CharSequence chars) {
        int trim = Utils.maxLimit(countTrailing(chars, 0, length()), length() - keepLength);
        return trim > 0 ? subSequence(0, length() - trim) : (T) this;
    }

    @Override
    final public T trimmedEnd(int keepLength, CharSequence chars) {
        int trim = Utils.maxLimit(countTrailing(chars, 0, length()), length() - keepLength);
        return trim > 0 ? subSequence(length() - trim) : nullSequence();
    }

    @Override
    final public T trimStart(CharSequence chars) {
        return trimStart(0, chars);
    }

    @Override
    final public T trimmedStart(CharSequence chars) {
        return trimmedStart(0, chars);
    }

    @Override
    final public T trimEnd(CharSequence chars) {
        return trimEnd(0, chars);
    }

    @Override
    final public T trimmedEnd(CharSequence chars) {
        return trimmedEnd(0, chars);
    }

    @Override
    final public T trim(CharSequence chars) {
        int trimStart = countLeading(chars, 0, length());
        int trimEnd = countTrailing(chars, 0, length());
        int trimmed = trimStart + trimEnd;
        return trimmed > 0 ? (trimmed >= length() ? subSequence(0, 0) : subSequence(trimStart, length() - trimEnd)) : (T) this;
    }

    @Override
    final public T trimStart(int keepLength) {
        return trimStart(keepLength, WHITESPACE_CHARS);
    }

    @Override
    final public T trimmedStart(int keepLength) {
        return trimmedStart(keepLength, WHITESPACE_CHARS);
    }

    @Override
    final public T trimEnd(int keepLength) {
        return trimEnd(keepLength, WHITESPACE_CHARS);
    }

    @Override
    final public T trimmedEnd(int keepLength) {
        return trimmedEnd(keepLength, WHITESPACE_CHARS);
    }

    @Override
    final public T trimStart() {
        return trimStart(0, WHITESPACE_CHARS);
    }

    @Override
    final public T trimmedStart() {
        return trimmedStart(0, WHITESPACE_CHARS);
    }

    @Override
    final public T trimEnd() {
        return trimEnd(0, WHITESPACE_CHARS);
    }

    @Override
    final public T trimmedEnd() {
        return trimmedEnd(0, WHITESPACE_CHARS);
    }

    @Override
    final public T padStart(int length, char pad) {
        return length <= length() ? (T) this : sequenceOf(RepeatedCharSequence.of(pad, length - length()));
    }

    @Override
    final public T padEnd(int length, char pad) {
        return length <= length() ? (T) this : append(RepeatedCharSequence.of(pad, length - length()));
    }

    @Override
    final public T padStart(int length) {
        return padStart(length, ' ');
    }

    @Override
    final public T padEnd(int length) {
        return padEnd(length, ' ');
    }

    @Override
    final public int eolLength() {
        int startIndex = length() - 1;
        int pos = startIndex;
        if (pos >= 0) {
            char c = charAt(pos);
            if (c == '\r') {
                pos--;
                if (pos >= 0) {
                    if (charAt(pos) == '\n') {
                        pos--;
                    }
                }
            } else if (c == '\n') {
                pos--;
            }
        }

        return startIndex - pos;
    }

    @Override
    final public int eolLength(int eolStart) {
        int pos = eolStart;
        int length = length();
        if (pos >= 0 && pos < length) {
            char c = charAt(pos);
            if (c == '\r') {
                pos++;
                if (pos < length) {
                    if (charAt(pos) == '\n') {
                        pos++;
                    }
                }
            } else if (c == '\n') {
                pos++;
            }
        }

        return pos - eolStart;
    }

    @Override
    final public T trimEOL() {
        int trim = eolLength();
        return trim > 0 ? subSequence(0, length() - trim) : (T) this;
    }

    @Override
    final public T trimmedEOL() {
        int trim = eolLength();
        return trim > 0 ? subSequence(length() - trim) : nullSequence();
    }

    @Override
    final public T trim() {
        int trimStart = countLeading(WHITESPACE_CHARS, 0, length());
        if (trimStart == length()) {
            return subSequence(trimStart, trimStart);
        }

        int trimEnd = countTrailing(WHITESPACE_CHARS, 0, length());
        return trimStart > 0 || trimEnd > 0 ? subSequence(trimStart, length() - trimEnd) : (T) this;
    }

    @Override
    final public T ifNull(T other) {
        return isNull() ? other : (T) this;
    }

    @Override
    final public T ifNullEmptyAfter(T other) {
        return isNull() ? other.subSequence(other.length(), other.length()) : (T) this;
    }

    @Override
    final public T ifNullEmptyBefore(T other) {
        return isNull() ? other.subSequence(0, 0) : (T) this;
    }

    @Override
    final public T nullIfEmpty() {
        return isEmpty() ? nullSequence() : (T) this;
    }

    @Override
    final public T nullIfBlank() {
        return isBlank() ? nullSequence() : (T) this;
    }

    @Override
    final public T nullIf(boolean condition) {
        return condition ? nullSequence() : (T) this;
    }

    @Override
    final public T nullIf(CharSequence... matches) {
        for (CharSequence match : matches) {
            if (matches(match)) return nullSequence();
        }
        return (T) this;
    }

    @Override
    final public T nullIfNot(CharSequence... matches) {
        for (CharSequence match : matches) {
            if (matches(match)) return (T) this;
        }
        return nullSequence();
    }

    @Override
    final public T nullIfStartsWith(CharSequence... matches) {
        for (CharSequence match : matches) {
            if (startsWith(match)) return nullSequence();
        }
        return (T) this;
    }

    @Override
    final public T nullIfStartsWithNot(CharSequence... matches) {
        for (CharSequence match : matches) {
            if (startsWith(match)) return (T) this;
        }
        return nullSequence();
    }

    @Override
    final public T nullIfEndsWith(CharSequence... matches) {
        for (CharSequence match : matches) {
            if (endsWith(match)) return nullSequence();
        }
        return (T) this;
    }

    @Override
    final public T nullIfEndsWithNot(CharSequence... matches) {
        for (CharSequence match : matches) {
            if (endsWith(match)) return (T) this;
        }
        return nullSequence();
    }

    @Override
    final public boolean isEmpty() {
        return length() == 0;
    }

    @Override
    final public boolean isBlank() {
        return isEmpty() || countLeading(WHITESPACE_CHARS, 0, length()) == length();
    }

    @Override
    final public boolean isNull() {
        return this == nullSequence();
    }

    @Override
    final public boolean isNotNull() {
        return this != nullSequence();
    }

    @Override
    final public boolean endsWith(CharSequence suffix) {
        return length() > 0 && matchCharsReversed(suffix, length() - 1, false);
    }

    @Override
    final public boolean startsWith(CharSequence prefix) {
        return length() > 0 && matchChars(prefix, 0, false);
    }

    @Override
    final public T removeSuffix(CharSequence suffix) {
        return !endsWith(suffix) ? (T) this : subSequence(0, length() - suffix.length());
    }

    @Override
    final public T removePrefix(CharSequence prefix) {
        return !startsWith(prefix) ? (T) this : subSequence(prefix.length(), length());
    }

    @Override
    final public T removeProperSuffix(CharSequence suffix) {
        return length() <= suffix.length() || !endsWith(suffix) ? (T) this : subSequence(0, length() - suffix.length());
    }

    @Override
    final public T removeProperPrefix(CharSequence prefix) {
        return length() <= prefix.length() || !startsWith(prefix) ? (T) this : subSequence(prefix.length(), length());
    }

    @Override
    final public boolean endsWithIgnoreCase(CharSequence suffix) {
        return length() > 0 && matchCharsReversed(suffix, length() - 1, true);
    }

    @Override
    final public boolean startsWithIgnoreCase(CharSequence prefix) {
        return length() > 0 && matchChars(prefix, 0, true);
    }

    @Override
    final public T removeSuffixIgnoreCase(CharSequence suffix) {
        return !endsWithIgnoreCase(suffix) ? (T) this : subSequence(0, length() - suffix.length());
    }

    @Override
    final public T removePrefixIgnoreCase(CharSequence prefix) {
        return !startsWithIgnoreCase(prefix) ? (T) this : subSequence(prefix.length(), length());
    }

    @Override
    final public T removeProperSuffixIgnoreCase(CharSequence suffix) {
        return length() <= suffix.length() || !endsWithIgnoreCase(suffix) ? (T) this : subSequence(0, length() - suffix.length());
    }

    @Override
    final public T removeProperPrefixIgnoreCase(CharSequence prefix) {
        return length() <= prefix.length() || !startsWithIgnoreCase(prefix) ? (T) this : subSequence(prefix.length(), length());
    }

    @Override
    final public boolean endsWith(CharSequence suffix, boolean ignoreCase) {
        return length() > 0 && matchCharsReversed(suffix, length() - 1, ignoreCase);
    }

    @Override
    final public boolean startsWith(CharSequence prefix, boolean ignoreCase) {
        return length() > 0 && matchChars(prefix, 0, ignoreCase);
    }

    @Override
    final public T removeSuffix(CharSequence suffix, boolean ignoreCase) {
        return !endsWith(suffix, ignoreCase) ? (T) this : subSequence(0, length() - suffix.length());
    }

    @Override
    final public T removePrefix(CharSequence prefix, boolean ignoreCase) {
        return !startsWith(prefix, ignoreCase) ? (T) this : subSequence(prefix.length(), length());
    }

    @Override
    final public T removeProperSuffix(CharSequence suffix, boolean ignoreCase) {
        return length() <= suffix.length() || !endsWith(suffix, ignoreCase) ? (T) this : subSequence(0, length() - suffix.length());
    }

    @Override
    final public T removeProperPrefix(CharSequence prefix, boolean ignoreCase) {
        return length() <= prefix.length() || !startsWith(prefix, ignoreCase) ? (T) this : subSequence(prefix.length(), length());
    }

    @Override
    final public T toLowerCase() {
        return toMapped(LowerCaseMapper.INSTANCE);
    }

    @Override
    final public T toUpperCase() {
        return toMapped(UpperCaseMapper.INSTANCE);
    }

    @Override
    final public T toLowerCase(Locale locale) {
        return toMapped(new LowerCaseMapper(locale));
    }

    @Override
    final public T toUpperCase(Locale locale) {
        return toMapped(new UpperCaseMapper(locale));
    }

    @Override
    public T toMapped(CharMapper mapper) {
        return sequenceOf(MappedSequence.of(mapper, this));
    }

    @Override
    final public boolean matches(CharSequence chars) {
        return chars.length() == length() && matchChars(chars, 0, false);
    }

    @Override
    final public boolean matchesIgnoreCase(CharSequence chars) {
        return chars.length() == length() && matchChars(chars, 0, true);
    }

    @Override
    final public boolean matches(CharSequence chars, boolean ignoreCase) {
        return chars.length() == length() && matchChars(chars, 0, ignoreCase);
    }

    @Override
    final public boolean matchChars(CharSequence chars) {
        return matchChars(chars, 0, false);
    }

    @Override
    final public boolean matchCharsIgnoreCase(CharSequence chars) {
        return matchChars(chars, 0, true);
    }

    @Override
    final public boolean matchChars(CharSequence chars, boolean ignoreCase) {
        return matchChars(chars, 0, ignoreCase);
    }

    @Override
    final public boolean matchChars(CharSequence chars, int startIndex) {
        return matchChars(chars, startIndex, false);
    }

    @Override
    final public boolean matchCharsIgnoreCase(CharSequence chars, int startIndex) {
        return matchChars(chars, startIndex, false);
    }

    @Override
    final public boolean matchChars(CharSequence chars, int startIndex, boolean ignoreCase) {
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

    @Override
    final public boolean matchCharsReversed(CharSequence chars, int endIndex) {
        return endIndex + 1 >= chars.length() && matchChars(chars, endIndex + 1 - chars.length(), false);
    }

    @Override
    final public boolean matchCharsReversedIgnoreCase(CharSequence chars, int endIndex) {
        return endIndex + 1 >= chars.length() && matchChars(chars, endIndex + 1 - chars.length(), true);
    }

    @Override
    final public boolean matchCharsReversed(CharSequence chars, int endIndex, boolean ignoreCase) {
        return endIndex + 1 >= chars.length() && matchChars(chars, endIndex + 1 - chars.length(), ignoreCase);
    }

    @Override
    final public T subSequence(Range range) {
        return subSequence(range.getStart(), range.getEnd());
    }

    @Override
    final public T subSequence(int start) {
        return subSequence(start, length());
    }

    @Override
    final public T trimTailBlankLines() {
        int iMax = length();
        int lastEOL = iMax;
        int i;

        for (i = iMax; i-- > 0; ) {
            char c = charAt(i);
            if (c == '\n') lastEOL = i + 1;
            else if (lastEOL == iMax || (c != ' ' && c != '\t')) break;
        }
        if (i < 0) return subSequence(0, 0);
        if (lastEOL != iMax) return subSequence(0, lastEOL);
        return (T) this;
    }

    @Override
    final public T trimLeadBlankLines() {
        int iMax = length();
        int lastEOL = 0;
        int i;

        for (i = 0; i < iMax; i++) {
            char c = charAt(i);
            if (c == '\n') lastEOL = i + 1;
            else if (c != ' ' && c != '\t') break;
        }
        if (i == iMax) return subSequence(iMax, iMax);
        if (lastEOL != 0) return subSequence(lastEOL);
        return (T) this;
    }

    @Override
    public String toString() {
        int iMax = length();
        StringBuilder sb = new StringBuilder(iMax);

        for (int i = 0; i < iMax; i++) {
            sb.append(charAt(i));
        }

        return sb.toString();
    }

    @Override
    final public String normalizeEOL() {
        return Escaping.normalizeEOL(toString());
    }

    @Override
    final public String normalizeEndWithEOL() {
        return Escaping.normalizeEndWithEOL(toString());
    }

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

    @Override
    final public T[] split(char delimiter) {
        return split(delimiter, 0);
    }

    @Override
    final public T[] split(char delimiter, int limit) {
        return split(delimiter, limit, 0);
    }

    @Override
    final public T[] split(char delimiter, int limit, int flags) {
        return split(delimiter, limit, flags, WHITESPACE_CHARS);
    }

    @Override
    final public T[] split(CharSequence delimiter) {
        return split(delimiter, 0);
    }

    @Override
    final public T[] split(CharSequence delimiter, int limit) {
        return split(delimiter, limit, 0);
    }

    @Override
    final public T[] split(CharSequence delimiter, int limit, int flags) {
        return split(delimiter, limit, flags, WHITESPACE_CHARS);
    }

    @Override
    final public T[] split(char delimiter, int limit, int flags, String trimChars) {
        if (trimChars == null) trimChars = WHITESPACE_CHARS;
        if (limit < 1) limit = Integer.MAX_VALUE;

        boolean includeDelimiterParts = (flags & SPLIT_INCLUDE_DELIM_PARTS) != 0;
        int includeDelimiter = !includeDelimiterParts && (flags & SPLIT_INCLUDE_DELIMS) != 0 ? 1 : 0;
        boolean trimParts = (flags & SPLIT_TRIM_PARTS) != 0;
        boolean skipEmpty = (flags & SPLIT_SKIP_EMPTY) != 0;
        ArrayList<T> items = new ArrayList<T>();

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
                            items.add(subSequence(pos, pos + 1));
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

    @Override
    final public T[] split(CharSequence delimiter, int limit, int flags, String trimChars) {
        if (trimChars == null) trimChars = WHITESPACE_CHARS;
        if (limit < 1) limit = Integer.MAX_VALUE;

        boolean includeDelimiterParts = (flags & SPLIT_INCLUDE_DELIM_PARTS) != 0;
        int includeDelimiter = !includeDelimiterParts && (flags & SPLIT_INCLUDE_DELIMS) != 0 ? delimiter.length() : 0;
        boolean trimParts = (flags & SPLIT_TRIM_PARTS) != 0;
        boolean skipEmpty = (flags & SPLIT_SKIP_EMPTY) != 0;
        ArrayList<T> items = new ArrayList<T>();

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

    @Override
    final public T appendTo(StringBuilder out) {
        return appendTo(out, 0, length());
    }

    @Override
    final public T appendTo(StringBuilder out, int start) {
        return appendTo(out, start, length());
    }

    @Override
    final public T appendTo(StringBuilder out, int start, int end) {
        out.append(this, start, end);
        return (T) this;
    }

    public static int[] expandTo(int[] indices, int length, int step) {
        int remainder = length & step;
        int next = length + (remainder != 0 ? step : 0);
        if (indices.length < length) {
            int[] replace = new int[length];
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

    @Override
    final public int[] indexOfAll(CharSequence s) {
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
            if (indices.length < iMax) indices = expandTo(indices, iMax, 32);
            indices[iMax++] = pos;
        }
        return truncateTo(indices, iMax);
    }

    @Override
    final public T replace(CharSequence find, CharSequence replace) {
        int[] indices = indexOfAll(find);
        if (indices.length == 0) return (T) this;

        int iMax = indices.length;
        StringBuilder sb = new StringBuilder(length() + (replace.length() - find.length()) * iMax);
        int i = 0;
        int lastPos = 0;
        while (i < iMax) {
            int pos = indices[i++];
            if (lastPos < pos) appendTo(sb, lastPos, pos);
            lastPos = pos + find.length();
            sb.append(replace);
        }

        if (lastPos < length()) {
            appendTo(sb, lastPos, length());
        }

        return sequenceOf(sb);
    }

    @Override
    final public T append(CharSequence... others) {
        if (others.length > 0) {
            int total = 0;
            for (CharSequence other : others) total += other.length();
            StringBuilder sb = new StringBuilder(length() + total);
            appendTo(sb);
            for (CharSequence other : others) {
                sb.append(other);
            }
            return sequenceOf(sb);
        }
        return (T) this;
    }

    @Override
    final public int getColumnAtIndex(int index) {
        int lineStart = lastIndexOfAny(EOL_CHARS, index);
        return index - (lineStart == -1 ? 0 : lineStart + eolLength(lineStart));
    }

    @Override
    final public Pair<Integer, Integer> getLineColumnAtIndex(int index) {
        int iMax = length();
        if (index < 0 || index > iMax) {
            throw new IllegalArgumentException("Index: " + index + " out of range [0, " + iMax + "]");
        }

        boolean hadCr = false;
        boolean hadEOL = true;
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
    public boolean equals(Object other) {
        return (this == other) || other instanceof CharSequence && ((CharSequence) other).length() == length() && matchChars((CharSequence) other, 0, false);
    }

    @Override
    public boolean equalsIgnoreCase(CharSequence other) {
        return (this == other) || (other != null) && other.length() == length() && matchChars(other, 0, true);
    }

    @Override
    public boolean equals(Object other, boolean ignoreCase) {
        return (this == other) || other instanceof CharSequence && ((CharSequence) other).length() == length() && matchChars((CharSequence) other, 0, ignoreCase);
    }

    @Override
    public int compareTo(CharSequence other) {
        int len1 = length();
        int len2 = other.length();
        int iMax = len1 <= len2 ? len1 : len2;
        for (int i = 0; i < iMax; i++) {
            char c1 = charAt(i);
            char c2 = other.charAt(i);
            if (c1 != c2) {
                return c1 - c2;
            }
        }
        return len1 - len2;
    }
}
