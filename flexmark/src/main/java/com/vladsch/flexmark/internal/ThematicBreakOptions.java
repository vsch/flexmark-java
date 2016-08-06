package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;

class ThematicBreakOptions {
    final boolean relaxedStart;

    public ThematicBreakOptions(DataHolder options) {
        this.relaxedStart = options.get(Parser.THEMATIC_BREAK_RELAXED_START);
    }
}
