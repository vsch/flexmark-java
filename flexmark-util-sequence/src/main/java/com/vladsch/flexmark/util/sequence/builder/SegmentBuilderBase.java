package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.misc.DelimitedBuilder;
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
    final public static int MIN_PART_CAPACITY = 8;

    final public static int[] EMPTY_PARTS = { };

    protected @NotNull int[] parts = EMPTY_PARTS;
    protected int partsSize = 0;
    protected int anchorsSize = 0;

    protected int startOffset = Range.NULL.getStart();
    protected int endOffset = Range.NULL.getEnd();
    protected int length = 0;
    final protected SegmentStats stats;      // committed and dangling text stats
    final protected SegmentStats textStats;  // dangling text stats
    final protected int options;

    // NOTE: all text accumulation is in the string builder, dangling text segment is between immutableOffset and text.length()
    final protected StringBuilder text = new StringBuilder();    // text segment ranges come from this CharSequence
    protected int immutableOffset = 0;   // text offset for all committed text segments

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
        parts = ensureCapacity(parts, size + 1);
    }

    public void trimToSize() {
        if (parts.length > partsSize) {
            parts = Arrays.copyOf(parts, partsSize * 2);
        }
    }

    protected SegmentBuilderBase() {
        this(F_INCLUDE_ANCHORS /*| F_TRACK_FIRST256*/);
    }

    protected SegmentBuilderBase(int options) {
        this.options = options & (F_INCLUDE_ANCHORS | F_TRACK_FIRST256);
        stats = new SegmentStats((options & F_TRACK_FIRST256) != 0);
        textStats = new SegmentStats((options & F_TRACK_FIRST256) != 0);
    }

    @Override
    public int getStartOffset() {
        return startOffset <= endOffset ? startOffset : -1;
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
        return endOffset >= startOffset ? endOffset : -1;
    }

    public boolean needEndOffset() {
        return getEndOffsetIfNeeded() != -1;
    }

    public int getEndOffsetIfNeeded() {
        int endOffset = getEndOffset();
        Seg seg = getSegOrNull(partsSize - 1);
        return endOffset != -1 && seg != null && seg.isBase() && endOffset != seg.getEnd() ? endOffset : -1;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean isBaseSubSequenceRange() {
        return getBaseSubSequenceRange() != null;
    }

    @Nullable
    @Override
    public Range getBaseSubSequenceRange() {
        if (partsSize == 1 && !haveDanglingText()) {
            Seg seg = getSeg(partsSize - 1);
            if (seg.length() != 0 && anchorsSize == 1) seg = getSeg(partsSize - 2);
            if (seg.isBase() && seg.getStart() == startOffset && seg.getEnd() == endOffset) {
                return seg.getRange();
            }
        }
        return null;
    }

    @Override
    public boolean haveOffsets() {
        return startOffset <= endOffset;
    }

    @Override
    public int size() {
        return partsSize + (haveDanglingText() ? 1 : 0);
    }

    @Override
    public CharSequence getText() {
        return text;
    }

    @Override
    public int noAnchorsSize() {
        return size() - anchorsSize;
    }

    private int computeLength() {
        int length = 0;
        int iMax = partsSize;
        for (int i = 0; i < iMax; i++) {
            Seg part = getSeg(i);
            length += part.length();
        }

        if (haveDanglingText()) {
            length += text.length() - immutableOffset;
        }
        return length;
    }

    @Override
    public int length() {
        // RELEASE: remove assert for release
        assert length == computeLength() : "length:" + length + " != computeLength(): " + computeLength();
        return length;
    }

    public SegmentStats getStats() {
        return stats;
    }

    // @formatter:off
    @Override public boolean isTrackTextFirst256() { return stats.isTrackTextFirst256(); }
    @Override public int getTextLength() { return stats.getTextLength(); }
    @Override public int getTextSegments() { return stats.getTextSegments(); }
    @Override public int getTextSpaceLength() { return stats.getTextSpaceLength(); }
    @Override public int getTextSpaceSegments() { return stats.getTextSpaceSegments(); }
    @Override public int getTextFirst256Length() { return stats.getTextFirst256Length(); }
    @Override public int getTextFirst256Segments() { return stats.getTextFirst256Segments(); }

// @formatter:on

    @Override
    public @NotNull Iterator<Object> iterator() {
        return new PartsIterator(this);
    }

    static class PartsIterator implements Iterator<Object> {
        final SegmentBuilderBase<?> builder;
        int nextIndex;

        public PartsIterator(SegmentBuilderBase<?> builder) {
            this.builder = builder;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < builder.size();
        }

        @Override
        public Object next() {
            return builder.getPart(nextIndex++);
        }
    }

    @Override
    public @NotNull Iterable<Seg> getSegments() {
        return new SegIterable(this);
    }

    static class SegIterable implements Iterable<Seg> {
        final SegmentBuilderBase<?> builder;

        public SegIterable(SegmentBuilderBase<?> builder) {
            this.builder = builder;
        }

        @NotNull
        @Override
        public Iterator<Seg> iterator() {
            return new SegIterator(builder);
        }
    }

    static class SegIterator implements Iterator<Seg> {
        final SegmentBuilderBase<?> builder;
        int nextIndex;

        public SegIterator(SegmentBuilderBase<?> builder) {
            this.builder = builder;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < builder.size();
        }

        @Override
        public Seg next() {
            return builder.getSegPart(nextIndex++);
        }
    }

    @Override
    public int getOptions() {
        return options;
    }

    @Override
    public boolean isIncludeAnchors() {
        return (options & F_INCLUDE_ANCHORS) != 0;
    }

    /**
     * Span for offsets of this list
     *
     * @return -ve if no information in the list, or span of offsets
     */
    @Override
    public int getSpan() {
        return startOffset > endOffset ? -1 : endOffset - startOffset;
    }

    @Nullable
    private Seg getSegOrNull(int index) {
        int i = index * 2;
        return i + 1 >= parts.length ? null : Seg.segOf(parts[i], parts[i + 1]);
    }

    @NotNull
    private Seg getSeg(int index) {
        int i = index * 2;
        return i + 1 >= parts.length ? Seg.NULL : Seg.segOf(parts[i], parts[i + 1]);
    }

    @NotNull
    public Object getPart(int index) {
        if (index == partsSize && haveDanglingText()) {
            // return dangling text
            return text.subSequence(immutableOffset, text.length());
        } else {
            int i = index * 2;
            Seg seg = i + 1 >= parts.length ? Seg.NULL : Seg.segOf(parts[i], parts[i + 1]);
            return seg.isBase() ? seg.getRange() : seg.isText() ? text.subSequence(seg.getTextStart(), seg.getTextEnd()) : Range.NULL;
        }
    }

    @NotNull
    Seg getSegPart(int index) {
        if (index == partsSize && haveDanglingText()) {
            // return dangling text
            return Seg.textOf(immutableOffset, text.length(), textStats.isTextFirst256(), textStats.isRepeatedText());
        } else {
            int i = index * 2;
            return i + 1 >= parts.length ? Seg.NULL : Seg.segOf(parts[i], parts[i + 1]);
        }
    }

    private void setSegEnd(int index, int endOffset) {
        int i = index * 2;
        assert i + 1 < parts.length;

//        parts[i] = startOffset;
        // adjust anchor count
        if (parts[i] == endOffset) {
            if (parts[i] != parts[i + 1]) anchorsSize++;
        } else if (parts[i] == parts[i + 1]) anchorsSize--;

        parts[i + 1] = endOffset;
    }

    private void addSeg(int startOffset, int endOffset) {
        ensureCapacity(partsSize);
        int i = partsSize * 2;
        parts[i] = startOffset;
        parts[i + 1] = endOffset;
        partsSize++;
        if (startOffset == endOffset) anchorsSize++;
    }

    @Nullable
    private Seg lastSegOrNull() {
        return partsSize == 0 ? null : getSegOrNull(partsSize - 1);
    }

    protected boolean haveDanglingText() {
        return text.length() > immutableOffset;
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

        CharSequence text = this.text.subSequence(immutableOffset, this.text.length());
        assert resolveOverlap || text.length() > 0;

        Seg lastSeg = lastSegOrNull();
        Range prevRange = lastSeg == null || !lastSeg.isBase() ? Range.NULL : lastSeg.getRange();

        if (!isIncludeAnchors() && haveOffsets()) {
            // need to use max with endOffset since anchors are not stored
            if (prevRange.isNull() || prevRange.getEnd() < endOffset) prevRange = Range.emptyOf(endOffset);
        }

        if (!haveOffsets()) startOffset = segStart;

        // NOTE: cannot incorporate segEnd if overlap is being resolved
        if (!resolveOverlap) endOffset = Math.max(endOffset, segEnd);

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

                length += segEnd - segStart;
                addSeg(segStart, segEnd);
            }
        } else {
            // remove dangling text information
            textStats.commitText();
            stats.commitText();
            stats.remove(textStats);
            textStats.clear();
            length -= text.length();
            this.text.delete(immutableOffset, this.text.length());

            if (lastSeg != null && lastSeg.isBase()) {
                // remove last seg from parts, it will be added on return
                length -= lastSeg.length();
                partsSize--;
                if (lastSeg.length() == 0) anchorsSize--;
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
                            startOffset = Math.min(startOffset, optRangeStart);
                            endOffset = Math.max(endOffset, optRangeEnd);

                            if (optRangeStart != optRangeEnd || isIncludeAnchors()) {
                                if (haveDanglingText) {
                                    commitText();
                                }

                                // add base segment
                                length += optRangeEnd - optRangeStart;
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
        addSeg(Seg.getTextStart(immutableOffset, textStats.isTextFirst256()), Seg.getTextEnd(text.length(), textStats.isRepeatedText()));
        immutableOffset = text.length();
        stats.commitText();
        textStats.clear();
    }

    private void addText(CharSequence optText) {
        length += optText.length();
        text.append(optText);

        stats.addText(optText);
        textStats.addText(optText);
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
        if (rangeSpan == 0 && (!isIncludeAnchors() || startOffset < this.endOffset)) {
            if (startOffset >= this.endOffset) {
                // can optimize text
                if (haveDanglingText()) {
                    processParts(startOffset, endOffset, false, false, this::optimizeText);
                } else {
                    if (!haveOffsets()) this.startOffset = startOffset;
                    this.endOffset = startOffset;
                }
            }
            //noinspection unchecked
            return (S) this;
        }

        if (this.endOffset > startOffset) {
            // overlap
            processParts(startOffset, endOffset, true, false, this::handleOverlap);
        } else if (this.endOffset == startOffset) {

            // adjacent, merge the two if no text between them
            if (haveDanglingText()) {
                processParts(startOffset, endOffset, false, false, this::optimizeText);
            } else {
                this.endOffset = endOffset;
                length += rangeSpan;

                if (partsSize == 0) {
                    // no last segment, add this one
                    addSeg(startOffset, endOffset);
                } else {
                    // combine this with the last segment
                    setSegEnd(partsSize - 1, endOffset);
                }
            }
        } else {
            // disjoint

            if (haveDanglingText()) {
                processParts(startOffset, endOffset, false, false, this::optimizeText);
            } else {
                if (!haveOffsets()) this.startOffset = startOffset;
                this.endOffset = endOffset;
                length += rangeSpan;
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
            stats.addText(text);
            textStats.addText(text);

            this.text.append(text);
            this.length += length;
        }
        //noinspection unchecked
        return (S) this;
    }

    @NotNull
    public S append(char c) {
        stats.addText(c);
        textStats.addText(c);

        text.append(c);
        length++;

        //noinspection unchecked
        return (S) this;
    }

    @NotNull
    public S append(char c, int repeat) {
        if (repeat > 0) {
            stats.addText(c, repeat);
            textStats.addText(c, repeat);
            length += repeat;

            while (repeat-- > 0) text.append(c);
        }
        //noinspection unchecked
        return (S) this;
    }

    @NotNull
    public String toString(@NotNull CharSequence chars, @NotNull CharSequence rangePrefix, @NotNull CharSequence rangeSuffix, @NotNull Function<CharSequence, CharSequence> textMapper) {
        if (endOffset > chars.length()) {
            throw new IllegalArgumentException("baseSequence length() must be at least " + endOffset + ", got: " + chars.length());
        }

        if (haveDanglingText() && haveOffsets()) {
            processParts(endOffset, endOffset, false, true, this::optimizeText);
        }

        StringBuilder out = new StringBuilder();

        int iMax = partsSize;
        for (int i = 0; i < iMax; i++) {
            Seg part = getSeg(i);

            if (!part.isBase()) {
                out.append(textMapper.apply(text.subSequence(part.getTextStart(), part.getTextEnd())));
            } else {
                out.append(rangePrefix).append(textMapper.apply(chars.subSequence(part.getStart(), part.getEnd()))).append(rangeSuffix);
            }
        }

        if (haveDanglingText()) {
            out.append(textMapper.apply(text.subSequence(immutableOffset, text.length())));
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
            processParts(endOffset, endOffset, false, true, this::optimizeText);
        }
        return toString();
    }

    @Override
    public String toString() {
        DelimitedBuilder sb = new DelimitedBuilder(", ");
        sb.append(this.getClass().getSimpleName()).append("{");

        if (haveOffsets()) {
            sb.append("[").append(startOffset).mark()
                    .append(endOffset).unmark()
                    .append(")").mark();
        } else {
            sb.append("NULL").mark();
        }

//        // CAUTION: this method is invoked from the debugger and any non-pure method invocation messes up internal structures
//        if (haveDanglingText() && haveOffsets()) {
//            optimizeText(endOffset, endOffset);
//        }
//
        SegmentStats committedStats = stats.committedCopy();
        sb.append(committedStats).mark()
                .append("l=").append(length).mark()
                .append("sz=").append(size()).mark()
                .append("na=").append(noAnchorsSize())
        ;

        if (size() > 0) sb.append(": ");

        int iMax = partsSize;
        for (int i = 0; i < iMax; i++) {
            Seg part = getSeg(i);
            sb.append(part.toString(text)).mark();
        }

        if (haveDanglingText()) {
            Seg part = Seg.textOf(immutableOffset, text.length(), textStats.isTextFirst256(), textStats.isRepeatedText());
            sb.append(part.toString(text)).mark();
        }

        sb.unmark().append(" }");
        return sb.toString();
    }
}
