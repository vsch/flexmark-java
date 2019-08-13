package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A empty implicit AttributesNode used to mark attribute span start
 */
public class AttributesDelimiter extends AttributesNode {
    public AttributesDelimiter() {
    }

    public AttributesDelimiter(BasedSequence chars) {
        super(chars);
    }

    public AttributesDelimiter(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(openingMarker, text, closingMarker);
    }

    public AttributesDelimiter(BasedSequence chars, String attributesBlockText) {
        super(chars, attributesBlockText);
    }
}
