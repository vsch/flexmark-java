package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;

class HeadingOptions {
    final boolean headersNoAtxSpace;
    final boolean headersNoLeadSpace;
    final int setextMarkerLength;

    public HeadingOptions(DataHolder options) {
        this.headersNoAtxSpace = Parser.HEADING_NO_ATX_SPACE.getFrom(options);
        this.headersNoLeadSpace = Parser.HEADING_NO_LEAD_SPACE.getFrom(options);
        this.setextMarkerLength = Parser.HEADING_SETEXT_MARKER_LENGTH.getFrom(options);
    }
}
