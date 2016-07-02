package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.internal.util.collection.DataHolder;

class TocOptions {
    final public boolean parseInvalidLevel;
    final public boolean renderOnlyHeaderText;

    public TocOptions(DataHolder options) {
        this.parseInvalidLevel = options.get(TocExtension.PARSE_INVALID_LEVEL);
        this.renderOnlyHeaderText = options.get(TocExtension.HEADER_TEXT_ONLY);
    }
}
