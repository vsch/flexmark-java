package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.Utils;

public class FormattedCounter {
    private final NumberFormat myFormat;
    private final Boolean myLowercase;
    private final String myDelimiter;
    private int myCount;

    public FormattedCounter(final NumberFormat format, final Boolean lowercase, final String delimiter) {
        myFormat = format;
        myLowercase = lowercase;
        myDelimiter = delimiter;
        reset();
    }

    public void reset() {
        myCount = 0;
    }

    public int getCount() {
        return myCount;
    }

    public int nextCount() {
        return ++myCount;
    }

    public String getFormatted(boolean withDelimiter) {
        String s = NumberFormat.getFormat(myFormat, Utils.minLimit(myCount, 1));
        String o = myLowercase == null ? s : myLowercase ? s.toLowerCase() : s.toUpperCase();
        return withDelimiter && myDelimiter != null && !myDelimiter.isEmpty() ? o + myDelimiter : o;
    }
}
