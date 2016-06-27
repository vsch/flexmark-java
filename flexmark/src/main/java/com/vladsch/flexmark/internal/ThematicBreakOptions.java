package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.parser.Parser;

class ThematicBreakOptions {
    final boolean relaxedStart;

    public ThematicBreakOptions(DataHolder options) {
        this.relaxedStart = options.get(Parser.THEMATIC_BREAK_RELAXED_START);
    }
}
