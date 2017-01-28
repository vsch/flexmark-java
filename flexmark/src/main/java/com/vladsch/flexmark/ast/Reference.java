package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class Reference extends Node implements ReferenceNode<ReferenceRepository, Reference, RefNode> {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence reference = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected BasedSequence urlOpeningMarker = BasedSequence.NULL;
    protected BasedSequence url = BasedSequence.NULL;
    protected BasedSequence pageRef = BasedSequence.NULL;
    protected BasedSequence anchorMarker = BasedSequence.NULL;
    protected BasedSequence anchorRef = BasedSequence.NULL;
    protected BasedSequence urlClosingMarker = BasedSequence.NULL;
    protected BasedSequence titleOpeningMarker = BasedSequence.NULL;
    protected BasedSequence title = BasedSequence.NULL;
    protected BasedSequence titleClosingMarker = BasedSequence.NULL;

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] {
                openingMarker,
                reference,
                closingMarker,
                urlOpeningMarker,
                url,
                pageRef,
                anchorMarker,
                anchorRef,
                urlClosingMarker,
                titleOpeningMarker,
                title,
                titleClosingMarker
        };
    }

    @Override
    public int compareTo(final Reference other) {
        return getReference().compareTo(other.getReference());
    }

    @Override
    public RefNode getReferencingNode(final Node node) {
        return node instanceof RefNode ? (RefNode) node : null;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        delimitedSegmentSpanChars(out, openingMarker, reference, closingMarker, "ref");
        delimitedSegmentSpanChars(out, urlOpeningMarker, url, urlClosingMarker, "url");
        delimitedSegmentSpanChars(out, titleOpeningMarker, title, titleClosingMarker, "title");
    }

    public Reference(BasedSequence label, BasedSequence url, BasedSequence title) {
        super(BasedSequence.NULL);

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

            // parse out the anchor marker and ref
            int pos = this.url.indexOf('#');
            if (pos < 0) {
                this.pageRef = this.url;
            } else {
                this.pageRef = this.url.subSequence(0, pos);
                this.anchorMarker = this.url.subSequence(pos, pos + 1);
                this.anchorRef = this.url.subSequence(pos + 1);
            }
        }

        if (title != null) {
            this.titleOpeningMarker = title.subSequence(0, 1);
            this.title = title.subSequence(1, title.length() - 1);
            this.titleClosingMarker = title.subSequence(title.length() - 1, title.length());
        }
        setCharsFromContent();
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

    public BasedSequence getTitleOpeningMarker() {
        return titleOpeningMarker;
    }

    public void setTitleOpeningMarker(BasedSequence titleOpeningMarker) {
        this.titleOpeningMarker = titleOpeningMarker;
    }

    public BasedSequence getTitleClosingMarker() {
        return titleClosingMarker;
    }

    public void setTitleClosingMarker(BasedSequence titleClosingMarker) {
        this.titleClosingMarker = titleClosingMarker;
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

    public BasedSequence getTitle() {
        return title;
    }

    public void setTitle(BasedSequence title) {
        this.title = title;
    }

    @Override
    protected String toStringAttributes() {
        return "reference=" + reference + ", url=" + url;
    }
}
