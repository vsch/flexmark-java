package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;

import java.util.List;

public class OrderedList extends ListBlock {

    public OrderedList() {
    }

    public OrderedList(BasedSequence chars) {
        super(chars);
    }

    public OrderedList(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public OrderedList(BlockContent blockContent) {
        super(blockContent);
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
