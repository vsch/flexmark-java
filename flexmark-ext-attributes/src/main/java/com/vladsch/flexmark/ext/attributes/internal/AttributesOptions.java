package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.attributes.FencedCodeAddType;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import org.jetbrains.annotations.NotNull;

class AttributesOptions implements MutableDataSetter {
    final public boolean assignTextAttributes;
    final public boolean wrapNonAttributeText;
    final public boolean useEmptyImplicitAsSpanDelimiter;
    final public boolean fencedCodeInfoAttributes;
    final public FencedCodeAddType fencedCodeAddAttributes;

    public AttributesOptions(DataHolder options) {
        assignTextAttributes = AttributesExtension.ASSIGN_TEXT_ATTRIBUTES.get(options);
        wrapNonAttributeText = AttributesExtension.WRAP_NON_ATTRIBUTE_TEXT.get(options);
        useEmptyImplicitAsSpanDelimiter = AttributesExtension.USE_EMPTY_IMPLICIT_AS_SPAN_DELIMITER.get(options);
        fencedCodeInfoAttributes = AttributesExtension.FENCED_CODE_INFO_ATTRIBUTES.get(options);
        fencedCodeAddAttributes = AttributesExtension.FENCED_CODE_ADD_ATTRIBUTES.get(options);
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        dataHolder.set(AttributesExtension.ASSIGN_TEXT_ATTRIBUTES, assignTextAttributes);
        dataHolder.set(AttributesExtension.WRAP_NON_ATTRIBUTE_TEXT, wrapNonAttributeText);
        dataHolder.set(AttributesExtension.USE_EMPTY_IMPLICIT_AS_SPAN_DELIMITER, useEmptyImplicitAsSpanDelimiter);
        dataHolder.set(AttributesExtension.FENCED_CODE_INFO_ATTRIBUTES, fencedCodeInfoAttributes);
        dataHolder.set(AttributesExtension.FENCED_CODE_ADD_ATTRIBUTES, fencedCodeAddAttributes);
        return dataHolder;
    }
}
