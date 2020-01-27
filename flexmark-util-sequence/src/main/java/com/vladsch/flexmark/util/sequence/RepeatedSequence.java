package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

/**
 * CharSequence that repeats in a wraparound the given sequence.
 * <p>
 * Partial repeat occurs when start % length() &gt;0 and/or end % length() &gt;0
 * <p>
 * The hashCode is purposefully matched to the string equivalent or this.toString().hashCode()
 */
public class RepeatedSequence implements CharSequence {
    public static RepeatedSequence NULL = new RepeatedSequence(BasedSequence.NULL, 0, 0);

    final private CharSequence chars;
    final private int startIndex;
    final private int endIndex;
    private int hashCode;

    private RepeatedSequence(CharSequence chars, int startIndex, int endIndex) {
        this.chars = chars;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public int length() {
        return endIndex - startIndex;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= endIndex - startIndex) throw new IndexOutOfBoundsException();
        return chars.charAt((startIndex + index) % chars.length());
    }

    @Override
    public CharSequence subSequence(int startIndex, int endIndex) {
        if (startIndex >= 0 && startIndex <= endIndex && endIndex <= this.endIndex - this.startIndex) {
            return (startIndex == endIndex) ? NULL : startIndex == this.startIndex && endIndex == this.endIndex ? this : new RepeatedSequence(chars, this.startIndex + startIndex, this.startIndex + endIndex);
        }

        throw new IllegalArgumentException("subSequence($startIndex, $endIndex) in RepeatedCharSequence('', " + this.startIndex + ", " + this.endIndex + ")");
    }

    public RepeatedSequence repeat(int count) {
        int endIndex = startIndex + (this.endIndex - startIndex) * count;
        return startIndex >= this.endIndex ? NULL : this.endIndex == endIndex ? this : new RepeatedSequence(chars, startIndex, endIndex);
    }

    @Override
    public int hashCode() {
        int h = hashCode;
        if (h == 0 && length() > 0) {
            for (int i = 0; i < length(); i++) {
                h = 31 * h + charAt(i);
            }
            hashCode = h;
        }
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof CharSequence && toString().equals(obj.toString()));
    }

    @NotNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this, 0, length());
        return sb.toString();
    }

    @NotNull
    public static CharSequence ofSpaces(int count) {
        return new RepeatedSequence(" ", 0, count);
    }

    @NotNull
    public static CharSequence repeatOf(char c, int count) {
        return new RepeatedSequence(String.valueOf(c), 0, count);
    }

    @NotNull
    public static CharSequence repeatOf(@NotNull CharSequence chars, int count) {
        return new RepeatedSequence(chars, 0, chars.length() * count);
    }

    @NotNull
    public static CharSequence repeatOf(@NotNull CharSequence chars, int startIndex, int endIndex) {
        return new RepeatedSequence(chars, startIndex, endIndex);
    }

    @NotNull
    @Deprecated
    public static CharSequence of(char c, int count) {
        return repeatOf(c, count);
    }

    @NotNull
    @Deprecated
    public static CharSequence of(@NotNull CharSequence chars, int count) {
        return repeatOf(chars, count);
    }

    @NotNull
    @Deprecated
    public static CharSequence of(@NotNull CharSequence chars, int startIndex, int endIndex) {
        return repeatOf(chars, startIndex, endIndex);
    }
}
