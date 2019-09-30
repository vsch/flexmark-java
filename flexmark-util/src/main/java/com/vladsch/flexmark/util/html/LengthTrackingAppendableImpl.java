package com.vladsch.flexmark.util.html;

import java.io.IOException;

/**
 * @deprecated Not used and will be removed
 */
@Deprecated
public class LengthTrackingAppendableImpl implements LengthTrackingAppendable {
    private final Appendable myAppendable;
    private int myLength;

    public LengthTrackingAppendableImpl(Appendable appendable) {
        myAppendable = appendable;
        myLength = 0;
    }

    @Override
    public int getLength() {
        return myLength;
    }

    @Override
    public LengthTrackingAppendable append(CharSequence csq) throws IOException {
        myAppendable.append(csq);
        myLength += csq.length();
        return this;
    }

    @Override
    public LengthTrackingAppendable append(CharSequence csq, int start, int end) throws IOException {
        myAppendable.append(csq, start, end);
        myLength += end - start;
        return this;
    }

    @Override
    public LengthTrackingAppendable append(char c) throws IOException {
        myAppendable.append(c);
        myLength++;
        return this;
    }

    public Appendable getAppendable() {
        return myAppendable;
    }

    public static LengthTrackingAppendable of(Appendable appendable) {
        return new LengthTrackingAppendableImpl(appendable);
    }

    @Override
    public String toString() {
        return myAppendable.toString();
    }
}
