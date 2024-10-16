package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKeyBase;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A BasedSequence with offset tracking that follows editing operations and subSequence() chopping
 * as best as it can
 *
 * <p>a subSequence() returns a sub-sequence from the original base sequence with updated offset
 * tracking
 */
public final class BasedOptionsSequence implements CharSequence, BasedOptionsHolder {
  private final @NotNull CharSequence chars;
  private final int optionFlags;
  private final @Nullable DataHolder options;

  private BasedOptionsSequence(
      @NotNull CharSequence chars, int optionFlags, @Nullable DataHolder options) {
    this.chars = chars;
    this.optionFlags =
        optionFlags
            & ~(options == null || SEGMENTED_STATS.get(options) == null
                ? F_COLLECT_SEGMENTED_STATS
                : 0);
    this.options = options;
  }

  @Override
  public int getOptionFlags() {
    return optionFlags;
  }

  @Override
  public boolean allOptions(int options) {
    return (optionFlags & options) == options;
  }

  @Override
  public boolean anyOptions(int options) {
    return (optionFlags & options) != 0;
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
  public int length() {
    return chars.length();
  }

  @Override
  public char charAt(int index) {
    return chars.charAt(index);
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    return chars.subSequence(start, end);
  }

  @Override
  public String toString() {
    return chars.toString();
  }

  @Override
  public IntStream chars() {
    return chars.chars();
  }

  @Override
  public IntStream codePoints() {
    return chars.codePoints();
  }

  @Override
  public boolean equals(Object object) {
    return chars.equals(object);
  }

  @Override
  public int hashCode() {
    return chars.hashCode();
  }

  public static BasedOptionsSequence of(@NotNull CharSequence chars, int optionFlags) {
    return new BasedOptionsSequence(chars, optionFlags, null);
  }

  public static BasedOptionsSequence of(
      @NotNull CharSequence chars, int optionFlags, @Nullable DataHolder options) {
    return new BasedOptionsSequence(chars, optionFlags, options);
  }
}
