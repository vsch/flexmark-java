package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.util.options.DataHolder;

public class FootnoteOptions {
    final String footnoteRefPrefix;
    final String footnoteRefSuffix;
    final String footnoteBackRefString;
    final String footnoteLinkRefClass;
    final String footnoteBackLinkRefClass;

    public FootnoteOptions(DataHolder options) {
        this.footnoteRefPrefix = options.get(FootnoteExtension.FOOTNOTE_REF_PREFIX);
        this.footnoteRefSuffix = options.get(FootnoteExtension.FOOTNOTE_REF_SUFFIX);
        this.footnoteBackRefString = options.get(FootnoteExtension.FOOTNOTE_BACK_REF_STRING);
        this.footnoteLinkRefClass = options.get(FootnoteExtension.FOOTNOTE_LINK_REF_CLASS);
        this.footnoteBackLinkRefClass = options.get(FootnoteExtension.FOOTNOTE_BACK_LINK_REF_CLASS);
    }
}
