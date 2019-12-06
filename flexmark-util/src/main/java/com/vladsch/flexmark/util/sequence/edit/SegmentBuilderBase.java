package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Supplier;

@SuppressWarnings("UnusedReturnValue")
public class SegmentBuilderBase<S extends SegmentBuilderBase<S>> implements ISegmentBuilder<S> {
    public static final String[] EMPTY_STRINGS = new String[0];

    final protected ArrayList<Seg> myParts = new ArrayList<>();
    protected int myStartOffset = Range.NULL.getStart();
    protected int myEndOffset = Range.NULL.getEnd();
    protected int myLength = 0;
    protected int myTextLength = 0;
    protected int myTextFirst256Segments = 0;
    protected int myTextSpaceLength = 0;
    protected int myTextFirst256Length = 0;
    protected final @Nullable int[] myFirst256;
    final protected int myOptions;

    protected SegmentBuilderBase() {
        this(F_INCLUDE_ANCHORS | F_TRACK_UNIQUE);
    }

    protected SegmentBuilderBase(int options) {
        myOptions = options & (F_INCLUDE_ANCHORS | F_TRACK_UNIQUE);
        myFirst256 = (options & F_TRACK_UNIQUE) != 0 ? new int[256] : null;
    }

    @SuppressWarnings("CopyConstructorMissesField")
    protected SegmentBuilderBase(@NotNull SegmentBuilderBase<S> other) {
        this(other.myOptions);

        myParts.addAll(other.myParts);
        myStartOffset = other.myStartOffset;
        myEndOffset = other.myEndOffset;
        myLength = other.myLength;
        myTextLength = other.myTextLength;
        myTextFirst256Segments = other.myTextFirst256Segments;
        myTextSpaceLength = other.myTextSpaceLength;
        myTextFirst256Length = other.myTextFirst256Length;

        if (myFirst256 != null && other.myFirst256 != null) {
            System.arraycopy(other.myFirst256, 0, myFirst256, 0, other.myFirst256.length);
        }
    }

    @Override
    public int getStartOffset() {
        return myStartOffset >= 0 ? myStartOffset : -1;
    }

    @Override
    public int getEndOffset() {
        return myEndOffset >= 0 ? myEndOffset : -1;
    }

    @Override
    public boolean isEmpty() {
        return myParts.isEmpty();
    }

    @Override
    public int length() {
        assert myLength == computeLength();
        return myLength;
    }

    @Override
    public int size() {
        return myParts.size();
    }

// @formatter:off
    @Override public boolean isTrackTextFirst256() {return (myOptions & F_TRACK_UNIQUE) != 0;}
    @Override public int getTextLength() {return myTextLength;}
    @Override public int getTextSegments() {return 0;}
    @Override public int getTextSpaceLength() {return myFirst256 == null ? 0 : myFirst256[' '];}
    @Override public int getTextSpaceSegments() {return myTextSpaceLength;}
    @Override public int getTextFirst256Segments() {return myTextFirst256Segments;}
    @Override public int getTextFirst256Length() {return myTextFirst256Length;}

// @formatter:on

    @Override
    public @NotNull Iterator<Object> iterator() {
        return new PartsIterator(myParts);
    }

    static class PartsIterator implements Iterator<Object> {
        final ArrayList<Seg> myParts;
        int myNextIndex;

        public PartsIterator(ArrayList<Seg> parts) {
            myParts = parts;
        }

        @Override
        public boolean hasNext() {
            return myNextIndex < myParts.size();
        }

        @Override
        public Object next() {
            Seg seg = myParts.get(myNextIndex);
            myNextIndex++;
            return seg.isTextOrString() ? seg.getText() : seg.getRange();
        }
    }

    @Override
    public int getOptions() {
        return myOptions;
    }

    @Override
    public boolean isIncludeAnchors() {
        return (myOptions & F_INCLUDE_ANCHORS) != 0;
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
    @Override
    public int getSpan() {
        return myStartOffset > myEndOffset ? -1 : myEndOffset - myStartOffset;
    }

    @Override
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
            assert myLength == 0 && myTextLength == 0 && myTextFirst256Length == 0 && myTextFirst256Segments == 0 : assertionError("empty, all lengths = 0", parts);
        } else {
            Seg lastSeg = lastSeg();
            if (lastSeg.isString()) {
                assert myStartOffset == Range.NULL.getStart() && myEndOffset == Range.NULL.getEnd() : assertionError("last seg string, no offsets ", parts);
            } else {
                assert myStartOffset <= firstSeg().getStart() && myEndOffset >= lastSeg().getEnd()
                        : assertionError(String.format("start:%d==first.start:%d, end:%d==last.end:%d ", myStartOffset, firstSeg().getStart(), myEndOffset, lastSeg().getEnd()), parts);
            }
            assert myLength >= myTextLength && myTextLength >= myTextFirst256Length && myTextLength >= myTextFirst256Segments : assertionError("l >= t >= ul", parts);
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
        if (myFirst256 != null) {
            int iMax = text.length();
            for (int i = 0; i < iMax; i++) {
                char c = text.charAt(i);
                if (c < 256) {
                    if (myFirst256[c] == 0) myTextFirst256Segments++;
                    myFirst256[c]++;
                    if (c == ' ') myTextSpaceLength++;
                    myTextFirst256Length++;
                }
            }
        }
    }

