package com.vladsch.flexmark.ext.simtoc;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.SubSequence;
import com.vladsch.flexmark.node.CustomBlock;
import com.vladsch.flexmark.node.Visitor;

/**
 * A simulated toc block node
 */
public class SimTocBlock extends CustomBlock {
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence tocKeyword = SubSequence.NULL;
    protected BasedSequence style = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;
    protected BasedSequence anchorMarker = SubSequence.NULL;
    protected BasedSequence openingTitleMarker = SubSequence.NULL;
    protected BasedSequence title = SubSequence.NULL;
    protected BasedSequence closingTitleMarker = SubSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpan(out, openingMarker, "openingMarker");
        segmentSpan(out, tocKeyword, "tocKeyword");
        segmentSpan(out, style, "style");
        segmentSpan(out, closingMarker, "closingMarker");
        segmentSpan(out, anchorMarker, "anchorMarker");
        segmentSpan(out, openingTitleMarker, "openingTitleMarker");
        segmentSpan(out, title, "title");
        segmentSpan(out, closingTitleMarker, "closingTitleMarker");
    }

    @Override
    public BasedSequence[] getSegments() {
        BasedSequence[] nodeSegments = new BasedSequence[] { openingMarker, tocKeyword, style, closingMarker, anchorMarker, openingTitleMarker, title, closingTitleMarker };
        if (lineSegments.size() == 0) return nodeSegments;
        BasedSequence[] allSegments = new BasedSequence[lineSegments.size() + nodeSegments.length];
        lineSegments.toArray(allSegments);
        System.arraycopy(allSegments, 0, allSegments, nodeSegments.length, lineSegments.size());
        return allSegments;
    }

    public SimTocBlock(BasedSequence chars) {
        this(chars, null, null);
    }

    public SimTocBlock(BasedSequence chars, BasedSequence styleChars, BasedSequence titleChars) {
        super(chars);
        openingMarker = chars.subSequence(0, 4);
        if (styleChars != null) {
            style = styleChars;
        }
        int closingPos = chars.indexOf(']', 4);
        if (!(closingPos != -1 && closingPos + 1 < chars.length() && chars.charAt(closingPos + 1) == ':')) {
            throw new IllegalStateException("Invalid TOC block sequence");
        }
        closingMarker = chars.subSequence(closingPos, closingPos + 2);
        int anchorPos = chars.indexOf('#', closingPos + 2);
        if (anchorPos == -1) {
            throw new IllegalStateException("Invalid TOC block sequence");
        }
        anchorMarker = chars.subSequence(anchorPos, anchorPos + 1);

        if (titleChars != null) {
            if (titleChars.length() < 2) {
                throw new IllegalStateException("Invalid TOC block title sequence");
            }

            openingTitleMarker = titleChars.subSequence(0, 1);
            title = titleChars.midSequence(1, -1);
            closingTitleMarker = titleChars.endSequence(1);
        }
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public BasedSequence getTocKeyword() {
        return tocKeyword;
    }

    public BasedSequence getStyle() {
        return style;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public BasedSequence getAnchorMarker() {
        return anchorMarker;
    }

    public BasedSequence getOpeningTitleMarker() {
        return openingTitleMarker;
    }

    public BasedSequence getTitle() {
        return title;
    }

    public BasedSequence getClosingTitleMarker() {
        return closingTitleMarker;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
