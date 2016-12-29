package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.ast.CustomNode;
import com.vladsch.flexmark.ast.DoNotDecorate;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class WikiNode extends CustomNode implements DoNotDecorate {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence link = BasedSequence.NULL;
    protected BasedSequence pageRef = BasedSequence.NULL;
    protected BasedSequence anchorMarker = BasedSequence.NULL;
    protected BasedSequence anchorRef = BasedSequence.NULL;
    protected BasedSequence textSeparatorMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected final boolean linkIsFirst;

    @Override
    public BasedSequence[] getSegments() {
        if (linkIsFirst) {
            return new BasedSequence[] {
                    openingMarker,
                    link,
                    pageRef,
                    anchorMarker,
                    anchorRef,
                    textSeparatorMarker,
                    text,
                    closingMarker
            };
        } else {
            return new BasedSequence[] {
                    openingMarker,
                    text,
                    textSeparatorMarker,
                    link,
                    pageRef,
                    anchorMarker,
                    anchorRef,
                    closingMarker
            };
        }
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        if (linkIsFirst) {
            segmentSpanChars(out, openingMarker, "linkOpen");
            segmentSpanChars(out, text, "text");
            segmentSpanChars(out, textSeparatorMarker, "textSep");
            segmentSpanChars(out, link, "link");
            if (pageRef.isNotNull()) segmentSpanChars(out, pageRef, "pageRef");
            if (anchorMarker.isNotNull()) segmentSpanChars(out, anchorMarker, "anchorMarker");
            if (anchorRef.isNotNull()) segmentSpanChars(out, anchorRef, "anchorRef");
            segmentSpanChars(out, closingMarker, "linkClose");
        } else {
            segmentSpanChars(out, openingMarker, "linkOpen");
            segmentSpanChars(out, link, "link");
            if (pageRef.isNotNull()) segmentSpanChars(out, pageRef, "pageRef");
            if (anchorMarker.isNotNull()) segmentSpanChars(out, anchorMarker, "anchorMarker");
            if (anchorRef.isNotNull()) segmentSpanChars(out, anchorRef, "anchorRef");
            segmentSpanChars(out, textSeparatorMarker, "textSep");
            segmentSpanChars(out, text, "text");
            segmentSpanChars(out, closingMarker, "linkClose");
        }
    }

    public boolean isLinkIsFirst() {
        return linkIsFirst;
    }

    public WikiNode(boolean linkIsFirst) {
        this.linkIsFirst = linkIsFirst;
    }

    public WikiNode(BasedSequence chars, boolean linkIsFirst) {
        super(chars);
        this.linkIsFirst = linkIsFirst;
        setLinkChars(chars);
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getPageRef() {
        return pageRef;
    }

    public void setPageRef(BasedSequence pageRef) {
        this.pageRef = pageRef;
    }

    public BasedSequence getTextSeparatorMarker() {
        return textSeparatorMarker;
    }

    public void setTextSeparatorMarker(BasedSequence textSeparatorMarker) {
        this.textSeparatorMarker = textSeparatorMarker;
    }

    public BasedSequence getText() {
        return text;
    }

    public void setText(BasedSequence text) {
        this.text = text;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
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

    public BasedSequence getLink() {
        return link;
    }

    public void setLink(BasedSequence link) {
        this.link = link;
    }

    public void setLinkChars(BasedSequence linkChars) {
        int length = linkChars.length();
        final int start = this instanceof WikiImage ? 3 : 2;
        openingMarker = linkChars.subSequence(0, start);
        closingMarker = linkChars.subSequence(length - 2, length);

        int pos = linkChars.indexOf('|');
        BasedSequence link;
        if (pos < 0) {
            link = linkChars.subSequence(start, length - 2);
        } else {
            textSeparatorMarker = linkChars.subSequence(pos, pos + 1);
            if (linkIsFirst) {
                link = linkChars.subSequence(start, pos);
                text = linkChars.subSequence(pos + 1, length - 2);
            } else {
                text = linkChars.subSequence(start, pos);
                link = linkChars.subSequence(pos + 1, length - 2);
            }
        }

        // now parse out the # from the link
        this.link = link;

        if (((this instanceof WikiImage))) {
            this.pageRef = link;
        } else {
            pos = link.indexOf('#');
            if (pos < 0) {
                this.pageRef = link;
            } else {
                this.pageRef = link.subSequence(0, pos);
                this.anchorMarker = link.subSequence(pos, pos + 1);
                this.anchorRef = link.subSequence(pos + 1);
            }
        }
    }
}
