package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.ReferenceRepository;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.parser.Parser;

public abstract class RefNode extends LinkNode {
    protected BasedSequence textOpeningMarker = SubSequence.NULL;
    protected BasedSequence text = SubSequence.NULL;
    protected BasedSequence textClosingMarker = SubSequence.NULL;
    protected BasedSequence referenceOpeningMarker = SubSequence.NULL;
    protected BasedSequence reference = SubSequence.NULL;
    protected BasedSequence referenceClosingMarker = SubSequence.NULL;

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] {
                textOpeningMarker,
                text,
                textClosingMarker,
                referenceOpeningMarker,
                reference,
                referenceClosingMarker
        };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        delimitedSegmentSpanChars(out, textOpeningMarker, text, textClosingMarker, "text");
        delimitedSegmentSpanChars(out, referenceOpeningMarker, reference, referenceClosingMarker, "reference");
    }

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
        return text == SubSequence.NULL;
    }

    public boolean isDummyReference() {
        return textOpeningMarker != SubSequence.NULL && text == SubSequence.NULL && textClosingMarker != SubSequence.NULL;
    }

    public BasedSequence getText() {
        return text;
    }

    public BasedSequence getReference() {
        return reference;
    }
    
    public Reference getReferenceNode() {
        ReferenceRepository repository = getDocument().get(Parser.REFERENCES);
        return repository == null ? null : repository.get(repository.normalizeKey(reference));
    }

    public BasedSequence getTextOpeningMarker() {
        return textOpeningMarker;
    }

    public void setTextOpeningMarker(BasedSequence textOpeningMarker) {
        this.textOpeningMarker = textOpeningMarker;
    }

    public void setText(BasedSequence text) {
        this.text = text;
    }

    public BasedSequence getTextClosingMarker() {
        return textClosingMarker;
    }

    public void setTextClosingMarker(BasedSequence textClosingMarker) {
        this.textClosingMarker = textClosingMarker;
    }

    public BasedSequence getReferenceOpeningMarker() {
        return referenceOpeningMarker;
    }

    public void setReferenceOpeningMarker(BasedSequence referenceOpeningMarker) {
        this.referenceOpeningMarker = referenceOpeningMarker;
    }

    public void setReference(BasedSequence reference) {
        this.reference = reference;
    }

    public BasedSequence getReferenceClosingMarker() {
        return referenceClosingMarker;
    }

    public void setReferenceClosingMarker(BasedSequence referenceClosingMarker) {
        this.referenceClosingMarker = referenceClosingMarker;
    }

    @Override
    protected String toStringAttributes() {
        Reference node = getReferenceNode();
        return "text=" + text + ", reference=" + reference + ", referenceNode=" + (node == null ? "null" : node.toStringAttributes());
    }
}
