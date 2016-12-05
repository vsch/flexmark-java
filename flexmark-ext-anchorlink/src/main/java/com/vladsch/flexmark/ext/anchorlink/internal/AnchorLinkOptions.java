package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.util.options.DataHolder;

class AnchorLinkOptions {
    public final boolean wrapText;
    public final String textPrefix;
    public final String textSuffix;
    public final String anchorClass;
    public final boolean setName;
    public final boolean setId;
    public final boolean noBlockQuotes;

    public AnchorLinkOptions(DataHolder options) {
        this.wrapText = options.get(AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT);
        this.textPrefix = options.get(AnchorLinkExtension.ANCHORLINKS_TEXT_PREFIX);
        this.textSuffix = options.get(AnchorLinkExtension.ANCHORLINKS_TEXT_SUFFIX);
        this.anchorClass = options.get(AnchorLinkExtension.ANCHORLINKS_ANCHOR_CLASS);
        this.setName = options.get(AnchorLinkExtension.ANCHORLINKS_SET_NAME);
        this.setId = options.get(AnchorLinkExtension.ANCHORLINKS_SET_ID);
        this.noBlockQuotes = options.get(AnchorLinkExtension.ANCHORLINKS_NO_BLOCK_QUOTE);
    }
}
