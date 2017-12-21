package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A EnumeratedReference node
 */
public class EnumeratedReferenceLink extends EnumeratedReferenceBase {
    public EnumeratedReferenceLink() {
    }

    public EnumeratedReferenceLink(BasedSequence chars) {
        super(chars);
    }

    public EnumeratedReferenceLink(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(openingMarker, text, closingMarker);
    }
}
