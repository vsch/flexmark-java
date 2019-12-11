package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

@SuppressWarnings("UnusedReturnValue")
public class SegmentBuilderBase<S extends SegmentBuilderBase<S>> implements ISegmentBuilder<S> {
    public static final int MIN_PART_CAPACITY = 8;

    final public static int[] EMPTY_PARTS = { };

    protected @NotNull int[] myParts = EMPTY_PARTS;
    protected int myPartsSize = 0;
    protected int myAnchorsSize = 0;

    protected int myStartOffset = Range.NULL.getStart();
    protected int myEndOffset = Range.NULL.getEnd();
    protected int myLength = 0;
    final protected SegmentStats myStats;      // committed and dangling text stats
    final protected SegmentStats myTextStats;  // dangling text stats
    final protected int myOptions;

    private static int[] ensureCapacity(@NotNull int[] prev, int size) {
        assert size >= 0;

        int prevSize = prev.length / 2;
        if (prevSize <= size) {
            int nextSize = Math.max(MIN_PART_CAPACITY, Math.max(prevSize + prevSize >> 1, size));
            return Arrays.copyOf(prev, nextSize * 2);
        }
        return prev;
    }

    private void ensureCapacity(int size) {
        myParts = ensureCapacity(myParts, size + 1);
    }

    public void trimToSize() {
        if (myParts.length > myPartsSize) {
            myParts = Arrays.copyOf(myParts, myPartsSize * 2);
        }
    }

    // NOTE: all text accumulation is in the string builder, dangling text segment is between immutableOffset and myText.length()
    final protected StringBuilder myText = new StringBuilder();    // text segment ranges come from this CharSequence
    protected int myImmutableOffset = 0;   // text offset for all committed text segments

    protected SegmentBuilderBase() {
        this(F_INCLUDE_ANCHORS /*| F_TRACK_FIRST256*/);
    }

    protected SegmentBuilderBase(int options) {
        myOptions = options & (F_INCLUDE_ANCHORS | F_TRACK_FIRST256);
        myStats = new SegmentStats((options & F_TRACK_FIRST256) != 0);
        myTextStats = new SegmentStats((options & F_TRACK_FIRST256) != 0);
    }

    @Override
    public int getStartOffset() {
        return myStartOffset <= myEndOffset ? myStartOffset : -1;
    }

    public boolean needStartOffset() {
        return getStartOffsetIfNeeded() != -1;
    }

    public int getStartOffsetIfNeeded() {
        int startOffset = getStartOffset();
        Seg seg = getSegOrNull(0);
        return startOffset != -1 && seg != null && seg.isBase() && startOffset != seg.getStart() ? startOffset : -1;
    }

    @Override
    public int getEndOffset() {
        return myEndOffset >= myStartOffset ? myEndOffset : -1;
    }

    public boolean needEndOffset() {
        return getEndOffsetIfNeeded() != -1;
    }

    public int getEndOffsetIfNeeded() {
        int endOffset = getEndOffset();
        Seg seg = getSegOrNull(myPartsSize - 1);
        return endOffset != -1 && seg != null && seg.isBase() && endOffset != seg.getEnd() ? endOffset : -1;
    }

    @Override
    public boolean isEmpty() {
        return myPartsSize == 0;
    }

    @Override
    public boolean isBaseSubSequenceRange() {
        return getBaseSubSequenceRange() != null;
    }

    @Nullable
    @Override
    public Range getBaseSubSequenceRange() {
        if (myPartsSize == 1 && !haveDanglingText()) {
            Seg seg = getSeg(myPartsSize - 1);
            if (seg.length() != 0 && myAnchorsSize == 1) seg = getSeg(myPartsSize - 2);
            if (seg.isBase() && seg.getStart() == myStartOffset && seg.getEnd() == myEndOffset) {
                return seg.getRange();
            }
        }
        return null;
    }

    @Override
    public boolean haveOffsets() {
        return myStartOffset <= myEndOffset;
    }

    @Override
    public int size() {
        return myPartsSize + (haveDanglingText() ? 1 : 0);
    }

    @Override
    public CharSequence getText() {
        return myText;
    }

    @Override
    public int noAnchorsSize() {
        return size() - myAnchorsSize;
    }

