package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.attributes.FencedCodeAddType;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;

class AttributesOptions implements MutableDataSetter {
    public final boolean assignTextAttributes;
    public final boolean wrapNonAttributeText;
    public final boolean useEmptyImplicitAsSpanDelimiter;
    public final boolean fencedCodeInfoAttributes;
    public final FencedCodeAddType fencedCodeAddAttributes;

    public AttributesOptions(DataHolder options) {
        assignTextAttributes = AttributesExtension.ASSIGN_TEXT_ATTRIBUTES.getFrom(options);
        wrapNonAttributeText = AttributesExtension.WRAP_NON_ATTRIBUTE_TEXT.getFrom(options);
        useEmptyImplicitAsSpanDelimiter = AttributesExtension.USE_EMPTY_IMPLICIT_AS_SPAN_DELIMITER.getFrom(options);
        fencedCodeInfoAttributes = AttributesExtension.FENCED_CODE_INFO_ATTRIBUTES.getFrom(options);
        fencedCodeAddAttributes = AttributesExtension.FENCED_CODE_ADD_ATTRIBUTES.getFrom(options);
    }

    @Override
    public MutableDataHolder setIn(MutableDataHolder dataHolder) {
        dataHolder.set(AttributesExtension.ASSIGN_TEXT_ATTRIBUTES, assignTextAttributes);
        dataHolder.set(AttributesExtension.WRAP_NON_ATTRIBUTE_TEXT, wrapNonAttributeText);
        dataHolder.set(AttributesExtension.USE_EMPTY_IMPLICIT_AS_SPAN_DELIMITER, useEmptyImplicitAsSpanDelimiter);
        dataHolder.set(AttributesExtension.FENCED_CODE_INFO_ATTRIBUTES, fencedCodeInfoAttributes);
        dataHolder.set(AttributesExtension.FENCED_CODE_ADD_ATTRIBUTES, fencedCodeAddAttributes);
        return dataHolder;
    }
}
