package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;

public class Reference extends Node {
    public BasedSequence openingMarker = SubSequence.NULL;
    public BasedSequence reference = SubSequence.NULL;
    public BasedSequence closingMarker = SubSequence.NULL;
    public BasedSequence urlOpeningMarker = SubSequence.NULL;
    public BasedSequence url = SubSequence.NULL;
    public BasedSequence urlClosingMarker = SubSequence.NULL;
    public BasedSequence titleOpenMarker = SubSequence.NULL;
    public BasedSequence title = SubSequence.NULL;
    public BasedSequence titleCloseMarker = SubSequence.NULL;

    public Reference(BasedSequence label, BasedSequence url, BasedSequence title) {
        super(new SubSequence(label.getBase().subSequence(label.getStartOffset(), (title != null ? title.getEndOffset() : (url != null ? url.getEndOffset() : label.getEndOffset())))));

        this.openingMarker = label.subSequence(0, 1);
        this.reference = label.subSequence(1, label.length() - 2).trim();
        this.closingMarker = label.subSequence(label.length() - 2, label.length());

        if (url != null) {
            // strip off <> wrapping
            if (url.startsWith("<") && url.endsWith(">")) {
                urlOpeningMarker = url.subSequence(0, 1);
                this.url = url.subSequence(1, url.length() - 1);
                urlClosingMarker = url.subSequence(url.length() - 1);
            } else {
                this.url = url;
            }
        }

        if (title != null) {
            this.titleOpenMarker = title.subSequence(0, 1);
            this.title = title.subSequence(1, title.length() - 1);
            this.titleCloseMarker = title.subSequence(title.length() - 1, title.length());
        }
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public BasedSequence getUrlOpeningMarker() {
        return urlOpeningMarker;
    }

    public void setUrlOpeningMarker(BasedSequence urlOpeningMarker) {
        this.urlOpeningMarker = urlOpeningMarker;
    }

    public BasedSequence getUrlClosingMarker() {
        return urlClosingMarker;
    }

    public void setUrlClosingMarker(BasedSequence urlClosingMarker) {
        this.urlClosingMarker = urlClosingMarker;
    }

    public BasedSequence getTitleOpenMarker() {
        return titleOpenMarker;
    }

    public void setTitleOpenMarker(BasedSequence titleOpenMarker) {
        this.titleOpenMarker = titleOpenMarker;
    }

    public BasedSequence getTitleCloseMarker() {
        return titleCloseMarker;
    }

    public void setTitleCloseMarker(BasedSequence titleCloseMarker) {
        this.titleCloseMarker = titleCloseMarker;
    }

    public BasedSequence getReference() {
        return reference;
    }

    public void setReference(BasedSequence reference) {
        this.reference = reference;
    }

    public BasedSequence getUrl() {
        return url;
    }

    public void setUrl(BasedSequence url) {
        this.url = url;
    }

    public BasedSequence getTitle() {
        return title;
    }

    public void setTitle(BasedSequence title) {
        this.title = title;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    protected String toStringAttributes() {
        return "reference=" + reference + ", url=" + url;
    }
}
