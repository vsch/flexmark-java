package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

public interface DelimitedNode {
    BasedSequence getOpeningMarker();
    void setOpeningMarker(BasedSequence openingMarker);
    BasedSequence getText();
    void setText(BasedSequence text);
    BasedSequence getClosingMarker();
    void setClosingMarker(BasedSequence closingMarker);
}
