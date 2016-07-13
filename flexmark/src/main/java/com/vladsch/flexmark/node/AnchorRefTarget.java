package com.vladsch.flexmark.node;

public interface AnchorRefTarget {
    String getAnchorRefText();
    String getAnchorRefId();
    void setAnchorRefId(String anchorRefId);
}
