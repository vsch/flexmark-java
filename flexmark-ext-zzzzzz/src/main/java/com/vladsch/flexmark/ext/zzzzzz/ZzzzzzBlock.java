package com.vladsch.flexmark.ext.zzzzzz;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A Zzzzzz block node
 */
public class ZzzzzzBlock extends Block {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected BasedSequence zzzzzz = BasedSequence.NULL;
    private int zzzzzzOrdinal = 0;
    private int firstReferenceOffset = Integer.MAX_VALUE;

    public int getFirstReferenceOffset() {
        return firstReferenceOffset;
    }

    public void setFirstReferenceOffset(int firstReferenceOffset) {
        this.firstReferenceOffset = firstReferenceOffset;
    }

    public void addFirstReferenceOffset(int firstReferenceOffset) {
        if (this.firstReferenceOffset < firstReferenceOffset) this.firstReferenceOffset = firstReferenceOffset;
    }

    public boolean isReferenced() {
        return this.firstReferenceOffset < Integer.MAX_VALUE;
    }

    public int getZzzzzzOrdinal() {
        return zzzzzzOrdinal;
    }

    public void setZzzzzzOrdinal(int zzzzzzOrdinal) {
        this.zzzzzzOrdinal = zzzzzzOrdinal;
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        out.append(" ordinal: ").append(zzzzzzOrdinal).append(" ");
        segmentSpan(out, openingMarker, "open");
        segmentSpan(out, text, "text");
        segmentSpan(out, closingMarker, "close");
        segmentSpan(out, zzzzzz, "zzzzzz");
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text, closingMarker, zzzzzz };
    }

    public ZzzzzzBlock() {
    }

    public ZzzzzzBlock(BasedSequence chars) {
        super(chars);
    }

    public ZzzzzzBlock(Node node) {
        super();
        appendChild(node);
        this.setCharsFromContent();
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getText() {
        return text;
    }

    public void setText(BasedSequence text) {
        this.text = text;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public BasedSequence getZzzzzz() {
        return zzzzzz;
    }

    public void setZzzzzz(BasedSequence zzzzzz) {
        this.zzzzzz = zzzzzz;
    }
}
