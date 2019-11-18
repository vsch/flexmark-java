package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class SegmentBuilder {
    private SegmentList myParts = new SegmentList();      // contains either Range of original sequence kept, or String inserted at position after the last Range
    private int myEndOffset;
    private long myModificationTimestamp;

    protected SegmentBuilder(@NotNull Range range) {
        if (range.isNotNull()) {
            myParts.add(range);
            myEndOffset = range.getEndOffset();
        }
    }

    protected SegmentBuilder(@NotNull SegmentBuilder other) {
        myParts.addAll(other.myParts);
        myEndOffset = other.myEndOffset;
        myModificationTimestamp = other.myModificationTimestamp;
    }

    public int getEndOffset() {
        return myEndOffset;
    }

    public long getModificationTimestamp() {
        return myModificationTimestamp;
    }

    public boolean isEmpty() {
        return myParts.isEmpty();
    }

    public boolean isNotEmpty() {
        return myParts.isNotEmpty();
    }

    public int length() {
        int length = 0;
        for (Object part : myParts.getList()) {
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
     * @return position of last range or end of list position
     */
    @NotNull
    private SegmentPosition getLastRangeIndex() {
        // should not have to look further than last 2 entries to find a range
        SegmentPosition position = myParts.getLast();
        if (position.getRangeOrNull() != null) return position;
        if (position.getRangeOrNull(-1) != null) return position.previous();
        return myParts.getEnd();
    }

    @Nullable
    public Range getLastPartIfRange() {
        return myParts.getRangeOrNull(myParts.size() - 1);
    }

    @Nullable
    public String getLastPartIfString() {
        return myParts.getStringOrNull(myParts.size() - 1);
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
        if (range == null) return this;

        // NOTE: if an empty range is not inserted then there is no range to attach recovered characters.
        //    empty ranges can be removed during optimization
        if (myEndOffset > 0 && myEndOffset >= range.getStart()) {
            // have overlap, remove overlap from range and add
            SegmentPosition position = getLastRangeIndex();
            if (position.isValidElement()) {
                Range prevRange = position.getRangeOrNull();
                if (prevRange != null) {
                    if (prevRange.overlaps(range)) {
                        handleOverlap(position, range);
                    } else if (prevRange.isAdjacentBefore(range)) {
                        // if no text in between merge them, else append
                        String text = position.getStringOrNull(1);

                        if (text == null || text.isEmpty()) {
                            Range expanded = prevRange.expandToInclude(range);
                            position.set(expanded);
                            if (text != null) position.remove(1);
                        } else {
                            // just append
                            myParts.add(range);
                        }
                    }
                }
            }
        } else {
            // just append
            myParts.add(range);
        }

        updateEndOffset();
        myModificationTimestamp++;
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
            myModificationTimestamp++;
        }
        return this;
    }

    @NotNull
    public SegmentBuilder append(@Nullable Object part) {
        if (part != null) {
            assert part instanceof Range || part instanceof String;
            if (part instanceof Range) append((Range) part);
            else append((String)part);
        }
        return this;
    }

    /**
     * Delete range in edited coordinate system but delete segments in accumulated parts
     *
     * @param startIndex start index in current range
     * @param endIndex   end index in current range
     */
    @NotNull
    public SegmentBuilder deleteByIndices(int startIndex, int endIndex) {
        return delete(Range.of(startIndex, endIndex));
    }

    /**
     * Delete range in edited coordinate system but delete segments in accumulated parts
     *
     * @param startIndex start index in current range
     * @param count      end index in current range
     */
    @NotNull
    public SegmentBuilder deleteByLength(int startIndex, int count) {
        return delete(Range.of(startIndex, startIndex + count));
    }

    /**
     * Delete range in edited coordinate system but delete segments in accumulated parts
     *
     * @param range range to delete in current range coordinates
     */
    @NotNull
    public SegmentBuilder delete(@NotNull Range range) {
        int startOffset = 0;
        Range remaining = range;

        for (SegmentPosition position : myParts) {
            Range asRange = position.getRangeOrNull();
            String asString = position.getStringOrNull();

            assert asRange != null || asString != null;

            Range partRange = asRange != null ? Range.ofLength(startOffset, asRange.length()) : Range.ofLength(startOffset, asString.length());

            Range intersect = partRange.intersect(remaining);
            if (intersect.isNotEmpty()) {
                if (remaining.doesContain(partRange)) {
                    // fully deleted
                    deleteParts(position);
                    remaining = Range.of(startOffset, remaining.getEnd() - partRange.getSpan());
                } else {
                    if (partRange.doesProperlyContain(remaining)) {
                        // middle chopped out leaving two pieces
                        if (asRange != null) {
                            int delta = partRange.getEnd() - remaining.getEnd();
                            position.set(asRange.withEnd(asRange.getStart() + delta));
                            position.add(1, asRange.withStart(asRange.getEnd() - delta));
                        } else {
                            Range removedString = intersect.shiftLeft(startOffset);
                            position.set(asString.substring(0, removedString.getStart()) + asString.substring(removedString.getEnd()));
                        }
                        break;
                    } else {
                        if (partRange.getStart() == intersect.getStart()) {
                            // head part removed
                            if (asRange != null) {
                                Range newRange = asRange.startPlus(intersect.getSpan());
                                position.set(newRange);
                                startOffset += newRange.length();
                            } else {
                                String newString = asString.substring(intersect.getSpan());
                                position.set(newString);
                                startOffset += newString.length();
                            }
                        } else {
                            // tail part removed
                            assert partRange.getEnd() == intersect.getEnd();
                            if (asRange != null) {
                                Range newRange = asRange.endMinus(intersect.getSpan());
                                position.set(newRange);
                                startOffset += newRange.length();
                            } else {
                                String newString = asString.substring(0, asString.length() - intersect.getSpan());
                                position.set(newString);
                                startOffset += newString.length();
                            }
                        }

                        // adjust remaining end by amount which was deleted since now the coordinates have changed by that amount
                        remaining = Range.of(startOffset, remaining.getEnd() - intersect.getSpan());
                    }
                }
            } else {
                startOffset += partRange.getSpan();
            }

            if (remaining.getEnd() <= startOffset) break;
        }

        updateEndOffset();
        myModificationTimestamp++;

        return this;
    }

    protected void updateEndOffset() {
        SegmentPosition position = getLastRangeIndex();
        myEndOffset = position.getRange().getEnd();
    }

    private void deleteParts(SegmentPosition position) {
        // check if there is overlap in spliced region
        int count = 1;
        Object part1 = position.getOrNull(-1);
        Object part2 = position.getOrNull(count);
        if (part1 instanceof Range && part2 instanceof Range) {
            if (((Range) part1).getEnd() == ((Range) part2).getStart()) {
                count++;
                position.set(-1, ((Range) part1).withEnd(((Range) part2).getEnd()));
            }
        } else if (part1 instanceof String && part2 instanceof String) {
            count++;
            position.set(-1, part1.toString() + part2);
        }

        position.remove(0, count);
    }

    /**
     * Insert string at given coordinates
     *
     * @param atIndex index in the current range where to insert
     * @param text    string to insert
     * @return this
     */
    public SegmentBuilder insert(int atIndex, @NotNull String text) {
        int startOffset = 0;

        for (SegmentPosition position : myParts) {
            Object part = position.get();
            Range partRange = part instanceof Range ? Range.of(startOffset, startOffset + ((Range) part).length()) : Range.of(startOffset, startOffset + ((String) part).length());

            if (partRange.isValidIndex(atIndex)) {
                // inside the part
                if (partRange.getStart() == atIndex) {
                    if (part instanceof String) {
                        // combine and replace
                        position.set(text + part);
                    } else {
                        // insert before
                        position.add(text);
                    }
                } else if (partRange.getEnd() == atIndex) {
                    if (part instanceof String) {
                        // combine and replace
                        position.set(part + text);
                    } else {
                        // insert after
                        position.add(1, text);
                    }
                } else {
                    // insert in the middle
                    if (part instanceof String) {
                        // combine by inserting in middle and replace
                        position.set(((String) part).substring(0, atIndex) + text + ((String) part).substring(atIndex));
                    } else {
                        Range range = (Range) part;
                        // split range into two and insert text between them
                        position.set(range.withEnd(range.getEnd() - (partRange.getEnd() - atIndex)));
                        position.add(1, text);

                        Range tail = range.withStart(range.getStart() + (partRange.getEnd() - atIndex));
                        position.add(2, tail);
                    }
                }
                // KLUDGE: to mark as having been inserted
                startOffset = -1;
                break;
            } else {
                startOffset += partRange.getSpan();
            }
        }

        if (startOffset != -1) {
            // append to the end
            myParts.add(text);
        }
        myModificationTimestamp++;
        return this;
    }

    @NotNull
    public List<Object> getParts() {
        return myParts.getList();
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

        for (Object part : myParts.getList()) {
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

        for (SegmentPosition position : myParts) {
            if (position.getStringOrNull() == null) continue;

            optimizer.accept(chars, position);
        }

        Range range = myParts.getLast().getRangeOrNull();
        myEndOffset = range == null ? 0 : range.getEnd();
        myModificationTimestamp++;
    }

    @Override
    public String toString() {
        DelimitedBuilder sb = new DelimitedBuilder(", ");
        sb.append("SegmentBuilder{end=").append(myEndOffset).append(", parts=");
        for (Object part : myParts.getList()) {
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
        return new SegmentBuilder(Range.NULL);
    }

    @NotNull
    public static SegmentBuilder rangeBuilder(@NotNull Range range) {
        return new SegmentBuilder(range);
    }

    @NotNull
    public static SegmentBuilder rangeBuilder(int startOffset, int endOffset) {
        startOffset = Math.max(0, startOffset);
        endOffset = Math.max(startOffset, endOffset);
        return new SegmentBuilder(Range.of(startOffset, endOffset));
    }

    @NotNull
    public static SegmentBuilder sequenceBuilder(@NotNull CharSequence sequence) {
        return new SegmentBuilder(Range.of(0, sequence.length()));
    }

    @NotNull
    public static SegmentBuilder copyBuilder(@NotNull SegmentBuilder other) {
        return new SegmentBuilder(other);
    }
}
