package com.vladsch.flexmark.internal.util.sequence;

import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.mappers.CharMapper;
import com.vladsch.flexmark.internal.util.mappers.LowerCaseMapper;
import com.vladsch.flexmark.internal.util.mappers.UpperCaseMapper;

import java.util.*;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public abstract class BasedSequenceImpl implements BasedSequence {

    public static final String WHITESPACE_CHARS = " \t\r\n";
    public static final String WHITESPACE_NBSP_CHARS = " \t\r\n\u00A0";
    public static final String EOL_CHARS = "\r\n";

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

    @Override
    public int countLeading(String chars) {
        return countChars(chars, 0, length());
    }

    @Override
    public int countLeadingNot(String chars) {
        return countNotChars(chars, 0, length());
    }

    @Override
    public int countTrailing(String chars) {
        return countCharsReversed(chars, 0, length());
    }

    @Override
    public int countTrailingNot(String chars) {
        return countNotCharsReversed(chars, 0, length());
    }

    @Override
    public BasedSequence trimStart(String chars) {
        int trim = countChars(chars, 0, length());
        return trim > 0 ? subSequence(trim, length()) : this;
    }

    @Override
    public BasedSequence trimmedStart(String chars) {
        int trim = countChars(chars, 0, length());
        return trim > 0 ? subSequence(0, trim) : SubSequence.NULL;
    }

    @Override
    public BasedSequence trimEnd(String chars) {
        int trim = countCharsReversed(chars, 0, length());
        return trim > 0 ? subSequence(0, length() - trim) : this;
    }

    @Override
    public BasedSequence trimmedEnd(String chars) {
        int trim = countCharsReversed(chars, 0, length());
        return trim > 0 ? subSequence(length() - trim) : SubSequence.NULL;
    }

    @Override
    public BasedSequence trim(String chars) {
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
        return trim > 0 ? subSequence(0, trim) : SubSequence.NULL;
    }

    @Override
    public BasedSequence trimEnd() {
        int trim = countCharsReversed(WHITESPACE_CHARS, 0, length());
        return trim > 0 ? subSequence(0, length() - trim) : this;
    }

    @Override
    public BasedSequence trimmedEnd() {
        int trim = countCharsReversed(WHITESPACE_CHARS, 0, length());
        return trim > 0 ? subSequence(length() - trim) : SubSequence.NULL;
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
        return trim > 0 ? subSequence(length() - trim) : SubSequence.NULL;
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
        return isEmpty() ? SubSequence.NULL : this;
    }

    @Override
    public BasedSequence nullIfBlank() {
        return isBlank() ? SubSequence.NULL : this;
    }

    @Override
    public BasedSequence nullIf(boolean condition) {
        return condition ? SubSequence.NULL : this;
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
        return this == SubSequence.NULL;
    }

    @Override
    public boolean isNotNull() {
        return this != SubSequence.NULL;
    }

    @Override
    public boolean endsWith(String suffix) {
        return matchCharsReversed(suffix, length());
    }

    @Override
    public boolean startsWith(String prefix) {
        return matchChars(prefix, 0);
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
    final public MappedSequence toMapped(CharMapper mapper) {
        return new MappedSequence(this, mapper);
    }

    private static boolean contains(String chars, char c) {
        int iMax = chars.length();

        for (int i = 0; i < iMax; i++) {
            if (chars.charAt(i) == c) return true;
        }

        return false;
    }

    @Override
    public boolean matches(String chars) {
        return chars.length() == length() && matchChars(chars);
    }

    @Override
    public boolean matchChars(String chars) {
        return matchChars(chars, 0);
    }

    @Override
    public boolean matchChars(String chars, int startIndex) {
        int iMax = chars.length();
        if (iMax > length() - startIndex) return false;

        for (int i = 0; i < iMax; i++) {
            if (chars.charAt(i) != charAt(i + startIndex)) return false;
        }
        return true;
    }

    @Override
    public boolean matchCharsReversed(String chars, int endIndex) {
        return matchChars(chars, endIndex - chars.length());
    }

    @Override
    public int countChars(String chars, int startIndex, int endIndex) {
        int count = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = startIndex; i < endIndex; i++) {
            if (!contains(chars, charAt(i))) break;
            count++;
        }

        return count;
    }

    @Override
    public int countCharsReversed(String chars, int startIndex, int endIndex) {
        int count = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = endIndex; i-- > startIndex; ) {
            if (!contains(chars, charAt(i))) break;
            count++;
        }

        return count;
    }

    @Override
    public int countNotChars(String chars, int startIndex, int endIndex) {
        int count = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = startIndex; i < endIndex; i++) {
            if (contains(chars, charAt(i))) break;
            count++;
        }

        return count;
    }

    @Override
    public int countNotCharsReversed(String chars, int startIndex, int endIndex) {
        int count = 0;
        if (endIndex > length()) endIndex = length();
        for (int i = endIndex; i-- > startIndex; ) {
            if (contains(chars, charAt(i))) break;
            count++;
        }

        return count;
    }

    @Override
    public int countChars(String chars) {
        return countChars(chars, 0, chars.length());
    }

    @Override
    public int countCharsReversed(String chars) {
        return countCharsReversed(chars, 0, chars.length());
    }

    @Override
    public int countNotChars(String chars) {
        return countNotChars(chars, 0, chars.length());
    }

    @Override
    public int countNotCharsReversed(String chars) {
        return countNotCharsReversed(chars, 0, chars.length());
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
    public int indexOf(char c) {
        return indexOf(c, 0);
    }

    @Override
    public int lastIndexOf(char c) {
        return lastIndexOf(c, length());
    }

    @Override
    public int indexOf(String s) {
        return indexOf(s, 0);
    }

    @Override
    public int lastIndexOf(String s) {
        return lastIndexOf(s, length());
    }

    @Override
    public int indexOf(char c, int index) {
        int iMax = length();
        for (int i = index; i < iMax; i++) {
            if (charAt(i) == c) return i;
        }
        return -1;
    }

    @Override
    public int indexOfAny(char c1, char c2) {
        return indexOfAny(c1, c2, 0);
    }

    @Override
    public int indexOfAny(char c1, char c2, int index) {
        int iMax = length();
        for (int i = index; i < iMax; i++) {
            char c = charAt(i);
            if (c == c1 || c == c2) return i;
        }
        return -1;
    }

    @Override
    public int indexOfAny(char c1, char c2, char c3) {
        return indexOfAny(c1, c2, c3, 0);
    }

    @Override
    public int indexOfAny(char c1, char c2, char c3, int index) {
        int iMax = length();
        for (int i = index; i < iMax; i++) {
            char c = charAt(i);
            if (c == c1 || c == c2 || c == c3) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(char c, int index) {
        for (int i = index; i-- > 0; i++) {
            if (charAt(i) == c) return i;
        }
        return -1;
    }

    @Override
    public int indexOf(String s, int index) {
        int sMax = s.length();
        if (sMax == 0) return index;

        int iMax = length();
        char firstChar = s.charAt(0);
        int pos = index;

        do {
            pos = indexOf(firstChar, pos);
            if (pos < 0 || pos + sMax > iMax) break;
            if (matchChars(s, pos)) return pos;
            pos++;
        } while (pos + sMax < iMax);

        return -1;
    }

    @Override
    public int lastIndexOf(String s, int index) {
        int sMax = s.length();
        if (sMax == 0) return index;

        int pos = index;
        char lastChar = s.charAt(s.length() - 1);

        do {
            pos = lastIndexOf(lastChar, pos);
            if (pos < sMax) break;
            if (matchCharsReversed(s, pos + 1)) return pos - sMax;
            pos--;
        } while (pos >= sMax);

        return -1;
    }

    public static final Map<Character, String> visibleSpacesMap;
    static {
        HashMap<Character, String> charMap = new HashMap<>();
        visibleSpacesMap = charMap;
        charMap.put('\n', "\\n");
        charMap.put('\r', "\\r");
        charMap.put('\f', "\\f");
        charMap.put('\t', "\\u2192");
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
    public List<BasedSequence> split(String delimiter) {
        return split(delimiter, 0);
    }

    @Override
    public List<BasedSequence> split(String delimiter, int limit) {
        return split(delimiter, limit, 0);
    }

    @Override
    public List<BasedSequence> split(String delimiter, int limit, int flags) {
        return split(delimiter, limit, flags, WHITESPACE_CHARS);
    }

    @Override
    public List<BasedSequence> split(char delimiter, int limit, int flags, String trimChars) {
        if (trimChars == null) trimChars = WHITESPACE_CHARS;
        if (limit < 1) limit = Integer.MAX_VALUE;

        boolean includeDelimParts = (flags & SPLIT_INCLUDE_DELIM_PARTS) != 0;
        int includeDelims = !includeDelimParts && (flags & SPLIT_INCLUDE_DELIMS) != 0 ? 1 : 0;
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
                    BasedSequence item = subSequence(lastPos, pos + includeDelims);
                    if (trimParts) item = item.trim(trimChars);
                    if (!item.isEmpty() || !skipEmpty) {
                        items.add(item);
                        if (includeDelimParts) {
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
    public List<BasedSequence> split(String delimiter, int limit, int flags, String trimChars) {
        if (trimChars == null) trimChars = WHITESPACE_CHARS;
        if (limit < 1) limit = Integer.MAX_VALUE;

        boolean includeDelimParts = (flags & SPLIT_INCLUDE_DELIM_PARTS) != 0;
        int includeDelims = !includeDelimParts && (flags & SPLIT_INCLUDE_DELIMS) != 0 ? 1 : 0;
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
                    BasedSequence item = subSequence(lastPos, pos + delimiter.length());
                    if (trimParts) item = item.trim(trimChars);
                    if (!item.isEmpty() || !skipEmpty) {
                        items.add(item);
                        if (includeDelimParts) {
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
}