    private int computeLength() {
        int length = 0;
        int iMax = myPartsSize;
        for (int i = 0; i < iMax; i++) {
            Seg part = getSeg(i);
            length += part.length();
        }

        if (haveDanglingText()) {
            length += myText.length() - myImmutableOffset;
        }
        return length;
    }

    @Override
    public int length() {
        // RELEASE: remove assert for release
        assert myLength == computeLength() : "myLength:" + myLength + " != computeLength(): " + computeLength();
        return myLength;
    }

    public SegmentStats getStats() {
        return myStats;
    }

    // @formatter:off
    @Override public boolean isTrackTextFirst256() { return myStats.isTrackTextFirst256(); }
    @Override public int getTextLength() { return myStats.getTextLength(); }
    @Override public int getTextSegments() { return myStats.getTextSegments(); }
    @Override public int getTextSpaceLength() { return myStats.getTextSpaceLength(); }
    @Override public int getTextSpaceSegments() { return myStats.getTextSpaceSegments(); }
    @Override public int getTextFirst256Length() { return myStats.getTextFirst256Length(); }
    @Override public int getTextFirst256Segments() { return myStats.getTextFirst256Segments(); }

// @formatter:on

    @Override
    public @NotNull Iterator<Object> iterator() {
        return new PartsIterator(this);
    }

    static class PartsIterator implements Iterator<Object> {
        final SegmentBuilderBase<?> myBuilder;
        int myNextIndex;

        public PartsIterator(SegmentBuilderBase<?> builder) {
            myBuilder = builder;
        }

        @Override
        public boolean hasNext() {
            return myNextIndex < myBuilder.size();
        }

        @Override
        public Object next() {
            return myBuilder.getPart(myNextIndex++);
        }
    }

    @Override
    public @NotNull Iterable<Seg> getSegments() {
        return new SegIterable(this);
    }

    static class SegIterable implements Iterable<Seg> {
        final SegmentBuilderBase<?> myBuilder;

        public SegIterable(SegmentBuilderBase<?> builder) {
            myBuilder = builder;
        }

        @NotNull
        @Override
        public Iterator<Seg> iterator() {
            return new SegIterator(myBuilder);
        }
    }

    static class SegIterator implements Iterator<Seg> {
        final SegmentBuilderBase<?> myBuilder;
        int myNextIndex;

        public SegIterator(SegmentBuilderBase<?> builder) {
            myBuilder = builder;
        }

        @Override
        public boolean hasNext() {
            return myNextIndex < myBuilder.size();
        }

