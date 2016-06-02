package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public class BlockQuote extends Block {
    public static int MARKER_SEGMENT = 0;
    public static int CONTENT_SEGMENT = 1;

    public BlockQuote() {
    }

    public BlockQuote(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public BlockQuote(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public BlockQuote(BlockContent blockContent) {
        super(blockContent);
    }

    public BlockQuote(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    @Override
    public CharSequence getContentLineChars(CharSequence charSequence, int line) {
        return getSegmentChars(charSequence, CONTENT_SEGMENT + line*2);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
