package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SubSequence;

public abstract class RefNode extends LinkNode {
    public BasedSequence textOpeningMarker = SubSequence.NULL;
    public BasedSequence text = SubSequence.NULL;
    public BasedSequence textClosingMarker = SubSequence.NULL;
    public BasedSequence referenceOpeningMarker = SubSequence.NULL;
    public BasedSequence reference = SubSequence.NULL;
    public BasedSequence referenceClosingMarker = SubSequence.NULL;

    public String linkUrl = "";
    public String linkText = "";
    public String linkTitle = null;

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
        return reference == SubSequence.NULL;
    }

    public boolean isDummyReference() {
        return referenceOpeningMarker != SubSequence.NULL && reference == SubSequence.NULL && referenceClosingMarker != SubSequence.NULL;
    }

    public BasedSequence getText() {
        return text;
    }

    public BasedSequence getReference() {
        return reference;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + text + ", reference=" + reference + ", linkUrl=" + linkUrl + ", linkText=" + linkText;
    }
}
