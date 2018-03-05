package com.vladsch.flexmark.ext.admonition.internal;

import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Map;

public class AdmonitionOptions {
    public final int contentIndent;
    public final boolean allowLeadingSpace;
    public final boolean interruptsParagraph;
    public final boolean interruptsItemParagraph;
    public final boolean withLeadSpacesInterruptsItemParagraph;
    public final boolean allowLazyContinuation;
    public final String unresolvedQualifier;
    public final Map<String,String> qualifierTypeMap;
    public final Map<String,String> qualifierTitleMap;
    public final Map<String,String> typeSvgMap;

    public AdmonitionOptions(DataHolder options) {
        contentIndent = AdmonitionExtension.CONTENT_INDENT.getFrom(options);
        allowLeadingSpace = AdmonitionExtension.ALLOW_LEADING_SPACE.getFrom(options);
        interruptsParagraph = AdmonitionExtension.INTERRUPTS_PARAGRAPH.getFrom(options);
        interruptsItemParagraph = AdmonitionExtension.INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
        withLeadSpacesInterruptsItemParagraph = AdmonitionExtension.WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
        allowLazyContinuation = AdmonitionExtension.ALLOW_LAZY_CONTINUATION.getFrom(options);
        unresolvedQualifier = AdmonitionExtension.UNRESOLVED_QUALIFIER.getFrom(options);
        qualifierTypeMap = AdmonitionExtension.QUALIFIER_TYPE_MAP.getFrom(options);
        qualifierTitleMap = AdmonitionExtension.QUALIFIER_TITLE_MAP.getFrom(options);
        typeSvgMap = AdmonitionExtension.TYPE_SVG_MAP.getFrom(options);
    }
}
