package com.vladsch.flexmark.ext.xwiki.macros.internal;

import com.vladsch.flexmark.ext.xwiki.macros.MacroExtension;
import com.vladsch.flexmark.util.options.DataHolder;

class MacroOptions {
    public final boolean macrosOption1;
    public final String macrosOption2;
    public final int macrosOption3;

    public MacroOptions(DataHolder options) {
        this.macrosOption1 = MacroExtension.MACROS_OPTION1.getFrom(options);
        this.macrosOption2 = MacroExtension.MACROS_OPTION2.getFrom(options);
        this.macrosOption3 = MacroExtension.MACROS_OPTION3.getFrom(options);
    }
}
