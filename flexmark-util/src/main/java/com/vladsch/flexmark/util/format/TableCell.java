package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.SubSequence;

import static com.vladsch.flexmark.util.Utils.*;

@SuppressWarnings("WeakerAccess")
public class TableCell {
    public final static TableCell NULL = new TableCell(SubSequence.NULL, " ", BasedSequence.NULL, 1, 0, CellAlignment.NONE);

    public final BasedSequence openMarker;
    public final BasedSequence text;
    public final BasedSequence closeMarker;
    public final int columnSpan;
    public final int rowSpan;
    public final CellAlignment alignment;
    public final int trackedTextOffset; // offset in the text
    public final int spanTrackedOffset; // offset in the span if span > 1
    public final int trackedTextAdjust; // adjustment to the resulting tracked position due to alignment  
    public final boolean afterSpace; // if adjustment should be done as if after delete

    public TableCell(final CharSequence text, final int rowSpan, final int columnSpan) {
        this(BasedSequence.NULL, text, BasedSequence.NULL, rowSpan, columnSpan, CellAlignment.NONE);
    }

    public TableCell(final CharSequence text, final int rowSpan, final int columnSpan, final CellAlignment alignment) {
        this(BasedSequence.NULL, text, BasedSequence.NULL, rowSpan, columnSpan, alignment);
    }

    public TableCell(CharSequence openMarker, final CharSequence text, CharSequence closeMarker, final int rowSpan, final int columnSpan) {
        this(openMarker, text, closeMarker, rowSpan, columnSpan, CellAlignment.NONE);
    }

    public TableCell(
            final CharSequence openMarker,
            final CharSequence text,
            final CharSequence closeMarker,
            final int rowSpan,
            final int columnSpan,
            final CellAlignment alignment
    ) {
        this(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, MarkdownTable.NOT_TRACKED, MarkdownTable.NOT_TRACKED, 0, false);
    }

    public TableCell(
            final CharSequence openMarker,
            final CharSequence text,
            final CharSequence closeMarker,
            final int rowSpan,
            final int columnSpan,
            final CellAlignment alignment,
            final int trackedTextOffset,
            final int spanTrackedOffset,
            final int trackedTextAdjust,
            final boolean afterSpace
    ) {
        BasedSequence chars = BasedSequenceImpl.of(text);
        this.openMarker = BasedSequenceImpl.of(openMarker);
        this.text = chars.isEmpty() ? PrefixedSubSequence.of(" ", this.openMarker.subSequence(this.openMarker.length(), this.openMarker.length())) : chars;
        this.closeMarker = BasedSequenceImpl.of(closeMarker);
        this.rowSpan = rowSpan;
        this.columnSpan = columnSpan;
        this.alignment = alignment != null ? alignment : CellAlignment.NONE;
        this.trackedTextOffset = trackedTextOffset;
        this.spanTrackedOffset = spanTrackedOffset;
        this.trackedTextAdjust = trackedTextAdjust;
        this.afterSpace = afterSpace;
    }

    public TableCell withColumnSpan(int columnSpan) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset == MarkdownTable.NOT_TRACKED ? MarkdownTable.NOT_TRACKED : min(spanTrackedOffset, columnSpan), trackedTextAdjust, afterSpace); }

    public TableCell withText(CharSequence text) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, MarkdownTable.NOT_TRACKED, spanTrackedOffset, trackedTextAdjust, afterSpace); }

    public TableCell withRowSpan(int rowSpan) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace); }

    public TableCell withAlignment(CellAlignment alignment) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace); }

    public TableCell withTrackedOffset(int trackedTextOffset) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace); }

    public TableCell withTrackedOffset(int trackedTextOffset, boolean afterSpace) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace); }
    
    public TableCell withSpanTrackedOffset(int spanTrackedOffset) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace); }

    public TableCell withTrackedTextAdjust(int trackedTextAdjust) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace); }
    
    public TableCell withAfterSpace(boolean afterSpace) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace); }

    BasedSequence getLastSegment() {
        return !closeMarker.isEmpty() ? closeMarker : text;
    }

    public int getEndOffset() {
        return !closeMarker.isEmpty() ? closeMarker.getEndOffset() : text.getEndOffset();
    }

    public int getStartOffset() {
        return !openMarker.isEmpty() ? openMarker.getStartOffset() : text.getStartOffset();
    }

    public int getInsideStartOffset() {
        return !openMarker.isEmpty() ? openMarker.getEndOffset() : text.getStartOffset();
    }

    public int getTextStartOffset() {
        return text.getStartOffset();
    }

    public int getTextEndOffset() {
        return text.getEndOffset();
    }
    
    public int getInsideEndOffset() {
        return !closeMarker.isEmpty() ? closeMarker.getStartOffset() : text.getEndOffset();
    }

    public int getCellSize() {
        return getEndOffset() - getStartOffset();
    }

    public int insideToTextOffset(int insideOffset) {
        return maxLimit(text.length(), minLimit(insideOffset - getInsideStartOffset() + getTextStartOffset(), 0));
    }

    public int textToInsideOffset(int insideOffset) {
        return maxLimit(getCellSize(), minLimit(insideOffset - getTextStartOffset() + getInsideStartOffset(), 0));
    }

    public boolean isInsideCell(int offset) {
        return offset >= getInsideStartOffset() && offset <= getInsideEndOffset();
    }

    public boolean isAtCell(int offset) {
        return offset >= getInsideStartOffset() && offset <= getInsideEndOffset();
    }

    /**
     * Returns the cell length occupied in the table
     *
     * @return length of the cell as occupied in the original file
     */
    public int getCellLength() {
        return getEndOffset() - getStartOffset();
    }

    /**
     * Returns the cell prefix length occupied in the table
     *
     * @return length of cell's prefix before actual text as occupied in the file
     */
    public int getCellPrefixLength() {
        return getInsideStartOffset() - getStartOffset();
    }
}
