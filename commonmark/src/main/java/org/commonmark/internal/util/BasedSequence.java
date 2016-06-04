package org.commonmark.internal.util;

import org.commonmark.internal.util.mappers.CharMapper;

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
    SourceRange getRange();

    @Override
    BasedSequence subSequence(int start, int end);

    BasedSequence subSequence(int start);
    BasedSequence baseSubSequence(int start, int end);

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
    boolean isEmpty();
    boolean isBlank();
    boolean isNull();
    boolean isNotNull();

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
}
