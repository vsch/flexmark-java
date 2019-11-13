package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class SegmentBuilder {
    public static SegmentBuilder emptyBuilder() {
        return new SegmentBuilder(Range.EMPTY);
    }

    public static SegmentBuilder rangeBuilder(@NotNull Range range) {
        return new SegmentBuilder(range);
    }

    public static SegmentBuilder rangeBuilder(int startOffset, int endOffset) {
        startOffset = Math.max(0, startOffset);
        endOffset = Math.max(startOffset, endOffset);
        return new SegmentBuilder(Range.of(startOffset, endOffset));
    }

    public static SegmentBuilder fullBuilder(@NotNull CharSequence sequence) {
        return new SegmentBuilder(Range.of(0, sequence.length()));
    }

    private ArrayList<Object> myParts = new ArrayList<>();      // contains either Range of original sequence kept, or String inserted at position after the last Range
    private int myEndOffset;

    private SegmentBuilder(@NotNull Range range) {
        myParts.add(range);
        myEndOffset = range.getEndOffset();
    }

    /**
     * append range in original range coordinates, no checking is done other than overlap with tail range
     *
     * @param startOffset start offset
     * @param endOffset   end offset
     */
    @NotNull
    public SegmentBuilder appendRange(int startOffset, int endOffset) {
        return appendRange(Range.of(startOffset, endOffset));
    }

    @Nullable
    public Range getLastRange() {
        Object part = myParts.size() > 0 ? myParts.get(myParts.size() - 1) : null;
        return part instanceof Range ? (Range) part : null;
    }

    @Nullable
    public String getLastString() {
        Object part = myParts.size() > 0 ? myParts.get(myParts.size() - 1) : null;
        return part instanceof String ? (String) part : null;
    }

    /**
     * append range in original sequence coordinates, no checking is done other than overlap with tail range
     * fast
     *
     * @param range in offsets of original range
     */
    @NotNull
    public SegmentBuilder appendRange(@NotNull Range range) {
        if (myEndOffset >= range.getStart()) {
            // have overlap, remove overlap from range and add
            Range lastRange = getLastRange();

            if (lastRange != null) {
                // can combine
                if (lastRange.getEnd() >= range.getStart()) {
                    if (lastRange.getEnd() < range.getEnd()) {
                        myParts.set(myParts.size() - 1, range.withStart(lastRange.getStart()));
                        myEndOffset = range.getEnd();
                    }
                    return this;
                }
            }

            // chop off overlap and append
            Range chopped = range.withStart(myEndOffset);
            if (chopped.isNotEmpty()) {
                myParts.add(chopped);
                myEndOffset = chopped.getEnd();
            }
        } else {
            // just append
            myParts.add(range);
            myEndOffset = range.getEndOffset();
        }
        return this;
    }

    @NotNull
    public SegmentBuilder appendString(@Nullable String text) {
        if (text != null && !text.isEmpty()) {
            String lastString = getLastString();
            if (lastString != null) {
                myParts.set(myParts.size() - 1, lastString + text);
            } else {
                myParts.add(text);
            }
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
        return deleteRange(Range.of(startIndex, endIndex));
    }

    /**
     * Delete range in edited coordinate system but delete segments in accumulated parts
     *
     * @param startIndex start index in current range
     * @param count      end index in current range
     */
    @NotNull
    public SegmentBuilder deleteByLength(int startIndex, int count) {
        return deleteRange(Range.of(startIndex, startIndex + count));
    }

    /**
     * Delete range in edited coordinate system but delete segments in accumulated parts
     *
     * @param range range to delete in current range coordinates
     */
    @NotNull
    public SegmentBuilder deleteRange(@NotNull Range range) {
        int startOffset = 0;
        int endOffset = -1;
        Range remaining = range;
        int firstRangeStart = -1;

        int i = 0;
        int iMax = myParts.size();

        while (i < iMax) {
            Object part = myParts.get(i);

            Range asRange = part instanceof Range ? (Range) part : null;
            String asString = part instanceof String ? (String) part : null;

            assert asRange != null || asString != null;

            Range partRange = asRange != null ? Range.of(startOffset, startOffset + asRange.length()) : Range.of(startOffset, startOffset + asString.length());
            endOffset = startOffset + partRange.length();

            if (asRange != null && firstRangeStart == -1) {
                firstRangeStart = asRange.getStart();
            }

            if (remaining.doesOverlap(partRange)) {
                if (remaining.doesContain(partRange)) {
                    // fully deleted
                    iMax -= deleteParts(i, i + 1);
                    remaining = Range.of(startOffset, remaining.getEnd() - partRange.getSpan());
                    // no change to i, part deleted
                } else {
                    if (partRange.doesProperlyContain(remaining)) {
                        // middle chopped out leaving two pieces
                        if (asRange != null) {
                            myParts.set(i, asRange.withEnd(asRange.getStart() + (partRange.getEnd() - remaining.getEnd())));
                            addPart(i + 1, asRange.withStart(asRange.getEnd() - (partRange.getEnd() - remaining.getEnd())));
                        } else {
                            myParts.set(i, asString.substring(0, remaining.getStart()));
                            addPart(i + 1, asString.substring(remaining.getEnd()));
                        }
                        break;
                    } else {
                        Range intersect = partRange.intersect(remaining);

                        if (partRange.getStart() == intersect.getStart()) {
                            // head part removed
                            if (asRange != null) {
                                Range newRange = asRange.withStart(asRange.getStart() + intersect.getSpan());
                                myParts.set(i, newRange);
                                startOffset += newRange.length();
                            } else {
                                String newString = asString.substring(intersect.getSpan());
                                myParts.set(i, newString);
                                startOffset += newString.length();
                            }
                        } else {
                            // tail part removed
                            assert partRange.getEnd() == intersect.getEnd();
                            if (asRange != null) {
                                Range newRange = asRange.withEnd(asRange.getEnd() - intersect.getSpan());
                                myParts.set(i, newRange);
                                startOffset += newRange.length();
                            } else {
                                String newString = asString.substring(0, intersect.getStart());
                                myParts.set(i, newString);
                                startOffset += newString.length();
                            }
                        }

                        // adjust remaining end by amount which was deleted since now the coordinates have changed by that amount
                        remaining = Range.of(startOffset, remaining.getEnd() - intersect.getSpan());
                        endOffset = startOffset;
                        i++;
                    }
                }
            } else {
                startOffset += partRange.getSpan();
                i++;
            }

            if (remaining.getEnd() <= startOffset) break;
        }

        if (myParts.isEmpty()) {
            // add empty range of the end offset
            startOffset = Math.max(0, firstRangeStart);
            myParts.add(Range.of(startOffset, startOffset));
        }
        return this;
    }

    private void addPart(int index, Object part) {
        if (index == myParts.size()) {
            myParts.add(part);
        } else {
            myParts.add(index, part);
        }
    }

    private int deleteParts(int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            if (startIndex > 0 && endIndex < myParts.size()) {
                // check if there is overlap in spliced region
                Object part1 = myParts.get(startIndex - 1);
                Object part2 = myParts.get(endIndex);
                if (part1 instanceof Range && part2 instanceof Range) {
                    if (((Range) part1).getEnd() == ((Range) part2).getStart()) {
                        endIndex++;
                        myParts.set(startIndex - 1, ((Range) part1).withEnd(((Range) part2).getEnd()));
                    }
                } else if (part1 instanceof String && part2 instanceof String) {
                    endIndex++;
                    myParts.set(startIndex - 1, part1.toString() + part2);
                }
            }

            if (startIndex < endIndex) {
                myParts.subList(startIndex, endIndex).clear();
                return endIndex - startIndex;
            }
        }
        return 0;
    }

    /**
     * Insert string at given coordinates
     *
     * @param atIndex index in the current range where to insert
     * @param text    string to insert
     * @return this
     */
    public SegmentBuilder insertString(int atIndex, @NotNull String text) {
        int startOffset = 0;

        int iMax = myParts.size();
        for (int i = 0; i < iMax; i++) {
            Object part = myParts.get(i);
            Range partRange = part instanceof Range ? Range.of(startOffset, startOffset + ((Range) part).length()) : Range.of(startOffset, startOffset + ((String) part).length());

            if (partRange.isValidIndex(atIndex)) {
                // inside the part
                if (partRange.getStart() == atIndex) {
                    if (part instanceof String) {
                        // combine and replace
                        myParts.set(i, text + part);
                    } else {
                        // insert before
                        myParts.add(i, text);
                    }
                } else if (partRange.getEnd() == atIndex) {
                    if (part instanceof String) {
                        // combine and replace
                        myParts.set(i, part + text);
                    } else {
                        // insert after
                        if (i + 1 == iMax) myParts.add(text);
                        else myParts.add(i + 1, text);
                    }
                } else {
                    // insert in the middle
                    if (part instanceof String) {
                        // combine by inserting in middle and replace
                        myParts.set(i, ((String) part).substring(0, atIndex) + text + ((String) part).substring(atIndex));
                    } else {
                        Range range = (Range) part;
                        // split range into two and insert text between them
                        myParts.set(i, range.withEnd(range.getEnd() - (partRange.getEnd() - atIndex)));
                        if (i + 1 == myParts.size()) myParts.add(text);
                        else myParts.add(i + 1, text);

                        Range tail = range.withStart(range.getStart() + (partRange.getEnd() - atIndex));
                        if (i + 2 == myParts.size()) myParts.add(tail);
                        else myParts.add(i + 1, tail);
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

        for (Object part : myParts) {
            if (part instanceof String) {
                builder.add((String) part);
            } else {
                assert part instanceof Range;
                builder.add(baseSequence.subSequence(((Range) part).getStart(), ((Range) part).getEnd()));
            }
        }
    }

    public <T extends SequenceBuilder<T, S>, S extends IRichSequence<S>> S toSequence(@NotNull S baseSequence, @NotNull T builder) {
        buildSequence(baseSequence, builder);
        return builder.toSequence();
    }

    public <T extends SequenceBuilder<T, S>, S extends IRichSequence<S>> String toString(@NotNull S baseSequence, @NotNull T builder) {
        buildSequence(baseSequence, builder);
        return builder.toString();
    }

    @Override
    public String toString() {
        DelimitedBuilder sb = new DelimitedBuilder(", ");
        sb.append("SegmentBuilder{endOffset=").append(myEndOffset).append(", parts=");
        for (Object part : myParts) {
            if (part instanceof Range) {
                sb.append(part).mark();
            } else {
                sb.append("'").append(part).append("'").mark();
            }
        }
        sb.unmark().append(" }");
        return sb.toString();
    }
}
