package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;

public class Link extends LinkNode {
    public Link() {
    }

    public Link(BasedSequence chars) {
        super(chars);
    }

    public Link(BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker, BasedSequence linkOpenMarker, BasedSequence url, BasedSequence titleOpenMarker, BasedSequence title, BasedSequence titleCloseMarker, BasedSequence linkCloseMarker) {
        super(textOpenMarker, text, textCloseMarker, linkOpenMarker, url, titleOpenMarker, title, titleCloseMarker, linkCloseMarker);
    }

    public Link(BasedSequence chars, BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker, BasedSequence linkOpenMarker, BasedSequence url, BasedSequence titleOpenMarker, BasedSequence title, BasedSequence titleCloseMarker, BasedSequence linkCloseMarker) {
        super(chars, textOpenMarker, text, textCloseMarker, linkOpenMarker, url, titleOpenMarker, title, titleCloseMarker, linkCloseMarker);
    }

    public Link(BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker, BasedSequence linkOpenMarker, BasedSequence url, BasedSequence linkCloseMarker) {
        super(textOpenMarker, text, textCloseMarker, linkOpenMarker, url, linkCloseMarker);
    }

    public Link(BasedSequence chars, BasedSequence textOpenMarker, BasedSequence text, BasedSequence textCloseMarker, BasedSequence linkOpenMarker, BasedSequence url, BasedSequence linkCloseMarker) {
        super(chars, textOpenMarker, text, textCloseMarker, linkOpenMarker, url, linkCloseMarker);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
