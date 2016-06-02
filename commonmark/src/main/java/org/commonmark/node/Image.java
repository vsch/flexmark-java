package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;

public class Image extends LinkNode {
    public Image() {
    }

    public Image(BasedSequence chars) {
        super(chars);
    }

    public Image(BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker, BasedSequence linkOpenMarker, BasedSequence url, BasedSequence titleOpenMarker, BasedSequence title, BasedSequence titleCloseMarker, BasedSequence linkCloseMarker) {
        super(textOpenMarker, text, textCloseMarker, linkOpenMarker, url, titleOpenMarker, title, titleCloseMarker, linkCloseMarker);
    }

    public Image(BasedSequence chars, BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker, BasedSequence linkOpenMarker, BasedSequence url, BasedSequence titleOpenMarker, BasedSequence title, BasedSequence titleCloseMarker, BasedSequence linkCloseMarker) {
        super(chars, textOpenMarker, text, textCloseMarker, linkOpenMarker, url, titleOpenMarker, title, titleCloseMarker, linkCloseMarker);
    }

    public Image(BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker, BasedSequence linkOpenMarker, BasedSequence url, BasedSequence linkCloseMarker) {
        super(textOpenMarker, text, textCloseMarker, linkOpenMarker, url, linkCloseMarker);
    }

    public Image(BasedSequence chars, BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker, BasedSequence linkOpenMarker, BasedSequence url, BasedSequence linkCloseMarker) {
        super(chars, textOpenMarker, text, textCloseMarker, linkOpenMarker, url, linkCloseMarker);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
