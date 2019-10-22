package com.vladsch.flexmark.ext.admonition.internal;

import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.Map;

public class AdmonitionOptions {
    public final int contentIndent;
    public final boolean allowLeadingSpace;
    public final boolean interruptsParagraph;
    public final boolean interruptsItemParagraph;
    public final boolean withSpacesInterruptsItemParagraph;
    public final boolean allowLazyContinuation;
    public final String unresolvedQualifier;
    public final Map<String, String> qualifierTypeMap;
    public final Map<String, String> qualifierTitleMap;
    public final Map<String, String> typeSvgMap;

    public AdmonitionOptions(DataHolder options) {
        contentIndent = AdmonitionExtension.CONTENT_INDENT.get(options);
        allowLeadingSpace = AdmonitionExtension.ALLOW_LEADING_SPACE.get(options);
        interruptsParagraph = AdmonitionExtension.INTERRUPTS_PARAGRAPH.get(options);
        interruptsItemParagraph = AdmonitionExtension.INTERRUPTS_ITEM_PARAGRAPH.get(options);
        withSpacesInterruptsItemParagraph = AdmonitionExtension.WITH_SPACES_INTERRUPTS_ITEM_PARAGRAPH.get(options);
        allowLazyContinuation = AdmonitionExtension.ALLOW_LAZY_CONTINUATION.get(options);
        unresolvedQualifier = AdmonitionExtension.UNRESOLVED_QUALIFIER.get(options);
        qualifierTypeMap = AdmonitionExtension.QUALIFIER_TYPE_MAP.get(options);
        qualifierTitleMap = AdmonitionExtension.QUALIFIER_TITLE_MAP.get(options);
        typeSvgMap = AdmonitionExtension.TYPE_SVG_MAP.get(options);
    }
}
