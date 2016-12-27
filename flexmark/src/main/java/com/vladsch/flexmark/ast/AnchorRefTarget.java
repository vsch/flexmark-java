package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public interface AnchorRefTarget {
    String getAnchorRefText();
    BasedSequence[] getAnchorRefSegments();
    String getAnchorRefId();
    void setAnchorRefId(String anchorRefId);
}
