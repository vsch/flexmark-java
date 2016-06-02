package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public class BulletList extends ListBlock {
    private char bulletMarker;

    public BulletList() {
    }

    public BulletList(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public BulletList(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public BulletList(BlockContent blockContent) {
        super(blockContent);
    }

    public BulletList(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public char getBulletMarker() {
        return bulletMarker;
    }

    public void setBulletMarker(char bulletMarker) {
        this.bulletMarker = bulletMarker;
    }

}
