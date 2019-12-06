package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public interface AnchorRefTarget {
    String getAnchorRefText();
    String getAnchorRefId();
    void setAnchorRefId(String anchorRefId);

    boolean isExplicitAnchorRefId();
    void setExplicitAnchorRefId(boolean value);
}
