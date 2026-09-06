package com.vladsch.flexmark.util.sequence.builder;


public class PlainSegmentBuilder extends SegmentBuilderBase<PlainSegmentBuilder> {
    public PlainSegmentBuilder() {
    }

    public PlainSegmentBuilder(int options) {
        super(options);
    }

    public static PlainSegmentBuilder emptyBuilder() {
        return new PlainSegmentBuilder();
    }

    public static PlainSegmentBuilder emptyBuilder(int options) {
        return new PlainSegmentBuilder(options);
    }
}
