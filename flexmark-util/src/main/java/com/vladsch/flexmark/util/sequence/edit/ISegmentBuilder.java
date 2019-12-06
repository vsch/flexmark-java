package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public interface ISegmentBuilder<S extends ISegmentBuilder<S>> extends Iterable<Object> {
    int F_INCLUDE_ANCHORS = 0x01;
    int F_TRACK_FIRST256 = 0x02;
    int getOptions();
    boolean isIncludeAnchors();

    boolean isEmpty();
    boolean isBaseSubSequenceRange();
    @Nullable Range baseSubSequenceRange();
    boolean hasOffsets();
    int getSpan();
    int getStartOffset();
    int getEndOffset();

    int size();
    int length();

    boolean isTrackTextFirst256();
    int getTextLength();
    int getTextSegments();

    int getTextSpaceLength();
    int getTextSpaceSegments();

    int getTextFirst256Length();
    int getTextFirst256Segments();

    /**
     * Return iterator over segment parts
     * Range - BASE
     * CharSequence - TEXT
     * @return iterator over segment builder parts
     */
    @NotNull
    @Override
    Iterator<Object> iterator();

    @NotNull S append(int startOffset, int endOffset);
    @NotNull S append(CharSequence text);
    @NotNull S appendAnchor(int offset);
    @NotNull S append(@NotNull Range range);
    @NotNull String toStringWithRangesVisibleWhitespace(@NotNull CharSequence chars);
    @NotNull String toStringWithRanges(@NotNull CharSequence chars);
    @NotNull String toString(@NotNull CharSequence chars);
}
