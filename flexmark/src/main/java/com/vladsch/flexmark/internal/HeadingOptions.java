package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;

class HeadingOptions {
    final boolean noAtxSpace;
    final boolean noLeadSpace;
    final boolean canInterruptItemParagraph;
    final int setextMarkerLength;

    public HeadingOptions(DataHolder options) {
        this.noAtxSpace = Parser.HEADING_NO_ATX_SPACE.getFrom(options);
        this.noLeadSpace = Parser.HEADING_NO_LEAD_SPACE.getFrom(options);
        this.canInterruptItemParagraph = Parser.HEADING_CAN_INTERRUPT_ITEM_PARAGRAPH.getFrom(options);
        this.setextMarkerLength = Parser.HEADING_SETEXT_MARKER_LENGTH.getFrom(options);
    }
}