        @Override
        public Seg next() {
            return myBuilder.getSegPart(myNextIndex++);
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

    /**
     * Span for offsets of this list
     *
     * @return -ve if no information in the list, or span of offsets
     */
    @Override
    public int getSpan() {
        return myStartOffset > myEndOffset ? -1 : myEndOffset - myStartOffset;
    }

    @Nullable
    private Seg getSegOrNull(int index) {
        int i = index * 2;
        return i + 1 >= myParts.length ? null : Seg.segOf(myParts[i], myParts[i + 1]);
    }

    @NotNull
    private Seg getSeg(int index) {
        int i = index * 2;
        return i + 1 >= myParts.length ? Seg.NULL : Seg.segOf(myParts[i], myParts[i + 1]);
    }

    @NotNull
    Object getPart(int index) {
        if (index == myPartsSize && haveDanglingText()) {
            // return dangling text
            return myText.subSequence(myImmutableOffset, myText.length());
        } else {
            int i = index * 2;
            Seg seg = i + 1 >= myParts.length ? Seg.NULL : Seg.segOf(myParts[i], myParts[i + 1]);
            return seg.isBase() ? seg.getRange() : seg.isText() ? myText.subSequence(seg.getTextStart(), seg.getTextEnd()) : Range.NULL;
        }
    }

    @NotNull
    Seg getSegPart(int index) {
        if (index == myPartsSize && haveDanglingText()) {
            // return dangling text
            return Seg.textOf(myImmutableOffset, myText.length(), myTextStats.isTextFirst256(), myTextStats.isRepeatedText());
        } else {
            int i = index * 2;
            return i + 1 >= myParts.length ? Seg.NULL : Seg.segOf(myParts[i], myParts[i + 1]);
        }
    }

    private void setSegEnd(int index, int endOffset) {
        int i = index * 2;
        assert i + 1 < myParts.length;

//        myParts[i] = startOffset;
        // adjust anchor count
        if (myParts[i] == endOffset) {
            if (myParts[i] != myParts[i + 1]) myAnchorsSize++;
        } else if (myParts[i] == myParts[i + 1]) myAnchorsSize--;

        myParts[i + 1] = endOffset;
    }

    private void addSeg(int startOffset, int endOffset) {
        ensureCapacity(myPartsSize);
        int i = myPartsSize * 2;
        myParts[i] = startOffset;
        myParts[i + 1] = endOffset;
        myPartsSize++;
        if (startOffset == endOffset) myAnchorsSize++;
    }

    @Nullable
    private Seg lastSegOrNull() {
        return myPartsSize == 0 ? null : getSegOrNull(myPartsSize - 1);
    }

    private boolean haveDanglingText() {
        return myText.length() > myImmutableOffset;
    }

    protected Object[] optimizeText(@NotNull Object[] parts) {
        return parts;
    }

    protected Object[] handleOverlap(@NotNull Object[] parts) {
        // range overlaps with last segment in the list
        Range lastSeg = (Range) parts[0];
        CharSequence text = (CharSequence) parts[1];
        Range range = (Range) parts[2];

        assert !lastSeg.isNull() && lastSeg.getEnd() > range.getStart();

        if (lastSeg.getEnd() < range.getEnd()) {
            // there is a part of the overlap outside the last seg range
            if (text.length() > 0) {
                // append the chopped off base part
                parts[2] = Range.of(lastSeg.getEnd(), range.getEnd());
            } else {
                // extend the last base seg to include this range
                parts[0] = lastSeg.withEnd(range.getEnd());
                parts[2] = Range.NULL;
            }
        } else {
            parts[2] = Range.NULL;
        }
        return parts;
    }

    private void processParts(int segStart, int segEnd, boolean resolveOverlap, boolean nullNextRange, @NotNull Function<Object[], Object[]> transform) {
        assert segStart >= 0 && segEnd >= 0 && segStart <= segEnd;

        CharSequence text = myText.subSequence(myImmutableOffset, myText.length());
        assert resolveOverlap || text.length() > 0;

        Seg lastSeg = lastSegOrNull();
        Range prevRange = lastSeg == null || !lastSeg.isBase() ? Range.NULL : lastSeg.getRange();

        if (!isIncludeAnchors() && haveOffsets()) {
            // need to use max with endOffset since anchors are not stored
            if (prevRange.isNull() || prevRange.getEnd() < myEndOffset) prevRange = Range.emptyOf(myEndOffset);
        }

        if (!haveOffsets()) myStartOffset = segStart;

        // NOTE: cannot incorporate segEnd if overlap is being resolved
        if (!resolveOverlap) myEndOffset = Math.max(myEndOffset, segEnd);

        Object[] parts = {
                prevRange,
                text,
                nullNextRange ? Range.NULL : Range.of(segStart, segEnd),
        };

        Object[] originalParts = parts.clone();
        Object[] optimizedText = transform.apply(parts);
        assert optimizedText.length > 0;

        if (Arrays.equals(optimizedText, originalParts)) {
            // nothing changed, make sure it was not called to resolve overlap
            assert !resolveOverlap;

            if (segEnd > segStart || isIncludeAnchors()) {
                // NOTE: only commit text if adding real range after it
                if (text.length() > 0) {
                    commitText();
                }

                myLength += segEnd - segStart;
                addSeg(segStart, segEnd);
            }
        } else {
            // remove dangling text information
            myTextStats.commitText();
            myStats.commitText();
            myStats.remove(myTextStats);
            myTextStats.clear();
            myLength -= text.length();
            myText.delete(myImmutableOffset, myText.length());

            if (lastSeg != null && lastSeg.isBase()) {
                // remove last seg from parts, it will be added on return
                myLength -= lastSeg.length();
                myPartsSize--;
                if (lastSeg.length() == 0) myAnchorsSize--;
            }

            int iMax = optimizedText.length;
            int optStartOffset = MAX_VALUE;
            int optEndOffset = MIN_VALUE;

            for (int i = 0; i < iMax; i++) {
                Object oPart = optimizedText[i];
                if (oPart instanceof CharSequence) {
                    CharSequence optText = (CharSequence) oPart;
                    if (optText.length() > 0) {
                        addText(optText);
                    }
                } else if (oPart instanceof Range) {
                    if (((Range) oPart).isNotNull()) {
                        int optRangeStart = ((Range) oPart).getStart();
                        int optRangeEnd = ((Range) oPart).getEnd();
                        assert optRangeStart >= 0 && optRangeEnd >= 0 && optRangeStart <= optRangeEnd;

                        if (optStartOffset == MAX_VALUE) optStartOffset = optRangeStart;

                        if (optRangeStart < optEndOffset) {
                            throw new IllegalStateException(String.format("Accumulated range [%d, %d) overlaps Transformed Range[%d]: [%d, %d)", optStartOffset, optEndOffset, i, optRangeStart, optRangeEnd));
                        }

                        optEndOffset = Math.max(optEndOffset, optRangeEnd);

                        boolean haveDanglingText = haveDanglingText();

                        if (haveDanglingText && resolveOverlap) {
                            processParts(optRangeStart, optRangeEnd, false, false, this::optimizeText);
                        } else {
                            // adjust offsets since they could have expanded
                            myStartOffset = Math.min(myStartOffset, optRangeStart);
                            myEndOffset = Math.max(myEndOffset, optRangeEnd);

                            if (optRangeStart != optRangeEnd || isIncludeAnchors()) {
                                if (haveDanglingText) {
                                    commitText();
                                }

                                // add base segment
                                myLength += optRangeEnd - optRangeStart;
                                addSeg(optRangeStart, optRangeEnd);
                            }
                        }
                    }
                } else if (oPart != null) {
                    throw new IllegalStateException("Invalid optimized part type " + oPart.getClass());
                }
            }
        }
    }

    private void commitText() {
        addSeg(Seg.getTextStart(myImmutableOffset, myTextStats.isTextFirst256()), Seg.getTextEnd(myText.length(), myTextStats.isRepeatedText()));
        myImmutableOffset = myText.length();
        myStats.commitText();
        myTextStats.clear();
    }

    private void addText(CharSequence optText) {
        myLength += optText.length();
        myText.append(optText);

        myStats.addText(optText);
        myTextStats.addText(optText);
    }

    /**
     * append anchor in original sequence coordinates, no checking is done other than overlap with tail range
     * fast
     *
     * @param offset offset in original sequence
     * @return this
     */
    @NotNull
    public S appendAnchor(int offset) {
        return append(offset, offset);
    }

    /**
     * append range in original sequence coordinates, no checking is done other than overlap with tail range
     * fast
     *
     * @param range range in original sequence
     * @return this
     */
    @NotNull
    public S append(@NotNull Range range) {
        return append(range.getStart(), range.getEnd());
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
    public S append(int startOffset, int endOffset) {
        if (endOffset < 0 || startOffset > endOffset)
            //noinspection unchecked
            return (S) this;

        int rangeSpan = endOffset - startOffset;
        if (rangeSpan == 0 && (!isIncludeAnchors() || startOffset < myEndOffset)) {
            if (startOffset >= myEndOffset) {
                // can optimize text
                if (haveDanglingText()) {
                    processParts(startOffset, endOffset, false, false, this::optimizeText);
                } else {
                    if (!haveOffsets()) myStartOffset = startOffset;
                    myEndOffset = startOffset;
                }
            }
            //noinspection unchecked
            return (S) this;
        }

        if (myEndOffset > startOffset) {
            // overlap
            processParts(startOffset, endOffset, true, false, this::handleOverlap);
        } else if (myEndOffset == startOffset) {

            // adjacent, merge the two if no text between them
            if (haveDanglingText()) {
                processParts(startOffset, endOffset, false, false, this::optimizeText);
            } else {
                myEndOffset = endOffset;
                myLength += rangeSpan;

                if (myPartsSize == 0) {
                    // no last segment, add this one
                    addSeg(startOffset, endOffset);
                } else {
                    // combine this with the last segment
                    setSegEnd(myPartsSize - 1, endOffset);
                }
            }
        } else {
            // disjoint

            if (haveDanglingText()) {
                processParts(startOffset, endOffset, false, false, this::optimizeText);
            } else {
                if (!haveOffsets()) myStartOffset = startOffset;
                myEndOffset = endOffset;
                myLength += rangeSpan;
                addSeg(startOffset, endOffset);
            }
        }
        //noinspection unchecked
        return (S) this;
    }

    @NotNull
    public S append(@NotNull CharSequence text) {
        int length = text.length();
        if (length != 0) {
            myStats.addText(text);
            myTextStats.addText(text);

            myText.append(text);
            myLength += length;
        }
        //noinspection unchecked
        return (S) this;
    }

    @NotNull
    public S append(char c) {
        myStats.addText(c);
        myTextStats.addText(c);

        myText.append(c);
        myLength++;

        //noinspection unchecked
        return (S) this;
    }

    @NotNull
    public S append(char c, int repeat) {
        if (repeat > 0) {
            myStats.addText(c, repeat);
            myTextStats.addText(c, repeat);
            myLength += repeat;

            while (repeat-- > 0) myText.append(c);
        }
        //noinspection unchecked
        return (S) this;
    }

    @NotNull
    public String toString(@NotNull CharSequence chars, @NotNull CharSequence rangePrefix, @NotNull CharSequence rangeSuffix, @NotNull Function<CharSequence, CharSequence> textMapper) {
        if (myEndOffset > chars.length()) {
            throw new IllegalArgumentException("baseSequence length() must be at least " + myEndOffset + ", got: " + chars.length());
        }

        if (haveDanglingText() && haveOffsets()) {
            processParts(myEndOffset, myEndOffset, false, true, this::optimizeText);
        }

        StringBuilder out = new StringBuilder();

        int iMax = myPartsSize;
        for (int i = 0; i < iMax; i++) {
            Seg part = getSeg(i);

            if (!part.isBase()) {
                out.append(textMapper.apply(myText.subSequence(part.getTextStart(), part.getTextEnd())));
            } else {
                out.append(rangePrefix).append(textMapper.apply(chars.subSequence(part.getStart(), part.getEnd()))).append(rangeSuffix);
            }
        }

        if (haveDanglingText()) {
            out.append(textMapper.apply(myText.subSequence(myImmutableOffset, myText.length())));
        }

        return out.toString();
    }

    @NotNull
    public String toStringWithRangesVisibleWhitespace(@NotNull CharSequence chars) {
        return toString(chars, "⟦", "⟧", SequenceUtils::toVisibleWhitespaceString);
    }

    @NotNull
    public String toStringWithRanges(@NotNull CharSequence chars) {
        return toString(chars, "⟦", "⟧", Function.identity());
    }

    @NotNull
    public String toString(@NotNull CharSequence chars) {
        return toString(chars, "", "", Function.identity());
    }

    public String toStringPrep() {
        if (haveDanglingText() && haveOffsets()) {
            processParts(myEndOffset, myEndOffset, false, true, this::optimizeText);
        }
        return toString();
    }

    @Override
    public String toString() {
        DelimitedBuilder sb = new DelimitedBuilder(", ");
        sb.append(this.getClass().getSimpleName()).append("{");

        if (haveOffsets()) {
            sb.append("[").append(myStartOffset).mark()
                    .append(myEndOffset).unmark()
                    .append(")").mark();
        } else {
            sb.append("NULL").mark();
        }

//        // CAUTION: this method is invoked from the debugger and any non-pure method invocation messes up internal structures
//        if (haveDanglingText() && haveOffsets()) {
//            optimizeText(myEndOffset, myEndOffset);
//        }
//
        SegmentStats committedStats = myStats.committedCopy();
        sb.append(committedStats).mark()
                .append("l=").append(myLength).mark()
                .append("sz=").append(size()).mark()
                .append("na=").append(noAnchorsSize())
        ;

        if (size() > 0) sb.append(": ");

        int iMax = myPartsSize;
        for (int i = 0; i < iMax; i++) {
            Seg part = getSeg(i);
            sb.append(part.toString(myText)).mark();
        }

        if (haveDanglingText()) {
            Seg part = Seg.textOf(myImmutableOffset, myText.length(), myTextStats.isTextFirst256(), myTextStats.isRepeatedText());
            sb.append(part.toString(myText)).mark();
        }

        sb.unmark().append(" }");
        return sb.toString();
    }
}
