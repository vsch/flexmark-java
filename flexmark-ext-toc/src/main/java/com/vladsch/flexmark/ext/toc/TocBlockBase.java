package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A TOC node
 */
public abstract class TocBlockBase extends Block {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence tocKeyword = BasedSequence.NULL;
    protected BasedSequence style = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpan(out, openingMarker, "openingMarker");
        segmentSpan(out, tocKeyword, "tocKeyword");
        segmentSpan(out, style, "style");
        segmentSpan(out, closingMarker, "closingMarker");
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        BasedSequence[] nodeSegments = new BasedSequence[] { openingMarker, tocKeyword, style, closingMarker };
        if (lineSegments.size() == 0) return nodeSegments;
        BasedSequence[] allSegments = new BasedSequence[lineSegments.size() + nodeSegments.length];
        lineSegments.toArray(allSegments);
        System.arraycopy(allSegments, 0, allSegments, nodeSegments.length, lineSegments.size());
        return allSegments;
    }

    public TocBlockBase(BasedSequence chars) {
        this(chars, false);
    }

    public TocBlockBase(BasedSequence chars, boolean closingSimToc) {
        this(chars, null, closingSimToc);
    }

    public TocBlockBase(BasedSequence chars, BasedSequence styleChars) {
        this(chars, styleChars, false);
    }

    public TocBlockBase(BasedSequence chars, BasedSequence styleChars, boolean closingSimToc) {
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
