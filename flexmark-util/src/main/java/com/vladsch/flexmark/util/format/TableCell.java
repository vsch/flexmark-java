package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.SubSequence;

import static com.vladsch.flexmark.util.Utils.maxLimit;
import static com.vladsch.flexmark.util.Utils.min;
import static com.vladsch.flexmark.util.Utils.minLimit;

@SuppressWarnings("WeakerAccess")
public class TableCell {
    public final static TableCell NULL = new TableCell(SubSequence.NULL, " ", BasedSequence.NULL, 1, 0, CellAlignment.NONE);
    public final static TableCell DEFAULT_CELL = new TableCell(SubSequence.NULL, " ", BasedSequence.NULL, 1, 1, CellAlignment.NONE);
    public static final int NOT_TRACKED = Integer.MAX_VALUE;

    public final BasedSequence openMarker;
    public final BasedSequence text;
    public final BasedSequence closeMarker;
    public final int columnSpan;
    public final int rowSpan;
    public final CellAlignment alignment;
    public final int trackedTextOffset; // offset in the text
    public final int spanTrackedOffset; // offset in the span if span > 1
    public final int trackedTextAdjust; // adjustment to the resulting tracked position due to alignment  
    public final boolean afterSpace; // if adjustment should be done after space
    public final boolean afterDelete; // if adjustment should be done as if after delete

    public TableCell(final CharSequence text, final int rowSpan, final int columnSpan) {
        this(BasedSequence.NULL, text, BasedSequence.NULL, rowSpan, columnSpan, CellAlignment.NONE);
    }

    public TableCell(
            final CharSequence text,
            final int rowSpan,
            final int columnSpan,
            final CellAlignment alignment
    ) {
        this(BasedSequence.NULL, text, BasedSequence.NULL, rowSpan, columnSpan, alignment);
    }

    public TableCell(
            CharSequence openMarker,
            final CharSequence text,
            CharSequence closeMarker,
            final int rowSpan,
            final int columnSpan
    ) {
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
        this(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, NOT_TRACKED, NOT_TRACKED, 0, false, false);
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
            final boolean afterSpace,
            final boolean afterDelete

    ) {
        BasedSequence chars = BasedSequenceImpl.of(text);
        this.openMarker = BasedSequenceImpl.of(openMarker);
        this.closeMarker = BasedSequenceImpl.of(closeMarker);
        BasedSequence useMarker = this.openMarker.isEmpty() ? this.closeMarker.subSequence(0, 0) : this.openMarker.subSequence(this.openMarker.length());
        this.text = chars.isEmpty() && chars != BasedSequence.NULL ? PrefixedSubSequence.of(" ", useMarker) : chars;
        this.rowSpan = rowSpan;
        this.columnSpan = columnSpan;
        this.alignment = alignment != null ? alignment : CellAlignment.NONE;
        this.trackedTextOffset = trackedTextOffset;
        this.spanTrackedOffset = spanTrackedOffset;
        this.trackedTextAdjust = trackedTextAdjust;
        this.afterSpace = afterSpace;
        this.afterDelete = afterDelete;
    }

