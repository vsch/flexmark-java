package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.internal.util.options.DataHolder;

class AnchorLinkOptions {
    final public boolean wrapText;
    final public String textPrefix;
    final public String textSuffix;
    final public String anchorClass;
    final public boolean setName;
    final public boolean setId;
    final public boolean noBlockQuotes;

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
