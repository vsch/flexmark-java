package com.vladsch.flexmark.util.sequence;

public class TagRange extends Range {
    protected final String tag;

    public static TagRange of(String tag, int start, int end) {
        return new TagRange(tag, start, end);
    }

    public TagRange(String tag, Range range) {
        super(range);
        this.tag = tag;
    }

    public TagRange(String tag, int start, int end) {
        super(start, end);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
    
    public TagRange withTag(String tag) {
        return tag.equals(getTag()) ? this : new TagRange(tag, getStart(), getEnd());
    }

    @Override
    public TagRange withStart(int start) {
        return start == getStart() ? this : new TagRange(getTag(), start, getEnd());
    }

    @Override
    public TagRange withEnd(int end) {
        return end == getEnd() ? this : new TagRange(getTag(), getStart(), end);
    }

    @Override
    public TagRange withRange(int start, int end) {
        return start == getStart() && end == getEnd() ? this : new TagRange(getTag(), start, end);
    }
}
