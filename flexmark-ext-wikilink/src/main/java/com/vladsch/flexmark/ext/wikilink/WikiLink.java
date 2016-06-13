package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.node.CustomNode;
import com.vladsch.flexmark.node.DoNotLinkify;
import com.vladsch.flexmark.node.Visitor;

public class WikiLink extends CustomNode implements DoNotLinkify {
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence link = SubSequence.NULL;
    protected BasedSequence textSeparatorMarker = SubSequence.NULL;
    protected BasedSequence text = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;
    final protected boolean linkIsFirst;

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] {
                openingMarker,
                link,
                textSeparatorMarker,
                text,
                closingMarker
        };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        if (linkIsFirst) {
            segmentSpanChars(out, openingMarker, "open");
            segmentSpanChars(out, text, "text");
            segmentSpanChars(out, textSeparatorMarker, "textSep");
            segmentSpanChars(out, link, "link");
            segmentSpanChars(out, closingMarker, "close");
        } else {
            segmentSpanChars(out, openingMarker, "linkOpen");
            segmentSpanChars(out, link, "link");
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

    public BasedSequence getLink() {
        return link;
    }

    public void setLink(BasedSequence link) {
        this.link = link;
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

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void setLinkChars(BasedSequence linkChars) {
        int length = linkChars.length();
        openingMarker = linkChars.subSequence(0, 2);
        closingMarker = linkChars.subSequence(length - 2, length);

        int pos = linkChars.indexOf('|');
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
    }
}
