package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.mappers.CharMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
@SuppressWarnings("SameParameterValue")
public interface BasedSequence extends CharSequence {
    BasedSequence NULL = new EmptyBasedSequence();
    BasedSequence EOL = new SubCharSequence("\n");
    List<BasedSequence> EMPTY_LIST = new ArrayList<>();
    BasedSequence[] EMPTY_ARRAY = new BasedSequence[0];
    String WHITESPACE_NO_EOL_CHARS = " \t";
    String WHITESPACE_CHARS = " \t\r\n";
    String WHITESPACE_NBSP_CHARS = " \t\r\n\u00A0";

    String EOL_CHARS = "\r\n";
    char EOL_CHAR = EOL_CHARS.charAt(1);
    char EOL_CHAR1 = EOL_CHARS.charAt(0);
    char EOL_CHAR2 = EOL_CHARS.charAt(1);

    CharSequence getBase();
    int getStartOffset();
    int getEndOffset();
    int getIndexOffset(int index);
    Range getSourceRange();

    @Override
    BasedSequence subSequence(int start, int end);
    BasedSequence subSequence(Range range);

    BasedSequence subSequence(int start);
    BasedSequence baseSubSequence(int start, int end);

    // start and end are offsets from the end of the sequence
    // no exceptions are thrown, instead the range is reduced to the legal available range
    BasedSequence endSequence(int start, int end);
    BasedSequence endSequence(int start);

    // index from the end of the sequence
    // no exceptions are thrown, instead a \0 is returned for an invalid index
    char endCharAt(int index);

    // start and end when >=0 are offsets from the start of the sequence, when <0 from the end of sequence
    // no exceptions are thrown, instead the range is reduced to the legal available range
    BasedSequence midSequence(int start, int end);
    BasedSequence midSequence(int start);

    // when index >= from the start of the sequence, when <0 then from the end
    // no exceptions are thrown, instead a \0 is returned for an invalid index
    char midCharAt(int index);

    int indexOf(CharSequence s);
    int indexOf(CharSequence s, int startIndex);
    int indexOf(CharSequence s, int startIndex, int endIndex);

    int indexOf(char c);
    int indexOfAny(char c1, char c2);
    int indexOfAny(char c1, char c2, char c3);
    int indexOfAny(CharSequence s);

    int indexOf(char c, int fromIndex);
    int indexOfAny(char c1, char c2, int fromIndex);
    int indexOfAny(char c1, char c2, char c3, int fromIndex);
    int indexOfAny(CharSequence s, int fromIndex);

    int indexOf(char c, int fromIndex, int endIndex);
    int indexOfAny(char c1, char c2, int fromIndex, int endIndex);
    int indexOfAny(char c1, char c2, char c3, int fromIndex, int endIndex);
    int indexOfAny(CharSequence s, int fromIndex, int endIndex);

    int indexOfNot(char c);
    int indexOfAnyNot(char c1, char c2);
    int indexOfAnyNot(char c1, char c2, char c3);
    int indexOfAnyNot(CharSequence s);

    int indexOfNot(char c, int fromIndex);
    int indexOfAnyNot(char c1, char c2, int fromIndex);
    int indexOfAnyNot(char c1, char c2, char c3, int fromIndex);
    int indexOfAnyNot(CharSequence s, int fromIndex);

    int indexOfNot(char c, int fromIndex, int endIndex);
    int indexOfAnyNot(char c1, char c2, int fromIndex, int endIndex);
    int indexOfAnyNot(char c1, char c2, char c3, int fromIndex, int endIndex);
    int indexOfAnyNot(CharSequence s, int fromIndex, int endIndex);

    int lastIndexOf(CharSequence s);
    int lastIndexOf(CharSequence s, int fromIndex);
    int lastIndexOf(CharSequence s, int startIndex, int fromIndex);

    int lastIndexOf(char c);
    int lastIndexOfAny(char c1, char c2);
    int lastIndexOfAny(char c1, char c2, char c3);
    int lastIndexOfAny(CharSequence s);

    int lastIndexOf(char c, int fromIndex);
    int lastIndexOfAny(char c1, char c2, int fromIndex);
    int lastIndexOfAny(char c1, char c2, char c3, int fromIndex);
    int lastIndexOfAny(CharSequence s, int fromIndex);

