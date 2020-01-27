package com.vladsch.flexmark.html2md.converter;

import java.util.Locale;

public class ListState {
    final public boolean isNumbered;
    public int itemCount;

    public ListState(boolean isNumbered) {
        this.isNumbered = isNumbered;
        itemCount = 0;
    }

    public String getItemPrefix(HtmlConverterOptions options) {
        if (isNumbered) {
            return String.format(Locale.US, "%d%c ", itemCount, options.orderedListDelimiter);
        } else {
            return String.format("%c ", options.unorderedListDelimiter);
        }
    }
}
