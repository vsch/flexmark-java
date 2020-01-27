package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.misc.Utils;

public class FormattedCounter {
    final private NumberFormat numberFormat;
    final private Boolean isLowercase;
    final private String delimiter;
    private int count;

    public FormattedCounter(NumberFormat format, Boolean lowercase, String delimiter) {
        numberFormat = format;
        isLowercase = lowercase;
        this.delimiter = delimiter;
        reset();
    }

    public void reset() {
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public int nextCount() {
        return ++count;
    }

    public String getFormatted(boolean withDelimiter) {
        String s = NumberFormat.getFormat(numberFormat, Utils.minLimit(count, 1));
        String o = isLowercase == null ? s : isLowercase ? s.toLowerCase() : s.toUpperCase();
        return withDelimiter && delimiter != null && !delimiter.isEmpty() ? o + delimiter : o;
    }
}