    int lastIndexOf(char c, int startIndex, int fromIndex);
    int lastIndexOfAny(char c1, char c2, int startIndex, int fromIndex);
    int lastIndexOfAny(char c1, char c2, char c3, int startIndex, int fromIndex);
    int lastIndexOfAny(CharSequence s, int startIndex, int fromIndex);

    int lastIndexOfNot(char c);
    int lastIndexOfAnyNot(char c1, char c2);
    int lastIndexOfAnyNot(char c1, char c2, char c3);
    int lastIndexOfAnyNot(CharSequence s);

    int lastIndexOfNot(char c, int fromIndex);
    int lastIndexOfAnyNot(char c1, char c2, int fromIndex);
    int lastIndexOfAnyNot(char c1, char c2, char c3, int fromIndex);
    int lastIndexOfAnyNot(CharSequence s, int fromIndex);

    int lastIndexOfNot(char c, int startIndex, int fromIndex);
    int lastIndexOfAnyNot(char c1, char c2, int startIndex, int fromIndex);
    int lastIndexOfAnyNot(char c1, char c2, char c3, int startIndex, int fromIndex);
    int lastIndexOfAnyNot(CharSequence s, int startIndex, int fromIndex);

    int countLeading(CharSequence chars);
    int countLeadingNot(CharSequence chars);
    int countLeading(CharSequence chars, int startIndex);
    int countLeadingNot(CharSequence chars, int startIndex);
    int countLeading(CharSequence chars, int startIndex, int endIndex);
    int countLeadingNot(CharSequence chars, int startIndex, int endIndex);

    int countTrailing(CharSequence chars);
    int countTrailingNot(CharSequence chars);
    int countTrailing(CharSequence chars, int startIndex);
    int countTrailingNot(CharSequence chars, int startIndex);
    int countTrailing(CharSequence chars, int startIndex, int endIndex);
    int countTrailingNot(CharSequence chars, int startIndex, int endIndex);

    int countLeading(char c);
    int countLeadingNot(char c);
    int countLeading(char c, int startIndex);
    int countLeadingNot(char c, int startIndex);
    int countLeading(char c, int startIndex, int endIndex);
    int countLeadingNot(char c, int startIndex, int endIndex);

    int countTrailing(char c);
    int countTrailingNot(char c);
    int countTrailing(char c, int startIndex);
    int countTrailingNot(char c, int startIndex);
    int countTrailing(char c, int startIndex, int endIndex);
    int countTrailingNot(char c, int startIndex, int endIndex);

    int countChars(char c);
    int countCharsReversed(char c);
    int countNotChars(char c);
    int countNotCharsReversed(char c);

    int countChars(char c, int startIndex);
    int countCharsReversed(char c, int startIndex);
    int countNotChars(char c, int startIndex);
    int countNotCharsReversed(char c, int startIndex);

    int countChars(char c, int startIndex, int endIndex);
    int countNotChars(char c, int startIndex, int endIndex);
    int countCharsReversed(char c, int startIndex, int endIndex);
    int countNotCharsReversed(char c, int startIndex, int endIndex);

    int countChars(CharSequence chars);
    int countCharsReversed(CharSequence chars);
    int countNotChars(CharSequence chars);
    int countNotCharsReversed(CharSequence chars);

    int countChars(CharSequence chars, int startIndex);
    int countCharsReversed(CharSequence chars, int startIndex);
    int countNotChars(CharSequence chars, int startIndex);
    int countNotCharsReversed(CharSequence chars, int startIndex);

    int countChars(CharSequence chars, int startIndex, int endIndex);
    int countNotChars(CharSequence chars, int startIndex, int endIndex);
    int countCharsReversed(CharSequence chars, int startIndex, int endIndex);
    int countNotCharsReversed(CharSequence chars, int startIndex, int endIndex);

    BasedSequence trimStart(CharSequence chars);
    BasedSequence trimEnd(CharSequence chars);
    BasedSequence trim(CharSequence chars);
    BasedSequence trimStart();
    BasedSequence trimEnd();
    BasedSequence trim();
    BasedSequence trimEOL();
    BasedSequence trimmedStart(CharSequence chars);
    BasedSequence trimmedEnd(CharSequence chars);
    BasedSequence trimmedStart();
    BasedSequence trimmedEnd();
    BasedSequence trimmedEOL();
    int eolLength();

    boolean isEmpty();
    boolean isBlank();
    boolean isNull();
    boolean isNotNull();

