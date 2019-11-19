package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class SegmentBuilder {
    private ArrayList<EditOp> myParts = new ArrayList<>();      // contains either Range of original sequence kept, or String inserted at position after the last Range
    private int myStartOffset = 0;
    private int myEndOffset = 0;
    private int myLastRangeIndex = 0;
    private int myLength = 0;

    protected SegmentBuilder() {

    }

    protected SegmentBuilder(@NotNull SegmentBuilder other) {
        myParts.addAll(other.myParts);
        myStartOffset = other.myStartOffset;
        myEndOffset = other.myEndOffset;
        myLastRangeIndex = other.myLastRangeIndex;
        myLength = other.myLength;
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
        assert myLength == computeLength();
        return myLength;
    }

    private int computeLength() {
        int length = 0;
        for (EditOp part : myParts) {
            length += part.length();
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
        return append(EditOp.baseOp(startOffset, endOffset));
    }

    /**
     * @param updateEndOffset if true update end offset
     * @return position of last range or end of list position
     */
    private int getLastRangeIndex(boolean updateEndOffset) {
        // should not have to look further than last 2 entries to find a range
        int iMax = myParts.size();
        for (int i = iMax; i-- > myLastRangeIndex; ) {
            EditOp part = myParts.get(i);
            if (part.isRange()) {
                myLastRangeIndex = i;
                if (updateEndOffset) {
                    int endOffset = part.getEnd();
                    assert endOffset >= myEndOffset;
                    myEndOffset = endOffset;
                }
                if (part.getEnd() != myEndOffset) {
                    int tmp = 0;
                }
                assert part.getEnd() == myEndOffset : "Expected end: " + part.getEnd() + " got: " + myEndOffset;
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
        return segmentList.getPosition(myLastRangeIndex);
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
        EditOp o1 = EditOp.NULL_OP;
        EditOp o2 = EditOp.NULL_OP;

        myStartOffset = 0;
        myEndOffset = 0;
        myLastRangeIndex = 0;

        int iMax = myParts.size();
        switch (iMax) {
            case 0:
                return Range.NULL;

            case 1:
                o1 = myParts.get(0);
                if (o1.isRange()) {
                    myStartOffset = o1.getStart();
                    myEndOffset = o1.getEnd();
                    return o1;
                }
                return Range.NULL;

            case 2:
                o1 = myParts.get(0);
                o2 = myParts.get(1);
                if (o1.isRange() && o2.isRange()) {
                    myStartOffset = o1.getStart();
                    myEndOffset = o2.getEnd();
                    myLastRangeIndex = 1;
                    return Range.of(myStartOffset, myEndOffset);
                }
                if (o1.isRange()) {
                    myStartOffset = o1.getStart();
                    myEndOffset = o1.getEnd();
                    myLastRangeIndex = 0;
                    return o1;
                }
                if (o2.isRange()) {
                    myStartOffset = o2.getStart();
                    myEndOffset = o2.getEnd();
                    myLastRangeIndex = 1;
                    return o2;
                }
                return Range.NULL;

            default:
                int firstRange = 0;
                for (int i = 0; i < iMax; i++) {
                    o1 = myParts.get(i);
                    if (o1.isRange()) {
                        firstRange = i;
                        break;
                    }
                }

                for (int i = iMax; i-- > firstRange; ) {
                    o2 = myParts.get(i);
                    if (o2.isRange()) {
                        myLastRangeIndex = i;
                        break;
                    }
                }

                if (o2 == null) o2 = o1;

                if (o1 != null) {
                    myStartOffset = o1.getStart();
                    myEndOffset = o2.getEnd();
                    return Range.of(myStartOffset, myEndOffset);
                }

                return Range.NULL;
        }
    }

    @NotNull
    public EditOp getLastPartIfRange() {
        EditOp part = myParts.isEmpty() ? EditOp.NULL_OP : myParts.get(myParts.size() - 1);
        return part.isRange() ? part : EditOp.NULL_OP;
    }

    /**
     * Get last part if un-ranged text
     *
     * @return op or NULL_OP
     */
    @NotNull
    public EditOp getLastPartIfString() {
        EditOp part = myParts.isEmpty() ? EditOp.NULL_OP : myParts.get(myParts.size() - 1);
        return !part.isPlainText() ? EditOp.NULL_OP : part;
    }

    public void handleOverlap(@NotNull SegmentPosition position, @NotNull Range range) {
        // NOTE: one after the last range should be String or nothing, if it was a Range then it would be the last one
        EditOp text = position.getOrNullOp(1);
        EditOp prevRange = position.getRangeOrNullOp();
        assert prevRange.isNotNull() && prevRange.overlaps(range);

        if (!prevRange.doesContain(range)) {
            // merge if no text in between, else chop and append
            assert text.isNullOp() || text.getText() != null;

            if (text.isNullOp() || text.getText() != null && text.getText().isEmpty()) {
                Range expanded = prevRange.expandToInclude(range);
                position.set(EditOp.baseOp(expanded));
                myLength += expanded.getSpan() - prevRange.getSpan();
                if (text.isNotNullOp()) position.remove(1);
            } else {
                // chop off overlap and append
                Range chopped = range.withStart(prevRange.getEnd());
                if (chopped.isNotEmpty()) {
                    position.append(EditOp.baseOp(chopped));
                    myLength += chopped.getSpan();
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
            // have overlap, remove overlap or adjacent from range and add
            SegmentList segmentList = new SegmentList(myParts);
            SegmentPosition position = getLastRangePosition(segmentList);

            if (position.isValidElement() && position.getRangeOrNullOp().isNotNull()) {
                EditOp prevRange = position.getRangeOrNullOp();

                if (prevRange.overlaps(range)) {
                    handleOverlap(position, range);
                    myLastRangeIndex = getLastRangeIndex(true);
                } else if (prevRange.isAdjacentBefore(range)) {
                    // if no text in between merge them, else append
                    EditOp textOp = position.getStringOrNullOp(1);

                    if (textOp.isNullOp() || textOp.getText().isEmpty()) {
                        Range expanded = prevRange.expandToInclude(range);
                        position.set(EditOp.baseOp(expanded));
                        if (textOp.isNotNullOp()) position.remove(1);

                        myEndOffset = expanded.getEnd();
                        myLength += expanded.getSpan() - prevRange.getSpan();
                    } else {
                        // just append
                        myParts.add(EditOp.baseOp(range));
                        myLength += range.getSpan();
                        myEndOffset = range.getEnd();
                        myLastRangeIndex = myParts.size() - 1;
                    }
                } else {
                    // just append
                    myParts.add(EditOp.baseOp(range));
                    myLength += range.getSpan();
                    myEndOffset = range.getEnd();
                    myLastRangeIndex = myParts.size() - 1;
                }
            }
        } else {
            // just append
            myParts.add(EditOp.baseOp(range));
            myLength += range.getSpan();
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
            EditOp lastString = getLastPartIfString();
            if (lastString.isNotNullOp()) {
                myParts.set(myParts.size() - 1, lastString.withSuffix(text));
            } else {
                myParts.add(EditOp.plainText(text));
            }
            myLength += text.length();
        }
        return this;
    }

    @NotNull
    public SegmentBuilder append(@Nullable EditOp part) {
        if (part != null) {
            assert part.isBase() || part.isPlainText();

            if (part.isBase()) append((Range) part);
            else append(part.getText());
        }
        return this;
    }

    @NotNull
    public List<EditOp> getParts() {
        return myParts;
    }

    @NotNull
    public String toStringWithRanges(@NotNull CharSequence chars) {
        BasedSequence baseSequence = BasedSequence.of(chars);

        if (myEndOffset > baseSequence.length()) {
            throw new IllegalArgumentException("baseSequence length() must be at least " + myEndOffset + ", got: " + baseSequence.length());
        }

        StringBuilder out = new StringBuilder();

        for (EditOp part : myParts) {
            if (part.isPlainText()) {
                out.append(BasedSequence.of(part.getText()).toVisibleWhitespaceString());
            } else {
                out.append("⟦").append(baseSequence.subSequence(part.getStart(), part.getEnd()).toVisibleWhitespaceString()).append("⟧");
            }
        }

        return out.toString();
    }

    @NotNull
    public String toString(@NotNull CharSequence chars) {
        BasedSequence baseSequence = BasedSequence.of(chars);

        if (myEndOffset > baseSequence.length()) {
            throw new IllegalArgumentException("baseSequence length() must be at least " + myEndOffset + ", got: " + baseSequence.length());
        }

        StringBuilder out = new StringBuilder();

        for (EditOp part : myParts) {
            if (part.isPlainText()) {
                out.append(BasedSequence.of(part.getText()).toVisibleWhitespaceString());
            } else {
                out.append(baseSequence.subSequence(part.getStart(), part.getEnd()));
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

        int length = computeLength();
        assert myLength == length;
        SegmentList segmentList = new SegmentList(myParts);

        for (SegmentPosition position : segmentList) {
            optimizer.accept(chars, position);
        }

        int length2 = computeLength();
        assert length == length2 : "Optimization should not change length";
        assert myLength == length2;

        updateOffsets();
    }

    @Override
    public String toString() {
        DelimitedBuilder sb = new DelimitedBuilder(", ");
        sb.append("SegmentBuilder{end=").append(myEndOffset).append(", parts=");
        for (EditOp part : myParts) {
            sb.append(part).mark();
        }
        sb.unmark().append(" }");
        return sb.toString();
    }

    @NotNull
    public static SegmentBuilder emptyBuilder() {
        return new SegmentBuilder();
    }
}
