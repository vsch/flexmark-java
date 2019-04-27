package com.vladsch.flexmark.ext.macros.internal;

import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;

class MacrosOptions implements MutableDataSetter {
    public final boolean sourceWrapMacroReferences;

    public MacrosOptions(DataHolder options) {
        sourceWrapMacroReferences = MacrosExtension.SOURCE_WRAP_MACRO_REFERENCES.getFrom(options);
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        dataHolder.set(MacrosExtension.SOURCE_WRAP_MACRO_REFERENCES, sourceWrapMacroReferences);
        return dataHolder;
    }
}
