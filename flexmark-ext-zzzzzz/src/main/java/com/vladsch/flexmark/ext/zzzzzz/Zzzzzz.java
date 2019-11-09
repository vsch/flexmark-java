package com.vladsch.flexmark.ext.zzzzzz;

import com.vladsch.flexmark.util.ast.DelimitedNode;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A Zzzzzz node
 */
public class Zzzzzz extends Node implements DelimitedNode, DoNotDecorate {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected ZzzzzzBlock zzzzzzBlock;
    protected String zzzzzzBlockText;

    public ZzzzzzBlock getZzzzzzBlock() {
        return zzzzzzBlock;
    }

    public void setZzzzzzBlock(ZzzzzzBlock zzzzzzBlock) {
        this.zzzzzzBlock = zzzzzzBlock;
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { openingMarker, text, closingMarker };
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        out.append(" ordinal: ").append(zzzzzzBlock != null ? zzzzzzBlock.getZzzzzzOrdinal() : 0).append(" ");
        delimitedSegmentSpanChars(out, openingMarker, text, closingMarker, "text");
    }

    public Zzzzzz() {
    }

    public Zzzzzz(BasedSequence chars) {
        super(chars);
    }

    public Zzzzzz(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(openingMarker.baseSubSequence(openingMarker.getStartOffset(), closingMarker.getEndOffset()));
        this.openingMarker = openingMarker;
        this.text = text;
        this.closingMarker = closingMarker;
    }

    public Zzzzzz(BasedSequence chars, String zzzzzzBlockText) {
        super(chars);
        this.zzzzzzBlockText = zzzzzzBlockText;
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
}
