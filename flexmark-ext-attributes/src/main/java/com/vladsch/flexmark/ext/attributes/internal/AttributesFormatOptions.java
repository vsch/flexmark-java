package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributeImplicitName;
import com.vladsch.flexmark.ext.attributes.AttributeValueQuotes;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import org.jetbrains.annotations.NotNull;

class AttributesFormatOptions implements MutableDataSetter {
    final public boolean attributesCombineConsecutive;
    final public boolean attributesSort;
    final public DiscretionaryText attributesSpaces;
    final public DiscretionaryText attributeEqualSpace;
    final public AttributeValueQuotes attributeValueQuotes;
    final public AttributeImplicitName attributeIdFormat;
    final public AttributeImplicitName attributeClassFormat;

    public AttributesFormatOptions(DataHolder options) {
        attributesCombineConsecutive = AttributesExtension.FORMAT_ATTRIBUTES_COMBINE_CONSECUTIVE.get(options);
        attributesSort = AttributesExtension.FORMAT_ATTRIBUTES_SORT.get(options);
        attributesSpaces = AttributesExtension.FORMAT_ATTRIBUTES_SPACES.get(options);
        attributeEqualSpace = AttributesExtension.FORMAT_ATTRIBUTE_EQUAL_SPACE.get(options);
        attributeValueQuotes = AttributesExtension.FORMAT_ATTRIBUTE_VALUE_QUOTES.get(options);
        attributeIdFormat = AttributesExtension.FORMAT_ATTRIBUTE_ID.get(options);
        attributeClassFormat = AttributesExtension.FORMAT_ATTRIBUTE_CLASS.get(options);
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        dataHolder.set(AttributesExtension.FORMAT_ATTRIBUTES_COMBINE_CONSECUTIVE, attributesCombineConsecutive);
        dataHolder.set(AttributesExtension.FORMAT_ATTRIBUTES_SORT, attributesSort);
        dataHolder.set(AttributesExtension.FORMAT_ATTRIBUTES_SPACES, attributesSpaces);
        dataHolder.set(AttributesExtension.FORMAT_ATTRIBUTE_EQUAL_SPACE, attributeEqualSpace);
        dataHolder.set(AttributesExtension.FORMAT_ATTRIBUTE_VALUE_QUOTES, attributeValueQuotes);
        dataHolder.set(AttributesExtension.FORMAT_ATTRIBUTE_ID, attributeIdFormat);
        dataHolder.set(AttributesExtension.FORMAT_ATTRIBUTE_CLASS, attributeClassFormat);
        return dataHolder;
    }
}
