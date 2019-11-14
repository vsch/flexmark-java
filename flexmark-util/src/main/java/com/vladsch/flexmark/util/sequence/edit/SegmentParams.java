package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

final public class SegmentParams {
    public final @Nullable Range prevRange;
    public final @NotNull String text;
    public final @Nullable Range nextRange;

    private SegmentParams(@Nullable Range prevRange, @NotNull String text, @Nullable Range nextRange) {
        this.prevRange = prevRange != null && prevRange.isNull() ? null : prevRange;
        this.text = text;
        this.nextRange = nextRange != null && nextRange.isNull() ? null : nextRange;
    }

    public @NotNull SegmentParams with(@Nullable Range prevRange, @NotNull String text, @Nullable Range nextRange) {
        if (prevRange != null && prevRange.isNull()) prevRange = null;
        if (nextRange != null && nextRange.isNull()) nextRange = null;
        if (Objects.equals(this.prevRange, prevRange) && this.text.equals(text) && Objects.equals(this.nextRange, nextRange)) return this;
        else return new SegmentParams(prevRange, text, nextRange);
    }

    @NotNull
    public static SegmentParams of(@Nullable Range prevRange, @NotNull String text, @Nullable Range nextRange) {
        return new SegmentParams(prevRange, text, nextRange);
    }

    @NotNull
    public static SegmentParams of(@NotNull String text, @Nullable Range nextRange) {
        return new SegmentParams(null, text, nextRange);
    }

    @NotNull
    public static SegmentParams of(@Nullable Range prevRange, @NotNull String text) {
        return new SegmentParams(prevRange, text, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SegmentParams params = (SegmentParams) o;

        if (!Objects.equals(prevRange, params.prevRange)) return false;
        if (!text.equals(params.text)) return false;
        return Objects.equals(nextRange, params.nextRange);
    }

    @Override
    public int hashCode() {
        int result = prevRange != null ? prevRange.hashCode() : 0;
        result = 31 * result + text.hashCode();
        result = 31 * result + (nextRange != null ? nextRange.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SegmentParams{prev=" + prevRange + ", text='" + text + '\'' + ", next=" + nextRange + '}';
    }
}
