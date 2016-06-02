package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;

public class ImageRef extends RefNode {
    public ImageRef() {
    }

    public ImageRef(BasedSequence chars) {
        super(chars);
    }

    public ImageRef(BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker, BasedSequence referenceOpenMarker, BasedSequence reference, BasedSequence referenceCloseMarker) {
        super(textOpenMarker, text, textCloseMarker, referenceOpenMarker, reference, referenceCloseMarker);
    }

    public ImageRef(BasedSequence chars, BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker, BasedSequence referenceOpenMarker, BasedSequence reference, BasedSequence referenceCloseMarker) {
        super(chars, textOpenMarker, text, textCloseMarker, referenceOpenMarker, reference, referenceCloseMarker);
    }

    public ImageRef(BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker) {
        super(textOpenMarker, text, textCloseMarker);
    }

    public ImageRef(BasedSequence chars, BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker) {
        super(chars, textOpenMarker, text, textCloseMarker);
    }

    public ImageRef(BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker, BasedSequence referenceOpenMarker, BasedSequence referenceCloseMarker) {
        super(textOpenMarker, text, textCloseMarker, referenceOpenMarker, referenceCloseMarker);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
