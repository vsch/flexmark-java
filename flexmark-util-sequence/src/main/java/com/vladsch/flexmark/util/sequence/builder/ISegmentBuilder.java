package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.misc.BitFieldSet;
import com.vladsch.flexmark.util.sequence.Range;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;

public interface ISegmentBuilder<S extends ISegmentBuilder<S>> extends Iterable<Object> {
    enum Options {
        INCLUDE_ANCHORS,
        TRACK_FIRST256,
    }

    Options O_INCLUDE_ANCHORS = Options.INCLUDE_ANCHORS;
    Options O_TRACK_FIRST256 = Options.TRACK_FIRST256;

    int F_INCLUDE_ANCHORS = BitFieldSet.intMask(O_INCLUDE_ANCHORS);
    int F_TRACK_FIRST256 = BitFieldSet.intMask(O_TRACK_FIRST256);

    int F_DEFAULT = F_INCLUDE_ANCHORS | F_TRACK_FIRST256;

    int getOptions();
    boolean isIncludeAnchors();

    boolean isEmpty();
    boolean isBaseSubSequenceRange();
    @Nullable Range getBaseSubSequenceRange();
    boolean haveOffsets();
    int getSpan();
    int getStartOffset();
    int getEndOffset();

    int size();
    CharSequence getText();
    int noAnchorsSize();
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
     *
     * @return iterator over segment builder parts
     */
    @Override
    Iterator<Object> iterator();

    /**
     * Return iterator over segments
     *
     * @return iterator over segment builder segments
     */
    Iterable<Seg> getSegments();

    S append(int startOffset, int endOffset);
    S append(CharSequence text);
    S appendAnchor(int offset);
    S append(Range range);
    String toStringWithRangesVisibleWhitespace(CharSequence chars);
    String toStringWithRanges(CharSequence chars);
    String toString(CharSequence chars);
}
