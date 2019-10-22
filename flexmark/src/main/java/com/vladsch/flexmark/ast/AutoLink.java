package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class AutoLink extends DelimitedLinkNode {
    public AutoLink() {
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, pageRef, anchorMarker, anchorRef, closingMarker };
    }

    @NotNull
    @Override
    public BasedSequence[] getSegmentsForChars() {
        return new BasedSequence[] {
                openingMarker,
                pageRef,
                anchorMarker,
                anchorRef,
                closingMarker
        };
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpanChars(out, openingMarker, "open");
        segmentSpanChars(out, text, "text");
        if (pageRef.isNotNull()) segmentSpanChars(out, pageRef, "pageRef");
        if (anchorMarker.isNotNull()) segmentSpanChars(out, anchorMarker, "anchorMarker");
        if (anchorRef.isNotNull()) segmentSpanChars(out, anchorRef, "anchorRef");
        segmentSpanChars(out, closingMarker, "close");
    }

    public AutoLink(BasedSequence chars) {
        super(chars);
    }

    public AutoLink(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(openingMarker, text, closingMarker);
        setUrlChars(text);
    }
}
