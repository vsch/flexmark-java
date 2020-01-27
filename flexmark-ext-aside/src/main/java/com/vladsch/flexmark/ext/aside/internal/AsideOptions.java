package com.vladsch.flexmark.ext.aside.internal;

import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.util.data.DataHolder;

class AsideOptions {
    final public boolean extendToBlankLine;
    final public boolean ignoreBlankLine;
    final public boolean allowLeadingSpace;
    final public boolean interruptsParagraph;
    final public boolean interruptsItemParagraph;
    final public boolean withLeadSpacesInterruptsItemParagraph;

    public AsideOptions(DataHolder options) {
        this.extendToBlankLine = AsideExtension.EXTEND_TO_BLANK_LINE.get(options);
        this.ignoreBlankLine = AsideExtension.IGNORE_BLANK_LINE.get(options);
        this.allowLeadingSpace = AsideExtension.ALLOW_LEADING_SPACE.get(options);
        this.interruptsParagraph = AsideExtension.INTERRUPTS_PARAGRAPH.get(options);
        this.interruptsItemParagraph = AsideExtension.INTERRUPTS_ITEM_PARAGRAPH.get(options);
        this.withLeadSpacesInterruptsItemParagraph = AsideExtension.WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH.get(options);
    }
}
