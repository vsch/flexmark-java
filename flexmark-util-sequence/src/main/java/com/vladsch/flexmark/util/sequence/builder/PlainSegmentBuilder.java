package com.vladsch.flexmark.util.sequence.builder;

import org.jetbrains.annotations.NotNull;

public class PlainSegmentBuilder extends SegmentBuilderBase<PlainSegmentBuilder> {
    public PlainSegmentBuilder() {
    }

    public PlainSegmentBuilder(int options) {
        super(options);
    }

    @NotNull
    public static PlainSegmentBuilder emptyBuilder() {
        return new PlainSegmentBuilder();
    }

    @NotNull
    public static PlainSegmentBuilder emptyBuilder(int options) {
        return new PlainSegmentBuilder(options);
    }
}
