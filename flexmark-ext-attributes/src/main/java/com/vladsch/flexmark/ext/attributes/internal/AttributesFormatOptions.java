package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributeValueQuotes;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import org.jetbrains.annotations.NotNull;

class AttributesFormatOptions implements MutableDataSetter {
    public final boolean attributesCombineConsecutive;
    public final DiscretionaryText attributesSpaces;
    public final DiscretionaryText attributeEqualSpace;
    public final AttributeValueQuotes attributeValueQuotes;

    public AttributesFormatOptions(DataHolder options) {
        attributesCombineConsecutive = AttributesExtension.FORMAT_ATTRIBUTES_COMBINE_CONSECUTIVE.get(options);
        attributesSpaces = AttributesExtension.FORMAT_ATTRIBUTES_SPACES.get(options);
        attributeEqualSpace = AttributesExtension.FORMAT_ATTRIBUTE_EQUAL_SPACE.get(options);
        attributeValueQuotes = AttributesExtension.FORMAT_ATTRIBUTE_VALUE_QUOTES.get(options);
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        dataHolder.set(AttributesExtension.FORMAT_ATTRIBUTES_COMBINE_CONSECUTIVE, attributesCombineConsecutive);
        dataHolder.set(AttributesExtension.FORMAT_ATTRIBUTES_SPACES, attributesSpaces);
        dataHolder.set(AttributesExtension.FORMAT_ATTRIBUTE_EQUAL_SPACE, attributeEqualSpace);
        dataHolder.set(AttributesExtension.FORMAT_ATTRIBUTE_VALUE_QUOTES, attributeValueQuotes);
        return dataHolder;
    }
}
