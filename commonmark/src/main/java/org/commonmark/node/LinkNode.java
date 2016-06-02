package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SubSequence;

public abstract class LinkNode extends Node {
    public BasedSequence textOpeningMarker = SubSequence.EMPTY;
    public BasedSequence text = SubSequence.EMPTY;
    public BasedSequence textClosingMarker = SubSequence.EMPTY;
    public BasedSequence linkOpeningMarker = SubSequence.EMPTY;
    public BasedSequence url = SubSequence.EMPTY;
    public BasedSequence titleOpeningMarker = SubSequence.EMPTY;
    public BasedSequence title = SubSequence.EMPTY;
    public BasedSequence titleClosingMarker = SubSequence.EMPTY;
    public BasedSequence linkClosingMarker = SubSequence.EMPTY;

    public LinkNode() {
    }

    public LinkNode(BasedSequence chars) {
        super(chars);
    }

    public LinkNode(BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence linkOpeningMarker, BasedSequence url, BasedSequence titleOpeningMarker, BasedSequence title, BasedSequence titleClosingMarker, BasedSequence linkClosingMarker) {
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.linkOpeningMarker = linkOpeningMarker;
        this.url = url;
        this.titleOpeningMarker = titleOpeningMarker;
        this.title = title;
        this.titleClosingMarker = titleClosingMarker;
        this.linkClosingMarker = linkClosingMarker;
    }

    public LinkNode(BasedSequence chars, BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence linkOpeningMarker, BasedSequence url, BasedSequence titleOpeningMarker, BasedSequence title, BasedSequence titleClosingMarker, BasedSequence linkClosingMarker) {
        super(chars);
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.linkOpeningMarker = linkOpeningMarker;
        this.url = url;
        this.titleOpeningMarker = titleOpeningMarker;
        this.title = title;
        this.titleClosingMarker = titleClosingMarker;
        this.linkClosingMarker = linkClosingMarker;
    }

    public LinkNode(BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence linkOpeningMarker, BasedSequence url, BasedSequence linkClosingMarker) {
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.linkOpeningMarker = linkOpeningMarker;
        this.url = url;
        this.linkClosingMarker = linkClosingMarker;
    }

    public LinkNode(BasedSequence chars, BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence linkOpeningMarker, BasedSequence url, BasedSequence linkClosingMarker) {
        super(chars);
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.linkOpeningMarker = linkOpeningMarker;
        this.url = url;
        this.linkClosingMarker = linkClosingMarker;
    }

    public void setTitleChars(BasedSequence titleChars) {
        if (titleChars != null) {
            int titleCharsLength = titleChars.length();
            titleOpeningMarker = titleChars.subSequence(0, 1);
            title = titleChars.subSequence(1, titleCharsLength - 1);
            titleClosingMarker = titleChars.subSequence(titleCharsLength - 1, 1);
        } else {
            titleOpeningMarker = SubSequence.EMPTY;
            title = SubSequence.EMPTY;
            titleClosingMarker = SubSequence.EMPTY;
        }
    }

    public void setTextChars(BasedSequence textChars) {
        int textCharsLength = textChars.length();
        this.textOpeningMarker = textChars.subSequence(0, 1);
        this.text = textChars.subSequence(1, textCharsLength - 1).trim();
        this.textClosingMarker = textChars.subSequence(textCharsLength - 1, textCharsLength);
    }

    public BasedSequence getText() {
        return text;
    }

    public BasedSequence getUrl() {
        return url;
    }

    public BasedSequence getTitle() {
        return title;
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + text + ", url=" + url + ", title=" + title;
    }

}