    public TableCell withColumnSpan(int columnSpan) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset == NOT_TRACKED ? NOT_TRACKED : min(spanTrackedOffset, columnSpan), trackedTextAdjust, afterSpace, afterDelete); }

    public TableCell withText(CharSequence text) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, NOT_TRACKED, spanTrackedOffset, trackedTextAdjust, afterSpace, afterDelete); }

    public TableCell withText(
            CharSequence openMarker,
            CharSequence text,
            CharSequence closeMarker
    ) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, NOT_TRACKED, spanTrackedOffset, trackedTextAdjust, afterSpace, afterDelete); }

    public TableCell withRowSpan(int rowSpan) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace, afterDelete); }

    public TableCell withAlignment(CellAlignment alignment) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace, afterDelete); }

    public TableCell withTrackedOffset(int trackedTextOffset) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace, afterDelete); }

    public TableCell withTrackedOffset(
            int trackedTextOffset,
            boolean afterSpace,
            boolean afterSpaceDelete
    ) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace, afterSpaceDelete); }

    public TableCell withSpanTrackedOffset(int spanTrackedOffset) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace, afterDelete); }

    public TableCell withTrackedTextAdjust(int trackedTextAdjust) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace, afterDelete); }

    public TableCell withAfterSpace(boolean afterSpace) { return new TableCell(openMarker, text, closeMarker, rowSpan, columnSpan, alignment, trackedTextOffset, spanTrackedOffset, trackedTextAdjust, afterSpace, afterDelete); }

    BasedSequence getLastSegment() {
        return !closeMarker.isEmpty() ? closeMarker : text;
    }

    public int getEndOffset() {
        return !closeMarker.isEmpty() ? closeMarker.getEndOffset() : text.getEndOffset();
    }

    public int getStartOffset(TableCell previousCell) {
        return previousCell != null ? previousCell.getEndOffset() : !openMarker.isEmpty() ? openMarker.getStartOffset() : text.getStartOffset();
    }

    public int getInsideStartOffset(TableCell previousCell) {
        return previousCell != null ? previousCell.getEndOffset() : !openMarker.isEmpty() ? openMarker.getEndOffset() : text.getStartOffset();
    }

    public int getTextStartOffset(TableCell previousCell) {
        if (!text.isEmpty()) return text.getStartOffset();
        else if (!openMarker.isEmpty()) return openMarker.getEndOffset() + 1;
        else if (previousCell != null) return previousCell.getEndOffset() + 1;
        else return closeMarker.getStartOffset() - 1;
    }

    public int getTextEndOffset(TableCell previousCell) {
        if (!text.isEmpty()) return text.getEndOffset();
        else if (!openMarker.isEmpty()) return openMarker.getEndOffset() + 1;
        else if (previousCell != null) return previousCell.getEndOffset() + 1;
        else return closeMarker.getStartOffset() - 1;
    }

    public int getInsideEndOffset() {
        return !closeMarker.isEmpty() ? closeMarker.getStartOffset() : text.getEndOffset();
    }

    public int getCellSize(TableCell previousCell) {
        return getEndOffset() - getStartOffset(previousCell);
    }

    public int insideToTextOffset(int insideOffset, TableCell previousCell) {
        return maxLimit(text.length(), minLimit(insideOffset - getInsideStartOffset(previousCell) + getTextStartOffset(previousCell), 0));
    }

    public int textToInsideOffset(int insideOffset, TableCell previousCell) {
        return maxLimit(getCellSize(previousCell), minLimit(insideOffset - getTextStartOffset(previousCell) + getInsideStartOffset(previousCell), 0));
    }

    public boolean isInsideCell(int offset, TableCell previousCell) {
        return offset >= getInsideStartOffset(previousCell) && offset <= getInsideEndOffset();
    }

    public boolean isAtCell(int offset, TableCell previousCell) {
        return offset >= getInsideStartOffset(previousCell) && offset <= getInsideEndOffset();
    }

    /**
     * Returns the cell length occupied in the table
     *
     * @param previousCell previous cell or null for first cell
     *
     * @return length of the cell as occupied in the original file
     */
    public int getCellLength(TableCell previousCell) {
        return getEndOffset() - getStartOffset(previousCell);
    }

    /**
     * Returns the cell prefix length occupied in the table
     *
     * @param previousCell previous cell or null for first cell
     *
     * @return length of cell's prefix before actual text as occupied in the file
     */
    public int getCellPrefixLength(TableCell previousCell) {
        return getInsideStartOffset(previousCell) - getStartOffset(previousCell);
    }

    private CharSequence dumpSequence(BasedSequence sequence) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"").append(sequence.replace("\"", "\\\"")).append("\"")
                .append(" [").append(sequence.getStartOffset()).append(", ").append(sequence.getEndOffset()).append("), length=").append(sequence.length())
                .append("}");
        return sb;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "openMarker=" + dumpSequence(openMarker) +
                ", text=" + dumpSequence(text) +
                ", closeMarker=" + dumpSequence(closeMarker) +
                ", columnSpan=" + columnSpan +
                ", rowSpan=" + rowSpan +
                ", alignment=" + alignment +
                ", trackedTextOffset=" + trackedTextOffset +
                ", spanTrackedOffset=" + spanTrackedOffset +
                ", trackedTextAdjust=" + trackedTextAdjust +
                ", afterSpace=" + afterSpace +
                ", afterDelete=" + afterDelete +
                '}';
    }
}
