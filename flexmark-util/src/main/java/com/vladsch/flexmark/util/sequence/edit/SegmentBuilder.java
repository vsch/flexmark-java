package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Supplier;

@SuppressWarnings("UnusedReturnValue")
public class SegmentBuilder {
    public static final String[] EMPTY_STRINGS = new String[0];
    protected ArrayList<Seg> myParts = new ArrayList<>();
    protected int myStartOffset = Range.NULL.getStart();
    protected int myEndOffset = Range.NULL.getEnd();
    protected int myLength = 0;
    protected int myTextLength = 0;
    protected int myTextUnique = 0;
    protected int myTextSpaceLength = 0;
    protected int myTextUniqueLength = 0;
    protected final int[] myFirst256 = new int[256];

    protected SegmentBuilder() {

    }

    protected SegmentBuilder(@NotNull SegmentBuilder other) {
        myParts.addAll(other.myParts);
        myStartOffset = other.myStartOffset;
        myEndOffset = other.myEndOffset;
        myLength = other.myLength;
        myTextLength = other.myTextLength;
        myTextUnique = other.myTextUnique;
        myTextSpaceLength = other.myTextSpaceLength;
        myTextUniqueLength = other.myTextUniqueLength;
        System.arraycopy(other.myFirst256, 0, myFirst256, 0, other.myFirst256.length);
    }

    public int getStartOffset() {
        return myStartOffset >= 0 ? myStartOffset : -1;
    }

    public int getEndOffset() {
        return myEndOffset >= 0 ? myEndOffset : -1;
    }

    public boolean isEmpty() {
        return myParts.isEmpty();
    }

    public int length() {
        assert myLength == computeLength();
        return myLength;
    }

    public int getLength() {
        return myLength;
    }

    public int getTextLength() {
        return myTextLength;
    }

    public int getTextSpaces() {
        return myFirst256[' '];
    }

    public int getTextSpaceLength() {
        return myTextSpaceLength;
    }

    public int getTextUnique() {
        return myTextUnique;
    }

    public int getTextUniqueLength() {
        return myTextUniqueLength;
    }

    public int[] getFirst256() {
        return myFirst256;
    }

    @NotNull
    public ArrayList<Seg> getParts() {
        return myParts;
    }

    /**
     * Span for offsets of this list
     *
     * @return -ve if no information in the list, or span of offsets
     */
    public int getSpan() {
        return myStartOffset > myEndOffset ? -1 : myEndOffset - myStartOffset;
    }

    public boolean hasOffsets() {
        return myStartOffset <= myEndOffset;
    }

    @NotNull
    public Seg firstSeg() {
        return myParts.isEmpty() ? Seg.NULL : myParts.get(0);
    }

    @Nullable
    public Seg firstSegOrNull() {
        return myParts.isEmpty() ? null : myParts.get(0);
    }

    @NotNull
    public Seg lastSeg() {
        return myParts.isEmpty() ? Seg.NULL : myParts.get(myParts.size() - 1);
    }

    @Nullable
    public Seg lastSegOrNull() {
        return myParts.isEmpty() ? null : myParts.get(myParts.size() - 1);
    }

    @NotNull
    public Seg lastSeg(int offsetFromLast) {
        assert offsetFromLast >= 0;
        return myParts.size() <= offsetFromLast ? Seg.NULL : myParts.get(myParts.size() - 1 - offsetFromLast);
    }

    @Nullable
    public Seg lastSegOrNull(int offsetFromLast) {
        assert offsetFromLast >= 0;
        return myParts.size() <= offsetFromLast ? null : myParts.get(myParts.size() - 1 - offsetFromLast);
    }

    private int computeLength() {
        int length = 0;

        for (Seg part : myParts) {
            length += part.length();
        }
        return length;
    }

    private int computeTextLength() {
        int length = 0;

        for (Seg part : myParts) {
            if (part.isText()) {
                length += part.length();
            }
        }
        return length;
    }

    protected String assertionError(String message, @Nullable Supplier<String[]> partSupplier) {
        String[] parts = partSupplier == null ? EMPTY_STRINGS : partSupplier.get();
        if (parts.length > 0) {
            // must be optimizer screw up
            StringBuilder sb = new StringBuilder();
            sb.append(message);
            if (!message.endsWith("\n")) sb.append("\n");
            for (String part : parts) sb.append(part).append("\n");
            return sb.toString();
        } else {
            return "Invariants violated: " + message + toString();
        }
    }

