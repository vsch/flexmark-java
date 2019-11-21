package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKeyBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

/**
 * A BasedSequence with offset tracking that follows editing operations and subSequence() chopping as best as it can
 * <p>
 * a subSequence() returns a sub-sequence from the original base sequence with updated offset tracking
 */
public final class BasedOptionsSequence implements CharSequence, BasedOptionsHolder {
    final private @NotNull CharSequence chars;
    final private int optionFlags;
    final private @Nullable DataHolder options;

    public BasedOptionsSequence(@NotNull CharSequence chars, int optionFlags) {
        this(chars, optionFlags, null);
    }

    public BasedOptionsSequence(@NotNull CharSequence chars, int optionFlags, @Nullable DataHolder options) {
        this.chars = chars;
        this.optionFlags = optionFlags;
        this.options = options;
    }

    @Override
    public boolean isOption(int option) {
        return (optionFlags & option) != 0;
    }

    @Override
    public <T> T getOption(DataKeyBase<T> dataKey) {
        return dataKey.get(options);
    }

    @Nullable
    @Override
    public DataHolder getOptions() {
        return options;
    }

    @Override
    public int length() {return chars.length();}

    @Override
    public char charAt(int index) {return chars.charAt(index);}

    @Override
    public CharSequence subSequence(int start, int end) {return chars.subSequence(start, end);}

    @Override
    public String toString() {return chars.toString();}

    @Override
    public IntStream chars() {return chars.chars();}

    @Override
    public IntStream codePoints() {return chars.codePoints();}

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object o) {
        return chars.equals(o);
    }

    @Override
    public int hashCode() {
        return chars.hashCode();
    }
}
