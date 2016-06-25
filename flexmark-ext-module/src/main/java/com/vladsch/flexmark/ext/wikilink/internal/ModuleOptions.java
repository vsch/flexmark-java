package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.ModuleExtension;
import com.vladsch.flexmark.internal.util.DataHolder;

class ModuleOptions {
    final public boolean moduleOption1;
    final public String moduleOption2;
    final public int moduleOption3;

    public ModuleOptions(DataHolder options) {
        this.moduleOption1 = options.get(ModuleExtension.MODULE_OPTION1);
        this.moduleOption2 = options.get(ModuleExtension.MODULE_OPTION2);
        this.moduleOption3 = options.get(ModuleExtension.MODULE_OPTION3);
    }
}