    private void validateInvariants(@Nullable Supplier<String[]> parts) {
        if (myParts.isEmpty()) {
            assert myStartOffset == myEndOffset || myStartOffset == Range.NULL.getStart() && myEndOffset == Range.NULL.getEnd() : assertionError("empty, no offsets, ", parts);
            assert myLength == 0 && myTextLength == 0 && myTextUniqueLength == 0 && myTextUnique == 0 : assertionError("empty, all lengths = 0", parts);
        } else {
            Seg lastSeg = lastSeg();
            if (lastSeg.isString()) {
                assert myStartOffset == Range.NULL.getStart() && myEndOffset == Range.NULL.getEnd() : assertionError("last seg string, no offsets ", parts);
            } else {
                assert myStartOffset <= firstSeg().getStart() && myEndOffset >= lastSeg().getEnd()
                        : assertionError(String.format("start:%d==first.start:%d, end:%d==last.end:%d ", myStartOffset, firstSeg().getStart(), myEndOffset, lastSeg().getEnd()), parts);
            }
            assert myLength >= myTextLength && myTextLength >= myTextUniqueLength && myTextLength >= myTextUnique : assertionError("l >= t >= ul", parts);
        }
    }

    protected void setLastSeg(@NotNull Seg seg) {
        assert !myParts.isEmpty();

        if (!seg.isNullRange()) updateOffsets(seg);
        myParts.set(myParts.size() - 1, seg);
    }

    protected void updateOffsets(@NotNull Seg seg) {
        if (!seg.isNullRange()) {
            myStartOffset = Math.min(myStartOffset, seg.getStart());
            myEndOffset = Math.max(myEndOffset, seg.getEnd());
        }
    }

    protected void updateLastTextSeg(@NotNull Seg seg) {
        assert !seg.isNullRange();

        updateOffsets(seg);

        Seg lastSeg = lastSeg();
        if (lastSeg.isTextOrString()) {
            assert !seg.isText();

            if (lastSeg.isString()) {
                setLastSeg(lastSeg.withRange(seg.getStart(), seg.getEnd()));
            } else {
                setLastSeg(lastSeg.withEnd(seg.getStart()));
            }
        }
    }

    protected void addBaseSeg(@NotNull Seg seg) {
        assert !seg.isNull() && !seg.isText();

        if (!seg.isNullRange()) {
            updateLastTextSeg(seg);
        }

        myParts.add(seg);
        myLength += seg.length();
    }

    protected void changeLength(int delta, boolean withTextLength) {
        myLength += delta;
        if (withTextLength) myTextLength += delta;
    }

    protected void changeTextLength(int delta) {
        myTextLength += delta;
    }

    protected void addedText(String text) {
        // need to count spaces in it
        int iMax = text.length();
        for (int i = 0; i < iMax; i++) {
            char c = text.charAt(i);
            if (c < 256) {
                if (myFirst256[c] == 0) myTextUnique++;
                myFirst256[c]++;
                if (c == ' ') myTextSpaceLength++;
                myTextUniqueLength++;
            }
        }
    }

    protected void removedText(String text) {
        // need to count spaces in it
        int iMax = text.length();
        for (int i = 0; i < iMax; i++) {
            char c = text.charAt(i);
            if (c < 256) {
                assert myFirst256[c] > 0;
                myFirst256[c]--;

                if (myFirst256[c] == 0) myTextUnique--;

                if (c == ' ') {
                    assert myTextSpaceLength > 0;
                    myTextSpaceLength--;
                }

                assert myTextUniqueLength > 0;
                myTextUniqueLength--;
            }
        }
    }

    public void handleOverlap(@NotNull Range range) {
        // range overlaps with last segment in the list
        Seg lastSeg = lastSeg();
        assert !lastSeg.isNullRange() && lastSeg.getEnd() > range.getStart();

        if (lastSeg.getEnd() < range.getEnd()) {
            // there is a part of the overlap outside the last seg range
            if (lastSeg.isText()) {
                // append the chopped off base part
                addBaseSeg(Seg.baseSeg(lastSeg.getEnd(), range.getEnd()));
            } else {
                // extend the last base seg to include this range
                setLastSeg(lastSeg.withEnd(range.getEnd()));
                changeLength(range.getEnd() - lastSeg.getEnd(), false);
            }
        }
    }

