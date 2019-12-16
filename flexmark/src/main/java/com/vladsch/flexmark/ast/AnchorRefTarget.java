package com.vladsch.flexmark.ast;

public interface AnchorRefTarget {
    String getAnchorRefText();
    String getAnchorRefId();
    void setAnchorRefId(String anchorRefId);

    boolean isExplicitAnchorRefId();
    void setExplicitAnchorRefId(boolean value);
}
