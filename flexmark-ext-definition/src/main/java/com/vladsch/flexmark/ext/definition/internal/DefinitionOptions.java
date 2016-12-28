package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.util.options.DataHolder;

class DefinitionOptions {
    public final boolean definitionOption1;

    public DefinitionOptions(DataHolder options) {
        this.definitionOption1 = options.get(DefinitionExtension.NO_TERM_TRAILING_COLON);
    }
}
