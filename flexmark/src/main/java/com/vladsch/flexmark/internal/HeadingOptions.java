package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.parser.Parser;

class HeadingOptions {
    final boolean headersNoAtxSpace;
    final boolean headersNoLeadSpace;

    public HeadingOptions(DataHolder options) {
        this.headersNoAtxSpace = options.get(Parser.HEADERS_NO_ATX_SPACE);
        this.headersNoLeadSpace = options.get(Parser.HEADERS_NO_LEAD_SPACE);
    }
}
