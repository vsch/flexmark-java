package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public class IndentedCodeBlock extends Block {
    public IndentedCodeBlock() {
    }

    public IndentedCodeBlock(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public IndentedCodeBlock(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public IndentedCodeBlock(BlockContent blockContent) {
        super(blockContent);
    }

    public IndentedCodeBlock(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
