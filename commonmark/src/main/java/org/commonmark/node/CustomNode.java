package org.commonmark.node;

public abstract class CustomNode extends SegmentedNode {
    public CustomNode() {
    }

    public CustomNode(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public CustomNode(int offsetInParent, int textLength, int ... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
