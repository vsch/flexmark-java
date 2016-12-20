package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public abstract class InlineLinkNode extends LinkNode {
    protected BasedSequence textOpeningMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence textClosingMarker = BasedSequence.NULL;
    protected BasedSequence linkOpeningMarker = BasedSequence.NULL;
    protected BasedSequence urlOpeningMarker = BasedSequence.NULL;
    protected BasedSequence url = BasedSequence.NULL;
    protected BasedSequence pageRef = BasedSequence.NULL;
    protected BasedSequence anchorMarker = BasedSequence.NULL;
    protected BasedSequence anchorRef = BasedSequence.NULL;
    protected BasedSequence urlClosingMarker = BasedSequence.NULL;
    protected BasedSequence titleOpeningMarker = BasedSequence.NULL;
    protected BasedSequence title = BasedSequence.NULL;
    protected BasedSequence titleClosingMarker = BasedSequence.NULL;
    protected BasedSequence linkClosingMarker = BasedSequence.NULL;

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] {
                textOpeningMarker,
                text,
                textClosingMarker,
                linkOpeningMarker,
                urlOpeningMarker,
                url,
                pageRef,
                anchorMarker,
                anchorRef,
                urlClosingMarker,
                titleOpeningMarker,
                title,
                titleClosingMarker,
                linkClosingMarker
        };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        delimitedSegmentSpanChars(out, textOpeningMarker, text, textClosingMarker, "text");
        segmentSpanChars(out, linkOpeningMarker, "linkOpen");
        delimitedSegmentSpanChars(out, urlOpeningMarker, url, urlClosingMarker, "url");
        if (pageRef.isNotNull()) segmentSpanChars(out, pageRef, "pageRef");
        if (anchorMarker.isNotNull()) segmentSpanChars(out, anchorMarker, "anchorMarker");
        if (anchorRef.isNotNull()) segmentSpanChars(out, anchorRef, "anchorRef");
        delimitedSegmentSpanChars(out, titleOpeningMarker, title, titleClosingMarker, "title");
        segmentSpanChars(out, linkClosingMarker, "linkClose");
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
        if (titleChars != null && titleChars != BasedSequence.NULL) {
            int titleCharsLength = titleChars.length();
            titleOpeningMarker = titleChars.subSequence(0, 1);
            title = titleChars.subSequence(1, titleCharsLength - 1);
            titleClosingMarker = titleChars.subSequence(titleCharsLength - 1, titleCharsLength);
        } else {
            titleOpeningMarker = BasedSequence.NULL;
            title = BasedSequence.NULL;
            titleClosingMarker = BasedSequence.NULL;
        }
    }

    public abstract void setTextChars(BasedSequence textChars);

    public void setUrl(BasedSequence linkOpeningMarker, BasedSequence url, BasedSequence linkClosingMarker) {
        this.linkOpeningMarker = linkOpeningMarker;
        this.setUrlChars(url);
        this.linkClosingMarker = linkClosingMarker;
    }

    public void setUrlChars(BasedSequence url) {
        if (url != null && url != BasedSequence.NULL) {
            // strip off <> wrapping
            if (url.startsWith("<") && url.endsWith(">")) {
                urlOpeningMarker = url.subSequence(0, 1);
                this.url = url.subSequence(1, url.length() - 1);
                urlClosingMarker = url.subSequence(url.length() - 1);
            } else {
                this.url = url;
            }

            // parse out the anchor marker and ref
            int pos = this.url.indexOf('#');
            if (pos < 0) {
                this.pageRef = this.url;
            } else {
                this.pageRef = this.url.subSequence(0, pos);
                this.anchorMarker = this.url.subSequence(pos, pos + 1);
                this.anchorRef = this.url.subSequence(pos + 1);
            }
        } else {
            this.urlOpeningMarker = BasedSequence.NULL;
            this.url = BasedSequence.NULL;
            this.urlClosingMarker = BasedSequence.NULL;
        }
    }

    public BasedSequence getPageRef() {
        return pageRef;
    }

    public void setPageRef(BasedSequence pageRef) {
        this.pageRef = pageRef;
    }

    public BasedSequence getAnchorMarker() {
        return anchorMarker;
    }

    public void setAnchorMarker(BasedSequence anchorMarker) {
        this.anchorMarker = anchorMarker;
    }

    public BasedSequence getAnchorRef() {
        return anchorRef;
    }

    public void setAnchorRef(BasedSequence anchorRef) {
        this.anchorRef = anchorRef;
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
