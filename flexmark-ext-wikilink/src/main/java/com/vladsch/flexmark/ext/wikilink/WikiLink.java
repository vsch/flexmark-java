package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.ast.CustomNode;
import com.vladsch.flexmark.ast.DoNotDecorate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SubSequence;

public class WikiLink extends CustomNode implements DoNotDecorate {
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence link = SubSequence.NULL;
    protected BasedSequence pageRef = SubSequence.NULL;
    protected BasedSequence anchorMarker = SubSequence.NULL;
    protected BasedSequence anchorRef = SubSequence.NULL;
    protected BasedSequence textSeparatorMarker = SubSequence.NULL;
    protected BasedSequence text = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;
    final protected boolean linkIsFirst;

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

    public WikiLink(boolean linkIsFirst) {
        this.linkIsFirst = linkIsFirst;
    }

    public WikiLink(BasedSequence chars, boolean linkIsFirst) {
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
        openingMarker = linkChars.subSequence(0, 2);
        closingMarker = linkChars.subSequence(length - 2, length);

        int pos = linkChars.indexOf('|');
        BasedSequence link;
        if (pos < 0) {
            link = linkChars.subSequence(2, length - 2);
        } else {
            textSeparatorMarker = linkChars.subSequence(pos, pos + 1);
            if (linkIsFirst) {
                link = linkChars.subSequence(2, pos);
                text = linkChars.subSequence(pos + 1, length - 2);
            } else {
                text = linkChars.subSequence(2, pos);
                link = linkChars.subSequence(pos + 1, length - 2);
            }
        }

        // now parse out the # from the link
        this.link = link;
        
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
