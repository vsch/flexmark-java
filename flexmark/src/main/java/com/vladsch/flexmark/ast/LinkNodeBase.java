package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public abstract class LinkNodeBase extends Node {
    protected BasedSequence urlOpeningMarker = BasedSequence.NULL;
    protected BasedSequence url = BasedSequence.NULL;
    protected BasedSequence pageRef = BasedSequence.NULL;
    protected BasedSequence anchorMarker = BasedSequence.NULL;
    protected BasedSequence anchorRef = BasedSequence.NULL;
    protected BasedSequence urlClosingMarker = BasedSequence.NULL;
    protected BasedSequence titleOpeningMarker = BasedSequence.NULL;
    protected BasedSequence title = BasedSequence.NULL;
    protected BasedSequence titleClosingMarker = BasedSequence.NULL;

    public LinkNodeBase() {
    }

    public LinkNodeBase(BasedSequence chars) {
        super(chars);
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

    public BasedSequence getUrl() {
        return url;
    }

    public BasedSequence getTitle() {
        return title;
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
}
