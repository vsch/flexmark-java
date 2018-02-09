package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

class AttributesOptions implements MutableDataSetter {
    public final boolean assignTextAttributes;

    public AttributesOptions(DataHolder options) {
        assignTextAttributes = AttributesExtension.ASSIGN_TEXT_ATTRIBUTES.getFrom(options);
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        dataHolder.set(AttributesExtension.ASSIGN_TEXT_ATTRIBUTES, assignTextAttributes);
        return dataHolder;
    }
}
