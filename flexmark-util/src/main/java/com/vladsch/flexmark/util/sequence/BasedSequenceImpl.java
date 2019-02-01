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
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public abstract class BasedSequenceImpl implements BasedSequence {
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
    public Range getIndexRange(final int startOffset, final int endOffset) {
        // we assume that start/end is within our range
        int baseOffset = getStartOffset();
        if (startOffset > getEndOffset() || endOffset < baseOffset) {
            throw new IllegalArgumentException("getIndexRange(" + startOffset + ", " + endOffset + ") not in range [" + baseOffset + ", " + getEndOffset() + "]");
        }
        return Range.of(startOffset - baseOffset, endOffset - baseOffset);
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
    @Override public int lastIndexOfAny(CharSequence s)                             { return lastIndexOfAny(s, 0, length()); }
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
    @Override public int lastIndexOfAnyNot(CharSequence s)                              { return lastIndexOfAnyNot(s, 0, length()); }
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

    // @formatter:off
    @Override public int countOf(char c)                                        { return countOf(c, 0, length()); }
    @Override public int countOfNot(char c)                                     { return countOfNot(c, 0, length()); }
    @Override public int countOf(char c, int fromIndex)                         { return countOf(c, fromIndex, length()); }
    @Override public int countOfNot(char c, int fromIndex)                      { return countOfNot(c, fromIndex, length()); }
    
    @Override public int countOf()                                              { return countOfAny(BasedSequence.WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override public int countOfNot()                                           { return countOfAnyNot(BasedSequence.WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override public int countOfAny(CharSequence chars)                         { return countOfAny(chars, 0, length()); }
    @Override public int countOfAnyNot(CharSequence chars)                      { return countOfAnyNot(chars, 0, length()); }
    @Override public int countOfAny(CharSequence chars, int fromIndex)          { return countOfAny(chars, fromIndex, length()); }
    @Override public int countOfAnyNot(CharSequence chars, int fromIndex)       { return countOfAnyNot(chars, fromIndex, length()); }
    // @formatter:on

    @Override
    public int countOf(char c1, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        int count = 0;
        for (int i = fromIndex; i-- > startIndex; ) {
            final char c = charAt(i);
            if (c == c1) count++;
        }
        return count;
    }

    @Override
    public int countOfAny(CharSequence s, int fromIndex, int endIndex) {
        BasedSequence sequence = of(s);
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
    public int countOfNot(char c1, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex >= length()) fromIndex = length();
        else fromIndex++;
        int count = 0;
        for (int i = fromIndex; i-- > startIndex; ) {
            final char c = charAt(i);
            if (c != c1) count++;
        }
        return count;
    }

    @Override
    public int countOfAnyNot(CharSequence s, int fromIndex, int endIndex) {
        BasedSequence sequence = of(s);
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

    @Override
    public BasedSequence lineAt(final int index) {
        return subSequence(startOfLine(index), endOfLine(index));
    }

    @Override
    public BasedSequence lineAtAnyEOL(final int index) {
        return subSequence(startOfLineAnyEOL(index), endOfLineAnyEOL(index));
    }

    // @formatter:off
    // these are duplicates and will be removed soon
    // countChars -> countLeading
    // countNotChars -> countLeadingNot
    // countCharsReversed -> countTrailing
    // countNotCharsReversed -> countTrailingNot
    @Deprecated public int countChars(char c)                                                               { return countLeading(c); }   
    @Deprecated public int countCharsReversed(char c)                                                       { return countTrailing(c); }   
    @Deprecated public int countNotChars(char c)                                                            { return countLeadingNot(c); }    
    @Deprecated public int countNotCharsReversed(char c)                                                    { return countTrailingNot(c); }    

    @Deprecated public int countChars(char c, int startIndex)                                               { return countLeading(c, startIndex); }   
    @Deprecated public int countCharsReversed(char c, int startIndex)                                       { return countTrailing(c, startIndex); }   
    @Deprecated public int countNotChars(char c, int startIndex)                                            { return countLeadingNot(c, startIndex); }    
    @Deprecated public int countNotCharsReversed(char c, int startIndex)                                    { return countTrailingNot(c, startIndex); }    

    @Deprecated public int countChars(char c, int startIndex, int endIndex)                                 { return countLeading(c, startIndex, endIndex); }     
    @Deprecated public int countNotChars(char c, int startIndex, int endIndex)                              { return countTrailingNot(c, startIndex, endIndex); }  
    @Deprecated public int countCharsReversed(char c, int startIndex, int endIndex)                         { return countTrailing(c, startIndex, endIndex); }     
    @Deprecated public int countNotCharsReversed(char c, int startIndex, int endIndex)                      { return countTrailingNot(c, startIndex, endIndex); }  

    @Deprecated public int countChars(CharSequence chars)                                                   { return countLeading( chars); }
    @Deprecated public int countCharsReversed(CharSequence chars)                                           { return countTrailing( chars); }
    @Deprecated public int countNotChars(CharSequence chars)                                                { return countLeadingNot( chars); }
    @Deprecated public int countNotCharsReversed(CharSequence chars)                                        { return countTrailingNot( chars); }
    @Deprecated public int countChars(CharSequence chars, int startIndex)                                   { return countLeading(chars, startIndex); }
    @Deprecated public int countCharsReversed(CharSequence chars, int startIndex)                           { return countTrailing(chars, startIndex); }
    @Deprecated public int countNotChars(CharSequence chars, int startIndex)                                { return countLeadingNot(chars, startIndex); }
    @Deprecated public int countNotCharsReversed(CharSequence chars, int startIndex)                        { return countTrailingNot(chars, startIndex); }

    @Deprecated public int countChars(CharSequence chars, int startIndex, int endIndex)                     { return countLeading(chars, startIndex, endIndex); }     
    @Deprecated public int countNotChars(CharSequence chars, int startIndex, int endIndex)                  { return countTrailingNot(chars, startIndex, endIndex); }  
    @Deprecated public int countCharsReversed(CharSequence chars, int startIndex, int endIndex)             { return countTrailing(chars, startIndex, endIndex); }     
    @Deprecated public int countNotCharsReversed(CharSequence chars, int startIndex, int endIndex)          { return countTrailingNot(chars, startIndex, endIndex); }  
    // @formatter:on

    // @formatter:off
    @Override public int countLeading(char c)                                       { return this.countLeading(c, 0, length()); }
    @Override public int countLeadingNot(char c)                                    { return this.countLeadingNot(c, 0, length()); }
    @Override public int countLeading(char c, int fromIndex)                        { return this.countLeading(c, fromIndex, length()); }
    @Override public int countLeadingNot(char c, int fromIndex)                     { return this.countLeadingNot(c, fromIndex, length()); }

    @Override public int countTrailing(char c)                                      { return this.countTrailing(c, 0, length()); }
    @Override public int countTrailingNot(char c)                                   { return this.countTrailingNot(c, 0, length()); }
    @Override public int countTrailing(char c, int fromIndex)                       { return this.countTrailing(c, 0, fromIndex); }
    @Override public int countTrailingNot(char c, int fromIndex)                    { return this.countTrailingNot(c, 0, fromIndex); }

    // @formatter:on

    @Override
    public int countLeading(char c, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        if (fromIndex > endIndex) fromIndex = endIndex;
        int index = indexOfNot(c, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    public int countTrailing(char c, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        if (startIndex > fromIndex) startIndex = fromIndex;
        int index = lastIndexOfNot(c, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex - index - 1;
    }

    @Override
    public int countLeadingNot(char c, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        if (fromIndex > endIndex) fromIndex = endIndex;
        int index = indexOf(c, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    public int countTrailingNot(char c, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        if (startIndex > fromIndex) startIndex = fromIndex;
        int index = lastIndexOf(c, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex - index - 1;
    }

    // @formatter:off
    @Override public int countLeading()                                          { return this.countLeading(BasedSequence.WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override public int countLeadingNot()                                       { return this.countLeadingNot(BasedSequence.WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override public int countTrailing()                                         { return this.countTrailing(BasedSequence.WHITESPACE_NO_EOL_CHARS, 0, length()); }
    @Override public int countTrailingNot()                                      { return this.countTrailingNot(BasedSequence.WHITESPACE_NO_EOL_CHARS, 0, length()); }
    
    @Override public int countLeading(CharSequence chars)                        { return countLeading(chars, 0, length()); }
    @Override public int countLeadingNot(CharSequence chars)                     { return countLeadingNot(chars, 0, length()); }
    @Override public int countLeading(CharSequence chars, int fromIndex)         { return countLeading(chars, fromIndex, length()); }
    @Override public int countLeadingNot(CharSequence chars, int fromIndex)      { return countLeadingNot(chars, fromIndex, length()); }

    @Override public int countTrailing(CharSequence chars)                       { return countTrailing(chars, 0, length()); }
    @Override public int countTrailingNot(CharSequence chars)                    { return countTrailingNot(chars, 0, length()); }
    @Override public int countTrailing(CharSequence chars, int fromIndex)        { return countTrailing(chars, 0, fromIndex); }
    @Override public int countTrailingNot(CharSequence chars, int fromIndex)     { return countTrailingNot(chars, 0, fromIndex); }
    // @formatter:on

    @Override
    public int countLeading(CharSequence chars, int fromIndex, int endIndex) {
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
    public int countLeadingColumns(int startColumn, CharSequence chars) {
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
    public int countTrailing(CharSequence chars, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        if (startIndex > fromIndex) startIndex = fromIndex;
        int index = lastIndexOfAnyNot(chars, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex <= index ? 0 : fromIndex - index - 1;
    }

    @Override
    public int countLeadingNot(CharSequence chars, int fromIndex, int endIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (endIndex > length()) endIndex = length();
        if (fromIndex > endIndex) fromIndex = endIndex;
        int index = indexOfAny(chars, fromIndex, endIndex);
        return index == -1 ? endIndex - fromIndex : index - fromIndex;
    }

    @Override
    public int countTrailingNot(CharSequence chars, int startIndex, int fromIndex) {
        if (startIndex < 0) startIndex = 0;
        if (fromIndex > length()) fromIndex = length();
        if (startIndex > fromIndex) startIndex = fromIndex;
        int index = lastIndexOfAny(chars, startIndex, fromIndex);
        return index == -1 ? fromIndex - startIndex : fromIndex <= index ? 0 : fromIndex - index - 1;
    }

    @Override
    public BasedSequence trimStart(int keepLength, CharSequence chars) {
        int trim = Utils.maxLimit(countLeading(chars, 0, length()), length() - keepLength);
        return trim > 0 ? subSequence(trim, length()) : this;
    }

    @Override
    public BasedSequence trimmedStart(int keepLength, CharSequence chars) {
        int trim = Utils.maxLimit(countLeading(chars, 0, length()), length() - keepLength);
        return trim > 0 ? subSequence(0, trim) : BasedSequence.NULL;
    }

    @Override
    public BasedSequence trimEnd(int keepLength, CharSequence chars) {
        int trim = Utils.maxLimit(countTrailing(chars, 0, length()), length() - keepLength);
        return trim > 0 ? subSequence(0, length() - trim) : this;
    }

    @Override
    public BasedSequence trimmedEnd(int keepLength, CharSequence chars) {
        int trim = Utils.maxLimit(countTrailing(chars, 0, length()), length() - keepLength);
        return trim > 0 ? subSequence(length() - trim) : BasedSequence.NULL;
    }

    @Override
    public BasedSequence trimStart(CharSequence chars) {
        return trimStart(0, chars);
    }

    @Override
    public BasedSequence trimmedStart(CharSequence chars) {
        return trimmedStart(0, chars);
    }

    @Override
    public BasedSequence trimEnd(CharSequence chars) {
        return trimEnd(0, chars);
    }

    @Override
    public BasedSequence trimmedEnd(CharSequence chars) {
        return trimmedEnd(0, chars);
    }

    @Override
    public BasedSequence trim(CharSequence chars) {
        int trimStart = countLeading(chars, 0, length());
        int trimEnd = countTrailing(chars, 0, length());
        int trimmed = trimStart + trimEnd;
        return trimmed > 0 ? (trimmed >= length() ? subSequence(0, 0) : subSequence(trimStart, length() - trimEnd)) : this;
    }

    @Override
    public BasedSequence trimStart(int keepLength) {
        return trimStart(keepLength, WHITESPACE_CHARS);
    }

    @Override
    public BasedSequence trimmedStart(int keepLength) {
        return trimmedStart(keepLength, WHITESPACE_CHARS);
    }

    @Override
    public BasedSequence trimEnd(int keepLength) {
        return trimEnd(keepLength, WHITESPACE_CHARS);
    }

    @Override
    public BasedSequence trimmedEnd(int keepLength) {
        return trimmedEnd(keepLength, WHITESPACE_CHARS);
    }

    @Override
    public BasedSequence trimStart() {
        return trimStart(0, WHITESPACE_CHARS);
    }

    @Override
    public BasedSequence trimmedStart() {
        return trimmedStart(0, WHITESPACE_CHARS);
    }

    @Override
    public BasedSequence trimEnd() {
        return trimEnd(0, WHITESPACE_CHARS);
    }

    @Override
    public BasedSequence trimmedEnd() {
        return trimmedEnd(0, WHITESPACE_CHARS);
    }

    @Override
    public BasedSequence padStart(int length, char pad) {
        return length <= length() ? this : PrefixedSubSequence.repeatOf(pad, length - length(), this);
    }

    @Override
    public BasedSequence padEnd(int length, char pad) {
        return length <= length() ? this : SegmentedSequence.of(this, PrefixedSubSequence.repeatOf(pad, length - length(), this.subSequence(length(), length())));
    }

    @Override
    public BasedSequence padStart(int length) {
        return padStart(length, ' ');
    }

    @Override
    public BasedSequence padEnd(int length) {
        return padEnd(length, ' ');
    }

    @Override
    public int eolLength() {
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
    public int eolLength(int eolStart) {
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
        int trimStart = countLeading(WHITESPACE_CHARS, 0, length());
        if (trimStart == length()) {
            return subSequence(trimStart, trimStart);
        }

        int trimEnd = countTrailing(WHITESPACE_CHARS, 0, length());
        return trimStart > 0 || trimEnd > 0 ? subSequence(trimStart, length() - trimEnd) : this;
    }

    @Override
    public BasedSequence ifNull(BasedSequence other) {
        return isNull() ? other : this;
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
    public BasedSequence nullIf(final CharSequence... matches) {
        for (CharSequence match : matches) {
            if (matches(match)) return BasedSequence.NULL;
        }
        return this;
    }

    @Override
    public BasedSequence nullIfNot(final CharSequence... matches) {
        for (CharSequence match : matches) {
            if (matches(match)) return this;
        }
        return BasedSequence.NULL;
    }

    @Override
    public BasedSequence nullIfStartsWith(final CharSequence... matches) {
        for (CharSequence match : matches) {
            if (startsWith(match)) return BasedSequence.NULL;
        }
        return this;
    }

    @Override
    public BasedSequence nullIfStartsWithNot(final CharSequence... matches) {
        for (CharSequence match : matches) {
            if (startsWith(match)) return this;
        }
        return BasedSequence.NULL;
    }

    @Override
    public BasedSequence nullIfEndsWith(final CharSequence... matches) {
        for (CharSequence match : matches) {
            if (endsWith(match)) return BasedSequence.NULL;
        }
        return this;
    }

    @Override
    public BasedSequence nullIfEndsWithNot(final CharSequence... matches) {
        for (CharSequence match : matches) {
            if (endsWith(match)) return this;
        }
        return BasedSequence.NULL;
    }

    @Override
    public boolean isEmpty() {
        return length() == 0;
    }

    @Override
    public boolean isBlank() {
        return isEmpty() || countLeading(WHITESPACE_CHARS, 0, length()) == length();
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
        return length() > 0 && matchCharsReversed(suffix, length() - 1, false);
    }

    @Override
    public boolean startsWith(CharSequence prefix) {
        return length() > 0 && matchChars(prefix, 0, false);
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
    public boolean endsWithIgnoreCase(CharSequence suffix) {
        return length() > 0 && matchCharsReversed(suffix, length() - 1, true);
    }

    @Override
    public boolean startsWithIgnoreCase(CharSequence prefix) {
        return length() > 0 && matchChars(prefix, 0, true);
    }

    @Override
    public BasedSequence removeSuffixIgnoreCase(CharSequence suffix) {
        return !endsWithIgnoreCase(suffix) ? this : subSequence(0, length() - suffix.length());
    }

    @Override
    public BasedSequence removePrefixIgnoreCase(CharSequence prefix) {
        return !startsWithIgnoreCase(prefix) ? this : subSequence(prefix.length(), length());
    }

    @Override
    public BasedSequence removeProperSuffixIgnoreCase(CharSequence suffix) {
        return length() <= suffix.length() || !endsWithIgnoreCase(suffix) ? this : subSequence(0, length() - suffix.length());
    }

    @Override
    public BasedSequence removeProperPrefixIgnoreCase(CharSequence prefix) {
        return length() <= prefix.length() || !startsWithIgnoreCase(prefix) ? this : subSequence(prefix.length(), length());
    }

    @Override
    public boolean endsWith(CharSequence suffix, boolean ignoreCase) {
        return length() > 0 && matchCharsReversed(suffix, length() - 1, ignoreCase);
    }

    @Override
    public boolean startsWith(CharSequence prefix, boolean ignoreCase) {
        return length() > 0 && matchChars(prefix, 0, ignoreCase);
    }

    @Override
    public BasedSequence removeSuffix(CharSequence suffix, boolean ignoreCase) {
        return !endsWith(suffix, ignoreCase) ? this : subSequence(0, length() - suffix.length());
    }

    @Override
    public BasedSequence removePrefix(CharSequence prefix, boolean ignoreCase) {
        return !startsWith(prefix, ignoreCase) ? this : subSequence(prefix.length(), length());
    }

    @Override
    public BasedSequence removeProperSuffix(CharSequence suffix, boolean ignoreCase) {
        return length() <= suffix.length() || !endsWith(suffix, ignoreCase) ? this : subSequence(0, length() - suffix.length());
    }

    @Override
    public BasedSequence removeProperPrefix(CharSequence prefix, boolean ignoreCase) {
        return length() <= prefix.length() || !startsWith(prefix, ignoreCase) ? this : subSequence(prefix.length(), length());
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
        return chars.length() == length() && matchChars(chars, 0, false);
    }

    @Override
    public boolean matchesIgnoreCase(CharSequence chars) {
        return chars.length() == length() && matchChars(chars, 0, true);
    }

    @Override
    public boolean matches(CharSequence chars, boolean ignoreCase) {
        return chars.length() == length() && matchChars(chars, 0, ignoreCase);
    }

    @Override
    public boolean matchChars(CharSequence chars) {
        return matchChars(chars, 0, false);
    }

    @Override
    public boolean matchCharsIgnoreCase(CharSequence chars) {
        return matchChars(chars, 0, true);
    }

    @Override
    public boolean matchChars(CharSequence chars, boolean ignoreCase) {
        return matchChars(chars, 0, ignoreCase);
    }

    @Override
    public boolean matchChars(CharSequence chars, int startIndex) {
        return matchChars(chars, startIndex, false);
    }

    @Override
    public boolean matchCharsIgnoreCase(CharSequence chars, int startIndex) {
        return matchChars(chars, startIndex, false);
    }

    @Override
    public boolean matchChars(CharSequence chars, int startIndex, boolean ignoreCase) {
        int iMax = chars.length();
        if (iMax > length() - startIndex) return false;

        if (ignoreCase) {
            for (int i = 0; i < iMax; i++) {
                final char c1 = chars.charAt(i);
                final char c2 = charAt(i + startIndex);
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
    public boolean matchCharsReversed(CharSequence chars, int endIndex) {
        return endIndex + 1 >= chars.length() && matchChars(chars, endIndex + 1 - chars.length(), false);
    }

    @Override
    public boolean matchCharsReversedIgnoreCase(CharSequence chars, int endIndex) {
        return endIndex + 1 >= chars.length() && matchChars(chars, endIndex + 1 - chars.length(), true);
    }

    @Override
    public boolean matchCharsReversed(CharSequence chars, int endIndex, boolean ignoreCase) {
        return endIndex + 1 >= chars.length() && matchChars(chars, endIndex + 1 - chars.length(), ignoreCase);
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
    public BasedSequence baseSubSequence(final int start) {
        return baseSubSequence(start, getBaseSequence().getEndOffset());
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
        return Escaping.unescapeString(this);
    }

    @Override
    public String unescapeNoEntities() {
        return Escaping.unescapeString(this, false);
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
        if (other.isEmpty()) {
            return this;
        } else if (isEmpty()) {
            return other;
        }
        assert isContinuedBy(other) : "sequence[" + getStartOffset() + ", " + getEndOffset() + "] is not continued by other[" + other.getStartOffset() + ", " + other.getEndOffset() + "]";
        return baseSubSequence(getStartOffset(), other.getEndOffset());
    }

    @Override
    public boolean containsAllOf(BasedSequence other) {
        return getBase() == other.getBase() && other.getStartOffset() >= getStartOffset() && other.getEndOffset() <= getEndOffset();
    }

    @Override
    public boolean containsSomeOf(BasedSequence other) {
        return getBase() == other.getBase() && !(getStartOffset() >= other.getEndOffset() || getEndOffset() <= other.getStartOffset());
    }

    @Override
    public BasedSequence intersect(BasedSequence other) {
        if (getBase() != other.getBase()) return SubSequence.NULL;
        else if (other.getEndOffset() <= getStartOffset()) return subSequence(0, 0);
        else if (other.getStartOffset() >= getEndOffset()) return subSequence(length(), length());
        else return this.baseSubSequence(Utils.max(getStartOffset(), other.getStartOffset()), Utils.min(getEndOffset(), other.getEndOffset()));
    }

    @Override
    public BasedSequence prefixOf(final BasedSequence other) {
        if (getBase() != other.getBase()) return SubSequence.NULL;
        else if (other.getStartOffset() <= getStartOffset()) return subSequence(0, 0);
        else if (other.getStartOffset() >= getEndOffset()) return this;
        else return this.baseSubSequence(getStartOffset(), other.getStartOffset());
    }

    @Override
    public BasedSequence suffixOf(final BasedSequence other) {
        if (getBase() != other.getBase()) return SubSequence.NULL;
        else if (other.getEndOffset() >= getEndOffset()) return subSequence(length(), length());
        else if (other.getEndOffset() <= getStartOffset()) return this;
        else return this.baseSubSequence(other.getEndOffset(), getEndOffset());
    }

    @Override
    public BasedSequence[] split(char delimiter) {
        return split(delimiter, 0);
    }

    @Override
    public BasedSequence[] split(char delimiter, int limit) {
        return split(delimiter, limit, 0);
    }

    @Override
    public BasedSequence[] split(char delimiter, int limit, int flags) {
        return split(delimiter, limit, flags, WHITESPACE_CHARS);
    }

    @Override
    public BasedSequence[] split(CharSequence delimiter) {
        return split(delimiter, 0);
    }

    @Override
    public BasedSequence[] split(CharSequence delimiter, int limit) {
        return split(delimiter, limit, 0);
    }

    @Override
    public BasedSequence[] split(CharSequence delimiter, int limit, int flags) {
        return split(delimiter, limit, flags, WHITESPACE_CHARS);
    }

    @Override
    public BasedSequence[] split(char delimiter, int limit, int flags, String trimChars) {
        if (trimChars == null) trimChars = WHITESPACE_CHARS;
        if (limit < 1) limit = Integer.MAX_VALUE;

        boolean includeDelimiterParts = (flags & SPLIT_INCLUDE_DELIM_PARTS) != 0;
        int includeDelimiter = !includeDelimiterParts && (flags & SPLIT_INCLUDE_DELIMS) != 0 ? 1 : 0;
        boolean trimParts = (flags & SPLIT_TRIM_PARTS) != 0;
        boolean skipEmpty = (flags & SPLIT_SKIP_EMPTY) != 0;
        ArrayList<BasedSequence> items = new ArrayList<BasedSequence>();

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
        return items.toArray(new BasedSequence[items.size()]);
    }

    @Override
    public BasedSequence[] split(CharSequence delimiter, int limit, int flags, String trimChars) {
        if (trimChars == null) trimChars = WHITESPACE_CHARS;
        if (limit < 1) limit = Integer.MAX_VALUE;

        boolean includeDelimiterParts = (flags & SPLIT_INCLUDE_DELIM_PARTS) != 0;
        int includeDelimiter = !includeDelimiterParts && (flags & SPLIT_INCLUDE_DELIMS) != 0 ? delimiter.length() : 0;
        boolean trimParts = (flags & SPLIT_TRIM_PARTS) != 0;
        boolean skipEmpty = (flags & SPLIT_SKIP_EMPTY) != 0;
        ArrayList<BasedSequence> items = new ArrayList<BasedSequence>();

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
        return items.toArray(new BasedSequence[items.size()]);
    }

    @Override
    public BasedSequence appendTo(final StringBuilder out) {
        return appendTo(out, 0, length());
    }

    @Override
    public BasedSequence appendTo(final StringBuilder out, final int start) {
        return appendTo(out, start, length());
    }

    @Override
    public BasedSequence appendTo(final StringBuilder out, final int start, final int end) {
        out.append(this, start, end);
        return this;
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
    public int[] indexOfAll(final CharSequence s) {
        final int length = s.length();
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
    public BasedSequence replace(final CharSequence find, final CharSequence replace) {
        int[] indices = indexOfAll(find);
        if (indices.length == 0) return this;

        final int iMax = indices.length;
        StringBuilder sb = new StringBuilder(length() + (replace.length() - find.length()) * iMax);
        final BasedSequence basedReplace = SubSequence.of(replace);
        int i = 0;
        int lastPos = 0;
        while (i < iMax) {
            int pos = indices[i++];
            if (lastPos < pos) appendTo(sb, lastPos, pos);
            lastPos = pos + find.length();
            basedReplace.appendTo(sb);
        }

        if (lastPos < length()) {
            appendTo(sb, lastPos, length());
        }

        return CharSubSequence.of(sb);
    }

    @Override
    public BasedSequence append(final CharSequence... others) {
        if (others.length > 0) {
            int total = 0;
            for (CharSequence other : others) total += other.length();
            StringBuilder sb = new StringBuilder(length() + total);
            appendTo(sb);
            for (CharSequence other : others) {
                if (other instanceof BasedSequence) ((BasedSequence) other).appendTo(sb);
                else sb.append(other);
            }
            return CharSubSequence.of(sb);
        }
        return this;
    }

    @Override
    public int getColumnAtIndex(final int index) {
        int lineStart = lastIndexOfAny(EOL_CHARS, index);
        return index - (lineStart == -1 ? 0 : lineStart + eolLength(lineStart));
    }

    @Override
    public Pair<Integer, Integer> getLineColumnAtIndex(final int index) {
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
        return (this == other) || other != null && other instanceof CharSequence && ((CharSequence) other).length() == length() && matchChars((CharSequence) other, 0, false);
    }

    @Override
    public boolean equalsIgnoreCase(CharSequence other) {
        return (this == other) || (other != null) && other.length() == length() && matchChars(other, 0, true);
    }

    @Override
    public boolean equals(Object other, boolean ignoreCase) {
        return (this == other) || other != null && other instanceof CharSequence && ((CharSequence) other).length() == length() && matchChars((CharSequence) other, 0, ignoreCase);
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

    public static BasedSequence of(CharSequence charSequence) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence);
        else if (charSequence instanceof String) return CharSubSequence.of(charSequence);
        else if (charSequence != null) return SubSequence.of(charSequence);
        return BasedSequence.NULL;
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
