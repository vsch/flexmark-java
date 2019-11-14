package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class SegmentBuilder {
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

    private ArrayList<Object> myParts = new ArrayList<>();      // contains either Range of original sequence kept, or String inserted at position after the last Range
    private int myEndOffset;

    private SegmentBuilder(@NotNull Range range) {
        if (range.isNotNull()) {
            myParts.add(range);
            myEndOffset = range.getEndOffset();
        }
    }

    private SegmentBuilder(@NotNull SegmentBuilder other) {
        myParts.addAll(other.myParts);
        myEndOffset = other.myEndOffset;
    }

    public int getEndOffset() {
        return myEndOffset;
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

    /**
     * @return index of last range or -1
     */
    private int getLastRangeIndex() {
        int iMax = myParts.size();
        int iLast = Math.max(0, iMax - 2);

        // should not have to look further than last 2 entries to find a range
        for (int i = iMax; i-- > iLast; ) {
            Object part = myParts.get(i);
            if (part instanceof Range) return i;
        }
        return -1;
    }

    @Nullable
    public Range getLastPartIfRange() {
        Object part = myParts.size() > 0 ? myParts.get(myParts.size() - 1) : null;
        return part instanceof Range ? (Range) part : null;
    }

    @Nullable
    public String getLastPartIfString() {
        Object part = myParts.size() > 0 ? myParts.get(myParts.size() - 1) : null;
        return part instanceof String ? (String) part : null;
    }

    @NotNull
    public SegmentParams handleOverlap(@NotNull SegmentParams overlap) {
        // default chops off overlapped range
        return overlap;
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
            int lastRangeIndex = getLastRangeIndex();
            if (lastRangeIndex != -1) {
                Range lastRange = (Range) myParts.get(lastRangeIndex);
                // NOTE: one after the last range should be String or nothing, if it was a Range then it would be the last one
                String text = lastRangeIndex + 1 < myParts.size() ? (String) myParts.get(lastRangeIndex + 1) : null;

                // can handle this
                SegmentParams params = SegmentParams.of(lastRange, text == null ? "" : text, range);
                SegmentParams fixedOverlap = handleOverlap(params);

                Range fixedLastRange = fixedOverlap.prevRange;
                String fixedText = fixedOverlap.text;
                Range fixedRange = fixedOverlap.nextRange;

                // if not fixed then we fix it by chopping it off here
                if (fixedLastRange != null && fixedRange != null) {
                    //noinspection StatementWithEmptyBody
                    if (fixedLastRange.overlapsOrAdjacent(fixedRange)) {
                        //noinspection StatementWithEmptyBody
                        if (!fixedLastRange.doesContain(fixedRange)) {
                            // merge if no text in between
                            if (text == null || text.isEmpty()) {
                                Range expanded = fixedLastRange.expandToInclude(fixedRange);
                                myParts.set(lastRangeIndex, expanded);
                                if (text != null) myParts.remove(lastRangeIndex + 1);
                                myEndOffset = expanded.getEnd();
                            } else {
                                // chop off overlap and append
                                Range chopped = fixedRange.withStart(fixedLastRange.getEnd());
                                if (chopped.isNotEmpty()) {
                                    myParts.add(chopped);
                                    myEndOffset = chopped.getEnd();
                                }
                            }
                        } else {
                            // fully contains added range, ignore
                        }
                        return this;
                    } else {
                        // fixed
                    }
                }

                // at least one range should remain otherwise handler attempted to change history, not resolve overlap
                assert fixedLastRange != null || fixedRange != null : "One of the fixed overlap ranges should not be null";

                // NOTE: first set and add, then remove in reverse index order
                // replace previous range
                if (fixedLastRange != null) myParts.set(lastRangeIndex, fixedLastRange);

                // insert text if it did not exist and is not empty
                if (text == null && !fixedText.isEmpty()) myParts.add(fixedText);

                // append fixed range if it is not null
                if (fixedRange != null) myParts.add(fixedRange);

                // remove text if became empty
                if (text != null && fixedText.isEmpty()) myParts.remove(lastRangeIndex + 1);

                // remove last range if it became null
                if (fixedLastRange == null) myParts.remove(lastRangeIndex);

                myEndOffset = fixedRange != null ? fixedRange.getEnd() : fixedLastRange.getEnd();
            } else {
                // WTF, if no last range then no overlap but an error in myEndOffset caching
                myParts.add(range);
                myEndOffset = range.getEndOffset();
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
            String lastString = getLastPartIfString();
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

            Range intersect = partRange.intersect(remaining);
            if (intersect.isNotEmpty()) {
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
                            myParts.set(i, asString.substring(0, intersect.getStart() - startOffset) + asString.substring(intersect.getEnd() - startOffset));
                        }
                        break;
                    } else {
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
                                String newString = asString.substring(0, asString.length() - intersect.getSpan());
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

        updateEndOffset();

        return this;
    }

    private void updateEndOffset() {
        int lastRangeIndex = getLastRangeIndex();
        myEndOffset = lastRangeIndex == -1 ? 0 : ((Range) myParts.get(lastRangeIndex)).getEnd();
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

    /**
     * Extend ranges to include sequence chars which match adjacent inserted characters, effectively removing redundant inserts
     *
     * @param chars sequence for which to optimize the ranges
     * @param <S>   type of rich sequence
     * @deprecated do not use, old code only for testing, will be deleted before release 0.60.0
     */
    @Deprecated
    public <S extends IRichSequence<S>> void optimizeFor(@NotNull S chars) {
        if (myEndOffset > chars.length()) {
            throw new IllegalArgumentException("baseSequence length() must be at least " + myEndOffset + ", got: " + chars.length());
        }

        int iMax = myParts.size();
        int i = 0;
        while (i < iMax) {
            Object part = myParts.get(i);

            if (part instanceof String) {
                String asString = (String) part;
                Range asPrev = i > 0 ? (Range) myParts.get(i - 1) : null;
                Range asNext = i + 1 < iMax ? (Range) myParts.get(i + 1) : null;

                if (asPrev != null && asNext != null) {
                    Range newPrevRange = null;
                    Range newNextRange = null;
                    String newString = asString;

                    int matched = chars.matchedCharCount(asString, asPrev.getEnd(), false);
                    if (matched > 0) {
                        newPrevRange = asPrev.withEnd(asPrev.getEnd() + matched);
                        newString = asString.substring(matched);
                    }

                    int matchNext = chars.matchedCharCountReversed(newString, asNext.getStart(), false);
                    if (matchNext > 0) {
                        // chop string and extend range
                        newNextRange = asNext.withStart(asNext.getStart() - matchNext);
                        newString = newString.substring(0, newString.length() - matchNext);
                    }

                    if (newPrevRange != null && newNextRange != null) {
                        if (newString.isEmpty()) {
                            // remove the string and next range
                            if (newPrevRange.isAdjacentBefore(newNextRange)) {
                                // this one is not possible, if they are adjacent after remainder matched then the full string would extend the previous range
                                myParts.set(i - 1, newPrevRange.expandToInclude(newNextRange));
                                myParts.subList(i, i + 2).clear();
                                iMax -= 2;
                                continue;
                            }
                        }

                        // add remainder of string and two ranges
                        myParts.set(i - 1, newPrevRange);
                        myParts.set(i, newString);
                        myParts.set(i + 1, newNextRange);
                    } else if (newPrevRange != null) {
                        if (newString.isEmpty()) {
                            // remove the string and next range
                            if (newPrevRange.isAdjacentBefore(asNext)) {
                                myParts.set(i - 1, newPrevRange.expandToInclude(asNext));
                                myParts.subList(i, i + 2).clear();
                                iMax -= 2;
                                continue;
                            }
                        }

                        // add remainder of string
                        myParts.set(i - 1, newPrevRange);
                        myParts.set(i, newString);
                    } else if (newNextRange != null) {
                        if (newString.isEmpty()) {
                            // remove the string and next range
                            if (newNextRange.isAdjacentBefore(asPrev)) {
                                myParts.set(i + 1, newNextRange.expandToInclude(asPrev));
                                myParts.subList(i - 1, i + 1).clear();
                                iMax -= 2;
                                continue;
                            }
                        }

                        // add remainder of string
                        myParts.set(i, newString);
                        myParts.set(i + 1, newNextRange);
                    }
                } else if (asPrev != null) {
                    // see if start of string matches adjacent chars after range
                    int matched = chars.matchedCharCount(asString, asPrev.getEnd(), false);
                    if (matched > 0) {
                        // chop string and extend range
                        Range newRange = asPrev.withEnd(asPrev.getEnd() + matched);
                        String newString = asString.substring(matched);
                        myParts.set(i, newString);
                        myParts.set(i - 1, newRange);
                    }
                } else if (asNext != null) {
                    // see if start of string matches adjacent chars after range
                    int matched = chars.matchedCharCountReversed(asString, asNext.getStart(), false);
                    if (matched > 0) {
                        // chop string and extend range
                        Range newRange = asNext.withStart(asNext.getStart() - matched);
                        String newString = asString.substring(0, asString.length() - matched);
                        myParts.set(i, newString);
                        myParts.set(i + 1, newRange);
                    }
                }

                // skip next range
                i += 2;
            } else {
                i++;
            }
        }
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

        int iMax = myParts.size();
        int i = 0;
        while (i < iMax) {
            Object part = myParts.get(i);

            if (part instanceof String) {
                String asString = (String) part;
                Range asPrev = i > 0 ? (Range) myParts.get(i - 1) : null;
                Range asNext = i + 1 < iMax ? (Range) myParts.get(i + 1) : null;

                SegmentParams params = SegmentParams.of(asPrev, asString, asNext);
                SegmentParams optParams = optimizer.apply(chars, params);

                if (!params.equals(optParams)) {
                    // have changes
                    Range newPrevRange = optParams.prevRange;
                    Range newNextRange = optParams.nextRange;
                    String newString = optParams.text;

                    if (newString.isEmpty()) {
                        // see if string and params need deletion
                        if (params.nextRange != null && optParams.nextRange == null) {
                            assert optParams.prevRange != null;

                            // delete string and next range
                            myParts.set(i - 1, newPrevRange);
                            myParts.subList(i, i + 2).clear();
                            iMax -= 2;
                            continue;
                        } else if (params.prevRange != null && optParams.prevRange == null) {
                            assert optParams.nextRange != null;

                            // delete previous and string
                            myParts.set(i + 1, newNextRange);
                            myParts.subList(i - 1, i + 1).clear();
                            iMax -= 2;
                            continue;
                        }
                    } else {
                        if (params.nextRange != null && optParams.nextRange == null) {
                            // delete next range
                            myParts.set(i - 1, newPrevRange);
                            myParts.set(i, newString);
                            myParts.subList(i + 1, i + 2).clear();
                            iMax -= 1;
                            i++;
                            continue;
                        } else if (params.prevRange != null && optParams.prevRange == null) {
                            // delete previous range
                            myParts.set(i + 1, newNextRange);
                            myParts.set(i, newString);
                            myParts.subList(i - 1, i).clear();
                            iMax -= 1;
                            i++;
                            continue;
                        }
                    }

                    assert (params.nextRange != null) == (optParams.nextRange != null);
                    assert (params.prevRange != null) == (optParams.prevRange != null);

                    // add remainder of string and two ranges
                    if (newPrevRange != null) myParts.set(i - 1, newPrevRange);
                    if (newNextRange != null) myParts.set(i + 1, newNextRange);

                    if (newString.isEmpty()) {
                        myParts.remove(i);
                        iMax--;
                        continue;
                    } else {
                        myParts.set(i, newString);
                    }
                }

                // OPTIMIZE: if next range is not null skip it automatically
                i += 1;
            } else {
                i++;
            }
        }
    }

    @Override
    public String toString() {
        DelimitedBuilder sb = new DelimitedBuilder(", ");
        sb.append("SegmentBuilder{endOffset=").append(myEndOffset).append(", parts=");
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
}
