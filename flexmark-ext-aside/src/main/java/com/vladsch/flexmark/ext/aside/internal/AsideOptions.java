package com.vladsch.flexmark.ext.aside.internal;

import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.util.options.DataHolder;

class AsideOptions {
    public final boolean extendToBlankLine;
    public final boolean ignoreBlankLine;
    public final boolean allowLeadingSpace;
    public final boolean interruptsParagraph;
    public final boolean interruptsItemParagraph;
    public final boolean withLeadSpacesInterruptsItemParagraph;

    public AsideOptions(DataHolder options) {
        this.extendToBlankLine = AsideExtension.EXTEND_TO_BLANK_LINE.getFrom(options);
        this.ignoreBlankLine = AsideExtension.IGNORE_BLANK_LINE.getFrom(options);
        this.allowLeadingSpace = AsideExtension.ALLOW_LEADING_SPACE.getFrom(options);
        this.interruptsParagraph = AsideExtension.INTERRUPTS_PARAGRAPH.getFrom(options);
        this.interruptsItemParagraph = AsideExtension.INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
        this.withLeadSpacesInterruptsItemParagraph = AsideExtension.WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
    }
}
