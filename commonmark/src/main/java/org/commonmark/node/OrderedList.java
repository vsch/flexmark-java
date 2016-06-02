package org.commonmark.node;

import org.commonmark.internal.BlockContent;

public class OrderedList extends ListBlock {
    public OrderedList() {
    }

    public OrderedList(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public OrderedList(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public OrderedList(BlockContent blockContent) {
        super(blockContent);
    }

    public OrderedList(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, blockContent);
    }

    private int startNumber;
    private char delimiter;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public char getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

}
