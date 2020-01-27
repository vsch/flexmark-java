package com.vladsch.flexmark.ext.macros.internal;

import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import org.jetbrains.annotations.NotNull;

class MacrosOptions implements MutableDataSetter {
    final public boolean sourceWrapMacroReferences;

    public MacrosOptions(DataHolder options) {
        sourceWrapMacroReferences = MacrosExtension.SOURCE_WRAP_MACRO_REFERENCES.get(options);
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        dataHolder.set(MacrosExtension.SOURCE_WRAP_MACRO_REFERENCES, sourceWrapMacroReferences);
        return dataHolder;
    }
}
