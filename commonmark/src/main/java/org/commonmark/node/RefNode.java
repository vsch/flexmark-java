package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SubSequence;

public abstract class RefNode extends Node {
    public BasedSequence textOpeningMarker = SubSequence.EMPTY;
    public BasedSequence text = SubSequence.EMPTY;
    public BasedSequence textClosingMarker = SubSequence.EMPTY;
    public BasedSequence referenceOpeningMarker = SubSequence.EMPTY;
    public BasedSequence reference = SubSequence.EMPTY;
    public BasedSequence referenceClosingMarker = SubSequence.EMPTY;

    public RefNode() {
    }

    public RefNode(BasedSequence chars) {
        super(chars);
    }

    public RefNode(BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence referenceOpeningMarker, BasedSequence reference, BasedSequence referenceClosingMarker) {
        super(new SubSequence(textOpeningMarker.getBase(), textOpeningMarker.getStartOffset(), referenceClosingMarker.getEndOffset()));
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.referenceOpeningMarker = referenceOpeningMarker;
        this.reference = reference;
        this.referenceClosingMarker = referenceClosingMarker;
    }

    public RefNode(BasedSequence chars, BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence referenceOpeningMarker, BasedSequence reference, BasedSequence referenceClosingMarker) {
        super(chars);
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.referenceOpeningMarker = referenceOpeningMarker;
        this.reference = reference;
        this.referenceClosingMarker = referenceClosingMarker;
    }

    public RefNode(BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker) {
        super(new SubSequence(textOpeningMarker.getBase(), textOpeningMarker.getStartOffset(), textClosingMarker.getEndOffset()));
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
    }

    public RefNode(BasedSequence chars, BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker) {
        super(chars);
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
    }

    public RefNode(BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence referenceOpeningMarker, BasedSequence referenceClosingMarker) {
        super(new SubSequence(textOpeningMarker.getBase(), textOpeningMarker.getStartOffset(), referenceClosingMarker.getEndOffset()));
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.referenceOpeningMarker = referenceOpeningMarker;
        this.referenceClosingMarker = referenceClosingMarker;
    }

    public void setReferenceChars(BasedSequence referenceChars) {
        int referenceCharsLength = referenceChars.length();
        this.referenceOpeningMarker = referenceChars.subSequence(0, 1);
        this.reference = referenceChars.subSequence(1, referenceCharsLength - 1).trim();
        this.referenceClosingMarker = referenceChars.subSequence(referenceCharsLength - 1, referenceCharsLength);
    }

    public void setTextChars(BasedSequence textChars) {
        int textCharsLength = textChars.length();
        this.textOpeningMarker = textChars.subSequence(0, 1);
        this.text = textChars.subSequence(1, textCharsLength - 1).trim();
        this.textClosingMarker = textChars.subSequence(textCharsLength - 1, textCharsLength);
    }

    public boolean isReferenceTextCombined() {
        return reference == SubSequence.EMPTY;
    }

    public boolean isDummyReference() {
        return referenceOpeningMarker != SubSequence.EMPTY && reference == SubSequence.EMPTY && referenceClosingMarker != SubSequence.EMPTY;
    }

    public BasedSequence getText() {
        return text;
    }

    public BasedSequence getReference() {
        return reference;
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + text + ", reference=" + reference;
    }

}
