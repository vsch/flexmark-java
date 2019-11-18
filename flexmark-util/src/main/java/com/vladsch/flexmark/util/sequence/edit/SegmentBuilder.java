package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class SegmentBuilder {
    private ArrayList<Object> myParts = new ArrayList<>();      // contains either Range of original sequence kept, or String inserted at position after the last Range
    private int myStartOffset = 0;
    private int myEndOffset = 0;
    private int myLastRangeIndex = 0;

    protected SegmentBuilder() {

    }

    protected SegmentBuilder(@NotNull SegmentBuilder other) {
        myParts.addAll(other.myParts);
        myStartOffset = other.myStartOffset;
        myEndOffset = other.myEndOffset;
        myLastRangeIndex = other.myLastRangeIndex;
    }

    public int getStartOffset() {
        return myEndOffset;
    }

    public int getEndOffset() {
        return myEndOffset;
    }

    public boolean isEmpty() {
        return myParts.isEmpty();
    }

    public boolean isNotEmpty() {
        return !myParts.isEmpty();
    }

    public int length() {
        int length = 0;
        for (Object part : myParts) {
            if (part instanceof String) {
                length += ((String) part).length();
            } else {
                length += ((Range) part).length();
            }
        }
        return length;
    }

    /**
     * append range in original range coordinates, no checking is done other than overlap with tail range
     *
     * @param startOffset start offset
     * @param endOffset   end offset
     */
    @NotNull
    public SegmentBuilder append(int startOffset, int endOffset) {
        return append(Range.of(startOffset, endOffset));
    }

    /**
     * @param updateEndOffset if true update end offset
     * @return position of last range or end of list position
     */
    private int getLastRangeIndex(boolean updateEndOffset) {
        // should not have to look further than last 2 entries to find a range
        int iMax = myParts.size();
        for (int i = iMax; i-- > myLastRangeIndex; ) {
            Object part = myParts.get(i);
            if (part instanceof Range) {
                myLastRangeIndex = i;
                if (updateEndOffset) {
                    int endOffset = ((Range) part).getEnd();
                    assert endOffset >= myEndOffset;

                    myEndOffset = endOffset;
                }
                if (((Range) part).getEnd() != myEndOffset) {
                    int tmp = 0;
                }
                assert ((Range) part).getEnd() == myEndOffset : "Expected end: " + ((Range) part).getEnd() + " got: " + myEndOffset;
                return i;
            }
        }
        return myLastRangeIndex;
    }

    /**
     * @return position of last range or end of list position
     */
    @NotNull
    private SegmentPosition getLastRangePosition(@NotNull SegmentList segmentList) {
        // should not have to look further than last 2 entries to find a range
        SegmentPosition position = segmentList.getLast();
        if (position.getRangeOrNull() != null) return position;
        if (position.getRangeOrNull(-1) != null) return position.previous();
        return segmentList.getEnd();
    }

    @NotNull
    public Range getOffsetRange() {
        Range range = myParts.size() == 0 ? Range.NULL : Range.of(myStartOffset, myEndOffset);
        // RELEASE: remove assert
        assert range.equals(updateOffsets());
        return range;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    private Range updateOffsets() {
        @NotNull List<Object> list = myParts;
        Object o1 = null;
        Object o2 = null;

        myStartOffset = 0;
        myEndOffset = 0;
        myLastRangeIndex = 0;

        int iMax = list.size();
        switch (iMax) {
            case 0:
                return Range.NULL;

            case 1:
                o1 = list.get(0);
                if (o1 instanceof Range) {
                    myStartOffset = ((Range) o1).getStart();
                    myEndOffset = ((Range) o1).getEnd();
                    return (Range) o1;
                }
                return Range.NULL;

            case 2:
                o1 = list.get(0);
                o2 = list.get(1);
                if (o1 instanceof Range && o2 instanceof Range) {
                    myStartOffset = ((Range) o1).getStart();
                    myEndOffset = ((Range) o2).getEnd();
                    myLastRangeIndex = 1;
                    return Range.of(myStartOffset, myEndOffset);
                }
                if (o1 instanceof Range) {
                    myStartOffset = ((Range) o1).getStart();
                    myEndOffset = ((Range) o1).getEnd();
                    myLastRangeIndex = 0;
                    return (Range) o1;
                }
                if (o2 instanceof Range) {
                    myStartOffset = ((Range) o2).getStart();
                    myEndOffset = ((Range) o2).getEnd();
                    myLastRangeIndex = 1;
                    return (Range) o2;
                }
                return Range.NULL;

            default:
                int firstRange = 0;
                for (int i = 0; i < iMax; i++) {
                    o1 = list.get(i);
                    if (o1 instanceof Range) {
                        firstRange = i;
                        break;
                    }
                }

                for (int i = iMax; i-- > firstRange; ) {
                    o2 = list.get(i);
                    if (o2 instanceof Range) {
                        myLastRangeIndex = i;
                        break;
                    }
                }

                if (o2 == null) o2 = o1;

                if (o1 != null) {
                    myStartOffset = ((Range) o1).getStart();
                    myEndOffset = ((Range) o2).getEnd();
                    return Range.of(myStartOffset, myEndOffset);
                }

                return Range.NULL;
        }
    }

    @Nullable
    public Range getLastPartIfRange() {
        Object part = myParts.isEmpty() ? null : myParts.get(myParts.size() - 1);
        return part instanceof Range ? (Range) part : null;
    }

    @Nullable
    public String getLastPartIfString() {
        Object part = myParts.isEmpty() ? null : myParts.get(myParts.size() - 1);
        return part instanceof String ? (String) part : null;
    }

    public void handleOverlap(@NotNull SegmentPosition position, @NotNull Range range) {
        // NOTE: one after the last range should be String or nothing, if it was a Range then it would be the last one
        String text = position.getStringOrNull(1);
        Range prevRange = position.getRangeOrNull();
        assert prevRange != null && prevRange.overlaps(range);

        if (!prevRange.doesContain(range)) {
            // merge if no text in between, else chop and append
            if (text == null || text.isEmpty()) {
                Range expanded = prevRange.expandToInclude(range);
                position.set(expanded);
                if (text != null) position.remove(1);
            } else {
                // chop off overlap and append
                Range chopped = range.withStart(prevRange.getEnd());
                if (chopped.isNotEmpty()) {
                    position.append(chopped);
                }
            }
//        } else {
//            // fully contains added range, ignore
        }
    }

    /**
     * append range in original sequence coordinates, no checking is done other than overlap with tail range
     * fast
     *
     * @param range in offsets of original range
     */
    @NotNull
    public SegmentBuilder append(@Nullable Range range) {
        if (range == null || range.isNull()) return this;
        if (myParts.isEmpty()) myStartOffset = range.getStart();

        // NOTE: if an empty range is not inserted then there is no range to attach recovered characters.
        //    empty ranges can be removed during optimization
        if (myEndOffset > 0 && myEndOffset >= range.getStart()) {
            // have overlap, remove overlap from range and add
            SegmentList segmentList = new SegmentList(myParts);
            SegmentPosition position = getLastRangePosition(segmentList);

            if (position.isValidElement()) {
                Range prevRange = position.getRangeOrNull();
                if (prevRange != null) {
                    if (prevRange.overlaps(range)) {
                        handleOverlap(position, range);
                        myLastRangeIndex = getLastRangeIndex(true);
                    } else if (prevRange.isAdjacentBefore(range)) {
                        // if no text in between merge them, else append
                        String text = position.getStringOrNull(1);

                        if (text == null || text.isEmpty()) {
                            Range expanded = prevRange.expandToInclude(range);
                            myEndOffset = expanded.getEnd();
                            position.set(expanded);
                            if (text != null) position.remove(1);
                        } else {
                            // just append
                            myParts.add(range);
                            myEndOffset = range.getEnd();
                            myLastRangeIndex = myParts.size() - 1;
                        }
                    }
                }
            }
        } else {
            // just append
            myParts.add(range);
            myEndOffset = range.getEnd();
            myLastRangeIndex = myParts.size() - 1;
        }

        int lastRangeIndex = getLastRangeIndex(false);
        assert myLastRangeIndex == lastRangeIndex;
        return this;
    }

    @NotNull
    public SegmentBuilder append(@Nullable String text) {
        if (text != null && !text.isEmpty()) {
            String lastString = getLastPartIfString();
            if (lastString != null) {
                myParts.set(myParts.size() - 1, lastString + text);
            } else {
                myParts.add(text);
            }
        }
        return this;
    }

    @NotNull
    public SegmentBuilder append(@Nullable Object part) {
        if (part != null) {
            assert part instanceof Range || part instanceof String;
            if (part instanceof Range) append((Range) part);
            else append((String) part);
        }
        return this;
    }

    @NotNull
    public List<Object> getParts() {
        return myParts;
    }

    public <T extends SequenceBuilder<T, S>, S extends IRichSequence<S>> void buildSequence(@NotNull S baseSequence, @NotNull T builder) {
        if (myEndOffset > baseSequence.length()) {
            throw new IllegalArgumentException("baseSequence length() must be at least " + myEndOffset + ", got: " + baseSequence.length());
        }

        for (Object part : getParts()) {
            if (part instanceof String) {
                builder.add((String) part);
            } else {
                assert part instanceof Range;
                builder.add(baseSequence.subSequence(((Range) part).getStart(), ((Range) part).getEnd()));
            }
        }
    }

    @NotNull
    public String toStringWithRanges(@NotNull CharSequence chars) {
        BasedSequence baseSequence = BasedSequence.of(chars);

        if (myEndOffset > baseSequence.length()) {
            throw new IllegalArgumentException("baseSequence length() must be at least " + myEndOffset + ", got: " + baseSequence.length());
        }

        StringBuilder out = new StringBuilder();

        for (Object part : myParts) {
            if (part instanceof String) {
                out.append(BasedSequence.of((String) part).toVisibleWhitespaceString());
            } else {
                assert part instanceof Range;
                out.append("⟦").append(baseSequence.subSequence(((Range) part).getStart(), ((Range) part).getEnd()).toVisibleWhitespaceString()).append("⟧");
            }
        }

        return out.toString();
    }

    /**
     * Extend ranges to include sequence chars which match adjacent inserted characters, effectively removing redundant inserts
     *
     * @param chars sequence for which to optimize the ranges
     * @param <S>   type of rich sequence
     */
    public <S extends IRichSequence<S>> void optimizeFor(@NotNull S chars, SegmentOptimizer<S> optimizer) {
        if (myEndOffset > chars.length()) {
            throw new IllegalArgumentException("baseSequence length() must be at least " + myEndOffset + ", got: " + chars.length());
        }

        SegmentList segmentList = new SegmentList(myParts);

        for (SegmentPosition position : segmentList) {
            if (position.getStringOrNull() == null) continue;
            optimizer.accept(chars, position);
        }
        updateOffsets();
    }

    @Override
    public String toString() {
        DelimitedBuilder sb = new DelimitedBuilder(", ");
        sb.append("SegmentBuilder{end=").append(myEndOffset).append(", parts=");
        for (Object part : myParts) {
            if (part instanceof Range) {
                sb.append(part).mark();
            } else {
                sb.append("'").append(Utils.escapeJavaString((String) part)).append("'").mark();
            }
        }
        sb.unmark().append(" }");
        return sb.toString();
    }

    @NotNull
    public static SegmentBuilder emptyBuilder() {
        return new SegmentBuilder();
    }
}