    /**
     * append range in original sequence coordinates, no checking is done other than overlap with tail range
     * fast
     *
     * @param startOffset start offset in original sequence
     * @param endOffset   end offset in original sequence
     * @return this
     */
    @NotNull
    public SegmentBuilder append(int startOffset, int endOffset) {
        @NotNull Range range = Range.of(startOffset, endOffset);
        return append(range);
    }

    /**
     * append range in original sequence coordinates, no checking is done other than overlap with tail range
     * fast
     *
     * @param range range in original sequence
     * @return this
     */
    @NotNull
    public SegmentBuilder append(@NotNull Range range) {
        if (range.isNull()) return this;
        if (range.getSpan() <= 0) {
            if (range.getSpan() == 0) updateLastTextSeg(Seg.anchorSeg(range.getEnd()));
            return this;
        }

        if (myEndOffset > range.getStart()) {
            // overlap
            handleOverlap(range);
            validateInvariants(() -> new String[] { "After " + toString() });
        } else if (myEndOffset == range.getStart()) {
            // adjacent, merge the two
            Seg lastSeg = lastSeg();
            if (lastSeg.isBase()) {
                setLastSeg(lastSeg.withEnd(range.getEnd()));
                changeLength(range.getEnd() - lastSeg.getEnd(), false);
            } else if (!lastSeg.isNullRange()) {
                addBaseSeg(Seg.baseSeg(range.withStart(lastSeg.getEnd())));
            } else {
                addBaseSeg(Seg.baseSeg(range));
            }
        } else {
            // disjoint
            addBaseSeg(Seg.baseSeg(range));
        }
        return this;
    }

    @NotNull
    public SegmentBuilder append(@NotNull String text) {
        int length = text.length();
        if (length != 0) {
            changeLength(length, true);

            Seg lastSeg = lastSeg();
            if (lastSeg.isNull()) {
                myParts.add(Seg.stringSeg(text));
                addedText(text);
            } else if (lastSeg.isText()) {
                // append to it
                setLastSeg(lastSeg.withTextSuffix(text));
                addedText(text);
            } else {
                // add after last seg as text
                myParts.add(Seg.textSeg(lastSeg.getEnd(), text));
                addedText(text);
            }
        }
        return this;
    }

    @NotNull
    public String toStringWithRanges(@NotNull CharSequence chars) {
        BasedSequence baseSequence = BasedSequence.of(chars);

        if (myEndOffset > baseSequence.length()) {
            throw new IllegalArgumentException("baseSequence length() must be at least " + myEndOffset + ", got: " + baseSequence.length());
        }

        StringBuilder out = new StringBuilder();

        for (Seg part : myParts) {
            if (!part.isBase()) {
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

        for (Seg part : myParts) {
            if (!part.isBase()) {
                out.append(BasedSequence.of(part.getText()));
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

        // RELEASE: remove debug code
        String before = toString();

        for (SegmentPosition position : segmentList) {
            optimizer.accept(this, chars, position);
        }

        // RELEASE: remove debug code
        String after = toString();
        int length2 = computeLength();
        Supplier<String[]> supplier = () -> new String[] { "Before: " + before, " After: " + after };
        assert length == length2 : assertionError("Optimization should not change length.", supplier);
        assert myLength == length2 : assertionError("Optimization should not change length.", supplier);
        validateInvariants(supplier);
    }

    @Override
    public String toString() {
        DelimitedBuilder sb = new DelimitedBuilder(", ");
        sb.append(this.getClass().getSimpleName()).append("{");

        if (hasOffsets()) {
            sb.append("[").append(myStartOffset).mark()
                    .append(myEndOffset).unmark()
                    .append(")").mark();
        } else {
            sb.append("NULL").mark();
        }

        sb.append("s=").append(getTextSpaces()).append(":").append(myTextSpaceLength).mark()
                .append("u=").append(myTextUnique).append(":").append(myTextUniqueLength).mark()
                .append("t=").append(myTextLength).mark()
                .append("l=").append(myLength).mark();

        for (Seg part : myParts) {
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