    BasedSequence ifNullEmptyAfter(BasedSequence other);
    BasedSequence ifNullEmptyBefore(BasedSequence other);
    BasedSequence nullIfEmpty();
    BasedSequence nullIfBlank();
    BasedSequence nullIf(boolean condition);

    int endOfDelimitedBy(CharSequence s, int index);
    int endOfDelimitedByAny(CharSequence s, int index);
    int endOfDelimitedByAnyNot(CharSequence s, int index);

    int startOfDelimitedBy(CharSequence s, int index);
    int startOfDelimitedByAny(CharSequence s, int index);
    int startOfDelimitedByAnyNot(CharSequence s, int index);

    int endOfLine(int index);
    int endOfLineAnyEOL(int index);
    int startOfLine(int index);
    int startOfLineAnyEOL(int index);

    String unescape();
    BasedSequence unescape(ReplacedTextMapper textMapper);
    String normalizeEOL();
    BasedSequence normalizeEOL(ReplacedTextMapper textMapper);

    String normalizeEndWithEOL();
    BasedSequence normalizeEndWithEOL(ReplacedTextMapper textMapper);

    boolean matches(CharSequence chars);
    boolean matchChars(CharSequence chars);
    boolean matchChars(CharSequence chars, int startIndex);
    boolean matchCharsReversed(CharSequence chars, int endIndex);

    boolean endsWith(CharSequence suffix);
    boolean startsWith(CharSequence prefix);

    BasedSequence removeSuffix(CharSequence suffix);
    BasedSequence removePrefix(CharSequence prefix);
    BasedSequence removeProperSuffix(CharSequence suffix);
    BasedSequence removeProperPrefix(CharSequence prefix);

    MappedSequence toLowerCase();
    MappedSequence toUpperCase();
    MappedSequence toLowerCase(Locale locale);
    MappedSequence toUpperCase(Locale locale);
    MappedSequence toMapped(CharMapper mapper);

    BasedSequence trimTailBlankLines();
    BasedSequence trimLeadBlankLines();
    String toVisibleWhitespaceString();

    boolean isContinuedBy(BasedSequence other);
    boolean isContinuationOf(BasedSequence other);

    BasedSequence spliceAtEnd(BasedSequence other);

    /**
     * start/end offset based containment, not textual
     *
     * @param other based sequence from the same parent
     * @return true if other is contained in this
     */
    boolean contains(BasedSequence other);

    int SPLIT_INCLUDE_DELIMS = 1;
    int SPLIT_TRIM_PARTS = 2;
    int SPLIT_SKIP_EMPTY = 4;
    int SPLIT_INCLUDE_DELIM_PARTS = 8;
    int SPLIT_TRIM_SKIP_EMPTY = SPLIT_TRIM_PARTS | SPLIT_SKIP_EMPTY;

    List<BasedSequence> split(char delimiter);
    List<BasedSequence> split(char delimiter, int limit);
    List<BasedSequence> split(char delimiter, int limit, int flags);
    List<BasedSequence> split(char delimiter, int limit, int flags, String trimChars);
    List<BasedSequence> split(CharSequence delimiter);
    List<BasedSequence> split(CharSequence delimiter, int limit);
    List<BasedSequence> split(CharSequence delimiter, int limit, int flags);
    List<BasedSequence> split(CharSequence delimiter, int limit, int flags, String trimChars);

    /**
     * @return the last character of the sequence or '\0' if empty
     */
    char lastChar();

    /**
     * @return the first character of the sequence or '\0' if empty
     */
    char firstChar();

    class EmptyBasedSequence extends BasedSequenceImpl {
        @Override
        public int length() {
            return 0;
        }

        @Override
        public char charAt(int index) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }

        @Override
        public int getIndexOffset(int index) {
            if (index == 0) return 0;
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }

        @Override
        public BasedSequence subSequence(int i, int i1) {
            if (i == 0 && i1 == 0) return this;
            throw new StringIndexOutOfBoundsException("EMPTY subSequence(" + i + "," + i1 + ") only subSequence(0, 0) is allowed");
        }

        @Override
        public BasedSequence baseSubSequence(int start, int end) {
            return subSequence(start, end);
        }

        @Override
        public CharSequence getBase() {
            return BasedSequence.NULL;
        }

        @Override
        public int getStartOffset() {
            return 0;
        }

        @Override
        public int getEndOffset() {
            return 0;
        }

        @Override
        public Range getSourceRange() {
            return Range.NULL;
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
