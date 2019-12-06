package com.vladsch.flexmark.util.sequence.edit;

import org.jetbrains.annotations.NotNull;

public class SegmentBuilder2 extends SegmentBuilderBase2<SegmentBuilder2> {
    public SegmentBuilder2() {
    }

    public SegmentBuilder2(int options) {
        super(options);
    }

    @NotNull
    public static SegmentBuilder2 emptyBuilder() {
        return new SegmentBuilder2();
    }

    @NotNull
    public static SegmentBuilder2 emptyBuilder(int options) {
        return new SegmentBuilder2(options);
    }

}
