package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

/**
 * CharSequence that repeats in a wraparound the given sequence.
 * <p>
 * Partial repeat occurs when start % length() &gt;0 and/or end % length() &gt;0
 * <p>
 * The hashCode is purposefully matched to the string equivalent or this.toString().hashCode()
 */
public class RepeatedCharSequence implements CharSequence {
    public static RepeatedCharSequence NULL = new RepeatedCharSequence(SubSequence.NULL, 0, 0);

    private final CharSequence myChars;
    private final int myStartIndex;
    private final int myEndIndex;
    private int myHash;

    private RepeatedCharSequence(CharSequence chars, int startIndex, int endIndex) {
        myChars = chars;
        myStartIndex = startIndex;
        myEndIndex = endIndex;
    }

    @Override
    public int length() {
        return myEndIndex - myStartIndex;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= myEndIndex - myStartIndex) throw new IndexOutOfBoundsException();
        return myChars.charAt((myStartIndex + index) % myChars.length());
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start >= 0 && start <= end && end <= myEndIndex - myStartIndex) {
            return (start == end) ? NULL : start == myStartIndex && end == myEndIndex ? this : new RepeatedCharSequence(myChars, myStartIndex + start, myStartIndex + end);
        }

        throw new IllegalArgumentException("subSequence($startIndex, $endIndex) in RepeatedCharSequence('', " + myStartIndex + ", " + myEndIndex + ")");
    }

    public RepeatedCharSequence repeat(int count) {
        int endIndex = myStartIndex + (myEndIndex - myStartIndex) * count;
        return myStartIndex >= myEndIndex ? NULL : myEndIndex == endIndex ? this : new RepeatedCharSequence(myChars, myStartIndex, endIndex);
    }

    @Override
    public int hashCode() {
        int h = myHash;
        if (h == 0 && length() > 0) {
            for (int i = 0; i < length(); i++) {
                h = 31 * h + charAt(i);
            }
            myHash = h;
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

    public static RepeatedCharSequence of(char c, int count) {
        return new RepeatedCharSequence(String.valueOf(c), 0, count);
    }

    public static RepeatedCharSequence of(CharSequence chars, int count) {
        return new RepeatedCharSequence(chars, 0, chars.length() * count);
    }

    public static RepeatedCharSequence of(CharSequence chars, int start, int end) {
        return new RepeatedCharSequence(chars, start, end);
    }
}
