package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public interface DelimitedNode {
    BasedSequence getOpeningMarker();
    void setOpeningMarker(BasedSequence openingMarker);
    BasedSequence getText();
    void setText(BasedSequence text);
    BasedSequence getClosingMarker();
    void setClosingMarker(BasedSequence closingMarker);
}
