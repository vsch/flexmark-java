package com.vladsch.flexmark.util.sequence.edit;

import org.jetbrains.annotations.NotNull;

public class SegmentBuilder extends SegmentBuilderBase<SegmentBuilder> {
    public SegmentBuilder() {
    }

    public SegmentBuilder(int options) {
        super(options);
    }

    public SegmentBuilder(@NotNull SegmentBuilderBase<SegmentBuilder> other) {
        super(other);
    }

    @NotNull
    public static SegmentBuilder emptyBuilder() {
        return new SegmentBuilder();
    }

    @NotNull
    public static SegmentBuilder emptyBuilder(int options) {
        return new SegmentBuilder(options);
    }

}
