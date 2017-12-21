package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A EnumeratedReference node
 */
public class EnumeratedReferenceText extends EnumeratedReferenceBase {
    public EnumeratedReferenceText() {
    }

    public EnumeratedReferenceText(BasedSequence chars) {
        super(chars);
    }

    public EnumeratedReferenceText(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(openingMarker, text, closingMarker);
    }
}
