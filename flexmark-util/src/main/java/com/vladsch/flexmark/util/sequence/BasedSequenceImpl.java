package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.mappers.CharMapper;
import com.vladsch.flexmark.util.mappers.LowerCaseMapper;
import com.vladsch.flexmark.util.mappers.UpperCaseMapper;

import java.util.*;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public abstract class BasedSequenceImpl implements BasedSequence {
    private static final Map<Character, String> visibleSpacesMap;
    static {
        HashMap<Character, String> charMap = new HashMap<>();
        visibleSpacesMap = charMap;
        charMap.put('\n', "\\n");
        charMap.put('\r', "\\r");
        charMap.put('\f', "\\f");
        charMap.put('\t', "\\u2192");
    }
    public static BasedSequence firstNonNull(BasedSequence... sequences) {
        for (BasedSequence sequence : sequences) {
            if (sequence != null && sequence != NULL) return sequence;
        }

        return NULL;
    }

    static boolean isVisibleWhitespace(char c) {
        return visibleSpacesMap.containsKey(c);
    }

    @Override
    public BasedSequence endSequence(int start, int end) {
        int length = length();
        int useStart = length - start;
        int useEnd = length - end;

        if (useStart < 0) useStart = 0;
        else if (useStart > length) useStart = length;
        if (useEnd < 0) useEnd = 0;
        else if (useEnd > length) useEnd = length;

        if (useStart > useEnd) useStart = useEnd;

        if (useStart == 0 && useEnd == length) {
            return this;
        } else {
            return subSequence(useStart, useEnd);
        }
    }

    @Override
    public BasedSequence endSequence(int start) {
        int length = length();
        if (start <= 0) {
            return subSequence(length, length);
        } else if (start >= length) {
            return this;
        } else {
            return subSequence(length - start, length);
        }
    }

    @Override
    public char endCharAt(int index) {
        if (index < 0 || index >= length()) return '\0';
        return charAt(length() - index);
    }

    @Override
    public BasedSequence midSequence(int start, int end) {
        int length = length();
        int useStart = start < 0 ? length + start : start;
        int useEnd = end < 0 ? length + end : end;

        if (useStart < 0) useStart = 0;
        else if (useStart > length) useStart = length;
        if (useEnd < 0) useEnd = 0;
        else if (useEnd > length) useEnd = length;

        if (useStart > useEnd) useStart = useEnd;
        if (useStart == 0 && useEnd == length) {
            return this;
        } else {
            return subSequence(useStart, useEnd);
        }
    }

    @Override
    public BasedSequence midSequence(int start) {
        int length = length();
        int useStart = start < 0 ? length + start : start;
        if (useStart <= 0) {
            return this;
        } else if (useStart >= length) {
            return subSequence(length, length);
        } else {
            return subSequence(useStart, length);
        }
    }

    @Override
    public char midCharAt(int index) {
        if (index < -length() || index >= length()) return '\0';
        return charAt(index < 0 ? length() + index : index);
    }

    /**
     * @return the last character of the sequence or '\0' if empty
     */
    @Override
    public char lastChar() {
        return isEmpty() ? '\0' : charAt(length() - 1);
    }

    /**
     * @return the first character of the sequence or '\0' if empty
     */
    @Override
    public char firstChar() {
        return isEmpty() ? '\0' : charAt(0);
    }

    // @formatter:off
    @Override public int indexOf(CharSequence s) { return indexOf(s, 0, length()); }
    @Override public int indexOf(CharSequence s, int fromIndex) { return indexOf(s, fromIndex, length()); }
    // @formatter:on

    @Override
    public int indexOf(CharSequence s, int fromIndex, int endIndex) {
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
    @Override public int indexOf(char c) { return indexOf(c, 0, length()); }
    @Override public int indexOfAny(char c1, char c2) { return indexOfAny(c1, c2, 0, length()); }
    @Override public int indexOfAny(char c1, char c2, char c3) { return indexOfAny(c1, c2, c3, 0, length()); }
    @Override public int indexOfAny(CharSequence s) { return indexOfAny(s, 0, length()); }
    @Override public int indexOf(char c, int fromIndex) { return indexOf(c, fromIndex, length()); }
    @Override public int indexOfAny(char c1, char c2, int fromIndex) { return indexOfAny(c1, c2, fromIndex, length()); }
    @Override public int indexOfAny(char c1, char c2, char c3, int fromIndex) { return indexOfAny(c1, c2, c3, fromIndex, length()); }
    @Override public int indexOfAny(CharSequence s, int index) { return indexOfAny(s, index, length()); }
    // @formatter:on

    @Override
    public int indexOf(char c, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            if (charAt(i) == c) return i;
        }
        return -1;
    }

    @Override
    public int indexOfAny(char c1, char c2, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c == c1 || c == c2) return i;
        }
        return -1;
    }

    @Override
    public int indexOfAny(char c1, char c2, char c3, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c == c1 || c == c2 || c == c3) return i;
        }
        return -1;
    }

    @Override
    public int indexOfAny(CharSequence s, int fromIndex, int endIndex) {
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
                BasedSequence sequence = of(s);
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
    @Override public int indexOfNot(char c) { return indexOfNot(c, 0, length()); }
    @Override public int indexOfAnyNot(char c1, char c2) { return indexOfAnyNot(c1, c2, 0, length()); }
    @Override public int indexOfAnyNot(char c1, char c2, char c3) { return indexOfAnyNot(c1, c2, c3, 0, length()); }
    @Override public int indexOfAnyNot(CharSequence s) { return indexOfAnyNot(s, 0, length()); }
    @Override public int indexOfNot(char c, int fromIndex) { return indexOfNot(c, fromIndex, length()); }
    @Override public int indexOfAnyNot(char c1, char c2, int fromIndex) { return indexOfAnyNot(c1, c2, fromIndex, length()); }
    @Override public int indexOfAnyNot(char c1, char c2, char c3, int fromIndex) { return indexOfAnyNot(c1, c2, c3, fromIndex, length()); }
    @Override public int indexOfAnyNot(CharSequence s, int fromIndex) { return indexOfAnyNot(s, fromIndex, length()); }
    // @formatter:on

    @Override
    public int indexOfNot(char c, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            if (charAt(i) != c) return i;
        }
        return -1;
    }

    @Override
    public int indexOfAnyNot(char c1, char c2, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c != c1 && c != c2) return i;
        }
        return -1;
    }

    @Override
    public int indexOfAnyNot(char c1, char c2, char c3, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = fromIndex; i < endIndex; i++) {
            char c = charAt(i);
            if (c != c1 && c != c2 && c != c3) return i;
        }
        return -1;
    }

    @Override
    public int indexOfAnyNot(CharSequence s, int fromIndex, int endIndex) {
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
                BasedSequence sequence = of(s);
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
    @Override public int lastIndexOf(CharSequence s) { return lastIndexOf(s, 0, length()); }
    @Override public int lastIndexOf(CharSequence s, int fromIndex) { return lastIndexOf(s, 0, fromIndex); }
    // @formatter:on

    @Override
    public int lastIndexOf(CharSequence s, int startIndex, int fromIndex) {
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
    @Override public int lastIndexOf(char c)                                        { return lastIndexOf(c, 0, length()); }
    @Override public int lastIndexOfAny(char c1, char c2)                           { return lastIndexOfAny(c1, c2, 0, length()); }
    @Override public int lastIndexOfAny(char c1, char c2, char c3)                  { return lastIndexOfAny(c1, c2, c3, 0, length()); }
    @Override public int lastIndexOfAny(CharSequence s)                             { return indexOfAny(s, 0, length()); }
    @Override public int lastIndexOf(char c, int fromIndex)                        { return lastIndexOf(c, 0, fromIndex); }
    @Override public int lastIndexOfAny(char c1, char c2, int fromIndex)           { return lastIndexOfAny(c1, c2, 0, fromIndex); }
    @Override public int lastIndexOfAny(char c1, char c2, char c3, int fromIndex)  { return lastIndexOfAny(c1, c2, c3, 0, fromIndex); }
    @Override public int lastIndexOfAny(CharSequence s, int fromIndex)             { return lastIndexOfAny(s, 0, fromIndex); }
    // @formatter:on

    @Override
    public int lastIndexOf(char c, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            if (charAt(i) == c) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOfAny(char c1, char c2, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            final char c = charAt(i);
            if (c == c1 || c == c2) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOfAny(char c1, char c2, char c3, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            final char c = charAt(i);
            if (c == c1 || c == c2 || c == c3) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOfAny(CharSequence s, int startIndex, int fromIndex) {
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
                BasedSequence sequence = of(s);
                if (startIndex < 0) startIndex = 0;
                if (fromIndex >= length()) fromIndex = length();
                else fromIndex++;
                for (int i = fromIndex; i-- > startIndex; ) {
                    final char c = charAt(i);
                    if (sequence.indexOf(c) != -1) return i;
                }
        }
        return -1;
    }

    // @formatter:off
    @Override public int lastIndexOfNot(char c)                                         { return lastIndexOfNot(c, 0, length()); }
    @Override public int lastIndexOfAnyNot(char c1, char c2)                            { return lastIndexOfAnyNot(c1, c2, 0, length()); }
    @Override public int lastIndexOfAnyNot(char c1, char c2, char c3)                   { return lastIndexOfAnyNot(c1, c2, c3, 0, length()); }
    @Override public int lastIndexOfAnyNot(CharSequence s)                              { return indexOfAnyNot(s, 0, length()); }
    @Override public int lastIndexOfNot(char c, int fromIndex)                         { return lastIndexOfNot(c, 0, fromIndex); }
    @Override public int lastIndexOfAnyNot(char c1, char c2, int fromIndex)            { return lastIndexOfAnyNot(c1, c2, 0, fromIndex); }
    @Override public int lastIndexOfAnyNot(char c1, char c2, char c3, int fromIndex)   { return lastIndexOfAnyNot(c1, c2, c3, 0, fromIndex); }
    @Override public int lastIndexOfAnyNot(CharSequence s, int fromIndex)              { return lastIndexOfAnyNot(s, 0, fromIndex); }
    // @formatter:on

    @Override
    public int lastIndexOfNot(char c, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            if (charAt(i) != c) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOfAnyNot(char c1, char c2, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            final char c = charAt(i);
            if (c != c1 && c != c2) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOfAnyNot(char c1, char c2, char c3, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        for (int i = fromIndex; i-- > startIndex; ) {
            final char c = charAt(i);
            if (c != c1 && c != c2 && c != c3) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOfAnyNot(CharSequence s, int startIndex, int fromIndex) {
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
                BasedSequence sequence = of(s);
                if (startIndex < 0) startIndex = 0;
                if (fromIndex >= length()) fromIndex = length();
                else fromIndex++;
                for (int i = fromIndex; i-- > startIndex; ) {
                    final char c = charAt(i);
                    if (sequence.indexOf(c) == -1) return i;
                }
        }
        return -1;
    }

    @Override
    public int startOfDelimitedBy(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = lastIndexOf(s, index - 1);
        return offset == -1 ? 0 : offset + 1;
    }

    @Override
    public int startOfDelimitedByAny(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = lastIndexOfAny(s, index - 1);
        return offset == -1 ? 0 : offset + 1;
    }

    @Override
    public int startOfDelimitedByAnyNot(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = lastIndexOfAnyNot(s, index - 1);
        return offset == -1 ? 0 : offset + 1;
    }

    @Override
    public int endOfDelimitedBy(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = indexOf(s, index);
        return offset == -1 ? length() : offset;
    }

    @Override
    public int endOfDelimitedByAny(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = indexOfAny(s, index);
        return offset == -1 ? length() : offset;
    }

    @Override
    public int endOfDelimitedByAnyNot(CharSequence s, int index) {
        if (index < 0) index = 0;
        else if (index > length()) index = length();
        int offset = indexOfAnyNot(s, index);
        return offset == -1 ? length() : offset;
    }

    @Override
    public int endOfLine(int index) {
        return endOfDelimitedBy(EOL, index);
    }

    @Override
    public int endOfLineAnyEOL(int index) {
        return endOfDelimitedByAny(EOL_CHARS, index);
    }

    @Override
    public int startOfLine(int index) {
        return startOfDelimitedBy(EOL, index);
    }

    @Override
    public int startOfLineAnyEOL(int index) {
        return startOfDelimitedByAny(EOL_CHARS, index);
    }

    // @formatter:off
    @Override public int countLeading(char c)                                   { return countChars(c, 0, length()); }
    @Override public int countLeadingNot(char c)                                { return countNotChars(c, 0, length()); }
    @Override public int countLeading(char c, int fromIndex)                   { return countChars(c, fromIndex, length()); }
    @Override public int countLeadingNot(char c, int fromIndex)                { return countNotChars(c, fromIndex, length()); }
    @Override public int countLeading(char c, int fromIndex, int endIndex)     { return countChars(c, fromIndex, endIndex); }
    @Override public int countLeadingNot(char c, int fromIndex, int endIndex)  { return countNotChars(c, fromIndex, endIndex); }

    @Override public int countTrailing(char c)                                  { return countCharsReversed(c, 0, length()); }
    @Override public int countTrailingNot(char c)                               { return countNotCharsReversed(c, 0, length()); }
    @Override public int countTrailing(char c, int fromIndex)                  { return countCharsReversed(c, 0, fromIndex); }
    @Override public int countTrailingNot(char c, int fromIndex)               { return countNotCharsReversed(c, 0, fromIndex); }
    @Override public int countTrailing(char c, int startIndex, int fromIndex)    { return countCharsReversed(c, startIndex, fromIndex); }
    @Override public int countTrailingNot(char c, int startIndex, int fromIndex) { return countNotCharsReversed(c, startIndex, fromIndex); }

    @Override public int countChars(char c)                                     { return countChars(c, 0, length()); }
    @Override public int countNotChars(char c)                                  { return countNotChars(c, 0, length()); }
    @Override public int countChars(char c, int fromIndex)                     { return countChars(c, fromIndex, length()); }
    @Override public int countNotChars(char c, int fromIndex)                  { return countNotChars(c, fromIndex, length()); }

    @Override public int countCharsReversed(char c)                             { return countCharsReversed(c, 0, length()); }
    @Override public int countNotCharsReversed(char c)                          { return countNotCharsReversed(c, 0, length()); }
    @Override public int countCharsReversed(char c, int fromIndex)             { return countCharsReversed(c, 0, fromIndex); }
    @Override public int countNotCharsReversed(char c, int fromIndex)          { return countNotCharsReversed(c, 0, fromIndex); }
    // @formatter:on

    @Override
    public int countChars(char c, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        int index = indexOfNot(c, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    public int countCharsReversed(char c, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        int index = lastIndexOfNot(c, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex - index - 1;
    }

    @Override
    public int countNotChars(char c, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        int index = indexOf(c, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    public int countNotCharsReversed(char c, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        int index = lastIndexOf(c, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex - index - 1;
    }

    // @formatter:off
    @Override public int countLeading(CharSequence chars)                                   { return countChars(chars, 0, length()); }
    @Override public int countLeadingNot(CharSequence chars)                                { return countNotChars(chars, 0, length()); }
    @Override public int countLeading(CharSequence chars, int fromIndex)                   { return countChars(chars, fromIndex, length()); }
    @Override public int countLeadingNot(CharSequence chars, int fromIndex)                { return countNotChars(chars, fromIndex, length()); }
    @Override public int countLeading(CharSequence chars, int fromIndex, int endIndex)     { return countChars(chars, fromIndex, endIndex); }
    @Override public int countLeadingNot(CharSequence chars, int fromIndex, int endIndex)  { return countNotChars(chars, fromIndex, endIndex); }

    @Override public int countTrailing(CharSequence chars)                                  { return countCharsReversed(chars, 0, length()); }
    @Override public int countTrailingNot(CharSequence chars)                               { return countNotCharsReversed(chars, 0, length()); }
    @Override public int countTrailing(CharSequence chars, int fromIndex)                  { return countCharsReversed(chars, 0, fromIndex); }
    @Override public int countTrailingNot(CharSequence chars, int fromIndex)               { return countNotCharsReversed(chars, 0, fromIndex); }
    @Override public int countTrailing(CharSequence chars, int startIndex, int fromIndex)    { return countCharsReversed(chars, startIndex, fromIndex); }
    @Override public int countTrailingNot(CharSequence chars, int startIndex, int fromIndex) { return countNotCharsReversed(chars, startIndex, fromIndex); }

    @Override public int countChars(CharSequence chars)                                     { return countChars(chars, 0, length()); }
    @Override public int countNotChars(CharSequence chars)                                  { return countNotChars(chars, 0, length()); }
    @Override public int countChars(CharSequence chars, int fromIndex)                     { return countChars(chars, fromIndex, length()); }
    @Override public int countNotChars(CharSequence chars, int fromIndex)                  { return countNotChars(chars, fromIndex, length()); }

    @Override public int countCharsReversed(CharSequence chars)                             { return countCharsReversed(chars, 0, length()); }
    @Override public int countNotCharsReversed(CharSequence chars)                          { return countNotCharsReversed(chars, 0, length()); }
    @Override public int countCharsReversed(CharSequence chars, int fromIndex)             { return countCharsReversed(chars, 0, fromIndex); }
    @Override public int countNotCharsReversed(CharSequence chars, int fromIndex)          { return countNotCharsReversed(chars, 0, fromIndex); }
    // @formatter:on

    @Override
    public int countChars(CharSequence chars, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        int index = indexOfAnyNot(chars, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    public int countCharsReversed(CharSequence chars, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        int index = lastIndexOfAnyNot(chars, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex - index - 1;
    }

    @Override
    public int countNotChars(CharSequence chars, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        int index = indexOfAny(chars, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    public int countNotCharsReversed(CharSequence chars, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        int index = lastIndexOfAny(chars, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex - index - 1;
    }

    @Override
    public BasedSequence trimStart(CharSequence chars) {
        int trim = countChars(chars, 0, length());
        return trim > 0 ? subSequence(trim, length()) : this;
    }

    @Override
    public BasedSequence trimmedStart(CharSequence chars) {
        int trim = countChars(chars, 0, length());
        return trim > 0 ? subSequence(0, trim) : BasedSequence.NULL;
    }

    @Override
    public BasedSequence trimEnd(CharSequence chars) {
        int trim = countCharsReversed(chars, 0, length());
        return trim > 0 ? subSequence(0, length() - trim) : this;
    }

    @Override
    public BasedSequence trimmedEnd(CharSequence chars) {
        int trim = countCharsReversed(chars, 0, length());
        return trim > 0 ? subSequence(length() - trim) : BasedSequence.NULL;
    }

    @Override
    public BasedSequence trim(CharSequence chars) {
        int trimStart = countChars(chars, 0, length());
        int trimEnd = countCharsReversed(chars, 0, length());
        int trimmed = trimStart + trimEnd;
        return trimmed > 0 ? (trimmed >= length() ? subSequence(0, 0) : subSequence(trimStart, length() - trimEnd)) : this;
    }

    @Override
    public BasedSequence trimStart() {
        int trim = countChars(WHITESPACE_CHARS, 0, length());
        return trim > 0 ? subSequence(trim, length()) : this;
    }

    @Override
    public BasedSequence trimmedStart() {
        int trim = countChars(WHITESPACE_CHARS, 0, length());
        return trim > 0 ? subSequence(0, trim) : BasedSequence.NULL;
    }

    @Override
    public BasedSequence trimEnd() {
        int trim = countCharsReversed(WHITESPACE_CHARS, 0, length());
        return trim > 0 ? subSequence(0, length() - trim) : this;
    }

    @Override
    public BasedSequence trimmedEnd() {
        int trim = countCharsReversed(WHITESPACE_CHARS, 0, length());
        return trim > 0 ? subSequence(length() - trim) : BasedSequence.NULL;
    }

    @Override
    public int eolLength() {
        return countCharsReversed(EOL_CHARS, 0, length());
    }

    @Override
    public BasedSequence trimEOL() {
        int trim = eolLength();
        return trim > 0 ? subSequence(0, length() - trim) : this;
    }

    @Override
    public BasedSequence trimmedEOL() {
        int trim = eolLength();
        return trim > 0 ? subSequence(length() - trim) : BasedSequence.NULL;
    }

    @Override
    public BasedSequence trim() {
        int trimStart = countChars(WHITESPACE_CHARS, 0, length());
        if (trimStart == length()) {
            return subSequence(trimStart, trimStart);
        }

        int trimEnd = countCharsReversed(WHITESPACE_CHARS, 0, length());
        return trimStart > 0 || trimEnd > 0 ? subSequence(trimStart, length() - trimEnd) : this;
    }

    @Override
    public BasedSequence ifNullEmptyAfter(BasedSequence other) {
        return isNull() ? other.subSequence(other.length(), other.length()) : this;
    }

    @Override
    public BasedSequence ifNullEmptyBefore(BasedSequence other) {
        return isNull() ? other.subSequence(0, 0) : this;
    }

    @Override
    public BasedSequence nullIfEmpty() {
        return isEmpty() ? BasedSequence.NULL : this;
    }

    @Override
    public BasedSequence nullIfBlank() {
        return isBlank() ? BasedSequence.NULL : this;
    }

    @Override
    public BasedSequence nullIf(boolean condition) {
        return condition ? BasedSequence.NULL : this;
    }

    @Override
    public boolean isEmpty() {
        return length() == 0;
    }

    @Override
    public boolean isBlank() {
        int trim = countChars(WHITESPACE_CHARS, 0, length());
        return trim == length();
    }

    @Override
    public boolean isNull() {
        return this == BasedSequence.NULL;
    }

    @Override
    public boolean isNotNull() {
        return this != BasedSequence.NULL;
    }

    @Override
    public boolean endsWith(CharSequence suffix) {
        return length() > 0 && matchCharsReversed(suffix, length() - 1);
    }

    @Override
    public boolean startsWith(CharSequence prefix) {
        return length() > 0 && matchChars(prefix, 0);
    }

    @Override
    public BasedSequence removeSuffix(CharSequence suffix) {
        return !endsWith(suffix) ? this : subSequence(0, length() - suffix.length());
    }

    @Override
    public BasedSequence removePrefix(CharSequence prefix) {
        return !startsWith(prefix) ? this : subSequence(prefix.length(), length());
    }

    @Override
    public BasedSequence removeProperSuffix(CharSequence suffix) {
        return length() <= suffix.length() || !endsWith(suffix) ? this : subSequence(0, length() - suffix.length());
    }

    @Override
    public BasedSequence removeProperPrefix(CharSequence prefix) {
        return length() <= prefix.length() || !startsWith(prefix) ? this : subSequence(prefix.length(), length());
    }

    @Override
    public MappedSequence toLowerCase() {
        return toMapped(LowerCaseMapper.INSTANCE);
    }

    @Override
    public MappedSequence toUpperCase() {
        return toMapped(UpperCaseMapper.INSTANCE);
    }

    @Override
    public MappedSequence toLowerCase(Locale locale) {
        return toMapped(new LowerCaseMapper(locale));
    }

    @Override
    public MappedSequence toUpperCase(Locale locale) {
        return toMapped(new UpperCaseMapper(locale));
    }

    @Override
    public final MappedSequence toMapped(CharMapper mapper) {
        return MappedSequence.of(mapper, this);
    }

    @Override
    public boolean matches(CharSequence chars) {
        return chars.length() == length() && matchChars(chars);
    }

    @Override
    public boolean matchChars(CharSequence chars) {
        return matchChars(chars, 0);
    }

    @Override
    public boolean matchChars(CharSequence chars, int startIndex) {
        int iMax = chars.length();
        if (iMax > length() - startIndex) return false;

        for (int i = 0; i < iMax; i++) {
            if (chars.charAt(i) != charAt(i + startIndex)) return false;
        }
        return true;
    }

    @Override
    public boolean matchCharsReversed(CharSequence chars, int endIndex) {
        return endIndex + 1 >= chars.length() && matchChars(chars, endIndex + 1 - chars.length());
    }

    @Override
    public BasedSequence subSequence(Range range) {
        return subSequence(range.getStart(), range.getEnd());
    }

    @Override
    public BasedSequence subSequence(int start) {
        return subSequence(start, length());
    }

    @Override
    public BasedSequence trimTailBlankLines() {
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
        return this;
    }

    @Override
    public BasedSequence trimLeadBlankLines() {
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
        return this;
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
    public String unescape() {
        return Escaping.unescapeString(toString());
    }

    @Override
    public BasedSequence unescape(ReplacedTextMapper textMapper) {
        return Escaping.unescape(this, textMapper);
    }

    @Override
    public String normalizeEOL() {
        return Escaping.normalizeEOL(toString());
    }

    @Override
    public BasedSequence normalizeEOL(ReplacedTextMapper textMapper) {
        return Escaping.normalizeEOL(this, textMapper);
    }

    @Override
    public String normalizeEndWithEOL() {
        return Escaping.normalizeEndWithEOL(toString());
    }

    @Override
    public BasedSequence normalizeEndWithEOL(ReplacedTextMapper textMapper) {
        return Escaping.normalizeEndWithEOL(this, textMapper);
    }

    @Override
    public String toVisibleWhitespaceString() {
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
    public boolean isContinuedBy(BasedSequence other) {
        return other.length() > 0 && length() > 0 && other.getBase() == getBase() && other.getStartOffset() == getEndOffset();
    }

    @Override
    public boolean isContinuationOf(BasedSequence other) {
        return other.length() > 0 && length() > 0 && other.getBase() == getBase() && other.getEndOffset() == getStartOffset();
    }

    @Override
    public BasedSequence spliceAtEnd(BasedSequence other) {
        assert isContinuedBy(other) : "sequence[" + getStartOffset() + ", " + getEndOffset() + "] is not continued by other[" + other.getStartOffset() + ", " + other.getEndOffset() + "]";
        return baseSubSequence(getStartOffset(), other.getEndOffset());
    }

    @Override
    public boolean contains(BasedSequence other) {
        return getBase() == other.getBase() && !(getStartOffset() >= other.getEndOffset() || getEndOffset() <= other.getStartOffset());
    }

    @Override
    public List<BasedSequence> split(char delimiter) {
        return split(delimiter, 0);
    }

    @Override
    public List<BasedSequence> split(char delimiter, int limit) {
        return split(delimiter, limit, 0);
    }

    @Override
    public List<BasedSequence> split(char delimiter, int limit, int flags) {
        return split(delimiter, limit, flags, WHITESPACE_CHARS);
    }

    @Override
    public List<BasedSequence> split(CharSequence delimiter) {
        return split(delimiter, 0);
    }

    @Override
    public List<BasedSequence> split(CharSequence delimiter, int limit) {
        return split(delimiter, limit, 0);
    }

    @Override
    public List<BasedSequence> split(CharSequence delimiter, int limit, int flags) {
        return split(delimiter, limit, flags, WHITESPACE_CHARS);
    }

    @Override
    public List<BasedSequence> split(char delimiter, int limit, int flags, String trimChars) {
        if (trimChars == null) trimChars = WHITESPACE_CHARS;
        if (limit < 1) limit = Integer.MAX_VALUE;

        boolean includeDelimiterParts = (flags & SPLIT_INCLUDE_DELIM_PARTS) != 0;
        int includeDelimiter = !includeDelimiterParts && (flags & SPLIT_INCLUDE_DELIMS) != 0 ? 1 : 0;
        boolean trimParts = (flags & SPLIT_TRIM_PARTS) != 0;
        boolean skipEmpty = (flags & SPLIT_SKIP_EMPTY) != 0;
        ArrayList<BasedSequence> items = new ArrayList<>();

        int lastPos = 0;
        int length = length();
        if (limit > 1) {
            while (lastPos < length) {
                int pos = indexOf(delimiter, lastPos);
                if (pos < 0) break;

                if (lastPos < pos || !skipEmpty) {
                    BasedSequence item = subSequence(lastPos, pos + includeDelimiter);
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
            BasedSequence item = subSequence(lastPos, length);
            if (trimParts) item = item.trim(trimChars);
            if (!item.isEmpty() || !skipEmpty) {
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public List<BasedSequence> split(CharSequence delimiter, int limit, int flags, String trimChars) {
        if (trimChars == null) trimChars = WHITESPACE_CHARS;
        if (limit < 1) limit = Integer.MAX_VALUE;

        boolean includeDelimiterParts = (flags & SPLIT_INCLUDE_DELIM_PARTS) != 0;
        int includeDelimiter = !includeDelimiterParts && (flags & SPLIT_INCLUDE_DELIMS) != 0 ? delimiter.length() : 0;
        boolean trimParts = (flags & SPLIT_TRIM_PARTS) != 0;
        boolean skipEmpty = (flags & SPLIT_SKIP_EMPTY) != 0;
        ArrayList<BasedSequence> items = new ArrayList<>();

        int lastPos = 0;
        int length = length();
        if (limit > 1) {
            while (lastPos < length) {
                int pos = indexOf(delimiter, lastPos);
                if (pos < 0) break;

                if (lastPos < pos || !skipEmpty) {
                    BasedSequence item = subSequence(lastPos, pos + includeDelimiter);
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
            BasedSequence item = subSequence(lastPos, length);
            if (trimParts) item = item.trim(trimChars);
            if (!item.isEmpty() || !skipEmpty) {
                items.add(item);
            }
        }
        return items;
    }

    public static BasedSequence of(CharSequence charSequence) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence);
        else if (charSequence instanceof String) return CharSubSequence.of(charSequence);
        else return SubSequence.of(charSequence);
    }

    public static BasedSequence of(CharSequence charSequence, int start) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence).subSequence(start);
        else if (charSequence instanceof String) return CharSubSequence.of(charSequence, start);
        else return SubSequence.of(charSequence, start);
    }

    public static BasedSequence of(CharSequence charSequence, int start, int end) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence).subSequence(start, end);
        else if (charSequence instanceof String) return CharSubSequence.of(charSequence, start, end);
        else return SubSequence.of(charSequence, start, end);
    }

}
