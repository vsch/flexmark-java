package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.parser.Parser;

class HeadingOptions {
    final boolean headersNoAtxSpace;
    final boolean headersNoLeadSpace;
    final int setextMarkerLength;

    public HeadingOptions(DataHolder options) {
        this.headersNoAtxSpace = options.get(Parser.HEADING_NO_ATX_SPACE);
        this.headersNoLeadSpace = options.get(Parser.HEADING_NO_LEAD_SPACE);
        this.setextMarkerLength = options.get(Parser.HEADING_SETEXT_MARKER_LENGTH);
    }
}
