package com.vladsch.flexmark.node;

public interface AnchorRefTarget {
    interface Visitor {
        void visit(AnchorRefTarget node);
    }

    String getAnchorRefText();
    String getAnchorRefId();
    void setAnchorRefId(String anchorRefId);
}
