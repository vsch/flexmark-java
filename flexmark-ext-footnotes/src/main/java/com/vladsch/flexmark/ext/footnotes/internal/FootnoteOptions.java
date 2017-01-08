package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;

public class FootnoteOptions {
    final String footnoteRefPrefix;
    final String footnoteRefSuffix;
    final String footnoteBackRefString;
    final String footnoteLinkRefClass;
    final String footnoteBackLinkRefClass;
    final int contentIndent;

    public FootnoteOptions(DataHolder options) {
        this.footnoteRefPrefix = FootnoteExtension.FOOTNOTE_REF_PREFIX.getFrom(options);
        this.footnoteRefSuffix = FootnoteExtension.FOOTNOTE_REF_SUFFIX.getFrom(options);
        this.footnoteBackRefString = FootnoteExtension.FOOTNOTE_BACK_REF_STRING.getFrom(options);
        this.footnoteLinkRefClass = FootnoteExtension.FOOTNOTE_LINK_REF_CLASS.getFrom(options);
        this.footnoteBackLinkRefClass = FootnoteExtension.FOOTNOTE_BACK_LINK_REF_CLASS.getFrom(options);
        this.contentIndent = Parser.LISTS_ITEM_INDENT.getFrom(options);
    }
}
