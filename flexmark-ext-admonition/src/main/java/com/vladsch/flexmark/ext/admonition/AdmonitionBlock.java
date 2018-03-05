package com.vladsch.flexmark.ext.admonition;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

/**
 * An Admonition block node
 */
public class AdmonitionBlock extends Block {
    private BasedSequence openingMarker = BasedSequence.NULL;
    private BasedSequence info = BasedSequence.NULL;
    protected BasedSequence titleOpeningMarker = BasedSequence.NULL;
    protected BasedSequence title = BasedSequence.NULL;
    protected BasedSequence titleClosingMarker = BasedSequence.NULL;

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] {
                openingMarker,
                info,
                titleOpeningMarker,
                title,
                titleClosingMarker,
        };
    }

    @Override
    public BasedSequence[] getSegmentsForChars() {
        return new BasedSequence[] {
                openingMarker,
                info,
                titleOpeningMarker,
                title,
                titleClosingMarker,
        };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        BasedSequence content = getContentChars();
        int lines = getContentLines().size();
        segmentSpanChars(out, openingMarker, "open");
        segmentSpanChars(out, info, "info");
        delimitedSegmentSpanChars(out, titleOpeningMarker, title, titleClosingMarker, "title");
    }

    public AdmonitionBlock() {
    }

    public AdmonitionBlock(BasedSequence chars) {
        super(chars);
    }

    public AdmonitionBlock(BasedSequence chars, BasedSequence openingMarker, BasedSequence info, List<BasedSequence> segments) {
        super(chars, segments);
        this.openingMarker = openingMarker;
        this.info = info;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public void setInfo(BasedSequence info) {
        this.info = info;
    }

    public BasedSequence getInfo() {
        return info;
    }

    public BasedSequence getTitle() {
        return title;
    }

    public BasedSequence getTitleOpeningMarker() {
        return titleOpeningMarker;
    }

    public void setTitleOpeningMarker(final BasedSequence titleOpeningMarker) {
        this.titleOpeningMarker = titleOpeningMarker;
    }

    public void setTitle(final BasedSequence title) {
        this.title = title;
    }

    public BasedSequence getTitleClosingMarker() {
        return titleClosingMarker;
    }

    public void setTitleClosingMarker(final BasedSequence titleClosingMarker) {
        this.titleClosingMarker = titleClosingMarker;
    }

    public BasedSequence getTitleChars() {
        return spanningChars(titleOpeningMarker, title, titleClosingMarker);
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
}