    protected void removedText(String text) {
        // need to count spaces in it
        if (myFirst256 != null) {
            int iMax = text.length();
            for (int i = 0; i < iMax; i++) {
                char c = text.charAt(i);
                if (c < 256) {
                    assert myFirst256[c] > 0;
                    myFirst256[c]--;

                    if (myFirst256[c] == 0) myTextFirst256Segments--;

                    if (c == ' ') {
                        assert myTextSpaceLength > 0;
                        myTextSpaceLength--;
                    }

                    assert myTextFirst256Length > 0;
                    myTextFirst256Length--;
                }
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
    @Override
    @NotNull
    public S append(int startOffset, int endOffset) {
        @NotNull Range range = Range.of(startOffset, endOffset);
        return append(range);
    }

    /**
     * append anchor in original sequence coordinates, no checking is done other than overlap with tail range
     * fast
     *
     * @param offset offset in original sequence
     * @return this
     */
    @Override
    @NotNull
    public S appendAnchor(int offset) {
        @NotNull Range range = Range.of(offset, offset);
        return append(range);
    }

    /**
     * append range in original sequence coordinates, no checking is done other than overlap with tail range
     * fast
     *
     * @param range range in original sequence
     * @return this
     */
    @Override
    @NotNull
    public S append(@NotNull Range range) {
        if (range.isNull())
            //noinspection unchecked
            return (S) this;
        int rangeSpan = range.getSpan();
        if (rangeSpan < 0 || rangeSpan == 0 && (!isIncludeAnchors() || range.getStart() < myEndOffset)) {
            if (rangeSpan == 0 && range.getStart() > myEndOffset) updateLastTextSeg(Seg.anchorSeg(range.getEnd()));
            //noinspection unchecked
            return (S) this;
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
        //noinspection unchecked
        return (S) this;
    }

    @Override
    @NotNull
    public S append(CharSequence text) {
        int length = text.length();
        if (length != 0) {
            changeLength(length, true);

            String useText = text.toString();
            Seg lastSeg = lastSeg();
            if (lastSeg.isNull()) {
                myParts.add(Seg.stringSeg(useText));
                addedText(useText);
            } else if (lastSeg.isText()) {
                // append to it
                setLastSeg(lastSeg.withTextSuffix(useText));
                addedText(useText);
            } else {
                // add after last seg as text
                myParts.add(Seg.textSeg(lastSeg.getEnd(), useText));
                addedText(useText);
            }
        }
        //noinspection unchecked
        return (S) this;
    }

    @Override
    @NotNull
    public String toStringWithRangesVisibleWhitespace(@NotNull CharSequence chars) {
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

    @Override
    @NotNull
    public String toStringWithRanges(@NotNull CharSequence chars) {
        BasedSequence baseSequence = BasedSequence.of(chars);

        if (myEndOffset > baseSequence.length()) {
            throw new IllegalArgumentException("baseSequence length() must be at least " + myEndOffset + ", got: " + baseSequence.length());
        }

        StringBuilder out = new StringBuilder();

        for (Seg part : myParts) {
            if (!part.isBase()) {
                out.append(BasedSequence.of(part.getText()));
            } else {
                out.append("⟦").append(baseSequence.subSequence(part.getStart(), part.getEnd())).append("⟧");
            }
        }

        return out.toString();
    }

    @Override
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
            Object frameId = segmentList.openFrame();
            try {
                optimizer.accept(this, chars, position);
            } finally {
                segmentList.closeFrame(frameId);
            }
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

        sb.append("s=").append(this.getTextSpaceLength()).append(":").append(myTextSpaceLength).mark()
                .append("u=").append(myTextFirst256Segments).append(":").append(myTextFirst256Length).mark()
                .append("t=").append(myTextLength).mark()
                .append("l=").append(myLength).mark();

        for (Seg part : myParts) {
            sb.append(part).mark();
        }
        sb.unmark().append(" }");
        return sb.toString();
    }
}
