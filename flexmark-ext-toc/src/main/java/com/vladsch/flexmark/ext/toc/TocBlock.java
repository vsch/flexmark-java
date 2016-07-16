package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.SubSequence;

/**
 * A TOC node
 */
public class TocBlock extends TocBlockBase {
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence tocKeyword = SubSequence.NULL;
    protected BasedSequence style = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpan(out, openingMarker, "openingMarker");
        segmentSpan(out, tocKeyword, "tocKeyword");
        segmentSpan(out, style, "style");
        segmentSpan(out, closingMarker, "closingMarker");
    }

    @Override
    public BasedSequence[] getSegments() {
        BasedSequence[] nodeSegments = new BasedSequence[]{openingMarker, tocKeyword, style, closingMarker};
        if (lineSegments.size() == 0) return nodeSegments;
        BasedSequence[] allSegments = new BasedSequence[lineSegments.size() + nodeSegments.length];
        lineSegments.toArray(allSegments);
        System.arraycopy(allSegments, 0, allSegments, nodeSegments.length, lineSegments.size());
        return allSegments;
    }

    public TocBlock(BasedSequence chars) {
        this(chars, false);
    }

    public TocBlock(BasedSequence chars, boolean closingSimToc) {
        this(chars, null, closingSimToc);
    }

    public TocBlock(BasedSequence chars, BasedSequence styleChars) {
        this(chars, styleChars, false);
    }

    public TocBlock(BasedSequence chars, BasedSequence styleChars, boolean closingSimToc) {
        super(chars);
        openingMarker = chars.subSequence(0, 1);
        tocKeyword = chars.subSequence(1, 4);
        if (styleChars != null) {
            style = styleChars;
        }
        int closingPos = chars.indexOf(']', 4);
        if (closingSimToc && !(closingPos != -1 && closingPos + 1 < chars.length() && chars.charAt(closingPos + 1) == ':')) {
            throw new IllegalStateException("Invalid TOC block sequence");
        }
        closingMarker = chars.subSequence(closingPos, closingPos + (closingSimToc ? 2 : 1));
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
}
