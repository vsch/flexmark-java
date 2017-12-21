package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

class AttributesOptions implements MutableDataSetter {
    //public final boolean attributesOption1;

    public AttributesOptions(DataHolder options) {
        //attributesOption1 = AttributesExtension.ATTRIBUTES_OPTION1.getFrom(options);
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        //dataHolder.set(AttributesExtension.ATTRIBUTES_OPTION1,attributesOption1);
        return dataHolder;
    }
}
