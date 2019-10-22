package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;

public class EnumeratedReferenceOptions {
    final int contentIndent;

    public EnumeratedReferenceOptions(DataHolder options) {
        this.contentIndent = Parser.LISTS_ITEM_INDENT.get(options);
    }
}
