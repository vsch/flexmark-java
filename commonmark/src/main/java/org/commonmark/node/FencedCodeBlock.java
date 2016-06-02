package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public class FencedCodeBlock extends Block {
    private int fenceIndent;

    public FencedCodeBlock() {
    }

    public FencedCodeBlock(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getInfoOffset() {
        return getSegmentEndOffset(0);
    }

    public int getContentOffset() {
        return getSegmentStartOffset(1);
    }

    public int getCloseFenceOffset() {
        return getSegmentStartOffset(segmentOffsets.length);
    }

    public CharSequence getOpeningFence(CharSequence charSequence) {
        return getSegmentChars(charSequence, 0);
    }

    /**
     * @see <a href="http://spec.commonmark.org/0.18/#info-string">CommonMark spec</a>
     */
    public CharSequence getInfo(CharSequence charSequence) {
        return getSegmentChars(charSequence, 1);
    }

    @Override
    public CharSequence getContentChars(CharSequence charSequence) {
        return charSequence.subSequence(getSegmentStartOffset(2), getSegmentEndOffset(segmentOffsets.length - 1));
    }

    public CharSequence getClosingFence(CharSequence charSequence) {
        return getSegmentChars(charSequence, segmentOffsets.length);
    }

    @Override
    public CharSequence getContentLineChars(CharSequence charSequence, int line) {
        return getSegmentChars(charSequence, 1 + (line + 1) * 2);
    }

    public int getFenceLength() {
        return getInfoOffset();
    }

    public int getFenceIndent() {
        return fenceIndent;
    }

    public void setFenceIndent(int fenceIndent) {
        this.fenceIndent = fenceIndent;
    }
}
