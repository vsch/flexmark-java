package com.vladsch.flexmark.ext.simtoc.internal;

import com.vladsch.flexmark.ext.simtoc.SimTocExtension;
import com.vladsch.flexmark.internal.util.collection.DataHolder;

class SimTocOptions {
    final public boolean parseInvalidLevel;
    final public boolean renderOnlyHeaderText;

    public SimTocOptions(DataHolder options) {
        this.parseInvalidLevel = options.get(SimTocExtension.PARSE_INVALID_LEVEL);
        this.renderOnlyHeaderText = options.get(SimTocExtension.HEADER_TEXT_ONLY);
    }
}
