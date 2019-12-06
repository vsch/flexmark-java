package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

public interface ISegmentBuilder<S extends ISegmentBuilder<S>> {
    int F_INCLUDE_ANCHORS = 0x01;
    int F_TRACK_UNIQUE = 0x02;
    int getOptions();
    boolean isIncludeAnchors();

    boolean isEmpty();
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

    @NotNull S append(int startOffset, int endOffset);
    @NotNull S append(CharSequence text);
    @NotNull S appendAnchor(int offset);
    @NotNull S append(@NotNull Range range);
    @NotNull String toStringWithRangesVisibleWhitespace(@NotNull CharSequence chars);
    @NotNull String toStringWithRanges(@NotNull CharSequence chars);
    @NotNull String toString(@NotNull CharSequence chars);
}
