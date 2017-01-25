package com.vladsch.flexmark.util.html;

import java.io.IOException;

public class LengthTrackingAppendableImpl implements LengthTrackingAppendable {
    private final Appendable myAppendable;
    private int myLength;

    public LengthTrackingAppendableImpl(final Appendable appendable) {
        myAppendable = appendable;
        myLength = 0;
    }

    @Override
    public int getLength() {
        return myLength;
    }

    @Override
    public LengthTrackingAppendable append(final CharSequence csq) throws IOException {
        myAppendable.append(csq);
        myLength += csq.length();
        return this;
    }

    @Override
    public LengthTrackingAppendable append(final CharSequence csq, final int start, final int end) throws IOException {
        myAppendable.append(csq, start, end);
        myLength += end - start;
        return this;
    }

    @Override
    public LengthTrackingAppendable append(final char c) throws IOException {
        myAppendable.append(c);
        myLength++;
        return this;
    }

    public Appendable getAppendable() {
        return myAppendable;
    }

    public static LengthTrackingAppendable of(final Appendable appendable) {
        return new LengthTrackingAppendableImpl(appendable);
    }

    @Override
    public String toString() {
        return myAppendable.toString();
    }
}
