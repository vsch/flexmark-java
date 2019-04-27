package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.util.data.DataHolder;

class AnchorLinkOptions {
    public final boolean wrapText;
    public final String textPrefix;
    public final String textSuffix;
    public final String anchorClass;
    public final boolean setName;
    public final boolean setId;
    public final boolean noBlockQuotes;

    public AnchorLinkOptions(DataHolder options) {
        this.wrapText = AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT.getFrom(options);
        this.textPrefix = AnchorLinkExtension.ANCHORLINKS_TEXT_PREFIX.getFrom(options);
        this.textSuffix = AnchorLinkExtension.ANCHORLINKS_TEXT_SUFFIX.getFrom(options);
        this.anchorClass = AnchorLinkExtension.ANCHORLINKS_ANCHOR_CLASS.getFrom(options);
        this.setName = AnchorLinkExtension.ANCHORLINKS_SET_NAME.getFrom(options);
        this.setId = AnchorLinkExtension.ANCHORLINKS_SET_ID.getFrom(options);
        this.noBlockQuotes = AnchorLinkExtension.ANCHORLINKS_NO_BLOCK_QUOTE.getFrom(options);
    }
}
