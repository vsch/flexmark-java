package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jspecify.annotations.Nullable;

public class BlankLine extends Block {
    private Block claimedBlankLine = null;

    public boolean isClaimed() {
        return claimedBlankLine != null;
    }

    @Nullable
    public Block getClaimedBlankLine() {
        return claimedBlankLine;
    }

    @SuppressWarnings("UnusedReturnValue")
    public BlankLine setClaimedBlankLine(Block claimedBlankLine) {
        this.claimedBlankLine = claimedBlankLine;
        return this;
    }

    public BlankLine(BasedSequence chars) {
        super(chars);
        setCharsFromContent();
    }

    public BlankLine(BasedSequence chars, Block claimedBlankLine) {
        super(chars);
        setCharsFromContent();
        this.claimedBlankLine = claimedBlankLine;
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }
}
