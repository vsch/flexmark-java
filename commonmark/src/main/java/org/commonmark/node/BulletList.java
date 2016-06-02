package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.util.BasedSequence;

import java.util.List;

public class BulletList extends ListBlock {
    private char bulletMarker;

    public BulletList() {
    }

    public BulletList(BasedSequence chars) {
        super(chars);
    }

    public BulletList(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public BulletList(BlockContent blockContent) {
        super(blockContent);
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
