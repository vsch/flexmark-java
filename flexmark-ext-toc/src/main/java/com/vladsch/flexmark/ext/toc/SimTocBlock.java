package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SubSequence;

/**
 * A simulated toc block ast
 */
public class SimTocBlock extends TocBlockBase {
    protected BasedSequence anchorMarker = SubSequence.NULL;
    protected BasedSequence openingTitleMarker = SubSequence.NULL;
    protected BasedSequence title = SubSequence.NULL;
    protected BasedSequence closingTitleMarker = SubSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        super.getAstExtra(out);
        segmentSpanChars(out, anchorMarker, "anchorMarker");
        segmentSpanChars(out, openingTitleMarker, "openingTitleMarker");
        segmentSpanChars(out, title, "title");
        segmentSpanChars(out, closingTitleMarker, "closingTitleMarker");
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
        super(chars, styleChars, true);
        int anchorPos = chars.indexOf('#', closingMarker.getEndOffset() - chars.getStartOffset());
        if (anchorPos == -1) {
            throw new IllegalStateException("Invalid TOC block sequence");
        }
        anchorMarker = chars.subSequence(anchorPos, anchorPos + 1);

        if (titleChars != null) {
            if (titleChars.length() < 2) {
                throw new IllegalStateException("Invalid TOC block title sequence");
            }

            openingTitleMarker = titleChars.subSequence(0, 1);
            title = titleChars.midSequence(1, -1).nullIfEmpty();
            closingTitleMarker = titleChars.endSequence(1);
        }
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
}
