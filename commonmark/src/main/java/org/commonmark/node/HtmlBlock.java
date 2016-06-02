package org.commonmark.node;

import org.commonmark.internal.BlockContent;

/**
 * HTML block
 *
 * @see <a href="http://spec.commonmark.org/0.18/#html-blocks">CommonMark Spec</a>
 */
public class HtmlBlock extends Block {
    public HtmlBlock() {
    }

    public HtmlBlock(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public HtmlBlock(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public HtmlBlock(BlockContent blockContent) {
        super(blockContent);
    }

    public HtmlBlock(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
