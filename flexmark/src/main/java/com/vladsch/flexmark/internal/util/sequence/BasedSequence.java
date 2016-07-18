package com.vladsch.flexmark.internal.util.sequence;

import com.vladsch.flexmark.internal.util.mappers.CharMapper;

import java.util.List;
import java.util.Locale;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public interface BasedSequence extends CharSequence {
    CharSequence getBase();
    int getStartOffset();
    int getEndOffset();
    int getIndexOffset(int index);
    SourceRange getSourceRange();

    @Override
    BasedSequence subSequence(int start, int end);

    BasedSequence subSequence(int start);
    BasedSequence baseSubSequence(int start, int end);

    // start and end are offsets from the end of the sequence
    BasedSequence endSequence(int start, int end);
    BasedSequence endSequence(int start);
    // index from the end of the sequence
    char endCharAt(int index);

    // start and end when >=0 are offsets from the start of the sequence, when <0 from the end of sequence
    BasedSequence midSequence(int start, int end);
    BasedSequence midSequence(int start);

    // when index >= from the start of the sequence, when <0 then from the end
    char midCharAt(int index);

    int countLeading(String chars);
    int countLeadingNot(String chars);
    int countTrailing(String chars);
    int countTrailingNot(String chars);
    int countChars(String chars, int startIndex, int endIndex);
    int countCharsReversed(String chars, int startIndex, int endIndex);
    int countNotChars(String chars, int startIndex, int endIndex);
    int countNotCharsReversed(String chars, int startIndex, int endIndex);
    int countChars(String chars);
    int countCharsReversed(String chars);
    int countNotChars(String chars);
    int countNotCharsReversed(String chars);

    BasedSequence trimStart(String chars);
    BasedSequence trimEnd(String chars);
    BasedSequence trim(String chars);
    BasedSequence trimStart();
    BasedSequence trimEnd();
    BasedSequence trim();
    BasedSequence trimEOL();
    BasedSequence trimmedStart(String chars);
    BasedSequence trimmedEnd(String chars);
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

    int indexOf(char c);
    int lastIndexOf(char c);
    int indexOf(String s);
    int lastIndexOf(String s);
    int indexOf(char c, int index);
    int indexOfAny(char c1, char c2);
    int indexOfAny(char c1, char c2, int index);
    int indexOfAny(char c1, char c2, char c3);
    int indexOfAny(char c1, char c2, char c3, int index);
    int indexOf(String s, int index);
    int lastIndexOf(char c, int index);
    int lastIndexOf(String s, int index);

    String unescape();
    BasedSequence unescape(ReplacedTextMapper textMapper);
    String normalizeEOL();
    BasedSequence normalizeEOL(ReplacedTextMapper textMapper);

    String normalizeEndWithEOL();
    BasedSequence normalizeEndWithEOL(ReplacedTextMapper textMapper);

    boolean matches(String chars);
    boolean matchChars(String chars);
    boolean matchChars(String chars, int startIndex);
    boolean matchCharsReversed(String chars, int endIndex);

    boolean endsWith(String suffix);
    boolean startsWith(String prefix);

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
    List<BasedSequence> split(String delimiter);
    List<BasedSequence> split(String delimiter, int limit);
    List<BasedSequence> split(String delimiter, int limit, int flags);
    List<BasedSequence> split(String delimiter, int limit, int flags, String trimChars);
}
