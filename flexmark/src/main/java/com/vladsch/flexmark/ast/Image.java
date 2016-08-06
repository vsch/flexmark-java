package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class Image extends InlineLinkNode {
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
    public void setTextChars(BasedSequence textChars) {
        int textCharsLength = textChars.length();
        this.textOpeningMarker = textChars.subSequence(0, 2);
        this.text = textChars.subSequence(2, textCharsLength - 1).trim();
        this.textClosingMarker = textChars.subSequence(textCharsLength - 1, textCharsLength);
    }
}
