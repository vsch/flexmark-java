package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;

public interface DelimitedNode {
    BasedSequence getOpeningMarker();
    void setOpeningMarker(BasedSequence openingMarker);
    BasedSequence getContent();
    void setContent(BasedSequence content);
    BasedSequence getClosingMarker();
    void setClosingMarker(BasedSequence closingMarker);
}
