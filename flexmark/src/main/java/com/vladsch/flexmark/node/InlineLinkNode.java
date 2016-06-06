package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;

public abstract class InlineLinkNode extends LinkNode {
    protected BasedSequence textOpeningMarker = SubSequence.NULL;
    protected BasedSequence text = SubSequence.NULL;
    protected BasedSequence textClosingMarker = SubSequence.NULL;
    protected BasedSequence linkOpeningMarker = SubSequence.NULL;
    protected BasedSequence urlOpeningMarker = SubSequence.NULL;
    protected BasedSequence url = SubSequence.NULL;
    protected BasedSequence urlClosingMarker = SubSequence.NULL;
    protected BasedSequence titleOpeningMarker = SubSequence.NULL;
    protected BasedSequence title = SubSequence.NULL;
    protected BasedSequence titleClosingMarker = SubSequence.NULL;
    protected BasedSequence linkClosingMarker = SubSequence.NULL;

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] {
                textOpeningMarker,
                text,
                textClosingMarker,
                linkOpeningMarker,
                urlOpeningMarker,
                url,
                urlClosingMarker,
                titleOpeningMarker,
                title,
                titleClosingMarker,
                linkClosingMarker
        };
    }

    public InlineLinkNode() {
    }

    public InlineLinkNode(BasedSequence chars) {
        super(chars);
    }

    public InlineLinkNode(BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence linkOpeningMarker, BasedSequence url, BasedSequence titleOpeningMarker, BasedSequence title, BasedSequence titleClosingMarker, BasedSequence linkClosingMarker) {
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

    public InlineLinkNode(BasedSequence chars, BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence linkOpeningMarker, BasedSequence url, BasedSequence titleOpeningMarker, BasedSequence title, BasedSequence titleClosingMarker, BasedSequence linkClosingMarker) {
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

    public InlineLinkNode(BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence linkOpeningMarker, BasedSequence url, BasedSequence linkClosingMarker) {
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.linkOpeningMarker = linkOpeningMarker;
        this.url = url;
        this.linkClosingMarker = linkClosingMarker;
    }

    public InlineLinkNode(BasedSequence chars, BasedSequence textOpeningMarker, BasedSequence text, BasedSequence textClosingMarker, BasedSequence linkOpeningMarker, BasedSequence url, BasedSequence linkClosingMarker) {
        super(chars);
        this.textOpeningMarker = textOpeningMarker;
        this.text = text;
        this.textClosingMarker = textClosingMarker;
        this.linkOpeningMarker = linkOpeningMarker;
        this.url = url;
        this.linkClosingMarker = linkClosingMarker;
    }

    public void setTitleChars(BasedSequence titleChars) {
        if (titleChars != null && titleChars != SubSequence.NULL) {
            int titleCharsLength = titleChars.length();
            titleOpeningMarker = titleChars.subSequence(0, 1);
            title = titleChars.subSequence(1, titleCharsLength - 1);
            titleClosingMarker = titleChars.subSequence(titleCharsLength - 1, titleCharsLength);
        } else {
            titleOpeningMarker = SubSequence.NULL;
            title = SubSequence.NULL;
            titleClosingMarker = SubSequence.NULL;
        }
    }

    public void setTextChars(BasedSequence textChars) {
        int textCharsLength = textChars.length();
        this.textOpeningMarker = textChars.subSequence(0, 1);
        this.text = textChars.subSequence(1, textCharsLength - 1).trim();
        this.textClosingMarker = textChars.subSequence(textCharsLength - 1, textCharsLength);
    }

    public void setUrl(BasedSequence linkOpeningMarker, BasedSequence url, BasedSequence linkClosingMarker) {
        this.linkOpeningMarker = linkOpeningMarker;
        this.setUrlChars(url);
        this.linkClosingMarker = linkClosingMarker;
    }

    public void setUrlChars(BasedSequence url) {
        if (url != null && url != SubSequence.NULL) {
            // strip off <> wrapping
            if (url.startsWith("<") && url.endsWith(">")) {
                urlOpeningMarker = url.subSequence(0, 1);
                this.url = url.subSequence(1, url.length() - 1);
                urlClosingMarker = url.subSequence(url.length() - 1);
            } else {
                this.url = url;
            }
        } else {
            this.urlOpeningMarker = SubSequence.NULL;
            this.url = SubSequence.NULL;
            this.urlClosingMarker = SubSequence.NULL;
        }
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

    public BasedSequence getLinkOpeningMarker() {
        return linkOpeningMarker;
    }

    public void setLinkOpeningMarker(BasedSequence linkOpeningMarker) {
        this.linkOpeningMarker = linkOpeningMarker;
    }

    public BasedSequence getUrlOpeningMarker() {
        return urlOpeningMarker;
    }

    public void setUrlOpeningMarker(BasedSequence urlOpeningMarker) {
        this.urlOpeningMarker = urlOpeningMarker;
    }

    public void setUrl(BasedSequence url) {
        this.url = url;
    }

    public BasedSequence getUrlClosingMarker() {
        return urlClosingMarker;
    }

    public void setUrlClosingMarker(BasedSequence urlClosingMarker) {
        this.urlClosingMarker = urlClosingMarker;
    }

    public BasedSequence getTitleOpeningMarker() {
        return titleOpeningMarker;
    }

    public void setTitleOpeningMarker(BasedSequence titleOpeningMarker) {
        this.titleOpeningMarker = titleOpeningMarker;
    }

    public void setTitle(BasedSequence title) {
        this.title = title;
    }

    public BasedSequence getTitleClosingMarker() {
        return titleClosingMarker;
    }

    public void setTitleClosingMarker(BasedSequence titleClosingMarker) {
        this.titleClosingMarker = titleClosingMarker;
    }

    public BasedSequence getLinkClosingMarker() {
        return linkClosingMarker;
    }

    public void setLinkClosingMarker(BasedSequence linkClosingMarker) {
        this.linkClosingMarker = linkClosingMarker;
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + text + ", url=" + url + ", title=" + title;
    }
}
