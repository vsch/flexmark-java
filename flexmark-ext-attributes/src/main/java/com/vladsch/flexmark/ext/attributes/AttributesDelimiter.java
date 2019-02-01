package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.ast.DelimitedNode;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.NonRenderingInline;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A empty implicit AttributesNode used to mark attribute span start
 */
public class AttributesDelimiter extends AttributesNode {
    public AttributesDelimiter() {
    }

    public AttributesDelimiter(final BasedSequence chars) {
        super(chars);
    }

    public AttributesDelimiter(final BasedSequence openingMarker, final BasedSequence text, final BasedSequence closingMarker) {
        super(openingMarker, text, closingMarker);
    }

    public AttributesDelimiter(final BasedSequence chars, final String attributesBlockText) {
        super(chars, attributesBlockText);
    }
}
